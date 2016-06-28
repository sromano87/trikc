package it.unibas.trikc.modelEntity.clustering.algorithm;


import java.util.Collection;

public interface LinkageStrategy {

	public Distance calculateDistance(Collection<Distance> distances);
}
