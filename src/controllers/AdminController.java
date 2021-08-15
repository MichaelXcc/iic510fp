package controllers;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import models.BankModule;
import models.DBConnect;
import application.DynamicTable;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class AdminController {

    @FXML
    private Pane pane1;
    @FXML
    private Pane pane2;
    @FXML
    private Pane pane3;
    @FXML
    private TextField textID;
    @FXML
    private TextField textAge;
    @FXML
    private TextField textSex;
    @FXML
    private TextField textRegion;
    @FXML
    private TextField textName;
    @FXML
    private TextField updateID;
    @FXML
    private TextField newBalance;

    BankModule bm;


    DBConnect conn = null;
    Statement stmt = null;
    private String userID;
    Map<String, String> dataMap = LoginController.dataMap;


    public AdminController() {
        conn = new DBConnect();
        userID = dataMap.get("usrID");
        bm = new BankModule();
    }

    public void viewAccounts() {

        DynamicTable d = new DynamicTable();
        // call method from DynamicTable class and pass some arbitrary query string
//        d.buildData("Select * from account");
        d.buildData("Select a.user_id, a.balance, a.createTime, a.updateTime, IFNULL(b.age,\"-\"), IFNULL(b.sex,\"-\") ,IFNULL(b.region, \"-\")from account as a LEFT JOIN bank as b ON a.user_id=b.user_id;");
    }

    public void updateRec() {

        pane3.setVisible(false);
        pane2.setVisible(false);
        pane1.setVisible(true);

    }

    public void deleteRec() {

        pane1.setVisible(false);
        pane2.setVisible(true);
        pane3.setVisible(false);
    }

    public void addBankRec() {

        pane1.setVisible(false);
        pane2.setVisible(false);
        pane3.setVisible(true);

    }

    public void submitBank() {

        dataMap.put("age", textAge.getText());
        dataMap.put("sex", textSex.getText());
        dataMap.put("region", textRegion.getText());
        dataMap.put("name", textName.getText());
        bm.insertRecords(dataMap);
    }

    public void submitUpdate() {
        dataMap.put("updateID", updateID.getText());
        dataMap.put("newBalance", newBalance.getText());
        bm.updateRecords(dataMap);
        System.out.println("Update Submit button pressed");

    }

    public void submitDelete() {
        dataMap.put("updateID", updateID.getText());
        dataMap.put("newBalance", newBalance.getText());
        bm.deleteRecords(dataMap);
        System.out.println("Delete Submit button pressed");

    }

}
