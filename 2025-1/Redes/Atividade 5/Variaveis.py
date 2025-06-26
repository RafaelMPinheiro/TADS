import socket

def get_ip():
    s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    try:
        s.connect(("8.8.8.8", 80))
        ip = s.getsockname()[0]
    except Exception:
        print("Não foi possível obter o IP")
    finally:
        s.close()
    return ip

# Variaveis para conexao do servidor e cliente
HOST = get_ip()
PORT = 10000

PERGUNTAS = [
	("Qual é a capital do Brasil?", "brasilia"),
	("Quanto é 7 x 8?", "56"),
	("Qual planeta é conhecido como planeta vermelho?", "marte"),
	("Quem escreveu 'Dom Casmurro'?", "machado de assis"),
	("Qual é o maior oceano do planeta?", "pacifico"),
	("Quantos segundos há em um minuto?", "60"),
	("Qual é o símbolo químico da água?", "h2o"),
	("Quem descobriu o Brasil?", "pedro alvares cabral"),
	("Qual o nome do processo pelo qual as plantas produzem seu alimento?", "fotossintese"),
	("Quantos lados tem um hexágono?", "6"),
	("Qual é a cor resultante da mistura de azul e amarelo?", "verde"),
	("Qual é a capital da Argentina?", "buenos aires"),
	("Quem pintou a Mona Lisa?", "leonardo da vinci"),
	("Qual é o maior animal terrestre?", "elefante africano"),
	("Que instrumento mede a temperatura?", "termometro"),
	("Quantos dias tem um ano bissexto?", "366"),
	("Qual é a língua falada no México?", "espanhol"),
	("Em que continente fica o Egito?", "africa"),
	("Qual é o menor número primo?", "2"),
	("Qual é o gás essencial para a respiração humana?", "oxigenio")
]
