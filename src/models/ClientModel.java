package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class ClientModel extends DBConnect{

    private int pid;
    private AccountModule accountModule;
    private final String TableName = "account";
    private double balance;

    ResultSet rs = null;

    // Declare DB objects
    DBConnect conn = null;
    Statement stmt = null;
    //set up Bank object
    BankModule custBank;

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public ClientModel() {
        conn = new DBConnect();

        //simulate bank data affliation of client
        custBank = new BankModule();
    }

    public Boolean checkBankInfo(String userId) {
        String sql = String.format("SELECT * from %s where user_id='%s';", TableName, userId);

        try {
            stmt = conn.connect().createStatement();
            rs = stmt.executeQuery(sql);
            conn.connect().close();
            return rs.next();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public void insertRecord(double bal, String userId) {
        try {
            // Execute a query
            System.out.println("Inserting record into the table...");
            stmt = conn.getConnection().createStatement();
            String sql = null;
            System.out.println("user id:" + userId);

            sql = " insert into account (user_id, balance) values('" + userId + "', '" + bal + "')";

            stmt.executeUpdate(sql);
            conn.getConnection().close();

            System.out.println("Balance inserted $" + bal + " for ClientID " + userId);

        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public List<ClientModel> getAccounts(String userID) {
        List<ClientModel> accounts = new ArrayList<>();
        String query = "SELECT user_id,balance FROM account WHERE user_id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, userID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ClientModel account = new ClientModel();
                // grab record data by table field name into ClientModel account object
//                account.setPid(resultSet.getInt("pid"));
//                account.setUserID(resultSet.getString("user_id"));
                account.setBalance(resultSet.getDouble("balance"));
                // add account data to arraylist
                accounts.add(account);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }

}