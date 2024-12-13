package coe528.project;
/*
 * @author 501183902, h6parmar, HARSHRAJ PARMAR
 */
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class BankApplication extends Application {

    private Manager Manager;
    private Customer Customer;

    @Override
    public void start(Stage primaryStage) {
        Manager = new Manager();
        
        System.out.println("to make sure in proper directory: " + System.getProperty("user.dir"));
        Scene loginScene = createLoginScene(primaryStage);
        
        primaryStage.setScene(loginScene);
        primaryStage.setTitle("Banking Application");
        primaryStage.show();
        
        primaryStage.setOnCloseRequest(event -> {
        Platform.exit();
        });
    }

    private Scene createLoginScene(Stage primaryStage) {
        GridPane loginPane = new GridPane();
        
        loginPane.setAlignment(Pos.CENTER); loginPane.setHgap(10); loginPane.setVgap(10); loginPane.setPadding(new Insets(20));

        Label usernameLabel = new Label("Username:"); TextField usernameField = new TextField();
        Label passwordLabel = new Label("Password:"); PasswordField passwordField = new PasswordField();
        
        Button loginButton = new Button("Login");

        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            String role;
            
            String directoryPath = System.getProperty("user.dir"); 
            File fileToLogin = new File(directoryPath,username + ".txt");
            
            boolean added = false;
            
            if(fileToLogin.exists()){
                try{
                BufferedReader reader = new BufferedReader(new FileReader(fileToLogin));
                   
                    String fileInfo = reader.readLine();
                    System.out.println(fileInfo);
                    String info[] = fileInfo.split("\\s+");
                    username = info[0];
                    double balance = Double.parseDouble(info[2]);
                    if(password.equals(info[1])){
                        Manager.AddCustomer2(username, password, balance);
                        System.out.println("added: " + username);
                        Manager.getCustomer(username).Login(username, password);
                        
                        if(Manager.getCustomer(username).isLoggedIn() == true){
                            this.Customer = Manager.getCustomer(username);
                            primaryStage.setScene(createCustomerDashboardScene(primaryStage, username));
                            added = true;
                        }
                    }
                    else System.out.println("invalid");
                }
                catch(Exception ecx){
                    
                }
            }
            else if(username.equals("admin")){
                role = "Manager";
                Manager.Login(username, password);
                if(Manager.isLoggedIn() == true && Manager.getRole().equalsIgnoreCase(role)){
                primaryStage.setScene(createManagerDashboardScene(primaryStage));
                }
                else showAlert(Alert.AlertType.ERROR, "Login Failed", "Username, password or role is invalid.");
            }
            else if(added == false){
                role = "Customer";
                Manager.getCustomer(username).Login(username, password);
                if(Manager.getCustomer(username).isLoggedIn() == true && Manager.getCustomer(username).getRole().equalsIgnoreCase(role)){
                this.Customer = Manager.getCustomer(username);
                primaryStage.setScene(createCustomerDashboardScene(primaryStage, username));
                }
                else showAlert(Alert.AlertType.ERROR, "Login Failed", "Username,password or role is invalid.");
            } 
        });
        
        loginPane.addRow(0, usernameLabel, usernameField);
        loginPane.addRow(1, passwordLabel, passwordField);
        loginPane.addRow(2, loginButton);

        return new Scene(loginPane, 500, 500, Color.LIGHTGRAY);
    }

