package it.unibas.trikc.repository.dissimilarity;

import java.io.File;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import it.unibas.trikc.modelEntity.DissimilarityMatrix;

public class DAOXmlDissimilarityMatrix implements IDAODissimilarityMatrix {

	@Override
	public void save(DissimilarityMatrix matrix, String nameFile) {
		Serializer serializer = new Persister(); 
		File result = new File("storage/" + nameFile + ".xml"); 
		try {
			serializer.write(matrix, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public DissimilarityMatrix load(String nameFile) {
		Serializer serializer = new Persister(); 
		File result = new File("storage/" + nameFile + ".xml"); 
		DissimilarityMatrix matrix = null; 
		try {
			matrix = serializer.read(DissimilarityMatrix.class, result); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return matrix;
	}
	
}
