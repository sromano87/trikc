package it.unibas.trikc.modelEntity.clustering.visitor;

import it.unibas.trikc.modelEntity.Clusters;
import it.unibas.trikc.modelEntity.DissimilarityMatrix;
import it.unibas.trikc.modelEntity.clustering.algorithm.Cluster;

public interface IVisitor {

	void visit(Cluster cluster);
	void setMatrix(DissimilarityMatrix matrix); 
	void setClusters(Clusters clusters); 
	
	Clusters getClusters();
}
