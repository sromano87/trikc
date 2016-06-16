package it.unibas.trikc.modelEntity;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import it.unibas.trikc.modelEntity.method.TestCase;

@Root
public class TestSuite {

	@Element
	private String name;
	
	@ElementList
	private List<TestCase> testCases = new ArrayList<>();
	
	public String getFullName() {
		return name;
	}
	
	public List<TestCase> getTestCases() {
		return testCases;
	}
	
	public TestCase getTestCaseAt(int index) {
		return testCases.get(index);
	}

	public void setFullName(String name) {
		this.name = name;
	}

	public void setTestCases(List<TestCase> testCases) {
		this.testCases = testCases;
	}
	
	public void addTestCase (TestCase testCase) {
		this.testCases.add(testCase);
	}
	
}
