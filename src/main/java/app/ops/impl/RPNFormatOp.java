package app.ops.impl;

import java.util.Map;

import app.RPNCalc;
import app.ops.RPNOp;
import app.util.RPNFormatModes.Mode;

public class RPNFormatOp implements RPNOp {
    public Mode formatMode;

    private RPNFormatOp(Mode mode) {
        this.formatMode = mode;
    }

    public static void associateOperators(RPNCalc calc) {
        Map<String, RPNOp> operators = calc.getOperatorsMap();
        operators.put("binary", new RPNFormatOp(Mode.BINARY));
        operators.put("hex", new RPNFormatOp(Mode.HEX));
        operators.put("octal", new RPNFormatOp(Mode.OCTAL));
        operators.put("roman", new RPNFormatOp(Mode.ROMAN));
        operators.put("decimal", new RPNFormatOp(Mode.DECIMAL));
    }
    
    public void execute(RPNCalc calc) {
        calc.setFormatMode(formatMode);
    }
}
