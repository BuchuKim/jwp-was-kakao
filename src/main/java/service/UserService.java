package service;

import java.util.Map;

import db.DataBase;
import model.User;

public class UserService {
    public void createUser(final Map<String, String> queryParameters) {
        User user = new User(queryParameters.get("userId"),
            queryParameters.get("password"),
            queryParameters.get("name"),
            queryParameters.get("email"));
        
        if (DataBase.findUserById(user.getUserId()) != null) {
            throw new IllegalArgumentException("이미 존재하는 사용자입니다.");
        }
        DataBase.addUser(user);
    }
    
    public User getUser(final String testId) {
        return DataBase.findUserById(testId);
    }
}
