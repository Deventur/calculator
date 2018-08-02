package ru.alfabank.calculator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Application {
    public static void main(String[] args) {

        String expressin;
        Calculator calculator = new Calculator();

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Введте выражение для расчета. Поддерживаются цифры, операции +,-,*,/,^,% и приоритеты в виде скобок ( и ):");
            expressin = bufferedReader.readLine();
            System.out.println("Результат: " + calculator.calculate(expressin));

        } catch (IOException e) {

            System.out.println(e);

        }
    }
}
