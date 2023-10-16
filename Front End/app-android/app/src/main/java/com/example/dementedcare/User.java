package com.example.dementedcare;

public class User {
    private String userId;
    private String selectedRole;
    private String email;

    private String firstName;
    private String lastName;
    private String contactNumber;

    public User() {
        // Required empty public constructor for Firestore to work with the class.
    }

    public User(String userId, String selectedRole, String email, String firstName, String lastName, String contactNumber) {
        this.userId = userId;
        this.selectedRole = selectedRole;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.contactNumber = contactNumber;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSelectedRole() {
        return selectedRole;
    }

    public void setSelectedRole(String selectedRole) {
        this.selectedRole = selectedRole;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

}
