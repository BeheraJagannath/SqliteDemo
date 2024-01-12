package com.example.squlitetutorial;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;
import com.example.squlitetutorial.Model.Password;
import java.util.List;

public class PatternActivity extends AppCompatActivity {
    Password Mpassword ;
    String userPassword ;
    PatternLockView patternLockView ;
    TextView state_text ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pattern);
        patternLockView = findViewById(R.id.patternView);
        state_text = findViewById(R.id.state_text);
        state_text.setText("Draw unlock pattern");
        Mpassword = new Password(this);
        if (Mpassword.getPASSWORD_KEY()==null){
            
        }
        setUpdateListener();
    }
    private void setUpdateListener() {
        patternLockView.addPatternLockListener(new PatternLockViewListener() {
            @Override
            public void onStarted() {

            }
            @Override
            public void onProgress(List<PatternLockView.Dot> progressPattern) {

            }
            @Override
            public void onComplete(List<PatternLockView.Dot> pattern) {
                String password = PatternLockUtils.patternToString(patternLockView , pattern);
                if (password.length()<4){
                    state_text.setText(Mpassword.SCHEMA_FAILED);
                    patternLockView.clearPattern();
                    return;

                }
                if (Mpassword.getPASSWORD_KEY()==null){
                    if (Mpassword.isFirst){
                        userPassword = password;
                        Mpassword.setFirst(false);
                        state_text.setText(Mpassword.CONFORM_PATTERN);

                    }else {
                        if (userPassword.equals(password)){
                            Mpassword.setPASSWORD_KEY(password);
//                            state_text.setText(Mpassword.PATTERN_SET);
                            state_text.setVisibility(View.GONE);
                            gotoMainActivity();

                        }else {
                            state_text.setText(Mpassword.PATTERN_SET);
//                            state_text.setVisibility(View.GONE);

                        }
                    }
                }else {
                    if (Mpassword.isCorrect(password)){
//                        state_text.setText(Mpassword.PATTERN_SET);
                        state_text.setVisibility(View.GONE);
                        gotoMainActivity();
                    }else {
                        state_text.setText(Mpassword.INCORRECT_PATTERN);
                    }
                }
                patternLockView.clearPattern();

            }
            @Override
            public void onCleared() {

            }
        });

    }
    private void gotoMainActivity() {
        Intent intent = new Intent(PatternActivity.this , MainActivity.class);
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {
        if (Mpassword.getPASSWORD_KEY()==null){
            Mpassword.setFirst(true);
            state_text.setText(Mpassword.FIRST_USE);
        }else {
            finish();
            super.onBackPressed();
        }
    }
}