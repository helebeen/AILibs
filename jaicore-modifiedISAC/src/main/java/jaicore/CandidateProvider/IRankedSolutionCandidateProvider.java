package jaicore.CandidateProvider;

import java.util.List;
import jaicore.CustomDataTypes.Instance;
import jaicore.CustomDataTypes.Solution;

public interface IRankedSolutionCandidateProvider<I,S> {
	List<Solution<S>>getCandidate(Instance<I> instance);
}
