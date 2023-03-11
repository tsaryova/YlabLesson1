package io.ylab.lessons.lesson2.complex;

public class ComplexNumberTest {
    public static void main(String[] args) {
        ComplexNumber complex1 = new ComplexNumber(4);
        ComplexNumber complex2 = new ComplexNumber(2);
        System.out.println("Первое число: " + complex1);
        System.out.println("Второе число: " + complex2);
        System.out.println();

        System.out.println("Сложение:");
        System.out.println(complex1.sum(complex2));
        System.out.println("Разность:");
        System.out.println(complex1.sub(complex2));
        System.out.println("Умножение:");
        System.out.println(complex1.mul(complex2));
        System.out.println("Деление:");
        System.out.println(complex1.div(complex2));
        System.out.println("Модуль:");
        System.out.println(complex1.mod());
    }
}
