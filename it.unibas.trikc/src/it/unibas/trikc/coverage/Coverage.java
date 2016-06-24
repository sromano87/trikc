package it.unibas.trikc.coverage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.runners.Suite.SuiteClasses;

import it.unibas.trikc.coverage.strategy.IStrategyJunit;
import it.unibas.trikc.coverage.strategy.StrategyJunit3;
import it.unibas.trikc.coverage.strategy.StrategyJunit4;
import it.unibas.trikc.modelEntity.Line;
import it.unibas.trikc.modelEntity.Package;
import it.unibas.trikc.modelEntity.TestSuite;
import it.unibas.trikc.modelEntity.compositeClass.IClass;
import it.unibas.trikc.modelEntity.method.TestCase;

/**
 * Class that implements the interface {@link ICoverage}.
 * 
 * 
 * @author TeamCoverage
 * @see it.unibas.trikc.coverage.strategy.StrategyJunit4
 * @see it.unibas.trikc.coverage.strategy.StrategyJunit3
 * @see it.unibas.trikc.coverage.strategy.IStrategyJunit
 */
public class Coverage implements ICoverage{

	private Map<String,Package> classesPackages =  new HashMap<String, Package>();
	private TestSuite testSuite;
	
	private String binPath;
	private JacocoServices jacocoServices = new JacocoServices();
	private MemoryClassLoader memoryClassLoader;
	private IStrategyJunit strategy;

	public void setClassesPackages(Map<String, Package> classesPackage) {
		this.classesPackages = classesPackage;
	}
	
	public String getBinPath() {
		return binPath;
	}
	public void setBinPath(String binPath) {
		this.binPath = binPath;
	}
	
	public TestSuite getTestSuite() {
		return testSuite;
	}
	public void setTestSuite(TestSuite testSuite) {
		this.testSuite = testSuite;
	}
	
	public void setMemoryClassLoader(MemoryClassLoader memoryClassLoader) {
		this.memoryClassLoader = memoryClassLoader;
	}
	
	public IStrategyJunit getStrategy() {
		return this.strategy;
	}
	
	/**
	 * According to the type of input TestSuite chooses the strategy to be applied to:
	 * each source class of each package
	 * and to each test class 
	 * 
	 * @throws java.lang.Exception
	 */
	public void executeCoverage() throws Exception {
		
		List<Package> listSourcePackage = new ArrayList<Package>(classesPackages.values());
		
		for(int l = 0; l < listSourcePackage.size();l++) {
			Package pakage = listSourcePackage.get(l);
			List<IClass> classesName = pakage.getClasses();
		
			for(int c = 0 ; c < classesName.size();c++) {
				IClass clazz = classesName.get(c);
				String className = clazz.getFullName();
				@SuppressWarnings("unused")
				Class<?> clazzToLoad = memoryClassLoader.loadClass(className);	
				@SuppressWarnings("unchecked")
				Class<junit.framework.TestSuite> testSuiteClass = (Class<junit.framework.TestSuite>) memoryClassLoader.loadClass(testSuite.getFullName());	
				SuiteClasses suiteJunit = testSuiteClass.getAnnotation(SuiteClasses.class);
				
				if(suiteJunit == null) {
					this.strategy = new StrategyJunit3();
					this.strategy.setClassName(className);
					this.strategy.setClazz(clazz);
					this.strategy.setJacocoServices(jacocoServices);
					this.strategy.setTestSuite(testSuite);
					this.strategy.setTestSuiteClass(testSuiteClass);
					this.testSuite = strategy.executeCoverage();
					
				}else {
					this.strategy = new StrategyJunit4();
					this.strategy.setClassName(className);
					this.strategy.setClazz(clazz);
					this.strategy.setJacocoServices(jacocoServices);
					this.strategy.setTestSuite(testSuite);
					this.strategy.setTestSuiteClass(testSuiteClass);
					this.testSuite = strategy.executeCoverage();
				}
			}
		}
	}
}

