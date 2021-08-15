package models;

import java.util.Arrays;

/**
 * @author chuan xu
 */
public class InitDB {
    public static void main(String[] args) {
        // user table init
        UsersModule userDB = new UsersModule();
        userDB.initTable();

        // account table init
        AccountModule accountDB = new AccountModule();
        accountDB.initTable();

        // bank table init
        BankModule bankDB = new BankModule();
        bankDB.initTable();
    }
}
