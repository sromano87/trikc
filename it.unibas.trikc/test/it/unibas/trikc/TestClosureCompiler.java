package it.unibas.trikc;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import it.unibas.trikc.coverage.CoverageFacade;
import it.unibas.trikc.coverage.ICoverage;
import it.unibas.trikc.modelEntity.Clusters;
import it.unibas.trikc.modelEntity.DissimilarityMatrix;
import it.unibas.trikc.modelEntity.TestSuite;
import it.unibas.trikc.modelEntity.dissimilarity.DissimilarityFactory;
import it.unibas.trikc.modelEntity.dissimilarity.IStrategyDissimilarity;
import it.unibas.trikc.modelEntity.reduction.IStrategyReduction;
import it.unibas.trikc.modelEntity.reduction.ReducingFactory;
import it.unibas.trikc.repository.XMLException;
import it.unibas.trikc.repository.clusters.DAOMockClusters;
import it.unibas.trikc.repository.clusters.IDAOClusters;
import it.unibas.trikc.repository.dissimilarity.DAOMockDissimilarityMatrix;
import it.unibas.trikc.repository.dissimilarity.IDAODissimilarityMatrix;
import it.unibas.trikc.repository.reduction.DAOMockTestSuite;
import it.unibas.trikc.repository.reduction.DAOXmlTestSuite;
import it.unibas.trikc.repository.reduction.IDAOTestSuite;

public class TestClosureCompiler {

	private ICoverage coverage;
	private DissimilarityMatrix dm;
	
	private String path = System.getProperty("user.dir");
	private String os = System.getProperty("os.name");
	private String binPath, testSuiteName, testPath, libPath; 
	
	@Before
	public void setup () {
		if (isWindows(os)) {
			binPath = path + "\\test_resources\\closure-compiler-master\\build\\classes";
			testSuiteName = "com.google.debugging.sourcemap.MyTestSuite";
			testPath = path + "\\test_resources\\closure-compiler-master\\build\\test-classes";
			libPath = path + "\\test_resources\\closure-compiler-master\\lib";
		} else if (isMac(os)) {
			binPath = path + "/test_resources/closure-compiler-master/build/classes";
			testSuiteName = "com.google.debugging.sourcemap.MyTestSuite";
			testPath = path + "/test_resources/closure-compiler-master/build/test-classes";
			libPath = path + "/test_resources/closure-compiler-master/lib"; 
		}
	}
	
	public boolean isWindows(String os) {
		return os.toLowerCase().contains("win");
	}
	
	public boolean isMac(String os) {
		return os.toLowerCase().contains("mac");
	}
	
	@Test
	public void testCoverageFacage() {
		CoverageFacade coverageFacade = new CoverageFacade();
		try {
			coverageFacade.runCoverage(binPath, testSuiteName, testPath, libPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.coverage = coverageFacade.getCoverage();
		String nameFile = "testSuiteCoverage_ClosureCompiler";
		IDAOTestSuite daoTs = new DAOMockTestSuite();
		try {
			daoTs.save(this.coverage.getTestSuite(), nameFile);
			System.out.println("Salvato");
		} catch (XMLException e) {
			e.printStackTrace();
		}
		Assert.assertTrue("Coverage: ",this.coverage.getTestSuite().getFullName().equals("com.google.debugging.sourcemap.MyTestSuite"));
	}
	
	//@Test
	public void testDissimilarity() {
		IDAOTestSuite daoTs = new DAOXmlTestSuite();
		DissimilarityFactory df = DissimilarityFactory.getInstance();
		IStrategyDissimilarity skd = df.getDissimilarity(Constants.STRING_KERNEL_DISSIMILARITY);
		
		try {
			TestSuite ts = daoTs.load("testSuiteCoverage_ClosureCompiler");
			System.out.println("---Caricata");
			dm = skd.computeDissimilarity(ts);
		} catch (XMLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		assertEquals(6, dm.getSize());
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

}
