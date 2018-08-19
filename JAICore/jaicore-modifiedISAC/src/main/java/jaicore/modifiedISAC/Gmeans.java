package jaicore.modifiedISAC;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Gmeans<A, D> {
	public ArrayList<A> points;
	public ArrayList<A> center;
	private HashMap<double[],ArrayList<double[]>> centerAndPoints = new  HashMap<double[],ArrayList<double[]>> ();
	
	public Gmeans(ArrayList<A> toClusterPoints) {
		this.points = toClusterPoints;
		this.center = new ArrayList<A>();
	}

	public abstract ArrayList<Cluster> gmeanscluster();

}
