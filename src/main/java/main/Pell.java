package main;

import java.util.Scanner;

public class Pell {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)){
            int n = scanner.nextInt();

            if (n >= 0 && n <= 30) {
                switch (n) {
                    case 0:
                        System.out.println(0);
                        break;
                    case 1:
                        System.out.println(1);
                        break;
                    default:
                        int P_n_1 = 1;
                        int P_n_2 = 0;
                        int result = 0;
                        for (int i = 2; i <= n; i++) {
                            result = 2 * P_n_1 + P_n_2;
                            P_n_2 = P_n_1;
                            P_n_1 = result;
                        }
                        System.out.println(result);
                        break;
                }

            } else {
                System.out.println("Введите n в диапазоне 0 <= n <= 30");
            }

        }
    }

}
