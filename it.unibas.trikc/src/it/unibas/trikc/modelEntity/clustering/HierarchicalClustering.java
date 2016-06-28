package it.unibas.trikc.modelEntity.clustering;

import java.util.ArrayList;
import java.util.List;

import it.unibas.trikc.modelEntity.Cluster;
import it.unibas.trikc.modelEntity.DissimilarityMatrix;

public class HierarchicalClustering implements IStrategyClustering {

	@Override
	public List<Cluster> clusterTestCases(DissimilarityMatrix matrix) {
		List<Cluster> clusters = new ArrayList<Cluster>(); 
		

		
		
		return clusters; 
	}

// ________________________________________________________	
//	CHIAMATA ALGORITMO ESTERNO
//    String[] names = new String[] { "O1", "O2", "O3", "O4", "O5", "O6" };
//    double[][] distances = matrix.getMatrix(); 
//
//    ClusteringAlgorithm alg = new DefaultClusteringAlgorithm();
//    it.unibas.trikc.modelEntity.clustering.algorithm.Cluster cluster = alg.performClustering
//    		(distances, names, new AverageLinkageStrategy());
	
}
