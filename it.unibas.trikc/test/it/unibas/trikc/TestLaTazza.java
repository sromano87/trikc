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
import it.unibas.trikc.modelEntity.clustering.ClusteringFactory;
import it.unibas.trikc.modelEntity.clustering.IStrategyClustering;
import it.unibas.trikc.modelEntity.clustering.StrategyHierarchicalClustering;
import it.unibas.trikc.modelEntity.dissimilarity.DissimilarityFactory;
import it.unibas.trikc.modelEntity.dissimilarity.IStrategyDissimilarity;
import it.unibas.trikc.modelEntity.reduction.IStrategyReduction;
import it.unibas.trikc.modelEntity.reduction.ReducingFactory;
import it.unibas.trikc.repository.XMLException;
import it.unibas.trikc.repository.clusters.DAOMockClusters;
import it.unibas.trikc.repository.clusters.IDAOClusters;
import it.unibas.trikc.repository.dissimilarity.DAOMockDissimilarityMatrix;
import it.unibas.trikc.repository.dissimilarity.DAOXmlDissimilarityMatrix;
import it.unibas.trikc.repository.dissimilarity.IDAODissimilarityMatrix;
import it.unibas.trikc.repository.reduction.DAOMockTestSuite;
import it.unibas.trikc.repository.reduction.DAOXmlTestSuite;
import it.unibas.trikc.repository.reduction.IDAOTestSuite;

public class TestLaTazza {

