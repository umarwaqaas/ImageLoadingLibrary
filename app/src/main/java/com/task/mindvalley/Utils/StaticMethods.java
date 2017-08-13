package com.task.mindvalley.Utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;

import com.task.mindvalley.Employee.Employee;
import com.task.mindvalley.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Umer Waqas on 11/08/2017.
 */

public class StaticMethods {
    /**
     * Show dialog  , if there is no internet connection for first time.
     *
     * @param context {@link Context} ,
     */
    public static void ShowNoInternetDialog(final Context context) {
        new AlertDialog.Builder(context)
                .setTitle(R.string.error)
                .setMessage(R.string.no_internet)
                .setPositiveButton(R.string.settings, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            context.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                        } catch (Exception e) {

                        }

                    }
                }).setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).show();
    }
    public static void ShowErrorMessageDialog(final Context context) {
        new AlertDialog.Builder(context)
                .setTitle(R.string.error)
                .setMessage(R.string.error_message)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            dialog.dismiss();
                        } catch (Exception e) {

                        }

                    }
                }).show();
    }


    /**
     * Will return the list of employees.
     *
     * @param employeesArray JsonArray ,
     */
    public static ArrayList<Employee> GetEmployeesList(JSONArray employeesArray) {
        ArrayList<Employee> employees = null;
        Employee employee = null;
        if (employeesArray != null && employeesArray.length() > 0) {

            employees = new ArrayList<>();
            for (int i = 0; i < employeesArray.length(); i++) {
                try {
                    JSONObject employeeObject = employeesArray.getJSONObject(i);
                    employee = new Employee();
                    employee.setEmployeeID(employeeObject.getString("id"));
                    employee.setEmployeeName(employeeObject.getJSONObject("user").get("name").toString());
                    employee.setEmployeeUsername(employeeObject.getJSONObject("user").get("username").toString());
                    employee.setEmployeeAvatarUrl(employeeObject.getJSONObject("user").getJSONObject("profile_image").get("large").toString());
                    employees.add(employee);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return employees;


    }
}
