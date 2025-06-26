from Variaveis import HOST, PORT
import socket

cliente = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
cliente.connect((HOST, PORT))

mensagem = cliente.recv(1024).decode()
print(mensagem)

nome = input(">> ")
cliente.send(nome.encode())

while True:
    msg = cliente.recv(1024).decode()
    if msg:
        print(msg)
        if "Digite" in msg:
            resposta = input(">> ")
            cliente.send(resposta.encode())
