/* Ntuthuko Mthiyane
   Assignment 6, GUI
   01 September 2015
*/ 

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.io.*;

public class ScrableGame extends JFrame implements ActionListener{
   private JLabel playerName;
   private JLabel Score;
   private int ScoreValue, start =-1,next=-2;
   private JPanel topBar,sideBar,bottomBar,top1;
   private JPanel board;
   private JTextField enteredWord;  
   private ArrayList<TileGUI> storedTiles;
   private TileCollection c; 
   private int tempScore = 0;
   private String[] results;
   private ArrayList<String> tempUsedWords= new ArrayList<String>();
   private TileGUI[] b;
   private String[] highScore = new String[2];
   private String ender;
     
   public ScrableGame(){
      setSize(850,650);
      setTitle("S . C  .R  . A . B . L . E   G . A .M . E");
      this.getContentPane().setBackground( Color.RED);
      
      ImageIcon img = new ImageIcon("unnamed.png");                                        // setting icon for the window
      setIconImage(img.getImage());
      
      setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
      this.addWindowListener( new CheckExit() );                  // So the apps opens another window when user closes
  
  // ======================= Creating defining the layouts ================================    
      top1 = new JPanel();
      top1.setLayout(new GridLayout(2,1));
      topBar = new JPanel();
      topBar.setLayout(new GridLayout(1,5));                 // top bar (Game, Help options)
	   board = new JPanel();
	   setLayout(new BorderLayout() );                        // Game board
      sideBar = new JPanel();
      sideBar.setLayout(new GridLayout(6,1));                // side view/ Player name etc...
      bottomBar = new JPanel();
      bottomBar.setLayout(new GridLayout(2,1));              // bottom panel      
      JPanel bottom1 = new JPanel();
      bottom1.setLayout(new GridLayout(1,3));
      JPanel bottom2 = new JPanel();
      bottom2.setLayout(new FlowLayout());
	   add(top1, BorderLayout.NORTH);
	   add(board, BorderLayout.CENTER);
      add(sideBar, BorderLayout.EAST);
      add(bottomBar, BorderLayout.SOUTH);
      
    	   	
  // ================================= Creating the bord ======================================
	   board.setLayout( new GridLayout(6,6) );
      b = new TileGUI[36];
      c = new TileCollection();
      storedTiles = new ArrayList<TileGUI>();
 
      for(int i=0;i<b.length;i++){       
         b[i] = new TileGUI(c.removeOne());
         board.add(b[i]);
         b[i].setActionCommand(""+ i);
         b[i].setBackground(Color.lightGray);
         b[i].addActionListener(this) ;                                                  // Adding action for each button
         storedTiles.add(b[i]);                                                         // Storing existing tiles in a list
      }

   // ============================== Creating JMenu bars ============================== 
   
     // ********************* Creating JMenu bar 1 (Game Options) ***************************
      JMenu gameOptions = new JMenu("Game"); 
      JMenuItem newGame = new JMenuItem("New Game");                      // Creating a menu bar and adding items
      JMenuItem exitGame = new JMenuItem("Close Game");
      JMenuItem topScores = new JMenuItem("View High Score");
      JMenuItem addName = new JMenuItem("Add Player Name"); 
      newGame.addActionListener(this);
      gameOptions.add(newGame);
      topScores.addActionListener(this);
      gameOptions.add(topScores);
      addName.addActionListener(this);
      gameOptions.add(addName);
      exitGame.addActionListener(this);
      gameOptions.add(exitGame);
      JMenuBar bar = new JMenuBar();                                   // adding to the bar
      bar.add(gameOptions);
      setJMenuBar(bar);
      topBar.add(bar);                                                 // Adding at the top of tjhe GUI
      
       
  // ***************************** Creating JMenu bar 2 **************************************
      JMenu instruct = new JMenu("Instructions"); 
      JMenuItem rules = new JMenuItem("Rules"); 
      instruct.add(rules);
      rules.addActionListener(this);
      
      JMenuItem how = new JMenuItem("How To Play"); 
      instruct.add(how);
      how.addActionListener(this);
      
      JMenuBar bar3 = new JMenuBar();                                // adding to the bar      
      bar3.add(instruct);
      setJMenuBar(bar3);
      topBar.add(bar3);                                             // adding to the top bar 
   
      
  // ****************** Creating JMenu bar 2 (Help) **************************
      JMenu help = new JMenu("Help"); 
      JMenuItem viewDicWords = new JMenuItem("View First 1000 Words In The Dictionary"); 
      help.add(viewDicWords);
      viewDicWords.addActionListener(this);  
      JMenuItem hint = new JMenuItem("Get Hint"); 
      help.add(hint);
      hint.addActionListener(this);
      JMenuBar bar2 = new JMenuBar();                                // adding to the bar      
      bar2.add(help);
      setJMenuBar(bar2);
      topBar.add(bar2);                                             // adding to the top bar 
         
  // =============== Creating buttons ===================
      JButton done = new JButton("Enter Word");
      done.addActionListener(this);
      
      JButton clear = new JButton("Clear");
      clear.addActionListener(this);
      
      JButton endGame = new JButton("End Game");
      endGame.addActionListener(this);
  
     
  // ================================ Creating labels =====================================   
      JLabel word = new JLabel("                --------- Word Being Formed -----------");
      enteredWord = new JTextField(36);                               // 36 becuase the word has onlt 36 tiles/ word cannot be more than that.
      JLabel playerLabel = new JLabel(" Player Name: ");
      playerName = new JLabel(" Unknown");                            // Default player name
      JLabel ScoreLabel = new JLabel(" Your Score:    ");
      Score = new JLabel("    "+ScoreValue);  
      JLabel emptyLabel = new JLabel("    ");                                     // Default Score
      
      Image myPic1 = Toolkit.getDefaultToolkit().getImage("images.jpg");       
      JLabel pic1 = new JLabel(new ImageIcon(myPic1));
   
 // ================================ Adding on the side bar =====================
      sideBar.add(playerLabel);
      sideBar.add(playerName);
      sideBar.add(ScoreLabel);
      sideBar.add(Score);
      
 // ================================ Adding to bottom bar ========================
      bottom1.add(emptyLabel);
      bottom1.add(word);
      bottom1.add(emptyLabel);
      bottom2.add(enteredWord);
      bottom2.add(clear);
      bottom2.add(done);
      bottom2.add(endGame);
      bottomBar.add(bottom1);
      bottomBar.add(bottom2);
      
      
      top1.add(topBar);               
      top1.add( pic1);
      
  // ============================= Getting existing words ========================
      try {                                                        // getting the list
            results = FileToArray.read("EnglishWords.txt");
      }
      catch (Exception exception) {
          System.out.println("Whoops, file error:");
          System.out.println(exception);
      }
      
  // ================================== Styling ======================================
      Font font_label = playerLabel.getFont();                                  // changing colors and font
      playerLabel.setFont(font_label.deriveFont(Font.BOLD,18f));
      playerLabel.setForeground(Color.WHITE);
      
      word.setFont(font_label.deriveFont(Font.BOLD,18f));
      word.setForeground(Color.WHITE);
      
      playerName.setFont(font_label.deriveFont(Font.BOLD,18f));
      playerName.setForeground(Color.WHITE);
      
      ScoreLabel.setFont(font_label.deriveFont(Font.BOLD,18f));
      ScoreLabel.setForeground(Color.WHITE);
      
      Score.setFont(font_label.deriveFont(Font.BOLD,18f));
      Score.setForeground(Color.WHITE);
      
      
      top1.setBackground(Color.gray);
      board.setBackground(Color.BLACK);
      sideBar.setBackground(Color.gray);
      setBackground(Color.gray);
      bottom1.setBackground(Color.gray);
      bottom2.setBackground(Color.gray);
      
      endGame.setBackground(Color.lightGray);
      clear.setBackground(Color.lightGray);
      done.setBackground(Color.lightGray);
      
      enteredWord.setBackground(Color.lightGray);      
    
   } //end of constructor

   
   // ************************************************** Game Action Listener *****************************************
   
