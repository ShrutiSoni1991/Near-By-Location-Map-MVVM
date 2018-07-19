package com.example.shruti.myapplication.View.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shruti.myapplication.Model.NearByApiResponse;
import com.example.shruti.myapplication.R;
import com.example.shruti.myapplication.Model.NearByPlacePOJO;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Sample_Adapter extends RecyclerView.Adapter<Sample_Adapter.CustomViewHolder> implements View.OnClickListener{

    private NearByApiResponse dataList;
    private Context context;

    public Sample_Adapter(Context context,NearByApiResponse dataList){
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public void onClick(View view) {

    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        public final View mView;

        TextView placeName, vicinity, lng, lat ;
        //ImageView coverImage;

        CustomViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            placeName = mView.findViewById(R.id.textView1);
            vicinity = mView.findViewById(R.id.textView2);
//            lng = mView.findViewById(R.id.textView3);
//            lat = mView.findViewById(R.id.textView4);//coverImage = mView.findViewById(R.id.imageView1);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, "This is Click ",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.samle_data, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, final int position) {
        holder.placeName.setText(dataList.getResults().get(position).getName());
        holder.vicinity.setText(dataList.getResults().get(position).getVicinity());
      //  holder.lat.setText(dataList.getResults().get(position).getGeometry().getLocation().getLat().toString());
      //  holder.lng.setText(dataList.getResults().get(position).getGeometry().getLocation().getLng().toString());
       // holder.coverImage.setImageResource(R.mipmap.icon);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "I am Selected:"+ position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.getResults().size();
    }
}