package it.unibas.trikc.repository.reduction;

import it.unibas.trikc.modelEntity.Line;
import it.unibas.trikc.modelEntity.TestSuite;
import it.unibas.trikc.modelEntity.method.TestCase;

public class DAOMockTestSuite implements IDAOTestSuite {

	@Override
	public void save(TestSuite testSuite, String nameFile) {
			
	}

	@Override
	public TestSuite load(String nameFile) {
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
		
		testCase1.addCoveredLine(line1);
		testCase1.addCoveredLine(line2);
		testCase1.addCoveredLine(line3);
		
		testCase2.addCoveredLine(line4);
		testCase2.addCoveredLine(line5);
		
		testSuite.addTestCase(testCase1);
		testSuite.addTestCase(testCase2);
		
		return testSuite;
	}

}
