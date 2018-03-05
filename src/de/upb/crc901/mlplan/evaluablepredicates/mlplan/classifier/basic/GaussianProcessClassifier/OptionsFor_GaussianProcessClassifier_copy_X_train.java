package de.upb.crc901.mlplan.evaluablepredicates.mlplan.classifier.basic.GaussianProcessClassifier;

import de.upb.crc901.mlplan.evaluablepredicates.mlplan.OptionsPredicate;

import java.util.Arrays;
import java.util.List;

/*
    copy_X_train : bool, optional (default: True)
    If True, a persistent copy of the training data is stored in the
    object. Otherwise, just a reference to the training data is stored,
    which might cause predictions to change if the data is modified
    externally.


*/
public class OptionsFor_GaussianProcessClassifier_copy_X_train extends OptionsPredicate {

  private static List<Object> validValues = Arrays.asList(new Object[] { "false" });

  @Override
  protected List<? extends Object> getValidValues() {
    return validValues;
  }
}
