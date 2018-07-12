package jaicore.modifiedISAC;

import java.util.ArrayList;

import jaicore.CustomDataTypes.GroupIdentifier;
import jaicore.CustomDataTypes.ProblemInstance;
import weka.core.Instance;

public class GMeansForModifiedISAC extends Gmeans<double[], Double> {
	private ArrayList<ProblemInstance<Instance>> instances;
	private ArrayList<Cluster> kmeansCluster;
	private ArrayList<Cluster> gmeansCluster;

	public GMeansForModifiedISAC(ArrayList<double[]> toClusterPoints, IDistanceMetric<Double, double[], double[]> dist,
			int k, ArrayList<ProblemInstance<Instance>> instances) {
		super(toClusterPoints, dist, k);
		this.instances = instances;
		this.kmeansCluster = null;
		this.gmeansCluster = null;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initializeGMeans() {
		// TODO Auto-generated method stub

	}

	@Override
	public ArrayList<Cluster> gmeanscluster() {

		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Cluster> kmeanscluster() {
		// TODO Auto-generated method stub
		boolean nothingchanged = false;
		while (!nothingchanged) {

		}
		return null;
	}

	@Override
	public void initializeKMeans() {
		// TODO Gucken wegen Medain
		setupfirstPoint();
		for (int i = 1; i < k; i++) {
			setupfollowingPoints();
		}
		for(double[] c : center) {
			GroupIdentifier<double[]> group = new GroupIdentifier<double[]>(c);
			kmeansCluster.add(new Cluster(null,group));
		}
		for(int i = 0; i< points.size();i++){
			int indexOfMyCenter = 0;
			double minDist = metric.computeDistance(kmeansCluster.get(0).getId().getIdentifier(),points.get(i));
			for(int j = 1; j<kmeansCluster.size();j++) {
				try {
					double distance = metric.computeDistance(c.getId().getIdentifier(),i);
					if(distance <= minDist) {
						indexOfMyCenter = j;
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			kmeansCluster.get(indexOfMyCenter).addInstance(instances.get(i));
		}
	}

	private void setupfirstPoint() {
		// TODO Es könnte besser sein den Median zu verwenden weil Manhatten Distance
		// aber dauert deutlich länger
		double[] firstCenter = new double[points.get(0).length];
		for (int j = 0; j < points.get(0).length; j++) {
			int sum = 0;
			for (int i = 0; i < points.size(); i++) {
				sum += points.get(i)[j];
			}
			sum = sum / points.size();
			firstCenter[j] = sum;
		}
		center.add(firstCenter);
	}

	private void setupfollowingPoints() {
		double[] maxDistPoint = new double[points.get(0).length];
		double sumdistance = 0;
		double max = 0;
		for (double[] i : points) {
			sumdistance = 0;
			for (double[] j : center) {
				try {
					sumdistance += metric.computeDistance(j, i);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (sumdistance >= max) {
				max = sumdistance;
				maxDistPoint = i;
			}
		}
		center.add(maxDistPoint);
	}
}
