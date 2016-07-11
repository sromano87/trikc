package it.unibas.trikc.modelEntity.reduction;

import it.unibas.trikc.Constants;

public class ReducingFactory {

	private static ReducingFactory singleton = new ReducingFactory();
	private IStrategyReduction strategy;
	
	public static ReducingFactory getInstance(){
		return singleton;
	};
	
	private ReducingFactory(){
		
	}
	
	public IStrategyReduction getReduction (String type){
		if(type.trim().equals(Constants.MOST_COVERING_REDUCTION)){
			strategy = new StrategyMostCoveringReduction();
		}
		return strategy; 
		
	}
	
	
	
}
