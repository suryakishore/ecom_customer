package com.customer.domain.model.help;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

public class HelpItemData {

  private String desc;

  private ArrayList<HelpSubCatListData> subcat;

  private String link;

  private String name;

  public HelpItemData(String desc,
      ArrayList<HelpSubCatListData> subcat, String link, String name) {
    this.desc = desc;
    this.subcat = subcat;
    this.link = link;
    this.name = name;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  public ArrayList<HelpSubCatListData> getSubcat() {
    return subcat;
  }

  public void setSubcat(ArrayList<HelpSubCatListData> subcat) {
    this.subcat = subcat;
  }

  public String getLink() {
    return link;
  }

  public void setLink(String link) {
    this.link = link;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
