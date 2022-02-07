package app.ops;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import app.RPNCalc;
import app.exceptions.impl.StackException;

public class RPNStaticOpTests {
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

    @Test(expected = StackException.class)
    public void testExeClear1() {
        calc.push("100");
        calc.push("24");
        calc.execute("c");
        calc.pop();
    }

    @Test(expected = StackException.class)
    public void testExeClear2() {
        calc.push("100");
        calc.push("24");
        calc.execute("clear");
        calc.pop();
    }

    @Test
    public void testSizeOp() {
        calc.push("100");
        calc.push("10");
        calc.execute("s");
        assertEquals("Size operation not functioning", "2", calc.pop());
    }

    @Test
    public void testDuplicate1() {
        calc.push("222");
        calc.execute("d");
        calc.pop();
        assertEquals("Incorrect value for duplicate - expected 222", "222", calc.pop());
    }

    @Test
    public void testDuplicate2() {
        calc.push("222");
        calc.execute("d");
        assertEquals("Incorrect value for duplicate - expected 222", "222", calc.pop());
    }

    @Test
    public void testReverse1() {
        calc.push("222");
        calc.push("111");
        calc.execute("r");
        assertEquals("Incorrect value for duplicate - expected 222", "222", calc.pop());
    }

    @Test
    public void testReverse2() {
        calc.push("222");
        calc.push("111");
        calc.execute("r");
        calc.pop();
        assertEquals("Incorrect value for duplicate - expected 111", "111", calc.pop());
    }

    @Test(expected = StackException.class)
    public void testReverse3() {
        calc.push("222");
        calc.execute("r");
    }

    
    @Test
    public void testSignedTransform1() {
        // calc starts off unsigned
        calc.execute("+-");
        assertEquals("+-", calc.signed());
        
    }

    @Test
    public void testSignedTransform2() {
        // calc starts off unsigned
        calc.execute("+-");
        assertEquals("+-", calc.signed());
        
    }

    @Test
    public void testSignedTransformOverflow1() {
        // calc starts off unsigned
        calc.execute("+-");
        calc.push("100");
        calc.push("-100");
        calc.execute("+-");
        assertEquals("O DECIMAL", calc.flags());
    }

    @Test
    public void testSignedTransformOverflow2() {
        // calc starts off unsigned
        calc8Bit.push("100");
        calc8Bit.push("255");
        calc8Bit.execute("+-");
        assertEquals("O DECIMAL", calc8Bit.flags());
    }

    @Test
    public void testSignedTransformOverflow3() {
        // calc is 32 bit, 2^32 = 4294967296
        calc.push("1");
        calc.push("4294967296");
        calc.execute("+-");
        assertEquals("O DECIMAL", calc.flags());
    }

    @Test
    public void testSignedTransformOverflow4() {
        // calc starts off unsigned
        calc.execute("+-");
        calc.push("100");
        calc.push("-6000");
        calc.execute("+-");
        assertEquals(""+(-6000 & (long) (Math.pow(2, 32) - 1)), calc.pop());
    }

    @Test
    public void testSignedTransformOverflow5() {
        // calc starts off unsigned
        calc.execute("+-");
        calc.push("100");
        calc.push("-2147483648"); // 2^32 / 2 = 2^31
        calc.execute("+-");
        assertEquals(""+(-2147483648 & (long) (Math.pow(2, 32) - 1)), calc.pop());
    }

    @Test
    public void testUnsignedOp1() {
        calc.execute("signed");
        calc.execute("unsigned");
        assertEquals("++", calc.signed());
    }

    @Test
    public void testUnsignedOp2() {
        calc.execute("signed");
        calc.push("-100");
        calc.execute("unsigned");
        assertEquals("O DECIMAL", calc.flags());
    }

    @Test
    public void testUnsignedOp3() {
        calc.execute("signed");
        calc.push("-6000");
        calc.execute("unsigned");
        assertEquals(""+(-6000 & (long) (Math.pow(2, 32) - 1)), calc.pop());
    }

    @Test
    public void testSignedOp1() {
        calc.execute("signed");
        assertEquals("+-", calc.signed());
    }

    @Test
    public void testSignedOp2() {
        calc.push("2347483648");
        calc.execute("signed");
        assertEquals("O DECIMAL", calc.flags());
    }

    @Test
    public void testSignedOp3() {
        calc.push("2347483648");
        calc.execute("signed");
        assertEquals(""+(2347483648l & (long) (Math.pow(2, 32)/2 - 1)), calc.pop());
    }
}
