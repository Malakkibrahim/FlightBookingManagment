package eg.alex.fcds.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Console {
	public static void print(String text) {
		System.out.println(text);
	}

	public static void printInline(String text) {
		System.out.print(text);
	}

  public static int displayMenu(String menuTitle, List<String> options) {
		Console.print(menuTitle);
		for (int i = 0; i < options.size(); i++) {
			Console.print("\t[" + (i + 1) + "] " + options.get(i));
		}
		try {
			Scanner scanner = new Scanner(System.in);
			int value = scanner.nextInt();
			
			while (value < 1 || value > options.size()) {
				Console.print("Try again!");
				value = scanner.nextInt();
			}
			return value;
		} 
    catch (Exception ex) {
      throw ex;
    }
	}

	public static ArrayList<String> displayForm(String formTitle, List<String> inputs) {
		Console.print(formTitle);
		Scanner scanner = new Scanner(System.in);
		ArrayList<String> responses = new ArrayList<>();
		try {
			for (String input : inputs) {
				Console.print("\t" + input + ": ");
		    	String response = scanner.nextLine();
				responses.add(response);
			}
			return responses;
		} 
	  catch (Exception ex) {
	    throw ex;
	  } 
	}
}
