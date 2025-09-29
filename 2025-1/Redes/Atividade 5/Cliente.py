from Variaveis import HOST, PORT
import socket

cliente = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
cliente.connect((HOST, PORT))

while True:
    msg = cliente.recv(1024).decode()
    if not msg:
        break

    print(msg)

    if "⏳" in msg:
        resposta = input(">> ")
        cliente.send(resposta.encode())

cliente.close()