package com.yaoh.AndroidDemo.dm.view;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.yaoh.AndroidDemo.R;
import com.yaoh.AndroidDemo.dm.bean.User;
import com.yaoh.AndroidDemo.dm.presenter.LoginPresenter;

public class TestMVPActivity extends AppCompatActivity implements View.OnClickListener, ILoginView {

    private static final String TAG = "TestMVPActivity";
    private LoginPresenter loginPresenter;

    private Button btn;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_mvp);
        progressDialog = new ProgressDialog(this);

        btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(this);

        loginPresenter = new LoginPresenter(this);
    }

    @Override
    public void onLoginSuccess(User user) {
        progressDialog.dismiss();

        Log.e(TAG, "登录成功--->" + user.toString());
    }

    @Override
    public void onLoginError(int Type) {
        progressDialog.dismiss();
    }

    @Override
    public void onClick(View v) {
        progressDialog.setMessage("请稍候");
        progressDialog.show();

        loginPresenter.login("yaoh", "12345678");
    }
}
