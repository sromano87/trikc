package it.unibas.trikc.modelEntity.dissimilarity;

import it.unibas.trikc.modelEntity.DissimilarityMatrix;
import it.unibas.trikc.modelEntity.TestSuite;

/**
 * A public interface with which you can choose the dissimilarity strategy. 
 * The only implementation is given by the function computeDissimilarity(TestSuite testSuite).
 * */

public interface IStrategyDissimilarity {
	
	public DissimilarityMatrix computeDissimilarity(TestSuite ts);

}
