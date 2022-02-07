package app.util;

import app.exceptions.impl.InvalidValueException;
import app.util.RPNFormatModes.Mode;

public class RPNFormatter {

    private Mode state;

    public RPNFormatter(Mode state) {
        this.setState(state);
    }

    public void setState(Mode state) {
        this.state = state;
    }

    public String format(long number) {
        return this.format(number, state);
    }

    public String format(long number, Mode mode) {
        String result = "";
        boolean negative = false;;
        if (number < 0) {
            negative = true;
            number *= -1;
        }

        if (mode == Mode.BINARY)
            result = "0b"+Long.toBinaryString(number);
        else if (mode == Mode.HEX)
            result = "0x"+Long.toHexString(number);
        else if (mode == Mode.OCTAL)
            result = "0"+Long.toOctalString(number);
        else if (mode == Mode.ROMAN) {
            if (negative)
                throw new InvalidValueException("Cannot format as roman");
            result = formatRoman(number);
        }
        else if (mode == Mode.DECIMAL)
            result = ""+number;

        if (negative)
            return "-"+result;
        return result;
    }

    private String formatRoman(long number) {
        if (number < 0 || number > 3999)
            throw new InvalidValueException("Cannot format as roman");
        if (number >= 1000)
            return "M" + formatRoman(number-1000);
        else if (number >= 900)
            return "CM" + formatRoman(number - 900);
        else if (number >= 500)
            return "D" + formatRoman(number - 500);
        else if (number >= 400)
            return "CD" + formatRoman(number - 400);
        else if (number >= 100)
            return "C" + formatRoman(number - 100);
        else if (number >= 90)
            return "XC" + formatRoman(number - 90);
        else if (number >= 50)
            return "L" + formatRoman(number - 50);
        else if (number >= 40)
            return "XL" + formatRoman(number - 40);
        else if (number >= 10)
            return "X" + formatRoman(number - 10);
        else if (number >= 9)
            return "IX" + formatRoman(number - 9);
        else if (number >= 5)
            return "V" + formatRoman(number - 5);
        else if (number >= 4)
            return "IV" + formatRoman(number - 4);
        else if (number >= 1)
            return "I" + formatRoman(number - 1);
        return "";
    }

    public String getFormat() {
        return state.toString();
    }
    
}
