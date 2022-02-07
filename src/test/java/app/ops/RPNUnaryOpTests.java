package app.ops;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import app.RPNCalc;

public class RPNUnaryOpTests {
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
    public void testBitwiseNot1() {
        calc.push("255");
        calc.execute("~"); // 32 bit calculator, expect 4294967040
        assertEquals("Incorrect value for bitwise not - expected 4294967040", "4294967040", calc.pop());
    }

    @Test
    public void testBitwiseNot2() {
        calc.push("0");
        calc.execute("~"); // 32 bit calculator, expect 4294967295
        assertEquals("Incorrect value for bitwise not - expected 4294967295", "4294967295", calc.pop());
    }

    @Test
    public void testBitwiseNot3() {
        calc.push("4294967295");
        calc.execute("~"); // 32 bit calculator, expect 0
        assertEquals("Incorrect value for bitwise not - expected 0", "0", calc.pop());
    }

    @Test
    public void testLogicalNot1() {
        calc.push("0");
        calc.execute("!");
        assertEquals("Incorrect value for not - expected 1", "1", calc.pop());
    }

    @Test
    public void testLogicalNot2() {
        calc.push("100000000000");
        calc.execute("!");
        assertEquals("Incorrect value for not - expected 0", "0", calc.pop());
    }

    @Test
    public void testLogicalNot3() {
        calc.push("222");
        calc.execute("!");
        assertEquals("Incorrect value for not - expected 0", "0", calc.pop());
    }
}
