package it.unibas.trikc.coverage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.runners.Suite.SuiteClasses;

import it.unibas.trikc.coverage.strategy.IStrategyJunit;
import it.unibas.trikc.coverage.strategy.StrategyJunit3;
import it.unibas.trikc.coverage.strategy.StrategyJunit4;
import it.unibas.trikc.modelEntity.Package;
import it.unibas.trikc.modelEntity.TestSuite;
import it.unibas.trikc.modelEntity.compositeClass.IClass;

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

	/**
	 * @param classesPackages 
	 * 						map containing the packages of source classes
	 * */
	public void setClassesPackages(Map<String, Package> classesPackages) {
		this.classesPackages = classesPackages;
	}
	
	/**
	 * @return binPath 
	 * 				string representing the full path of the bin folder
	 * */
	public String getBinPath() {
		return binPath;
	}
	/**
	 * @param binPath 
	 * 				string representing the full path of the bin folder
	 * */
	public void setBinPath(String binPath) {
		this.binPath = binPath;
	}
	/**
	 * @return testSuite 
	 * 				the {@link TestSuite} initially empty which is filled after the coverage
	 * */
	public TestSuite getTestSuite() {
		return testSuite;
	}
	
	/**
	 * @param testSuite 
	 * 				the {@link TestSuite} initially empty which is filled after the coverage
	 * */
	public void setTestSuite(TestSuite testSuite) {
		this.testSuite = testSuite;
	}
	
	/**
	 * @param memoryClassLoader 
	 * 				the {@link MemoryClassLoader} to use to load the classes
	 * */
	public void setMemoryClassLoader(MemoryClassLoader memoryClassLoader) {
		this.memoryClassLoader = memoryClassLoader;
	}
	
	/**
	 * @return strategy 
	 * 				the type of {@link IStrategyJunit} used 
	 * */
	public IStrategyJunit getStrategy() {
		return this.strategy;
	}
	
	/**
	 * According to the input TestSuite type, chooses the strategy to be applied to:
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
				@SuppressWarnings("unchecked")
				Class<junit.framework.TestSuite> testSuiteClass = (Class<junit.framework.TestSuite>) memoryClassLoader.loadClass(testSuite.getFullName());	
				SuiteClasses suiteJunit = testSuiteClass.getAnnotation(SuiteClasses.class);
				
				if(suiteJunit == null) {
					this.strategy = new StrategyJunit3();
					this.strategy.setClazz(clazz);
					this.strategy.setJacocoServices(jacocoServices);
					this.strategy.setTestSuite(testSuite);
					this.strategy.setTestSuiteClass(testSuiteClass);
					this.testSuite = strategy.executeCoverage();
					
				} else {
					this.strategy = new StrategyJunit4();
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

