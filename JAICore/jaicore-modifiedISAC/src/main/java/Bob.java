
import java.io.InputStream;
import java.util.ArrayList;

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
		printDoubleArray(d);
	}

}
