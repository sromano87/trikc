package it.unibas.trikc.repository.reduction;

import java.io.File;
import java.net.URL;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import it.unibas.trikc.modelEntity.Clusters;
import it.unibas.trikc.modelEntity.Line;
import it.unibas.trikc.modelEntity.Package;
import it.unibas.trikc.modelEntity.Sut;
import it.unibas.trikc.modelEntity.TestSuite;
import it.unibas.trikc.modelEntity.compositeClass.CompositeClass;
import it.unibas.trikc.modelEntity.compositeClass.LeafNestedClass;
import it.unibas.trikc.modelEntity.method.MyMethod;
import it.unibas.trikc.modelEntity.method.TestCase;
import it.unibas.trikc.repository.XMLException;
import it.unibas.trikc.views.PageCoverage;

public class DAOMockTestSuite implements IDAOTestSuite {

	@Override
	public void save(TestSuite testSuite, String nameFile) throws XMLException{
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
	public TestSuite load(String nameFile) throws XMLException{
		String path = System.getProperty("user.home"); 
		path = path + "/.TRIKC/";
		File dir = new File(path); 
		if (!dir.exists()) {
			dir.mkdirs(); 
		}
		Serializer serializer = new Persister(); 
		File result = new File(path + nameFile + ".xml"); 
		
		TestSuite ts  = null; 
		try {
			ts = serializer.read(TestSuite.class, result); 
		} catch (Exception e) {
			throw new XMLException(e);
		}
		return ts;
		
	}

}
