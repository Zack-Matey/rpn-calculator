package app.ops.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import app.RPNCalc;
import app.exceptions.impl.InvalidValueException;
import app.exceptions.impl.StackException;
import app.ops.RPNOp;

public class RPNStaticOp implements RPNOp {

    private Method operator;

    private RPNStaticOp(Method op) {
        this.operator = op;
    }

    public static void associateOperators(RPNCalc calc) {
        Map<String, RPNOp> operators = calc.getOperatorsMap();
        try {
            operators.put("c", new RPNStaticOp(RPNStaticOp.class.getDeclaredMethod("clear", RPNCalc.class)));
            operators.put("clear", new RPNStaticOp(RPNStaticOp.class.getDeclaredMethod("clear", RPNCalc.class)));
            operators.put("s", new RPNStaticOp(RPNStaticOp.class.getDeclaredMethod("size", RPNCalc.class)));
            operators.put("size", new RPNStaticOp(RPNStaticOp.class.getDeclaredMethod("size", RPNCalc.class)));
            operators.put("d", new RPNStaticOp(RPNStaticOp.class.getDeclaredMethod("duplicate", RPNCalc.class)));
            operators.put("duplicate", new RPNStaticOp(RPNStaticOp.class.getDeclaredMethod("duplicate", RPNCalc.class)));
            operators.put("r", new RPNStaticOp(RPNStaticOp.class.getDeclaredMethod("reverse", RPNCalc.class)));
            operators.put("reverse", new RPNStaticOp(RPNStaticOp.class.getDeclaredMethod("reverse", RPNCalc.class)));
            operators.put("signed", new RPNStaticOp(RPNStaticOp.class.getDeclaredMethod("setSigned", RPNCalc.class)));
            operators.put("unsigned", new RPNStaticOp(RPNStaticOp.class.getDeclaredMethod("setUnsigned", RPNCalc.class)));
            operators.put("+-", new RPNStaticOp(RPNStaticOp.class.getDeclaredMethod("switchSign", RPNCalc.class)));
        } catch (NoSuchMethodException | SecurityException e) {} // satify compiler
    }

    public void execute(RPNCalc calc) {
        try {
            this.operator.invoke(this, calc);
        }
        catch (InvocationTargetException e) {
            // if the method throws an exception, throw it if it's one we declared
            // which should be the only exceptions which can get thrown
            try {
                throw e.getCause();
            } 
            catch (InvalidValueException | StackException e1) {
                throw e1;
            } 
            catch (Throwable e1) {}
        } 
        // satify compiler
        catch (IllegalAccessException | IllegalArgumentException e) {}
    }

    private void clear(RPNCalc calc) {
        calc.clear();
    }

    private void size(RPNCalc calc) {
        calc.push(calc.size());
    }

    private void duplicate(RPNCalc calc) {
        long a = calc.longPop();
        calc.push(a);
        calc.push(a);
    }

    private void reverse(RPNCalc calc) {
        long a = calc.longPop();
        long b = calc.longPop();
        calc.push(a);
        calc.push(b);
    }

    private void switchSign(RPNCalc calc) {
        calc.setSigned(!calc.getSigned());
    }

    private void setSigned(RPNCalc calc) {
        calc.setSigned(true);
    }

    private void setUnsigned(RPNCalc calc) {
        calc.setSigned(false);
    }
}
