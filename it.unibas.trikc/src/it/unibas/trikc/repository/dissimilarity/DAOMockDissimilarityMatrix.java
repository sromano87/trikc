package it.unibas.trikc.repository.dissimilarity;

import java.io.File;
import java.net.URL;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import it.unibas.trikc.modelEntity.DissimilarityMatrix;
import it.unibas.trikc.modelEntity.method.TestCase;
import it.unibas.trikc.repository.XMLException;
import it.unibas.trikc.views.PageCoverage;

public class DAOMockDissimilarityMatrix implements IDAODissimilarityMatrix {

	@Override
	public void save(DissimilarityMatrix matrix, String nameFile) throws XMLException{
		/*URL location = PageCoverage.class.getProtectionDomain().getCodeSource().getLocation();
		StringBuilder path = new StringBuilder();
		path.append(location.getPath());
		path.append("storage");*/
		
		Serializer serializer = new Persister(); 
		File result = new File("./storage/" + nameFile + ".xml"); 
		//File result = new File(path.toString()+"/" + nameFile + ".xml"); 
		try {
			serializer.write(matrix, result);
		} catch (Exception e) {
			//e.printStackTrace();
			throw new XMLException(e);
		}
	}

	@Override
	public DissimilarityMatrix load(String nameFile) throws XMLException{
		//URL location = PageCoverage.class.getProtectionDomain().getCodeSource().getLocation();
		//StringBuilder path = new StringBuilder();
		//path.append(location.getPath());
		//path.append("storage");
		Serializer serializer = new Persister(); 
		//File result = new File(path.toString()+"/" + nameFile + ".xml"); 
		File result = new File("./storage/" + nameFile + ".xml"); 
		DissimilarityMatrix matrix = null; 
		try {
			matrix = serializer.read(DissimilarityMatrix.class, result); 
		} catch (Exception e) {
			//e.printStackTrace();
			throw new XMLException(e);
		}
		return matrix;
		
		/*DissimilarityMatrix matrix = new DissimilarityMatrix(7);
		  
		  TestCase testCase1 = new TestCase();
		  TestCase testCase2 = new TestCase();
		  TestCase testCase3 = new TestCase();
		  TestCase testCase4 = new TestCase();
		  TestCase testCase5 = new TestCase();
		  TestCase testCase6 = new TestCase();
		  TestCase testCase7 = new TestCase();
		  
		  testCase1.setFullName("testCase1");
		  testCase2.setFullName("testCase2");
		  testCase3.setFullName("testCase3");
		  testCase4.setFullName("testCase4");
		  testCase5.setFullName("testCase5");
		  testCase6.setFullName("testCase6");
		  testCase7.setFullName("testCase7");
		  
		  matrix.addHeader(testCase1);
		  matrix.addHeader(testCase2);
		  matrix.addHeader(testCase3);
		  matrix.addHeader(testCase4);
		  matrix.addHeader(testCase5);
		  matrix.addHeader(testCase6);
		  matrix.addHeader(testCase7);

		  matrix.setSize(49);
		  
		  //riga 0
		  matrix.setValueAt(0, 0, 0);
		  matrix.setValueAt(0, 1, 0.5);
		  matrix.setValueAt(0, 2, 0.4286);
		  matrix.setValueAt(0, 3, 1.0);
		  matrix.setValueAt(0, 4, 0.25);
		  matrix.setValueAt(0, 5, 0.625);
		  matrix.setValueAt(0, 6, 0.375);
		    
		//riga 1
		  matrix.setValueAt(1, 0, 0.5);
		  matrix.setValueAt(1, 1, 0);
		  matrix.setValueAt(1, 2, 0.7143);
		  matrix.setValueAt(1, 3, 0.8333);
		  matrix.setValueAt(1, 4, 0.6667);
		  matrix.setValueAt(1, 5, 0.2);
		  matrix.setValueAt(1, 6, 0.7778);
		  
		//riga 2
		  matrix.setValueAt(2, 0, 0.4286);
		  matrix.setValueAt(2, 1, 0.7143);
		  matrix.setValueAt(2, 2, 0);
		  matrix.setValueAt(2, 3, 1);
		  matrix.setValueAt(2, 4, 0.4286);
		  matrix.setValueAt(2, 5, 0.6667);
		  matrix.setValueAt(2, 6, 0.3333);
		  
		//riga 3
		  matrix.setValueAt(3, 0, 1.0);
		  matrix.setValueAt(3, 1, 0.8333);
		  matrix.setValueAt(3, 2, 1.0);
		  matrix.setValueAt(3, 3, 0);
		  matrix.setValueAt(3, 4, 1.0);
		  matrix.setValueAt(3, 5, 0.8);
		  matrix.setValueAt(3, 6, 0.8571);
		  
		//riga 4
		  matrix.setValueAt(4, 0, 0.25);
		  matrix.setValueAt(4, 1, 0.6667);
		  matrix.setValueAt(4, 2, 0.4286);
		  matrix.setValueAt(4, 3, 1.0);
		  matrix.setValueAt(4, 4, 0);
		  matrix.setValueAt(4, 5, 0.7778);
		  matrix.setValueAt(4, 6, 0.3750);
		  
		//riga 5
		  matrix.setValueAt(5, 0, 0.625);
		  matrix.setValueAt(5, 1, 0.2);
		  matrix.setValueAt(5, 2, 0.6667);
		  matrix.setValueAt(5, 3, 0.8);
		  matrix.setValueAt(5, 4, 0.7778);
		  matrix.setValueAt(5, 5, 0);
		  matrix.setValueAt(5, 6, 0.75);
		  
		//riga 6
		  matrix.setValueAt(6, 0, 0.3750);
		  matrix.setValueAt(6, 1, 0.7778);
		  matrix.setValueAt(6, 2, 0.3333);
		  matrix.setValueAt(6, 3, 0.8571);
		  matrix.setValueAt(6, 4, 0.3750);
		  matrix.setValueAt(6, 5, 0.75);
		  matrix.setValueAt(6, 6, 0);
		  
		  
		  return matrix;
		*/
		
		
		/*DissimilarityMatrix matrix = new DissimilarityMatrix(4);
		
		TestCase testCase1 = new TestCase();
		TestCase testCase2 = new TestCase();
		TestCase testCase3 = new TestCase();
		TestCase testCase4 = new TestCase();
		
		testCase1.setFullName("testCase1");
		testCase2.setFullName("testCase2");
		testCase3.setFullName("testCase3");
		testCase4.setFullName("testCase4");
		
		matrix.addHeader(testCase1);
		matrix.addHeader(testCase2);
		matrix.addHeader(testCase3);
		matrix.addHeader(testCase4);
		
		matrix.setSize(4);
		
		double value = 0.1; 
		for (int i = 0; i < matrix.getSize(); i++) {
			for (int j = 0; j < matrix.getSize(); j++) {
				if (i < j) {
					matrix.setValueAt(i, j, value*j);
					matrix.setValueAt(j, i, value*j);
				} 
			}
			value += 0.1; 
		}
				
		
		return matrix;*/
	}
	
	

}
