package it.unibas.trikc.coverage.strategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;

import org.jacoco.core.analysis.CoverageBuilder;
import org.junit.runner.JUnitCore;
import org.junit.runner.Request;
import org.junit.runners.AllTests;

import it.unibas.trikc.modelEntity.TestSuite;
import it.unibas.trikc.modelEntity.method.TestCase;
import junit.framework.Test;


/**
 * Class that implements pattern strategy
 * extends {@link AbstractStrategyJunit}
 * used when the TestSuite to analyze uses Junit3
 *
 * 
 * @author TeamCoverage
 */
public class StrategyJunit3 extends AbstractStrategyJunit{

	@Override
	public TestSuite executeCoverage() throws Exception {
		junit.framework.TestSuite allTestsSuite = null;
		@SuppressWarnings("rawtypes")
		ArrayList listOfTestsClasses = null;
		try {
			allTestsSuite = (junit.framework.TestSuite) AllTests.testFromSuiteMethod(getTestSuiteClass());
			Enumeration<Test> enumeration = allTestsSuite.tests();
			listOfTestsClasses= Collections.list(enumeration);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		for(int i = 0; i < listOfTestsClasses.size(); i++) {
			junit.framework.TestSuite testClass = (junit.framework.TestSuite) listOfTestsClasses.get(i);
			for(int cm = 0; cm < testClass.countTestCases(); cm++) {
				Class<?> testClassToCoverage = getJacocoServices().createInstrument(getClassName(), testClass.getName());	
				if(!(testClass.testAt(cm).toString().contains("jacoco"))) {
					String[] tmp = testClass.testAt(cm).toString().split("\\(");
					String nameTestMethod = testClass.getName() + "." + tmp[0];	
					TestCase testCase = this.findTestCase(nameTestMethod);

					Request request = Request.method(testClassToCoverage, tmp[0]);
					new JUnitCore().run(request);
						
					CoverageBuilder coverageBuilder = getJacocoServices().collectAnalysis(getClassName());
					analyzeResult(coverageBuilder, getClazz(), testCase);	
				}
				getJacocoServices().runtimeShutdown();	
			}	
		}
		
		return super.getTestSuite();
	}

	
}
