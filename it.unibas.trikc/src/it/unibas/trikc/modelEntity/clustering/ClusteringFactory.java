package it.unibas.trikc.modelEntity.clustering;

import it.unibas.trikc.Constants;

/**
 * This class builds the criterion of clustering selected by the user.
 * */
public class ClusteringFactory {
	
	private static ClusteringFactory singleton = new ClusteringFactory();
	private IStrategyClustering strategy;
	
	public static ClusteringFactory getInstance(){
		return singleton;
	}
	
	private ClusteringFactory(){
		
	}

	/**
	 * This method returns the clustering strategy chosen by the user.
	 * It takes as input a string value.
	 * @param type
	 * */

	public IStrategyClustering getClustering(String type){
		if(type.trim().equals(Constants.HIERARCHICAL_CLUSTERING)){
			strategy = new StrategyHierarchicalClustering();
		}
		
		return strategy; 
		
	}
}
