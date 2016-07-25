package it.unibas.trikc.modelEntity.dissimilarity;

import it.unibas.trikc.Constants;


/**
 * This class builds the criterion of similarity selected by the user.
 * */
public class DissimilarityFactory {

	private static DissimilarityFactory singleton = new DissimilarityFactory();
	private IStrategyDissimilarity strategy;
	
	public static DissimilarityFactory getInstance(){
		return singleton;
	}
	
	private DissimilarityFactory(){
		
	}
	
	
		
	/**
	 * This method returns the dissimilarity strategy chosen by the user.
	 * It takes as input a string value.
	 * @param type
	 * */
	public IStrategyDissimilarity getDissimilarity(String type){
		if(type.trim().equals(Constants.STRING_KERNEL_DISSIMILARITY)){
			strategy = new StrategyStringKernelDissimilarity();
		}
		return strategy;
	}
}
