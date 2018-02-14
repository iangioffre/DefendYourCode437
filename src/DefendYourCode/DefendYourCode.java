package DefendYourCode;

import java.io.File;
import java.util.Scanner;

public class DefendYourCode {

	public static void main(String[] args) {
		//read name twice once for 
		

	}
	public static String getName(Scanner kb, String firstORlast)
	{
		System.out.println("Please enter your "+firstORlast+" name");
		return kb.nextLine();
	}
	public static int readInt(Scanner kb)
	{
		System.out.println("Please enter the number you would like to open");
		return Integer.parseInt(kb.nextLine());
	}
	public static String readFileName(Scanner kb)
	{
		System.out.println("Please enter the name of the file you would like to open");
		return kb.nextLine();
	}
	public static File openFile(String filename)
	{
		return new File(filename);
	}

}
