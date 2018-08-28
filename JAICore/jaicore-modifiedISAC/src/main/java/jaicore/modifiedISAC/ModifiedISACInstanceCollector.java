package jaicore.modifiedISAC;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import DataManager.IInstanceCollector;
import jaicore.CustomDataTypes.Performance;
import jaicore.CustomDataTypes.ProblemInstance;
import jaicore.CustomDataTypes.Solution;
import jaicore.CustomDataTypes.Tuple;
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
	private ArrayList<ArrayList<Tuple<Solution<String>,Performance<Double>>>> collectedClassifierandPerformance;
	
	private int numberOfClassifier=21;
	private ArrayList<String> allClassifier = new ArrayList<String>();



	private ArrayList<ProblemInstance<Instance>> collectedInstances = new ArrayList<ProblemInstance<Instance>>();
	private static ArrayList<String> AtributesofTrainingsdata = new ArrayList<String>();

	/**
	 * @return Returns the attributes of the collected and processed Instances as well
	 * as their order
	 */
	public static ArrayList<String> getAtributesofTrainingsdata() {
		return AtributesofTrainingsdata;
	}
	
	public  ArrayList<ArrayList<Tuple<Solution<String>,Performance<Double>>>> getCollectedClassifierandPerformance() {
		return collectedClassifierandPerformance;
	}
	
	public int getNumberOfClassifier() {
		return numberOfClassifier;
	}
	
	public ArrayList<String> getAllClassifier() {
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
		
		collectedClassifierandPerformance = new ArrayList<ArrayList<Tuple<Solution<String>,Performance<Double>>>>();
		
		for(Instance i : data) {
			ArrayList<Tuple<Solution<String>,Performance<Double>>> pandc = new ArrayList<Tuple<Solution<String>,Performance<Double>>>();	
			for(int j = i.numAttributes()-1; j>104; j--) {
				Solution<String> classi = new Solution<String>(i.attribute(j).name());
				Performance<Double> perfo = new Performance<Double>(i.value(j));
				Tuple<Solution<String>, Performance<Double>> tup = new Tuple<Solution<String>,Performance<Double>>(classi,perfo);
				pandc.add(tup);
			}
			collectedClassifierandPerformance.add(pandc);
		}
		
		Instance inst = data.get(0);
		for(int i = inst.numAttributes()-1;i>104;i--) {
			allClassifier.add(inst.attribute(i).name());
		}
		for(String solu :allClassifier) {
			System.out.println(solu);
		}
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
	public void setNumberOfClassifier(int number) {
		this.numberOfClassifier = number;
	}
	@Override
	public List<ProblemInstance<Instance>> getProblemInstances() {
		return this.collectedInstances;
	}


}
