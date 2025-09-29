import hashlib
import random as rd
import time
from datetime import datetime

characters = [
    '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
    '#', '$', '%', '&', '*', '+', '-', '.', '=',
    'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
    'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
]

hashs3 = [
    "635041d70280a5333d59b1ea677ffc57",
    "e2756f938e0b14a7330c37724e4066d4",
    "43acfb3c016819505e96a8544f2d9660"
]
hashs4 = [
    "8aa8ea48ed2c5bf1da94c5f3a5cc3983",
    "c53e750d2b47beb63f41bc171ed736a5"
]
hashs5 = [
    "d2204ad6b80c725a8099348922778874",
    "35c328f4fbead6d97b7dcead4f8bd1c3",
]
hashs6 = [
    "32f824770b4a7c4f386dfd985a48080b",
    "db2e895defc3c71d3e76a16997bb5b41"
]

def criptografar_md5(texto):
    hash_md5 = hashlib.md5()
    hash_md5.update(texto.encode('utf-8'))
    return hash_md5.hexdigest()


def quebraSenha3(hash_senha):
    for i in range(0,len(characters)):
        for j in range(0,len(characters)):
            for l in range(0,len(characters)):
                teste = characters[i]+characters[j]+characters[l]
                hash_teste = criptografar_md5(teste)
                if hash_senha == hash_teste:
                    return teste
    return None

def quebraSenha4(hash_senha):
    for i in range(0,len(characters)):
        for j in range(0,len(characters)):
            for l in range(0,len(characters)):
                for m in range(0,len(characters)):
                    teste = characters[i]+characters[j]+characters[l]+characters[m]
                    hash_teste = criptografar_md5(teste)
                    if hash_senha == hash_teste:
                        return teste
    return None

def quebraSenha5(hash_senha):
    for i in range(0,len(characters)):
        for j in range(0,len(characters)):
            for l in range(0,len(characters)):
                for m in range(0,len(characters)):
                    for n in range(0,len(characters)):
                        teste = characters[i]+characters[j]+characters[l]+characters[m]+characters[n]
                        hash_teste = criptografar_md5(teste)
                        if hash_senha == hash_teste:
                            return teste
    return None

def quebraSenha6(hash_senha):
    for i in range(0,len(characters)):
        for j in range(0,len(characters)):
            for l in range(0,len(characters)):
                for m in range(0,len(characters)):
                    for n in range(0,len(characters)):
                        for o in range(0,len(characters)):
                            for p in range(0,len(characters)):
                                teste = characters[i]+characters[j]+characters[l]+characters[m]+characters[n]+characters[o]+characters[p]
                                hash_teste = criptografar_md5(teste)
                                if hash_senha == hash_teste:
                                    return teste
    return None

print("Quebra para hashs de 3")
for h in hashs3:
    inicio = time.time()
    senha = quebraSenha3(h)
    fim = time.time()
    resultado = f"{h} - {senha} - {fim - inicio:.4f}s"
    print(resultado)

print("Quebra para hashs de 4")
for h in hashs4:
    inicio = time.time()
    senha = quebraSenha4(h)
    fim = time.time()
    resultado = f"{h} - {senha} - {fim - inicio:.4f}s"
    print(resultado)

print("Quebra para hashs de 5")
for h in hashs5:
    inicio = time.time()
    senha = quebraSenha5(h)
    fim = time.time()
    resultado = f"{h} - {senha} - {fim - inicio:.4f}s"
    print(resultado)

print("Quebra para hashs de 6")
for h in hashs6:
    inicio = time.time()
    senha = quebraSenha6(h)
    fim = time.time()
    resultado = f"{h} - {senha} - {fim - inicio:.4f}s"
    print(resultado)