   public void actionPerformed(ActionEvent e){
      String chosen = e.getActionCommand();

   // ********************** Creating hints ***************************************** 
      if (chosen.equals("Get Hint")){

         String hintWords = "";
         int nextLine1 = 0;                                               // Just dummy variable
         
         if(enteredWord.getText().length()==0){
               hintWords += "No word was entered (No points deducted)"; 
            } 
         else{
            ScoreValue -=2;
            for(String m:results){
               if (m.length()>=enteredWord.getText().length()){
                  if(m.substring(0,enteredWord.getText().length()).equalsIgnoreCase(enteredWord.getText())){      // and word of it begins like this
                     hintWords += m+", ";
                     nextLine1 += 1;
                  }
               }            
               if(nextLine1==20){                                             // making words go on a new line
                  hintWords += "\n";
                  nextLine1 = 0;
               }
            }
            if (hintWords.length()==0){                                       // when no word is found
               hintWords = "No word(s) found. Sorry about your lost point.";
            }
         }
         this.Score.setText("    "+ScoreValue);
         JPanel panel0 = new JPanel();
         JOptionPane.showMessageDialog(panel0,hintWords,"Hints",JOptionPane.INFORMATION_MESSAGE,null);  
      } 
      
     // ************************ Add player name *****************************************
      else if(chosen.equals("Add Player Name")){                                      
         String Name = JOptionPane.showInputDialog("Enter player name:");
         if(Name!=null){
         this.playerName.setText(" " + Name);}  
      }
      
     //  **************************** View game rules *************************************
      else if(chosen=="Rules"){                                                       
         String spacer = "         ";
         JPanel panel = new JPanel();
         JOptionPane.showMessageDialog(panel,"Game Rules:\nPresented with a 6x6 grid of Scrabble tiles, the challenge is to form as many high scoring words as possible. The tiles must form words which,\nin crossword fashion, flow left to right in rows or downwards in columns. The words must be defined in the standard dictionary.\n"+spacer+"* Words may only be formed from sequences of adjacent tiles.\n" +spacer+"* Two tiles are adjacent if their edges or corners meet.\n" +spacer+"* A tile may be used in at most one word. (Tiles appearing red are used tiles)\n","Rules",JOptionPane.INFORMATION_MESSAGE, null);
      }
      else if(chosen=="How To Play"){
         String spacer = "      ";
         JPanel panel = new JPanel();
         JOptionPane.showMessageDialog(panel,"How To Play:\n"+spacer+"* Form a word from sequences of adjacent tiles.\n"+spacer+"* Press 'Enter Word' when selection of a tile sequence is complete.\n"+spacer+"* Press 'Clear' to cancel the whole sequence or first move of sequence.\n"+spacer+"* Press 'End Game' when no more words can be form.\n"+spacer+"* Double click a letter to undo move.\n "+spacer+"\nGetting Help:\nYou can get help by selecting 'View Dictionary' or 'Hint'.\nNote:\n"+spacer+"* You lose 1 point by using View Dictionary.\n"+spacer+"* You lose 2 points by using hint (Helps by giving options of completing a current word).\n","How To Play",JOptionPane.INFORMATION_MESSAGE, null);
      }
      
     // ******************************** To close game **********************************
      else if(chosen == "Close Game"){
         int dialogButton = JOptionPane.YES_NO_OPTION;
         int dialogResult = JOptionPane.showConfirmDialog(this, "Are you sure you want to exit?", "Scrabe Game: Warning", dialogButton);   // Getting confirmation from user
         if(dialogResult == 0) {                                                      // closing if yes
            System.exit(0);
         } 
      }
      
    // ******************************** Dictionary **********************************
      else if(chosen =="View First 1000 Words In The Dictionary"){
         ScoreValue -=1;
         this.Score.setText("    "+ScoreValue);
         String dicWords = "";
         int nextLine = 0;
         int count=0;
         for(String z:results){
            dicWords += z+", ";
            nextLine += 1;
            count+=1;
                        
            if(nextLine==30){        // making words go on a new line
               dicWords += "\n";
               nextLine = 0;
            }
            if (count ==1000){
               break;
            }
         }
         System.out.println(dicWords);         // ================== ************remove ************************* =============================
         JPanel panel3 = new JPanel();
         JOptionPane.showMessageDialog(panel3,dicWords,"Dictionary",JOptionPane.INFORMATION_MESSAGE,null);                  // Making window to display the words
      }
      
     // ************************************** If player wants a new game **************************************************
     else if(chosen== "New Game"){

        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog(this, "Are you sure you want to estart a new game?", "Scrabe Game: Warning", dialogButton);   // Getting confirmation from user
        if(dialogResult == 0) {                                                      // closing if yes
           dispose();
           ScrableGame p1 = new ScrableGame();                           // creating new object
           p1.setVisible(true);

            
         } 
      }

       
     // ************************************** When user is done forming a particular word **********************************
      else if(chosen =="Enter Word"){
         String finder = "not found";                                      // variable to check if word exists
         
         for(String q:results){
            if(enteredWord.getText().equalsIgnoreCase(q)){                // if word exists
               ScoreValue +=tempScore;
               this.Score.setText("    "+ScoreValue);
               finder = "found";                                          // when word is found
               
               for(String s:tempUsedWords){                               // declare the letters as used only if a words forms
                   storedTiles.get(Integer.parseInt(s)).setEnabled(false);
                   storedTiles.get(Integer.parseInt(s)).setBackground(Color.RED);               // changing button Color to red
               }
               break;
            }
         }
         if (finder.equals("not found")){
            JPanel panel2 = new JPanel();
            JOptionPane.showMessageDialog(panel2,"Oops!!\nThe word you formed does not exist within the existing dictionary.\nClick on 'Help' > 'View Dictionary' to view existing words.","Error",JOptionPane.INFORMATION_MESSAGE,null);
            
            for(String l:tempUsedWords){                               // undo color
               storedTiles.get(Integer.parseInt(l)).setBackground(Color.lightGray);   // changing button color 
               storedTiles.get(Integer.parseInt(l)).setForeground(Color.BLACK); // resetting color
            }
         }
         start =-1;                           // resetting start placeholder
         tempScore=0;                         // reseting both text bar and temp score
         enteredWord.setText("");
         tempUsedWords.removeAll(tempUsedWords);
  
      }    
    // ************************** To re-enter word ***************************  
      else if(chosen=="Clear"){
         start =-1;                           // resetting start placeholder
         tempScore=0;                         // reseting both text bar and temp score
         enteredWord.setText("");
         
         
         for(String LL:tempUsedWords){                                             // undo color
            storedTiles.get(Integer.parseInt(LL)).setBackground(Color.lightGray);             // changing button color to null 
            storedTiles.get(Integer.parseInt(LL)).setForeground(Color.BLACK);
         }
         tempUsedWords.removeAll(tempUsedWords);
      }
     // ********************************* To view high Score *******************************************
     else if (chosen=="View High Score"){
     
        String fileName = "highScores.txt";                                                        // The name of the file to open. 
        String line = null;
        
        try {
            
            FileReader fileReader = new FileReader(fileName);                                      // FileReader reads text files in the default encoding.             
            BufferedReader bufferedReader = new BufferedReader(fileReader);                        // Wrap FileReader in BufferedReader.

            while((line = bufferedReader.readLine()) != null) {
                highScore=line.split(",");
            }    
            bufferedReader.close();                                                               // Close files.           
        }
        catch(FileNotFoundException ex) {                                                         // catching errors
           System.out.println("Unable to open file '" + fileName + "'");                
        }
        catch(IOException ex) {
           System.out.println("Error reading file '" + fileName + "'");                   
        }
        
        JPanel panel3 = new JPanel();
        JOptionPane.showMessageDialog(panel3,"HIGH SCORE VIEWER\nPlayer Name:  "+highScore[0]+"\nPlayer Score:  "+highScore[1],"High Score",JOptionPane.INFORMATION_MESSAGE,null);
            

     }   
     // ******************************** Indicatibg the game has been ended ***************************
     else if(ender=="end"){
        JPanel panel4 = new JPanel();
        JOptionPane.showMessageDialog(panel4,"Game has been end, click new game to play again","Error",JOptionPane.INFORMATION_MESSAGE,null);
     }
  
     // ********************************* Recording Score **********************************************
     else if(chosen =="End Game"){
        ender="end"; 
        String fileName = "highScores.txt";                                                        // The name of the file to open. 
        String line = null;
        
       
        try {
            
            FileReader fileReader = new FileReader(fileName);                                      // FileReader reads text files in the default encoding.             
            BufferedReader bufferedReader = new BufferedReader(fileReader);                        // Wrap FileReader in BufferedReader.

            while((line = bufferedReader.readLine()) != null) {
                highScore=line.split(",");
                System.out.println(highScore[0] +" " + highScore[1]);
            }    
            bufferedReader.close();                                                               // Close files.           
        }
        catch(FileNotFoundException ex) {                                                         // catching errors
           System.out.println("Unable to open file '" + fileName + "'");                
        }
        catch(IOException ex) {
           System.out.println("Error reading file '" + fileName + "'");                   
        }
        
        if(Integer.parseInt(highScore[1])<ScoreValue){                                                // If high score achieved
       
           try {     // Saving the score to the text fie   
              FileWriter fileWriter = new FileWriter(fileName);
              BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);                                            // Always wrap FileWriter in BufferedWriter.
              bufferedWriter.write(playerName.getText().substring(1,playerName.getText().length())+"," + ScoreValue );        // append a newline character.
              bufferedWriter.close();                                                                                         // Closing file.
           }
           catch(IOException ex) {
              System.out.println("Error writing to file '"+ fileName + "'");                                  // if file doesn't exist
           }
        }
        
     // ***************************** disable the grid/tiles when game is ended ***********************
        for(String rr:tempUsedWords){                                               // undo color
               storedTiles.get(Integer.parseInt(rr)).setEnabled(true);                  // enable buttons if word not found
               storedTiles.get(Integer.parseInt(rr)).setBackground(Color.lightGray);   // changing button color 
               storedTiles.get(Integer.parseInt(rr)).setForeground(Color.BLACK);       // resetting color
            }

        for(TileGUI qq: storedTiles){                                            // disable tiles
           qq.setEnabled(false); 
        }        
        tempUsedWords.removeAll(tempUsedWords);                                // clear the temp used positions
               
     }
     
