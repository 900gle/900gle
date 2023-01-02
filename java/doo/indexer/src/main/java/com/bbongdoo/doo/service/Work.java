package com.bbongdoo.doo.service;

public class Work {


    public static String work1() {
        log("작업 1 시작");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log("작업 1 종료");
        return "work1Doo";
    }

    public static void work2(String result) {
        log("작업 1의 결과: " + result);
        log("작업 2 시작");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log("작업 2 종료");
    }

    public static void work3() {
        log("작업 3 시작");
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log("작업 3 종료");
    }

    public static void log(String content) {
        System.out.println(Thread.currentThread().getName() + "> " + content);
    }
}
