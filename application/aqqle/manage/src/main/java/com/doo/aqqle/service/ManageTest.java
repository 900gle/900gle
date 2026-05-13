//package com.doo.aqqle.service;
//
//import java.io.IOException;
//
//public class ManageTest {
//
//
//    public static void main(String[] args) {
//
//
//        try {
//
//          Process process =  executeGrepProcessCommand(9200);
//
//
//
//        } catch (IOException e) {
//            e.getStackTrace();
//        }
//
//
//
//
//    }
//
//
//    private static Process executeGrepProcessCommand(int port) throws IOException {
//        String command = String.format("netstat -nat | grep LISTEN|grep %d", port);
//        String[] shell = {"/bin/sh", "-c", command};
//        return Runtime.getRuntime().exec(shell);
//    }
//
//}
