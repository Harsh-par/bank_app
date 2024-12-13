package coe528.project;
/*
 * @author 501183902, h6parmar, HARSHRAJ PARMAR
 */
public class GoldLevel extends Level{
    String Level = "Gold";
    int Cost = 10;
   
   @Override
   public int getCost(){
   return this.Cost;
   }
   
   @Override
   public String getLevel(){
   return this.Level;
   }
   
}
