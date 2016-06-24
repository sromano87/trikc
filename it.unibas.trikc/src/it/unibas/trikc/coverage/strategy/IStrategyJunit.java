package it.unibas.trikc.coverage.strategy;

import it.unibas.trikc.coverage.JacocoServices;
import it.unibas.trikc.modelEntity.TestSuite;
import it.unibas.trikc.modelEntity.compositeClass.IClass;


/**
 * Interface IStrategyJunit
 * 
 *
 * @author TeamCoverage
 */
public interface IStrategyJunit {

	public TestSuite getTestSuite();
	public void setTestSuite(TestSuite testSuite);
	public String getClassName();
	public void setClassName(String className);
	public IClass getClazz();
	public void setClazz(IClass clazz);
	public JacocoServices getJacocoServices();
	public void setJacocoServices(JacocoServices jacocoServices);
	public void setTestSuiteClass(Class<junit.framework.TestSuite> testSuiteClass);
	public Class<junit.framework.TestSuite> getTestSuiteClass();
	
	/**
	 * Explore the classes that belong to the TestSuite and run all the test
	 * after they have been instrumented
	 * 
	 * @return {@link TestSuite} the testSuite containing all tests performed and the lines covered
	 */
	public TestSuite executeCoverage() throws Exception;
}
