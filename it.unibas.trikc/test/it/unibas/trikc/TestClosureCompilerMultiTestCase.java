package it.unibas.trikc;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import it.unibas.trikc.coverage.CoverageFacade;
import it.unibas.trikc.modelEntity.Clusters;
import it.unibas.trikc.modelEntity.DissimilarityMatrix;
import it.unibas.trikc.modelEntity.TestSuite;
import it.unibas.trikc.modelEntity.clustering.ClusteringFactory;
import it.unibas.trikc.modelEntity.dissimilarity.DissimilarityFactory;
import it.unibas.trikc.modelEntity.dissimilarity.IStrategyDissimilarity;
import it.unibas.trikc.modelEntity.method.TestCase;
import it.unibas.trikc.modelEntity.reduction.IStrategyReduction;
import it.unibas.trikc.modelEntity.reduction.ReducingFactory;
import it.unibas.trikc.repository.XMLException;
import it.unibas.trikc.repository.clusters.DAOXmlClusters;
import it.unibas.trikc.repository.clusters.IDAOClusters;
import it.unibas.trikc.repository.dissimilarity.DAOXmlDissimilarityMatrix;
import it.unibas.trikc.repository.dissimilarity.IDAODissimilarityMatrix;
import it.unibas.trikc.repository.reduction.DAOXmlTestSuite;
import it.unibas.trikc.repository.reduction.IDAOTestSuite;

public class TestClosureCompilerMultiTestCase {

	private DissimilarityMatrix dm;
	
	private String path = System.getProperty("user.dir");
	private String os = System.getProperty("os.name");
	private String binPath, testSuiteName, testPath, libPath; 
	
	@Before
	public void setup () {
		if (isWindows(os)) {
			binPath = path + "\\test_resources\\closure-compiler-master\\build\\classes";
			testSuiteName = "com.google.debugging.sourcemap.MyTestSuite";
			testPath = path + "\\test_resources\\closure-compiler-master\\build\\test";
			libPath = path + "\\test_resources\\closure-compiler-master\\lib";
		} else if (isMac(os)) {
			binPath = path + "/test_resources/closure-compiler-master/build/classes";
			testSuiteName = "com.google.debugging.sourcemap.MyTestSuite";
			testPath = path + "/test_resources/closure-compiler-master/build/test";
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
	public void testCoverageFacade() {
		CoverageFacade coverageFacade = new CoverageFacade();
		try {
			coverageFacade.runCoverage(binPath, testSuiteName, testPath, libPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Assert.assertTrue("Coverage: ",coverageFacade.getCoverage().getTestSuite().getFullName().equals("com.google.debugging.sourcemap.MyTestSuite"));
		List<TestCase> testCases = coverageFacade.getCoverage().getTestSuite().getTestCases();
		for(TestCase tc : testCases){
			if(tc.getFullName().equals("com.google.debugging.sourcemap.Base64Test.testBase64")) {
				Assert.assertTrue(tc.getCoveredLines().size() == 8);		
			} 
			if(tc.getFullName().equals("com.google.debugging.sourcemap.SourceMapConsumerV3Test.testMap")) {
				Assert.assertTrue(tc.getCoveredLines().size() == 480);
			} 
		}
	}
	
	@Test
	public void testDissimilarity() {
		IDAOTestSuite daoTs = new DAOXmlTestSuite();
		DissimilarityFactory df = DissimilarityFactory.getInstance();
		IStrategyDissimilarity skd = df.getDissimilarity(Constants.STRING_KERNEL_DISSIMILARITY);
		try {
			TestSuite ts = daoTs.loadForTest("testSuiteCoverage_com.google.debugging.sourcemap.MyTestSuite_ClosureCompiler");
			dm = skd.computeDissimilarity(ts);
		} catch (XMLException e1) {
			e1.printStackTrace();
		} 
		assertEquals(6, dm.getSize());
		assertEquals(Double.valueOf(0), dm.getValueAt(0, 0));
		assertEquals(Double.valueOf(0), dm.getValueAt(1, 1));
		assertEquals(Double.valueOf(0), dm.getValueAt(4, 4));
	}
	
	@Test
	public void testClusteringLevel1() {
		IDAODissimilarityMatrix daom = new DAOXmlDissimilarityMatrix();
		DissimilarityMatrix dm;
		try {
			dm = daom.loadForTest("DissimilarityMatrix_com.google.debugging.sourcemap.MyTestSuite_ClosureCompiler");
			Clusters clusters = ClusteringFactory.getInstance().getClustering(Constants.HIERARCHICAL_CLUSTERING)
						.clusterTestCases(dm, 1, Constants.COMPLETE_LINKAGE_STRATEGY);
			assertEquals(1, clusters.getClusters().size());
		} catch (XMLException e) {
			e.printStackTrace();
		}

	}
		
		
	@Test
	public void TestReductionClusters0() {
		IDAOClusters daoc = new DAOXmlClusters();
		Clusters clusters;
		try {
			clusters = daoc.loadForTest("Clustering_com.google.debugging.sourcemap.MyTestSuite_ClosureCompiler_0");
			ReducingFactory rd = ReducingFactory.getInstance();
			IStrategyReduction mcr = rd.getReduction(Constants.MOST_COVERING_REDUCTION);
			TestSuite ts = mcr.reduceTestSuite(clusters);
			assertEquals(3, ts.getTestCases().size());	
		} catch (XMLException e) {
			e.printStackTrace();
		}
			
	}
		
	@Test
	public void TestReductionClusters1() {
		IDAOClusters daoc = new DAOXmlClusters();
		Clusters clusters;
		try {
			clusters = daoc.loadForTest("Clustering_com.google.debugging.sourcemap.MyTestSuite_ClosureCompiler_1");
			ReducingFactory rd = ReducingFactory.getInstance();
			IStrategyReduction mcr = rd.getReduction(Constants.MOST_COVERING_REDUCTION);
			TestSuite ts = mcr.reduceTestSuite(clusters);
			assertEquals(1, ts.getTestCases().size());
		} catch (XMLException e) {
			e.printStackTrace();
		}
			
	}
}