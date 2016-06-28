package it.unibas.trikc.modelEntity.dissimilarity;

import it.unibas.trikc.Constants;

public class DissimilarityFactory {

	private static DissimilarityFactory singleton = new DissimilarityFactory();
	private IStrategyDissimilarity strategy;
	
	public static DissimilarityFactory getInstance(){
		return singleton;
	}
	
	private DissimilarityFactory(){
		
	}
	
	public IStrategyDissimilarity getDissimilarity(String type){
		if(type.trim().equals(Constants.STRING_KERNEL_DISSIMILARITY)){
			strategy = new StringKernelDissimilarity();
		}
		return strategy;
	}
}
