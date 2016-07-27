package it.unibas.trikc.repository.clusters;

import it.unibas.trikc.modelEntity.Clusters;
import it.unibas.trikc.repository.XMLException;

public interface IDAOClusters {
	
	public void save (Clusters clusters, String nameFile) throws XMLException; 
	public Clusters load (String nameFile)throws XMLException;
	public Clusters loadForTest(String nameFile) throws XMLException; 

}
