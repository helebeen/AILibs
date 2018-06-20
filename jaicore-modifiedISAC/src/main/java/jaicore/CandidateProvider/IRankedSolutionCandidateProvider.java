package jaicore.CandidateProvider;

import java.util.List;

import jaicore.CustomDataTypes.ProblemInstance;
import jaicore.CustomDataTypes.Ranking;

public interface IRankedSolutionCandidateProvider<I,S> {
	Ranking<S> getCandidate(ProblemInstance<I> instance);
}
