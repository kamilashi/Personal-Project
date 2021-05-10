package gamelogic;

import ui.GUI;
import java.io.IOException;
import java.util.Date;
import java.util.Scanner;

public abstract class IORedirect {
	private static String outputString = "";
	private static String inputString = "";
	private static Scanner reader;
	private static boolean consoleActivate = false;
	static GUI gui;

	public static void updateOutput(String string) {
		
		if (string.contains("<currentTime>")) {					
			Date time = new Date();							//write to buffer
			outputString += String.format(string.replace("<currentTime>", "%tR") + "%n", time);
		} else {
			outputString += String.format("%s %n", string);
		}
	}

	public static String getOutput() // for this implementation
	{

		String output = outputString;
		outputString = "";
		if (output != null) {				//from buffer to return
			return output;					//flush buffer
		} else {
			return String.format("");
		}
	}

	public static void printOutput() // no need for this method??
	{
		if (consoleActivate) {
			String output = outputString;
			outputString = "";					//from buffer to console
			if (output != null) {				//flush buffer
				System.out.println(output);
			}
		} else 
		{
			//redirect to GUI
		}
	}
	
	public static void printOutput(String string) //from parameters to console/GUI? no buffer
	{
		String output = "";
		if (string.contains("<currentTime>")) {
			Date time = new Date();
			output += String.format(string.replace("<currentTime>", "%tR"), time);		//format the string
		} else {
			output += String.format("%s", string);
		}
		
		if (consoleActivate) {
			String out = output;
			output = "";
			if (out != null) {
				System.out.println(out);
			}
		} else 
		{
			//redirect to GUI
			
			gui.updateOutput(output + "\n");
		}
	}

	public static void updateInput(String input) {
		inputString = input; // to buffer
	}


	public static String getInput() {
		if (consoleActivate) {
			reader = new Scanner(System.in); // ok to do so?
			return reader.nextLine();
		} else {
			String input = inputString;  //from buffer
			inputString = "";
			return input;
		}
	}

	public static void activateConsole() {
		 consoleActivate = true;
	}
	
	public static void printNotFound() {
		printOutput("Can't find it");
	}
	public static void printError(String message) {
		printOutput(message);
	}

	public static void activateGUI(Game game) {
		try {
			gui = new GUI(game);
			consoleActivate = false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void activateConsoleWithGUI(Game game) {
		try {
			gui = new GUI(game);
			consoleActivate = true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
