package jaicore.modifiedISAC;

import java.util.ArrayList;

abstract public class Kmeans<A,D> {
	public ArrayList<A> points;
	public ArrayList<A> center;
	public int k;
	public IDistanceMetric<D, A, A> metric;

	public Kmeans(ArrayList<A> toClusterPoints, IDistanceMetric<D, A, A> dist, int k) {
		this.points = toClusterPoints;
		this.metric = dist;
		this.k = k;
	}

	public abstract ArrayList<Cluster> kmeanscluster();

	public abstract void initializeKMeans();
}
