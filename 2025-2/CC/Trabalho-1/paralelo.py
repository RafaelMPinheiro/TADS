import hashlib
import time
from itertools import product
from multiprocessing import Pool, cpu_count
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


def criptografar_md5(texto: str) -> str:
    h = hashlib.md5()
    h.update(texto.encode('utf-8'))
    return h.hexdigest()


def _busca_com_prefixo(args):
    prefix, total_len, target_hash = args
    remaining = total_len - len(prefix)
    if remaining < 0:
        return None

    if remaining == 0:
        # Já no tamanho alvo
        if criptografar_md5(prefix) == target_hash:
            return prefix
        return None

    for combo in product(characters, repeat=remaining):
        candidate = prefix + ''.join(combo)
        if criptografar_md5(candidate) == target_hash:
            return candidate
    return None


def quebra_senha_paralelo(target_hash: str, length: int) -> str | None:
    num_processes = cpu_count()
    
    with Pool(processes=num_processes) as pool:
        args_iter = ((ch, length, target_hash) for ch in characters)
        for result in pool.imap_unordered(_busca_com_prefixo, args_iter):
            if result is not None:
                pool.terminate()
                pool.join()
                return result
    return None


def main():
    print(f"Sistema detectado: {cpu_count()} CPUs disponíveis")
    print(f"Caracteres disponíveis: {len(characters)}")
    print("=" * 60)
    
    for h in hashs3:
        inicio = time.time()
        senha = quebra_senha_paralelo(h, 3)  # Usa todas as CPUs disponíveis
        fim = time.time()
        resultado = f"{h} - {senha} - {fim - inicio:.4f}s"
        print(resultado)
        
    print("Quebra para hashs de 4")
    for h in hashs4:
        inicio = time.time()
        senha = quebra_senha_paralelo(h, 4)
        fim = time.time()
        resultado = f"{h} - {senha} - {fim - inicio:.4f}s"
        print(resultado)
        
    print("Quebra para hashs de 5")
    for h in hashs5:
        inicio = time.time()
        senha = quebra_senha_paralelo(h, 5)
        fim = time.time()
        resultado = f"{h} - {senha} - {fim - inicio:.4f}s"
        print(resultado)
        
    print("Quebra para hashs de 6")
    for h in hashs6:
        inicio = time.time()
        senha = quebra_senha_paralelo(h, 6)  # Usa todas as CPUs disponíveis
        fim = time.time()
        resultado = f"{h} - {senha} - {fim - inicio:.4f}s"
        print(resultado)
        
if __name__ == "__main__":
    main()
