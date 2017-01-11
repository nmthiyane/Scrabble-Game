import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Random;
/**
 *  A TileCollection object represents a set of Scrabble tiles.  
 *  Tiles may be removed from the collection one at a time.
 *  @author Stephan Jamieson
 *  @version 25/08/2015
 */
public class TileCollection {

    private static int totalTiles() {
        int total = 0;
        for (int tileNumber=0; tileNumber<FREQUENCY.length; tileNumber++) {

            total=total+FREQUENCY[tileNumber];
        }
        return total;
    }

    private final static char[] LETTER = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H','I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T','U', 'V', 'W', 'X', 'Y','Z'};  
    private final static int[] FREQUENCY = {9, 2, 2, 4, 12, 2, 3, 2, 9, 1, 1, 4, 2, 6, 8, 2, 1, 6, 4, 6, 4, 2, 2, 1, 2, 1};
    private final static  int[] VALUE = {1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3, 1, 1, 3, 10, 1, 1, 1, 1, 4, 4, 8, 4, 10};

    private static Tile[] makeTileArray() {

        Tile[] tiles = new Tile[totalTiles()];
        int instanceIndex = 0;
        
        for (int tileNumber=0; tileNumber<LETTER.length; tileNumber++) {
            char tileLetter = LETTER[tileNumber];
            int tileValue = VALUE[tileNumber];
                
            for (int instances=0; instances<FREQUENCY[tileNumber]; instances++) {
                tiles[instanceIndex] = new Tile(tileLetter, tileValue);
                instanceIndex++;
            }
        }
        return tiles;
    }

    private static void shuffleTiles(Tile[] tileArray) {

        Random random = new Random();
        int numberOfRuns = tileArray.length;

        while (numberOfRuns>0) {

            numberOfRuns--;

            for (int index=0; index<tileArray.length-1; index++) {
        
                int destination = random.nextInt(tileArray.length);
           
                Tile tile = tileArray[destination];
                tileArray[destination] = tileArray[index];
                tileArray[index]=tile;
            }
        }
    }

    /* **************** Instance variables and methods *************** */
    private List<Tile> collection;
    
    /**
     * Create a new collection of scrabble tiles.
     */
    public TileCollection() {

        Tile[] tileArray = makeTileArray();
        shuffleTiles(tileArray);
        this.collection = new ArrayList<Tile>(Arrays.asList(tileArray));
    }

    /**
     * Remove a randomly chosen tile from the collection.
     * @throws NoSuchElementException if the collection is empty.
     */
    public Tile removeOne() {

        if (this.size()==0) {
            throw new NoSuchElementException("TileCollection:removeOne(): collection is empty");
        }
        else {
            return collection.remove(0);
        }
    }

    /**
     * Obtain the number of tiles remaining in the collection.
     */
    public int size() {
        return collection.size();
    }
}
