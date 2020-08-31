package com.customer.remote.http.model.request;

import android.text.TextUtils;

public class FilterRequest  {
  public static String getCatFilterRequest(String language, String catName, String subCatName,
      String subSubCatName) {
    String body = "{\"from\":0,\"size\":10,\"query\":" +
        "{\"bool\":{\"must\":[{\"match\":{\"status\":1}}," +
        "{\"match_phrase_prefix\":{\"catName.en\":\"" + catName + "\"}},";
    if (!TextUtils.isEmpty(subCatName)) {
      body += "{\"match_phrase_prefix\":{\"subCatName.en\":\"" + subCatName + "\"}},";
    } else {
      body += "}]}}}";
    }
    if (!TextUtils.isEmpty(subSubCatName)) {
      body += "{\"match_phrase_prefix\":{\"subSubCatName.en\":\"" + subSubCatName + "\"}}]}}}";
    } else {
      body += "}]}}}";
    }
    body = body.replace("\\", "");
    return body;
  }

  public static String getSearchRequest(String language, String searchQuery) {

    String body =
        "{\"from\":0,\"size\":10,\"query\":{\"bool\":{\"must\":[{\"match\":{\"status\":1}}," +
            "{\"match_phrase_prefix\":{\"pPName.en\":\"" + searchQuery + "\"}}]}}}";
    body = body.replace("\\", "");
    return body;
  }

}
