package app.util;

import app.exceptions.impl.InvalidValueException;

public class RPNParser {

    private final String ROMAN = "^(M{0,3})(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})$";

    public long parse(String number) {
        boolean negative = false;
        long result;
        if (number.startsWith("-"))
            negative = true;
        if (number.compareTo("0") == 0)
            return 0;
        
        if (number.startsWith("0b") || number.startsWith("-0b"))
            result = Long.parseLong(number, 2 + (negative ? 1 : 0), number.length(), 2);
        else if (number.startsWith("0x") || number.startsWith("-0x"))
            result = Long.parseLong(number, 2 + (negative ? 1 : 0), number.length(), 16);
        else if (number.startsWith("0") || number.startsWith("-0"))
            result = Long.parseLong(number, 1 + (negative ? 1 : 0), number.length(), 8);
        else if (this.isRoman(number))
            result = parseRoman(number);
        else
            result = Long.parseLong(number, (negative ? 1 : 0), number.length(), 10);

        return (negative) ? result * -1 : result;
    }
    
    private int parseRoman(String number) {
        if (!isRoman(number))
            throw new InvalidValueException("Not a roman numberal");
        return recursiveParseRoman(number);
    }

    // Credit to StackOverflow: https://stackoverflow.com/a/19392801 for the ROMAN regex
    private boolean isRoman(String number) {
        return (number.matches(ROMAN)) ? true : false;
    }

    // Credit to StackOverflow: https://stackoverflow.com/a/19392801 for this logic
    private int recursiveParseRoman(String number) {
        if (number.isEmpty())
            return 0;
        if (number.startsWith("M"))
            return 1000 + recursiveParseRoman(number.substring(1));
        else if (number.startsWith("CM"))
            return 900 + recursiveParseRoman(number.substring(2));
        else if (number.startsWith("D"))
            return 500 + recursiveParseRoman(number.substring(1));
        else if (number.startsWith("CD"))
            return 400 + recursiveParseRoman(number.substring(2));
        else if (number.startsWith("C"))
            return 100 + recursiveParseRoman(number.substring(1));
        else if (number.startsWith("XC"))
            return 90 + recursiveParseRoman(number.substring(2));
        else if (number.startsWith("L"))
            return 50 + recursiveParseRoman(number.substring(1));
        else if (number.startsWith("XL"))
            return 40 + recursiveParseRoman(number.substring(2));
        else if (number.startsWith("X"))
            return 10 + recursiveParseRoman(number.substring(1));
        else if (number.startsWith("IX"))
            return 9 + recursiveParseRoman(number.substring(2));
        else if (number.startsWith("V"))
            return 5 + recursiveParseRoman(number.substring(1));
        else if (number.startsWith("IV"))
            return 4 + recursiveParseRoman(number.substring(2));
        else if (number.startsWith("I"))
            return 1 + recursiveParseRoman(number.substring(1));
        throw new IllegalArgumentException("Unexpected roman numerals");
    }
}
