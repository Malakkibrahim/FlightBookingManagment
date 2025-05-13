package eg.alex.fcds.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Console {
  private static Scanner scanner;

	public static void print(String text) {
		System.out.println(text);
	}

	public static void printInline(String text) {
		System.out.print(text);
	}

  public static int displayMenu(String menuTitle, List<String> options) {
		Console.print(menuTitle);
		for (int i = 1; i < options.size() + 1; i++) {
			Console.print("\t[" + i + "] " + options.get(i));
		}
		try {
			scanner = new Scanner(System.in);
			int value = scanner.nextInt();
			
			while (value < 1 || value > options.size()) {
				Console.print("Try again!");
				value = scanner.nextInt();
				scanner.close(); 
			}
			scanner.close();
			return value;
		} 
    catch (Exception ex) {
      throw ex;
    }
	}

	public static ArrayList<String> displayForm(String formTitle, List<String> inputs) {
		Console.print(formTitle);
		ArrayList<String> responses = new ArrayList<>();
		try {
			for (String input : inputs) {
				Console.print("\t" + input + ": ");
				scanner = new Scanner(System.in);
		    	String response = scanner.nextLine();
				responses.add(response);
				scanner.close();
			}
			return responses;
		} 
	    catch (Exception ex) {
	    	throw ex;
	    }
	}
}
