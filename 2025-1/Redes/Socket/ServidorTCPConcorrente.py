#Servidor TCP

import socket
import os
import sys
HOST = ''              # Endereco IP do Servidor
PORT = 5000            # Porta que o Servidor esta
tcp = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
orig = (HOST,PORT)
tcp.bind(orig)
tcp.listen(1)
i=1
while True:
	con, cliente = tcp.accept()
	pid = os.fork()
	i+=1
	if pid == 0:
		tcp.close()
		print ('Conectado por', cliente)
		while True:
			msg = con.recv(1024)
			if not msg: break
			print (i,'  ',cliente, msg)
		print ('Finalizando conexao do cliente', cliente)
		con.close()
		sys.exit(0)
	else:
		con.close()
