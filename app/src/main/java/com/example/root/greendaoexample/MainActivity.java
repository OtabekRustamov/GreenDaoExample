package com.example.root.greendaoexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.greenrobot.greendao.database.Database;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText etName;
    private EditText etgroup;
    private Button btnAdd;
    private Button btnDel;
    private Button btnEd;
    private RecyclerView recyclerView;
    private DaoSession daoSession;
    private List<Student> students;
    //public final String BASE_URL = "http://api.myservice.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etName = (EditText) findViewById(R.id.etName);
        etgroup = (EditText) findViewById(R.id.etgroup);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnDel = (Button) findViewById(R.id.bn_delete);
        btnEd = (Button) findViewById(R.id.btnAdd);
        recyclerView = (RecyclerView) findViewById(R.id.rvMainActivity);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // read all data
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "my-db");
        final Database database = helper.getWritableDb();
        daoSession = new DaoMaster(database).newSession();

        students = daoSession.getStudentDao().loadAll();
        recyclerView.setAdapter(new MyAdapter());

        btnDel.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        btnEd.setOnClickListener(this);
        // save student

    }

    private void initUI() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAdd: {
                if (!etName.getText().toString().isEmpty()) {
                    Student student = new Student();
                    student.setName(etName.getText().toString());
                    student.setGroup(Integer.parseInt(etgroup.getText().toString()));
                    // save
                    daoSession.getStudentDao().insert(student);
                }
                break;
            }
            case R.id.bn_edit: {
                Student student = new Student();
                daoSession.getStudentDao();

                break;
            }
        }
    }


    private class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.item_rv, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.textView.setText(students.get(position).getName());
            holder.tvGroup.setText("" + students.get(position).getGroup());
        }

        @Override
        public int getItemCount() {
            return students.size();
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public TextView tvGroup;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tvName);
            tvGroup = (TextView) itemView.findViewById(R.id.tvGroup);
        }
    }
}
