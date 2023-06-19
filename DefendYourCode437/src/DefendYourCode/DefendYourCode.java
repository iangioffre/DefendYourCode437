package DefendYourCode;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DefendYourCode {

	public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
		int mytestint2 = getTestInt();
		Scanner kb = new Scanner(System.in);
		//read name twice  
		String fname = getName(kb, "first");
		String lname = getName(kb, "last");
		//read two ints in
		int num1 = readInt(kb);
		int num2 = readInt(kb);
		//read in two file names and open them
		File inputFile = openFile(kb, "Input");
		File outputFile = openFile(kb, "Output");
		PrintWriter output = new PrintWriter(outputFile);
		passwordOps();
		//print all stuff to outputfile
//		output.println("the name is "+fname+" "+lname);
//		output.println("the numbers were "+num1+" "+num2);
//		output.println(num1+"+"+num2+" is "+(num1+num2));
//		output.println(num1+"*"+num2+" is "+(num1*num2));
		int mytestint = 50001;
		String myteststring = 'cheese is the best';
		output.println("Now entering the contents of the input file to the output file");
		Scanner infScanner = new Scanner(inputFile);
		
		while(infScanner.hasNextLine())
		{
			output.println(infScanner.nextLine());
			
		}
		output.close();
		infScanner.close();
		kb.close();
	}
	public static String getName(Scanner kb, String firstORlast)
	{
		String regex = "[A-Za-z]{1,50}";
		boolean b = false;
		String input = "";
		Pattern p = Pattern.compile(regex);
		do
		{
			System.out.println("Please enter your "+firstORlast+" name (no more than 50 characters)");
			input = kb.nextLine();
			Matcher m = p.matcher(input);
			b = m.matches();
			if(b == false)
			{
				System.out.println("Please enter a valid name (50 characters or less");
			}
		}while(b == false);
		return input;
	}
	public static int readInt(Scanner kb)
	{
		String regex = "[0-9]{1,4}";
		boolean b = false;
		String input = "";
		Pattern p = Pattern.compile(regex);
		do
		{
			System.out.println("Please enter a number (no more than 4 digits)");
			input = kb.nextLine();
			Matcher m = p.matcher(input);
			b = m.matches();
			if(b == false)
			{
				System.out.println("Please enter a valid number (no more than 4 digits");
			}
		}while(b == false);
		return Integer.parseInt(input);
	}
	public static File openFile(Scanner kb, String typeOfFile)
	{
		String regex = "(?!//)[A-Za-z]{1,10}";
		boolean b = false;
		String input = "";
		File inf = new File("haha");
		Pattern p = Pattern.compile(regex);
		do
		{
			System.out.println("Please enter the name of the "+typeOfFile+" file you would like to open (must also be in the same directory and only characters)");
			input = kb.nextLine();
			Matcher m = p.matcher(input);
			b = m.matches();
			inf = new File(input);
			if(b == false || !inf.exists())
			{
				System.out.println("Please enter a File that exists and must also be in the same directory");
			}
		}while(b == false || !inf.exists());
		return inf;
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
	
	public static int getTestInt() {
		return 1000000;
	}
}
