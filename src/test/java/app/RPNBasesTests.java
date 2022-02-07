package app;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import app.exceptions.impl.InvalidValueException;

public class RPNBasesTests {
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
    public void testRomanPop1() {
        calc.push("3262");
        calc.execute("roman");
        assertEquals("Retrieved number not equal to sent value", "MMMCCLXII", calc.pop());
    }

    @Test
    public void testRomanPop2() {
        calc.push("3999");
        calc.execute("roman");
        assertEquals("Retrieved number not equal to sent value", "MMMCMXCIX", calc.pop());
    }

    @Test(expected = InvalidValueException.class)
    public void testRomanPop3() {
        calc.push("4000");
        calc.execute("roman");
        calc.pop();
    }

    @Test(expected = InvalidValueException.class)
    public void testRomanPop4() {
        calcSigned.push("-40");
        calcSigned.execute("roman");
        System.out.println(calcSigned.pop());
    }

    @Test
    public void testRomanPush1() {
        calc.push("MMMCCLXII");
        assertEquals("Retrieved number not equal to sent value", "3262", calc.pop());
    }

    @Test
    public void testRomanPush2() {
        calc.push("MMMCMXCIX");
        assertEquals("Retrieved number not equal to sent value", "3999", calc.pop());
    }



    @Test
    public void testBinaryPop1() {
        calc.push("3262");
        calc.execute("binary");
        assertEquals("Retrieved number not equal to sent value", "0b110010111110", calc.pop());
    }

    @Test
    public void testBinaryPop2() {
        calc.push("5000000");
        calc.execute("binary");
        assertEquals("Retrieved number not equal to sent value", "0b10011000100101101000000", calc.pop());
    }

    @Test
    public void testBinaryPop3() {
        calcSigned.push("-3262");
        calcSigned.execute("binary");
        assertEquals("Retrieved number not equal to sent value", "-0b110010111110", calcSigned.pop());
    }

    @Test
    public void testBinaryPop4() {
        calcSigned.push("-5000000");
        calcSigned.execute("binary");
        assertEquals("Retrieved number not equal to sent value", "-0b10011000100101101000000", calcSigned.pop());
    }

    @Test
    public void testBinaryPush1() {
        calc.push("0b110010111110");
        assertEquals("Retrieved number not equal to sent value", "3262", calc.pop());
    }

    @Test
    public void testBinaryPush2() {
        calcSigned.push("-0b10011000100101101000000");
        assertEquals("Retrieved number not equal to sent value", "-5000000", calcSigned.pop());
    }


    @Test
    public void testHexPop1() {
        calc.push("3262");
        calc.execute("hex");
        assertEquals("Retrieved number not equal to sent value", "0xcbe", calc.pop());
    }

    @Test
    public void testHexPop2() {
        calc.push("5000000");
        calc.execute("hex");
        assertEquals("Retrieved number not equal to sent value", "0x4c4b40", calc.pop());
    }

    @Test
    public void testHexPop3() {
        calcSigned.push("-3262");
        calcSigned.execute("hex");
        assertEquals("Retrieved number not equal to sent value", "-0xcbe", calcSigned.pop());
    }

    @Test
    public void testHexPop4() {
        calcSigned.push("-5000000");
        calcSigned.execute("hex");
        assertEquals("Retrieved number not equal to sent value", "-0x4c4b40", calcSigned.pop());
    }

    @Test
    public void testHexPush1() {
        calc.push("0xcbe");
        assertEquals("Retrieved number not equal to sent value", "3262", calc.pop());
    }

    @Test
    public void testHexPush2() {
        calcSigned.push("-0x4c4b40");
        assertEquals("Retrieved number not equal to sent value", "-5000000", calcSigned.pop());
    }

    @Test
    public void testHexPush3() {
        calcSigned.push("-0x4C4b40");
        assertEquals("Retrieved number not equal to sent value", "-5000000", calcSigned.pop());
    }

    
    @Test
    public void testOctalPop1() {
        calc.push("3262");
        calc.execute("octal");
        assertEquals("Retrieved number not equal to sent value", "06276", calc.pop());
    }

    @Test
    public void testOctalPop2() {
        calc.push("5000000");
        calc.execute("octal");
        assertEquals("Retrieved number not equal to sent value", "023045500", calc.pop());
    }

    @Test
    public void testOctalPop3() {
        calcSigned.push("-3262");
        calcSigned.execute("octal");
        assertEquals("Retrieved number not equal to sent value", "-06276", calcSigned.pop());
    }

    @Test
    public void testOctalPop4() {
        calcSigned.push("-5000000");
        calcSigned.execute("octal");
        assertEquals("Retrieved number not equal to sent value", "-023045500", calcSigned.pop());
    }

    @Test
    public void testOctalPush1() {
        calc.push("06276");
        assertEquals("Retrieved number not equal to sent value", "3262", calc.pop());
    }

    @Test
    public void testOctalPush2() {
        calcSigned.push("-023045500");
        assertEquals("Retrieved number not equal to sent value", "-5000000", calcSigned.pop());
    }

}
