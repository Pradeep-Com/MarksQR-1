package com.MarksQR;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class QRCodeGenerator {

private static final String ENCRYPTION_ALGORITHM = "SHA-256";

public static void generateQRCodeImage(String encryptedText, int width, int height, String filePath)
        throws WriterException, IOException {
    QRCodeWriter qrCodeWriter = new QRCodeWriter();
    BitMatrix bitMatrix = qrCodeWriter.encode(encryptedText, BarcodeFormat.QR_CODE, width, height);

    Path path = FileSystems.getDefault().getPath(filePath);
    MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);

}

public static byte[] getQRCodeImage(String encryptedText, int width, int height) throws WriterException, IOException {
    QRCodeWriter qrCodeWriter = new QRCodeWriter();
    BitMatrix bitMatrix = qrCodeWriter.encode(encryptedText, BarcodeFormat.QR_CODE, width, height);

    ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
    MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
    byte[] pngData = pngOutputStream.toByteArray();
    return pngData;
}

public static String encryptData(String rollNo, int paper1Marks, int paper2Marks) throws NoSuchAlgorithmException {
    String data = rollNo + "|" + paper1Marks + "|" + paper2Marks;
    MessageDigest digest = MessageDigest.getInstance(ENCRYPTION_ALGORITHM);
    byte[] encodedHash = digest.digest(data.getBytes(StandardCharsets.UTF_8));
    return bytesToHex(encodedHash);
}

private static String bytesToHex(byte[] hash) {
    StringBuilder hexString = new StringBuilder(2 * hash.length);
    for (byte b : hash) {
        String hex = Integer.toHexString(0xff & b);
        if (hex.length() == 1) {
            hexString.append('0');
        }
        hexString.append(hex);
    }
    return hexString.toString();
}
}
