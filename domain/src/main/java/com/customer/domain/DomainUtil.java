package com.customer.domain;

import static com.customer.domain.DomainConstants.LOWER_BOUND;

import java.util.List;
import java.util.Map;

public class DomainUtil {

  public static boolean isEmptyArray(List object) {
    return object == null || object.size() == LOWER_BOUND;
  }

  public static boolean isEmptyMap(Map object) {
    return object == null || object.size() == LOWER_BOUND;
  }
}
