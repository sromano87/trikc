package it.unibas.trikc.modelEntity.dissimilarity;

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
		DissimilarityMatrix dissimilarityMatrix = new DissimilarityMatrix(testCases.size());
		dissimilarityMatrix.setHeaders(testCases);
		double dissimilarity = 0;
		for (int i = 0; i < testCases.size(); i++) {
			for (int j = i; j < testCases.size() ; j++) {
				dissimilarity = compareTestCases(testSuite.getTestCaseAt(i),testSuite.getTestCaseAt(j));
				dissimilarityMatrix.setValueAt(i, j,dissimilarity);
				dissimilarityMatrix.setValueAt(j, i,dissimilarity);
			//	System.out.println("In compute dissimilarity " + dissimilarity);
			  //System.out.println("In compute dissimilarity valore a " + i + j +" "+  dissimilarityMatrix.getValueAt(i, j));
				
			}
		}
		return dissimilarityMatrix;
	}
	
	private double compareTestCases(TestCase t1, TestCase t2){
		StringKernel stringKernel = new StringKernel();
		double [] dissimilarity = new double [t1.getCoveredLines().size()] ;
		double dissimilarityTrue = 1;
		 for (int i = 0; i < t1.getCoveredLines().size(); i++) {
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
