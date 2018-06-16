package jaicore.BuildRanker;

import java.util.List;

import jaicore.CustomDataTypes.Group;
import jaicore.CustomDataTypes.Instance;

public interface IGroupBuilder<C,I>{
	List<Group<C,I>> buildGroup(List<Instance<I>> allInstances);
}
