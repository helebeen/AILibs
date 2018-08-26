package jaicore.modifiedISAC;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import DataManager.IInstanceCollector;
import jaicore.CustomDataTypes.Performance;
import jaicore.CustomDataTypes.ProblemInstance;
import jaicore.CustomDataTypes.Solution;
import jaicore.CustomDataTypes.Tuple;
import weka.classifiers.CheckClassifier;
import weka.classifiers.Classifier;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

/**
 * @author Helen
 * This class should collect Instances in form of metafeatures form a file.
 */
public class ModifiedISACInstanceCollector implements IInstanceCollector<Instance> {

	/**
	 * The collected and processed Instances
	 */
	private ArrayList<ArrayList<Tuple<Classifier,Double>>> collectedClassifierandPerformance;
	//TODO implemntieren !!
	private int numberOfClassifier;
	private ArrayList<Classifier> allClassifier;



	private ArrayList<ProblemInstance<Instance>> collectedInstances = new ArrayList<ProblemInstance<Instance>>();
	private static ArrayList<String> AtributesofTrainingsdata = new ArrayList<String>();

	/**
	 * @return Returns the attributes of the collected and processed Instances as well
	 * as their order
	 */
	public static ArrayList<String> getAtributesofTrainingsdata() {
		return AtributesofTrainingsdata;
	}
	
	public ArrayList<ArrayList<Tuple<Classifier,Double>>> getCollectedClassifierandPerformance() {
		return collectedClassifierandPerformance;
	}
	
	public int getNumberOfClassifier() {
		return numberOfClassifier;
	}
	
	public ArrayList<Classifier> getAllClassifier() {
		return allClassifier;
	}
	/** This constructor is used if a own file is used to extracted the training instances
	 * The Instances has to only contain the metafeatures
	 * @param filename
	 * @throws Exception
	 */
	public ModifiedISACInstanceCollector(String filename) throws Exception {
		DataSource customsource = new DataSource(filename);
		Instances customdata = customsource.getDataSet();
		for (Instance i : customdata) {
			collectedInstances.add(new ProblemInstance<Instance>(i));
		}
	}

	/** This constructor is used if the default file should be used. Parts of the Instances
	 * have to be removed for the further computation. (The dataset-ID, Classifers with performance )
	 * @throws Exception
	 */
	public ModifiedISACInstanceCollector() throws Exception {
		InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("metaData_smallDataSets_computed.arff");
		DataSource source = new DataSource(inputStream);
		Instances data = source.getDataSet();
		int tmp = 0;
//		for(Instance i : data) {
//			ArrayList<Tuple<Solution<Classifier>,Performance<Double>>> pandc = new ArrayList<Tuple<Solution<Classifier>,Performance<Double>>>();	
//			for(int j = i.numAttributes()-1; j>=103; j++) {
//				Solution<Classifier> classi = new Solution<Classifier>(Classifier.);
//				Performance<Double> perfo = new Performance<Double>(i.attribute(j));
//				Tupel<Solution<Classifier>,Performance<Double>>> tup = new Tuple<Solution<Classifier>,Performance<Double>>>(classi,perfo);
//				pandac.add(tup);
//			}
//			collectedClassifierandPerformance.add(pandac);
//			tmp++;
//		}
		for(int i = 0; i<data.numInstances();i++) {
			for(int j = data.numAttributes()-1; j>=103;j--) {
				data.get(i);
			}
		}
		
		data.deleteAttributeAt(0);
		for (int j = data.numAttributes() - 1; j >= 103; j--) {
			data.deleteAttributeAt(j);
		}
		for(int i = 0; i<data.numAttributes();i++) {
			AtributesofTrainingsdata.add(data.attribute(i).toString());			
		}
		for (Instance i : data) {
			collectedInstances.add(new ProblemInstance<Instance>(i));
		}
	}

	@Override
	public List<ProblemInstance<Instance>> getProblemInstances() {
		return this.collectedInstances;
	}


}
