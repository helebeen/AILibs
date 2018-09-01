package jaicore.modifiedISAC.evalutation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import jaicore.CustomDataTypes.RankingForGroup;
import jaicore.CustomDataTypes.Solution;
import jaicore.modifiedISAC.ModifiedISAC;
import jaicore.modifiedISAC.ModifiedISACInstanceCollector;
import weka.core.Instance;
import weka.core.Instances;

public class modifiedISACEvaluator {
	public static double[] evaluateModifiedISACLeaveOneOut(Instances data) {
		double[] results = new double[data.numInstances()];
		double[] overallranking = new double[22];
		int[] totalvalue = new int[22];

		for(Instance i : data) {
			int tmp = 0;
			for(int l = i.numAttributes()-1; l >= 104;l--) {
				if(!Double.isNaN(i.value(l))){
					overallranking[tmp]= i.value(l);
					totalvalue[tmp]++;
				}	
				tmp++;
			}
		}
		for(int i = 0; i<overallranking.length;i++) {
			overallranking[i] = overallranking[i]/totalvalue[i];
		}
		HashMap<String,Double> overall = new HashMap<String,Double>();
		for(int i = 0; i<overallranking.length;i++) {
			overall.put(data.get(0).attribute(data.numAttributes()-1-i).name(),overallranking[i]);
		}

		for (int i = 0; i < data.numInstances(); i++) {
			HashMap<String, Integer> positionInRanking = new HashMap<String, Integer>();
			Instances trainingData = new Instances(data);
			Instances testprep = new Instances(data);
			Instances tester = new Instances(data);
			Instance reminder = tester.get(0);
			
			trainingData.delete(i);

			ModifiedISACInstanceCollector collect = new ModifiedISACInstanceCollector(trainingData, 104, 125);
			ModifiedISAC isac = new ModifiedISAC(collect, null);
			isac.bulidRanker();
			HashMap<String, Double> ClassandPerfo = new HashMap<String, Double>();
			for (int p = testprep.numAttributes() - 1; p >= 104; p--) {
				ClassandPerfo.put(testprep.get(i).attribute(p).name(), testprep.get(i).value(p));
				testprep.deleteAttributeAt(p);
			}
			testprep.deleteAttributeAt(0);
			Instance inst = testprep.get(i);
			RankingForGroup<double[], String> ranking = isac.getRanking(inst);
			// get the ground truth ranking as string list

			double[] rankingTruth = new double[22];
			int tmp = 0;
			
			
			
			while (!ClassandPerfo.isEmpty()) {
//				System.out.println("size left " + ClassandPerfo.size());
				double maxPerfo = 0;
				String myClassi = "";
				for (String classi : ClassandPerfo.keySet()) {
					if (!ClassandPerfo.get(classi).isNaN()) {
//						System.out.println("double nicht nan");
						if (ClassandPerfo.get(classi) >= maxPerfo) {
							maxPerfo = ClassandPerfo.get(classi);
							myClassi = classi;
						}
					}
				}
//				System.out.println("myClassi is null " + myClassi == null);
				if (myClassi.isEmpty()) {
//					System.out.println("Hallllllllllllllo");
					int nans = tmp;
					for (String str : ClassandPerfo.keySet()) {
						positionInRanking.put(str, nans);
						rankingTruth[nans] = nans;
						nans++;
					}
					ClassandPerfo.clear();
				} else {
//					System.out.println(tmp);
					rankingTruth[tmp] = tmp;
					positionInRanking.put(myClassi, tmp);
					ClassandPerfo.remove(myClassi);
					tmp++;
				}
				
			}
			
			
//			HashMap<String,Double> loopoverall = (HashMap<String, Double>) overall.clone();
//			double finishedoverallranking[] = new double[22];
//			HashMap<String,Integer> rankingoverall = new HashMap<String,Integer>();
//			int loopcounter = 0;
//			while(!loopoverall.isEmpty()) {
//				double maxPerfo = 0;
//				String myClassifier = "";
//				for(String calssi: loopoverall.keySet()) {
//					if(loopoverall.get(calssi)>=maxPerfo) {
//						maxPerfo = loopoverall.get(calssi);
//						myClassifier = calssi;
//					}
//				}
//				finishedoverallranking[loopcounter] = loopcounter;
//				rankingoverall.put(myClassifier, loopcounter);
//				loopoverall.remove(myClassifier);
//				loopcounter++;
//			}
			
			// get the ranking as string list
			ArrayList<String> rankingAsStringList = new ArrayList<String>();
			for (Solution<String> rank : ranking.getRanking()) {
				rankingAsStringList.add(rank.getSolution());
			}
			double[] rankingFromMyMethod = new double[22];
			int intermidiate = 0;
			for (String classi : rankingAsStringList) {
				rankingFromMyMethod[intermidiate] = positionInRanking.get(classi);
//				rankingFromMyMethod[intermidiate] = rankingoverall.get(classi);
				intermidiate++;
			}
//			
			int size = 3;
			double[] rankingtruth = new double[size];
			double[] myranking = new double[size];
			for(int u = 0; u < size; u++) {
				rankingtruth[u] = rankingTruth[u];
				myranking[u] = (rankingFromMyMethod.length-1)-rankingFromMyMethod[u];
			}
//			KendallsCorrelation test = new KendallsCorrelation();
//			double thisResult = test.correlation(rankingtruth, myranking);
//			double thisResult = test.correlation(rankingTruth, rankingFromMyMethod);
//			double thisResult = test.correlation(finishedoverallranking, rankingFromMyMethod);
			
			// double value as result of this evaluation
			HashMap<Integer,String> test = new HashMap<Integer,String>();
			for(String str: positionInRanking.keySet()) {
				test.put(positionInRanking.get(str),str);
			}
//			ArrayList<Double> difference1 = new ArrayList<Double>();
//			ArrayList<Double> difference2 = new ArrayList<Double>();
			double[] difference1 = new double[size];
			double[] difference2 = new double[size];
			ArrayList<String> top3truth = new ArrayList<String>();
			ArrayList<String> top3my = new ArrayList<String>();
			for(int h = 0; h<size;h++) {
				String classitruth = test.get(new Integer((int)rankingtruth[h]));
				top3truth.add(classitruth);
				String mytruth = test.get(new Integer((int)myranking[h]));
				top3my.add(mytruth);
				double perfotruth = 0;
				double perfomy = 0;		
				for(int t = 125; t>=104;t--) {
					if(reminder.attribute(t).name().equals(classitruth)) {
						perfotruth = reminder.value(t);
					}
					
					if(reminder.attribute(t).name().equals(mytruth)) {
						perfomy = reminder.value(t);
					}
				}
				difference1[h] = perfotruth;
				difference2[h] = perfomy;
			}
			Arrays.sort(difference2);
			System.out.println("Das betrachtete Datenset: "+(i+1));
			System.out.print("Der Verlust zweichen Platz eins der optimal Lösung und der besten meiner Lösungen: ");
			System.out.println((Math.rint((1000.0 *(difference1[0]-difference2[size-1]))))/1000.0);
			System.out.print("Der Verlust gegen meine schlecteste Lösung in den top 3: ");
			System.out.println((Math.rint((1000.0 *(difference1[0]-difference2[0]))))/1000.0);
//				difference1.add(perfotruth);
//				difference2.add(perfomy);
//			}
//			double maxPerfo = Double.MIN_VALUE;
//			for(int h = 0; h<difference2.size();h++) {
//				if(difference2.get(h)>= maxPerfo) {
//					maxPerfo = difference2.get(h);
//				}
//			}
//			System.out.println("Der Verlust "+(difference1.get(0)-maxPerfo));
			//System.out.println(positionInRanking.toString());
			System.out.println("Bester in der optimalen Lösung "+difference1[0]);
			System.out.println("Der beste meiner top3 "+difference2[0]);
			System.out.println("Das wahre ranking: "+top3truth.toString());
			System.out.println("Mein ranking: "+top3my.toString());
			System.out.println(Arrays.toString(rankingtruth));
			System.out.println(Arrays.toString(myranking));
			System.out.println(" ");
			results[i] = 4;
			//System.out.println("Durchlauf nummer "+i);		
		}
		return results;
	}
}
