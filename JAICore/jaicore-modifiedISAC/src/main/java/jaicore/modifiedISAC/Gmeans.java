package jaicore.modifiedISAC;

import java.util.ArrayList;

public abstract class Gmeans<A, D> {
	public ArrayList<A> points;
	public ArrayList<A> center;
	
	public Gmeans(ArrayList<A> toClusterPoints) {
		this.points = toClusterPoints;
		this.center = new ArrayList<A>();
	}

	public abstract ArrayList<Cluster> gmeanscluster();

}
