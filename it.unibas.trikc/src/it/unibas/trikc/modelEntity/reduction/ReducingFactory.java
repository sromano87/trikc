package it.unibas.trikc.modelEntity.reduction;

import it.unibas.trikc.Constants;


/**
 * This class builds the criterion of reduction selected by the user.
 * */
public class ReducingFactory {

	private static ReducingFactory singleton = new ReducingFactory();
	private IStrategyReduction strategy;
	
	public static ReducingFactory getInstance(){
		return singleton;
	};
	
	private ReducingFactory(){
		
	}
	
	
	/**
	 * This method returns the reduction strategy chosen by the user.
	 * It takes as input a string value.
	 * @param type
	 * */
	public IStrategyReduction getReduction (String type){
		if(type.trim().equals(Constants.MOST_COVERING_REDUCTION)){
			strategy = new StrategyMostCoveringReduction();
		}
		return strategy; 
		
	}
	
	
	
}
