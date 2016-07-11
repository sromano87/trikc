package it.unibas.trikc.modelEntity.clustering;

import it.unibas.trikc.Constants;

public class ClusteringFactory {
	
	private static ClusteringFactory singleton = new ClusteringFactory();
	private IStrategyClustering strategy;
	
	public static ClusteringFactory getInstance(){
		return singleton;
	}
	
	private ClusteringFactory(){
		
	}

	public IStrategyClustering getClustering(String type){
		if(type.trim().equals(Constants.HIERARCHICAL_CLUSTERING)){
			strategy = new StrategyHierarchicalClustering();
		}
		
		return strategy; 
		
	}
}
