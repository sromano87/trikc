package it.unibas.trikc.modelEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.Root;

import it.unibas.trikc.modelEntity.method.TestCase;

/**
 * Class Clusters
 *
 * @author TeamDataLayer
 */

@Root
public class Clusters {
	
	@ElementMap
	private Map<String, Cluster> clusters = new HashMap<String, Cluster>(); 

	/**
	 * This method returns a List of Cluster where the name is a key
	 * @param key
	 * @return List
	 */
	public List<TestCase> getCluster (String key) {
		return clusters.get(key).getTestCases(); 
	}
	
	/**
	 * This method set a List of Cluster
	 * @param key
	 * @param values
	 */
	public void setCluster (String key, List<TestCase> values) {
		Cluster cluster = new Cluster(); 
		cluster.setTestCases(values); 
		clusters.put(key, cluster); 
	}
	
	/**
	 * This method add a test case into cluster with key like as parameter
	 * @param key
	 * @param testCase
	 */
	public void addTestCaseTo( String key, TestCase testCase){
		Cluster cluster = clusters.get(key);
		if(cluster == null){
			cluster = new Cluster();
			clusters.put(key, cluster);
		}
		cluster.addTestCase(testCase);
	}

	/**
	 * This method return a map that contains all Cluster
	 * @return Map<String, Cluster>
	 */
	public Map<String, Cluster> getClusters() {
		return clusters;
	}

	/**
	 * This method set a map of Clusters
	 * @param clusters
	 */
	public void setClusters(Map<String, Cluster> clusters) {
		this.clusters = clusters;
	}
	
	
	
}
