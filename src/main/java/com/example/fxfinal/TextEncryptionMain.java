package com.example.fxfinal;
import java.util.Scanner;

public class TextEncryptionMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Choose an encryption method:");
        System.out.println("1. AES Encryption");
        System.out.println("2. Caesar Cipher");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        if (choice == 1) {
            System.out.print("Enter the secret key: ");
            String secretKey = scanner.nextLine();

            AESEncryption aesEncryption = new AESEncryption(secretKey);

            System.out.print("Enter the message to encrypt: ");
            String message = scanner.nextLine();

            String encryptedMessage = aesEncryption.encrypt(message);
            System.out.println("Encrypted message: " + encryptedMessage);

            String decryptedMessage = aesEncryption.decrypt(encryptedMessage);
            System.out.println("Decrypted message: " + decryptedMessage);
        } else if (choice == 2) {
            System.out.println("Choose an operation:");
            System.out.println("1. Encrypt");
            System.out.println("2. Decrypt");
            int operation = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Enter the shift value: ");
            int shift = scanner.nextInt();
            scanner.nextLine();

            CaesarCipher caesarCipher = new CaesarCipher(shift);

            System.out.print("Enter the message: ");
            String message = scanner.nextLine();

            if (operation == 1) {
                String encryptedMessage = caesarCipher.encrypt(message);
                System.out.println("Encrypted message: " + encryptedMessage);
            } else if (operation == 2) {
                String decryptedMessage = caesarCipher.decrypt(message);
                System.out.println("Decrypted message: " + decryptedMessage);
            } else {
                System.out.println("Invalid operation. Exiting...");
            }
        } else {
            System.out.println("Invalid choice. Exiting...");
        }
    }
}