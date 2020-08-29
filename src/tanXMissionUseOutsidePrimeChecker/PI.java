package tanXMissionUseOutsidePrimeChecker;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Scanner;

import UtilityFunctions.Fraction;

public class PI {

	public static void main(String args[]) {
		getPiForNDigits(100500);
	}
	//Trusting:
	//https://decimal.info/digits-of-pi/value-of-pi-to-5000-decimal-places.html

	
	//Trusting:
	// https://www.piday.org/million/

	public static Fraction getPiForNDigits(int n) {
		
		//TODO: string builders would be more ideal, but nah...
		String numeratorString = "";
		String denumeratorString = "1";
		
		Scanner in = null;
		
		try {
			//File filetry = new File("C:\\Users\\Michael\\projectEuler2\\weirdMathStuff\\piMillion.txt");
			//System.out.println("Path: " + filetry.getPath());
			in = new Scanner(new File("piMillion.txt"));
			
			
			int countDigits = 0;
			while(in.hasNextLine() && countDigits < n) {
				
				String next = in.nextLine();
				System.out.println(next);
				
				numeratorString += next;
				countDigits += next.length();
	
				
			}
		} catch(Exception e) {
			System.out.println("There was an exception in getting pi!");
			e.printStackTrace();
			System.exit(1);

		} finally {
			in.close();
		}
		BigInteger numerator = new BigInteger(numeratorString);
		
		for(int i=1; i<numeratorString.length(); i++) {
			denumeratorString += "0";
		}
		
		BigInteger denumerator = new BigInteger(denumeratorString);
		
		//System.out.println("pi: " + new Fraction(numerator, denumerator).getDecimalFormat(15));
		return new Fraction(numerator, denumerator);
	}
	
}
