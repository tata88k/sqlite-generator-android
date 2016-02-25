package com.example.ipad.unix;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.ipad.unix.db.DataAccessProxy;
import com.example.ipad.unix.model.Employee;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    int count = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**create or open database*/
        final DataAccessProxy proxy = new DataAccessProxy(this, "unix.db", 1);
        final Employee employee = new Employee();

        findViewById(R.id.btn_toDo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**query data from database*/
                final List<Employee> list = proxy.query(new Employee(), "select * from " + employee.getTableName() + " where departmentId=?", new String[]{"1"});
                AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Results")
                        .setPositiveButton("update", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                /**update data from database*/
                                if (list == null || list.size() == 0) return;
                                Employee em = list.get(0);
                                em.setName("update data !");
                                proxy.update(em);
                            }
                        })
                        .setNegativeButton("delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                /**delete data from database*/
                                if (list == null || list.size() == 0) return;
                                Employee em = list.get(0);
                                proxy.delete(em);
                            }
                        })
                        .setNeutralButton("add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Employee e = new Employee();
                                count++;
                                e.setName("name" + String.valueOf(count));
                                e.setDepartmentId(1);
                                /**if you don't set the primary key "id" to AUTOINCREMENT , remember to assign*/
                                e.setId(count);
                                proxy.insert(e);
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
