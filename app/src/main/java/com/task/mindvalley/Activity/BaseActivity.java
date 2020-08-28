package com.task.mindvalley.Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


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
