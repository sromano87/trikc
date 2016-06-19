package it.unibas.trikc.repository.dissimilarity;

import it.unibas.trikc.modelEntity.DissimilarityMatrix;
import it.unibas.trikc.modelEntity.method.TestCase;

public class DAOMockDissimilarityMatrix implements IDAODissimilarityMatrix {

	@Override
	public void save(DissimilarityMatrix matrix, String nameFile) {
		
	}

	@Override
	public DissimilarityMatrix load(String nameFile) {
		DissimilarityMatrix matrix = new DissimilarityMatrix(4);
		
		TestCase testCase1 = new TestCase();
		TestCase testCase2 = new TestCase();
		TestCase testCase3 = new TestCase();
		TestCase testCase4 = new TestCase();
		
		testCase1.setFullName("testCase1");
		testCase2.setFullName("testCase2");
		testCase3.setFullName("testCase3");
		testCase4.setFullName("testCase4");
		
		matrix.addHeader(testCase1);
		matrix.addHeader(testCase2);
		matrix.addHeader(testCase3);
		matrix.addHeader(testCase4);
		
		matrix.setSize(4);
		
		double value = 0.1; 
		for (int i = 0; i < matrix.getSize(); i++) {
			for (int j = 0; j < matrix.getSize(); j++) {
				if (i < j) {
					matrix.setValueAt(i, j, value*j);
					matrix.setValueAt(j, i, value*j);
				} 
			}
			value += 0.1; 
		}
				
		
		return matrix;
	}
	
	

}
