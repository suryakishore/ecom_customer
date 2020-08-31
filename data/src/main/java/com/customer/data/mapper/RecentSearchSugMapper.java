package com.customer.data.mapper;

import com.customer.data.utils.DataUtils;
import com.customer.domain.model.recentsearch.RecentSearchData;
import com.customer.domain.model.recentsearch.RecentSearchSuggestionData;
import com.customer.remote.http.model.response.recentsearch.RecentSearchDetails;
import com.customer.remote.http.model.response.recentsearch.RecentSearchSuggestion;
import java.util.ArrayList;

public class RecentSearchSugMapper {

  public RecentSearchData mapper(RecentSearchDetails details) {
    return new RecentSearchData(details.getMessage(), details.getTotalCount(),
        convertToSuggestionData(details.getData()));
  }

  private ArrayList<RecentSearchSuggestionData> convertToSuggestionData(
      ArrayList<RecentSearchSuggestion> searchSuggestions) {
    ArrayList<RecentSearchSuggestionData> searchSuggestionData = new ArrayList<>();
    if (!DataUtils.isEmptyArray(searchSuggestions)) {
      for (RecentSearchSuggestion suggestion : searchSuggestions) {
        RecentSearchSuggestionData data = new RecentSearchSuggestionData(suggestion.getTimestamp(),
            suggestion.getSearchText(), suggestion.getSearchIn());
        searchSuggestionData.add(data);
      }
    }
    return searchSuggestionData;
  }
}
