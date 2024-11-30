import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class AnimatedCharacter extends Actor {
    private GreenfootImage spriteSheet;
    private GreenfootImage[][] animationFrames; // 2D array to store frames by direction
    private int frameWidth = 64;
    private int frameHeight = 64;
    private int framesPerRow = 9;    // Specific number of frames per animation
    private int totalRows = 4;       // Number of directions (up, down, left, right)
    private int currentFrame = 0;
    private int animationSpeed = 5;
    private int animationCounter = 0;
    private int speed = 1;
    
    private int currentRow = 2; // Default to facing down (row 2)
    private int shootCooldown = 30;  // Cooldown time in frames (adjust as needed)
    private int currentCooldown = 0; // Current cooldown counter
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

    // Update the act() method
    public void act() {
        handleMovement();
        if (isMoving()) { 
            animate();
        }
        skill(); // Add this line to check for skill usage
        if (debugMode) {
            showDebugInfo();
        }
    }

    // Replace the skill() method
    public void skill() {
        boolean isSpacePressed = Greenfoot.isKeyDown("space");
        
        // Decrease cooldown counter if it's active
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
        boolean isMoving = false; // Track whether the character is moving
        
        if (Greenfoot.isKeyDown("left")) {
            setLocation(getX() - speed, getY());
            currentRow = 1; // Left animation row
            isMoving = true;
        }
        if (Greenfoot.isKeyDown("right")) {
            setLocation(getX() + speed, getY());
            currentRow = 3; // Right animation row
            isMoving = true;
        }
        if (Greenfoot.isKeyDown("up")) {
            setLocation(getX(), getY() - speed);
            currentRow = 0; // Up animation row
            isMoving = true;
        }
        if (Greenfoot.isKeyDown("down")) {
            setLocation(getX(), getY() + speed);
            currentRow = 2; // Down animation row
            isMoving = true;
        }
        if (Greenfoot.isKeyDown("shift")) {
            speed = 3; 
        } else {
            speed = 1;  
        }
        
        // If no key is pressed, reset to idle frame (first frame)
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
            // Update frame index, wrapping around to 1 (skip idle frame 0 except when stopped)
            currentFrame = (currentFrame + 1) % framesPerRow;
            
            // Set the current frame from our 2D array
            setImage(animationFrames[currentRow][currentFrame]);
        }
    }

    private void shoot() {
        // Create a new projectile
        skill1 projectile = new skill1();

        // Set the projectile's direction based on the character's current row (facing direction)
        if (currentRow == 0) { // Up
            projectile.setRotation(270); // Point upwards
        } else if (currentRow == 1) { // Left
            projectile.setRotation(180); // Point left
        } else if (currentRow == 2) { // Down
            projectile.setRotation(90);  // Point down
        } else if (currentRow == 3) { // Right
            projectile.setRotation(0);   // Point right
        }

        // Add the projectile to the world at the character's current position
        getWorld().addObject(projectile, getX(), getY());
    }

    private void showDebugInfo() {
        World world = getWorld();
        if (world != null) {
            // Clear previous debug text
            world.showText("", 100, 20); // Clear previous text
            
            // Create debug string
            String debug = String.format(
                "Cooldown: %d | Row: %d | Speed: %d | Space: %b", 
                currentCooldown, 
                currentRow, 
                speed, 
                wasSpacePressed
            );
            
            // Display debug info at top of screen
            world.showText(debug, 200, 20);
        }
    }
}
