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
	public void runCoverage(String binPath, String testSuite) throws Exception {
	
		ICoverage coverage = new Coverage();
		
		Loader loader = Loader.getInstance();
		loader.setBinPath(binPath);
		loader.setTestSuiteName(testSuite);
		loader.load(coverage);
		
		this.coverage = coverage;
		this.coverage.executeCoverage();

		IDAOTestSuite dao = new DAOXmlTestSuite();
		dao.save(coverage.getTestSuite(), "testSuiteCoverage_" + testSuite);
		System.out.println(coverage.getTestSuite().toString());

	}

	public ICoverage getCoverage() {
		return coverage;
	}

	public void setCoverage(ICoverage coverage) {
		this.coverage = coverage;
	}
	
}
