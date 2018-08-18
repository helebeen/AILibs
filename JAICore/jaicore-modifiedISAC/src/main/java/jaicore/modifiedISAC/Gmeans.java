package jaicore.modifiedISAC;

import java.util.ArrayList;

public abstract class Gmeans<A, D> extends Kmeans<A,D> {

	public Gmeans(ArrayList<A> toClusterPoints, IDistanceMetric<D, A, A> dist) {
		super(toClusterPoints, dist);
		// TODO Auto-generated constructor stub
	}

	public abstract void initializeGMeans();

	public abstract ArrayList<Cluster> gmeanscluster();

}
