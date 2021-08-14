package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicLong;

import application.Main;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import models.ClientModel;

public class ClientController implements Initializable {

    static String userid;
    ClientModel cm;

    /***** TABLEVIEW intel *********************************************************************/

    @FXML
    private TableView<ClientModel> tblAccounts;
//    @FXML
//    private TableColumn<ClientModel, String> userId;
    @FXML
    private TableColumn<ClientModel, String> balance;
    @FXML
    private Label errorLog;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        userId.setCellValueFactory(new PropertyValueFactory<ClientModel, String>("userId"));
        balance.setCellValueFactory(new PropertyValueFactory<ClientModel, String>("balance"));

        // auto adjust width of columns depending on their content
        tblAccounts.setColumnResizePolicy((param) -> true);
        Platform.runLater(() -> customResize(tblAccounts));

        // set invisible initially
        tblAccounts.setVisible(false);
//        cm.setUserid();
    }

    public void customResize(TableView<?> view) {

        AtomicLong width = new AtomicLong();
        view.getColumns().forEach(col -> {
            width.addAndGet((long) col.getWidth());
        });
        double tableWidth = view.getWidth();

        if (tableWidth > width.get()) {
            view.getColumns().forEach(col -> {
                col.setPrefWidth(col.getWidth()+((tableWidth-width.get())/view.getColumns().size()));
            });
        }
    }

    public void viewAccounts() throws IOException {
        // load table data from ClientModel List
        tblAccounts.getItems().setAll(cm.getAccounts(userid));
        // set tableview to visible if not
        tblAccounts.setVisible(true);
        System.out.println(cm.getClientInfo());
    }

    /***** End TABLEVIEW intel *********************************************************************/

    public void logout() {
        // System.exit(0);
        try {
            AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("/views/LoginView.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/application/styles.css").toExternalForm());
            Main.stage.setScene(scene);
            Main.stage.setTitle("Login");
        } catch (Exception e) {
            System.out.println("Error occured while inflating view: " + e);
        }
    }

    public void createTransaction() {
        if (cm.checkBankInfo()) {
            errorLog.setText("You already have an account");
        }

        TextInputDialog dialog = new TextInputDialog("Enter dollar amount");
        dialog.setTitle("Bank Account Entry Portal");
        dialog.setHeaderText("Enter Transaction");
        dialog.setContentText("Please enter your balance:");

        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            System.out.println("Balance entry: " + result.get());
            cm.insertRecord(userid,Double.parseDouble(result.get()));
        }

        // The Java 8 way to get the response value (with lambda expression).
        result.ifPresent(balance -> System.out.println("Balance entry: " + balance));

    }

    public static void setUserid(String user_id) {
        userid = user_id;
        System.out.println("Welcome id: " + userid);
    }

    public ClientController() {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("From Customer controller");
        alert.setHeaderText("Bank Of IIT- Chicago Main Branch");
        alert.setContentText("Welcome !"); alert.showAndWait();
        cm = new ClientModel();

    }

}
