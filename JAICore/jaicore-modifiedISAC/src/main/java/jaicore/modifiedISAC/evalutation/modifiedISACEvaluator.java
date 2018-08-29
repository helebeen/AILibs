package jaicore.modifiedISAC.evalutation;

import jaicore.CustomDataTypes.RankingForGroup;
import jaicore.modifiedISAC.ModifiedISAC;
import jaicore.modifiedISAC.ModifiedISACInstanceCollector;
import weka.core.Instance;
import weka.core.Instances;

public class modifiedISACEvaluator {
	public static double[] evaluateModifiedISACLeaveOneOut(Instances data) {
		double[] results = new double[data.numInstances()];
		
		for (int i = 0; i < data.numInstances(); i++) {
			Instances trainingData = new Instances(data);
			Instance testInstance = trainingData.get(i);
			trainingData.delete(i);
			
			ModifiedISACInstanceCollector collect = new ModifiedISACInstanceCollector(trainingData, 104, 125);
			ModifiedISAC isac = new ModifiedISAC(collect, null);
			isac.bulidRanker();
			RankingForGroup<double[], String> ranking = isac.getRanking(testInstance);
			// get the ground truth ranking as string list
			
			// get the ranking as string list
			
			// double value as result of this evaluation
			double thisResult=0;
			results[i]=thisResult;
		}
		return null;
	}
}
