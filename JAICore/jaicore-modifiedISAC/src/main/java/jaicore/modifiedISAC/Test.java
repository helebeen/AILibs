package jaicore.modifiedISAC;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import jaicore.CustomDataTypes.GroupIdentifier;
import jaicore.CustomDataTypes.ProblemInstance;
import weka.core.Instance;
import org.apache.commons.math3.distribution.NormalDistribution;

public class Test extends Gmeans<double[], Double> {
	private ArrayList<ProblemInstance<Instance>> instances;
	private ArrayList<Cluster> kmeansCluster;
	private ArrayList<Cluster> gmeansCluster;

	private HashMap<double[], Integer> PositionOfPointInList = new HashMap<double[], Integer>();

	private HashMap<double[], ArrayList<double[]>> pointsInCenter = new HashMap<double[], ArrayList<double[]>>();
	private HashMap<double[], double[]> CenterOfPoint = new HashMap<double[], double[]>();
	private ArrayList<double[]> initpoints = new ArrayList<double[]>();

	public Test(ArrayList<double[]> toClusterPoints, IDistanceMetric<Double, double[], double[]> dist,
			ArrayList<ProblemInstance<Instance>> instances) {
		super(toClusterPoints, dist);
		this.instances = instances;
		this.kmeansCluster = new ArrayList<Cluster>();
		this.gmeansCluster = new ArrayList<Cluster>();
		for (int i = 0; i < toClusterPoints.size(); i++) {
			PositionOfPointInList.put(toClusterPoints.get(i), i);
		}
		initpoints = (ArrayList<double[]>) toClusterPoints.clone();
	}

	@Override
	public void initializeGMeans() {
		// TODO Auto-generated method stub

	}

	@Override
	public ArrayList<Cluster> gmeanscluster() {
		HashMap<double[], ArrayList<double[]>> intermidiateClusterwithPoints = new HashMap<double[], ArrayList<double[]>>();
		HashMap<Integer, double[]> positionOfCenter = new HashMap<Integer, double[]>();
		ArrayList<double[]> allPoints = new ArrayList<double[]>();

		for (double[] d : points) {
			allPoints.add(d);
		}

		int tmp = 1;

		int k = 1;
		int i = 1;
		kmeanscluster(k);
		
		for (double[] c : center) {
			positionOfCenter.put(tmp, c);
			tmp++;
		}
		for (double[] c : center) {
			printDoubleArray(c);
			intermidiateClusterwithPoints.put(c, new ArrayList<double[]>());
		}
		for (double[] point : points) {
			for(double[] d : intermidiateClusterwithPoints.keySet()) {
				if(Arrays.equals(CenterOfPoint.get(point),d)) {
					intermidiateClusterwithPoints.get(d).add(point);
					CenterOfPoint.put(d, CenterOfPoint.remove(CenterOfPoint.get(point)));
				}
			}
		}

		while (i <= k) {
			clearKMeans();
			points = intermidiateClusterwithPoints.get(positionOfCenter.get(i));
			kmeanscluster(2);
			double[] v = difference(center.get(0), center.get(1));
			double w = 0;
			for (int l = 0; l < v.length; l++) {
				if(!Double.isNaN(v[l])) {
					w += Math.pow(v[l], 2);
				}
			}
			double[] y = new double[points.size()];
			for (int r = 0; r < points.size(); r++) {
				for (int p = 0; p < points.get(r).length; p++) {
					y[r] += (v[p] * points.get(r)[p]) / w;
				}
			}
			if (!andersonDarlingTest(y)) {
				intermidiateClusterwithPoints.remove(positionOfCenter.get(i));
				intermidiateClusterwithPoints.put(center.get(0), pointsInCenter.get(center.get(0)));
				positionOfCenter.replace(i, center.get(0));
				k++;
				intermidiateClusterwithPoints.remove(positionOfCenter.get(k));
				intermidiateClusterwithPoints.put(center.get(1), pointsInCenter.get(center.get(1)));
				positionOfCenter.replace(k, center.get(1));

			} else {
				i++;
			}
		}
		for(double[] d : intermidiateClusterwithPoints.keySet()) {
			System.out.print("Center: ");
			printDoubleArray(d);
			for(double[] j: intermidiateClusterwithPoints.get(d)) {
				printDoubleArray(j);
			}
		}
		return null;
	}

