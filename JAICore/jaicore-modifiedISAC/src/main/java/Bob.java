
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import jaicore.CustomDataTypes.Solution;
import jaicore.modifiedISAC.ClassifierRankingForGroup;
import jaicore.modifiedISAC.ModifiedISAC;
import jaicore.modifiedISAC.ModifiedISACInstanceCollector;
import jaicore.modifiedISAC.evalutation.modifiedISACEvaluator;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class Bob {
	private static void printDoubleArray(double[] d) {
		for (int i = 0; i < d.length; i++) {
			System.out.print("|" + d[i] + "|");
		}
		System.out.println(" ");
	}
	
	public static void normalRun() {
		try {
			ModifiedISACInstanceCollector coll = new ModifiedISACInstanceCollector();

			ModifiedISAC isac = new ModifiedISAC(coll, null);
			isac.bulidRanker();

			ArrayList<ClassifierRankingForGroup> rankings = isac.getRankings();
			for (ClassifierRankingForGroup rank : rankings) {
				System.out.print("Center des Clusters: ");
				printDoubleArray(rank.getIdentifierForGroup().getIdentifier());
				int tmp = 1;
				for (Solution<String> solu : rank.getRanking()) {
					System.out.println("Nummer " + tmp + " " + solu.getSolution());
					tmp++;
				}
				System.out.println("--------------------------------------------------------");
			}

		} catch (Exception e) {
			System.out.println("Hallo Hier ist ein Fehler in der main");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String[] args) throws Exception {
		InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("metaData_smallDataSets_computed.arff");
		DataSource source = new DataSource(inputStream);
		Instances data = source.getDataSet();
//		normalRun();
		double[] d = modifiedISACEvaluator.evaluateModifiedISACLeaveOneOut(data);
		double tmp = Arrays.stream(d).filter(x -> x != Double.NaN).average().getAsDouble();
		System.out.println("Average correlation: "+tmp);
		System.out.println("Max Kendall correlation "+Arrays.stream(d).max().getAsDouble());
		System.out.println("Min Kendall correlation "+Arrays.stream(d).min().getAsDouble());
		System.out.println("The correlations that are greater than 0 "+Arrays.stream(d).filter(x ->  { return x > 0.0; }).mapToObj(x -> { return Double.valueOf(x);}).collect(Collectors.toList()));
		System.out.println(" ");
		System.out.println(Arrays.toString(d));
		System.out.println("My first place vs. opt first place in acc "+Arrays.toString(modifiedISACEvaluator.getPlatz1my()));
		System.out.println("The overall first place vs. opt first place in acc "+Arrays.toString(modifiedISACEvaluator.getPlatz1overall()));
		//printDoubleArray(d);
	}

}
