import greenfoot.*;

public class MyWorld extends World
{
    public MyWorld()
    {    
        super(600, 400, 1); 
        addObject(new AnimatedCharacter(), getWidth() / 2, getHeight() / 2); // Голд тоглогч
        
        // 10 Enemy үүсгэх
        enemy.spawnEnemies(this, 1);
    }

    public void act() {
        // Дэлхийн тоглолтын нэмэлт логик энд байж болно
    }
}
