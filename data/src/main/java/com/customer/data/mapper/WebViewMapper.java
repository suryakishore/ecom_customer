package com.customer.data.mapper;

import com.customer.domain.model.webview.WebViewListData;
import com.customer.remote.http.model.response.webview.WebPageData;

public class WebViewMapper {
  public WebViewListData mapper(WebPageData webPageDetails) {
    return (convertToCategoryItemData(webPageDetails));
  }

  private WebViewListData convertToCategoryItemData(WebPageData webPageData) {
    WebViewListData webViewListData = null;
    if (webPageData != null) {
      webViewListData = new WebViewListData(webPageData.getPrivacyObj(), webPageData.get_id(),
          webPageData.getTermsObj());
    }
    return webViewListData;
  }
}