	private boolean andersonDarlingTest(double[] d) {
		Arrays.sort(d);
		double mean = 1;
		double variance = 0;
		
		int totalvalue = 0;
		for (double i : d) {
			if (!Double.isNaN(i)) {
				totalvalue++;
				mean += i;
			}
		}
		
		mean = mean /totalvalue;
		
		for (double i : d) {
			if(!Double.isNaN(i)) {
				variance += Math.pow((i - mean), 2);
			}
		}
		
		double[] y = standraizeRandomVariable(d, mean, variance);
		double aSquare1 = (-1) * d.length;
		double aSquare2 = 0;
		
		NormalDistribution normal = new NormalDistribution();
		for (int i = 1; i < y.length; i++) {
			if(!Double.isNaN(y[i])) {
			aSquare2 = +((2 * i) - 1) * (Math.log(normal.cumulativeProbability(y[i])))
					+ Math.log(1 - (normal.cumulativeProbability(y[(y.length - i)])));
			}
		}
		aSquare2 = (1 / y.length) * aSquare2;
		double aSquare = aSquare1 - aSquare2;
		double aSqurestar = aSquare * (1 + (4 / y.length) - (25 / (Math.pow(y.length, 2))));
		if (y.length <= 10) {
			return aSqurestar > 0.683;
		} else {
			if (y.length <= 20) {
				return aSqurestar > 0.704;
			} else {
				if (y.length <= 50) {
					return aSqurestar > 0.735;
				} else {
					if (y.length <= 100) {
						return aSqurestar > 0.754;
					} else {
						return aSqurestar > 0.787;
					}
				}
			}
		}
	}

	private double[] standraizeRandomVariable(double[] d, double mean, double variance) {
		double[] tmp = new double[d.length];
		for (int i = 0; i < tmp.length; i++) {
			if(!Double.isNaN(d[i])) {
				tmp[i] = (d[i] - mean) / variance;
			}
			else {
				tmp[i] = Double.NaN;
			}
		}
		return tmp;
	}

	private double[] difference(double[] a, double[] b) {
		double[] c = new double[a.length];
		for (int i = 0; i < a.length; i++) {
			// TODO Muss das auch normaliziert werden
			if(!(Double.isNaN(a[i])||Double.isNaN(b[i]))) {
				c[i] = a[i] - b[i];
			}
		}
		return c;
	}

