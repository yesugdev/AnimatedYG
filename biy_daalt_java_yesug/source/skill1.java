import greenfoot.*;

public class skill1 extends Actor {
    private int speed = 5;

    public void act() {
        // First check if we're at the edge
        if (isAtEdge() && getWorld() != null) {
            getWorld().removeObject(this);
            return; // Exit early if removed
        }
        
        // Then handle movement and collision
        move(speed);
        checkCollisionWithEnemy();
    }

    private void checkCollisionWithEnemy() {
        if (getWorld() == null) return;
        
        Actor enemy1 = getOneIntersectingObject(enemy.class);
        if (enemy1 != null) {
            getWorld().removeObject(enemy1);
            getWorld().removeObject(this);
        }
    }
}