//MANAGER DASHBOARD
    private Scene createManagerDashboardScene(Stage primaryStage) {
        BorderPane dashboardPane    = new BorderPane();
        
        TextField ManagerName       = new TextField("Welcome back Manager");
        ManagerName.setEditable(false);
        ManagerName.setAlignment(Pos.CENTER);
        
        Button addCustomerButton    = new Button("Add Customer"); 
        Button deleteCustomerButton = new Button("Delete Customer");
        Button logoutButton         = new Button("Logout");

    //ADDCUSTOMER 
        TextField addUsernameField     = new TextField();
        addUsernameField.setPromptText("Enter Customer's Username: ");
        TextField addPasswordField     = new TextField();
        addPasswordField.setPromptText("Enter Customer's password: ");
        Button addConfirmButton        = new Button("Confirm Addition");
        Button backButton1             = new Button("Back");
    
        //AddCustomerBUTTON
        addCustomerButton.setOnAction(e -> {
            VBox addCustomerPane = new VBox(10);
            addCustomerPane.setAlignment(Pos.CENTER);
            addCustomerPane.getChildren().addAll(addUsernameField, addPasswordField, addConfirmButton, backButton1);
            dashboardPane.setCenter(addCustomerPane);
        });
        
        //AddConfirmBUTTON
        addConfirmButton.setOnAction(e -> {
            String username = addUsernameField.getText();
            String password = addPasswordField.getText();
            if(password == null || password.isEmpty()){
                showAlert(Alert.AlertType.INFORMATION, "Add Customer", "Invalid password");
            }
            else if(Manager.AddCustomer(username, password) == true){
                 showAlert(Alert.AlertType.INFORMATION, "Add Customer", "Customer Added: " + Manager.getCustomer(username).getUsername());
            }
            else showAlert(Alert.AlertType.INFORMATION, "Add Customer", "Customer not Added: USERNAME EXISTS");
            
            addUsernameField.clear();
            addPasswordField.clear();
        });
        
        //BackBUTTON1
        backButton1.setOnAction(e -> {
            primaryStage.setScene(createManagerDashboardScene(primaryStage));
        });
        
    //DELETECUSTOMER
        TextField deleteUsernameField      = new TextField();
        deleteUsernameField.setPromptText("Enter Customer's Username: ");
        Button deleteConfirmButton         = new Button("Confirm Removal");
        Button backButton2                 = new Button("Back");
        
        //DeleteCustomerBUTTON
        deleteCustomerButton.setOnAction(e -> {
            VBox deleteCustomerPane = new VBox(10);
            deleteCustomerPane.setAlignment(Pos.CENTER);
            deleteCustomerPane.getChildren().addAll(deleteUsernameField, deleteConfirmButton, backButton2);
            dashboardPane.setCenter(deleteCustomerPane);
        });
        
        //deleteConfirmBUTTON
        deleteConfirmButton.setOnAction(e -> {
            String username = deleteUsernameField.getText();
            if(Manager.DeleteCustomer(username) == true){
                 showAlert(Alert.AlertType.INFORMATION, "Delete Customer", "Customer Deleted: " + username);
            }
            else showAlert(Alert.AlertType.INFORMATION, "Delete Customer", "Customer not Deleted: USERNAME DOES NOT EXIST");
            deleteUsernameField.clear();
        });
        
        //backBUTTON2
        backButton2.setOnAction(e -> {
            primaryStage.setScene(createManagerDashboardScene(primaryStage));
        });

    //LOGOUT
        logoutButton.setOnAction(e -> {
            Manager.Logout();
            primaryStage.setScene(createLoginScene(primaryStage));
        });

        VBox buttons = new VBox(10);
        buttons.setAlignment(Pos.CENTER);
        buttons.getChildren().addAll(ManagerName, addCustomerButton, deleteCustomerButton, logoutButton);

        dashboardPane.setCenter(buttons);
        return new Scene(dashboardPane, 500, 500, Color.LIGHTBLUE);
    }

