
import java.util.ArrayList;

import jaicore.CustomDataTypes.ProblemInstance;
import jaicore.CustomDataTypes.Solution;
import jaicore.modifiedISAC.ClassifierRankingForGroup;
import jaicore.modifiedISAC.Cluster;
import jaicore.modifiedISAC.L1DistanceMetric;
import jaicore.modifiedISAC.ModifiedISAC;
import jaicore.modifiedISAC.ModifiedISACInstanceCollector;
import jaicore.modifiedISAC.Normalizer;
import jaicore.modifiedISAC.ModifiedISACgMeans;
import weka.core.Instance;

public class Bob {	
	private static void printDoubleArray(double[] d) {
		for (int i = 0; i < d.length; i++) {
			System.out.print("|" + d[i] + "|");
		}
		System.out.println(" ");
	}
	public static void main(String[] args) {
		try {
		ModifiedISACInstanceCollector coll = new ModifiedISACInstanceCollector();
			
			// for(ProblemInstance<Instance> i : coll.getProblemInstances()) {
			// System.out.println( i.getInstance());
			// }
			
//			Normalizer norm = new Normalizer(coll.getProblemInstances());
//			norm.setupnormalize();
			
			// double[] toprint = norm.normalize(coll.getProblemInstances().get(coll.getProblemInstances().size() - 1)
			//		.getInstance().toDoubleArray());
			// double[] toprint2 = norm.normalize(coll.getProblemInstances().get(21).getInstance().toDoubleArray());
			// for(double d : toprint) {
			// System.out.print(d+"||");
			// }
			
			//System.out.println(dist.computeDistance(toprint, toprint2));
			// double[] test = norm.getbasis();
			//
			// for (double i : test) {
			// Math.round(i);
			// System.out.print(i + "||");
			// }
//			ArrayList<double[]> toClusterpoints = new ArrayList<double[]>();
//			for (ProblemInstance<Instance> test : coll.getProblemInstances()) {
//				toClusterpoints.add(norm.normalize(test.getInstance().toDoubleArray()));
//			}
//			for(double[] d :toClusterpoints) {
//				for(int i = 0; i < d.length;i++) {
//					System.out.print("|"+d[i]+"|");
//					if(d[i]>1 || d[i]<(-1)) {
//						System.out.print("---------------PROBLEM-------------------");
//					}
//				}
//				System.out.println(" ");
//			}
			
//			ArrayList<ProblemInstance<Instance>> instnaces = (ArrayList<ProblemInstance<Instance>>) coll.getProblemInstances();
//			ModifiedISACgMeans tester = new ModifiedISACgMeans(toClusterpoints, instnaces);
//			tester.gmeanscluster();
			
			ModifiedISAC isac = new ModifiedISAC(coll, null);
			isac.bulidRanker();
			
			ArrayList<ClassifierRankingForGroup> rankings = isac.getRankings();
			for(ClassifierRankingForGroup rank : rankings) {
				System.out.print("Center des Clusters: "); 
				printDoubleArray(rank.getIdentifierForGroup().getIdentifier());
				int tmp = 1;
				for(Solution<String> solu : rank.getRanking()) {
					System.out.println("Nummer "+tmp+" "+ solu.getSolution());
					tmp++;
				}
				System.out.println("--------------------------------------------------------");
			}
			
			//ArrayList<Cluster> test = tester.kmeanscluster();
			//for (Cluster c : test) {
				//System.out.print(c.getId().getIdentifier());
				//for (ProblemInstance<Instance> i : c.getInstances()) {
				//	System.out.print(i.getInstance().toDoubleArray());
				//}

			//}


		} catch (Exception e) {
			System.out.println("Hallo Hier ist ein Fehler in der main");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


}
