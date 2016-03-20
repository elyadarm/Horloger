package com.horlogernet.dao.user;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.horlogernet.dao.Dao;
import com.horlogernet.entity.User;


public interface UserDao extends Dao<User, Long>, UserDetailsService
{

	User findByName(String name);

}