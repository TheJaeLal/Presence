package com.mjjam.attendanceapp.faculty;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mjjam.attendanceapp.R;
import com.mjjam.attendanceapp.widgets.BaseTextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * Created by Archish on 12/19/2016.
 */

public class AttendanceOverviewAdapter extends RecyclerView.Adapter<AttendanceOverviewAdapter.StudentViewHolder> {

    ArrayList<String> data;
    private LikeItemUpdateListener commander;

    public AttendanceOverviewAdapter(ArrayList<String> data, LikeItemUpdateListener commander) {
        this.data = data;
        this.commander = commander;
    }

    public interface LikeItemUpdateListener {


        void onItemCardClicked(String home);
    }

    @Override
    public AttendanceOverviewAdapter.StudentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_students, parent, false);
        StudentViewHolder holder = new StudentViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final AttendanceOverviewAdapter.StudentViewHolder holder, final int position) {
        holder.tvTitle.setText(data.get(position));

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class StudentViewHolder extends RecyclerView.ViewHolder {

        BaseTextView tvTitle;
        public StudentViewHolder(final View itemView) {
            super(itemView);
            tvTitle = (BaseTextView) itemView.findViewById(R.id.tvStudent);

        }

    }


}