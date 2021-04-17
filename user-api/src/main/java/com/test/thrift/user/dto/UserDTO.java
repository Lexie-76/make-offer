package com.test.thrift.user.dto;

public class UserDTO {
    private int id;
    private String username;
    private String email;

    public UserDTO() {
    }

    public UserDTO(String username, String realName, String email) {
        this.username = username;
        this.email = email;
    }

    public UserDTO(int id, String username, String realName, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }




    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
