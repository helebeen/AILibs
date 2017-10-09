package jaicore.planning.model.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import jaicore.basic.SetUtil;
import jaicore.logic.fol.structure.ConstantParam;
import jaicore.logic.fol.structure.Literal;
import jaicore.logic.fol.structure.Monom;
import jaicore.logic.fol.structure.VariableParam;
import jaicore.planning.model.strips.StripsAction;
import jaicore.planning.model.strips.StripsOperation;
import jaicore.planning.model.strips.StripsPlanningDomain;

public class PlannerUtil {
	
	public static Collection<StripsAction> getApplicableActionsInState(Monom state, StripsPlanningDomain domain) {
		Collection<StripsAction> applicableDerivedActions = new ArrayList<>();
		for (Operation op : domain.getOperations()) {
			applicableDerivedActions.addAll(getPossibleOperationGroundingsForState(state, (StripsOperation)op));
		}
		return applicableDerivedActions;
	}
	
	public static Collection<StripsAction> getPossibleOperationGroundingsForState(Monom state, StripsOperation operation) {
		Collection<StripsAction> applicableDerivedActions = new ArrayList<>();
		
		/* implement groundings here */
		try {
			for (Map<VariableParam,ConstantParam> grounding: SetUtil.allTotalMappings(operation.getParams(), state.getConstantParams())) {
				Monom precondition = new Monom(operation.getPrecondition(), grounding);
				List<Literal> positiveLiterals = precondition.stream().filter(l -> l.isPositive()).collect(Collectors.toList());
				List<Literal> negativeLiterals = precondition.stream().filter(l -> l.isNegated()).map(l -> l.clone().toggleNegation()).collect(Collectors.toList());
				if (state.containsAll(positiveLiterals) && SetUtil.intersection(state, negativeLiterals).isEmpty())
					applicableDerivedActions.add(new StripsAction(operation, grounding));
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return applicableDerivedActions;
	}

	public static void main(String[] args) {
		System.out.println("NON-PRIMITIVE!");
	}
}