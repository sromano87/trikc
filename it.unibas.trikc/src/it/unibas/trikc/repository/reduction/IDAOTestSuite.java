package it.unibas.trikc.repository.reduction;

import it.unibas.trikc.modelEntity.TestSuite;
import it.unibas.trikc.repository.XMLException;

public interface IDAOTestSuite {
	
	public void save (TestSuite testSuite, String nameFile) throws XMLException;
	public TestSuite load(String nameFile)throws XMLException;
	public TestSuite loadForTest(String nameFile) throws XMLException;
	
}
