import java.util.*;

public class HillCipher {
    // function to perform Hill Cipher decryption
    public static String hill_decrypt(String ciphertext, int[][] key) {
        int n = key.length;
        int det = key[0][0] * key[1][1] - key[0][1] * key[1][0];
        int det_inv = 0;
        for (int i = 0; i < 26; i++) {
            if ((i * det) % 26 == 1) {
                det_inv = i;
                break;
            }
        }
        int[][] inv = new int[][] {
            { det_inv * key[1][1] % 26, (det_inv * -key[0][1] + 26) % 26 },
            { (det_inv * -key[1][0] + 26) % 26, det_inv * key[0][0] % 26 }
        };
        String plaintext = "";
        for (int i = 0; i < ciphertext.length(); i += n) {
            int[] block = { ciphertext.charAt(i) - 'A', ciphertext.charAt(i+1) - 'A' };
            int[][] block_matrix = new int[][] { block };
            int[][] decrypted_matrix = inv;
            int[][] decrypted_block = new int[][] { { 0 }, { 0 } };
            for (int j = 0; j < 2; j++) {
                for (int k = 0; k < 1; k++) {
                    decrypted_block[j][k] = 0;
                    for (int l = 0; l < 2; l++) {
                        decrypted_block[j][k] += decrypted_matrix[j][l] * block_matrix[l][k];
                    }
                    decrypted_block[j][k] %= 26;
                }
            }
            plaintext += (char) (decrypted_block[0][0] + 'A');
            plaintext += (char) (decrypted_block[1][0] + 'A');
        }
        return plaintext;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter ciphertext: ");
        String ciphertext = scanner.nextLine().toUpperCase();
        int[][] key = new int[2][2];
        System.out.print("Enter key matrix as comma-separated numbers (e.g. 9,4,5,7): ");
        String keyStr = scanner.nextLine();
        String[] keySplit = keyStr.split(",");
        for (int i = 0; i < 4; i++) {
            key[i / 2][i % 2] = Integer.parseInt(keySplit[i]);
        }
        String plaintext = hill_decrypt(ciphertext, key);
        System.out.println("Plaintext: " + plaintext);
    }
}
