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
		String firstPass = password(kb);
		String salt = saltMine();
		System.out.println(salt);
		byte[] firstHash = hashThePass(firstPass, salt);
		File db =  new File("privateDBpleasedonttouch.txt");
		System.out.println(salt);
		//Rewrites the file every time.
		//PrintWriter out = new PrintWriter(new FileWriter(db, false));
		//Now we encrypt
		//After this, we just have to digest the hash and store it along with the salt

		
		Scanner reader = new Scanner(db);
		//String newSalt = reader.nextLine();
		
		//Looping to check the second password.
		System.out.println("Please enter the password again to verify.");
		String secondPassword = password(kb);
		System.out.println(salt);
		byte[] newHash = hashThePass(secondPassword, salt);
		
		if(MessageDigest.isEqual(firstHash, newHash))
			System.out.println("Hurray! You remembered your password!");
		else
			System.out.println("Sorry, your password is incorrect.");
		
		System.out.println(salt);
	}
	public static String password(Scanner kb)
	{
		//6 characters minimum, 16 maximum
		//one upperCase, lowercase, digit, alphabetic
		Pattern pattern = Pattern.compile("\\A(?=\\w{6,16}\\z)" //checks for password length
				+ "(?=\\D*\\d)"			//check for digit
				+ "(?=[^A-Z]*[A-Z])"	//check for uppercase
				+ "(?=[^a-z]*[a-z])" 	//lowercase
				+ "");//end of string
		boolean looping = true;
		String attemptedPassword = "";
		while(true == looping)
		{
			attemptedPassword = kb.nextLine();
			Matcher matcher = pattern.matcher(attemptedPassword);
			if(matcher.find())
			{
				System.out.println(attemptedPassword + " is a valid password.");
				looping = false;
			}	
			else{
				System.out.println(attemptedPassword + " is an invalid password. Please enter a valid password");
			}
		}
		return attemptedPassword;
	}
	
	
	public static byte[] hashThePass(String pass, String salt) throws NoSuchAlgorithmException
	{
		int newArrayLength = pass.getBytes().length + salt.getBytes().length;
		byte[] ret = Arrays.copyOf(pass.getBytes(), newArrayLength);
		
		for(int i = pass.getBytes().length; i < ret.length - 1; i++)
			ret[i] = salt.getBytes()[i - salt.getBytes().length];
		
		MessageDigest md = MessageDigest.getInstance("md5");
		md.update(ret);
		
		return md.digest();
	}
	public static String saltMine(){
		String ret = "";
		SecureRandom rng = new SecureRandom();
		for(int i = 0; i < 8; i++)
			ret = ret + rng.nextInt(10);
		
		return ret;
	}


}
