package it.unibas.trikc.modelEntity.dissimilarity;

import java.util.ArrayList;
import java.util.List;

import cc.mallet.types.StringKernel;
import it.unibas.trikc.modelEntity.DissimilarityMatrix;
import it.unibas.trikc.modelEntity.TestSuite;
import it.unibas.trikc.modelEntity.method.TestCase;


/**
 * The StringKernelDissimilarity class is a public class that provides methods for calculating 
 * dissimilarity between different Test-Cases. 
 * It implements the public interface IStrategyDissimilarity.
 * */
public class StrategyStringKernelDissimilarity implements IStrategyDissimilarity{

	private List<TestCase> testCases;
	
	
	/**
	 * This function calculates dissimilarity between two test cases of the same Test-Suite.
	 * In input this method requires a TestSuite Object and returns a DissimaryMatrix object.
	 * @param testSuite
	 * */
	@Override
	public DissimilarityMatrix computeDissimilarity(TestSuite testSuite) {
		testCases = testSuite.getTestCases();
		testCases = checkEmptyTestCase(testCases);
		//System.out.println("test case "+ testCases.size());
		DissimilarityMatrix dissimilarityMatrix = new DissimilarityMatrix(testCases.size());
		dissimilarityMatrix.setHeaders(testCases);
		double dissimilarity = 0;
		for (int i = 0; i < testCases.size(); i++) {
			for (int j = i; j < testCases.size() ; j++) {
				dissimilarity = compareTestCases(testCases.get(i),testCases.get(j));
				dissimilarityMatrix.setValueAt(i, j,dissimilarity);
				dissimilarityMatrix.setValueAt(j, i,dissimilarity);
				//System.out.println("-- sono in string kernel  " + i+","+j + "--> "+ dissimilarity);
			}
		}
		dissimilarityMatrix = normalizeMatrix(dissimilarityMatrix);
		return dissimilarityMatrix;
	}
	
	private DissimilarityMatrix normalizeMatrix(DissimilarityMatrix dissimilarityMatrix){
		double max = dissimilarityMatrix.getValueAt(0, 0);
		for (int i = 0; i < dissimilarityMatrix.getSize(); i++) {
			for (int j = i; j < dissimilarityMatrix.getSize() ; j++) {
				if(dissimilarityMatrix.getValueAt(i, j) > max){
					max = dissimilarityMatrix.getValueAt(i, j);
				}
			}
		}
		for (int i = 0; i < dissimilarityMatrix.getSize(); i++) {
			for (int j = i; j < dissimilarityMatrix.getSize() ; j++) {
				dissimilarityMatrix.setValueAt(i, j, (dissimilarityMatrix.getValueAt(i, j)/max));
			}
		}
		return dissimilarityMatrix; 
		
	}
	
	
	private List<TestCase> checkEmptyTestCase(List<TestCase> testCases2) {
		List <TestCase> backingTestCase = new ArrayList<>();
		for (TestCase testCase : testCases2) {
			if(testCase.getCoveredLines().size() != 0){
				backingTestCase.add(testCase);
			}
		}
		return backingTestCase;
	}

	
		/**
	 * this method compares each covered line of each test case.
	 * It returns the arithmetic average dissimilarity of each line.
	 * @param t1
	 * @param t2
	 */
	private double compareTestCases(TestCase t1, TestCase t2){
		StringKernel stringKernel = new StringKernel();
		TestCase a = new TestCase(); 
		if (t1.getCoveredLines().size() < t2.getCoveredLines().size()) {
			a = t2;  
			t2 = t1; 
			t1 = a; 
		}
		double [] dissimilarity = new double [t1.getCoveredLines().size()] ;
		double dissimilarityTrue = 1;
		 for (int i = 0; i < t1.getCoveredLines().size(); i++) {
			dissimilarityTrue = 1.0;
			for (int j = 0; j < t2.getCoveredLines().size(); j++) {
				double dissimilarityBacking = 1;
				dissimilarityBacking = (1-stringKernel.K(t1.getCoveredLineAt(i).getId(), t2.getCoveredLineAt(j).getId()));
				if(dissimilarityBacking < dissimilarityTrue){
					dissimilarityTrue = dissimilarityBacking;
					dissimilarity[i] = dissimilarityTrue;
				} 
			}
		 }
		 for (int i = 0; i < dissimilarity.length; i++) {
			dissimilarityTrue += dissimilarity[i];
		 }
		 return dissimilarityTrue/dissimilarity.length;
		
	}
	

}
