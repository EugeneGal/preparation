package org.example.mongotemplate.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.example.mongotemplate.document.User;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

/**
 * Default implementation of {@link UserOperations}.
 *
 * @author Yauheni Halaidzin
 * @since 21.06.2024
 */
@Repository
@RequiredArgsConstructor
public class DefaultUserOperations implements UserOperations {

    private final MongoTemplate mongoTemplate;

    @Override
    public List<User> getAllUsers() {
        return mongoTemplate.findAll(User.class);
    }

    @Override
    public User getUserById(String userId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(userId));

        return mongoTemplate.findOne(query, User.class);
    }

    @Override
    public User addNewUser(User user) {
        return mongoTemplate.save(user);
    }

    @Override
    public Object getAllUserSettings(String userId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(userId));
        User user = mongoTemplate.findOne(query, User.class);

        return user != null ? user.getUserSettings() : null;
    }

    @Override
    public String getUserSetting(String userId, String key) {
        Query query = new Query();
        query.fields().include("userSettings");
        query.addCriteria(Criteria.where("userId").is(userId).andOperator(Criteria.where("userSettings." + key).exists(true)));
        User user = mongoTemplate.findOne(query, User.class);

        return user != null ? user.getUserSettings().get(key) : null;
    }

    @Override
    public void addUserSetting(String userId, String key, String value) {
        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(userId));
        User user = mongoTemplate.findOne(query, User.class);

        if (user != null) {
            user.getUserSettings().put(key, value);
            mongoTemplate.save(user);
        }
    }

}
