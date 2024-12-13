package coe528.project;
/*
 * @author 501183902, h6parmar, HARSHRAJ PARMAR
 */
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Logger;

public final class BankAccount{
    final private String Username, Password;
    private Level Level;
    private double Balance;
    private boolean AccessGranted;
   
    public BankAccount(String Username, String Password){
        this.Username = Username;
        this.Password = Password;
        this.Balance = 100.0;
        this.Level = new SilverLevel();
        System.out.println("Account Creation Sucessful for " + this.Username);
    }
    
    public BankAccount(String Username, String Password, double Balance){
        this.Username = Username;
        this.Password = Password;
        this.Balance = Balance;
        UpdateLevel();
        System.out.println("Account Creation Sucessful for " + this.Username);
    }
    
    public boolean Login(String Username, String Password){
        if(this.Username.equals(Username)){
            if(this.Password.equals(Password)){
                AccessGranted = true;
                System.out.println("Login Sucessful, welcome back " + this.Username);
                return AccessGranted;
            }
            System.out.println("Incorrect Password");
            AccessGranted = false;
            return AccessGranted;
        }
        System.out.println("Incorrect Username");
        AccessGranted = false;
        return AccessGranted;
    }
    
    public boolean Logout(){
        if(AccessGranted == true){
            AccessGranted = false;
            System.out.println("Logout Sucessful");
            return true;
        }
        System.out.println("Unable to Logout: No Login");
        return false;
    }
    
    public double getBalance(){
        return this.Balance;
    }
    
    public void updateBalanceOnFile(){
        BufferedWriter writer  = null;
        String directory = System.getProperty("user.dir"); 
        File file = new File(directory, this.Username + ".txt");
        try {
            double balance  = this.Balance;
            String User     = this.Username;
            String Pass     = this.Password;
            writer = new BufferedWriter(new FileWriter(file));
            writer.write(User + " " + Pass + " " + balance);
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(Manager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } 
    }
    
    public void UpdateLevel(){
        if(this.Balance < 10000){
            this.Level = new SilverLevel();
            
        }
        if(this.Balance >= 10000 && this.Balance < 20000){
            this.Level = new GoldLevel();
            
        }
        if(this.Balance >= 20000){
            this.Level = new PlatinumLevel();
            
        }
    }
    
    public boolean DepositMoney(double DepositAmount){
        if(AccessGranted == true){
            if(DepositAmount > 0){
            this.Balance = this.Balance + DepositAmount;
            System.out.println("Deposited: $" + DepositAmount + "\nNew Balance: $" + this.Balance);
            UpdateLevel();
            return true;
            }
            else System.out.println("Unable to Deposit: Invalid Deposit Amount");
            return false;
        }
        else return false;
    }
    
    public boolean WithdrawMoney(double WithdrawAmount){
        if(AccessGranted == true){
            if(this.Balance >= WithdrawAmount && WithdrawAmount > 0){
                this.Balance = this.Balance - WithdrawAmount;
                System.out.println("Withdrew: $" + WithdrawAmount + "\nNew Balance: $" + this.Balance);
                UpdateLevel();
                return true;
            }
            else System.out.println("Unable to Withdraw: Invalid Withdraw Amount");
            return false;
            
        }   
        else return false;
    }
    
    public boolean OnlinePurchase(double CostAmount){
        if(AccessGranted == true){
            if(this.Level.getLevel().equals("Silver") && CostAmount >= 50.0 && this.Balance > CostAmount + this.Level.getCost()){
                this.Balance = this.Balance - (CostAmount + this.Level.getCost());
                UpdateLevel();
                System.out.println("Purchase Cost: $" + (CostAmount+this.Level.getCost()));
                return true;
            }
            else if(this.Level.getLevel().equals("Gold") && CostAmount >= 50.0 && this.Balance > CostAmount + this.Level.getCost()){
                this.Balance = this.Balance - (CostAmount + this.Level.getCost());
                UpdateLevel();
                System.out.println("Purchase Cost: $" + (CostAmount+this.Level.getCost()));
                return true;
            }
            else if(this.Level.getLevel().equals("Platinum") && CostAmount >= 50.0 && this.Balance > CostAmount + this.Level.getCost()){
                this.Balance = this.Balance - (CostAmount + this.Level.getCost());
                UpdateLevel();
                System.out.println("Purchase Cost: $" + (CostAmount+this.Level.getCost()));
                return true;
            }
            else return false;
        }
        else return false;
    }
    
    public String currentLevel(){
        return this.Level.getLevel();
    }
    
    public boolean getAccessStatus(){
        return this.AccessGranted;
    }
   
}
