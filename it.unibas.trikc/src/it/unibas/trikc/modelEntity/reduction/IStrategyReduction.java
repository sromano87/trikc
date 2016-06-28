package it.unibas.trikc.modelEntity.reduction;

import java.util.List;

import it.unibas.trikc.modelEntity.Cluster;
import it.unibas.trikc.modelEntity.TestSuite;

public interface IStrategyReduction {
	
	public TestSuite reduceTestSuite(List<Cluster> clusters);

}
