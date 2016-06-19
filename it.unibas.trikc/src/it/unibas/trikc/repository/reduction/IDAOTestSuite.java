package it.unibas.trikc.repository.reduction;

import it.unibas.trikc.modelEntity.TestSuite;

public interface IDAOTestSuite {
	
	public void save (TestSuite testSuite, String nameFile);
	public TestSuite load(String nameFile);

}
