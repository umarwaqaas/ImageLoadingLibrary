package com.task.mindvalley.Adapter;


import com.task.mindvalley.Employee.Employee;
import com.task.mindvalley.R;

import java.util.ArrayList;

import Cache.DataCacheManager;
import DataRequest.DataLoader;
import RequestListener.NetworkRequestListener;
import de.hdodenhof.circleimageview.CircleImageView;

import android.content.Context;
import android.graphics.Bitmap;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;


/**
 * Created by megha on 15-03-06.
 */
public class EmployeesListAdapter extends RecyclerView.Adapter<EmployeesListAdapter.ViewHolder> {

    private ArrayList<Employee> mEmployeesList;
    private DataCacheManager<Bitmap> imagesCacheManager;
    private Context mContext = null;
    private static EachEmployeeClickListener employeeClickListener;


    public EmployeesListAdapter() {
        mEmployeesList = new ArrayList<>();
        this.imagesCacheManager = new DataCacheManager<>(50 * 1024 * 1024);

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.each_employee_row_layout, parent, false);
        mContext = view.getContext();
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Employee employee = mEmployeesList.get(position);
        if (employee == null) {
            return;
        } else {
            holder.txtEmployeeName.setText(employee.getEmployeeName());
            holder.txtEmployeeDescription.setText(employee.getEmployeeName());
            LoadEachEmployeeAvatar(employee.getEmployeeAvatarUrl(), holder.pgImageLoading, holder.imageEmployeeAvatar);
        }


    }


    @Override
    public int getItemCount() {
        return mEmployeesList.size();
    }


    public void setOnItemClickListener(EachEmployeeClickListener myClickListener) {
        this.employeeClickListener = myClickListener;
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public CircleImageView imageEmployeeAvatar;
        public TextView txtEmployeeName;
        public TextView txtEmployeeDescription;
        public ProgressBar pgImageLoading;

        public ViewHolder(View itemView) {
            super(itemView);
            imageEmployeeAvatar = (CircleImageView) itemView.findViewById(R.id.imageEmployeeAvatar);
            txtEmployeeName = (TextView) itemView.findViewById(R.id.txtEmployeeName);
            txtEmployeeDescription = (TextView) itemView.findViewById(R.id.txtEmployeeDescription);
            pgImageLoading = (ProgressBar) itemView.findViewById(R.id.pgImageLoading);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            employeeClickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    public void setEmployeesList(ArrayList<Employee> employees) {
        if (mEmployeesList == null) {
            mEmployeesList = new ArrayList<>();
        }
        this.mEmployeesList = employees;
        notifyDataSetChanged();
    }


    void LoadEachEmployeeAvatar(String imageUrl, final ProgressBar progressBar, final CircleImageView avatar) {
        DataLoader
                .with(mContext)
                .load(DataLoader.Method.GET, imageUrl)
                .asImage()
                .setDataCacheManager(imagesCacheManager)
                .setRequestCallback(new NetworkRequestListener<Bitmap>() {
                    @Override
                    public void onRequestStart() {
                        progressBar.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onDataArrive(Bitmap data) {
                        progressBar.setVisibility(View.GONE);
                        if (data != null) {
                            avatar.setImageBitmap(data);
                        }

                    }

                    @Override
                    public void onErrorMessage(Exception e) {
                        progressBar.setVisibility(View.GONE);
                        if (e != null) {
                            Log.e("Umer", e.toString());
                        }

                    }

                    @Override
                    public void onCancelRequest() {
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }

    public interface EachEmployeeClickListener {
        public void onItemClick(int position, View v);
    }

}
