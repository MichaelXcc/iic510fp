package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class ClientModel extends DBConnect implements User<BankModule>{

    private String userId;
    private int pid;
    private double balance;
    private AccountModule accountModule;
    private final String TableName = "account";
    ResultSet rs = null;

    // Declare DB objects
    DBConnect conn = null;
    Statement stmt = null;
    //set up Bank object
    BankModule custBank;

    public ClientModel() {
        conn = new DBConnect();

        //simulate bank data affliation of client
        custBank = new BankModule();
//        custBank.setBankId(100);
//        custBank.setBankName("Bank of IIT");
//        custBank.setBankAddress("10 W 35th St, Chicago, IL 60616");
    }

    /* getters & setters */

    public String getUserid() {
        return userId;
    }

    public void setUserid(String userId) {
        this.userId = userId;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getPid() {
        return pid;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }


    public Boolean checkBankInfo() {

        String sql = String.format("SELECT * from %s where user_id='%s';", TableName, getUserid());

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

    // INSERT INTO METHOD
    public void insertRecord(String userId, double bal) {
        try {
            setUserid(userId);
            // Execute a query
            System.out.println("Inserting record into the table...");
            stmt = conn.getConnection().createStatement();
            String sql = null;

            sql = " insert into account (user_id, balance) values('" + userId + "', '" + bal + "')";

            stmt.executeUpdate(sql);
            conn.getConnection().close();

            System.out.println("Balance inserted $" + bal + " for ClientID " + userId);

        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public List<ClientModel> getAccounts(String userId) {
        List<ClientModel> accounts = new ArrayList<>();
        String query = "SELECT user_id,balance FROM account WHERE user_id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1,userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ClientModel account = new ClientModel();
                // grab record data by table field name into ClientModel account object
//                account.setPid(resultSet.getInt("pid"));
                account.setUserid(resultSet.getString("user_id"));
                account.setBalance(resultSet.getDouble("balance"));
                // add account data to arraylist
                accounts.add(account);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // return arraylist
        System.out.println(accounts.toString());
        return accounts;
    }

    @Override
    public BankModule getClientInfo() {
        // TODO Auto-generated method stub
        return custBank;
    }
}