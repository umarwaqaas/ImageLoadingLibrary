package com.task.mindvalley.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.task.mindvalley.Adapter.EmployeesListAdapter;
import com.task.mindvalley.Employee.Employee;
import com.task.mindvalley.R;
import com.task.mindvalley.Utils.Config;
import com.task.mindvalley.Utils.NetworkUtils;
import com.task.mindvalley.Utils.StaticMethods;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import BaseRequest.RequestType;
import Cache.DataCacheManager;
import DataRequest.DataLoader;
import RequestListener.NetworkRequestListener;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by Umer Waqas on 09/08/2017.
 */
public class MainActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {


    private TextView txtTitle;
    private RecyclerView mRecycleView;
    private RequestType<JSONArray> jsonResult;
    private DataCacheManager<JSONArray> cacheManager;
    private RecyclerView.LayoutManager employeeLayoutManager;
    private EmployeesListAdapter employeesAdapter;
    private ProgressBar pgDataLoading;
    private SwipeRefreshLayout refreshLayout;
    private ArrayList<Employee> employeesList;

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews() {
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        mRecycleView = (RecyclerView) findViewById(R.id.rvEmployeeList);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshLayout);

        mRecycleView.setNestedScrollingEnabled(false);
        pgDataLoading = (ProgressBar) findViewById(R.id.pgDataLoading);
        mRecycleView.setHasFixedSize(true);
        employeeLayoutManager = new LinearLayoutManager(MainActivity.this);
        mRecycleView.setLayoutManager(employeeLayoutManager);
        employeesAdapter = new EmployeesListAdapter();
        mRecycleView.setAdapter(employeesAdapter);
        cacheManager = new DataCacheManager<>(30 * 1024 * 1024);
        refreshLayout.setOnRefreshListener(this);


        employeesAdapter.setOnItemClickListener(new EmployeesListAdapter.EachEmployeeClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                OpenProfileActivity(position, v);
            }

        });

    }

    @Override
    protected void setTextLabels() {

    }

    @Override
    protected void loadData() {
        // if (NetworkUtils.isNetworkAvailable(MainActivity.this)) {
        jsonResult = DataLoader
                .with(MainActivity.this)
                .load(DataLoader.Method.GET, Config.Http_Data_Url)
                .asJsonArray()
                .setDataCacheManager(cacheManager)
                .setRequestCallback(new NetworkRequestListener<JSONArray>() {
                    @Override
                    public void onRequestStart() {
                        pgDataLoading.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onDataArrive(JSONArray data) {
                        pgDataLoading.setVisibility(View.GONE);
                        if (data != null) {
                            employeesList = StaticMethods.GetEmployeesList(data);
                            if (employeesList != null && employeesList.size() > 0) {
                                employeesAdapter.setEmployeesList(employeesList);
                            } else {
                                StaticMethods.ShowErrorMessageDialog(MainActivity.this);

                            }

                        } else {
                            StaticMethods.ShowErrorMessageDialog(MainActivity.this);
                        }
                        ResetRefreshListener();

                    }

                    @Override
                    public void onErrorMessage(Exception e) {
                        pgDataLoading.setVisibility(View.GONE);
                        if (e != null) {
                            Log.e("Umer", e.toString());
                        }
                        StaticMethods.ShowErrorMessageDialog(MainActivity.this);
                        ResetRefreshListener();

                    }

                    @Override
                    public void onCancelRequest() {
                        pgDataLoading.setVisibility(View.GONE);
                        ResetRefreshListener();
                    }
                });
//        } else {
//            StaticMethods.ShowNoInternetDialog(MainActivity.this);
//            pgDataLoading.setVisibility(View.GONE);
//        }

    }


    @Override
    public void onRefresh() {
        if (refreshLayout.isRefreshing()) {
            pgDataLoading.setVisibility(View.VISIBLE);
            loadData();
        }

    }

    void ResetRefreshListener(){
        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }
    }
    void OpenProfileActivity(int position, View v) {
        if (employeesList != null && employeesList.size() > 0) {
            Employee employee = employeesList.get(position);
            Config.SelectedEmployee=employee;
            if (employee != null) {
                Intent intent = new Intent(MainActivity.this, UserProfileActivity.class);
                String transitionName = getString(R.string.transition_string);
                View view = v.findViewById(R.id.imageEmployeeAvatar);
                ActivityOptionsCompat options =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this,
                                view,   // Starting view
                                transitionName    // The String
                        );
                ActivityCompat.startActivity(MainActivity.this, intent, options.toBundle());
            }
        } else {
            return;
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        Config.SelectedEmployee=null;
    }
}
