package com.demo.dm.model;

import com.demo.dm.bean.User;

/**
 * Package com.demo.dm.model.
 * Created by yaoh on 2017/09/07.
 * Company Beacool IT Ltd.
 * <p/>
 * Description:
 */
public interface IUserModel {

    User login(String name, String password);

}