	@Override
	public ArrayList<Cluster> kmeanscluster(int k) {
		this.k = k;
		initializeKMeans();
		// for(double[] d : points) {
		// printDoubleArray(d);
		// }
		//
		boolean test = true;
		// int i = 1;
		while (test) {
			relocateCenter();
			test = relocatePoints();
			// System.out.println("Durchlauf Nummer " + i);
			// i++;
		}
		// System.out.println("schleife Verlassen");
		// for (double[] c : pointsInCenter.keySet()) {
		// printDoubleArray(c);
		// }
		// for (int h = 0; h<points.size();h++) {
		// int indexofCenter = 0;
		// for (int l = 0; l<center.size(); l++) {
		// try {
		// if(Arrays.equals(CenterOfPoint.get(points.get(h)), center.get(l))) {
		// indexofCenter = l;
		// }
		// System.out.println("Abstand des Punktes "+h+ " zu Center " + l +" "+
		// metric.computeDistance(points.get(h), center.get(l)));
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }
		// System.out.println("Das Center das gewaehlt worden ist "+indexofCenter);
		// System.out.println("----------------------------------------------------------------------------");
		// }
		return null;
	}

	@Override
	public void initializeKMeans() {
		initializeFirstCenter();
		initializeFollowingCenter();
		locateFirstPoints();
	}

	private void initializeFirstCenter() {
		double[] firstCenter = new double[points.get(0).length];
		for (int i = 0; i < points.get(0).length; i++) {
			int totalvalue = points.size();
			for (double[] d : points) {
				if (Double.isNaN(d[i])) {
					totalvalue--;
				} else {
					firstCenter[i] += d[i];
				}
			}
			firstCenter[i] = firstCenter[i] / totalvalue;
		}
		center.add(firstCenter);
		pointsInCenter.put(firstCenter, new ArrayList<double[]>());
	}

	private void locateFirstPoints() {
		try {
			for (double[] point : points) {
				int indexOfMyCenter = 0;
				double maxCenterDist = metric.computeDistance(point, center.get(0));
				for (int i = 1; i < center.size(); i++) {
					double tmp = metric.computeDistance(point, center.get(i));
					if (tmp <= maxCenterDist) {
						indexOfMyCenter = i;
						maxCenterDist = tmp;
					}
				}
				CenterOfPoint.put(point, center.get(indexOfMyCenter));
				// oldCenterOfPoint.put(point, indexOfMyCenter);
				pointsInCenter.get(center.get(indexOfMyCenter)).add(point);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private boolean relocatePoints() {
		boolean hasSomethingChanged = false;
		for (double[] c : center) {
			pointsInCenter.get(c).clear();
		}
		try {
			for (double[] point : points) {
				double minDist = metric.computeDistance(point, center.get(0));
				int indexOfMyCenter = 0;
				for (int i = 1; i < center.size(); i++) {
					double tmp = metric.computeDistance(point, center.get(i));
					if (tmp < minDist) {
						indexOfMyCenter = i;
						minDist = tmp;
					}
				}
				if (!Arrays.equals(CenterOfPoint.get(point), center.get(indexOfMyCenter))) {
					hasSomethingChanged = true;
					CenterOfPoint.replace(point, center.get(indexOfMyCenter));
				}

				// oldCenterOfPoint.replace(point, indexOfMyCenter);
				pointsInCenter.get(center.get(indexOfMyCenter)).add(point);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return hasSomethingChanged;
	}

	private void relocateCenter() {
		for (int i = 0; i < center.size(); i++) {
			int size = center.get(i).length;
			double[] sumarray = new double[size];
			double[] totalvalue = new double[size];
			if (!pointsInCenter.get(center.get(i)).isEmpty()) {
				for (double[] d : pointsInCenter.get(center.get(i))) {
					for (int j = 0; j < d.length; j++) {
						if (!Double.isNaN(d[j])) {
							sumarray[j] += d[j];
							totalvalue[j]++;
						}
					}
				}

				for (int l = 0; l < sumarray.length; l++) {
					if (Double.isNaN(sumarray[l])) {
						sumarray[l] = 0;
					} else {
						if (!(sumarray[l] == 0 || totalvalue[l] == 0)) {
							sumarray[l] = sumarray[l] / totalvalue[l];
						} else {
							if (totalvalue[l] == 0) {
								sumarray[l] = Double.NaN;
							}
						}

					}
				}
			}
			ArrayList<double[]> myPoints = pointsInCenter.remove(center.get(i));
			pointsInCenter.put(sumarray, myPoints);
			center.set(i, sumarray);
		}

	}

	private void initializeFollowingCenter() {
		for (int i = 1; i < k; i++) {
			double maxsum = 0;
			int indexOfnewCenter = 0;
			int indexofnewCenterinInit = 0;
			for (int j = 0; j < initpoints.size(); j++) {
				double tmp = 0;
				for (double[] c : center) {
					try {
						tmp += metric.computeDistance(initpoints.get(j), c);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (tmp >= maxsum) {
					maxsum = tmp;
					indexOfnewCenter = PositionOfPointInList.get(initpoints.get(j));
					indexofnewCenterinInit = j;
				}
			}
			initpoints.remove(indexofnewCenterinInit);
			center.add(points.get(indexOfnewCenter));
			
			pointsInCenter.put(points.get(indexOfnewCenter), new ArrayList<double[]>());
		}
	}

	private void clearKMeans() {
		points.clear();
		center.clear();
		pointsInCenter.clear();
		CenterOfPoint.clear();
	}

	private void postProcessing() {
		// take all points that are associated with the Center and get the Index of each
		// one and find the
		// instance that produced this point. Create a Cluster with the center and all
		// Instances that are
		// associated to that center add the Cluster to the kmeansClusterlist.
		for (double[] d : pointsInCenter.keySet()) {
			ArrayList<double[]> poinstOfCenter = pointsInCenter.get(d);
			Cluster c = new Cluster(new ArrayList<ProblemInstance<Instance>>(), new GroupIdentifier(d));
			for (double[] point : poinstOfCenter) {
				int indexOfPoint = PositionOfPointInList.get(point);
				c.addInstance(instances.get(indexOfPoint));
			}
			kmeansCluster.add(c);
		}
	}

	private void printDoubleArray(double[] d) {
		for (int i = 0; i < d.length; i++) {
			System.out.print("|" + d[i] + "|");
			if (d[i] > 1 || d[i] < (-1)) {
				System.out.print("---------------PROBLEM-------------------");
			}
		}
		System.out.println(" ");
	}

}
