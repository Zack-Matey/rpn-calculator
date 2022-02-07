package app.ops.impl;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Map;
import java.util.function.LongBinaryOperator;
import app.ops.RPNOp;

import app.RPNCalc;
import app.exceptions.impl.InvalidValueException;

public class RPNBinaryOp implements RPNOp {

    private static final LongBinaryOperator ADD = (long a, long b) -> {
        return a + b;
    };
    private static final LongBinaryOperator SUB = (long a, long b) -> {
        return a - b;
    };
    private static final LongBinaryOperator MULT = (long a, long b) -> {
        return a * b;
    };
    private static final LongBinaryOperator DIV = (long a, long b) -> {
        if (b==0)
            throw new UncheckedIOException("Cannot divide by 0", new IOException());
        return a / b;
    };
    private static final LongBinaryOperator MOD = (long a, long b) -> {
        if (b==0)
            throw new UncheckedIOException("Cannot divide by 0", new IOException());
        return a % b;
    };
    private static final LongBinaryOperator BITWISE_AND = (long a, long b) -> {
        return a & b;
    };
    private static final LongBinaryOperator BITWISE_OR = (long a, long b) -> {
        return a | b;
    };
    private static final LongBinaryOperator BITWISE_XOR = (long a, long b) -> {
        return a ^ b;
    };
    private static final LongBinaryOperator LEFTSHIFT = (long a, long b) -> {
        return a << b;
    };
    private static final LongBinaryOperator RIGHTSHIFT = (long a, long b) -> {
        return a >> b;
    };
    private static final LongBinaryOperator AND = (long a, long b) -> {
        return (a == 0 ? false : true) && (b == 0 ? false : true) ? 1 : 0;
    };
    private static final LongBinaryOperator OR = (long a, long b) -> {
        return (a == 0 ? false : true) || (b == 0 ? false : true) ? 1 : 0;
    };
    private static final LongBinaryOperator EQUALS = (long a, long b) -> {
        return (a == b) ? 1 : 0;
    };
    private static final LongBinaryOperator NOT_EQUALS = (long a, long b) -> {
        return (a != b) ? 1 : 0;
    };
    private static final LongBinaryOperator GREATER = (long a, long b) -> {
        return (a > b) ? 1 : 0;
    };
    private static final LongBinaryOperator LESS = (long a, long b) -> {
        return (a < b) ? 1 : 0;
    };
    private static final LongBinaryOperator GREATER_EQUALS = (long a, long b) -> {
        return (a >= b) ? 1 : 0;
    };
    private static final LongBinaryOperator LESS_EQUALS = (long a, long b) -> {
        return (a <= b) ? 1 : 0;
    };

    private LongBinaryOperator operator;

    private RPNBinaryOp(LongBinaryOperator op) {
        this.operator = op;
    }
    
    public static void associateOperators(RPNCalc calc) {
        Map<String, RPNOp> operators = calc.getOperatorsMap();
        operators.put("+", new RPNBinaryOp(RPNBinaryOp.ADD));
        operators.put("-", new RPNBinaryOp(RPNBinaryOp.SUB) );
        operators.put("*", new RPNBinaryOp(RPNBinaryOp.MULT));
        operators.put("/", new RPNBinaryOp(RPNBinaryOp.DIV));
        operators.put("%", new RPNBinaryOp(RPNBinaryOp.MOD));
        operators.put("&", new RPNBinaryOp(RPNBinaryOp.BITWISE_AND));  
        operators.put("|", new RPNBinaryOp(RPNBinaryOp.BITWISE_OR));
        operators.put("^", new RPNBinaryOp(RPNBinaryOp.BITWISE_XOR));
        operators.put("<<", new RPNBinaryOp(RPNBinaryOp.LEFTSHIFT));
        operators.put(">>", new RPNBinaryOp(RPNBinaryOp.RIGHTSHIFT));
        operators.put("&&", new RPNBinaryOp(RPNBinaryOp.AND));
        operators.put("||", new RPNBinaryOp(RPNBinaryOp.OR));
        operators.put("==", new RPNBinaryOp(RPNBinaryOp.EQUALS));
        operators.put("!=", new RPNBinaryOp(RPNBinaryOp.NOT_EQUALS));
        operators.put(">", new RPNBinaryOp(RPNBinaryOp.GREATER));
        operators.put("<", new RPNBinaryOp(RPNBinaryOp.LESS));
        operators.put(">=", new RPNBinaryOp(RPNBinaryOp.GREATER_EQUALS));
        operators.put("<=", new RPNBinaryOp(RPNBinaryOp.LESS_EQUALS));
    }

    public void execute(RPNCalc calc)  {
        long b = calc.longPop();
        long a = calc.longPop();
        try {
            calc.push(operator.applyAsLong(a, b));
        }
        catch (UncheckedIOException e) {
            throw new InvalidValueException(e.getMessage());
        }
    }
}
