package com.palvair.tuto.orm.service;

import com.palvair.tuto.orm.entity.Contact;
import com.palvair.tuto.orm.entity.User;
import com.palvair.tuto.orm.repository.UserRepository;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * Created by rpalvair on 20/10/2014.
 */
@Component
public class DefaultUserServiceDelegate implements UserServiceDelegate {
    @Autowired
    private UserRepository<User> userRepository;

    @Override
    public void saveRandomUser(int count) {
        for (int i = 0; i < count; i++) {
            final String firstname = RandomStringUtils.randomAlphabetic(5);
            final String lastname = RandomStringUtils.randomAlphabetic(5);
            final String age = RandomStringUtils.randomNumeric(2);
            User user = new User();
            user.setFirstname(firstname);
            user.setLastname(lastname);
            user.setAge(age);
            final Contact contact = new Contact();
            contact.setName("your friend");
            user.setContact(new ArrayList<Contact>() {{
                add(contact);
            }});
            userRepository.save(user);
        }
    }

    @Override
    public void delete(Iterable<? extends User> entities) {
        userRepository.delete(entities);
    }

    @Override
    public void saveRandomUser() {
        final String firstname = RandomStringUtils.randomAlphabetic(5);
        final String lastname = RandomStringUtils.randomAlphabetic(5);
        final String age = RandomStringUtils.randomNumeric(2);
        User user = new User();
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setAge(age);
        userRepository.save(user);
    }
}
