package coe528.project;
/*
 * @author 501183902, h6parmar, HARSHRAJ PARMAR
 */

//OVERVIEW          Customers are mutable, Each Customer has a Username and Password String used to access a BankAccount, 
//                  and the BankAccount is used to keep track of all Withdraws, Deposits and Purchases as well as Balance.

//ABSTRACTION       A "Customer" is the abstract representation of a user of a Bank Account, Each customer can Login, Logout from their BankAccount
//                  Once logged in they can Withdraw, Deposit and Purchase with their Bank Account.

//REP INVARIANT     Username, Password, BankAccount and Role cannot be NULL, or EMPTY. Role must be "Customer"
//

public class Customer{
    final private BankAccount BankAccount;
    final private String Username, Password;
    final private String role = "Customer";
    
    //EFFECTS:  Initializes Customer's Username and Password and creates a BankAccount with Username and Password
    //MODIFIES: Username, Password and BankAccount are Initialized
    //REQUIRES: Username and Password can not be null or empty
    public Customer(String Username, String Password){
            this.Username = Username;
            this.Password = Password;
            BankAccount = new BankAccount(Username, Password);
    }
    
    //EFFECTS:  Initializes Customer's Username and Password and creates a BankAccount with Username and Password balance
    //MODIFIES: Username, Password, balance and BankAccount are Initialized
    //REQUIRES: Username and Password and balance can not be null or empty
    public Customer(String Username, String Password, double Balance){
        this.Username = Username;
        this.Password = Password;
        BankAccount = new BankAccount(Username, Password, Balance);
    }
    
    //EFFECTS:  Logs into BankAccount, if correct Username and Password
    //MODIFIES: Boolean accessStatus = true
    public void Login(String Username, String Password){
        if(this.Username.equals(Username) && this.Password.equals(Password)){
            this.BankAccount.Login(Username, Password);
        }
    }
    
    //EFFECTS:  Logs out of BankAccount
    //MODIFIES: Boolean accessStatus = false
    public void Logout(){
        this.BankAccount.Logout();
        this.BankAccount.updateBalanceOnFile();
    }
    
    //EFFECTS:  Updates Customers balance after deposit, returns true if sucessful, else false
    //MODIFIES: Changes Balance of BankAccount
    //REQUIRES: Amount must be greater than 0
    public boolean Deposit(double Amount){
        return this.BankAccount.DepositMoney(Amount);
    }
    
    //EFFECTS:  Updates Customers balance after withdraw, returns true if sucessful, else false
    //MODIFIES: Changes Balance of BankAccount
    //REQUIRES: Amount must be less than or equal to currentBalance and cannot be amount less than 0
    public boolean Withdraw(double Amount){
        return this.BankAccount.WithdrawMoney(Amount);
    }
    
    //EFFECTS:  Updates Customers balance after purchase, returns true if sucessful, else false
    //MODIFIES: Changes Balance of BankAccount
    //REQUIRES: Amount must be greater than 50, but also must have enough Balance to make purchase
    public boolean Purchase(double Amount){
        return this.BankAccount.OnlinePurchase(Amount);
    }
    
    //EFFECTS: Returns String Username of Customer only Manager can access//
    public String getUsername(){
        return this.Username;
    }
    
    //EFFECTS: Returns String Password of Customer only Manager can access//
    public String getPassword(){
        return this.Password;
    }
    
    //EFFECTS: Returns double Balance of BankAccount//
    public double Balance(){
        return BankAccount.getBalance();
    }
    
    //EFFECTS: Returns true if logged in, else false
    public boolean isLoggedIn(){
        return this.BankAccount.getAccessStatus();
    }
    
    //EFFECTS: Returns String Role of Customer//
    public String getRole(){
        return this.role;
    }
    
    //EFFECTS: Return current Level as a String//
    public String currentLevel(){
        return this.BankAccount.currentLevel();
    }
    
    //EFFECTS: Checks if abstraction function holds for customer class//
    @Override
    public String toString(){
        return "Username: " + Username + " Password: " + Password + " Role: " + role;
    }
    
    //EFFECTS: Checks if rep invariant holds for Customer class returns true if it holds, else false//
    public boolean repOk(){
        if(Username != null && !Username.isEmpty() && Password != null && !Password.isEmpty() && BankAccount != null && role.equals("Customer")){
            return true;
        }
        return false;
    }
}