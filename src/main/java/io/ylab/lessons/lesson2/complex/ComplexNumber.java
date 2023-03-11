package io.ylab.lessons.lesson2.complex;

public class ComplexNumber {
    private double real;
    private double imaginary;

    public double getReal() {
        return real;
    }

    public double getImaginary() {
        return imaginary;
    }

    public ComplexNumber(double real) {
        this.real = real;
        this.imaginary = 0;
    }

    public ComplexNumber(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    public ComplexNumber sum(ComplexNumber complex) {
        double newReal = this.real + complex.getReal();
        double newImaginary = this.imaginary + complex.getImaginary();

        return new ComplexNumber(newReal, newImaginary);
    }

    public ComplexNumber sub(ComplexNumber complex) {
        double newReal = this.real - complex.getReal();
        double newImaginary = this.imaginary - complex.getImaginary();

        return new ComplexNumber(newReal, newImaginary);
    }

    public ComplexNumber mul(ComplexNumber complex) {
        double otherReal = complex.getReal();
        double otherImaginary = complex.getImaginary();
        double newReal = this.real * otherReal - this.imaginary * otherImaginary;
        double newImaginary = this.imaginary * otherReal + this.real * otherImaginary;

        return new ComplexNumber(newReal, newImaginary);
    }

    public ComplexNumber div(ComplexNumber complex) {
        double otherReal = complex.getReal();
        double otherImaginary = complex.getImaginary();
        double newReal = (this.real * otherReal + this.imaginary * otherImaginary) /
                (otherReal * otherReal + otherImaginary * otherImaginary);
        double newImaginary = (this.imaginary * otherReal - this.real * otherImaginary) /
                (otherReal * otherReal + otherImaginary * otherImaginary);

        return new ComplexNumber(newReal, newImaginary);
    }

    public double mod() {
        return Math.sqrt(
                this.real * this.real +
                        this.imaginary * this.imaginary);
    }

    @Override
    public String toString() {
        String result = String.format("%.2f", this.real);
        if (this.imaginary >= 0) {
            result += "+";
        }
        result += String.format("%.2f", this.imaginary) + "i";
        return result;
    }
}
