package com.demo.dm.view;

import com.demo.dm.bean.User;

/**
 * Package com.demo.dm.view.
 * Created by yaoh on 2017/09/07.
 * Company Beacool IT Ltd.
 * <p/>
 * Description:
 */
public interface ILoginView {
    void onLoginSuccess(User user);

    void onLoginError(int Type);
}
