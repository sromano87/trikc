package it.unibas.trikc
.repository.reduction;

import java.io.File;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import it.unibas.trikc.modelEntity.TestSuite;
import it.unibas.trikc.repository.XMLException;

public class DAOXmlTestSuite implements IDAOTestSuite {
	
	@Override
	public void save(TestSuite testSuite, String nameFile) throws XMLException {
		String path = System.getProperty("user.home"); 
		path = path + "/.TRIKC/"; 
		File dir = new File(path); 
		if (!dir.exists()) {
			dir.mkdirs(); 
		}
		Serializer serializer = new Persister(); 
		File result = new File(path + nameFile + ".xml");
		
		try {
			serializer.write(testSuite, result);
		} catch (Exception e) {
			throw new XMLException(e);
		}
	}

	@Override
	public TestSuite load(String nameFile) throws XMLException {
		String path = System.getProperty("user.home"); 
		path = path + "/.TRIKC/";
		File dir = new File(path); 
		if (!dir.exists()) {
			dir.mkdirs(); 
		}
		Serializer serializer = new Persister(); 
		File result = new File(path + nameFile + ".xml"); 
		
		TestSuite testSuite = new TestSuite(); 
		try {
			testSuite = serializer.read(TestSuite.class, result); 
		} catch (Exception e) {
			throw new XMLException(e);
		}
		return testSuite;
	}
	
	
	public boolean isWindows(String os) {
		return os.toLowerCase().contains("win");
	}
	
	public boolean isMac(String os) {
		return os.toLowerCase().contains("mac");
	}
	
	
	
	@Override
	public TestSuite loadForTest(String nameFile) throws XMLException {
		String path = System.getProperty("user.dir"); 
		if (isWindows(System.getProperty("os.name"))) {
			path = path + "\\test_resources\\xml_resources\\";
		} else if (isMac(System.getProperty("os.name"))) {
			path = path + "/test_resources/xml_resources/";
		}
		Serializer serializer = new Persister(); 
		File result = new File(path + nameFile + ".xml"); 
		
		TestSuite testSuite = new TestSuite(); 
		try {
			testSuite = serializer.read(TestSuite.class, result); 
		} catch (Exception e) {
			throw new XMLException(e);
		}
		return testSuite;
	}

}
