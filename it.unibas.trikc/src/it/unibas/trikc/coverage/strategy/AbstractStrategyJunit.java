package it.unibas.trikc.coverage.strategy;

import org.jacoco.core.analysis.CoverageBuilder;
import org.jacoco.core.analysis.IClassCoverage;
import org.jacoco.core.analysis.ICounter;
import org.jacoco.core.analysis.IMethodCoverage;

import it.unibas.trikc.coverage.JacocoServices;
import it.unibas.trikc.modelEntity.Line;
import it.unibas.trikc.modelEntity.TestSuite;
import it.unibas.trikc.modelEntity.compositeClass.IClass;
import it.unibas.trikc.modelEntity.method.IMethod;
import it.unibas.trikc.modelEntity.method.MyMethod;
import it.unibas.trikc.modelEntity.method.TestCase;

/**
 * Abstract class that implements {@link IStrategyJunit}
 *
 * 
 * @author TeamCoverage
 */
public abstract class AbstractStrategyJunit implements IStrategyJunit{
	
	private TestSuite testSuite;
	private IClass clazz;
	private JacocoServices jacocoServices = new JacocoServices();
	private Class<junit.framework.TestSuite> testSuiteClass;
	
	/**
	 * @return testSuite 
	 * 				the filled {@link TestSuite}
	 * */
	public TestSuite getTestSuite() {
		return testSuite;
	}
	
	/**
	 * @param testSuite 
	 * 				the {@link TestSuite} to fill
	 * */
	public void setTestSuite(TestSuite testSuite) {
		this.testSuite = testSuite;
	}
	
	/**
	 * @return class
	 * 			 the class to analyze
	 * */
	public IClass getClazz() {
		return clazz;
	}

	/**
	 * @param clazz
	 * 				the class to analyze
	 * */
	public void setClazz(IClass clazz) {
		this.clazz = clazz;
	}

	/**
	 * @return jacocoServices 
	 * 					the instance of the jacocoService class to use
	 * */
	public JacocoServices getJacocoServices() {
		return jacocoServices;
	}

	/**
	 * @param jacocoServices 
	 * 					the instance of the jacocoService class to use
	 * */
	public void setJacocoServices(JacocoServices jacocoServices) {
		this.jacocoServices = jacocoServices;
	}
	
	/**
	 * @return testSuiteClass 
	 * 					the test suite class to run
	 * */
	public Class<junit.framework.TestSuite> getTestSuiteClass() {
		return testSuiteClass;
	}

	/**
	 * @param testSuiteClass 
	 * 					the test suite class to run
	 * */
	public void setTestSuiteClass(Class<junit.framework.TestSuite> testSuiteClass) {
		this.testSuiteClass = testSuiteClass;
	}
	
	/**
	 * Finds a {@link TestCase} in the {@link TestSuite} by the name and returns it
	 * if it does not exist creates a new one
	 * 
	 * @return testCase 
	 * 				
	 * */
	public TestCase findTestCase(String nameTestMethod) {
		for(TestCase test : testSuite.getTestCases()) {
			if(test.getFullName().equals(nameTestMethod)) {
				return test;
			}
		}
		TestCase testCase = new TestCase();
		testCase.setFullName(nameTestMethod);
		testSuite.addTestCase(testCase);
		return testCase;
	}
	
	/**Analyzes the coverage calculated by JaCoCo and creates {@link Line}, {@link MyMethod}
	 * 
	 * @param coverageBuilder 
	 * 					{@link CoverageBuilder}	result of the coverage
	 * @param clazz
	 * 				{@link IClass} to add the created methods
	 * 
	 * @param testCase
	 * 				{@link TestCase} to add the covered lines
	 * 
	 * */
	public void analyzeResult(CoverageBuilder coverageBuilder,IClass clazz, TestCase testCase) {
		for (IClassCoverage cc : coverageBuilder.getClasses()) {
			for(IMethodCoverage m : cc.getMethods()) {
				String methodName = m.getName();
				String methodBytecode = m.getDesc();
				MyMethod myMethod = new MyMethod();
				myMethod.setDescriptor(methodName+methodBytecode);
				myMethod.setFullName(methodName);
				MyMethod methodFound = findMethod(clazz, methodName);
				if(methodFound == null) {
					methodFound = myMethod;
					methodFound.setMyClass(clazz);
				}
				String methodFullName = clazz.getFullName() + "." + methodName + methodBytecode;
				for (int l = m.getFirstLine(); l <= m.getLastLine(); l++) {
					if(cc.getLine(l).getStatus() == ICounter.FULLY_COVERED || cc.getLine(l).getStatus() == ICounter.PARTLY_COVERED) {
						Line line = createLine(l, methodFullName);
						testCase.addCoveredLine(line);
						methodFound.addLine(line);
						line.setMyMethod(methodFound);
					}
				}
			}	
		}		
	}

	/**Creates the {@link Line}*/
	public Line createLine(int number, String methodFullName) {
		String lineId = methodFullName + "." + number;
		Line line = new Line();
		line.setNumber(number);
		line.setId(lineId);
		return line;
	}
	
	/**
	 * Finds a {@link MyMethod} in the {@link IClass} by the name and returns it
	 * if it does not exist return null
	 * 
	 * @return method
	 * 				
	 * */
	private MyMethod findMethod(IClass clazz, String methodName) {
		for(IMethod m : clazz.getMethods()) {
			if(m.getFullName().equals(methodName)) {
				return (MyMethod) m;
			}
		}
		return null;
	}

	

}
