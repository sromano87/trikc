package it.unibas.trikc.modelEntity.reduction;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.unibas.trikc.modelEntity.Cluster;
import it.unibas.trikc.modelEntity.Clusters;
import it.unibas.trikc.modelEntity.TestSuite;
import it.unibas.trikc.modelEntity.method.TestCase;

public class StrategyMostCoveringReduction implements IStrategyReduction{

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
