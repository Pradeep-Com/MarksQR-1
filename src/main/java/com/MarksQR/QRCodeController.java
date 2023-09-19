package com.MarksQR;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLEncoder;

@RestController
public class QRCodeController {
    
    private static final String QR_CODE_IMAGE_PATH = "./src/main/resources/QRCode.png";
    
    @GetMapping(value = "/genrateAndDownloadQRCode/{codeText}/{width}/{height}")
    public ResponseEntity<String> generateAndDownloadQRCode(@PathVariable("codeText") String codeText,
                                                             @PathVariable("width") int width,
                                                             @PathVariable("height") int height) {
        try {
            String encodedCodeText = URLEncoder.encode(codeText, "UTF-8");
            QRCodeService qrCodeService = new QRCodeServiceImpl();
            Student student = qrCodeService.getStudentDetails(encodedCodeText);
            String encryptedData = qrCodeService.encryptData(student);
            QRCodeGenerator.generateQRCodeImage(encryptedData, width, height, QR_CODE_IMAGE_PATH);
            return ResponseEntity.ok("QR code generated and downloaded successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    
    @GetMapping(value = "/genrateQRCode/{codeText}/{width}/{height}")
    public ResponseEntity<byte[]> generateQRCode(@PathVariable("codeText") String codeText,
                                                  @PathVariable("width") int width,
                                                  @PathVariable("height") int height) {
        try {
            String encodedCodeText = URLEncoder.encode(codeText, "UTF-8");
            QRCodeService qrCodeService = new QRCodeServiceImpl();
            Student student = qrCodeService.getStudentDetails(encodedCodeText);
            String encryptedData = qrCodeService.encryptData(student);
            byte[] qrCodeData = QRCodeGenerator.getQRCodeImage(encryptedData, width, height);
            return ResponseEntity.ok().header("Content-Type", "image/png").body(qrCodeData);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
