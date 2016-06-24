package it.unibas.trikc.coverage.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import it.unibas.trikc.coverage.Coverage;
import it.unibas.trikc.coverage.ICoverage;
import it.unibas.trikc.coverage.Loader;
import it.unibas.trikc.coverage.strategy.IStrategyJunit;
import it.unibas.trikc.coverage.strategy.StrategyJunit3;
import it.unibas.trikc.modelEntity.TestSuite;
import it.unibas.trikc.modelEntity.compositeClass.IClass;
import it.unibas.trikc.modelEntity.method.TestCase;
import it.unibas.trikc.modelEntity.Line;
import it.unibas.trikc.modelEntity.Package;

/**
 * @author Carmen
 *
 */
public class CoverageTestJunit3 {

	private ICoverage coverage;
	
	@Before
	public void setUp() {
		
		Loader.getInstance().setBinPath("C:\\Users\\Carmen\\workspace\\Hello\\bin");
		Loader.getInstance().setTestSuiteName("it.unibas.hello.test.TestSuite");
		ICoverage coverage = new Coverage();
		try {
			Loader.getInstance().load(coverage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.coverage = coverage;
	}
	
	@After
	public void tearDown() {
		Loader.getInstance().reset();
	}
	
	@Test
	public void testNumberOfSourceClassFound() {
		Map<String, Package> map = Loader.getInstance().getMapClassesPackages();
		List<Package> list = new ArrayList<Package>(map.values());
		List<IClass> totalClassesFound = new ArrayList<IClass>();
		for(Package p: list){
			List<IClass> classList = p.getClasses();
			totalClassesFound.addAll(classList);
		}
		assertTrue(totalClassesFound.size() == 2);
	}
	
	
	@Test
	public void testNumberOfTestCaseFound() {
		try {
			this.coverage.executeCoverage();
			TestSuite testSuite = coverage.getTestSuite();
			List<TestCase> totalTestCase = testSuite.getTestCases();
			assertTrue(totalTestCase.size() == 2);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testNumberOfLinesCoverade(){
		try {
			this.coverage.executeCoverage();
			List<Line> totalCoveredLine = new ArrayList<Line>();
			TestSuite testSuite = coverage.getTestSuite();
			List<TestCase> testCases = testSuite.getTestCases();
				for(TestCase tc : testCases){
				List <Line> line = tc.getCoveredLines();
				totalCoveredLine.addAll(line);			
			}
		assertTrue(totalCoveredLine.size() == 3);			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test 
	public void testNumberOfLinesCoveradeFromEveryTestCase() {
		try {
			this.coverage.executeCoverage();
			TestSuite testSuite = coverage.getTestSuite();
			List<TestCase> testCases = testSuite.getTestCases();
				for(TestCase tc : testCases){
					if(tc.getFullName().equals("it.unibas.hello.test.TestHelloImplJUnit3.testHello")) {
						assertTrue(tc.getCoveredLines().size() == 3);		
					} else if(tc.getFullName().equals("it.unibas.hello.test.TestHelloImplJUnit3.testEmpty")) {
						assertTrue(tc.getCoveredLines().isEmpty());	
					}
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void testStrategy() {
		try {
			this.coverage.executeCoverage();
			IStrategyJunit strategy = coverage.getStrategy();
			assertTrue(strategy instanceof StrategyJunit3);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void testEmptyTestSuite() {
		TestSuite testSuite = coverage.getTestSuite();
		assertTrue(testSuite.getTestCases().isEmpty());
	}
}
