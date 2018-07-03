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

	private ArrayList<ProblemInstance<Instance>> collectedInstances;

	public ModifiedISACInstanceCollector(String filename) throws Exception {
		DataSource customsource = new DataSource(filename);
		Instances customdata = customsource.getDataSet();
		for (Instance i : customdata) {
			collectedInstances.add(new ProblemInstance<Instance>(i));
		}
	}

	public ModifiedISACInstanceCollector() throws Exception {
		InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("metaData_smallDataSets_computed");
		DataSource source = new DataSource(inputStream);
		Instances data = source.getDataSet();
		for (Instance i : data) {
			i.deleteAttributeAt(0);
			for (int j = i.numAttributes(); j >= 104 ; j--) {
				i.deleteAttributeAt(j);
			}
			collectedInstances.add(new ProblemInstance<Instance>(i));
		}
	}

	@Override
	public List<ProblemInstance<Instance>> getProblemInstances() {
		return this.collectedInstances;
	}

}
