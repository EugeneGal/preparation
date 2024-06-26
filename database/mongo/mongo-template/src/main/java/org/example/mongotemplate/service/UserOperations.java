package org.example.mongotemplate.service;

import java.util.List;

import org.example.mongotemplate.document.User;

/**
 * User operations.
 *
 * @author Yauheni Halaidzin
 * @since 21.06.2024
 */
public interface UserOperations {

    List<User> getAllUsers();

    User getUserById(String userId);

    User addNewUser(User user);

    Object getAllUserSettings(String userId);

    String getUserSetting(String userId, String key);

    void addUserSetting(String userId, String key, String value);

}
