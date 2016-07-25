package it.unibas.trikc.repository.clusters;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import it.unibas.trikc.modelEntity.Clusters;
import it.unibas.trikc.modelEntity.DissimilarityMatrix;
import it.unibas.trikc.modelEntity.method.TestCase;
import it.unibas.trikc.repository.XMLException;

public class DAOMockClusters implements IDAOClusters {

	@Override
	public void save(Clusters clusters, String nameFile) throws XMLException{
		String path = System.getProperty("user.home"); 
		path = path + "/.TRIKC/";
		File dir = new File(path); 
		if (!dir.exists()) {
			dir.mkdirs(); 
		}
		Serializer serializer = new Persister(); 
		File result = new File(path + nameFile + ".xml"); 
		
		try {
			serializer.write(clusters, result);
		} catch (Exception e) {
			throw new XMLException(e);
		}
	}

	@Override
	public Clusters load(String nameFile) throws XMLException{
		String path = System.getProperty("user.home"); 
		path = path + "/.TRIKC/"; 
		File dir = new File(path); 
		if (!dir.exists()) {
			dir.mkdirs(); 
		}
		Serializer serializer = new Persister(); 
		File result = new File(path + nameFile + ".xml"); 
		
		Clusters clusters  = null; 
		try {
			clusters = serializer.read(Clusters.class, result); 
		} catch (Exception e) {
			throw new XMLException(e);
		}
		return clusters;
	}

}
