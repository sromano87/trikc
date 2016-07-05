package it.unibas.trikc.modelEntity.clustering.visitor;

import it.unibas.trikc.modelEntity.Clusters;
import it.unibas.trikc.modelEntity.DissimilarityMatrix;
import it.unibas.trikc.modelEntity.clustering.algorithm.Cluster;

public class VisitorCluster implements IVisitor {
	
	private Clusters clusters; 
	private DissimilarityMatrix matrix;
	
	@Override
	public void visit(Cluster cluster) {
		if(!cluster.getChildren().isEmpty()){
			for(Cluster child : cluster.getChildren()){
				child.accept(this);
			}
		}else{
			clusters.addTestCaseTo(cluster.getParent().getName(), matrix.getHeaderByName(cluster.getName()));
		}
	}

	@Override
	public void setMatrix(DissimilarityMatrix matrix) {
		this.matrix = matrix; 
		
	}

	@Override
	public void setClusters(Clusters clusters) {
		this.clusters = clusters;
		
	}

	@Override
	public Clusters getClusters() {
		return clusters;
	}

}
