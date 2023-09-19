package com.MarksQR;

public interface QRCodeService {
    Student getStudentDetails(String codeText);
    String encryptData(Student student) throws Exception;
	Student decryptData(String encryptedData) throws Exception;
}
