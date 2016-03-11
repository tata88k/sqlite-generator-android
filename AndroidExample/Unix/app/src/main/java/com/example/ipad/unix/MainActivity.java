package com.example.ipad.unix;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.ipad.unix.db.DataAccessProxy;
import com.example.ipad.unix.db.Strings;
import com.example.ipad.unix.model.Employee;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    int count;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int version = 1;
        Context context = getApplication();

        /**create or open database*/
        final DataAccessProxy proxy = new DataAccessProxy(context, "unix.db", version);

        /** get count*/
//        count = proxy.getCount(Employee.class, "where name=?", new String[]{"Oliver"});
        count = proxy.getCount(Employee.class, null, null);
        findViewById(R.id.btn_toDo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /**query data from database*/
//                final List<Employee> list = proxy.query(Employee.class, "select * from " +  new Employee().getTableName() + " where departmentId=?", new String[]{"1"});
//                final List<Employee> list = proxy.load(Employee.class, " where departmentId=?", new String[]{"1"});
                final List<Employee> list = proxy.load(Employee.class, null, null);

                AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Results")
                        .setPositiveButton("update", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                count++;

                                /**update data from database*/
                                if (list == null || list.size() == 0) return;
                                Employee employee = list.get(0);
                                employee.setName("update data !");
                                proxy.update(employee);
                            }
                        })
                        .setNegativeButton("delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                count++;

                                /**delete data from database*/
                                if (list == null || list.size() == 0) return;
                                Employee employee = list.get(0);
                                proxy.delete(employee);
                            }
                        })
                        .setNeutralButton("add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                count++;
                                count++;

                                /**insert data into database*/
                                Employee employee = new Employee();
                                employee.setName("name" + String.valueOf(count));
                                employee.setDepartmentId(1);
                                /**if you don't set the primary key "id" to AUTOINCREMENT , remember to assign*/
                                employee.setId(count);
                                proxy.insert(employee);
                            }
                        })
                        .create();

                StringBuilder sb = new StringBuilder();
                int index = 0;
                if (list == null || list.size() == 0) {
                    sb.append("no data !");
                } else {
                    for (Employee e : list) {
                        index++;
                        sb.append("row__").append(String.valueOf(index)).append("__: ").append(e.getName()).append("\n");
                    }
                }
                dialog.setMessage(sb.toString());
                dialog.show();
            }
        });
    }
}
