package controllers;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import models.UsersModule;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;


public class LoginController {

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Label nameError, pwdError;

    private final UsersModule model;

    public static Map<String, String> dataMap = new HashMap<String, String>();

    public LoginController() {
        model = new UsersModule();
    }

    public void signin() throws NoSuchAlgorithmException {
        nameError.setText("");
        String username = this.txtUsername.getText();
        String password = this.txtPassword.getText();
        if (!model.register(username, password)) {
            nameError.setText("User name existing");
        }
        dataMap.put("usrID", model.getId());
    }

    public void login() {
        nameError.setText("");
        String username = this.txtUsername.getText();
        String password = this.txtPassword.getText();

        // Validations
        if (username == null || username.trim().equals("")) {
            nameError.setText("Username Cannot be empty or spaces");
            return;
        }
        if (password == null || password.trim().equals("")) {
            pwdError.setText("Password Cannot be empty or spaces");
            return;
        }
        if (username == null || username.trim().equals("") && (password == null || password.trim().equals(""))) {
            nameError.setText("User name / Password Cannot be empty or spaces");
            pwdError.setText("User name / Password Cannot be empty or spaces");
            return;
        }
//        authentication check
        checkCredentials(username, password);
    }

    public void checkCredentials(String username, String password) {
        Boolean isValid = model.getCredentials(username, password);

        if (!isValid) {
            nameError.setText("User does not exist or password error");
            return;
        }
        dataMap.put("usrID", model.getId());
        System.out.println("??????id???" + dataMap.get("usrID"));
        try {
            AnchorPane root;
            if (model.isAdmin() && isValid) {
                // If user is admin, inflate admin view
                root = (AnchorPane) FXMLLoader.load(getClass().getResource("/views/AdminView.fxml"));
                Main.stage.setTitle("Admin View");
            } else {
                // If user is customer, inflate customer view
                root = (AnchorPane) FXMLLoader.load(getClass().getResource("/views/ClientView.fxml"));
                // ***Set user ID acquired from db****
                Main.stage.setTitle("Client View");
            }
            Scene scene = new Scene(root);
            Main.stage.setScene(scene);
        } catch (Exception e) {
            System.out.println("Error occured while inflating view: " + e);
        }

    }
}