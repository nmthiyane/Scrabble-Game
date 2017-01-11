import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
 * This utility class may be used to read the contents of a word file into an array.
 * 
 * @author Stephan Jamieson
 * @version 25/8/2015
 */
public class FileToArray {


    private final static String START_LABEL = "START";

    /**
     * Constructs a word list from the named file. The file should be
     * organized such that there is a single word per line.
     * 
     * The file is assumed to contain a preamble such as a copyright notice.
     * The beginning of the data should be marked by a line containing
     * the single word "START".
     * 
     * @throws FileNotFoundException if unable to open the named file.
     * @throws IOException if some error occurs during reading of the file (uncommon).
     */
    public static String[] read(String filename) throws IOException, FileNotFoundException {    
        BufferedReader buffer = new BufferedReader(new FileReader(filename));        

        String line = buffer.readLine();
        while (! line.equals(START_LABEL) ) {
            line = buffer.readLine();
        }

        List<String> words = new ArrayList<String>();
        line = buffer.readLine();
        
        while (line !=null) {
            words.add(line.trim());
            line = buffer.readLine();
        }
        buffer.close();
        String[] arrayResult = new String[words.size()];
        arrayResult = words.toArray(arrayResult);
        return arrayResult;
    }

    /**
     * Test harness for the read method.
     */
    public static void main(String[] args) {
        // File name in args[0].
        try {
            String[] results = FileToArray.read(args[0]);
            for (String word : results) {
                System.out.println(word);
            }
        }
        catch (Exception exception) {
            System.out.println("Whoops, file error:");
            System.out.println(exception);
        }
    }
}
