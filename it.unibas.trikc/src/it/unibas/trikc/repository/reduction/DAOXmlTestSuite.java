package it.unibas.trikc
.repository.reduction;

import java.io.File;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import it.unibas.trikc.modelEntity.TestSuite;

public class DAOXmlTestSuite implements IDAOTestSuite {
	
	@Override
	public void save(TestSuite testSuite, String nameFile) {
		Serializer serializer = new Persister(); 
		File result = new File("storage/" + nameFile + ".xml"); 
		try {
			serializer.write(testSuite, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public TestSuite load(String nameFile) {
		Serializer serializer = new Persister(); 
		File result = new File("storage/" + nameFile + ".xml"); 
		TestSuite testSuite = new TestSuite(); 
		try {
			testSuite = serializer.read(TestSuite.class, result); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return testSuite;
	}

}
