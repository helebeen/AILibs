package DataManager;

import java.util.List;

import jaicore.CustomDataTypes.ProblemInstance;

public interface IInstanceCollector <I>{
	void collectInstnaces();
	List<ProblemInstance<I>> getProblemInstances();
}
