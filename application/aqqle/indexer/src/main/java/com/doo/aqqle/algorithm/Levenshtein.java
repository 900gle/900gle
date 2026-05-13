package com.doo.aqqle.algorithm;

public class Levenshtein {
    public int getMinimum(int val1, int val2, int val3) {
        int minNumber = val1;
        if(minNumber > val2) minNumber = val2;
        if(minNumber > val3) minNumber = val3;
        return minNumber;
    }

    public int levenshteinDistance(char[] s, char[] t) {
        int m = s.length;
        int n = t.length;

        int[][] d = new int[m + 1][n + 1];

        for (int i = 1; i < m; i++) {
            d[i][0] = i;
        }

        for (int j = 1; j < n; j++) {
            d[0][j] = j;
        }

        for (int j = 1; j < n; j++) {
            for (int i = 1; i < m; i++) {
                if (s[i] == t[j]) {
                    d[i][j] = d[i - 1][j - 1];
                } else {
                    d[i][j] = getMinimum(d[i - 1][j], d[i][j - 1], d[i - 1][j - 1]) + 1;
                }
            }
        }
        return d[m - 1][n - 1];
    }

    public void runAlgorithm() {

        char[] stringA = "고구마맛 200ml".toCharArray();
        char[] stringB = "고구마맛 300ml".toCharArray();

        int result = levenshteinDistance(stringA, stringB);
        System.out.println("편집거리 : " + result);
    }

    public static void main(String[] args) {
        new Levenshtein().runAlgorithm();
    }
}
