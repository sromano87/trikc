package it.unibas.trikc.modelEntity.clustering;

import it.unibas.trikc.modelEntity.Clusters;
import it.unibas.trikc.modelEntity.DissimilarityMatrix;

public interface IStrategyClustering {
	
	public Clusters clusterTestCases(DissimilarityMatrix matrix, double level, String linkageStrategy);

}
