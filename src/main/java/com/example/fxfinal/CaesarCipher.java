package com.example.fxfinal;

public class CaesarCipher {
    private int shift;

    public CaesarCipher() {
        shift = 7;
    }

    public String decrypt(String encryptedMessage) {
        StringBuilder decrypted = new StringBuilder();
        StringBuilder formatted = new StringBuilder();

        for (int i = 0; i < encryptedMessage.length(); i++) {
            char ch = encryptedMessage.charAt(i);
            if (Character.isLetter(ch)) {
                char shifted = (char) (ch - shift);
                if (Character.isUpperCase(ch)) {
                    shifted = (char) ((shifted - 'A' + 26) % 26 + 'A');
                } else {
                    shifted = (char) ((shifted - 'a' + 26) % 26 + 'a');
                }
                decrypted.append(shifted);
            } else {
                decrypted.append(ch);
            }
        }

        for (int i = 0; i < decrypted.length(); i++) {
            char c = decrypted.charAt(i);
            if (c == '^') {
                formatted.append(Character.toUpperCase(decrypted.charAt(i + 1)));
                i++;
            } else if (c == '$') {
                formatted.append(" ");
            } else if (c == '(') {
                int count = 0;
                char[] wordChar = new char[decrypted.length()];
                StringBuilder reversed = new StringBuilder();
                do {
                    char charAtI = decrypted.charAt(i + 1);
                    if (charAtI != ')') {
                        wordChar[count] = charAtI;
                        count++;
                    } else {
                        i++;
                        break;
                    }
                    i++;
                }while (true);

                for (int j = count - 1; j >= 0; j--) {
                    reversed.append(wordChar[j]);
                }

                formatted.append(reversed);
            } else {
                formatted.append(c);
            }
        }


        return formatted.toString();
    }
}
