package com.yaoh.AndroidDemo.dm.model;

import com.yaoh.AndroidDemo.dm.bean.User;

/**
 * Package com.yaoh.demo.dm.model.
 * Created by yaoh on 2017/09/07.
 * Company Beacool IT Ltd.
 * <p/>
 * Description:
 */
public class UserModel implements IUserModel {

    @Override
    public User login(String name, String password) {
        User user = new User();
        user.setUser_name(name);
        user.setUser_password(password);

        return user;
    }
}
