package it.unibas.trikc.coverage.strategy;

import java.lang.reflect.Method;

import org.jacoco.core.analysis.CoverageBuilder;
import org.junit.Before;
import org.junit.runner.JUnitCore;
import org.junit.runner.Request;
import org.junit.runners.Suite.SuiteClasses;

import it.unibas.trikc.modelEntity.TestSuite;
import it.unibas.trikc.modelEntity.method.TestCase;

/**
 * Class that implements pattern strategy
 * extends {@link AbstractStrategyJunit}
 * used when the TestSuite to analyze uses Junit4
 *
 * 
 * @author TeamCoverage
 */
public class StrategyJunit4 extends AbstractStrategyJunit{

	@Override
	public TestSuite executeCoverage() throws Exception {
		
		SuiteClasses suiteJunit = super.getTestSuiteClass().getAnnotation(SuiteClasses.class);
		Class<?>[] testClasses = suiteJunit.value();
			
		for(int i = 0; i < testClasses.length; i++) {
			Class<?> testClass = testClasses[i];
			Method[] methods = testClass.getDeclaredMethods();
			for(int cm = 0; cm < methods.length; cm++) {
				Class<?> testClassToCoverage = super.getJacocoServices().createInstrument(super.getClassName(), testClass.getName());
				if(!methods[cm].isAnnotationPresent(Before.class) && !methods[cm].getName().contains("jacoco")) {
					String nameTestMethod = testClass.getName() + "." + methods[cm].getName();	
					TestCase testCase = this.findTestCase(nameTestMethod);
					Request request = Request.method(testClassToCoverage, methods[cm].getName());
					new JUnitCore().run(request);
						
					CoverageBuilder coverageBuilder = super.getJacocoServices().collectAnalysis(super.getClassName());
					analyzeResult(coverageBuilder, super.getClazz(), testCase);	
				}
				super.getJacocoServices().runtimeShutdown();	
			}	
		}
		return super.getTestSuite();
	}

}
