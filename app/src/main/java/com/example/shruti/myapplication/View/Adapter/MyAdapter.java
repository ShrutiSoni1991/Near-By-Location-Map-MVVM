package com.example.shruti.myapplication.View.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shruti.myapplication.R;
import com.example.shruti.myapplication.Model.NearByPlacePOJO;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.CustomViewHolder> {

    private List<NearByPlacePOJO> dataList;
    private Context context;

    public MyAdapter(Context context,List<NearByPlacePOJO> dataList){
        this.context = context;
        this.dataList = dataList;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        public final View mView;

        TextView txtTitle;
        ImageView coverImage;

        CustomViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            txtTitle = mView.findViewById(R.id.textView);
            coverImage = mView.findViewById(R.id.imageView1);
        }
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, final int position) {
        holder.txtTitle.setText(dataList.get(position).getTitle());

        Picasso.Builder builder = new Picasso.Builder(context);
        builder.downloader(new OkHttp3Downloader(context));
        builder.build().load(dataList.get(position).getThumbnailUrl())
                .placeholder((R.drawable.ic_launcher_background))
                .error(R.drawable.ic_launcher_background)
                .into(holder.coverImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "I am Selected:"+position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
//
//import android.content.Context;
//import android.support.annotation.NonNull;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//import com.example.shruti.myapplication.R;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//
//public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MainViewHolder> {
//
//    List<String> testArray = new ArrayList<>();
//    RecyclerView recyclerView;
//    static int change;
//    Random random = new Random();
//    Context context;
//
//
//    @NonNull
//    @Override
//    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
//        context = parent.getContext();
//        return new MainViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
//
//    }
//
//    @Override
//    public int getItemCount() {
//        //return testArray.size();
//        return 100;
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//
//        return super.getItemViewType(position);
//    }
//
//class MainViewHolder extends RecyclerView.ViewHolder{
//
//    TextView textView;
//    ImageView imageView1;
//    public MainViewHolder(View itemView) {
//        super(itemView);
//        textView = (TextView)itemView.findViewById(R.id.textView);
//        imageView1 = (ImageView)itemView.findViewById(R.id.imageView1);
//
//        itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(view.getContext(),testArray.get(getAdapterPosition()),Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//}
//}