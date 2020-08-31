package com.customer.data.mapper;

import android.util.Log;
import com.customer.data.utils.DataUtils;
import com.customer.domain.model.common.CategoryDataModel;
import com.customer.domain.model.productcategory.CategoryData;
import com.customer.domain.model.productcategory.SubCategoryData;
import com.customer.domain.model.productcategory.SubSubCategoryData;
import com.customer.remote.http.model.response.common.CategoryDetailsModel;
import com.customer.remote.http.model.response.productCategory.CategoryDetail;
import com.customer.remote.http.model.response.productCategory.SubCategoryDetails;
import com.customer.remote.http.model.response.productCategory.SubSubCategoryDetails;
import java.util.ArrayList;

public class ProductCategoryMapper {
  public CategoryData mapper(CategoryDetail detailPojo) {
    return new CategoryData(convertToCategoryData(detailPojo.getData()), detailPojo.getMessage());
  }

  private ArrayList<CategoryDataModel> convertToCategoryData(
      ArrayList<CategoryDetailsModel> cateList) {
    ArrayList<CategoryDataModel> categoryData = new ArrayList<>();
    if (!DataUtils.isEmptyArray(cateList)) {
      for (CategoryDetailsModel catDet : cateList) {
        CategoryDataModel data = new CategoryDataModel(catDet.getWebsiteBannerImageUrl(),
            catDet.getImageUrl(), catDet.getCatName(), catDet.getBannerImageUrl(),
            catDet.getWebsiteImageUrl(), catDet.getId(),
            convertToSubCategoryData(catDet.getSubCategory()), catDet.getPenCount());
        categoryData.add(data);
      }
    }
    return categoryData;
  }

  private ArrayList<SubCategoryData> convertToSubCategoryData(
      ArrayList<SubCategoryDetails> subCategoryDetails) {
    ArrayList<SubCategoryData> subCategoryData = new ArrayList<>();
    if (!DataUtils.isEmptyArray(subCategoryDetails)) {
      for (SubCategoryDetails details : subCategoryDetails) {
        Log.d("exe", "details.getSubSubCategory()" + details.getSubSubCategory());
        SubCategoryData data = new SubCategoryData(details.getSubCatCount(),details.getImageUrl(),
            details.getId(), details.getSubCategoryName(), details.getSubSubCategoryName());
        subCategoryData.add(data);
      }
    }
    return subCategoryData;
  }

  private ArrayList<SubSubCategoryData> convertToSubSubCatData(
      ArrayList<SubSubCategoryDetails> detailsList) {
    ArrayList<SubSubCategoryData> categoryData = new ArrayList<>();
    if (!DataUtils.isEmptyArray(detailsList)) {
      for (SubSubCategoryDetails details : detailsList) {
        SubSubCategoryData data = new SubSubCategoryData(details.getSubSubCategoryName(),
            details.getImageUrl(),
            details.getId());
        categoryData.add(data);
      }
    }
    return categoryData;
  }
}
