package com.example.universitymanagementsystem;

public class UserSession {
    private static UserSession instance;
    private User loggedInUser;

    // Private constructor to prevent instantiation
    private UserSession() {}

    // Public method to get the singleton instance
    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    // Method to set the logged-in user
    public void setLoggedInUser(User user) {
        this.loggedInUser = user;
    }

    // Method to get the logged-in user
    public User getLoggedInUser() {
        return loggedInUser;
    }

    // Method to clear the session
    public void clearSession() {
        this.loggedInUser = null;
    }
}

