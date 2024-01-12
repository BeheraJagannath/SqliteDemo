package com.example.squlitetutorial;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
public class NewActivity extends AppCompatActivity {
    SqliteHelper sqliteHelper;
    private EditText courseName, courseEmail, courseMobile, courseGender;
    private TextView addCourseBtn;
    String Name, Email, Mobile, Gender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        sqliteHelper = new SqliteHelper(this);

        courseName = findViewById(R.id.add_name);
        courseEmail = findViewById(R.id.add_email);
        courseMobile = findViewById(R.id.add_mobilen);
        courseGender = findViewById(R.id.add_gender);
        addCourseBtn = findViewById(R.id.idBtnAddCourse);

        if (getIntent().getBooleanExtra("viewUpdate", false)) {
            Name = getIntent().getStringExtra("name");
            Email = getIntent().getStringExtra("email");
            Mobile = getIntent().getStringExtra("mobile");
            Gender = getIntent().getStringExtra("gender");
            addCourseBtn.setText("Update");

            setviewUpdateNote();
        }

        addCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = courseName.getText().toString().trim();
                String email = courseEmail.getText().toString();
                String mobile = courseMobile.getText().toString();
                String gender = courseGender.getText().toString();
                if (getIntent().getBooleanExtra("viewUpdate", false)) {
                    if (TextUtils.isEmpty(name)) {
                        courseName.setError("Name is required");
                        return;
                    }
                    if (TextUtils.isEmpty(email)) {
                        courseEmail.setError("Email is required");
                        return;
                    }
                    if (TextUtils.isEmpty(mobile)) {
                        courseMobile.setError("Mobile No is required");
                        return;
                    }
                    if (TextUtils.isEmpty(gender)) {
                        courseGender.setError("Gender is required");
                        return;
                    }
                    sqliteHelper.updateCourse(Name, courseName.getText().toString(),
                    courseEmail.getText().toString(), courseMobile.getText().toString(), courseGender.getText().toString());

                    finish();

                }else {

                    if (TextUtils.isEmpty(name)) {
                        courseName.setError("Name is required");
                        return;
                    }
                    if (TextUtils.isEmpty(email)) {
                        courseEmail.setError("Email is required");
                        return;
                    }
                    if (TextUtils.isEmpty(mobile)) {
                        courseMobile.setError("Mobile No is required");
                        return;
                    }
                    if (TextUtils.isEmpty(gender)) {
                        courseGender.setError("Gender is required");
                        return;
                    }

                    sqliteHelper.addNewCourse(name, email, mobile, gender);
                    Log.d("data added","Course has been added.");
                    courseName.setText("");
                    courseEmail.setText("");
                    courseMobile.setText("");
                    courseGender.setText("");

                    finish();
                }

             }
        });
    }

    private void setviewUpdateNote() {
        courseName.setText(Name);
        courseEmail.setText(Email);
        courseMobile .setText(Mobile);
        courseGender.setText(Gender);

    }




}