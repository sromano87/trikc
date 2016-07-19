package it.unibas.trikc
.repository.reduction;

import java.io.File;
import java.net.URL;
import java.util.logging.Logger;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import it.unibas.trikc.modelEntity.TestSuite;
import it.unibas.trikc.repository.XMLException;
import it.unibas.trikc.views.PageCoverage;

public class DAOXmlTestSuite implements IDAOTestSuite {
	private static Logger logger = Logger.getLogger(DAOXmlTestSuite.class.getName());
	
	@Override
	public void save(TestSuite testSuite, String nameFile) throws XMLException {
		
		URL location = PageCoverage.class.getProtectionDomain().getCodeSource().getLocation();
		StringBuilder path = new StringBuilder();
		path.append(location.getPath());
		path.append("storage");
		
		Serializer serializer = new Persister(); 
		//File result = new File("/storage/" + nameFile + ".xml"); 
		File result = new File(path.toString()+"/"+ nameFile + ".xml");
		logger.info("RISULTATO "+result.toString());
		try {
			serializer.write(testSuite, result);
		} catch (Exception e) {
			//e.printStackTrace();
			throw new XMLException(e);
		}
	}

	@Override
	public TestSuite load(String nameFile) throws XMLException {
		URL location = PageCoverage.class.getProtectionDomain().getCodeSource().getLocation();
		StringBuilder path = new StringBuilder();
		path.append(location.getPath());
		path.append("storage");
		Serializer serializer = new Persister(); 
		//File result = new File("./storage/" + nameFile + ".xml"); 
		File result = new File(path.toString()+"/" + nameFile + ".xml"); 
		TestSuite testSuite = new TestSuite(); 
		try {
			testSuite = serializer.read(TestSuite.class, result); 
		} catch (Exception e) {
			//e.printStackTrace();
			throw new XMLException(e);
		}
		return testSuite;
	}

}
