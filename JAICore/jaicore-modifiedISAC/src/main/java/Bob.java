
import java.util.ArrayList;

import jaicore.CustomDataTypes.ProblemInstance;
import jaicore.modifiedISAC.Cluster;
import jaicore.modifiedISAC.GMeansForModifiedISAC;
import jaicore.modifiedISAC.L1DistanceMetric;
import jaicore.modifiedISAC.ModifiedISACInstanceCollector;
import jaicore.modifiedISAC.Normalizer;
import weka.core.Instance;

public class Bob {
	public static void main(String[] args) {
		System.out.println("Hallo");
		try {
			ModifiedISACInstanceCollector coll = new ModifiedISACInstanceCollector();
			// for(ProblemInstance<Instance> i : coll.getProblemInstances()) {
			// System.out.println( i.getInstance());
			// }
			System.out.println("Hallo");
			Normalizer norm = new Normalizer(coll.getProblemInstances());
			norm.setupnormalize();
			double[] toprint = norm.normalize(coll.getProblemInstances().get(coll.getProblemInstances().size() - 1)
					.getInstance().toDoubleArray());
			double[] toprint2 = norm.normalize(coll.getProblemInstances().get(21).getInstance().toDoubleArray());
			// for(double d : toprint) {
			// System.out.print(d+"||");
			// }
			L1DistanceMetric dist = new L1DistanceMetric();
			System.out.println("Hallo");
			//System.out.println(dist.computeDistance(toprint, toprint2));
			// double[] test = norm.getbasis();
			//
			// for (double i : test) {
			// Math.round(i);
			// System.out.print(i + "||");
			// }
			System.out.println("Hallo");
			ArrayList<double[]> toClusterpoints = new ArrayList<double[]>();
			for (ProblemInstance<Instance> test : coll.getProblemInstances()) {
				toClusterpoints.add(norm.normalize(test.getInstance().toDoubleArray()));
			}
			System.out.println("Hallo");
			ArrayList<ProblemInstance<Instance>> instnaces = (ArrayList<ProblemInstance<Instance>>) coll.getProblemInstances();
			System.out.println("Hallo");
			GMeansForModifiedISAC tester = new GMeansForModifiedISAC(toClusterpoints, dist, 4, instnaces);
			ArrayList<Cluster> test = tester.kmeanscluster();
			System.out.println("Hallo");
			for (Cluster c : test) {
				System.out.print(c.getId().getIdentifier());
				for (ProblemInstance<Instance> i : c.getInstances()) {
					System.out.print(i.getInstance().toDoubleArray());
				}

			}

		} catch (Exception e) {
			System.out.println("Hallo");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
