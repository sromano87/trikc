package it.unibas.trikc.modelEntity.reduction;

import it.unibas.trikc.modelEntity.Clusters;
import it.unibas.trikc.modelEntity.TestSuite;

public interface IStrategyReduction {
	
	public TestSuite reduceTestSuite(Clusters clusters);

}
