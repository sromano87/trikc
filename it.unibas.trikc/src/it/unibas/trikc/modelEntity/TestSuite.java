package it.unibas.trikc.modelEntity;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import it.unibas.trikc.modelEntity.method.TestCase;

/**
 * Class TestSuite
 *
 * @author TeamDataLayer
 */

@Root
public class TestSuite {

	@Element
	private String name;
	
	@ElementList
	private List<TestCase> testCases = new ArrayList<>();
	
	/**
	 * This method return a full name descriptor of Class
	 * @return name; 
	 */
	public String getFullName() {
		return name;
	}
	
	/**
	 * This method return a List of instance of type {@link TestCase}
	 * @return testCases
	 */
	public List<TestCase> getTestCases() {
		return testCases;
	}
	
	/**
	 * This method return a instance of {@link TestCase} at position index
	 * @param index
	 * @return TestCase
	 */
	public TestCase getTestCaseAt(int index) {
		return testCases.get(index);
	}

	/**
	  * This method set a class attribute
	  * @param name
	  */
	public void setFullName(String name) {
		this.name = name;
	}

	/**
	 * This method set a List TestCase
	 * @param testCases
	 */
	public void setTestCases(List<TestCase> testCases) {
		this.testCases = testCases;
	}
	
	/**
	 * This method add a element of type {@link TestCase}
	 * @param testCase
	 */
	public void addTestCase (TestCase testCase) {
		this.testCases.add(testCase);
	}
	
	@Override 
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("TestSuite: \n");
		stringBuilder.append(name);
		stringBuilder.append("\n");
		stringBuilder.append("List TestCase: " + testCases.size() + "\n");
		
		for(TestCase testCase : testCases) {
			stringBuilder.append("\t");
			stringBuilder.append(testCase.toString());
		}
		return stringBuilder.toString();
	}
}
