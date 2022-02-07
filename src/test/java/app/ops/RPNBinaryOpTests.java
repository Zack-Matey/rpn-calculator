package app.ops;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import app.RPNCalc;
import app.exceptions.impl.InvalidValueException;

public class RPNBinaryOpTests {
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
    public void testExeAdd() {
        calc.push("100");
        calc.push("20");
        calc.execute("+");
        assertEquals("Add not producing expected value", "120", calc.pop());
    }

    @Test
    public void testExeSub() {
        calc.push("100");
        calc.push("20");
        calc.execute("-");
        assertEquals("Sub not producing expected value", "80", calc.pop());
    }

    @Test
    public void testExeDiv() {
        calc.push("100");
        calc.push("20");
        calc.execute("/");
        assertEquals("Div not producing expected value", "5", calc.pop());
    }

    @Test
    public void testExeMult() {
        calc.push("100");
        calc.push("20");
        calc.execute("*");
        assertEquals("Mult not producing expected value", "2000", calc.pop());
    }

    @Test
    public void testExeMod1() {
        calc.push("100");
        calc.push("20");
        calc.execute("%");
        assertEquals("Mod not producing expected value", "0", calc.pop());
    }

    @Test
    public void testExeMod2() {
        calc.push("100");
        calc.push("24");
        calc.execute("%");
        assertEquals("Mod not producing expected value", "4", calc.pop());
    }

    @Test
    public void test8BitAdd() {
        calc8Bit.push("150");
        calc8Bit.push("150");
        calc8Bit.execute("+");
        assertEquals("Value not properly truncated", "44", calc8Bit.pop());
    }

    @Test(expected = InvalidValueException.class)
    public void testDivBy0() {
        calc.push("100");
        calc.push("0");
        calc.execute("/");
    }

    @Test(expected = InvalidValueException.class)
    public void testModBy0() {
        calc.push("100");
        calc.push("0");
        calc.execute("%");
    }

    @Test
    public void testBitwiseAnd1() {
        calc.push("0");
        calc.push("1000");
        calc.execute("&");
        assertEquals("Incorrect value for bitwise and - expected 0", "0", calc.pop());
    }

    @Test
    public void testBitwiseAnd2() {
        calc.push("100");
        calc.push("45");
        calc.execute("&");
        assertEquals("Incorrect value for bitwise and - expected 36", "36", calc.pop());
    }

    @Test
    public void testBitwiseAnd3() {
        calc.push("555");
        calc.push("222");
        calc.execute("&");
        assertEquals("Incorrect value for bitwise and - expected 10", "10", calc.pop());
    }

    @Test
    public void testBitwiseOr1() {
        calc.push("0");
        calc.push("1000");
        calc.execute("|");
        assertEquals("Incorrect value for bitwise or - expected 1000", "1000", calc.pop());
    }

    @Test
    public void testBitwiseOr2() {
        calc.push("45");
        calc.push("222");
        calc.execute("|");
        assertEquals("Incorrect value for bitwise or - expected 255", "255", calc.pop());
    }

    @Test
    public void testBitwiseOr3() {
        calc.push("255");
        calc.push("333");
        calc.execute("|");
        assertEquals("Incorrect value for bitwise or - expected 511", "511", calc.pop());
    }

    @Test
    public void testBitwiseXor1() {
        calc.push("0");
        calc.push("1000");
        calc.execute("^");
        assertEquals("Incorrect value for bitwise xor - expected 1000", "1000", calc.pop());
    }

    @Test
    public void testBitwiseXor2() {
        calc.push("255");
        calc.push("45");
        calc.execute("^");
        assertEquals("Incorrect value for bitwise xor - expected 210", "210", calc.pop());
    }

    @Test
    public void testBitwiseXor3() {
        calc.push("255");
        calc.push("255");
        calc.execute("^");
        assertEquals("Incorrect value for bitwise xor - expected 0", "0", calc.pop());
    }

    @Test
    public void testLeftshift1() {
        calc.push("1");
        calc.push("2");
        calc.execute("<<");
        assertEquals("Incorrect value for leftshift - expected 4", "4", calc.pop());
    }

    @Test
    public void testLeftshift2() {
        calc.push("16");
        calc.push("1");
        calc.execute("<<");
        assertEquals("Incorrect value for leftshift - expected 32", "32", calc.pop());
    }

    @Test
    public void testLeftshift3() {
        calc.push("64");
        calc.push("2");
        calc.execute("<<");
        assertEquals("Incorrect value for leftshift - expected 256", "256", calc.pop());
    }

    @Test
    public void testLeftshiftOverflow() {
        calc8Bit.push("255");
        calc8Bit.push("1");
        calc8Bit.execute("<<");
        assertEquals("Incorrect result for leftshift - expected overflow", "O DECIMAL", calc8Bit.flags());
    }

    @Test
    public void testLeftshiftTructation() {
        calc8Bit.push("255");
        calc8Bit.push("1");
        calc8Bit.execute("<<");
        assertEquals("Incorrect value for leftshift - expected 254", "254", calc8Bit.pop());
    }

    @Test
    public void testRightshift1() {
        calc.push("64");
        calc.push("2");
        calc.execute(">>");
        assertEquals("Incorrect value for rightshift - expected 16", "16", calc.pop());
    }

    @Test
    public void testRightshift2() {
        calc.push("256");
        calc.push("8");
        calc.execute(">>");
        assertEquals("Incorrect value for rightshift - expected 1", "1", calc.pop());
    }

    @Test
    public void testRightshift3() {
        calc.push("1");
        calc.push("1");
        calc.execute(">>");
        assertEquals("Incorrect value for rightshift - expected 0", "0", calc.pop());
    }

