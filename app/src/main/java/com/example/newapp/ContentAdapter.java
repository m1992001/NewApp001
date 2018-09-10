package com.example.newapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ViewHolder>{
    private List<Content> mContent;
    private Context mContext;

    static  class ViewHolder extends RecyclerView.ViewHolder{
        ImageView contentImage;
        TextView contentName;
        CardView cardView;

        public ViewHolder(View view){
            super(view);
            cardView = (CardView) view;
            contentImage = view.findViewById(R.id.content_image);
            contentName = view.findViewById(R.id.content_name);
        }
    }

    public ContentAdapter(List<Content> contentList){
        mContent = contentList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mContext == null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.content_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        //首页新闻点击事件
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                final Content content = mContent.get(position);
//                final CountDownLatch latch = new CountDownLatch(1);
//                Log.d("ContentAdapter_con_id", content.getContentId());
                if("".equals(content.getContentId())) {
                    HttpUtil.getHttpRequest(HttpUtil.IP + "/app/news/" + content.getName(), new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Log.d("Fragment1", "服务器访问失败");
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String resp = response.body().string();
                            try {
                                JSONObject respJson = new JSONObject(resp);
                                final String con = (String) respJson.get("con");
//                                Log.d("ContentAdapter_con", con);
                                content.setContentId(con);
                                Intent intent = new Intent(mContext, ContentActivity.class);
                                intent.putExtra(ContentActivity.CONTENT_NAME, content.getName());
                                intent.putExtra(ContentActivity.CONTENT_IMAGE_ID, content.getImageId());
                                intent.putExtra(ContentActivity.CONTENT_TEXT, content.getContentId());
                                mContext.startActivity(intent);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }else{
                    Intent intent = new Intent(mContext, ContentActivity.class);
                    intent.putExtra(ContentActivity.CONTENT_NAME, content.getName());
                    intent.putExtra(ContentActivity.CONTENT_IMAGE_ID, content.getImageId());
                    intent.putExtra(ContentActivity.CONTENT_TEXT, content.getContentId());
                    mContext.startActivity(intent);
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Content content = mContent.get(position);
        Log.d("Fragment1",HttpUtil.IP + content.getImageId());
        Glide.with(mContext).load(HttpUtil.IP + content.getImageId()).into(holder.contentImage);
        holder.contentName.setText(content.getName());
    }

    @Override
    public int getItemCount() {
        return mContent.size();
    }
}
