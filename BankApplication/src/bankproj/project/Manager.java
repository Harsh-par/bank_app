package coe528.project;
/*
 * @author 501183902, h6parmar, HARSHRAJ PARMAR
 */
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Manager{
    final private String Username, Password;
    final private ArrayList<Customer> ClientList;
    private boolean AccessGranted;
    final private String role = "Manager";
    final private String dataFile = "customerFile";

    
    public Manager(){
        this.Username = "admin";
        this.Password = "admin";
        this.AccessGranted = false;
        ClientList = new ArrayList<>();
    }
    
    public void Login(String Username, String Password){
        if(this.Username.equals(Username) && this.Password.equals(Password)){
            this.AccessGranted = true;
            System.out.println("Login Sucessful: Welcome back admin");
        }
        else{ 
        this.AccessGranted = false;
        System.out.println("Login Unsucessful: Invalid Username or Password");
        }        
    }
    public boolean isLoggedIn(){
        return this.AccessGranted;
    }
    
    public void Logout(){
        this.AccessGranted = false;
    }
    
    public boolean AddCustomer(String Username, String Password){
        if(AccessGranted == true){
            String directory = System.getProperty("user.dir"); 
            File checkFile = new File(directory,Username + ".txt");
            if(checkFile.exists()){
                return false;
            }
            for(int i=0; i<this.ClientList.size(); i++){
                if(this.ClientList.get(i).getUsername().equals(Username)){
                    System.out.println("AddCustomer unsucessful: Username exists");
                    return false;
                }
            }
            Customer Customer = new Customer(Username, Password);
            this.ClientList.add(Customer);
            addFile(Customer);
            System.out.println("AddCustomer sucessful Added: " + Customer.getUsername());
            return true;
        }
        else return false;
    }
    
    public boolean AddCustomer2(String Username, String Password, double Balance){
        Customer Customer = new Customer(Username, Password, Balance);
        this.ClientList.add(Customer);
        System.out.println("AddCustomer sucessful Added: " + Customer.getUsername());
        return true;
    }
    
    public void addFile(Customer Customer){
        BufferedWriter writer  = null;
        String directory = System.getProperty("user.dir"); 
        File file = new File(directory, Customer.getUsername() + ".txt");
        try {
            double balance  = Customer.Balance();
            String User     = Customer.getUsername();
            String Pass     = Customer.getPassword();
            writer = new BufferedWriter(new FileWriter(file));
            writer.write(User + " " + Pass + " " + balance);
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(Manager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                writer.close();
            } catch (IOException ex) {
                Logger.getLogger(Manager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public boolean DeleteCustomer(String Username){
        if(AccessGranted == true){
            if(deleteFile(Username)){
                return true;
            }
            for(int i=0; i<this.ClientList.size(); i++){
                if(this.ClientList.get(i).getUsername().equals(Username)){
                    this.ClientList.remove(i);
                    System.out.println("Removed: " + Username);
                    return true;
                }  
            }
        }
        else if(AccessGranted == false){
            return false;
        }
        return false;
    }
    
    public boolean deleteFile(String Username){
        String directoryPath = System.getProperty("user.dir"); 
        File fileToDelete = new File(directoryPath, Username + ".txt");
        try{
            if(fileToDelete.delete()){
              System.out.println("deleted user");  
              return true;
            } else{
                throw new Exception();
            }
        } catch (Exception e){
            System.out.println("Error");
        }
        return false;
    }
  
    public void DisplayCustomers(){
        if(AccessGranted == true){
            String Display = "";
            for(int i=0; i<this.ClientList.size(); i++){
                Display = Display + this.ClientList.get(i).getUsername() + " ";
            }
            System.out.println(Display);
        }
        else System.out.println("Login Required for access");
    }
    
    public Customer getCustomer (String Username){
        for(int i=0; i<this.ClientList.size(); i++){
            if(this.ClientList.get(i).getUsername().equals(Username)){
                return this.ClientList.get(i);
                }
        }
        System.out.println("Invalid Username");
        return null;
    }
    
    public String getRole(){
        return this.role;
    }
  
}
