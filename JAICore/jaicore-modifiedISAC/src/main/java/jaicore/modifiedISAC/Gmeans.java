package jaicore.modifiedISAC;

import java.util.ArrayList;

public abstract class Gmeans<A,D> extends Kmeans<A, D> {

	public Gmeans(int k, ArrayList<A> toClusterPoints, IDistanceMetric<D, A, A> dist) {
		super(k, toClusterPoints, dist);
		// TODO Auto-generated constructor stub
	}
	
	public abstract Cluster clustergmeans();

}
