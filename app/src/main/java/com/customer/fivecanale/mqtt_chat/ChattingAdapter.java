package com.customer.fivecanale.mqtt_chat;


import static com.customer.fivecanale.util.EcomConstants.ZERO;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.customer.domain.model.chat.GetChatData;
import com.customer.fivecanale.util.Scaler;
import java.util.ArrayList;

/**
 * <h>ChattingAdapter</h>
 * Created by Ali on 12/22/2017.
 */
public class ChattingAdapter extends RecyclerView.Adapter {
  private Context mContext;
  private ArrayList<GetChatData> chatData;
  private RequestOptions options;

  public ChattingAdapter(ArrayList<GetChatData> chatData) {
    this.chatData = chatData;
    options = new RequestOptions()
        .fitCenter()
        .placeholder(R.drawable.ic_place_holder);
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    mContext = parent.getContext();
    View view = LayoutInflater.from(mContext).inflate(R.layout.item_chatting, parent, false);
    return new ViewHolders(view);
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    ViewHolders hldr = (ViewHolders) holder;
    if (chatData.get(position).getCustProType() == 1) {
      hldr.rlCustMsg.setVisibility(View.VISIBLE);
      hldr.rlProMsg.setVisibility(View.GONE);
      hldr.ivCustSendPic.setVisibility(chatData.get(position).getContentType() == 1 ? View.GONE
          : View.VISIBLE);
      if (chatData.get(position).getContentType() == 1) {
        hldr.tvCustMsg.setText(chatData.get(position).getContent());
        hldr.tvCustMsg.setVisibility(View.VISIBLE);
      } else {
        hldr.tvCustMsg.setVisibility(View.GONE);
        String url = chatData.get(position).getContent();
        if (!url.equals("")) {
          Glide.with(mContext)
              .load(url)
              .apply(options)
              .into(hldr.ivCustSendPic);
        }
      }
    } else {
      hldr.rlCustMsg.setVisibility(View.GONE);
      hldr.rlProMsg.setVisibility(View.VISIBLE);
      hldr.ivProReceivedPic.setVisibility(chatData.get(position).getContentType() == 1 ? View.GONE
          : View.VISIBLE);
      if (chatData.get(position).getContentType() == 1) {
        hldr.tvProMsg.setText(chatData.get(position).getContent());
        hldr.tvProMsg.setVisibility(View.VISIBLE);
      } else {
        hldr.tvProMsg.setVisibility(View.GONE);
        String url = chatData.get(position).getContent();
        if (!url.equals("")) {
          Glide.with(mContext)
              .load(url)
              .apply(options)
              .into(hldr.ivProReceivedPic);
        }
      }
    }
  }

  @Override
  public int getItemCount() {
    return chatData != null ? chatData.size() : ZERO;
  }

  class ViewHolders extends RecyclerView.ViewHolder {
    private RelativeLayout rlCustMsg, rlProMsg;
    private ImageView ivChatSendStatus, ivCustSendPic, ivProReceivedPic;
    private TextView tvCustMsg, tvProMsg;
    //        private AppTypeface appTypeface;
    private double width, height;

    public ViewHolders(View itemView) {
      super(itemView);
//            appTypeface = AppTypeface.getCountEmitterInstance(mContext);
      double size[];
      size = Scaler.getScalingFactor(mContext);
      ivChatSendStatus = (ImageView) itemView.findViewById(R.id.ivChatSendStatus);
      ivCustSendPic = (ImageView) itemView.findViewById(R.id.ivCustSendPic);
      ivProReceivedPic = (ImageView) itemView.findViewById(R.id.ivProReceivedPic);
      tvCustMsg = (TextView) itemView.findViewById(R.id.tvCustMsg);
      tvProMsg = (TextView) itemView.findViewById(R.id.tvProMsg);
      rlCustMsg = (RelativeLayout) itemView.findViewById(R.id.rlCustMsg);
      rlProMsg = (RelativeLayout) itemView.findViewById(R.id.rlProMsg);
           /* tvCustMsg.setTypeface(appTypeface.getHind_regular());
            tvProMsg.setTypeface(appTypeface.getHind_regular());*/
      width = 200 * size[0];
      height = 200 * size[1];
    }
  }
}
