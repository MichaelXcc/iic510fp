package models;

import utils.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

public class BankModule {
    DBConnect conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    private final String TableName = "bank";
    private final char secret = '3';


    public BankModule() {
        conn = new DBConnect();
    }

    public void initTable() {
        try {
            System.out.println("Connecting to database to create Bank Manager Table ...");
            System.out.println("Connected database successfully...");
            stmt = conn.connect().createStatement();

            String createSqlTable = "CREATE TABLE bank " +
                                    "(pid INTEGER not NULL AUTO_INCREMENT, " +
                                    " user_id VARCHAR(128) not null, " +
                                    " age INT not null," +
                                    " sex VARCHAR(10) not null, " +
                                    " region VARCHAR(10) not null, " +
                                    " PRIMARY KEY ( pid ))";
            stmt.executeUpdate(createSqlTable);
            System.out.println("Created Bank Manager Table in given database...");
            conn.connect().close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void updateRecords(Map<String, String> info) {
        String updateSql = String.format("UPDATE %s SET balance=%f, updateTime='%s' where user_id='%s'",
                TableName,
                Double.parseDouble(info.get("newBalance")),
                util.getDateString("1"),
                info.get("updateID"));

        try {
            stmt = conn.connect().createStatement();
            stmt.executeUpdate(updateSql);
            conn.connect().close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void deleteRecords(Map<String, String> info) {
        String updateSql = String.format("delete from %s where user_id='%s'",
                TableName,
                info.get("updateID"));
        try {
            stmt = conn.connect().createStatement();
            stmt.executeUpdate(updateSql);
            conn.connect().close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    public void insertRecords(Map<String,String> info) {
        String formatToSql = String.format("INSERT INTO %s (user_id, age, sex, region) " +
                        "VALUES ('%s', '%s', '%s', '%s');",
                TableName,
                info.get("usrID"),
                info.get("age"),
                info.get("sex"),
                info.get("region")
                );
        try {
            stmt = conn.connect().createStatement();
            stmt.executeUpdate(formatToSql);
            conn.connect().close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println("Records inserted!");
    }


    public Boolean checkBankInfo(String bankName) {
        String sql = String.format("SELECT * from %s where bank_name='%s';", TableName, bankName);

        try {
            stmt = conn.connect().createStatement();
            rs = stmt.executeQuery(sql);
            conn.connect().close();
            return rs.next();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return true;
        }
    }


    public Boolean bankLogin(String bankName, String bankpwd) {
        if (!checkBankInfo(bankName)) {
            System.out.println("名称不存在");
            return false;
        }
        String encryptResult = util.encryptAndDecrypt(bankpwd, secret);
        String logSql = String.format("select * from %s where user_name='%s' and user_pwd='%s';",
                TableName,
                bankName,
                encryptResult);
        try {
            stmt = conn.connect().createStatement();
            rs = stmt.executeQuery(logSql);
            conn.connect().close();
            return rs.next();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }



}
