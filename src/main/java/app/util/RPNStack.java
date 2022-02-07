package app.util;

import java.util.LinkedList;

import app.exceptions.impl.StackException;

public class RPNStack {

    private boolean overflowFlag = false;
    private long posLim;
    private long negLim;
    private long bitMask;
    private int bitsize;
    private boolean signed;
    private LinkedList<Long> stack;

    public RPNStack(int bitsize, boolean signed) {

        stack = new LinkedList<Long>();
        this.bitsize = bitsize;

        if (signed)
            setSigned();
        else
            setUnsigned();
    }

    public void setSigned() {
        signed = true;
        posLim = (long) (Math.pow(2, bitsize)/2 - 1);
        negLim = (long) -1*(posLim+1);
        bitMask = posLim;

        for (int i = 0; i < stack.size(); i++) {
            stack.set(i, truncateAndSetOverflow(stack.get(i)));
        }
    }

    public void setUnsigned() {
        signed = false;
        posLim = (long) (Math.pow(2, bitsize) - 1);
        negLim = 0l;
        bitMask = posLim;

        for (int i = 0; i < stack.size(); i++) {
            stack.set(i, truncateAndSetOverflow(stack.get(i)));
        }
    }

    public boolean getSigned() {
        return this.signed;
    }

    public void push(long value) {
            stack.add(truncateAndSetOverflow(value));
        }

    private long truncateAndSetOverflow(long value) {
        if (value <= posLim && value >= negLim)
            return value;
        else {
            overflowFlag = true;
            return (value & bitMask);
        }
    }

    public long pop() {
        if (stack.isEmpty())
            throw new StackException("Stack empty, cannot pop value");
        long returnValue = stack.getLast();
        stack.removeLast();
        return returnValue;
    }

    public long peek(int depth) {
        if (stack.isEmpty())
            throw new StackException("Stack empty, cannot peek");
        if (stack.size() < depth+1)
            throw new StackException("Stack depth invalid");
        return stack.get(stack.size()-1-depth);
    }

    public void clear() {
        stack.clear();
    }

    public int size() {
        return stack.size();
    }

    public boolean flags() {
        return overflowFlag;
    }

}