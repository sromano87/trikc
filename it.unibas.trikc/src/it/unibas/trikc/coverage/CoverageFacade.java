package it.unibas.trikc.coverage;

import it.unibas.trikc.repository.reduction.DAOXmlTestSuite;
import it.unibas.trikc.repository.reduction.IDAOTestSuite;

/**
 * Instance of the facade pattern.
 * It exposes a simple interface to access complex subsystems
 * 
 * 
 * @author TeamCoverage
 * @see it.unibas.trikc.coverage.Coverage
 * @see it.unibas.trikc.coverage.Loader
 */
public class CoverageFacade {

	private ICoverage coverage;
	
	/**
	 * It performs all the necessary operations to execute the SUT coverage
	 * 
	 * @param binPath
	 *            absolute bin folder path of the SUT
	 * @param testSuite
	 *            fully-qualified name of the TestSuite to be analyzed
	 *            
	 * @throws java.lang.Exception
	 */
	public void runCoverage(String binPath, String testSuite, String testPath, String libPath) throws Exception {
		
		ICoverage coverage = new Coverage();
		
		Loader loader = Loader.getInstance();
		loader.setBinPath(binPath);
		loader.setTestSuiteName(testSuite);
		loader.setTestPath(testPath);
		loader.setLibPath(libPath);
		loader.load(coverage);
		
		this.coverage = coverage;
		this.coverage.executeCoverage();
		
		System.out.println("TEST SUITE: " + this.coverage.getTestSuite().toString());
		
		IDAOTestSuite dao = new DAOXmlTestSuite();
		dao.save(coverage.getTestSuite(), "testSuiteCoverage_" + testSuite);
		
	}

	public ICoverage getCoverage() {
		return coverage;
	}

	public void setCoverage(ICoverage coverage) {
		this.coverage = coverage;
	}
	
}
