package DataManager;

import java.util.List;

import jaicore.CustomDataTypes.ProblemInstance;

public interface IInstanceCollector <I>{
	void collectInstnaces()throws Exception;
	List<ProblemInstance<I>> getProblemInstances();
}
