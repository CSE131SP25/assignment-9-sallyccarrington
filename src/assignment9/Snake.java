package assignment9;

import java.util.LinkedList;

public class Snake {

    private static final double SEGMENT_SIZE = 0.02;
    private static final double MOVEMENT_SIZE = SEGMENT_SIZE * 1.5;
    private LinkedList<BodySegment> segments;
    private double deltaX;
    private double deltaY;
    private int score;

    /**
     * makes the initial body segment and has it start at center 
     * while setting the score = 0
     */
    public Snake() {
        //LINKED LIST bc segments have an order to them
        segments = new LinkedList<BodySegment>();
        BodySegment snakePiece = new BodySegment(0.5, 0.5, SEGMENT_SIZE);
        segments.add(snakePiece);
        deltaX = 0;
        deltaY = 0;
        score = 0;
    }

    public void changeDirection(int direction) {
        if (direction == 1) { // up
            deltaY = MOVEMENT_SIZE;
            deltaX = 0;
        } else if (direction == 2) { // down
            deltaY = -MOVEMENT_SIZE;
            deltaX = 0;
        } else if (direction == 3) { // left
            deltaY = 0;
            deltaX = -MOVEMENT_SIZE;
        } else if (direction == 4) { // right
            deltaY = 0;
            deltaX = MOVEMENT_SIZE;
        }
    }

    /**
     * Moves the snake by updating the position of each of the segments
     * based on the current direction of travel
     */
    public void move() {
        // finds the old pieces coordinates then moves them the given deltaX/Y
        BodySegment newPiece = new BodySegment(segments.get(0).getX() + this.deltaX,
                                               segments.get(0).getY() + this.deltaY,
                                               SEGMENT_SIZE);
        // adds a new piece to the first place in the list
        segments.addFirst(newPiece);
        // removes the last segment to make it appear like the snake moved
        segments.remove(segments.get(segments.size() - 1));
    }

    /**
     * Draws the snake by drawing each segment
     */
    public void draw() {
        for (int i = 0; i < segments.size(); i++) {
            // here it calls the body segment code and iterates 
            // through the list to draw it
            segments.get(i).draw();
        }
    }

    /**
     * The snake attempts to eat the given food, growing if it does so successfully
     * @param f the food to be eaten
     * @return true if the snake successfully ate the food
     */
    public boolean eatFood(Food f) {
        BodySegment frontPiece = segments.iterator().next(); // takes first segment of list
        // distance formula 
        double distance = Math.sqrt(Math.pow(frontPiece.getX() - f.getX(), 2) +
                                    Math.pow(frontPiece.getY() - f.getY(), 2));
        // below finds distance between head and food to see if the two are touching
        double distanceBetween = distance - (SEGMENT_SIZE * 2);
        if (distanceBetween < 0) {
            BodySegment lastPiece = segments.descendingIterator().next();
            // adds tail to back of snake by taking the og X and Y and subtracting the deltas to show movement
            segments.add(new BodySegment(lastPiece.getX() - deltaX,
                                         lastPiece.getY() - deltaY,
                                         SEGMENT_SIZE));
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns true if the head of the snake is in bounds
     * @return whether or not the head is in the bounds of the window
     */
    public boolean isInbounds() {
        // position of initial segment and makes it a variable for the bounds
        BodySegment frontPiece = segments.iterator().next();
        if (frontPiece.getX() < 0 || frontPiece.getX() > 1 ||
            frontPiece.getY() < 0 || frontPiece.getY() > 1) {
            // this means the snake is out of bound
            return false;
        } else {
            return true;
        }
    }
}
