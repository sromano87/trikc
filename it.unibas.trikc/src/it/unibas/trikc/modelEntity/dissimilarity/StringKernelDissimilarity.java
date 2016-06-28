package it.unibas.trikc.modelEntity.dissimilarity;

import java.util.List;

import cc.mallet.types.StringKernel;
import it.unibas.trikc.modelEntity.DissimilarityMatrix;
import it.unibas.trikc.modelEntity.TestSuite;
import it.unibas.trikc.modelEntity.method.TestCase;

public class StringKernelDissimilarity implements IStrategyDissimilarity{

	private List<TestCase> testCases ;
	
	@Override
	public DissimilarityMatrix computeDissimilarity(TestSuite testSuite) {
		DissimilarityMatrix dissimilarityMatrix = new DissimilarityMatrix();
		testCases = testSuite.getTestCases();
		dissimilarityMatrix.setHeaders(testCases);
		double dissimilarity = 0;
		for (int i = 0; i < testCases.size(); i++) {
			for (int j = i; j < testCases.size() ; j++) {
				dissimilarity = compareTestCases(testSuite.getTestCaseAt(i),testSuite.getTestCaseAt(j));
				dissimilarityMatrix.setValueAt(i, j,dissimilarity);
				dissimilarityMatrix.setValueAt(j, i,dissimilarity);
			}
		}
		return dissimilarityMatrix;
	}
	
	private double compareTestCases(TestCase t1, TestCase t2){
		StringKernel stringKernel = new StringKernel();
		double dissimilarity = 0;
		 for (int i = 0; i < t1.getCoveredLines().size(); i++) {
			for (int j = 0; j < t2.getCoveredLines().size(); j++) {
			 dissimilarity += (1-stringKernel.K(t1.getCoveredLineAt(i).getId(), t2.getCoveredLineAt(j).getId()) );
			}
		}
		return dissimilarity/(t1.getCoveredLines().size()*t2.getCoveredLines().size());
	}
	

}
