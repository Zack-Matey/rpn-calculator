package app.ops.impl;

import app.ops.RPNOp;
import app.RPNCalc;

import java.util.Map;
import java.util.function.LongUnaryOperator;

public class RPNUnaryOp implements RPNOp {
    private static final LongUnaryOperator BITWISE_NOT = (long a) -> {
        return ~a;
    };
    private static final LongUnaryOperator NOT = (long a) -> {
        return a == 0 ? 1 : 0;
    };

    private LongUnaryOperator operator;
    
    private RPNUnaryOp(LongUnaryOperator op) {
        this.operator = op;
    }

    public static void associateOperators(RPNCalc calc) {
        Map<String, RPNOp> operators = calc.getOperatorsMap();
        operators.put("~",  new RPNUnaryOp(RPNUnaryOp.BITWISE_NOT));
        operators.put("!", new RPNUnaryOp(RPNUnaryOp.NOT));
    }

    public void execute(RPNCalc calc) {
        calc.push(operator.applyAsLong(calc.longPop()));
    }
}
