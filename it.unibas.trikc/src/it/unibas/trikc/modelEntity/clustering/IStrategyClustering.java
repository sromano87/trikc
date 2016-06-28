package it.unibas.trikc.modelEntity.clustering;

import java.util.List;

import it.unibas.trikc.modelEntity.Cluster;
import it.unibas.trikc.modelEntity.DissimilarityMatrix;

public interface IStrategyClustering {
	
	public List<Cluster> clusterTestCases(DissimilarityMatrix matrix);

}
