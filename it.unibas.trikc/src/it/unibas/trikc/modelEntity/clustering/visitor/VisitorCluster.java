package it.unibas.trikc.modelEntity.clustering.visitor;

import it.unibas.trikc.modelEntity.Clusters;
import it.unibas.trikc.modelEntity.DissimilarityMatrix;
import it.unibas.trikc.modelEntity.clustering.algorithm.Cluster;

public class VisitorCluster implements IVisitor {
	
	private Clusters clusters; 
	private DissimilarityMatrix matrix;
	private String nomePadre; 
	
	@Override
	public void visit(Cluster cluster, double level) {
		
		if(cluster.getParent() == null){ //vale solo per il nodo radice
			nomePadre = cluster.getName(); 
		} else if((cluster.getDistance().getDistance() < level) //vale per tutti gli altri nodi
				&& (cluster.getParent().getDistance().getDistance() >= level)){
			nomePadre = cluster.getParent().getName();
		}
		
		if(!cluster.getChildren().isEmpty()){
			for(Cluster child : cluster.getChildren()){
				child.accept(this, level);
			}
		}else{
//			clusters.addTestCaseTo(cluster.getParent().getName(), matrix.getHeaderByName(cluster.getName()));
			clusters.addTestCaseTo(nomePadre, matrix.getHeaderByName(cluster.getName()));
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
