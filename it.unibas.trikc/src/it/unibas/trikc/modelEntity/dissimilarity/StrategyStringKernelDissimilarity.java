package it.unibas.trikc.modelEntity.dissimilarity;

import java.util.ArrayList;
import java.util.List;

import cc.mallet.types.StringKernel;
import it.unibas.trikc.modelEntity.DissimilarityMatrix;
import it.unibas.trikc.modelEntity.TestSuite;
import it.unibas.trikc.modelEntity.method.TestCase;

public class StrategyStringKernelDissimilarity implements IStrategyDissimilarity{

	private List<TestCase> testCases ;
	
	@Override
	public DissimilarityMatrix computeDissimilarity(TestSuite testSuite) {
		testCases = testSuite.getTestCases();
		testCases = checkEmptyTestCase(testCases);
		DissimilarityMatrix dissimilarityMatrix = new DissimilarityMatrix(testCases.size());
		dissimilarityMatrix.setHeaders(testCases);
		double dissimilarity = 0;
		for (int i = 0; i < testCases.size(); i++) {
			for (int j = i; j < testCases.size() ; j++) {
				dissimilarity = compareTestCases(testCases.get(i),testCases.get(j));
				dissimilarityMatrix.setValueAt(i, j,dissimilarity);
				dissimilarityMatrix.setValueAt(j, i,dissimilarity);
				
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
