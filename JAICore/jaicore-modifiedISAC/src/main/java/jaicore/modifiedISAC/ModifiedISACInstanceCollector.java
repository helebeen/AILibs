package jaicore.modifiedISAC;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import DataManager.IInstanceCollector;
import jaicore.CustomDataTypes.ProblemInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class ModifiedISACInstanceCollector implements IInstanceCollector<Instance> {

	private ArrayList<ProblemInstance<Instance>> collectedInstances = new ArrayList<ProblemInstance<Instance>>();

	public ModifiedISACInstanceCollector(String filename) throws Exception {
		DataSource customsource = new DataSource(filename);
		Instances customdata = customsource.getDataSet();
		for (Instance i : customdata) {
			collectedInstances.add(new ProblemInstance<Instance>(i));
		}
	}

	public ModifiedISACInstanceCollector() throws Exception {
		InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("metaData_smallDataSets_computed.arff");
		DataSource source = new DataSource(inputStream);
		Instances data = source.getDataSet();
		data.deleteAttributeAt(0);
		for (int j = data.numAttributes()-1; j >= 103; j--) {
			data.deleteAttributeAt(j);
		}
		System.out.println(data);
		for (Instance i : data) {
			collectedInstances.add(new ProblemInstance<Instance>(i));
		}
	}

	@Override
	public List<ProblemInstance<Instance>> getProblemInstances() {
		return this.collectedInstances;
	}

}
