package main;

import java.util.Scanner;

public class Stars {

    public static void main(String[] args) throws Exception{
        try (Scanner scanner = new Scanner(System.in)){
            int n = scanner.nextInt();
            int m = scanner.nextInt();
            String template = scanner.next();
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    System.out.print(template);;
                }
                System.out.println();
            }
        }

    }

    public static void printFigureWithSpaces() throws Exception{
        try (Scanner scanner = new Scanner(System.in)){
            int n = scanner.nextInt();
            int m = scanner.nextInt();
            String template = scanner.next();

            String strLineToPrint = "";
            String[] arrSymbolsInLine = new String[m];
            for (int i = 0; i < m; i++) {
                arrSymbolsInLine[i] = template;
            }

            for (int i = 0; i < n; i++) {
                System.out.println(String.join(" ", arrSymbolsInLine));
            }
        }
    }

}
