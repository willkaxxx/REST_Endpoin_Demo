package com.epam.training.demo.mapper;

import com.epam.training.demo.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User convert(Object obj){
        Object[] data = (Object[]) obj;
        if(data.length != 2)
            throw new ClassCastException("Wrong parameter amount");
        User user = new User();
        user.setId((Long)data[0]);
        user.setName(data[1].toString());
        return user;
    }
}
