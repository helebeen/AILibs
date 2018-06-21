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
	private static final String defaultfilename = "metaData_smallDataSets_computed";
	String customfilename;

	public ModifiedISACInstanceCollector(String filename) {
		this.customfilename = filename;
	}

	public ModifiedISACInstanceCollector() {

	}

	@Override
	public void collectInstnaces() throws Exception {
		if (customfilename != null) {
			DataSource customsource = new DataSource(customfilename);
			Instances customdata = customsource.getDataSet();
			for (Instance i : customdata) {
				collectedInstances.add(new ProblemInstance<Instance>(i));
			}
		} else {
			InputStream inputStream = Thread.currentThread().getContextClassLoader()
					.getResourceAsStream(defaultfilename);
			DataSource source = new DataSource(inputStream);
			Instances data = source.getDataSet();
			for (Instance i : data) {
				collectedInstances.add(new ProblemInstance<Instance>(i));
			}
		}
	}

	@Override
	public List<ProblemInstance<Instance>> getProblemInstances() {
		return this.collectedInstances;
	}

}
