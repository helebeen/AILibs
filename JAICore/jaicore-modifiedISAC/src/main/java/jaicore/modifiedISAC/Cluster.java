package jaicore.modifiedISAC;

import java.util.List;

import jaicore.CustomDataTypes.Group;
import jaicore.CustomDataTypes.GroupIdentifier;
import jaicore.CustomDataTypes.ProblemInstance;
import weka.core.Instance;

public class Cluster extends Group<double[], Instance> {

	Cluster(List<ProblemInstance<Instance>> instanlist, GroupIdentifier<double[]> id) {
		super(instanlist, id);
		// TODO Auto-generated constructor stub
	}

}
