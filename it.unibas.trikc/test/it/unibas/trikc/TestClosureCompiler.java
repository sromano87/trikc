package it.unibas.trikc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import it.unibas.trikc.coverage.Coverage;
import it.unibas.trikc.coverage.ICoverage;
import it.unibas.trikc.coverage.Loader;
import it.unibas.trikc.modelEntity.Clusters;
import it.unibas.trikc.modelEntity.DissimilarityMatrix;
import it.unibas.trikc.modelEntity.Sut;
import it.unibas.trikc.modelEntity.TestSuite;
import it.unibas.trikc.modelEntity.clustering.IStrategyClustering;
import it.unibas.trikc.modelEntity.clustering.StrategyHierarchicalClustering;
import it.unibas.trikc.modelEntity.dissimilarity.DissimilarityFactory;
import it.unibas.trikc.modelEntity.dissimilarity.IStrategyDissimilarity;
import it.unibas.trikc.modelEntity.reduction.IStrategyReduction;
import it.unibas.trikc.modelEntity.reduction.ReducingFactory;
import it.unibas.trikc.repository.XMLException;
import it.unibas.trikc.repository.dissimilarity.DAOMockDissimilarityMatrix;
import it.unibas.trikc.repository.dissimilarity.DAOXmlDissimilarityMatrix;
import it.unibas.trikc.repository.dissimilarity.IDAODissimilarityMatrix;
import it.unibas.trikc.repository.reduction.DAOMockTestSuite;
import it.unibas.trikc.repository.reduction.DAOXmlTestSuite;
import it.unibas.trikc.repository.reduction.IDAOTestSuite;

public class TestClosureCompiler {

	private ICoverage coverage;
	private DissimilarityMatrix dm;
	
	@Before
	public void setUp() {

		ICoverage coverage = new Coverage();
		
		Loader.getInstance().setBinPath("/Users/riccardogiuzio/Desktop/IS/TEST/daProvare/closure-compiler-master/build/classes");
		Loader.getInstance().setTestSuiteName("com.google.javascript.jscomp.TestSuite");
		
		try {
			Loader.getInstance().load(coverage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.coverage = coverage;
	}

	@After
	public void tearDown() {
		Loader.getInstance().reset();
	}

	@Test
	public void testCoverage() {
		try {
			this.coverage.executeCoverage();
			System.out.println(this.coverage.getTestSuite());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			System.out.println(e1.getMessage());
		}
		String nameFile = "testSuiteCoverage_ClosureCompiler";
		IDAOTestSuite daoTs = new DAOMockTestSuite();
		try {
			daoTs.save(this.coverage.getTestSuite(), nameFile);
			System.out.println("Salvato");
		} catch (XMLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Assert.assertTrue("Coverage: ",
				this.coverage.getTestSuite().getFullName().equals("com.google.javascript.jscomp.TestSuite"));
	}

	//@Test
	public void testDissimilarity() {
		IDAOTestSuite daoTs = new DAOXmlTestSuite();
		DissimilarityFactory df = DissimilarityFactory.getInstance();
		IStrategyDissimilarity skd = df.getDissimilarity(Constants.STRING_KERNEL_DISSIMILARITY);
		
		try {
			TestSuite ts = daoTs.load("testSuiteCoverage_changeReqs_junit.All_tests");
			System.out.println("---Caricata");
			dm = skd.computeDissimilarity(ts);
		} catch (XMLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		assertEquals(25, dm.getSize());
		assertEquals(Double.valueOf(0), dm.getValueAt(0, 0));
		assertEquals(Double.valueOf(0), dm.getValueAt(1, 1));
		IDAODissimilarityMatrix dao = new DAOMockDissimilarityMatrix(); 
		try {
			dao.save(dm, "DissimilarityMatrix_changeReqs_junit.All_tests");
		} catch (XMLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals(Double.valueOf(0), dm.getValueAt(4, 4));
	}

	/*public void testClustering(){
	 * IStrategyClustering strategy = new StrategyHierarchicalClustering();
	 * Clusters clusters = strategy.clusterTestCases(dm); assertTrue("Test OK",
	 * dm.getHeaders().size() == 10);
	 * 
	 * ReducingFactory rd = ReducingFactory.getInstance(); IStrategyReduction
	 * mcr = rd.getReduction(Constants.MOST_COVERING_REDUCTION); TestSuite ts =
	 * mcr.reduceTestSuite(clusters);
	 * 
	 * assertEquals(5, ts.getTestCases().size()); //System.out.println(
	 * "Test suite reduction " + ts ); }
	 */

}
