package jaicore.modifiedISAC;

import java.util.ArrayList;

abstract public class Kmeans<A, B, D> {
	public ArrayList<A> points;
	public ArrayList<A> center;
	public IDistanceMetric<D, B, B> metric;

	public Kmeans(ArrayList<A> toClusterPoints, IDistanceMetric<D, B, B> dist) {
		this.points = toClusterPoints;
		this.metric = dist;
	}

	public abstract Cluster kmeanscluster();

	public abstract void initializeKMeans();
}
