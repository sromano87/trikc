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
	public IClass getClazz();
	public void setClazz(IClass clazz);
	public JacocoServices getJacocoServices();
	public void setJacocoServices(JacocoServices jacocoServices);
	public void setTestSuiteClass(Class<junit.framework.TestSuite> testSuiteClass);
	public Class<junit.framework.TestSuite> getTestSuiteClass();

	/**
	 * Explores the classes that belong to the TestSuite and runs all the test
	 * after they have been instrumented
	 * 
	 * @return {@link TestSuite} the testSuite containing all the performed tests and the covered lines
	 */
	public TestSuite executeCoverage() throws Exception;
}
