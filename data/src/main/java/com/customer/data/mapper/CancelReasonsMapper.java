package com.customer.data.mapper;

import com.customer.data.utils.DataUtils;
import com.customer.domain.model.cancelreasons.CancelReasonsData;
import com.customer.domain.model.cancelreasons.CancelReasonsItemData;
import com.customer.remote.http.model.response.getReasons.GetReasonsItemDetails;
import com.customer.remote.http.model.response.getReasons.ReasonData;
import java.util.ArrayList;

public class CancelReasonsMapper {
  public CancelReasonsData mapper(GetReasonsItemDetails getReasonsDetails) {
    return new CancelReasonsData(convertToReasonItemData(getReasonsDetails.getReasonData()),
        getReasonsDetails.getTotalCount());
  }

  private ArrayList<CancelReasonsItemData> convertToReasonItemData(
      ArrayList<ReasonData> reasonData) {
    ArrayList<CancelReasonsItemData> cancelReasonsItemData = new ArrayList<>();
    if (!DataUtils.isEmptyArray(reasonData)) {
      for (ReasonData itemDetails : reasonData) {
        CancelReasonsItemData itemData = new CancelReasonsItemData(itemDetails.getReason(),
            itemDetails.getReasonMsg(), itemDetails.getCustomerUnavailable(),
            itemDetails.getReasonId(), itemDetails.get_id(), itemDetails.getReAttemptAllowed(),
            itemDetails.getReturnToStore());
        cancelReasonsItemData.add(itemData);
      }
    }
    return cancelReasonsItemData;
  }
}
