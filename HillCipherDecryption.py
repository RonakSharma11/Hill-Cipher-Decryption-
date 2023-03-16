import numpy as np

# function to find the inverse of a matrix
def matrix_inverse(matrix):
    det = int(np.round(np.linalg.det(matrix)))
    inv = np.linalg.inv(matrix)
    adj = np.round(det * inv).astype(int)
    det_inv = np.linalg.inv([[det]])
    return np.mod(det_inv * adj, 26)

# function to perform Hill Cipher decryption
def hill_decrypt(ciphertext, key):
    n = len(key)
    plaintext = ""
    for i in range(0, len(ciphertext), n):
        block = [ord(c) - ord('A') for c in ciphertext[i:i+n]]
        block = np.reshape(block, (n, 1))
        decrypted_block = np.dot(matrix_inverse(key), block)
        decrypted_block = np.mod(decrypted_block, 26)
        decrypted_block = [chr(b + ord('A')) for b in decrypted_block.flatten()]
        plaintext += "".join(decrypted_block)
    return plaintext

# get input from user
ciphertext = input("Enter ciphertext: ").upper()
key_str = input("Enter key matrix as comma-separated numbers: ")
key = np.array(key_str.split(","), dtype=int).reshape(int(len(key_str)**0.5), -1)

# decrypt ciphertext using the key
plaintext = hill_decrypt(ciphertext, key)
print("Plaintext:", plaintext)
