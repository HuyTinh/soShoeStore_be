package com.personal.soshoestore_be.service;

import com.personal.soshoestore_be.dto.UserRegisterDTO;
import com.personal.soshoestore_be.dto.UserUpdateContactDTO;
import com.personal.soshoestore_be.dto.UserUpdatePasswordDTO;
import com.personal.soshoestore_be.dto.UserUpdateProfileDTO;
import com.personal.soshoestore_be.model.User;

public interface UserService {
    String createUser(UserRegisterDTO userRegisterDto);

    String login(String email, String password) ;

    String updateUserProfile(Long id, UserUpdateProfileDTO userUpdateProfileDTO);

    String updateUserContact(Long id, UserUpdateContactDTO userUpdateContactDTO);

    void changePassword(Long id, UserUpdatePasswordDTO userUpdatePasswordDTO);

    User getUserByToken(String token);
}

