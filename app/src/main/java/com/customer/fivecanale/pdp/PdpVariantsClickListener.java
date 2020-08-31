package com.customer.fivecanale.pdp;

import com.customer.domain.model.pdp.VariantsData;
import java.util.ArrayList;

/**
 * interface onclick listener for the pdp variants.
 */
public interface PdpVariantsClickListener {
  void onVariantClick(ArrayList<VariantsData> variantsData,String unitId);
}

