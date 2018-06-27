package jaicore.modifiedISAC;

import java.util.ArrayList;

import jaicore.CustomDataTypes.ProblemInstance;
import weka.core.Instance;

public class GMeansForModifiedISAC extends Gmeans<ProblemInstance<Instance>,double[], Double> {

	public GMeansForModifiedISAC(ArrayList<ProblemInstance<Instance>> toClusterPoints,
			IDistanceMetric<Double, double[], double[]> dist) {
		super(toClusterPoints, dist);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initializeGMeans() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Cluster gmeanscluster() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Cluster kmeanscluster() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void initializeKMeans() {
		// TODO Auto-generated method stub
		
	}



}
