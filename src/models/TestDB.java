package models;

import java.util.Arrays;

public class TestDB {
    public static void main(String[] args) {
        // USER
//        UsersModule userDB = new UsersModule();
//        userDB.initTable();
//        if (userDB.login("xc10","xcvba1")) {
//            System.out.println("登陆成功");
//        } else {
//            System.out.println("密码不正确");
//        }
//        userDB.checkUserInfo("1");
        // Account
//        AccountModule accountDB = new AccountModule();
//        accountDB.initTable();
//        accountDB.initAccount("fc1b2f9c-fdb7-462e-b406-c31117a4186c");
//        accountDB.depositAccount("fc1b2f9c-fdb7-462e-b406-c31117a4186c", 99.01);
//        accountDB.getAccountBalance("fcda9e0c-513f-4829-9fb5-e5233497a2e0");
//        accountDB.depositAccount("fcda9e0c-513f-4829-9fb5-e5233497a2e0",100.00);
//        accountDB.depositAccount("fcda9e0c-513f-4829-9fb5-e5233497a2e0",100.00);
        String a = "110.1001";
        System.out.println(Arrays.toString(a.split("\\.")));
    }
}
