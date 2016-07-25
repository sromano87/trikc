package it.unibas.trikc.modelEntity.clustering;

import it.unibas.trikc.modelEntity.Clusters;
import it.unibas.trikc.modelEntity.DissimilarityMatrix;


/**
 * A public interface with which you can choose the clustering strategy. 
 * The only implementation is given by the function clusterTestCases(DissimilarityMatrix matrix).
 * */
public interface IStrategyClustering {
	
	public Clusters clusterTestCases(DissimilarityMatrix matrix, double level, String linkageStrategy);

}
