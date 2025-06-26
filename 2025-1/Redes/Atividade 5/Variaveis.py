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
    ("Brasília é a capital do Brasil.", 'v'),
    ("7 x 8 é igual a 54.", 'f'),
    ("Marte é conhecido como o planeta vermelho.", 'v'),
    ("'Dom Casmurro' foi escrito por Machado de Assis.", 'v'),
    ("O Oceano Atlântico é o maior do planeta.", 'f'),
    ("Há 60 segundos em um minuto.", 'v'),
    ("H2O é o símbolo químico da água.", 'v'),
    ("Cristóvão Colombo descobriu o Brasil.", 'f'),
    ("A fotossíntese é o processo pelo qual as plantas produzem seu alimento.", 'v'),
    ("Um hexágono tem 8 lados.", 'f'),
    ("A mistura de azul e amarelo resulta em verde.", 'v'),
    ("Buenos Aires é a capital da Argentina.", 'v'),
    ("Leonardo da Vinci pintou a Mona Lisa.", 'v'),
    ("A baleia azul é o maior animal terrestre.", 'f'),
    ("O termômetro é usado para medir temperatura.", 'v'),
    ("Um ano bissexto tem 365 dias.", 'f'),
    ("Espanhol é a língua falada no México.", 'v'),
    ("O Egito fica na Ásia.", 'f'),
    ("O menor número primo é o 2.", 'v'),
    ("O gás oxigênio é essencial para a respiração humana.", 'v')
]
