package it.unibas.trikc;

import org.junit.Before;
import org.junit.Test;

import it.unibas.trikc.coverage.CoverageFacade;
import it.unibas.trikc.coverage.ICoverage;
import it.unibas.trikc.modelEntity.Clusters;
import it.unibas.trikc.modelEntity.DissimilarityMatrix;
import it.unibas.trikc.modelEntity.TestSuite;
import it.unibas.trikc.modelEntity.clustering.ClusteringFactory;
import it.unibas.trikc.modelEntity.dissimilarity.DissimilarityFactory;
import it.unibas.trikc.modelEntity.dissimilarity.IStrategyDissimilarity;
import it.unibas.trikc.modelEntity.reduction.IStrategyReduction;
import it.unibas.trikc.modelEntity.reduction.ReducingFactory;
import it.unibas.trikc.repository.XMLException;
import it.unibas.trikc.repository.clusters.DAOXmlClusters;
import it.unibas.trikc.repository.clusters.IDAOClusters;
import it.unibas.trikc.repository.dissimilarity.DAOXmlDissimilarityMatrix;
import it.unibas.trikc.repository.dissimilarity.IDAODissimilarityMatrix;
import it.unibas.trikc.repository.reduction.DAOXmlTestSuite;
import it.unibas.trikc.repository.reduction.IDAOTestSuite;

public class TestClosureCompiler {

	private ICoverage coverage;
	private DissimilarityMatrix dm;
	private TestSuite testSuite;
	private TestSuite testSuiteLevel0;
	private TestSuite testSuiteLevel1;
	private Clusters clustersLevel0;
	private Clusters clustersLevel1;
	
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
	public void testClosureCompiler() {
		//Coverage
		CoverageFacade coverageFacade = new CoverageFacade();
		try {
			coverageFacade.runCoverage(binPath, testSuiteName, testPath, libPath);
			this.coverage = coverageFacade.getCoverage();
			this.testSuite = this.coverage.getTestSuite();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("-------CoverageDone-------");
		
		//Dissimilarity
		DissimilarityFactory df = DissimilarityFactory.getInstance();
		IStrategyDissimilarity skd = df.getDissimilarity(Constants.STRING_KERNEL_DISSIMILARITY);
		dm = skd.computeDissimilarity(this.testSuite);
		
		System.out.println("-------DissimilarityDone-------\n");
		
		//Clustering level 0
		this.clustersLevel0 = ClusteringFactory.getInstance().getClustering(Constants.HIERARCHICAL_CLUSTERING)
					.clusterTestCases(dm, 0, Constants.COMPLETE_LINKAGE_STRATEGY);

		System.out.println("-------ClusteringLevel0Done-------\n");
		//Clustering level 1

		this.clustersLevel1 = ClusteringFactory.getInstance().getClustering(Constants.HIERARCHICAL_CLUSTERING)
					.clusterTestCases(dm, 1, Constants.COMPLETE_LINKAGE_STRATEGY);
		
		System.out.println("-------ClusteringLevel1Done-------\n");
		
		//Reduction level0
		ReducingFactory rd = ReducingFactory.getInstance();
		IStrategyReduction mcr = rd.getReduction(Constants.MOST_COVERING_REDUCTION);
		this.testSuiteLevel0 = mcr.reduceTestSuite(this.clustersLevel0);
		
		System.out.println("-------ReductionLevel0Done-------\n");
		
		//Reduction level1
		this.testSuiteLevel1 = mcr.reduceTestSuite(this.clustersLevel1);

		System.out.println("-------ReductionLevel1Done------- \n");
		
		try {
			String nameFile = "testSuiteCoverage_com.google.debugging.sourcemap.MyTestSuite_ClosureCompiler";
			IDAOTestSuite daoTs = new DAOXmlTestSuite();
			daoTs.save(this.coverage.getTestSuite(), nameFile);
			
			IDAODissimilarityMatrix dao = new DAOXmlDissimilarityMatrix(); 
			dao.save(dm, "DissimilarityMatrix_com.google.debugging.sourcemap.MyTestSuite_ClosureCompiler");

			IDAOClusters daoc = new DAOXmlClusters();
			daoc.save(this.clustersLevel0, "Clustering_com.google.debugging.sourcemap.MyTestSuite_ClosureCompiler_0");
			daoc.save(this.clustersLevel1, "Clustering_com.google.debugging.sourcemap.MyTestSuite_ClosureCompiler_1");
			
			daoTs.save(this.testSuiteLevel0, "Reduction_com.google.debugging.sourcemap.MyTestSuite_ClosureCompiler_0");
			daoTs.save(this.testSuiteLevel1, "Reduction_com.google.debugging.sourcemap.MyTestSuite_ClosureCompiler_1");

		}catch(XMLException e) {
			
		}
		System.out.println("-------OriginalTestSuite-------\n");
		
		System.out.println(this.testSuite);
		
		System.out.println("\n-------ReductTestSuiteWithClusterLevel0-------\n");

		System.out.println(this.testSuiteLevel0);
		
		System.out.println("\n-------ReductTestSuiteWithClusterLevel1-------\n");

		System.out.println(this.testSuiteLevel1);
		
	}

}
