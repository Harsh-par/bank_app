package coe528.project;
/*
 * @author 501183902, h6parmar, HARSHRAJ PARMAR
 */
public class SilverLevel extends Level{
    String Level = "Silver";
    int Cost = 20;
   
   @Override
   public int getCost(){
   return this.Cost;
   }
   
   @Override
   public String getLevel(){
   return this.Level;
   }
   
}
