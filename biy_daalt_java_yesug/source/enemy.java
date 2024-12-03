import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Enemy class
 */
public class enemy extends Actor
{
    private int speed; // Enemy-ийн тогтмол хурд

    public enemy() {
        speed = 1; // Хурд тогтмол 2
    }

    public void act()
    {
        moveTowardsCharacter();
        checkOutOfBounds();
    }

    /**
     * Санамсаргүй байрлалд мөр, баганын дагуух тэгш хэмтэй Enemy үүсгэх
     */
    public static void spawnEnemies(World world, int count) {
        for (int i = 0; i < count; i++) {
            int edge = Greenfoot.getRandomNumber(4); // 0: зүүн, 1: баруун, 2: дээд, 3: доод
            int x1 = 0, y1 = 0, x2 = 0, y2 = 0;

            if (edge == 0) { // Зүүн талын багана
                x1 = 0;
                y1 = Greenfoot.getRandomNumber(world.getHeight());
                x2 = world.getWidth() - 1;
                y2 = y1; // Тэгш хэм хадгалах
            } else if (edge == 1) { // Баруун талын багана
                x1 = world.getWidth() - 1;
                y1 = Greenfoot.getRandomNumber(world.getHeight());
                x2 = 0;
                y2 = y1; // Тэгш хэм хадгалах
            } else if (edge == 2) { // Дээд мөр
                x1 = Greenfoot.getRandomNumber(world.getWidth());
                y1 = 0;
                x2 = x1; // Тэгш хэм хадгалах
                y2 = world.getHeight() - 1;
            } else { // Доод мөр
                x1 = Greenfoot.getRandomNumber(world.getWidth());
                y1 = world.getHeight() - 1;
                x2 = x1; // Тэгш хэм хадгалах
                y2 = 0;
            }

            // Тэгш хэмтэй Enemy-үүдийг дэлгэцэнд нэмэх
            world.addObject(new enemy(), x1, y1);
            world.addObject(new enemy(), x2, y2);
        }
    }

    /**
     * AnimatedCharacter объект руу хөдөлгөх
     */
    private void moveTowardsCharacter() {
        if (!getWorld().getObjects(AnimatedCharacter.class).isEmpty()) {
            AnimatedCharacter target = (AnimatedCharacter) getWorld().getObjects(AnimatedCharacter.class).get(0);

            if (target != null) {
                turnTowards(target.getX(), target.getY());
                move(speed);
            }
        }
    }

    /**
     * Дэлгэцээс гарвал устгах
     */
    private void checkOutOfBounds() {
        if (isAtEdge()) {
            getWorld().removeObject(this);
        }
    }
}