	private ICoverage coverage;
	private DissimilarityMatrix dm;
	//@Before
	public void setUp() {

		ICoverage coverage = new Coverage();
		
		Loader.getInstance().setBinPath("/Users/Maria/Documents/universita magistrale/2015-2016/ing software/LaTazza/LaTazza_IT_A/bin");
		Loader.getInstance().setTestSuiteName("changeReqs_junit.MyTestSuite");
		
		try {
			Loader.getInstance().load(coverage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.coverage = coverage;
	}

	//@After
	public void tearDown() {
		Loader.getInstance().reset();
	}

	//@Test
	public void testCoverage() {
		try {
			this.coverage.executeCoverage();
			System.out.println(this.coverage.getTestSuite());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			//System.out.println(e1.getMessage());
		}
		String nameFile = "testSuiteCoverage_LaTazza_changeReqs_junit.MyTestSuite";
		IDAOTestSuite daoTs = new DAOMockTestSuite();
		try {
			daoTs.save(this.coverage.getTestSuite(), nameFile);
			System.out.println("Salvato");
		} catch (XMLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Assert.assertTrue("Coverage: ",
				this.coverage.getTestSuite().getFullName().equals("changeReqs_junit.MyTestSuite"));
	}

	//@Test
	public void testDissimilarity() {
		IDAOTestSuite daoTs = new DAOXmlTestSuite();
		DissimilarityFactory df = DissimilarityFactory.getInstance();
		IStrategyDissimilarity skd = df.getDissimilarity(Constants.STRING_KERNEL_DISSIMILARITY);
		
		try {
			TestSuite ts = daoTs.load("testSuiteCoverage_LaTazza_changeReqs_junit.MyTestSuite");
			System.out.println("---Caricata");
			System.out.println("Test Suite --> " + ts.toString());
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
			dao.save(dm, "DissimilarityMatrix_LaTazza_changeReqs_junit.MyTestSuite");
			System.out.println("SALVATO DISS");
		} catch (XMLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals(Double.valueOf(0), dm.getValueAt(4, 4));
	}
	
	
	
	@Test
	public void testClusteringLevel0() {
		IDAODissimilarityMatrix daom = new DAOMockDissimilarityMatrix();
		DissimilarityMatrix dm;
		IDAOClusters daoc = new DAOMockClusters();
		try {
			dm = daom.load("DissimilarityMatrix_LaTazza_changeReqs_junit.MyTestSuite");
			System.out.println("caricata latazza");
			// IStrategyClustering strategy = new
			// StrategyHierarchicalClustering();
			Clusters clusters = ClusteringFactory.getInstance().getClustering(Constants.HIERARCHICAL_CLUSTERING)
					.clusterTestCases(dm, 0, Constants.COMPLETE_LINKAGE_STRATEGY);
			assertEquals(4, clusters.getClusters().size());
			daoc.save(clusters, "Clusters0_LaTazza_changeReqs_junit.MyTestSuite");
		} catch (XMLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void testClusteringLevel05() {
		IDAODissimilarityMatrix daom = new DAOMockDissimilarityMatrix();
		DissimilarityMatrix dm;
		IDAOClusters daoc = new DAOMockClusters();
		try {
			dm = daom.load("DissimilarityMatrix_LaTazza_changeReqs_junit.MyTestSuite");
			System.out.println("caricata latazza");
			// IStrategyClustering strategy = new
			// StrategyHierarchicalClustering();
			Clusters clusters = ClusteringFactory.getInstance().getClustering(Constants.HIERARCHICAL_CLUSTERING)
					.clusterTestCases(dm, 0.5, Constants.COMPLETE_LINKAGE_STRATEGY);
			assertEquals(1, clusters.getClusters().size());
			daoc.save(clusters, "Clusters05_LaTazza_changeReqs_junit.MyTestSuite");
		} catch (XMLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void testClusteringLevel1() {
		IDAODissimilarityMatrix daom = new DAOMockDissimilarityMatrix();
		DissimilarityMatrix dm;
		IDAOClusters daoc = new DAOMockClusters();
		try {
			dm = daom.load("DissimilarityMatrix_LaTazza_changeReqs_junit.MyTestSuite");
			System.out.println("caricata latazza");
			// IStrategyClustering strategy = new
			// StrategyHierarchicalClustering();
			Clusters clusters = ClusteringFactory.getInstance().getClustering(Constants.HIERARCHICAL_CLUSTERING)
					.clusterTestCases(dm, 1, Constants.COMPLETE_LINKAGE_STRATEGY);
			assertEquals(1, clusters.getClusters().size());
			daoc.save(clusters, "Clusters1_LaTazza_changeReqs_junit.MyTestSuite");
		} catch (XMLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void TestReductionCluster1() {
		IDAOClusters daoc = new DAOMockClusters();
		Clusters clusters;
		try {
			clusters = daoc.load("Clusters1_LaTazza_changeReqs_junit.MyTestSuite");
			ReducingFactory rd = ReducingFactory.getInstance();
			IStrategyReduction mcr = rd.getReduction(Constants.MOST_COVERING_REDUCTION);
			TestSuite ts = mcr.reduceTestSuite(clusters);

			assertEquals(1, ts.getTestCases().size());

		} catch (XMLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void TestReductionCluster0() {
		IDAOClusters daoc = new DAOMockClusters();
		Clusters clusters;
		try {
			clusters = daoc.load("Clusters0_LaTazza_changeReqs_junit.MyTestSuite");
			ReducingFactory rd = ReducingFactory.getInstance();
			IStrategyReduction mcr = rd.getReduction(Constants.MOST_COVERING_REDUCTION);
			TestSuite ts = mcr.reduceTestSuite(clusters);

			assertEquals(4, ts.getTestCases().size());

		} catch (XMLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void TestReductionCluster05() {
		IDAOClusters daoc = new DAOMockClusters();
		Clusters clusters;
		try {
			clusters = daoc.load("Clusters05_LaTazza_changeReqs_junit.MyTestSuite");
			ReducingFactory rd = ReducingFactory.getInstance();
			IStrategyReduction mcr = rd.getReduction(Constants.MOST_COVERING_REDUCTION);
			TestSuite ts = mcr.reduceTestSuite(clusters);

			assertEquals(1, ts.getTestCases().size());

		} catch (XMLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
