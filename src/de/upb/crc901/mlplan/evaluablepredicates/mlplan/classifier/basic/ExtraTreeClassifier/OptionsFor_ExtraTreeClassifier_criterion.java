package de.upb.crc901.mlplan.evaluablepredicates.mlplan.classifier.basic.ExtraTreeClassifier;

import de.upb.crc901.mlplan.evaluablepredicates.mlplan.OptionsPredicate;

import java.util.Arrays;
import java.util.List;

/*
    criterion : string, optional (default="gini")
    The function to measure the quality of a split. Supported criteria are
    "gini" for the Gini impurity and "entropy" for the information gain.


*/
public class OptionsFor_ExtraTreeClassifier_criterion extends OptionsPredicate {

  private static List<Object> validValues = Arrays.asList(new Object[] { "entropy" });

  @Override
  protected List<? extends Object> getValidValues() {
    return validValues;
  }
}
