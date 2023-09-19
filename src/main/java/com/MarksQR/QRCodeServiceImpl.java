package com.MarksQR;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;


public class QRCodeServiceImpl implements QRCodeService {
    
    private static final String SECRET_KEY = "MY_SECRET_KEY_123";
    
    public Student getStudentDetails(String codeText) {
        // This is a dummy implementation. Replace it with your own implementation to get the student details.
        return new Student("John Doe", "john.doe@example.com", codeText);
    }
    
    @Override
    public String encryptData(Student student) throws Exception {
        String plainText = student.toString();
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] key = md.digest(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
        SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] cipherText = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(cipherText);
    }

    @Override
    public Student decryptData(String encryptedData) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] key = md.digest(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
        SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] cipherText = Base64.getDecoder().decode(encryptedData);
        byte[] plainText = cipher.doFinal(cipherText);
        String[] data = new String(plainText, StandardCharsets.UTF_8).split(",");
        return new Student(data[0], data[1], data[2]);
    }
}

	

