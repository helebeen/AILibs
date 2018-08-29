package jaicore.modifiedISAC.evalutation;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.math3.stat.correlation.KendallsCorrelation;

import jaicore.CustomDataTypes.RankingForGroup;
import jaicore.CustomDataTypes.Solution;
import jaicore.modifiedISAC.ModifiedISAC;
import jaicore.modifiedISAC.ModifiedISACInstanceCollector;
import weka.core.Instance;
import weka.core.Instances;

public class modifiedISACEvaluator {
	public static double[] evaluateModifiedISACLeaveOneOut(Instances data) {
		double[] results = new double[data.numInstances()];
		
		for (int i = 0; i < data.numInstances(); i++) {
			HashMap<String,Integer> positionInRanking = new HashMap<String,Integer>();
			Instances trainingData = new Instances(data);
			Instance testInstance = trainingData.get(i);
			trainingData.delete(i);
			
			ModifiedISACInstanceCollector collect = new ModifiedISACInstanceCollector(trainingData, 104, 125);
			ModifiedISAC isac = new ModifiedISAC(collect, null);
			isac.bulidRanker();
			RankingForGroup<double[], String> ranking = isac.getRanking(testInstance);
			// get the ground truth ranking as string list
			HashMap<String,Double> ClassandPerfo = new HashMap<String,Double>();
			for(int j = 1; j<104;j++) {
				ClassandPerfo.put(testInstance.attribute(j).name(),testInstance.value(j));
			}
			double[] rankingTruth = new double[21];
			int tmp = 0; 
		
			while(!ClassandPerfo.isEmpty()) {
				double maxPerfo = Double.MAX_VALUE;
				String myClassi = null;
				for(String classi: ClassandPerfo.keySet()) {
					if(!ClassandPerfo.get(classi).isNaN()) {
						if(ClassandPerfo.get(classi)>=maxPerfo) {
							maxPerfo = ClassandPerfo.get(classi);
							myClassi = classi;
							
						}
					}
				}
				rankingTruth[tmp] = tmp;
				positionInRanking.put(myClassi,tmp);
				ClassandPerfo.remove(myClassi);
				tmp++;
			}
			// get the ranking as string list
				ArrayList<String> rankingAsStringList = new ArrayList<String>();
				for(Solution<String> rank: ranking.getRanking()) {
					rankingAsStringList.add(rank.getSolution());
				}
				double[] rankingFromMyMethod = new double[21];
				int intermidiate = 0;
				for(String classi : rankingAsStringList) {
					rankingFromMyMethod[intermidiate] = positionInRanking.get(classi);
				}
				KendallsCorrelation test= new KendallsCorrelation();
				double thisResult = test.correlation(rankingTruth, rankingFromMyMethod);
			// double value as result of this evaluation

			results[i]=thisResult;
		}
		return results;
	}
}
