package it.unibas.trikc.modelEntity.clustering;

import it.unibas.trikc.Constants;
import it.unibas.trikc.modelEntity.Clusters;
import it.unibas.trikc.modelEntity.DissimilarityMatrix;
import it.unibas.trikc.modelEntity.clustering.algorithm.AverageLinkageStrategy;
import it.unibas.trikc.modelEntity.clustering.algorithm.ClusteringAlgorithm;
import it.unibas.trikc.modelEntity.clustering.algorithm.CompleteLinkageStrategy;
import it.unibas.trikc.modelEntity.clustering.algorithm.DefaultClusteringAlgorithm;
import it.unibas.trikc.modelEntity.clustering.algorithm.LinkageStrategy;
import it.unibas.trikc.modelEntity.clustering.algorithm.SingleLinkageStrategy;
import it.unibas.trikc.modelEntity.clustering.visitor.IVisitor;
import it.unibas.trikc.modelEntity.clustering.visitor.VisitorCluster;


public class StrategyHierarchicalClustering implements IStrategyClustering {
	
	
	/**
	 * This method create a Clusters with Hierarchical cluster algorithm.
	 * Input are a Dissimilarity Matrix, a level and a strategy linkage.
	 * A level is a double from 0 to 1. 
	 * Strategy linkage has three choice, Complete linkage strategy, Single linkage strategy and Average linkage strategy
	 *@return Clusters 
	 * 
	 * 
	 * 
	 */
	@Override
	public Clusters clusterTestCases(DissimilarityMatrix matrix, double level, String linkageStr) {
		
		String[] names = new String[matrix.getHeaders().size()];
		for(int m = 0; m < matrix.getHeaders().size(); m++){
			names[m] = matrix.getHeaders().get(m).getFullName();
		}

		double[][] distances = matrix.getMatrix(); 

		LinkageStrategy linkageStrategy = null; 
		if(linkageStr.equals(Constants.COMPLETE_LINKAGE_STRATEGY)){
			linkageStrategy = new CompleteLinkageStrategy(); 
		}else if(linkageStr.equals(Constants.SINGLE_LINKAGE_STRATEGY)){
			linkageStrategy = new SingleLinkageStrategy(); 
		}else{
			linkageStrategy = new AverageLinkageStrategy(); 
		}
		
		
		ClusteringAlgorithm alg = new DefaultClusteringAlgorithm();
		it.unibas.trikc.modelEntity.clustering.algorithm.Cluster cluster = alg.performClustering
				(distances, names, linkageStrategy); 
		cluster.toConsole(10);

		if(level == new Double(0)){
			level += 0.001;
		}
		
		IVisitor visitor = new VisitorCluster();
		visitor.setMatrix(matrix);
		visitor.setClusters(new Clusters());
		visitor.visit(cluster, level);

		System.out.println("Clustering completato");

		return visitor.getClusters();

	}
	
	/*public static void main(String[] args) throws Exception {
		DAOMockDissimilarityMatrix daoDM = new DAOMockDissimilarityMatrix();
		DissimilarityMatrix matrix = daoDM.load(null);
				
	    String[] names = new String[matrix.getHeaders().size()];
	    for(int m = 0; m < matrix.getHeaders().size(); m++){
			names[m] = matrix.getHeaders().get(m).getFullName();
		}
	    
	    double[][] distances = matrix.getMatrix(); 
	
	    ClusteringAlgorithm alg = new DefaultClusteringAlgorithm();
	    it.unibas.trikc.modelEntity.clustering.algorithm.Cluster cluster = alg.performClustering
	    		(distances, names, new CompleteLinkageStrategy());
		cluster.toConsole(10);
		
		double level = 0.6;
		if(level == new Double(0)){
			level += 0.001;
		}
		
		IVisitor visitor = new VisitorCluster();
		visitor.setMatrix(matrix);
		visitor.setClusters(new Clusters());
		visitor.visit(cluster, level);
		
		System.out.println("Clustering completato");
	}*/
	
	
}
