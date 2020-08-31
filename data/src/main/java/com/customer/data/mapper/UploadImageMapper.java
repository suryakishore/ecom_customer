package com.customer.data.mapper;

import com.customer.domain.model.uploadimage.UploadImageItemData;
import com.customer.remote.http.model.response.uploadImage.UploadImageItemDetails;

public class UploadImageMapper {
  public UploadImageItemData mapper(UploadImageItemDetails details) {
    UploadImageItemData uploadImageItemData = null;
    if (details != null) {
      uploadImageItemData = new UploadImageItemData(details.getImageUrl());
    }
    return uploadImageItemData;
  }

  private UploadImageItemData convertToUploadItemData(UploadImageItemDetails data) {
    UploadImageItemData uploadImageItemData = null;
    if (data != null) {
      uploadImageItemData = new UploadImageItemData(data.getImageUrl());
    }
    return uploadImageItemData;
  }
}
