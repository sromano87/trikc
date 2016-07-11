package it.unibas.trikc.repository.dissimilarity;

import java.io.File;
import java.net.URL;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import it.unibas.trikc.modelEntity.DissimilarityMatrix;
import it.unibas.trikc.repository.XMLException;
import it.unibas.trikc.views.PageCoverage;

public class DAOXmlDissimilarityMatrix implements IDAODissimilarityMatrix {

	@Override
	public void save(DissimilarityMatrix matrix, String nameFile) throws XMLException {
		URL location = PageCoverage.class.getProtectionDomain().getCodeSource().getLocation();
		StringBuilder path = new StringBuilder();
		path.append(location.getPath());
		path.append("storage");
		
		Serializer serializer = new Persister(); 
//		File result = new File("/storage/" + nameFile + ".xml"); 
		File result = new File(path.toString()+"/" + nameFile + ".xml"); 
		try {
			serializer.write(matrix, result);
		} catch (Exception e) {
			//e.printStackTrace();
			throw new XMLException(e);
		}
	}

	@Override
	public DissimilarityMatrix load(String nameFile) throws XMLException{
		URL location = PageCoverage.class.getProtectionDomain().getCodeSource().getLocation();
		StringBuilder path = new StringBuilder();
		path.append(location.getPath());
		path.append("storage");
		Serializer serializer = new Persister(); 
		File result = new File(path.toString()+"/" + nameFile + ".xml"); 
//		File result = new File("/storage/" + nameFile + ".xml"); 
		DissimilarityMatrix matrix = null; 
		try {
			matrix = serializer.read(DissimilarityMatrix.class, result); 
		} catch (Exception e) {
			//e.printStackTrace();
			throw new XMLException(e);
		}
		return matrix;
	}
	
}
