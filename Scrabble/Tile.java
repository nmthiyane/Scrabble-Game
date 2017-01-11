/**
 * A Tile object represents a scrabble tile. It has two attributes, the letter and the value.
 * @author Stephan Jamieson
 * @version 25/08/2015
 */
public class Tile {

    private char letter;
    private int value;

    /** 
     * Create a Tile for the given letter and value.
     */    
    public Tile(char letter, int value) {
        this.letter = letter;
        this.value = value;
    }

    /**
     * Obtain the letter on this tile. 
     */
    public char letter() { return letter;}
    
    /**
     * Obtain the value of this letter tile.
     */
    public int value() { return value;}

    /**
     * Obtain a String representation of this tile.
     */
    public String toString() {
        return "("+letter+", "+value+")";
    }
}
