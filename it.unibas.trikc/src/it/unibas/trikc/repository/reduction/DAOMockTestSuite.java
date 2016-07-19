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
		Serializer serializer = new Persister(); 
		File result = new File("./storage/" + nameFile + ".xml"); 
		//File result = new File(path.toString()+"/"+ nameFile + ".xml");
		try {
			serializer.write(testSuite, result);
		} catch (Exception e) {
			//e.printStackTrace();
			throw new XMLException(e);
		}
	}

	@Override
	public TestSuite load(String nameFile) throws XMLException{
		Serializer serializer = new Persister(); 
		//File result = new File(path.toString()+"/" + nameFile + ".xml"); 
		File result = new File("./storage/" + nameFile + ".xml"); 
		TestSuite ts  = null; 
		try {
			ts = serializer.read(TestSuite.class, result); 
		} catch (Exception e) {
			//e.printStackTrace();
			throw new XMLException(e);
		}
		return ts;
		
		/*
		TestSuite testSuite = new TestSuite();
		testSuite.setFullName("TestSuite1");
		
		TestCase testCase1 = new TestCase();
		TestCase testCase2 = new TestCase();
		
		testCase1.setFullName("testCase1");
		testCase2.setFullName("testCase2");
		
		Line line1 = new Line(100, "it.unibas.mediapesata.modello.Esame.getVoto()I.100"); 
		Line line2 = new Line(200, "it.unibas.mediapesata.modello.Esame.getVoto()I.200"); 
		Line line3 = new Line(300, "it.unibas.mediapesata.modello.Esame.getVoto()I.300"); 
		Line line4 = new Line(400, "it.unibas.mediapesata.modello.Esame.getVoto()I.400"); 
		Line line5 = new Line(500, "it.unibas.mediapesata.modello.Esame.getVoto()I.500");
		
		Sut sut = new Sut(); 
		sut.setFullName("sut.it.unibas.mediapesata.modello");
		
		Package myPackage = new Package(); 
		myPackage.setFullName("it.unibas.mediapesata.modello");
		myPackage.setMySut(sut);
		
		CompositeClass myClass = new CompositeClass(); 
		myClass.setFullName("Esame");
		myClass.setPackage(myPackage);
		
		MyMethod method1 = new MyMethod(); 
		method1.setFullName("getVoto");
		method1.setDescriptor("getVoto()I");
		method1.setMyClass(myClass);
		
		line1.setMyMethod(method1);
		line2.setMyMethod(method1);
		line3.setMyMethod(method1);
		line4.setMyMethod(method1);
		line5.setMyMethod(method1);
		
		testCase1.addCoveredLine(line1);
		testCase1.addCoveredLine(line2);
		testCase1.addCoveredLine(line3);
		
		testCase2.addCoveredLine(line4);
		testCase2.addCoveredLine(line5);
		
		testSuite.addTestCase(testCase1);
		testSuite.addTestCase(testCase2);
		
		return testSuite;
		*/
	}

}
