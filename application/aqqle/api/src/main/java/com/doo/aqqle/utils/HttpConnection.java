package com.doo.aqqle.utils;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

public class HttpConnection {

    public static final String USER_AGENT = "Mozilla/5.0";

    /**
     * GET방식 조회
     * Connection TimeOut 15초
     * Read TimeOut 10 초
     * @param url
     * @param charSet
     * @return
     * @throws Exception
     */
    public static String sendGet(String url, String charSet, int conTimeOut, int readTimeOut) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setConnectTimeout(conTimeOut);          //Connection 15초
        con.setReadTimeout(readTimeOut);            //Read 10초
        con.setUseCaches(false);                    //동적생성결과조회

        Charset charset = Charset.forName(charSet);
        StringBuffer response = new StringBuffer();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), charset))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        } catch (IOException e) {
            System.err.println(" API 호출 중 IOException 발생: " + e.getMessage());
            throw new Exception("API 호출 실패 (IOException)", e); //  예외를 다시 던짐
        }

        return response.toString();
    }

    /**
     * Connection TimeOut 15초
     * Read TimeOut 10 초
     * @param url
     * @param urlParameters
     * @param charSet
     * @return
     * @throws Exception
     */
    public static String sendPost(String url, String urlParameters, String charSet, int conTimeOut, int readTimeOut) throws Exception{
        return sendPost(url, urlParameters, charSet, conTimeOut, readTimeOut, null);
    }
    public static String sendPost(String url, String urlParameters, String charSet,int conTimeOut, int readTimeOut, String mediaType) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //Header Setting
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        con.setRequestProperty("charset", charSet);
        con.setConnectTimeout(conTimeOut);       //Connection 12초
        con.setReadTimeout(readTimeOut);          //Read 10초
        if (mediaType != null) {
            con.setRequestProperty("Content-Type", mediaType);
        }
        con.setUseCaches(false);            //동적생성결과조회

        //Send post request
        con.setDoOutput(true);

        try (DataOutputStream wr = new DataOutputStream(con.getOutputStream());) {
            wr.writeBytes(urlParameters);
            wr.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Charset charset = Charset.forName(charSet);
        StringBuffer response = new StringBuffer();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), charset))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response.toString();
    }

}