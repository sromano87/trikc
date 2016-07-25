package it.unibas.trikc.modelEntity.reduction;

import it.unibas.trikc.modelEntity.Clusters;
import it.unibas.trikc.modelEntity.TestSuite;


/**
 * A public interface with which you can choose the reduction strategy. 
 * The only implementation is given by the function generateNewTestSuite(Map clusters).
 * */
public interface IStrategyReduction {
	
	public TestSuite reduceTestSuite(Clusters clusters);

}
