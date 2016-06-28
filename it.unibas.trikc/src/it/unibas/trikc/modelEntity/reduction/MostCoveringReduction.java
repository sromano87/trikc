package it.unibas.trikc.modelEntity.reduction;

import java.util.List;

import it.unibas.trikc.modelEntity.Cluster;
import it.unibas.trikc.modelEntity.TestSuite;

public class MostCoveringReduction implements IStrategyReduction{

	@Override
	public TestSuite reduceTestSuite(List<Cluster> clusters) {
		TestSuite testSuiteReduced = new TestSuite(); 
		
		return testSuiteReduced;
	}

}
