package it.unibas.trikc.coverage;

import java.util.Map;

import it.unibas.trikc.coverage.strategy.IStrategyJunit;
import it.unibas.trikc.modelEntity.Package;
import it.unibas.trikc.modelEntity.TestSuite;


/**
 * Interface ICoverage
 *
 * @author TeamCoverage
 */
public interface ICoverage {
	
	public void executeCoverage() throws Exception;
	public void setClassesPackages(Map<String, Package> classesPackage);
	public void setTestSuite(TestSuite testSuite);
	public TestSuite getTestSuite();
	public IStrategyJunit getStrategy();
	void setBinPath(String binPath);
	void setMemoryClassLoader(MemoryClassLoader memoryClassLoader);
	
}
