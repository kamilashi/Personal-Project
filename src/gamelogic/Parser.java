package gamelogic;
import java.util.Scanner;

public class Parser 
{
    private CommandWords commands;  // holds all valid command words
    //private Scanner reader;         // source of command input

    /**
     * Create a parser to read from the terminal window.
     */
    public Parser() 
    {
        commands = new CommandWords();
       
    }

    /**
     * @return The next command from the user.
     */
    public Command getCommand(String userInput) 
    {
        String inputLine = userInput;   // will hold the full input line
        String word1 = null;
        String word2 = null;
        String word3 = null;

        IORedirect.printOutput("> ");     // print prompt

        //inputLine = reader.nextLine();
        
        // Find up to two words on the line.
        Scanner tokenizer = new Scanner(inputLine);
        if(tokenizer.hasNext()) {
            word1 = tokenizer.next();      // get first word
            if(tokenizer.hasNext()) {
                word2 = tokenizer.next();      // get second word
                if(tokenizer.hasNext()) {
                    word3 = tokenizer.next(); 
                // note: we just ignore the rest of the input line.
                }
            }
        }
        tokenizer.close();            //no idea why		
        return new Command(commands.getCommandWord(word1), word2, word3);
    }

    /**
     * Print out a list of valid command words.
     */
    public void showCommands()
    {
        commands.showAll();
    }
}
