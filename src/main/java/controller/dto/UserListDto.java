package controller.dto;

import java.util.List;

import model.User;

public class UserListDto {
    private final List<User> users;

    public UserListDto(List<User> users) {
        this.users = users;
    }

    public List<User> getUsers() {
        return users;
    }
}
