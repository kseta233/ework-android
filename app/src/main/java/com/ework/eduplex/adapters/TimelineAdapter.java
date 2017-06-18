package com.ework.eduplex.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ework.eduplex.R;
import com.ework.eduplex.service.model.Timeline;
import com.ework.eduplex.utils.Constant;

import java.util.List;

/**
 * Created by eWork on 3/16/2016.
 */
public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.MyViewHolder> {

    private List<Timeline> timelinesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView dayMonth, year, title, content;
        public ImageView iconTimeline;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.tvItemTimelineTitle);
            content = (TextView) view.findViewById(R.id.tvItemTimelineContent);
            year = (TextView) view.findViewById(R.id.tvItemTimelineYear);
            dayMonth = (TextView) view.findViewById(R.id.tvItemTimelineDayMonth);
            iconTimeline = (ImageView) view.findViewById(R.id.ivItemTimeline);
        }
    }

    public TimelineAdapter(List<Timeline> timelinesList) {
        this.timelinesList = timelinesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_timeline, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Timeline timeline = timelinesList.get(position);
        holder.title.setText(timeline.getAction());
        holder.content.setText(timeline.getNews());

        try {
            holder.dayMonth.setText(timeline.getDate());
            holder.year.setText("");
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("errorsettimeline", "error split date");
        }

        //SET TIMELINE ICON BASED ON ITS TYPE
        try {
            if (timeline.getIcon().equals(Constant.TimelinesFlag.SUBMISSION_FLAG))
                holder.iconTimeline.setImageResource(R.mipmap.pengajuan_kredit);
            else if (timeline.getIcon().equals(Constant.TimelinesFlag.SUBMISSION_FAILED_FLAG))
                holder.iconTimeline.setImageResource(R.mipmap.pengajuan_gagal);
            else if (timeline.getIcon().equals(Constant.TimelinesFlag.SUBMISSION_SUCCESS_FLAG))
                holder.iconTimeline.setImageResource(R.mipmap.pengajuan_berhasil);
            else if (timeline.getIcon().equals(Constant.TimelinesFlag.KTUNAI_IN_FLAG))
                holder.iconTimeline.setImageResource(R.mipmap.ktunai_in);
            else if (timeline.getIcon().equals(Constant.TimelinesFlag.KTUNAI_OUT_FLAG))
                holder.iconTimeline.setImageResource(R.mipmap.ktunai_out);
            else if (timeline.getIcon().equals(Constant.TimelinesFlag.DUE_DATE_FLAG))
                holder.iconTimeline.setImageResource(R.mipmap.jatuh_tempo);
            else
                holder.iconTimeline.setImageResource(R.mipmap.ic_adjust_black_18dp);
        } catch (Exception e) {
//            e.printStackTrace();
            holder.iconTimeline.setImageResource(R.mipmap.ic_adjust_black_18dp);
        }
    }

    @Override
    public int getItemCount() {
        return timelinesList.size();
    }

    public void refreshDataSet(List<Timeline> listTimeline) {
        this.timelinesList = listTimeline;
        notifyDataSetChanged();
    }
}
