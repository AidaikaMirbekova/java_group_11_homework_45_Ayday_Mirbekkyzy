package com.company.lesson45;

import java.util.ArrayList;
import java.util.List;

public class User {
        private String name;
        private String email;
        private String login;
        private String password;

        public User() { }

    public User(String name, String email) {
        this(name,email, null,null);
    }
    public User(String name, String email, String login){
            this(name,email,login, null);
    }

    public User(String name, String email, String login, String password) {
        this.name = name;
        this.email = email;
        this.login = login;
        this.password = password;
    }

    public static User makeUser(String name, String email, String login, String password){
            User u = new User();
            u.name = name;
            u.email = email;
            u.login = login;
            u.password = password;
            return u;
        }



        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public class UserData{
            private final User.UserData userData = new User.UserData();
            private List<User> userList = new ArrayList<>();

            public UserData getUserData() {
                return userData;
            }

            public List<User> getUserList() {
                return userList;
            }

            public void setUserList(List<User> userList) {
                this.userList = userList;
            }

            public UserData(){
                userList.add(new User("Aydayka", "aydayka@gmail.com", "ayday1", null));
                userList.add(new User("Anton", "antoshka@gmail.com", "anton", null));



            }

        }
    }
