package DefendYourCode;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DefendYourCode {

	public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
		passwordOps();

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
	public static void passwordOps() throws IOException, NoSuchAlgorithmException{
		System.out.println("Please enter a password containing:\n\t6 to 16 alphabetic and numeric characters"
				+ "\n\tAt least one alphabetic and one numeric password"
				+ "\n\tAt least one uppercase and one lowercase letter");
		Scanner kb = new Scanner(System.in);
		boolean stopLoop = false;
		String firstPass = "";
		//Looping to check the first password.
		while(false == stopLoop)
		{
			firstPass = kb.nextLine();
			stopLoop = password(firstPass);
		}
		SecureRandom rng = new SecureRandom();
		byte[] salt = new byte[8];
		rng.nextBytes(salt);

		byte[] hashed =Arrays.copyOf(firstPass.getBytes(), (firstPass.length() + salt.length));
		int counter = 0;
		for(int i = firstPass.length(); i < hashed.length; i++)
		{
			hashed[i] = salt[counter];
			counter++;
		}
		File db =  new File("privateDBpleasedonttouch.txt");

		//Rewrites the file every time.
		//PrintWriter out = new PrintWriter(new FileWriter(db, false));
		//Now we encrypt
		MessageDigest md = MessageDigest.getInstance("md5");
		md.update(hashed);
		try (Writer out = new BufferedWriter(new OutputStreamWriter(
	              new FileOutputStream("filename.txt"), "utf-8"))) {
			out.write("Input");
			/*out.write(salt.toString());
			out.write(md.digest().toString());*/
	}

		for(byte b: hashed)
			System.out.print(b);
		System.out.println();
		for(byte z: salt)
			System.out.print(z);
		System.out.println("Done!");
		
		Scanner reader = new Scanner(db);
		
	}
	public static boolean password(String inp)
	{
		//6 characters minimum, 16 maximum
		//one upperCase, lowercase, digit, alphabetic
		Pattern pattern = Pattern.compile("\\A(?=\\w{6,16}\\z)" //checks for password length
				+ "(?=\\D*\\d)"			//check for digit
				+ "(?=[^A-Z]*[A-Z])"	//check for uppercase
				+ "(?=[^a-z]*[a-z])" 	//lowercase
				+ "");//end of string
		Matcher matcher = pattern.matcher(inp);

		if(matcher.find())
		{
			System.out.println(inp + " is a valid password. Hashing and salting now!");
			return true;
		}	
		else{
			System.out.println(inp + " is an invalid password.");
			return false;
		}
	}

}
