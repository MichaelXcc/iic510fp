package controllers;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.control.Label;
import models.BankModule;
import models.DBConnect;
import application.DynamicTable;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class AdminController {

    @FXML
    private Pane pane1, pane2, pane3;
    @FXML
    private TextField textID, textAge, textSex, textRegion, textName, updateID, newBalance, deleteID;
    @FXML
    private Label logError;
    BankModule bm;


    DBConnect conn = null;
    Statement stmt = null;
    String userID;
    Map<String, String> dataMap = LoginController.dataMap;


    public AdminController() {
        conn = new DBConnect();
        userID = dataMap.get("usrID");
        bm = new BankModule();
    }

    public void viewAccounts() {

        DynamicTable d = new DynamicTable();
        // call method from DynamicTable class and pass some arbitrary query string
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
        logError.setText("");
        dataMap.put("textID", textID.getText());
        dataMap.put("textAge", textAge.getText());
        dataMap.put("textSex", textSex.getText());
        dataMap.put("textRegion", textRegion.getText());
        dataMap.put("textName", textName.getText());
        if (bm.insertRecords(dataMap)) {
            logError.setText("user already exists ");
        }
        logError.setText("add succeed");

    }

    public void submitUpdate() {
        logError.setText("");
        dataMap.put("updateID", updateID.getText());
        dataMap.put("newBalance", newBalance.getText());
        if (!bm.updateRecords(dataMap)) {
            logError.setText("update error");
            return;
        }
        logError.setText("Update succeed");
        System.out.println("Update Submit button pressed");
    }

    public void submitDelete() {
        logError.setText("");
        dataMap.put("deleteID", deleteID.getText());
        if (!bm.deleteRecords(dataMap)) {
            logError.setText("delete error");
            return;
        }
        logError.setText("delete succeed");
        System.out.println("Delete Submit button pressed");

    }

}
