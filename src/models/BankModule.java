package models;

import utils.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BankModule {
    DBConnect conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    private final String TableName = "bank_manager";
    private final char secret = '3';


    public BankModule() {
        conn = new DBConnect();
    }

    public void initTable() {
        try {
            System.out.println("Connecting to database to create Bank Manager Table ...");
            System.out.println("Connected database successfully...");
            stmt = conn.connect().createStatement();

            String createSqlTable = "CREATE TABLE bank_manager " +
                                    "(pid INTEGER not NULL AUTO_INCREMENT, " +
                                    " bank_id VARCHAR(128) not null, " +
                                    " bank_name VARCHAR(64) not null, " +
                                    " bank_pwd VARCHAR(64) not null, " +
                                    " limits VARCHAR(64) not null, " +
                                    " PRIMARY KEY ( pid ))";
            stmt.executeUpdate(createSqlTable);
            System.out.println("Created Bank Manager Table in given database...");
            conn.connect().close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
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
