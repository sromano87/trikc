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
		Serializer serializer = new Persister(); 
		File result = new File("./storage/" + nameFile + ".xml"); 
		//File result = new File(path.toString()+"/" + nameFile + ".xml"); 
		try {
			serializer.write(clusters, result);
		} catch (Exception e) {
			//e.printStackTrace();
			throw new XMLException(e);
		}
	}

	@Override
	public Clusters load(String nameFile) throws XMLException{
		
		Serializer serializer = new Persister(); 
		//File result = new File(path.toString()+"/" + nameFile + ".xml"); 
		File result = new File("./storage/" + nameFile + ".xml"); 
		Clusters clusters  = null; 
		try {
			clusters = serializer.read(Clusters.class, result); 
		} catch (Exception e) {
			//e.printStackTrace();
			throw new XMLException(e);
		}
		return clusters;
		/*Clusters clusters = new Clusters(); 
		
		TestCase testCase1 = new TestCase();
		TestCase testCase2 = new TestCase();
		TestCase testCase3 = new TestCase();
		TestCase testCase4 = new TestCase();
		
		testCase1.setFullName("testCase1");
		testCase2.setFullName("testCase2");
		testCase3.setFullName("testCase3");
		testCase4.setFullName("testCase4");

		List<TestCase> cluster1 = new ArrayList<>(); 
		cluster1.add(testCase1); 
		cluster1.add(testCase2);
		
		
		List<TestCase> cluster2 = new ArrayList<>(); 
		cluster2.add(testCase3); 
		cluster2.add(testCase4);
		
		clusters.setCluster("Cluster1", cluster1);
		clusters.setCluster("Cluster2", cluster2);
		
		return clusters;*/
	}

}
