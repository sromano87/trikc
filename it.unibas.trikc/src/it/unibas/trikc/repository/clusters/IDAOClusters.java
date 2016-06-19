package it.unibas.trikc.repository.clusters;

import it.unibas.trikc.modelEntity.Clusters;

public interface IDAOClusters {
	
	public void save (Clusters clusters, String nameFile); 
	public Clusters load (String nameFile); 

}
