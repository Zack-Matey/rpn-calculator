package app;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import app.exceptions.impl.InvalidValueException;
import app.ops.RPNOp;
import app.ops.impl.RPNBinaryOp;
import app.ops.impl.RPNFormatOp;
import app.ops.impl.RPNStaticOp;
import app.ops.impl.RPNUnaryOp;
import app.util.RPNFormatter;
import app.util.RPNParser;
import app.util.RPNStack;
import app.util.RPNFormatModes.Mode;

//T2: Use a coverage tool

public class RPNCalc {

    private final int DEFAULT_BIT_SIZE = 64;
    private final boolean DEFAULT_SIGNED_STATE = true;
    private final Mode DEFAULT_FORMAT_MODE = Mode.DECIMAL;
    
    private Map<String, RPNOp> operators = new HashMap<String, RPNOp>();
    private RPNStack stack;
    private RPNParser parser;
    private RPNFormatter formatter;
    private final String NUMERIC_REGEX = "-?\\d+";

    public RPNCalc() {
        fillMap();
        stack = new RPNStack(DEFAULT_BIT_SIZE, DEFAULT_SIGNED_STATE);
        parser = new RPNParser();
        formatter = new RPNFormatter(DEFAULT_FORMAT_MODE);
    }

    public RPNCalc(int bitsize) {
        fillMap();
        stack = new RPNStack(bitsize, DEFAULT_SIGNED_STATE);
        parser = new RPNParser();
        formatter = new RPNFormatter(DEFAULT_FORMAT_MODE);
    }

    public RPNCalc(int bitsize, boolean signed) {
        fillMap();
        stack = new RPNStack(bitsize, signed);
        parser = new RPNParser();
        formatter = new RPNFormatter(DEFAULT_FORMAT_MODE);
    }

    public RPNCalc(int bitsize, boolean signed, Mode mode) {
        fillMap();
        stack = new RPNStack(bitsize, signed);
        parser = new RPNParser();
        formatter = new RPNFormatter(mode);
    }


    public void fillMap() {
        RPNBinaryOp.associateOperators(this);
        RPNUnaryOp.associateOperators(this);
        RPNStaticOp.associateOperators(this);
        RPNFormatOp.associateOperators(this);
    }

    public Map<String, RPNOp> getOperatorsMap() {
        return operators;
    }
    
    public void execute(String op) {
        if (operators.keySet().contains(op))
            operators.get(op).execute(this);
        else
            throw new InvalidValueException("Invalid operation");
    }

    public void evaluate(String expression) {
        StringTokenizer strtok = new StringTokenizer(expression, " ");
        String currentToken;
        while (strtok.hasMoreTokens()) {
            currentToken = strtok.nextToken();
            if (Pattern.matches(NUMERIC_REGEX, currentToken)) {
                this.push(currentToken);
            }
            else {
                this.execute(currentToken);
            }
        }
    }
    
    public void push(String value) {
        try {
            stack.push(parser.parse(value));
        }
        catch (NumberFormatException e) {
            throw new InvalidValueException("Non-numerical string argument to push");
        }
    }

    public void push(long value) {
        stack.push(value);
    }

    public String pop() {
        return formatter.format(stack.pop());
    }

    public long longPop() {
        return stack.pop();
    }

    public String peek(int depth) {
        return ""+stack.peek(depth);
    }

    public void clear() {
        stack.clear();
    }

    public void setSigned(boolean signed) {
        if (signed)
            stack.setSigned();
        else
            stack.setUnsigned();
    }

    public boolean getSigned() {
        return stack.getSigned();
    }

    public int size() {
        return stack.size();
    }

    public String signed() {
        return stack.getSigned() ? "+-" : "++";
    }

    public void setFormatMode(Mode mode) {
        formatter.setState(mode);
    }

    public String flags() {
        return (stack.flags() ? "O " : "o ")+formatter.getFormat();
    }

}