//CUSTOMER DASHBOARD
    private Scene createCustomerDashboardScene(Stage primaryStage, String username) {
        BorderPane dashboardPane = new BorderPane();
        
        TextField CustomerName       = new TextField("Welcome back, " + Manager.getCustomer(username).getUsername() + " - Logout to save transactions.");
        CustomerName.setEditable(false);
        CustomerName.setAlignment(Pos.CENTER);
        
        Button getLevelButton        = new Button("current Level");
        Button balanceButton         = new Button("current Balance");
        Button withdrawMoneyButton   = new Button("Withdraw");
        Button depositMoneyButton    = new Button("Deposit");
        Button purchaseMoneyButton   = new Button("Purchase");
        Button logoutButton          = new Button("Logout");
    //LEVEL   
        getLevelButton.setOnAction(e -> {
            showAlert(Alert.AlertType.INFORMATION, "Current Level", "Level: " + Manager.getCustomer(username).currentLevel());
        });
    //BALANCE    
        balanceButton.setOnAction(e -> {
            showAlert(Alert.AlertType.INFORMATION, "BankAccount Balance", "Balance: $" + Manager.getCustomer(username).Balance());
        });
    //WITHDRAW 
        TextField withdrawMoneyField         = new TextField();
        withdrawMoneyField.setPromptText("Enter amount to withdraw: $");
        Button withdrawConfirmButton         = new Button("Withdraw");
        Button withdrawbackButton1           = new Button("Back");
        
        withdrawMoneyButton.setOnAction(e -> {
            VBox withdrawMoneyPane = new VBox(10);
            withdrawMoneyPane.setAlignment(Pos.CENTER);
            withdrawMoneyPane.getChildren().addAll(withdrawMoneyField, withdrawConfirmButton, withdrawbackButton1);
            dashboardPane.setCenter(withdrawMoneyPane);
           
        });
        
        withdrawConfirmButton.setOnAction(e -> {
            double Amount;
            try{
                Amount = Double.parseDouble(withdrawMoneyField.getText());
                if(Manager.getCustomer(username).Withdraw(Amount) == true){
                    showAlert(Alert.AlertType.INFORMATION, "Withdraw", "Balance $: " + Manager.getCustomer(username).Balance());
                }
                else showAlert(Alert.AlertType.INFORMATION, "Withdraw", "Invalid amount");
            }
            catch(NumberFormatException ex){
                showAlert(Alert.AlertType.INFORMATION, "Withdraw", "Enter Number!");
            }
            withdrawMoneyField.clear();  
        });
        
        withdrawbackButton1.setOnAction(e -> {
            primaryStage.setScene(createCustomerDashboardScene(primaryStage, username));
        });
    //DEPOSIT 
        TextField depositMoneyField                 = new TextField();
        depositMoneyField.setPromptText("Enter amount to deposit: $");
        Button depositConfirmButton                 = new Button("Deposit");
        Button depositbackButton1                   = new Button("Back");
        
        depositMoneyButton.setOnAction(e -> {
            VBox depositMoneyPane = new VBox(10);
            depositMoneyPane.setAlignment(Pos.CENTER);
            depositMoneyPane.getChildren().addAll(depositMoneyField, depositConfirmButton, depositbackButton1);
            dashboardPane.setCenter(depositMoneyPane);
        });
        
        depositConfirmButton.setOnAction(e -> {
            double Amount;
            try{
                Amount = Double.parseDouble(depositMoneyField.getText());
                if(Manager.getCustomer(username).Deposit(Amount) == true){
                    showAlert(Alert.AlertType.INFORMATION, "Deposit", "Balance $: " + Manager.getCustomer(username).Balance());
                }
                else showAlert(Alert.AlertType.INFORMATION, "Deposit", "Invalid amount");
            }
            catch(NumberFormatException ex){
                showAlert(Alert.AlertType.INFORMATION, "Deposit", "Enter Number!");
            }
            depositMoneyField.clear();  
        });
        
        depositbackButton1.setOnAction(e -> {
            primaryStage.setScene(createCustomerDashboardScene(primaryStage, username));
        });
        
    //PURCHASE
        TextField purchaseMoneyField                 = new TextField();
        purchaseMoneyField.setPromptText("Enter amount of purchase: $");
        Button purchaseConfirmButton                 = new Button("Purchase");
        Button purchasebackButton1                   = new Button("Back");
        
        purchaseMoneyButton.setOnAction(e -> {
            VBox purchaseMoneyPane = new VBox(10);
            purchaseMoneyPane.setAlignment(Pos.CENTER);
            purchaseMoneyPane.getChildren().addAll(purchaseMoneyField, purchaseConfirmButton, purchasebackButton1);
            dashboardPane.setCenter(purchaseMoneyPane);
        });
        
        purchaseConfirmButton.setOnAction(e -> {
            double Amount;
            try{
                Amount = Double.parseDouble(purchaseMoneyField.getText());
              if(Amount < 50){
                    showAlert(Alert.AlertType.INFORMATION, "Purchase", "Purchase must cost more than 50$");
              }
              
                if(Manager.getCustomer(username).Purchase(Amount) == true){
                    showAlert(Alert.AlertType.INFORMATION, "Purchase", "Balance $: " + Manager.getCustomer(username).Balance());
                }
                else showAlert(Alert.AlertType.INFORMATION, "Purchase", "Invalid amount");
            }
            catch(NumberFormatException ex){
                showAlert(Alert.AlertType.INFORMATION, "Purchase", "Enter Number!");
            }
            purchaseMoneyField.clear();  
        });
        
        purchasebackButton1.setOnAction(e -> {
            primaryStage.setScene(createCustomerDashboardScene(primaryStage, username));
        });
        
    //LOGOUT
        logoutButton.setOnAction(e -> {
            Manager.getCustomer(username).Logout();
            primaryStage.setScene(createLoginScene(primaryStage));
        });

        VBox buttons = new VBox(10);
        buttons.setAlignment(Pos.CENTER);
        buttons.getChildren().addAll(CustomerName, getLevelButton, balanceButton,withdrawMoneyButton, depositMoneyButton, purchaseMoneyButton, logoutButton);

        dashboardPane.setCenter(buttons);
        return new Scene(dashboardPane, 500, 500, Color.LIGHTBLUE);
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
        
    }
}