package models;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.*;

public class AccountModule {
    DBConnect conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    double balance;
    private final String TableName = "account";

    public AccountModule() {
        conn = new DBConnect();
    }

    public void initTable() {
        try {
            System.out.println("Connecting to database to create Account Table ...");
            System.out.println("Connected database successfully...");
            stmt = conn.connect().createStatement();

            String createSqlTable = "CREATE TABLE account " +
                                    "(pid INTEGER not NULL AUTO_INCREMENT, " +
                                    " user_id VARCHAR(128) not null, " +
                                    " balance double not null DEFAULT 0.00, " +
                                    " createTime timestamp not null DEFAULT current_timestamp , " +
                                    " updateTime timestamp not null DEFAULT current_timestamp , " +
                                    " PRIMARY KEY ( pid ))";
            stmt.executeUpdate(createSqlTable);
            System.out.println("Created Account Table in given database...");
            conn.connect().close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    public Double getAccountBalance(String user_id) {
        Double result = 0.00;
        String balanceSql = String.format("select balance from %s where user_id='%s';",
                TableName,
                user_id
                );
        try {
            stmt = conn.connect().createStatement();
            rs = stmt.executeQuery(balanceSql);
            while (rs.next()) {
                result = rs.getDouble("balance");
            }
            conn.connect().close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }


    public void depositAccount(Double amount, String userID) {
        balance = getAccountBalance(userID) + amount;
        String updateSql = String.format("UPDATE %s SET balance=%f, updateTime='%s' where user_id='%s'",
                TableName,
                balance,
                getDateString("1"),
                userID);

        try {
            stmt = conn.connect().createStatement();
            stmt.executeUpdate(updateSql);
            conn.connect().close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void withdrawal(Double amount, String userID){
        balance = getAccountBalance(userID) - amount;
        String updateSql = String.format("UPDATE %s SET balance=%f, updateTime='%s' where user_id='%s'",
                TableName,
                balance,
                getDateString("1"),
                userID);

        try {
            stmt = conn.connect().createStatement();
            stmt.executeUpdate(updateSql);
            conn.connect().close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static String getDateString(String timezone){
        TimeZone time = TimeZone.getTimeZone(timezone);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        TimeZone.setDefault(time);
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        return format.format(date);
    }

}
