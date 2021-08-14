package models;

public abstract class Bank extends DBConnect{
    public String userID;
    public String userName;
    public Double balance;

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public boolean admin;

    public String getUserID() {
        return userID;
    }

    public String getUserName() {
        return userName;
    }

    public Double getBalance() {
        return balance;
    }

    public boolean isAdmin() {
        return admin;
    }
}
