package it.unibas.trikc.modelEntity.clustering.algorithm;


public interface ClusteringAlgorithm {

  public Cluster performClustering(double[][] distances, String[] clusterNames,
      LinkageStrategy linkageStrategy);

  public Cluster performWeightedClustering(double[][] distances, String[] clusterNames,
      double[] weights, LinkageStrategy linkageStrategy);

}
