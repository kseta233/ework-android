package com.ework.eduplex.adapters;

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
import com.ework.eduplex.R;
import com.ework.eduplex.activities.katalog.KatalogItemDetailActivity;
import com.ework.eduplex.service.model.Product;
import com.ework.eduplex.utils.Constant;
import com.ework.eduplex.utils.Utils;

import java.util.List;

/**
 * Created by eWork on 3/23/2016.
 */
public class BarangAdapter extends RecyclerView.Adapter<BarangAdapter.MyHolder> {

    private List<Product> itemList;
    private Context context;

    public BarangAdapter(Context context, List<Product> itemList) {
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
    public void onBindViewHolder(MyHolder holder, final int position) {
        holder.name.setText(itemList.get(position).getProduct_name());
        holder.harga.setText(Utils.getShownNominal(itemList.get(position).getProduct_price()));
        try {
            String prefix = Constant.API_URL;
            String xUrl = prefix + itemList.get(position).getProduct_images().get(0);

            Log.i("PHOTOURL",xUrl);

            Glide.with(context).load(xUrl).into(holder.imageBarang);
//            Glide.with(context).load(itemList.get(position).getProduct_images().get(0)).into(holder.imageBarang);
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, KatalogItemDetailActivity.class);
                intent.putExtra(Constant.IntentExtraKeys.KEY_PRODUCT_ID, itemList.get(position).getProduct_id());
                intent.putExtra(Constant.IntentExtraKeys.KEY_PRODUCT_OBJECT, itemList.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }


    public class MyHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public TextView harga;
        public ImageView imageBarang;
        public CardView cardView;

        public MyHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.tvItemName);
            harga = (TextView) itemView.findViewById(R.id.tvItemHarga);
            imageBarang = (ImageView) itemView.findViewById(R.id.ivItem);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
        }
    }
}
