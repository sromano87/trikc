package it.unibas.trikc.modelEntity.reduction;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.unibas.trikc.modelEntity.Cluster;
import it.unibas.trikc.modelEntity.Clusters;
import it.unibas.trikc.modelEntity.TestSuite;
import it.unibas.trikc.modelEntity.method.TestCase;


/**
 * The MostCoveringReduction class is a public class that provides methods for generating 
 * reduced Test-Suites.
 * It implements the public interface IStrategyReduction.
 * */

public class StrategyMostCoveringReduction implements IStrategyReduction{


	/**
	 * This method creates and returns a new reduced Test-Suite from a map of clusters (posta in input).
	 * @param clusters
	 * */
	@Override
	public TestSuite reduceTestSuite(Clusters clusters) {
		Map<String, Cluster> clustersMap = clusters.getClusters();
		
		TestSuite testSuiteReduced = new TestSuite(); 
		List<TestCase> listOfTestCaseMostCovering = new ArrayList<>();
		for (String key : clustersMap.keySet()) {
			listOfTestCaseMostCovering.addAll(computeMostCoveringTestCase(clustersMap.get(key)));
		}
		testSuiteReduced.setTestCases(listOfTestCaseMostCovering);
		return testSuiteReduced;
	}

	/**
	 * This method computes the most coverage of Test-Case of a cluster. 
	 * It returns a list of Test-Case.
	 * @param cluster
	 * */
	private List<TestCase> computeMostCoveringTestCase(Cluster cluster) {
		List<TestCase> listOfTestCaseMostCovering = new ArrayList<>();
		int lineCovered = 0;
		for (int i = 0; i < cluster.getTestCases().size(); i++) {
			if(cluster.getTestCases().get(i).getCoveredLines().size() == lineCovered){
				listOfTestCaseMostCovering.clear();
				listOfTestCaseMostCovering.add(cluster.getTestCases().get(i));
				lineCovered = cluster.getTestCases().get(i).getCoveredLines().size();
			}
			if(cluster.getTestCases().get(i).getCoveredLines().size() > lineCovered){
				if(listOfTestCaseMostCovering.isEmpty()){
					listOfTestCaseMostCovering.add(cluster.getTestCases().get(i));
					lineCovered = cluster.getTestCases().get(i).getCoveredLines().size();
				}else{
					listOfTestCaseMostCovering.clear();
					listOfTestCaseMostCovering.add(cluster.getTestCases().get(i));
					lineCovered = cluster.getTestCases().get(i).getCoveredLines().size();
				}	
			}	
		}	
		return listOfTestCaseMostCovering; 
	}
	
}
