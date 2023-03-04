package main;

import java.util.Random;
import java.util.Scanner;

public class Guess {

    public static void main(String[] args) {
        int number = new Random().nextInt(100);
        int maxAttempts = 10;
        int countAttempts = 1;
        System.out.println("Я загадал число. У тебя " + maxAttempts + " попыток угадать.");

        try (Scanner scanner = new Scanner(System.in)){
            while (scanner.hasNext()) {
                int inputNum = scanner.nextInt();
                if (countAttempts < maxAttempts ) {
                    if (inputNum > number) {
                        System.out.printf("Мое число меньше! Осталось %d попыток %n", maxAttempts - countAttempts);
                    } else if (inputNum < number) {
                        System.out.printf("Мое число больше! Осталось %d попыток %n", maxAttempts - countAttempts);
                    } else {
                        System.out.printf("Ты угадал с %d попытки!", countAttempts);
                        break;
                    }
                    countAttempts++;
                } else {
                    System.out.println("Ты не угадал!");
                    break;
                }
            }
        }

    }

}
