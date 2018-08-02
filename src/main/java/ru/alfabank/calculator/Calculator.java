package ru.alfabank.calculator;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

class Calculator {

    private final StringBuilder stackOpertators = new StringBuilder();
    private final StringBuilder expressionRPN = new StringBuilder();

    /**
     * Преобразовать строку в обратную польскую нотацию
     *
     * @param expression Входная строка
     * @return Выходная строка в обратной польской нотации
     */
    String converToRPN(String expression) throws RuntimeException {

        char curChar;   //текущий символ из выражения

        for (int i = 0; i < expression.length(); i++) {

            curChar = expression.charAt(i);

            if (isOperator(curChar)) {

                processingOerators(curChar);

            } else if ('(' == curChar || ')' == curChar) {

                processingBrackets(curChar);

            } else if (Character.isDigit(curChar)) {

                processingDigits(curChar);

            }
        }

        // Если в стеке остались операторы, добавляем их в входную строку
        while (stackOpertators.length() > 0) {
            expressionRPN.append(" ").append(stackOpertators.substring(stackOpertators.length() - 1));
            stackOpertators.setLength(stackOpertators.length() - 1);
        }

        return expressionRPN.toString();
    }

    private void processingDigits(char digit) {
        if ((expressionRPN.length() == 0) ||
                (Character.isDigit(expressionRPN.substring(expressionRPN.length() - 1).charAt(0))) ||
                (Character.isWhitespace(expressionRPN.substring(expressionRPN.length() - 1).charAt(0)))) {

            expressionRPN.append(digit);

        } else {
            expressionRPN.append(" ").append(digit);
        }
    }

    private void processingBrackets(char bracket) throws RuntimeException {

        char cTmp; //временный буфер для операторов из стека

        if ('(' == bracket) {

            stackOpertators.append(bracket);

        } else if (')' == bracket) {

            cTmp = stackOpertators.substring(stackOpertators.length() - 1).charAt(0);

            while ('(' != cTmp) {

                if (stackOpertators.length() < 1) {
                    throw new RuntimeException("Ошибка разбора скобок. Проверьте правильность выражения.");
                }

                //HACK
                if (expressionRPN.substring(expressionRPN.length() - 1).charAt(0) != ' ') {

                    expressionRPN.append(" ");

                }

                expressionRPN.append(cTmp).append(" ");
                stackOpertators.setLength(stackOpertators.length() - 1);
                cTmp = stackOpertators.substring(stackOpertators.length() - 1).charAt(0);
            }

            stackOpertators.setLength(stackOpertators.length() - 1);
        }

    }

    private void processingOerators(char operator) {
        char cTmp; //временный буфер для операторов из стека
        if (isOperator(operator)) {

            while (stackOpertators.length() > 0) {

                cTmp = stackOpertators.substring(stackOpertators.length() - 1).charAt(0);

                if (isOperator(cTmp) && (getPriorityForOperator(operator) <= getPriorityForOperator(cTmp))) {

                    expressionRPN.append(" ").append(cTmp).append(" ");
                    stackOpertators.setLength(stackOpertators.length() - 1);

                } else {

                    expressionRPN.append(" ");
                    break;

                }
            }
            if (!Character.isWhitespace(expressionRPN.substring(expressionRPN.length() - 1).charAt(0))) {
                expressionRPN.append(" ");
            }

            stackOpertators.append(operator);
        }
    }

    /**
     * Функция проверяет, является ли текущий символ оператором
     *
     * @param c - проверяемый символ.
     * @return boolean ответ
     */
    private boolean isOperator(char c) {

        switch (c) {
            case '-':
            case '+':
            case '*':
            case '/':
            case '^':
                return true;
        }
        return false;
    }

    /**
     * Возвращает приоритет операции
     *
     * @param operator char
     * @return byte
     */
    private byte getPriorityForOperator(char operator) {
        switch (operator) {
            case '^':
                return 3;
            case '*':
            case '/':
            case '%':
                return 2;
        }
        return 1; // Тут остается + и -
    }

    /**
     * Считает выражение
     *
     * @param expressin выражение для вычисления
     * @return double result
     */
    double calculate(String expressin) throws RuntimeException {

        //Запишем входное выражение в обрутной польской нотации
        String expressionRPN = converToRPN(expressin);

        double op1 = 0, op2 = 0;
        String tmpElem;
        Deque<Double> stack = new ArrayDeque<>();
        StringTokenizer stringTokenizer = new StringTokenizer(expressionRPN);
        while (stringTokenizer.hasMoreTokens()) {
            try {
                tmpElem = stringTokenizer.nextToken().trim();
                if (1 == tmpElem.length() && isOperator(tmpElem.charAt(0))) {
                    if (stack.size() < 2) {
                        throw new IllegalArgumentException("Неверное количество данных в стеке для операции " + tmpElem);
                    }
                    op2 = stack.pop();
                    op1 = stack.pop();
                    switch (tmpElem.charAt(0)) {
                        case '+':
                            op1 += op2;
                            break;
                        case '-':
                            op1 -= op2;
                            break;
                        case '/':
                            op1 /= op2;
                            break;
                        case '*':
                            op1 *= op2;
                            break;
                        case '%':
                            op1 %= op2;
                            break;
                        case '^':
                            op1 = Math.pow(op1, op2);
                            break;
                        default:
                            throw new RuntimeException("Недопустимая операция " + tmpElem);
                    }
                } else {
                    op1 = Double.parseDouble(tmpElem);
                }
                stack.push(op1);
            } catch (Exception e) {
                throw new IllegalArgumentException("Недопустимый символ в выражении");
            }
        }

        if (stack.size() > 1) {
            throw new IllegalArgumentException("Количество операторов не соответствует количеству операндов");
        }

        return stack.pop();
    }
}

