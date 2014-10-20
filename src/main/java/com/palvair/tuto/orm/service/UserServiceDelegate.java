package com.palvair.tuto.orm.service;

import com.palvair.tuto.orm.entity.User;

/**
 * Created by rpalvair on 20/10/2014.
 */
public interface UserServiceDelegate {

    void saveRandomUser(int count);

    void delete(Iterable<? extends User> entities);

    void saveRandomUser();
}
