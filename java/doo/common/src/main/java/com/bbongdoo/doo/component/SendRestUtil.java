package com.bbongdoo.doo.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@Slf4j
@Component
public class SendRestUtil {

    public static String sendRest(String url, String json) throws IllegalStateException {
        String inputLine = null;
        StringBuffer outResult = new StringBuffer();

        try {
            URL sendUrl = new URL(url);

            HttpURLConnection conn = (HttpURLConnection) sendUrl.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept-Charset", "UTF-8");
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(200000);

            OutputStream os = conn.getOutputStream();
            os.write(json.getBytes("UTF-8"));
            os.flush();

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

            while ((inputLine = in.readLine()) != null){
                outResult.append(inputLine);
            }
            conn.disconnect();
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return outResult.toString();
    }
}
