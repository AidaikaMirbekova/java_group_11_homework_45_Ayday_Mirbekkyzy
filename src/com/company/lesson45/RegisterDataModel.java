package com.company.lesson45;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RegisterDataModel {
    private UserReg user = new UserReg("Apache", "FreeMarker");
    private LocalDateTime currentDateTime = LocalDateTime.now();
    private List<UserReg> userList = new ArrayList<>();

    public RegisterDataModel() {
        userList.add(new UserReg("Marco","Fill", 1234));
        userList.add(new UserReg("Winston", "Duarte",1234));
        userList.add(new UserReg("Amos", "Burton",1234));
        userList.get(1).setEmailConfirmed(true);
    }

    public UserReg getUser() {
        return user;
    }

    public void setUser(UserReg user) {
        this.user = user;
    }

    public LocalDateTime getCurrentDateTime() {
        return currentDateTime;
    }

    public void setCurrentDateTime(LocalDateTime currentDateTime) {
        this.currentDateTime = currentDateTime;
    }

    public List<UserReg>  getUsers() {
        return userList;
    }

    public void setUsers(List<UserReg> userList) {
        this.userList = userList;
    }



    public static class UserReg {
        private String firstName;
        private String lastName;
        private String email;
        private boolean emailConfirmed = false;

        public UserReg(String firstName) {
            this(firstName, null, 1234);
        }

        public UserReg(String firstName, String lastName) {
            this(firstName, lastName, 1234);
        }

        public UserReg(String firstName, String lastName, int password) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = firstName+"@test.mail";
        }


        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public boolean isEmailConfirmed() {
            return emailConfirmed;
        }

        public void setEmailConfirmed(boolean emailConfirmed) {
            this.emailConfirmed = emailConfirmed;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }


}
