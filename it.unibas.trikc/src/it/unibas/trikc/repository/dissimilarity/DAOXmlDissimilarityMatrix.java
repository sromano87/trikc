package it.unibas.trikc.repository.dissimilarity;

import java.io.File;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import it.unibas.trikc.modelEntity.DissimilarityMatrix;
import it.unibas.trikc.repository.XMLException;


public class DAOXmlDissimilarityMatrix implements IDAODissimilarityMatrix {

	@Override
	public void save(DissimilarityMatrix matrix, String nameFile) throws XMLException {
		String path = System.getProperty("user.home"); 
		path = path + "/.TRIKC/";
		File dir = new File(path); 
		if (!dir.exists()) {
			dir.mkdirs(); 
		} 
		Serializer serializer = new Persister(); 
		File result = new File(path + nameFile + ".xml"); 
		
		try {
			serializer.write(matrix, result);
		} catch (Exception e) {
			throw new XMLException(e);
		}
	}

	@Override
	public DissimilarityMatrix load(String nameFile) throws XMLException{
		String path = System.getProperty("user.home"); 
		path = path + "/.TRIKC/";
		File dir = new File(path); 
		if (!dir.exists()) {
			dir.mkdirs(); 
		}
		Serializer serializer = new Persister(); 
		File result = new File(path + nameFile + ".xml"); 
		
		DissimilarityMatrix matrix = null; 
		try {
			matrix = serializer.read(DissimilarityMatrix.class, result); 
		} catch (Exception e) {
			throw new XMLException(e);
		}
		return matrix;
	}
	
	
	public boolean isWindows(String os) {
		return os.toLowerCase().contains("win");
	}
	
	public boolean isMac(String os) {
		return os.toLowerCase().contains("mac");
	}
	
	
	
	@Override
	public DissimilarityMatrix loadForTest(String nameFile) throws XMLException {
		String path = System.getProperty("user.dir"); 
		if (isWindows(System.getProperty("os.name"))) {
			path = path + "\\test_resources\\xml_resources\\";
		} else if (isMac(System.getProperty("os.name"))) {
			path = path + "/test_resources/xml_resources/";
		}
		Serializer serializer = new Persister(); 
		File result = new File(path + nameFile + ".xml"); 
		
		DissimilarityMatrix matrix = null; 
		try {
			matrix = serializer.read(DissimilarityMatrix.class, result); 
		} catch (Exception e) {
			throw new XMLException(e);
		}
		return matrix;
	}
}
