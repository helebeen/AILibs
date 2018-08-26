package jaicore.modifiedISAC;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import jaicore.CustomDataTypes.GroupIdentifier;
import jaicore.CustomDataTypes.Performance;
import jaicore.CustomDataTypes.ProblemInstance;
import jaicore.CustomDataTypes.RankingForGroup;
import jaicore.CustomDataTypes.Solution;
import jaicore.CustomDataTypes.Tuple;
import jaicore.GroupBasedRanker.GroupBasedRanker;
import weka.classifiers.CheckClassifier;
import weka.classifiers.Classifier;
import weka.core.Instance;

public class ModifiedISAC extends GroupBasedRanker<double [],Instance, Classifier, Double> {
	
	private HashMap<double[],Integer> positionOfInstance = new HashMap<double[],Integer>();
	private ArrayList<ClassifierRankingForGroup> rankings;
	private ArrayList<Cluster> foundCluster;
	public ModifiedISAC(ModifiedISACInstanceCollector instcoll,
			ModifiedISACTableGeneratorandCompleter table) {
		super(instcoll, table);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void bulidRanker() {
		try {
			ModifiedISACInstanceCollector collector = new ModifiedISACInstanceCollector();
			ArrayList<ProblemInstance<Instance>> collectedInstances = (ArrayList<ProblemInstance<Instance>>) collector.getProblemInstances();
			ArrayList<double[]> toClusterpoints = new ArrayList<double[]>();
			
			Normalizer norm = new Normalizer(collectedInstances);
			norm.setupnormalize();
		
			for (ProblemInstance<Instance> tmp : collectedInstances) {
				toClusterpoints.add(norm.normalize(tmp.getInstance().toDoubleArray()));
			}
			
			ModifiedISACGroupBuilder builder = new ModifiedISACGroupBuilder();
			builder.setPoints(toClusterpoints);
			
			int tmp = 0;
			for(ProblemInstance<Instance> i : collectedInstances) {
				positionOfInstance.put(i.getInstance().toDoubleArray(), tmp);
				tmp++;
			}
			
			//foundCluster = builder.buildGroup(collectedInstances);
			constructRanking(collector);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private void constructRanking(ModifiedISACInstanceCollector collector) {
		for(Cluster c: foundCluster) {
			ArrayList<Solution<Classifier>> ranking = new ArrayList<Solution<Classifier>>();
			int [] tmp = new int[collector.getNumberOfClassifier()];
			double[] clusterMean = new double[collector.getNumberOfClassifier()];
			for(ProblemInstance<Instance> prob :c.getInstances()) {
				ArrayList<Tuple<Classifier, Double>> solutionsOfPoint = collector.getCollectedClassifierandPerformance().get(positionOfInstance.get(prob.getInstance().toDoubleArray()));
				for(int i = 0; i< solutionsOfPoint.size();i++) {
					double perfo = solutionsOfPoint.get(i).getPerformance().getdirctPerformance();
					if(Double.isNaN(perfo)) {
						clusterMean[i] += perfo;
						tmp[i]++;
					}
				}
			}
			for(int i = 0; i<clusterMean.length;i++) {
				clusterMean[i] = clusterMean[i]/tmp[i];
			}
			HashMap<Double,Integer> reminder = new HashMap<Double,Integer>();
			int counter = 0;
			for(double d : clusterMean) {
				reminder.put(d,counter);
				counter++;
			}
			Arrays.sort(clusterMean);
			for(int i = clusterMean.length-1;i>= 0; i--) {
				int index = reminder.get(clusterMean[i]);
				ranking.add(new Solution(collector.getAllClassifier().get(index)));
			}
			rankings.add(new ClassifierRankingForGroup(c.getId(),ranking));
		}
	}
	@Override
	public RankingForGroup<double[], Classifier> getRanking() {
		// TODO Auto-generated method stub
		//TODO muss den Übergabewert auf den Punktändern
//		L1DistanceMetric dist = new L1DistanceMetric();
//		RankingForGroup<double[],Classifier> myRanking;
//		double minDist = Double.MAX_VALUE;
//		for(RankingForGroup<double[],Classifier> rank : rankings) {
//			double computedDist = dist.computeDistance(rank.getIdentifierForGroup().getIdentifier(),point);
//			if(computedDist <= minDist) {
//				myRanking = rank;
//				minDist = computedDist;
//			}
//		}
		return null;//ranking; 
	}

}
