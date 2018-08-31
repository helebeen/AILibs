package jaicore.modifiedISAC;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import jaicore.CustomDataTypes.Performance;
import jaicore.CustomDataTypes.ProblemInstance;
import jaicore.CustomDataTypes.RankingForGroup;
import jaicore.CustomDataTypes.Solution;
import jaicore.CustomDataTypes.Tuple;
import jaicore.GroupBasedRanker.GroupBasedRanker;
import weka.core.Instance;

public class ModifiedISAC extends GroupBasedRanker<double[], Instance, String, Double> {

	private HashMap<double[], Integer> positionOfInstance = new HashMap<double[], Integer>();
	private ArrayList<ClassifierRankingForGroup> rankings = new ArrayList<ClassifierRankingForGroup>();
	private ArrayList<Cluster> foundCluster;
	private Normalizer norm;

	public ModifiedISAC(ModifiedISACInstanceCollector instcoll, ModifiedISACTableGeneratorandCompleter table) {
		super(instcoll, table);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void bulidRanker() {
		try {
			ModifiedISACInstanceCollector collector = new ModifiedISACInstanceCollector();
			ArrayList<ProblemInstance<Instance>> collectedInstances = (ArrayList<ProblemInstance<Instance>>) collector
					.getProblemInstances();
			ArrayList<double[]> toClusterpoints = new ArrayList<double[]>();

			this.norm = new Normalizer(collectedInstances);
			norm.setupnormalize();

			for (ProblemInstance<Instance> tmp : collectedInstances) {
				toClusterpoints.add(norm.normalize(tmp.getInstance().toDoubleArray()));
			}

			ModifiedISACGroupBuilder builder = new ModifiedISACGroupBuilder();
			builder.setPoints(toClusterpoints);

			int tmp = 0;
			for (ProblemInstance<Instance> i : collectedInstances) {
				positionOfInstance.put(i.getInstance().toDoubleArray(), tmp);
				tmp++;
			}

			foundCluster = (ArrayList<Cluster>) builder.buildGroup(collectedInstances);
			constructRanking(collector);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void constructRanking(ModifiedISACInstanceCollector collector) {
		for (Cluster c : foundCluster) {
			ArrayList<Solution<String>> ranking = new ArrayList<Solution<String>>();
			int[] tmp = new int[collector.getNumberOfClassifier()];
			double[] clusterMean = new double[collector.getNumberOfClassifier()];
			for (ProblemInstance<Instance> prob : c.getInstances()) {
				int myIndex = 0;
				for (double[] d : positionOfInstance.keySet()) {
					if (Arrays.equals(d, prob.getInstance().toDoubleArray())) {
						myIndex = positionOfInstance.get(d);
					}
				}
				ArrayList<Tuple<Solution<String>, Performance<Double>>> solutionsOfPoint = collector
						.getCollectedClassifierandPerformance().get(myIndex);
				for (int i = 0; i < solutionsOfPoint.size(); i++) {

					double perfo = solutionsOfPoint.get(i).getPerformance().getdirctPerformance();
					if (!Double.isNaN(perfo)) {

						clusterMean[i] += perfo;
						tmp[i]++;
					}
				}
			}
			for (int i = 0; i < clusterMean.length; i++) {
				clusterMean[i] = clusterMean[i] / tmp[i];
			}
			// HashMap<String,Integer> reminder = new HashMap<String,Integer>();
			// for(int i =0; i< clusterMean.length;i++) {
			// reminder.put(collector.getAllClassifier().get(i),i);
			// }
			// Arrays.sort(clusterMean);
			// for(int i = clusterMean.length-1;i>= 0; i--) {
			// int index = reminder.get(collector.getAllClassifier().get(i));
			// ranking.add(new Solution<String>(collector.getAllClassifier().get(index)));
			// }
			ArrayList<String> testtest = collector.getAllClassifier();
			HashMap<String, Double> test = new HashMap<String, Double>();
			for (int i = 0; i < clusterMean.length; i++) {
				test.put(testtest.get(i), clusterMean[i]);
			}

			while (!test.isEmpty()) {
				double max = Double.MAX_VALUE;
				String classi = null;
				for (String str : test.keySet()) {
					double candidate = test.get(str);
					if (candidate <= max) {
						classi = str;
						max = candidate;
					}
				}
				ranking.add(new Solution<String>(classi));
				test.remove(classi);
			}
			rankings.add(new ClassifierRankingForGroup(c.getId(), ranking));

		}
	}

	@Override
	public RankingForGroup<double[], String> getRanking(Instance prob) {
		RankingForGroup<double[], String> myRanking = null;
		try {
			double[] point = norm.normalize( prob.toDoubleArray());
			L1DistanceMetric dist = new L1DistanceMetric();
			double minDist = Double.MAX_VALUE;
			for (RankingForGroup<double[], String> rank : rankings) {
				double computedDist= dist.computeDistance(rank.getIdentifierForGroup().getIdentifier(), point);
				
				if (computedDist <= minDist) {
					myRanking = rank;
					minDist = computedDist;
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return myRanking;
	}

	public ArrayList<ClassifierRankingForGroup> getRankings() {
		return this.rankings;
	}
}
