package com.ework.eduplex.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.ework.eduplex.R;
import com.ework.eduplex.activities.katalog.KatalogItemDetailActivity;
import com.ework.eduplex.service.model.Promo;
import com.ework.eduplex.utils.Constant;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by eWork on 8/16/2016.
 */
public class BannerPagerAdapter extends PagerAdapter {
    // Declare Variables
    Context context;
    List<Promo> listPromo;
    LayoutInflater inflater;

    public BannerPagerAdapter(Context context, List<Promo> listPromo) {
        this.context = context;
        this.listPromo = listPromo;
    }

    @Override
    public int getCount() {
        return listPromo.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        // Declare Variables
        ImageView imgBanner;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.item_banner, container,
                false);
        imgBanner = (ImageView) itemView.findViewById(R.id.ivItemBanner);

        //Recycle the bitmap
        try {
            ((BitmapDrawable) imgBanner.getDrawable()).getBitmap().recycle();
        } catch (Exception e) {
        }

        //Set the image.
        Picasso.with(context).load(listPromo.get(position).getImage_url()).into(imgBanner);

        //Set the promo link.
        imgBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //GO TO THE LINK OR DIRECT TO THE SPECIFIC CATALOG
                try {
                    Promo p = listPromo.get(position);
                    if (p.getPromo_product_internal_link().equals("")) {
                        String url = p.getPromo_url();
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        context.startActivity(i);
                    } else {
                        Intent intent = new Intent(context, KatalogItemDetailActivity.class);
                        intent.putExtra(Constant.IntentExtraKeys.KEY_PRODUCT_ID, p.getPromo_product_internal_link());
                        context.startActivity(intent);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    //NO LINK NOR PRODUCT INTERNAL LINK
                }
            }
        });

        // Add viewpager_item.xml to ViewPager
        ((ViewPager) container).addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // Remove viewpager_item.xml from ViewPager
        ((ViewPager) container).removeView((RelativeLayout) object);

    }
}
