from Variaveis import HOST, PORT, PERGUNTAS
import socket
import time
import random

class ConexaoJogador:
	def __init__(self, conexao, endereco, id_jogador, nome):
		self.conexao = conexao
		self.endereco = endereco
		self.id_jogador = id_jogador
		self.nome = nome
		self.pontos = 0

	def enviar(self, mensagem: str):
		try:
			self.conexao.sendall(mensagem.encode())
		except:
			pass

	def receber(self) -> str:
		try:
			return self.conexao.recv(1024).decode().strip().lower()
		except:
			return ""

	def fechar(self):
		self.conexao.close()


class Partida:
	pontos_acerto = [5, 7, 10]
	pontos_erro = [-5, -5, -3]
	
	def __init__(self, jogadores):
		self.jogadores = jogadores
		self.perguntas = PERGUNTAS
		self.rodada = 0
		self.jogador_da_vez = random.choice(self.jogadores)
		self.contador = 0

	def proxima_rodada(self):
		self.rodada = self.rodada + 1
		self.jogador_da_vez = self.jogadores[1 - self.jogadores.index(self.jogador_da_vez)]
		self.contador = 0

	def lidar_com_pergunta(self, jogador, pergunta: str, resposta_correta: str):
		self.enviar_para_todos(f"\n🎯 {jogador.nome.upper()}, é sua vez!\n")
		self.enviar_para_todos(f"📢 Pergunta: {pergunta}\n")

		# Sempre envia ⏳ antes de esperar resposta
		if self.contador == 0:
			jogador.enviar("⏳ Digite a resposta (v/f) ou 'passa'")
		elif self.contador == 1:
			jogador.enviar("⏳ Digite a resposta (v/f) ou 'repassa'")
		else:
			jogador.enviar("⏳ Adversário repassou a pergunta, agora você deve responder (v/f)")

		resposta = jogador.receber()

		if self.contador < 2 and (resposta == 'passa' or resposta == 'repassa'):
			self.enviar_para_todos(f"\n🔁 {jogador.nome} passou a vez.")
			proximo_jogador = self.jogadores[1 - self.jogadores.index(jogador)]
			self.contador += 1
			self.lidar_com_pergunta(proximo_jogador, pergunta, resposta_correta)
			return
		
		if resposta == 'fim':
			jogador.enviar("👋 Jogo encerrado por você.")
			jogador.pontos = 30
		elif resposta == resposta_correta:
			pontos = self.pontos_acerto[self.contador]
			self.enviar_para_todos(f"\n✅ {jogador.nome} acertou! [+{pontos} pontos]")
			jogador.pontos += pontos
		else:
			pontos = self.pontos_erro[self.contador]
			self.enviar_para_todos(f"\n❌ {jogador.nome} errou! [{pontos} pontos]")
			jogador.pontos += pontos

	def enviar_para_todos(self, msg):
		for jogador in self.jogadores:
			jogador.enviar(msg)

	def iniciar(self):
		while True:
			if self.jogadores[0].pontos >= 30 or self.jogadores[1].pontos >= 30 or len(self.perguntas) == 0:
				break
			self.enviar_para_todos("\n📊 PLACAR ATUAL:")
			for jogador in self.jogadores:
				self.enviar_para_todos(f"\n\n{jogador.nome}: {jogador.pontos} pontos")

			indice = random.randrange(len(self.perguntas))
			pergunta, resposta_correta = self.perguntas.pop(indice)

			self.lidar_com_pergunta(self.jogador_da_vez, pergunta, resposta_correta)

			time.sleep(1)
			self.proxima_rodada()

		self.enviar_para_todos("\n🏁 JOGO ENCERRADO!")
		vencedor = max(self.jogadores, key=lambda j: j.pontos)
		self.enviar_para_todos(f"🥇 VENCEDOR: {vencedor.nome} com {vencedor.pontos} pontos!\n")

class ServidorJogo:
	def __init__(self):
		self.host = HOST
		self.porta = PORT
		self.socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
		self.jogadores = []

	def iniciar(self):
		self.socket.bind((self.host, self.porta))
		self.socket.listen(2)
		print(f"Servidor iniciado em {self.host}:{self.porta}")
		self.aguardar_jogadores()
		self.iniciar_partida()
		self.reiniciar()

	def reiniciar(self):
		while True:
			jogadores_ativos = []
			for jogador in self.jogadores:
				jogador.enviar("⏳ Deseja jogar novamente? (s/n)")
				resposta = jogador.receber()
				if resposta == 's':
					jogador.pontos = 0
					jogadores_ativos.append(jogador)
				else:
					jogador.enviar("👋 Até logo!")
					jogador.fechar()

			self.jogadores = jogadores_ativos

			if len(self.jogadores) < 2:
				self.aguardar_jogadores()
			self.iniciar_partida()

	def aguardar_jogadores(self):
		print("🕹️ Aguardando jogadores...\n")
		while len(self.jogadores) < 2:
			conexao, endereco = self.socket.accept()
			conexao.sendall("⏳ Informe seu nome: ".encode())
			nome = conexao.recv(1024).decode().strip()
			jogador = ConexaoJogador(conexao, endereco, len(self.jogadores), nome)
			jogador.enviar(f"\n👋 Olá {jogador.nome}!\nAguardando os outros jogadores...\n")
			self.jogadores.append(jogador)
			print(f"✅ {jogador.nome} conectado de {endereco}")

	def iniciar_partida(self):
		for jogador in self.jogadores:
			jogador.enviar("🎮 Todos conectados! O jogo vai começar.\n")
		partida = Partida(self.jogadores)
		partida.iniciar()

servidor = ServidorJogo()
servidor.iniciar()
