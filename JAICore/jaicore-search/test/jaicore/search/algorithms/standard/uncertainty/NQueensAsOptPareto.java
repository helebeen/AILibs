package jaicore.search.algorithms.standard.uncertainty;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.junit.Test;

import jaicore.search.algorithms.interfaces.IPathUnification;
import jaicore.search.algorithms.interfaces.ISolutionEvaluator;
import jaicore.search.algorithms.parallel.parallelexploration.distributed.interfaces.SerializableGraphGenerator;
import jaicore.search.algorithms.standard.core.ORGraphSearch;
import jaicore.search.algorithms.standard.uncertainty.paretosearch.ParetoSelection;
import jaicore.search.graphgenerators.nqueens.QueenNode;
import jaicore.search.structure.core.NodeExpansionDescription;
import jaicore.search.structure.core.NodeType;
import jaicore.search.structure.graphgenerator.NodeGoalTester;
import jaicore.search.structure.graphgenerator.SingleRootGenerator;
import jaicore.search.structure.graphgenerator.SuccessorGenerator;

public class NQueensAsOptPareto {

	int dimension = 6;
	int correctSolutions = 4;
	
	@Test
	public void testNQueensProblem () {
		
		ORGraphSearch<QueenNode, String, Double> search = new ORGraphSearch<QueenNode, String, Double>(
				new SerializableGraphGenerator<QueenNode, String>() {
					
					private static final long serialVersionUID = 1L;

					@Override
					public SingleRootGenerator<QueenNode> getRootGenerator() {
						return () -> new QueenNode(dimension);
					}

					@Override
					public SuccessorGenerator<QueenNode, String> getSuccessorGenerator() {
						return n ->{
							List<NodeExpansionDescription<QueenNode,String>> l = new ArrayList<>();
							int currentRow = n.getPositions().size();
							for(int i = 0; i < dimension; i++) {
								l.add(new NodeExpansionDescription<>(n, new QueenNode(n, i), "(" + currentRow + ", " + i + ")", NodeType.OR));
							}
							return l;
						};
					}

					@Override
					public NodeGoalTester<QueenNode> getGoalTester() {
						return n -> {
							if(n.getNumberOfQueens() == dimension)
								return true;
							else
								return false;
							
						};
					}

					@Override
					public boolean isSelfContained() {
						return true;
					}

					@Override
					public void setNodeNumbering(boolean nodenumbering) {
						// TODO Auto-generated method stub
						
					}
				
		
				},
				new UncertaintyRandomCompletionEvaluator<QueenNode, String, Double>(
						new Random(123l),
						3,
						new IPathUnification<QueenNode>() {

							@Override
							public List<QueenNode> getSubsumingKnownPathCompletion(
									Map<List<QueenNode>, List<QueenNode>> knownPathCompletions, List<QueenNode> path)
									throws InterruptedException {
								return null;
							}
						},
						new ISolutionEvaluator<QueenNode, Double>() {
							@Override
							public Double evaluateSolution(List<QueenNode> solutionPath) throws Exception {
								QueenNode leaf = solutionPath.get(solutionPath.size() - 1);
								if (leaf.getNumberOfQueens() == dimension) {
									return scoreSolution(leaf);
								} else {
									return 0.0d;
								}
							}

							@Override
							public boolean doesLastActionAffectScoreOfAnySubsequentSolution(
									List<QueenNode> partialSolutionPath) {
								return true;
							}
						},
						new BasicUncertaintySource<>()
				)
		);
		search.setOpen(new ParetoSelection<>(true));

		int foundCorrectSolutions = 0;
		int solutions = 0;
		List<QueenNode> solution;
		while (foundCorrectSolutions < correctSolutions) {
			solution = search.nextSolution();
			solutions++;
			if (scoreSolution(solution.get(solution.size() - 1)) == 0.0d) {
				foundCorrectSolutions++;
			}
		}

		System.out.println("done with " + foundCorrectSolutions + " correct solutions (" + solutions + " at all)");
		assertEquals(foundCorrectSolutions, correctSolutions);
	}
	
	private double scoreSolution (QueenNode n) {
		double attackedQueens = 0.0d;
		List<Integer> positions = n.getPositions();
		
		for (int i = 0; i < positions.size(); i ++) {
			boolean attacked = false;			
			
			for (int j= 0; j < positions.size(); j ++) {
				if (i != j) {
					if (positions.get(i).equals(positions.get(j))) {
						attacked = true;
						break;
					} else {
						int x = i -j;
						int y = positions.get(i) - positions.get(j);
						if (((x * 1 - 1 * y) == 0) || ((x * (-1) - (-1) * y) == 0) || ((x * 1 - (-1) * y) == 0) || ((x * (-1) - 1 * y) == 0)) {
							attacked = true;
							break;
						}
					}
				}
			}
			
			if (attacked) {
				attackedQueens++;
			} else {
			}
			attacked = false;
		}
		double rating = (attackedQueens / (double) dimension);
		return rating;
	}
	
}
