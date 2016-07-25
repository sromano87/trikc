package it.unibas.trikc.modelEntity;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import it.unibas.trikc.modelEntity.method.TestCase;

/**
 * Class Cluster
 *
 * @author TeamDataLayer
 */

@Root
public class Cluster {
	
	@ElementList
	private List<TestCase> testCases = new ArrayList<TestCase>();
	
	/**
	  * This is no-arg constructor
	  */
	public Cluster () {}

	/**
	 * This method return a List of coveredLines of {@link TestCase}
	 * @return testCases
	 */
	public List<TestCase> getTestCases() {
		return testCases;
	}

	/**
	 * This method set a testCases
	 * @param testCases
	 */
	public void setTestCases(List<TestCase> testCases) {
		this.testCases = testCases;
	} 
	
	/**
	 * This method add a testCase of type {@link TestCase}
	 * @param testCase
	 */
	public void addTestCase(TestCase testCase){
		this.testCases.add(testCase);
	}
	
}