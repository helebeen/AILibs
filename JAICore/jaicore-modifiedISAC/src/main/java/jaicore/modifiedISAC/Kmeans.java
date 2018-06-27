package jaicore.modifiedISAC;

import java.util.ArrayList;

abstract public class Kmeans<A, D> {
	private ArrayList<A> points;
	private ArrayList<A> center;
	private IDistanceMetric<D, A, A> metric;

	public Kmeans(int k, ArrayList<A> toClusterPoints, IDistanceMetric<D, A, A> dist) {
		this.points = toClusterPoints;
		this.metric = dist;
	}

	public abstract Cluster kmeanscluster();

	public abstract void initialize();
}
