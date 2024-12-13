package coe528.project;
/*
 * @author 501183902, h6parmar, HARSHRAJ PARMAR
 */
public class PlatinumLevel extends Level{
    String Level = "Platinum";
    int Cost = 0;
   
   @Override
   public int getCost(){
   return this.Cost;
   }
   
   @Override
   public String getLevel(){
   return this.Level;
   }
   
}
