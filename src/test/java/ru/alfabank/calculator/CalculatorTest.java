package ru.alfabank.calculator;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class CalculatorTest {

    private final Calculator calculator = new Calculator();

    @Test
    public void shuldConverExpressionToExpressionRPN() throws Exception {
        String expression = "(3  ф *(a5+ 7^ 3 ))/   4+ 1";
        String expressionRPN = calculator.converToRPN(expression);
        assertEquals("Преобразование выполнено неверно!", "3 5 7 3 ^ + * 4 / 1 +", expressionRPN);
    }

    @Test
    public void shuldCalculetSimpleExpression() throws Exception {
        String expression = "2+2*2";
        double result = calculator.calculate(expression);
        assertEquals("Твой калькулятор - кампост!", 6.0, result, 0.0);
    }
}
