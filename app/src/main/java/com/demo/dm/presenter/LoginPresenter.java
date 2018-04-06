package com.demo.dm.presenter;

import android.os.Handler;
import android.os.Message;

import com.demo.dm.bean.User;
import com.demo.dm.model.IUserModel;
import com.demo.dm.model.UserModel;
import com.demo.dm.view.ILoginView;

/**
 * Package com.demo.dm.presenter.
 * Created by yaoh on 2017/09/07.
 * Company Beacool IT Ltd.
 * <p/>
 * Description:
 */
public class LoginPresenter {
    IUserModel userModel;
    ILoginView loginView;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    public LoginPresenter(ILoginView loginView) {
        this.loginView = loginView;
        userModel = new UserModel();
    }

    public void login(final String name, final String password) {

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                User user = userModel.login(name, password);
                loginView.onLoginSuccess(user);
            }
        }, 2000);
    }
}
