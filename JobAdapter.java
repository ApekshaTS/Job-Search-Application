package com.example.jobsearchapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class JobAdapter extends ArrayAdapter<Job> {
    public JobAdapter(Context context, List<Job> jobs) {
        super(context, 0, jobs);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Job job = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_job, parent, false);
        }
        TextView titleTextView = convertView.findViewById(R.id.titleTextView);
        TextView companyTextView = convertView.findViewById(R.id.companyTextView);

        titleTextView.setText(job.getTitle());
        companyTextView.setText(job.getCompany());

        return convertView;
    }
}
