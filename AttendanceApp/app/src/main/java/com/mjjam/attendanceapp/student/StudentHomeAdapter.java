package com.mjjam.attendanceapp.student;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mjjam.attendanceapp.R;
import com.mjjam.attendanceapp.data.models.Lecture;
import com.mjjam.attendanceapp.widgets.BaseTextView;

import java.util.ArrayList;


/**
 * Created by Archish on 12/19/2016.
 */

public class StudentHomeAdapter extends RecyclerView.Adapter<StudentHomeAdapter.StudentViewHolder> {

    ArrayList<Lecture> data;
    private LikeItemUpdateListener commander;

    public StudentHomeAdapter(ArrayList<Lecture> data, LikeItemUpdateListener commander) {
        this.data = data;
        this.commander = commander;
    }

    public interface LikeItemUpdateListener {


        void onItemCardClicked(String home);
    }

    @Override
    public StudentHomeAdapter.StudentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_student_home, parent, false);
        StudentViewHolder holder = new StudentViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final StudentHomeAdapter.StudentViewHolder holder, final int position) {
        holder.tvUSName.setText(data.get(position).getLectureName());
        holder.tvUSTime.setText(data.get(position).getLectureTime());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class StudentViewHolder extends RecyclerView.ViewHolder {

        BaseTextView tvUSName,tvUSTime;
        public StudentViewHolder(final View itemView) {
            super(itemView);
            tvUSName = (BaseTextView) itemView.findViewById(R.id.tvUSName);
            tvUSTime = (BaseTextView) itemView.findViewById(R.id.tvUSTime);

        }

    }


}