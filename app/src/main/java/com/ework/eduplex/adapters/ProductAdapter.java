package com.ework.eduplex.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ework.eduplex.R;
import com.ework.eduplex.service.model.Product;
import com.ework.eduplex.utils.Constant;
import com.ework.eduplex.utils.Utils;

import java.util.List;

import javax.microedition.khronos.opengles.GL;

/**
 * Created by eWork on 3/23/2016.
 */
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyHolder> {

    private List<Product> itemList;
    private Context context;

    public ProductAdapter(Context context, List<Product> itemList) {
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_grid, null);
        MyHolder rcv = new MyHolder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        holder.name.setText(itemList.get(position).getProduct_name());
        holder.harga.setText(Utils.getShownNominal(itemList.get(position).getProduct_price()));
        String prefix = Constant.API_URL;
        if (itemList.get(position).getProduct_images().size() != 0){
            String xUrl = prefix + itemList.get(position).getProduct_images().get(0);
            Log.i("PHOTOURL",xUrl);
            Glide.with(context).load(xUrl).into(holder.imageProduct);
        }

    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }



    public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView name;
        public TextView harga;
        public ImageView imageProduct;

        public MyHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            name = (TextView)itemView.findViewById(R.id.tvItemName);
            harga = (TextView)itemView.findViewById(R.id.tvItemHarga);
            imageProduct = (ImageView)itemView.findViewById(R.id.ivItem);
        }

        @Override
        public void onClick(View view) {
//            int itemPosition = rv.getChildPosition(view);
//            Product item = itemList.get(itemPosition);
        }
    }

    public void refreshDataSet(List<Product> listProduct) {
        this.itemList = listProduct;
        notifyDataSetChanged();
    }
}
