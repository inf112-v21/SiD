package sid.roborally.game_mechanics;

/**
 *
 *  Directions that can be used by any GridObject.
 *  Rotate methods for right, left and 180.
 *
 *  @author Terje Trommestad
 */

public enum Direction {
    NORTH,
    EAST,
    SOUTH,
    WEST;


    public Direction rotateRight() {
        return values()[(ordinal() + 1) % 4];
    }

    public Direction rotate180() {
         return values()[(ordinal() + 2) % 4];
     }

    public Direction rotateLeft() {
        return values()[(ordinal() + 3) % 4];
    }
}








