package models;

import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

import utils.*;

public class UsersModule extends Bank{
    DBConnect conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    private final String TableName = "user_info";
    private final char secret = '3';

    public UsersModule() {
        conn = new DBConnect();
    }

    public Boolean getCredentials(String username, String password){

        String query = "SELECT * FROM user_info WHERE user_name = ? and user_pwd = ?;";
        try(PreparedStatement stmt = conn.connect().prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, util.createHashes(password));
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                setUserID(rs.getString("user_id"));
                setAdmin(rs.getBoolean("admin"));
                return true;
            }
        }catch (SQLException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return false;
    }
    // 初始化
    public void initTable() {
        try {
            System.out.println("Connecting to database to create User Table ...");
            System.out.println("Connected database successfully...");
            stmt = conn.connect().createStatement();

            String createSqlTable = "CREATE TABLE user_info " +
                                    "(pid INTEGER not NULL AUTO_INCREMENT, " +
                                    " user_id VARCHAR(128) not null, " +
                                    " user_name VARCHAR(64) not null, " +
                                    " user_pwd VARCHAR(64) not null, " +
                                    " admin tinyint(1) not null DEFAULT 0," +
                                    " PRIMARY KEY (pid))";
            stmt.executeUpdate(createSqlTable);
            System.out.println("Created User Table in given database...");
            conn.connect().close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    // 注册时候检查重复
    public Boolean checkUserInfo(String userName) {
        String sql = String.format("SELECT * from %s where user_name='%s';", TableName, userName);

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

//    public Boolean login(String userName,String userPwd) {
//        if (!checkUserInfo(userName)) {
//            System.out.println("名称不存在");
//            return false;
//        }
//        String encryptResult = util.encryptAndDecrypt(userPwd, secret);
//        String logSql = String.format("select * from %s where user_name='%s' and user_pwd='%s';",
//                TableName,
//                userName,
//                encryptResult);
//        try {
//            stmt = conn.connect().createStatement();
//            rs = stmt.executeQuery(logSql);
//            conn.connect().close();
//            return rs.next();
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//            return false;
//        }
//    }

    public Boolean register(String userName,String userPwd) throws NoSuchAlgorithmException {
        if (checkUserInfo(userName)) {
            System.out.println("名称存在");
            return false;
        }

//        String encryptResult = util.encryptAndDecrypt(userPwd, secret);
        String insertSql = String.format("INSERT INTO %s (user_id, user_name, user_pwd)" +
                "VALUES ('%s', '%s', '%s');",
                TableName,
                util.getUUID(),
                userName,
                util.createHashes(userPwd));
        try {
            stmt = conn.connect().createStatement();
            stmt.executeUpdate(insertSql);
            conn.connect().close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
        System.out.println("Records inserted!");
        return true;
    }


}
