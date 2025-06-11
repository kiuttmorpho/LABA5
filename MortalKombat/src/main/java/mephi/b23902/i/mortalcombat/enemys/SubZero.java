/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.b23902.i.mortalcombat.enemys;

import mephi.b23902.i.mortalcombat.player.Player;
import javax.swing.ImageIcon;

public class SubZero extends Player{
    
    public SubZero(int level, int health, int damage , int attack){
        super (level, health, damage, attack);
        picture = new ImageIcon(this.getClass().getResource("/SubZero.png"));
        
    }
    
    @Override
    public String getName(){
        return "Sub-Zero";
    }
    
    @Override
     public Boolean isWizard() {
         return true;
     }
}
