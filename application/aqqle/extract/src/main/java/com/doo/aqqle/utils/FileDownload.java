package com.doo.aqqle.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileDownload {

    public static void downloadFile(String fileURL, String saveDir) throws IOException {
        URL url = new URL(fileURL);
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        int responseCode = httpConn.getResponseCode();

        // HTTP 응답 코드 확인
        if (responseCode == HttpURLConnection.HTTP_OK) {
            String fileName = "";
            String disposition = httpConn.getHeaderField("Content-Disposition");

            // 파일 이름 추출
            if (disposition != null && disposition.contains("filename=")) {
                int index = disposition.indexOf("filename=");
                if (index > 0) {
                    fileName = disposition.substring(index + 9, disposition.length() );
                }
            } else {
                // URL에서 파일 이름 추출
                fileName = fileURL.substring(fileURL.lastIndexOf("/") + 1);
            }

            // 파일 저장 경로 생성
            Path savePath = Paths.get(saveDir, fileName);

            // InputStream 열기
            try (InputStream inputStream = httpConn.getInputStream()) {
                // 파일 저장
                Files.copy(inputStream, savePath, StandardCopyOption.REPLACE_EXISTING);
            }

            System.out.println("File downloaded to " + savePath.toString());
        } else {
            System.out.println("No file to download. Server replied HTTP code: " + responseCode);
        }
        httpConn.disconnect();
    }

}
