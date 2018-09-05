package jaicore.modifiedISAC.evalutation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.apache.commons.math3.stat.correlation.KendallsCorrelation;

import jaicore.CustomDataTypes.RankingForGroup;
import jaicore.CustomDataTypes.Solution;
import jaicore.modifiedISAC.ModifiedISAC;
import jaicore.modifiedISAC.ModifiedISACInstanceCollector;
import weka.core.Instance;
import weka.core.Instances;

public class modifiedISACEvaluator {
	private static double [] platz1my;
	private static double [] platz1overall;
	public static double[] getPlatz1my() {
		return platz1my;
	}
	public static double[] getPlatz1overall() {
		return platz1overall;
	}
	public static double[] evaluateModifiedISACLeaveOneOut(Instances data) {
		double[] results = new double[data.numInstances()];
		platz1my = new double[data.numInstances()];
		platz1overall= new double[data.numInstances()];
		for (int i = 0; i < data.numInstances(); i++) {
			HashMap<String, Integer> positionInRanking = new HashMap<String, Integer>();
			Instances trainingData = new Instances(data);
			Instances testprep = new Instances(data);
			Instances tester = new Instances(data);
			Instance reminder = tester.get(i);
			
			trainingData.delete(i);
			double[] overallranking = new double[22];
			int[] totalvalue = new int[22];

			for(Instance inst : trainingData) {
				int tmp = 0;
				for(int l = inst.numAttributes()-1; l >= 104;l--) {
					if(!Double.isNaN(inst.value(l))){
						overallranking[tmp]+= inst.value(l);
						totalvalue[tmp]++;
					}	
					tmp++;
				}
			}
			for(int o = 0; o<overallranking.length;o++) {
				overallranking[o] = overallranking[o]/totalvalue[o];
			}
			HashMap<String,Double> overall = new HashMap<String,Double>();
			int exampel = 0;
			if(i == 0) {
				exampel = 1;
			}
			for(int p = 0; p<overallranking.length;p++) {
				overall.put(data.get(exampel).attribute((data.numAttributes()-1)-p).name(),overallranking[p]);
			}
			ModifiedISACInstanceCollector collect = new ModifiedISACInstanceCollector(trainingData, 104, 125);
			ModifiedISAC isac = new ModifiedISAC(collect, null);
			isac.bulidRanker();
			HashMap<String, Double> ClassandPerfo = new HashMap<String, Double>();
			for (int p = testprep.numAttributes() - 1; p >= 104; p--) {
				ClassandPerfo.put(testprep.get(i).attribute(p).name(), testprep.get(i).value(p));
//				System.out.print("Das ist der Classifier "+ testprep.get(i).attribute(p).name());
//				System.out.println(" Performance "+testprep.get(i).value(p));
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
				double maxPerfo = Double.MIN_VALUE;
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
			int size = 3;
			double[] difference3 = new double[size];
			HashMap<String,Double> loopoverall = (HashMap<String, Double>) overall.clone();
			double finishedoverallranking[] = new double[22];
			HashMap<String,Integer> rankingoverall = new HashMap<String,Integer>();
			int loopcounter = 0;
			while(!loopoverall.isEmpty()) {
				double maxPerfo = Double.MIN_VALUE;
				String myClassifier = "";
				for(String calssi: loopoverall.keySet()) {
					if(loopoverall.get(calssi)>=maxPerfo) {
						maxPerfo = loopoverall.get(calssi);
						myClassifier = calssi;
					}
				}
				if(loopcounter<size) {
					difference3[loopcounter]=maxPerfo;
				}
				finishedoverallranking[loopcounter] = positionInRanking.get(myClassifier);
				rankingoverall.put(myClassifier, loopcounter);
				loopoverall.remove(myClassifier);
				loopcounter++;
			}
			
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
			
			double[] rankingtruth = new double[size];
			double[] myranking = new double[size];
			for(int u = 0; u < size; u++) {
				rankingtruth[u] = rankingTruth[u];
				myranking[u] = (rankingFromMyMethod.length-1)-rankingFromMyMethod[u];
//				myranking[u] = rankingFromMyMethod[u];
			}
			for(int t = 0; t< rankingFromMyMethod.length; t++) {
				rankingFromMyMethod[t]= (rankingFromMyMethod.length-1)-rankingFromMyMethod[t];
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
			System.out.println("Das betrachtete Datenset: "+(i+1));
			System.out.print("Der Verlust zweichen Platz eins der optimal Lösung und der besten meiner Lösungen: ");
			System.out.println((Math.rint((1000.0 *(difference1[0]-difference2[0]))))/1000.0);
			System.out.println("Mein Platz 3: "+difference2[size-1]);
			System.out.print("Der Verlust gegen meinen Platz 3: ");
			System.out.println((Math.rint((1000.0 *(difference1[0]-difference2[size-1]))))/1000.0);
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
			System.out.println("Bester in der optimalen Lösung: "+difference1[0]);
			System.out.println("Mein Platz eins:  "+difference2[0]);
			double platz1 = difference2[0];
			platz1my[i] = difference1[0]-platz1;
			System.out.println(difference3[0]);
			platz1overall[i] = difference1[0]-difference3[0];
			System.out.println("Das wahre ranking: "+top3truth.toString());
			System.out.println("Mein ranking: "+top3my.toString());
			Arrays.sort(difference2);
			System.out.println("Beste Performance "+difference2[size-1]);
			
			System.out.println("Ist die beste Performance Plazt 1? "+(platz1 == difference2[size-1]));
			System.out.println(Arrays.toString(rankingtruth));
			System.out.println(Arrays.toString(myranking));
			System.out.println(" ");
			KendallsCorrelation correl = new KendallsCorrelation();
			results[i] = correl.correlation(rankingTruth, rankingFromMyMethod);
			
			//System.out.println("Durchlauf nummer "+i);		
		}
		return results;
	}
}