    @Test
    public void testEquals1() {
        calc.push("1");
        calc.push("0");
        calc.execute("==");
        assertEquals("Incorrect value for equals - expected 0", "0", calc.pop());
    }

    @Test
    public void testEquals2() {
        calc.push("5562");
        calc.push("5562");
        calc.execute("==");
        assertEquals("Incorrect value for equals - expected 1", "1", calc.pop());
    }

    @Test
    public void testEquals3() {
        calc8Bit.push("255");
        calc8Bit.push("255");
        calc8Bit.execute("==");
        assertEquals("Incorrect value for equals - expected 1", "1", calc8Bit.pop());
    }

    @Test
    public void testNotEquals1() {
        calc.push("1");
        calc.push("0");
        calc.execute("!=");
        assertEquals("Incorrect value for not equals - expected 1", "1", calc.pop());
    }

    @Test
    public void testNotEquals2() {
        calc.push("22225");
        calc.push("6662");
        calc.execute("!=");
        assertEquals("Incorrect value for not equals - expected 1", "1", calc.pop());
    }

    @Test
    public void testNotEquals3() {
        calc8Bit.push("255");
        calc8Bit.push("255");
        calc8Bit.execute("!=");
        assertEquals("Incorrect value for not equals - expected 0", "0", calc8Bit.pop());
    }

    @Test
    public void testGreaterThan1() {
        calc8Bit.push("255");
        calc8Bit.push("255");
        calc8Bit.execute(">");
        assertEquals("Incorrect value for greater than - expected 0", "0", calc8Bit.pop());
    }

    @Test
    public void testGreaterThan2() {
        calc8Bit.push("254");
        calc8Bit.push("255");
        calc8Bit.execute(">");
        assertEquals("Incorrect value for greater than - expected 0", "0", calc8Bit.pop());
    }

    @Test
    public void testGreaterThan3() {
        calc.push("1000000");
        calc.push("0");
        calc.execute(">");
        assertEquals("Incorrect value for greater than - expected 1", "1", calc.pop());
    }

    @Test
    public void testLessThan1() {
        calc8Bit.push("255");
        calc8Bit.push("255");
        calc8Bit.execute("<");
        assertEquals("Incorrect value for less than - expected 0", "0", calc8Bit.pop());
    }

    @Test
    public void testLessThan2() {
        calc8Bit.push("255");
        calc8Bit.push("254");
        calc8Bit.execute("<");
        assertEquals("Incorrect value for less than - expected 0", "0", calc8Bit.pop());
    }

    @Test
    public void testLessThan3() {
        calc.push("0");
        calc.push("1000000");
        calc.execute("<");
        assertEquals("Incorrect value for less than - expected 1", "1", calc.pop());
    }

    @Test
    public void testLessThanEqualTo1() {
        calc8Bit.push("255");
        calc8Bit.push("255");
        calc8Bit.execute("<=");
        assertEquals("Incorrect value for less than equal to - expected 1", "1", calc8Bit.pop());
    }

    @Test
    public void testLessThanEqualTo2() {
        calc8Bit.push("255");
        calc8Bit.push("254");
        calc8Bit.execute("<=");
        assertEquals("Incorrect value for less than equal to - expected 0", "0", calc8Bit.pop());
    }

    @Test
    public void testLessThanEqualTo3() {
        calc.push("0");
        calc.push("1000000");
        calc.execute("<=");
        assertEquals("Incorrect value for less than equal to - expected 1", "1", calc.pop());
    }

    @Test
    public void testGreaterThanEqualTo1() {
        calc8Bit.push("255");
        calc8Bit.push("255");
        calc8Bit.execute(">=");
        assertEquals("Incorrect value for greater than equal to - expected 1", "1", calc8Bit.pop());
    }

    @Test
    public void testGreaterThanEqualTo2() {
        calc8Bit.push("255");
        calc8Bit.push("254");
        calc8Bit.execute(">=");
        assertEquals("Incorrect value for greater than equal to - expected 1", "1", calc8Bit.pop());
    }

    @Test
    public void testGreaterThanEqualTo3() {
        calc.push("0");
        calc.push("1000000");
        calc.execute(">=");
        assertEquals("Incorrect value for greater than equal to - expected 0", "0", calc.pop());
    }

    @Test
    public void testLogicalAnd1() {
        calc.push("0");
        calc.push("0");
        calc.execute("&&");
        assertEquals("Incorrect value for and - expected 0", "0", calc.pop());
    }

    @Test
    public void testLogicalAnd2() {
        calc.push("0");
        calc.push("100000000000");
        calc.execute("&&");
        assertEquals("Incorrect value for and - expected 0", "0", calc.pop());
    }

    @Test
    public void testLogicalAnd3() {
        calc.push("555555");
        calc.push("100000000000");
        calc.execute("&&");
        assertEquals("Incorrect value for and - expected 1", "1", calc.pop());
    }

    @Test
    public void testLogicalOr1() {
        calc.push("0");
        calc.push("0");
        calc.execute("||");
        assertEquals("Incorrect value for or - expected 0", "0", calc.pop());
    }

    @Test
    public void testLogicalOr2() {
        calc.push("0");
        calc.push("100000000000");
        calc.execute("||");
        assertEquals("Incorrect value for or - expected 1", "1", calc.pop());
    }

    @Test
    public void testLogicalOr3() {
        calc.push("555555");
        calc.push("100000000000");
        calc.execute("||");
        assertEquals("Incorrect value for or - expected 1", "1", calc.pop());
    }

}
