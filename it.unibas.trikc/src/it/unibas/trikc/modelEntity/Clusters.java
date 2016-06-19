package it.unibas.trikc.modelEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.Root;

import it.unibas.trikc.modelEntity.method.TestCase;

@Root
public class Clusters {
	
	@ElementMap
	private Map<String, Cluster> clusters = new HashMap<String, Cluster>(); 

	public List<TestCase> getCluster (String key) {
		return clusters.get(key).getTestCases(); 
	}
	
	public void setCluster (String key, List<TestCase> values) {
		Cluster cluster = new Cluster(); 
		cluster.setTestCases(values); 
		clusters.put(key, cluster); 
	}
	
}
