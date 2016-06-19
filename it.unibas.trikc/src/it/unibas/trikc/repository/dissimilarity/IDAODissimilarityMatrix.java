package it.unibas.trikc.repository.dissimilarity;

import it.unibas.trikc.modelEntity.DissimilarityMatrix;

public interface IDAODissimilarityMatrix {
	
	public void save (DissimilarityMatrix matrix, String nameFile);
	public DissimilarityMatrix load(String nameFile); 

}
