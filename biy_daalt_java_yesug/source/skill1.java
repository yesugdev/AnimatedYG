import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class skill1 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class skill1 extends Actor
{
    /**
     * Act - do whatever the skill1 wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private int speed = 5;
    public void act()
    {
       moveProjectile();
    }
    private void moveProjectile() {
        // Move the projectile in the direction it is facing
        move(speed);
        
        // If the projectile goes out of the world, remove it
        if (isAtEdge()) {
            getWorld().removeObject(this);
        }
    }
}
