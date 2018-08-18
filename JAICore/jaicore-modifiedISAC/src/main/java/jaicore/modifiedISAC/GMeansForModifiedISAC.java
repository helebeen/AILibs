package jaicore.modifiedISAC;

import java.util.ArrayList;
import java.util.HashMap;

import jaicore.CustomDataTypes.GroupIdentifier;
import jaicore.CustomDataTypes.ProblemInstance;
import weka.core.Instance;

public class GMeansForModifiedISAC extends Gmeans<double[], Double> {
	private ArrayList<ProblemInstance<Instance>> instances;
	private ArrayList<Cluster> kmeansCluster;
	private ArrayList<Cluster> gmeansCluster;
	private HashMap<double[], Integer> PositionOfPointInList = new HashMap<double[], Integer>();
	private HashMap<double[], ArrayList<double[]>> pointsInCenter = new HashMap<double[], ArrayList<double[]>>();

	public GMeansForModifiedISAC(ArrayList<double[]> toClusterPoints, IDistanceMetric<Double, double[], double[]> dist,
			int k, ArrayList<ProblemInstance<Instance>> instances) {
		super(toClusterPoints, dist, k);
		this.instances = instances;
		this.kmeansCluster = new ArrayList<Cluster>();
		this.gmeansCluster = new ArrayList<Cluster>();
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
		initializeKMeans();
		boolean clusteringFinished = false;
		while(!clusteringFinished) {
			clusteringFinished = !moveCenterForKmeans();
			System.out.println(clusteringFinished);
			relocatePoints();
		}
		System.out.println("Fertig");
		return kmeansCluster;
	}

	@Override
	public void initializeKMeans() {
		// TODO Gucken wegen Medain
		// find first center
		setupfirstPoint();
		// setup all other k-1 center
		for (int i = 1; i < k; i++) {
			setupfollowingPoints();
		}
		// put the fist k found cluster into the list of Cluster with the center as
		// goupidentifer
		// make a new cluster with the goupidentifer and an empty set of points in
		// cluster
		for (int i = 0; i < k; i++) {
			kmeansCluster.add(new Cluster(new ArrayList<ProblemInstance<Instance>>(), null));
		}
		for (int i = 0; i < points.size(); i++) {
			PositionOfPointInList.put(points.get(i), i);
		}
		relocatePoints();
	}

	private boolean moveCenterForKmeans() {
		boolean hasSomethingChanged = false;
		ArrayList<double[]> toConsiderPoints = new ArrayList<double[]>();
		for (double[] c : center) {
			toConsiderPoints.clear();
			toConsiderPoints = pointsInCenter.get(c);
			double[] newCenter = new double[c.length];
			for (double[] point : toConsiderPoints) {
				for (int i = 0; i < point.length; i++) {
					if(!Double.isNaN(point[i])) {
					newCenter[i] = newCenter[i] + point[i];
					}
				}
			}
			for (int i = 0; i < newCenter.length; i++) {
				newCenter[i] = newCenter[i] / toConsiderPoints.size();
			}
			if (!compareCenter(c, newCenter)) {
				hasSomethingChanged = true;
				c = newCenter;
			}	
		}
		return hasSomethingChanged;
	}

	private void setupfirstPoint() {
		// TODO Es könnte besser sein den Median zu verwenden weil Manhatten Distance
		// aber dauert deutlich länger
		// For every point the distance to every other point is calculated and the
		// the average is then calculated therefore the in average most distant point
		// is the first center.
		double[] firstCenter = new double[points.get(0).length];
		for (int j = 0; j < points.get(0).length; j++) {
			double sum = 0;
			for (int i = 0; i < points.size(); i++) {
				if(!Double.isNaN(points.get(i)[j])) {
					sum += points.get(i)[j];
				}
			}
			sum = sum/points.size();
			firstCenter[j] = sum;
		}
		center.add(firstCenter);
	}

	private void setupfollowingPoints() {
		double[] maxDistPoint = new double[points.get(0).length];
		double sumdistance = 0;
		double max = 0;
		// goes through the list of all points and for each point computes the distance
		// to
		// to all till now found center and sums it up if the point has the most found
		// distance
		// so far it is the new maximum distance point in the end the point with the
		// maximum
		// distance is found.
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

	private void relocatePoints() {
		pointsInCenter.clear();
		for (int i = 0; i < center.size(); i++) {
			GroupIdentifier<double[]> group = new GroupIdentifier<double[]>(center.get(i));
			kmeansCluster.get(i).setGroupIdentifier(group);
			pointsInCenter.put(center.get(i), new ArrayList<double[]>());
		}
		try {
			// for every point put it in the point set of the cluster it is nearest to in
			// form of a ProblemInstance.
			// The points are in the same order as the ProblemInstances because the points
			// are generated from the Porblem
			// Instances.
			// First min dist = dist to fist cluster and gets updated if a other cluster is
			// nearer.
			for (int i = 0; i < points.size(); i++) {
				int indexOfMyCenter = 0;
				double minDist = metric.computeDistance(kmeansCluster.get(0).getId().getIdentifier(), points.get(i));
				for (int j = 1; j < kmeansCluster.size(); j++) {
					double distance = metric.computeDistance(kmeansCluster.get(j).getId().getIdentifier(),points.get(i));
					if (distance <= minDist) {
						indexOfMyCenter = j;
					}
				}
				pointsInCenter.get(center.get(indexOfMyCenter)).add(points.get(i));
				kmeansCluster.get(indexOfMyCenter).addInstance(instances.get(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		;
	}
	//TODO entferne diese method
	public HashMap<double[], ArrayList<double[]>> getPointsWithCluster(){
		return this.pointsInCenter;
	}
	
	private boolean compareCenter(double[] a,double[] b) {
		double[] tmpA = new double[a.length];
		double[] tmpB = new double[b.length];
		for(int i = 0; i<a.length;i++) {
			tmpA[i] = Math.rint(a[i]*1000); 
			tmpB[i] = Math.rint(b[i]*1000);
		}
		for(int i = 0; i<a.length;i++) {
			if(!(Double.isNaN(tmpA[i])|| Double.isNaN(tmpB[i])))
			{if(!(tmpA[i] == tmpB[i])) {
				return false;
			}
			}
		}
		return true;
	}
}
