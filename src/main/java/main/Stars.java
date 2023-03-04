package main;

import java.util.Scanner;

public class Stars {

    public static void main(String[] args) throws Exception{
        try (Scanner scanner = new Scanner(System.in)){
            int n = scanner.nextInt();
            int m = scanner.nextInt();
            String template = scanner.next();
            for (int i = 0; i < n; i++) {
                String strLine = "";
                for (int j = 0; j < m; j++) {
                    strLine = strLine.concat(template);
                }
                System.out.println(strLine);
            }
        }
    }

}
