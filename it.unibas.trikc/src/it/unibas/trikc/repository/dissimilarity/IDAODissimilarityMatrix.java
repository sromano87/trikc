package it.unibas.trikc.repository.dissimilarity;

import it.unibas.trikc.modelEntity.DissimilarityMatrix;
import it.unibas.trikc.repository.XMLException;

public interface IDAODissimilarityMatrix {
	
	public void save (DissimilarityMatrix matrix, String nameFile) throws XMLException;
	public DissimilarityMatrix load(String nameFile) throws XMLException; 

}
