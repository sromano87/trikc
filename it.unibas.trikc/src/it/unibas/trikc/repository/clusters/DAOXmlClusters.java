package it.unibas.trikc.repository.clusters;

import java.io.File;
import java.net.URL;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import it.unibas.trikc.modelEntity.Clusters;
import it.unibas.trikc.repository.XMLException;
import it.unibas.trikc.views.PageCoverage;

public class DAOXmlClusters implements IDAOClusters {

	@Override
	public void save(Clusters clusters, String nameFile) throws XMLException{
		URL location = PageCoverage.class.getProtectionDomain().getCodeSource().getLocation();
		StringBuilder path = new StringBuilder();
		path.append(location.getPath());
		path.append("storage");
		Serializer serializer = new Persister(); 
//		File result = new File("/storage/" + nameFile + ".xml"); 
		File result = new File(path.toString()+"/" + nameFile + ".xml"); 
		try {
			serializer.write(clusters, result);
		} catch (Exception e) {
			//e.printStackTrace();
			throw new XMLException(e);
		}
	}

	@Override
	public Clusters load(String nameFile) throws XMLException{
		URL location = PageCoverage.class.getProtectionDomain().getCodeSource().getLocation();
		StringBuilder path = new StringBuilder();
		path.append(location.getPath());
		path.append("storage");
		Serializer serializer = new Persister(); 
		//File result = new File("/storage/" + nameFile + ".xml"); 
		File result = new File(path.toString()+"/" + nameFile + ".xml"); 
		Clusters clusters = new Clusters(); 
		try {
			clusters = serializer.read(Clusters.class, result); 
		} catch (Exception e) {
			//e.printStackTrace();
			throw new XMLException(e);
		}
		return clusters;
	}

}
