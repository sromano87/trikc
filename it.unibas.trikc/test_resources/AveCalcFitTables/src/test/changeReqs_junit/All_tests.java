package changeReqs_junit;

import java.util.ArrayList;

import calculator.MainFrame;
import junit.framework.Test;
import junit.framework.TestSuite;

public class All_tests extends TestSuite {
	public static Test suite() {
        TestSuite suite = new TestSuite("Test for AveCalc");
        //$JUnit-BEGIN$
        
        // Test_ChangeReqs1
        suite.addTestSuite(Test_ChangeReqs1.class);
        
        // Test_ChangeReqs2
        suite.addTestSuite(Test_ChangeReqs2.class);
        
        // Test_ChangeReqs3
        suite.addTestSuite(Test_ChangeReqs3.class);
        
        // Test_ChangeReqs3
        suite.addTestSuite(Test_ChangeReqs4.class);
		
        //$JUnit-END$
        return suite;
    }

}
