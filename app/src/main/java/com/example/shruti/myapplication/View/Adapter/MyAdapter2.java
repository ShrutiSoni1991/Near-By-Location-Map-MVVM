package com.example.shruti.myapplication.View.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shruti.myapplication.Model.NearByPlacePOJO;
import com.example.shruti.myapplication.R;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyAdapter2 extends  RecyclerView.Adapter<MyAdapter2.CustomViewHolder> {

private List<NearByPlacePOJO> dataList;
private Context context;

public MyAdapter2(Context context,List<NearByPlacePOJO> dataList){
        this.context = context;
        this.dataList = dataList;
        }

class CustomViewHolder extends RecyclerView.ViewHolder {

    public final View mView;

    TextView txtTitle;
    //ImageView coverImage;

    CustomViewHolder(View itemView) {
        super(itemView);
        mView = itemView;

        txtTitle = mView.findViewById(R.id.title);
      //  coverImage = mView.findViewById(R.id.itemImage);
    }
}

    @Override
    public MyAdapter2.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.horizontal_recyclerview_item, parent, false);
        return new MyAdapter2.CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyAdapter2.CustomViewHolder holder, final int position) {
        holder.txtTitle.setText(dataList.get(position).getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "I am Selected:"+ position, Toast.LENGTH_SHORT).show();
            }
        });
//        Picasso.Builder builder = new Picasso.Builder(context);
//        builder.downloader(new OkHttp3Downloader(context));
//        builder.build().load(dataList.get(position).getThumbnailUrl())
//                .placeholder((R.drawable.ic_launcher_background))
//                .error(R.drawable.ic_launcher_background)
//                .into(holder.coverImage);

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}