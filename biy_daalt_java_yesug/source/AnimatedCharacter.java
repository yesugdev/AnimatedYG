import greenfoot.*;  
public class AnimatedCharacter extends Actor {
    private GreenfootImage spriteSheet;
    private GreenfootImage[][] animationFrames; 
    private int frameWidth = 64;
    private int frameHeight = 64;
    private int framesPerRow = 9;    
    private int totalRows = 4;      
    private int currentFrame = 0;
    private int animationSpeed = 5;
    private int animationCounter = 0;
    private int speed = 1;
    
    private int currentRow = 2; 
    private int shootCooldown = 30;  
    private int currentCooldown = 0; 
    private boolean wasSpacePressed = false;
    private boolean debugMode = true;

    public AnimatedCharacter() {
        spriteSheet = new GreenfootImage("character.png");
        animationFrames = new GreenfootImage[totalRows][framesPerRow];
        
        // Extract frames for each direction
        for (int row = 0; row < totalRows; row++) {
            for (int col = 0; col < framesPerRow; col++) {
                animationFrames[row][col] = new GreenfootImage(frameWidth, frameHeight);
                // Offset calculation based on actual sprite sheet layout
                int yOffset = (row + 8) * frameHeight; // Starting from row 8
                animationFrames[row][col].drawImage(spriteSheet, 
                    -col * frameWidth,  // X offset
                    -yOffset);          // Y offset
            }
        }
        
        setImage(animationFrames[currentRow][0]);
    }


    public void act() {
        handleMovement();
        if (isMoving()) { 
            animate();
        }
        skill(); 
        if (debugMode) {
            showDebugInfo();
        }
    }

    public void skill() {
        boolean isSpacePressed = Greenfoot.isKeyDown("q");
        
        if (currentCooldown > 0) {
            currentCooldown--;
        }
        // Only shoot if:
        // 1. Space is pressed (and wasn't pressed last frame)
        // 2. Cooldown is complete
        if (isSpacePressed && !wasSpacePressed && currentCooldown <= 0) {
            shoot();
            currentCooldown = shootCooldown; 
            // Reset cooldown
        }
        // Update previous space state
        wasSpacePressed = isSpacePressed;
    }
    
    private void handleMovement() {
        boolean isMoving = false; 
        
        if (Greenfoot.isKeyDown("left")) {
            setLocation(getX() - speed, getY());
            currentRow = 1;
            isMoving = true;
        }
        if (Greenfoot.isKeyDown("right")) {
            setLocation(getX() + speed, getY());
            currentRow = 3; 
            isMoving = true;
        }
        if (Greenfoot.isKeyDown("up")) {
            setLocation(getX(), getY() - speed);
            currentRow = 0; 
            isMoving = true;
        }
        if (Greenfoot.isKeyDown("down")) {
            setLocation(getX(), getY() + speed);
            currentRow = 2; 
            isMoving = true;
        }
        if (Greenfoot.isKeyDown("shift")) {
            speed = 3; 
        } else {
            speed = 1;  
        }
        
        
        if (!isMoving) {
            currentFrame = 0;
            setImage(animationFrames[currentRow][currentFrame]);
        }
    }

    private boolean isMoving() {
        return Greenfoot.isKeyDown("left") || Greenfoot.isKeyDown("right") || Greenfoot.isKeyDown("up") || Greenfoot.isKeyDown("down");
    }

    private void animate() {
        animationCounter++;
        
        if (animationCounter % animationSpeed == 0) {
            
            currentFrame = (currentFrame + 1) % framesPerRow;
        
            setImage(animationFrames[currentRow][currentFrame]);
        }
    }

    private void shoot() {
    
        skill1 projectile = new skill1();

       
        if (currentRow == 0) { // Up
            projectile.setRotation(270); // Point upwards
        } else if (currentRow == 1) { // Left
            projectile.setRotation(180); // Point left
        } else if (currentRow == 2) { // Down
            projectile.setRotation(90);  // Point down
        } else if (currentRow == 3) { // Right
            projectile.setRotation(0);   // Point right
        }

        getWorld().addObject(projectile, getX(), getY());
    }

    private void showDebugInfo() {
        World world = getWorld();
        if (world != null) {
            // Clear previous debug text
            world.showText("", 100, 20);
            
            String debug = String.format(
                "Cooldown: %d | Row: %d | Speed: %d | Space: %b", 
                currentCooldown, 
                currentRow, 
                speed, 
                wasSpacePressed
            );
        
            world.showText(debug, 200, 20);
        }
    }
}


