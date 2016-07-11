package it.unibas.trikc.modelEntity.clustering;

import it.unibas.trikc.modelEntity.Clusters;
import it.unibas.trikc.modelEntity.DissimilarityMatrix;
import it.unibas.trikc.modelEntity.clustering.algorithm.ClusteringAlgorithm;
import it.unibas.trikc.modelEntity.clustering.algorithm.CompleteLinkageStrategy;
import it.unibas.trikc.modelEntity.clustering.algorithm.DefaultClusteringAlgorithm;
import it.unibas.trikc.modelEntity.clustering.visitor.IVisitor;
import it.unibas.trikc.modelEntity.clustering.visitor.VisitorCluster;

public class StrategyHierarchicalClustering implements IStrategyClustering {
	
	@Override
	public Clusters clusterTestCases(DissimilarityMatrix matrix) {
		
		String[] names = new String[matrix.getHeaders().size()];
		for(int m = 0; m < matrix.getHeaders().size(); m++){
			names[m] = matrix.getHeaders().get(m).getFullName();
		}

		double[][] distances = matrix.getMatrix(); 

		ClusteringAlgorithm alg = new DefaultClusteringAlgorithm();
		it.unibas.trikc.modelEntity.clustering.algorithm.Cluster cluster = alg.performClustering
				(distances, names, new CompleteLinkageStrategy()); //AverageLinkageStrategy()
		cluster.toConsole(10);

		IVisitor visitor = new VisitorCluster();
		visitor.setMatrix(matrix);
		visitor.setClusters(new Clusters());
		visitor.visit(cluster);

		System.out.println("Clustering completato");

		return visitor.getClusters();

	}
	
//	public static void main(String[] args) throws Exception {
//		DAOMockDissimilarityMatrix daoDM = new DAOMockDissimilarityMatrix();
//		DissimilarityMatrix matrix = daoDM.load(null);
//				
//	    String[] names = new String[matrix.getHeaders().size()];
//	    for(int m = 0; m < matrix.getHeaders().size(); m++){
//			names[m] = matrix.getHeaders().get(m).getFullName();
//		}
//	    
//	    double[][] distances = matrix.getMatrix(); 
//	
//	    ClusteringAlgorithm alg = new DefaultClusteringAlgorithm();
//	    it.unibas.trikc.modelEntity.clustering.algorithm.Cluster cluster = alg.performClustering
//	    		(distances, names, new CompleteLinkageStrategy()); //AverageLinkageStrategy()
//		cluster.toConsole(10);
//		
//		IVisitor visitor = new VisitorCluster();
//		visitor.setMatrix(matrix);
//		visitor.setClusters(new Clusters());
//		visitor.visit(cluster);
//		
//		System.out.println("Clustering completato");
//	}
	
	
}
