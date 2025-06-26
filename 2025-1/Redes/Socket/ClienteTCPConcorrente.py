#ClienteTCP

import socket
HOST = '10.0.82.158'
PORT = 5000
tcp = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
dest = (HOST, PORT)
tcp.connect(dest)
print ('Para sair use CTRL+X\n')
msg = input('Digite algo:')

while (True):
	tcp.send(msg.encode('UTF-8'))
	msg = input('Digite algo:')
	
tcp.close()
