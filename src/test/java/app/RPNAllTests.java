package app;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import app.ops.RPNBinaryOpTests;
import app.ops.RPNStaticOpTests;
import app.ops.RPNUnaryOpTests;

@RunWith(Suite.class)
@SuiteClasses({ RPNBinaryOpTests.class, RPNGeneralTests.class, RPNStaticOpTests.class, RPNUnaryOpTests.class, RPNBasesTests.class  })
public class RPNAllTests {
    
}
