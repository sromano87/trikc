package com.google.debugging.sourcemap;

import junit.framework.Test;
import junit.framework.TestSuite;

public class MyTestSuite extends junit.framework.TestSuite{
	
	public static Test suite() {
        final TestSuite s = new TestSuite();
        s.addTestSuite(Base64Test.class);
        s.addTestSuite(SourceMapConsumerV3Test.class);
        return s;
    }

}
