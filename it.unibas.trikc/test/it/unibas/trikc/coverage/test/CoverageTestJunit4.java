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
import it.unibas.trikc.coverage.strategy.StrategyJunit4;
import it.unibas.trikc.modelEntity.TestSuite;
import it.unibas.trikc.modelEntity.compositeClass.IClass;
import it.unibas.trikc.modelEntity.method.TestCase;
import it.unibas.trikc.modelEntity.Line;
import it.unibas.trikc.modelEntity.Package;

public class CoverageTestJunit4 {

	private ICoverage coverage;
	
	@Before
	public void setUp() {
		
		Loader.getInstance().setBinPath("C:\\Users\\Carmen\\workspace\\marsrover\\bin");
		Loader.getInstance().setTestSuiteName("it.unibas.marsrover.test.TestSuite");
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
		assertTrue(totalClassesFound.size() == 3);
	}
	
	
	@Test
	public void testNumberOfTestCaseFound() {
		try {
			this.coverage.executeCoverage();
			TestSuite testSuite = coverage.getTestSuite();
			List<TestCase> totalTestCase = testSuite.getTestCases();
			assertTrue(totalTestCase.size() == 6);
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
			assertTrue(totalCoveredLine.size() == 217);			
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
					if(tc.getFullName().equals("it.unibas.marsrover.test.MarsRoverTest.testLanding") || tc.getFullName().equals("it.unibas.marsrover.test.CellTest.testLanding")) {
						assertTrue(tc.getCoveredLines().size() == 39);		
					} else if(tc.getFullName().equals("it.unibas.marsrover.test.MarsRoverTest.testTurning")) {
						assertTrue(tc.getCoveredLines().size() == 42);
					} else if(tc.getFullName().equals("it.unibas.marsrover.test.MarsRoverTest.testMovingForward")) {
						assertTrue(tc.getCoveredLines().size() == 44);
					} else if(tc.getFullName().equals("it.unibas.marsrover.test.MarsRoverTest.testMovingAround")) {
						assertTrue(tc.getCoveredLines().size() == 53);
					} else if(tc.getFullName().equals("it.unibas.marsrover.test.CellTest.testEmpty")) {
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
			assertTrue(strategy instanceof StrategyJunit4);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void testEmptyTestSuite() {
		TestSuite testSuite = coverage.getTestSuite();
		assertTrue(testSuite.getTestCases().isEmpty());
	}
}
