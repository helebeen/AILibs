
import jaicore.modifiedISAC.L1DistanceMetric;
import jaicore.modifiedISAC.ModifiedISACInstanceCollector;
import jaicore.modifiedISAC.Normalizer;

public class Bob {
	public static void main(String[] args) {
		try {
			ModifiedISACInstanceCollector coll = new ModifiedISACInstanceCollector();
			// for(ProblemInstance<Instance> i : coll.getProblemInstances()) {
			// System.out.println( i.getInstance());
			// }
			Normalizer norm = new Normalizer(coll.getProblemInstances());
			norm.setupnormalize();
			double [] toprint = norm.normalize(coll.getProblemInstances().get(coll.getProblemInstances().size()-1).getInstance().toDoubleArray());
			double [] toprint2 = norm.normalize(coll.getProblemInstances().get(21).getInstance().toDoubleArray());
//			for(double d : toprint) {
//				System.out.print(d+"||");
//			}
			L1DistanceMetric dist = new L1DistanceMetric();
			System.out.println(dist.computeDistance(toprint,toprint2));
//			double[] test = norm.getbasis();
//			
//			for (double i : test) {
//				Math.round(i);
//				System.out.print(i + "||");
//			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
