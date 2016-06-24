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
 * Abstract class that implements {@link IStrategyUnit}
 *
 * 
 * @author TeamCoverage
 */
public abstract class AbstractStrategyJunit implements IStrategyJunit{
	
	private TestSuite testSuite;
	private String className;
	private IClass clazz;
	private JacocoServices jacocoServices = new JacocoServices();
	private Class<junit.framework.TestSuite> testSuiteClass;
	
	public TestSuite getTestSuite() {
		return testSuite;
	}
	
	public void setTestSuite(TestSuite testSuite) {
		this.testSuite = testSuite;
	}
	

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public IClass getClazz() {
		return clazz;
	}

	public void setClazz(IClass clazz) {
		this.clazz = clazz;
	}

	public JacocoServices getJacocoServices() {
		return jacocoServices;
	}

	public void setJacocoServices(JacocoServices jacocoServices) {
		this.jacocoServices = jacocoServices;
	}
	
	public Class<junit.framework.TestSuite> getTestSuiteClass() {
		return testSuiteClass;
	}

	public void setTestSuiteClass(Class<junit.framework.TestSuite> testSuiteClass) {
		this.testSuiteClass = testSuiteClass;
	}
	
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
	
	/**Analyzes the coverage calculated by JaCoCo and create {@link Line}, {@link MyMethod}
	 * 
	 * @param coverageBuilder: 
	 * 					{@link CoverageBuilder}	result of the coverage
	 * @param clazz:
	 * 				{@link IClass} to add the methods created
	 * 
	 * @param testCase:
	 * 				{@link TestCase} to add the lines covered
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

	public Line createLine(int number, String methodFullName) {
		String lineId = methodFullName + "." + number;
		Line line = new Line();
		line.setNumber(number);
		line.setId(lineId);
		return line;
	}
	
	private MyMethod findMethod(IClass clazz, String methodName) {
		for(IMethod m : clazz.getMethods()) {
			if(m.getFullName().equals(methodName)) {
				return (MyMethod) m;
			}
		}
		return null;
	}

	

}
