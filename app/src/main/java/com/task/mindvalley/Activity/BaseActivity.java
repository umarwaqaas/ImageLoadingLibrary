package com.task.mindvalley.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;



/**
 * Created by Umer Waqas on 09/08/2017.
 */

public abstract class BaseActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(getContentView());

        initViews();

        setTextLabels();

        loadData();

    }

    protected abstract int getContentView();

    protected abstract void initViews();

    protected abstract void setTextLabels();

    public void setTitle(CharSequence title, int icon) {
    }

    protected abstract void loadData();




}
