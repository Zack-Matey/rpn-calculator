package app.ops;

import app.RPNCalc;
import app.exceptions.impl.InvalidValueException;


public interface RPNOp {
    public void execute(RPNCalc calc) throws InvalidValueException;
}
