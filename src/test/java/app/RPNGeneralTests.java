package app;

import static org.junit.Assert.*;

import javax.naming.directory.InvalidAttributeValueException;

import org.junit.Before;
import org.junit.Test;

import app.exceptions.impl.InvalidValueException;
import app.exceptions.impl.StackException;

public class RPNGeneralTests {
    RPNCalc calc;
    RPNCalc calc8Bit;
    RPNCalc calc8BitSigned;
    RPNCalc calcSigned;
    RPNCalc calc64Bit;

    @Before
    public void setupObjects() {
        calc = new RPNCalc(32, false); // unsigned
        calc8Bit = new RPNCalc(8, false); // unsigned
        calc8BitSigned = new RPNCalc(8, true); // signed
        calcSigned = new RPNCalc(32, true); // signed
        calc64Bit = new RPNCalc(64, false);
    }

    @Test
    public void testPush() {
        calc.push("999");
        assertEquals("Retrieved number not equal to sent value", "999", calc.pop());
    }

    @Test(expected = InvalidValueException.class)
    public void testInvalidPush() {
        calc.push("five");
    }

    @Test
    public void testPop() {
        calc.push("888");
        assertEquals("Retrieved number not equal to sent value", "888", calc.pop());
    }

    @Test
    public void testPeek()  {
        calc.push("888");
        assertEquals("Retrived number not equal to sent value", "888", calc.peek(0));
    }

    @Test
    public void testPeek2()  {
        calc.push("100");
        calc.push("2");
        calc.push("1");
        assertEquals("Retrieved number not equalt to sent value", "100", calc.peek(2));
    }

    @Test
    public void testSize() {
        calc.push("100");
        calc.push("200");
        calc.push("2000");
        assertEquals("Stack size incorrect", 3, calc.size());
    }

    @Test
    public void testClear() {
        calc.clear();
        assertEquals("Stack not cleared", 0, calc.size());
    }

    @Test
    public void testFlags1() {
        calc8Bit.push("150");
        calc8Bit.push("150");
        calc8Bit.execute("+");
        assertEquals("Flag not set", "O DECIMAL", calc8Bit.flags());
    }

    @Test
    public void testFlags2() {
        calc8Bit.push("300");
        assertEquals("Flag not set", "O DECIMAL", calc8Bit.flags());
    }

    @Test
    public void testSignedNegOverflow1() {
        calc8BitSigned.push("-120");
        calc8BitSigned.push("-9");
        calc8BitSigned.execute("+");
        assertEquals("Expected overflow", "O DECIMAL", calc8BitSigned.flags());
    }

    @Test
    public void testSignedNegOverflow2() {
        calc8BitSigned.push("-12");
        calc8BitSigned.push("11");
        calc8BitSigned.execute("*");
        assertEquals("Expected overflow", "O DECIMAL", calc8BitSigned.flags());
    }

    @Test
    public void testSignedPosOverflow1() {
        calc8BitSigned.push("120");
        calc8BitSigned.push("9");
        calc8BitSigned.execute("+");
        assertEquals("Expected overflow", "O DECIMAL", calc8BitSigned.flags());
    }

    @Test
    public void testSignedPosOverflow2()  {
        calc8BitSigned.push("12");
        calc8BitSigned.push("11");
        calc8BitSigned.execute("*");
        assertEquals("Expected overflow", "O DECIMAL", calc8BitSigned.flags());
    }

    @Test
    public void testUnsignedNegOverflow1() {
        calc8Bit.push("0");
        calc8Bit.push("10");
        calc8Bit.execute("-");
        assertEquals("Expected overflow", "O DECIMAL", calc8Bit.flags());
    }

    @Test
    public void testUnsignedNegOverflow2() {
        calc8Bit.push("-1");
        assertEquals("Expected overflow", "O DECIMAL", calc8Bit.flags());
    }

    @Test
    public void testUnsignedPosOverflow1() {
        calc8Bit.push("250");
        calc8Bit.push("6");
        calc8Bit.execute("+");
        assertEquals("Expected overflow", "O DECIMAL", calc8Bit.flags());
    }

    @Test
    public void testUnsignedPosOverflow2() {
        calc8Bit.push("25");
        calc8Bit.push("11");
        calc8Bit.execute("*");
        assertEquals("Expected overflow", "O DECIMAL", calc8Bit.flags());
    }

    @Test
    public void test64Bit() {
        calc64Bit.push("" + Long.MAX_VALUE);
        assertEquals("64-bit calculator cannot accept max value long", "" + Long.MAX_VALUE, calc64Bit.pop());
    }

    @Test(expected = StackException.class)
    public void testInvalidPeek() {
        calc.push("100");
        calc.push("10");
        calc.peek(100);
    }

    @Test(expected = StackException.class)
    public void testInvalidPeek2() {
        calc.peek(100);
    }

    @Test(expected = InvalidValueException.class)
    public void testExeInvalidOp() {
        calc.push("10");
        calc.push("10");
        calc.execute("cheese");
    }

    @Test
    public void testEval1() {
        calc.evaluate("100 200 +");
        assertEquals("Eval + not currect, expected 300", "300", calc.pop());
    }

    @Test
    public void testEval2() {
        calc.evaluate("200 200 /");
        assertEquals("Eval / not currect, expected 1", "1", calc.pop());
    }

    @Test
    public void testEval3() {
        calc.evaluate("50 2 *");
        assertEquals("Eval * not currect, expected 100", "100", calc.pop());
    }

    @Test
    public void test8BitPush1() {
        calc8Bit.push("4");
        calc8Bit.push("255");
        assertEquals("Cannot accomidate 255 on 8 bit", "255", calc8Bit.pop());
    }
    
    @Test
    public void test8BitPush2() {
        calc8Bit.push("6");
        calc8Bit.push("300");
        assertEquals("Value not properly truncated","44",calc8Bit.pop());
    }

}
