package it.unibas.trikc.repository.clusters;

import java.io.File;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import it.unibas.trikc.modelEntity.Clusters;

public class DAOXmlClusters implements IDAOClusters {

	@Override
	public void save(Clusters clusters, String nameFile) {
		Serializer serializer = new Persister(); 
		File result = new File("storage/" + nameFile + ".xml"); 
		try {
			serializer.write(clusters, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Clusters load(String nameFile) {
		Serializer serializer = new Persister(); 
		File result = new File("storage/" + nameFile + ".xml"); 
		Clusters clusters = new Clusters(); 
		try {
			clusters = serializer.read(Clusters.class, result); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return clusters;
	}

}
