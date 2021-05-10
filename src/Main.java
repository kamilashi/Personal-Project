import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
    	System.setProperty("sun.java2d.uiScale", "1.0");
    	System.setProperty("prism.allowhidpi", "false");
    	
        gamelogic.Game newGame = new gamelogic.Game();
        newGame.playWithGUI("Isaac");
    }
}
