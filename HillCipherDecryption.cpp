#include <iostream>
#include <string>
#include <array>
#include <cmath>

using namespace std;

// function to perform Hill Cipher decryption
string hill_decrypt(string ciphertext, array<array<int, 2>, 2> key) {
    int n = key.size();
    int det = key[0][0] * key[1][1] - key[0][1] * key[1][0];
    int det_inv = 0;
    for (int i = 0; i < 26; i++) {
        if ((i * det) % 26 == 1) {
            det_inv = i;
            break;
        }
    }
    array<array<int, 2>, 2> inv = {{
        {det_inv * key[1][1] % 26, (det_inv * -key[0][1] + 26) % 26},
        {(det_inv * -key[1][0] + 26) % 26, det_inv * key[0][0] % 26}
    }};
    string plaintext = "";
    for (int i = 0; i < ciphertext.length(); i += n) {
        array<int, 2> block = {ciphertext[i]-'A', ciphertext[i+1]-'A'};
        array<array<int, 2>, 1> block_matrix = {{block}};
        array<array<int, 2>, 2> decrypted_matrix = inv;
        array<array<int, 2>, 2> decrypted_block = {{
            {0, 0},
            {0, 0}
        }};
        for (int j = 0; j < 2; j++) {
            for (int k = 0; k < 1; k++) {
                decrypted_block[j][k] = 0;
                for (int l = 0; l < 2; l++) {
                    decrypted_block[j][k] += decrypted_matrix[j][l] * block_matrix[l][k];
                }
                decrypted_block[j][k] %= 26;
            }
        }
        plaintext += decrypted_block[0][0] + 'A';
        plaintext += decrypted_block[1][0] + 'A';
    }
    return plaintext;
}

int main() {
    string ciphertext;
    array<array<int, 2>, 2> key = {{0}};
    cout << "Enter ciphertext: ";
    cin >> ciphertext;
    cout << "Enter key matrix as comma-separated numbers (e.g. 9,4,5,7): ";
    string key_str;
    cin >> key_str;
    sscanf(key_str.c_str(), "%d,%d,%d,%d", &key[0][0], &key[0][1], &key[1][0], &key[1][1]);
    string plaintext = hill_decrypt(ciphertext, key);
    cout << "Plaintext: " << plaintext << endl;
    return 0;
}
