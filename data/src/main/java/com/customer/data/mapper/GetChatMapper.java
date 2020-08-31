package com.customer.data.mapper;

import com.customer.data.utils.DataUtils;
import com.customer.domain.model.changelan.ChangeLanData;
import com.customer.domain.model.changelan.ChangeLanDetails;
import com.customer.domain.model.chat.GetChatData;
import com.customer.domain.model.chat.GetChatDetails;
import com.customer.remote.http.model.response.changelanguage.ChangeLanItemDetails;
import com.customer.remote.http.model.response.changelanguage.ChangeLanListDetails;
import com.customer.remote.http.model.response.chat.GetChatItemDetails;
import com.customer.remote.http.model.response.chat.GetChatListDetails;
import java.util.ArrayList;

public class GetChatMapper {
  public GetChatDetails mapper(GetChatListDetails getChatListDetails) {
    return new GetChatDetails(
        convertToLanguageItemData(getChatListDetails.getData()), getChatListDetails.getMessage()
    );
  }

  private ArrayList<GetChatData> convertToLanguageItemData(
      ArrayList<GetChatItemDetails> lanItemDetailsArrayList) {
    ArrayList<GetChatData> lanDetailsArrayList = new ArrayList<>();
    if (!DataUtils.isEmptyArray(lanItemDetailsArrayList)) {
      for (GetChatItemDetails details : lanItemDetailsArrayList) {
        GetChatData data = new GetChatData(details.get_id(),details.getType(),details.getContentType(),details.getContent(),details.getFromID(),
            details.getBid(),details.getTargetId());
        lanDetailsArrayList.add(data);
      }
    }
    return lanDetailsArrayList;
  }
}