     // ********************************** When tile is selected ****************************************
      else{
         
         char selectedLetter = storedTiles.get(Integer.parseInt(chosen)).getTile().letter();         // getting letter of chosen tile
         int letterScore = storedTiles.get(Integer.parseInt(chosen)).getTile().value();              // getting value of chosen tile
         
         
         if (start ==-1){  // for first chosen letter
            if(tempUsedWords.contains(chosen)){              // when letter has been used before
            }
            
            else{                                                                         // letter hasn't been used
               tempUsedWords.add(chosen);
               enteredWord.setText( enteredWord.getText() + selectedLetter);               // Updating the word with entered character    
               tempScore += letterScore;                                                   // updating temp score        
               start=Integer.parseInt(chosen);                                             // now starts stands for previous
               storedTiles.get(Integer.parseInt(chosen)).setBackground(Color.GREEN);   // changing button color 
               storedTiles.get(Integer.parseInt(chosen)). setForeground(Color.WHITE);   // changing text color       
            } //  end of else  
         }   
         
        else if (tempUsedWords.size()>1 && chosen==tempUsedWords.get(tempUsedWords.size()-1)){   // to undo a specific letter when you like it twice
           tempScore -=letterScore;                           
           start =Integer.parseInt( tempUsedWords.get(tempUsedWords.size()  -2));  // thing in position not this /////////////////////////******************/*/*****/*
           storedTiles.get(Integer.parseInt(chosen)).setBackground(Color.lightGray);             // changing button color to null 
           storedTiles.get(Integer.parseInt(chosen)).setForeground(Color.BLACK);
           tempUsedWords.remove(tempUsedWords.size()-1);
           enteredWord.setText( enteredWord.getText().substring(0,enteredWord.getText().length()-1));
           
           if(tempUsedWords.size()==1){
              start=-1;
              
           }
           }
                                                                            
         else{
            if(tempUsedWords.contains(chosen)){         // when letter has already been used
            }
            else{
               next =Integer.parseInt(chosen);
               if (next==start-1 || next==start+1 || next==start-5 || next==start+5 || next==start-6 || next==start+6 || next==start-7 || next==start+7){
                  tempUsedWords.add(chosen);
                  enteredWord.setText( enteredWord.getText() + selectedLetter);               // Updating the word with entered character    
                  tempScore += letterScore;  
                  start=Integer.parseInt(chosen);                                                               // now starts stands for previous    
                  storedTiles.get(Integer.parseInt(chosen)).setBackground(Color.GREEN);               // changing button Color
                  storedTiles.get(Integer.parseInt(chosen)). setForeground(Color.WHITE);   // changing text color 
               }
               else{         // remove
                  System.out.println("Ilegal: "+start);
               }
            } // end of 3rd else
         } // end of 2nd else
      } // end of 1st end
      
   } // end of action performed
   
  
 // =========================================== The following clss deals with the window ==========================================
   private class CheckExit extends WindowAdapter {
      public void windowClosing( WindowEvent e) {
         MsgWindow check = new MsgWindow();
         check.setVisible(true);
      }
   }
// *********************** Action listner for the second window **************************
   private class MsgWindow extends JFrame implements ActionListener {
      public MsgWindow( ) {
         setSize(300,150);
         setTitle("Scrable Game: Warning");
         JLabel lab = new JLabel ("Are you sure you want to exit?");
         JButton Y = new JButton("YES");
         JButton N = new JButton("NO");
         
         JPanel tops = new JPanel();
         tops.setLayout(new FlowLayout());
         tops.add(lab);
         JPanel bottoms = new JPanel();
         bottoms.setLayout(new FlowLayout());
         bottoms.add(Y);
         bottoms.add(N); 
         setLayout( new GridLayout(2,1) );
         add(tops); 
         add(bottoms);
         Y.addActionListener(this); 
         N.addActionListener(this);
      }
      public void actionPerformed(ActionEvent e) {
         String ans = e.getActionCommand();
         if (ans.equals("YES")) System.exit(0);              // end program
         else dispose();                                          // get rid off MsgWindow, user said "NO" to closing
      }
   }  // End of inner class


   
   public static void main(String [] args){
      ScrableGame p = new ScrableGame();
      p.setVisible(true);
   } 
} // end of the GUI
