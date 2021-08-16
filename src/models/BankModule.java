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

    public Boolean updateRecords(Map<String, String> info) {
        if (!checkInfo(info.get("updateID"))) {
            return false;
        }
        String updateSql = String.format("UPDATE %s SET balance=%f, updateTime='%s' where user_id='%s'",
                "account",
                Double.parseDouble(info.get("newBalance")),
                util.getDateString("1"),
                info.get("updateID"));
        try {
            stmt = conn.connect().createStatement();
            stmt.executeUpdate(updateSql);
            conn.connect().close();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }

    }

    public Boolean deleteRecords(Map<String, String> info) {
        if (!checkInfo(info.get("deleteID"))) {
            return false;
        }
        System.out.println("delete");
        String updateSql = String.format("delete from %s where user_id='%s';",
                "account",
                info.get("deleteID"));
        System.out.println(updateSql);
        try {
            stmt = conn.connect().createStatement();
            stmt.executeUpdate(updateSql);
            conn.connect().close();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }


    public Boolean insertRecords(Map<String,String> info) {
        if (checkInfo(info.get("textID"))) {
            return true;
        }

        String formatToSql = String.format("INSERT INTO %s (user_id, age, sex, region) " +
                        "VALUES ('%s', '%s', '%s', '%s');",
                TableName,
                info.get("textID"),
                info.get("textAge"),
                info.get("textSex"),
                info.get("textRegion")
                );
        try {
            stmt = conn.connect().createStatement();
            stmt.executeUpdate(formatToSql);
            conn.connect().close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println("Records inserted!");
        return false;
    }


    public Boolean checkInfo(String userId) {
        String sql = String.format("SELECT * from %s where user_id ='%s';",
                "account",
                userId);

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



}
