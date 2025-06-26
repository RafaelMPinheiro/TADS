#Servidor TCP

import socket
HOST = '10.0.80.93'
PORT = 9999
tcp = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
orig = (HOST, PORT)
tcp.bind(orig)
tcp.listen(1)
print ('Servidor Rodando')
while True:
	con1, cliente1 = tcp.accept()
	con2, cliente2 = tcp.accept()
	print ('Clientes Conectados por J1', cliente1, ' J2 ', cliente2)
	while True:
		msg1 = con1.recv(1024)
		if not msg1: break
		print (' ',cliente1,' - ',msg1.decode())
		msg2 = con2.recv(1024)
		if not msg2: break
		print (' ',cliente2,' - ',msg2.decode())
		con2.send(msg1)
		con1.send(msg2)
	print ('Finalizando conexao do clientes')
	con1.close()
	con2.close()
	SystemExit()
