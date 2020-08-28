package com.task.mindvalley.Activity;

import android.graphics.Bitmap;

import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.task.mindvalley.R;
import com.task.mindvalley.Utils.Config;

import Cache.DataCacheManager;
import DataRequest.DataLoader;
import RequestListener.NetworkRequestListener;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by Umer Waqas on 09/08/2017.
 */
public class UserProfileActivity extends BaseActivity {
    private TextView txtUserName;
    private TextView txtName;
    private TextView txtID;
    private TextView txtTitle;
private CircleImageView employeeAvatar;
    private DataCacheManager<Bitmap> imagesCacheManager;

    @Override
    protected int getContentView() {
        return R.layout.activity_profile;
    }

    @Override
    protected void initViews() {
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        employeeAvatar = (CircleImageView)findViewById(R.id.employeeAvatar);
        txtID = (TextView) findViewById(R.id.txtID);
        txtName = (TextView) findViewById(R.id.txtName);
        txtUserName = (TextView) findViewById(R.id.txtUserName);
        imagesCacheManager = new DataCacheManager<>(50 * 1024 * 1024);
    }

    @Override
    protected void setTextLabels() {
        txtTitle.setText("User Profile");
    }

    @Override
    protected void loadData() {
        if (Config.SelectedEmployee != null) {
            txtID.setText(Config.SelectedEmployee.getEmployeeID().toString());
            txtName.setText(Config.SelectedEmployee.getEmployeeName().toString());
            txtUserName.setText(Config.SelectedEmployee.getEmployeeUsername().toString());
            LoadEachEmployeeAvatar(Config.SelectedEmployee.getEmployeeAvatarUrl()  , employeeAvatar);
        }
    }



    void LoadEachEmployeeAvatar(String imageUrl, final CircleImageView avatar) {
        DataLoader
                .with(UserProfileActivity.this)
                .load(DataLoader.Method.GET, imageUrl)
                .asImage()
                .setDataCacheManager(imagesCacheManager)
                .setRequestCallback(new NetworkRequestListener<Bitmap>() {
                    @Override
                    public void onRequestStart() {
                       // progressBar.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onDataArrive(Bitmap data) {
                       // progressBar.setVisibility(View.GONE);
                        if (data != null) {
                            avatar.setImageBitmap(data);
                        }

                    }

                    @Override
                    public void onErrorMessage(Exception e) {
                       // progressBar.setVisibility(View.GONE);
                        if (e != null) {
                            Log.e("Umer", e.toString());
                        }

                    }

                    @Override
                    public void onCancelRequest() {
                       // progressBar.setVisibility(View.GONE);
                    }
                });
    }




}
