package ru.alfabank.calculator;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Application {
    public static void main(String[] args) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String expressin;

        Calculator calculator = new Calculator();

        try {

            System.out.println("Введте выражение для расчета. Поддерживаются цифры, операции +,-,*,/,^,% и приоритеты в виде скобок ( и ):");
            expressin = bufferedReader.readLine();
            System.out.println("Результат: " + calculator.calculate(expressin));

        } catch (Exception e) {

            System.out.println(e.getMessage());

        }
    }
}
