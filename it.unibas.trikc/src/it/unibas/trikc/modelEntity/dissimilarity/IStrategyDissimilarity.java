package it.unibas.trikc.modelEntity.dissimilarity;

import it.unibas.trikc.modelEntity.DissimilarityMatrix;
import it.unibas.trikc.modelEntity.TestSuite;



public interface IStrategyDissimilarity {
	
	public DissimilarityMatrix computeDissimilarity(TestSuite ts);

}
