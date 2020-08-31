package com.customer.fivecanale.landing.cartscreen;

import androidx.appcompat.widget.AppCompatTextView;
import com.customer.domain.model.getcart.CartAttributesData;
import java.util.ArrayList;

public interface QuantityClick {
  void onItemClick(AppCompatTextView appCompatTextView,String unitName,int pos,int action);
}
