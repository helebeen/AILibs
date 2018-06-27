package jaicore.modifiedISAC;

import java.util.ArrayList;

public abstract class Gmeans<A, B, D> extends Kmeans<A, B, D> {

	public Gmeans(ArrayList<A> toClusterPoints, IDistanceMetric<D, B, B> dist) {
		super(toClusterPoints, dist);
		// TODO Auto-generated constructor stub
	}

	public abstract void initializeGMeans();

	public abstract Cluster gmeanscluster();

}
