package tanXMissionUseOutsidePrimeChecker;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

import UtilityFunctions.Fraction;

/* youtube comment: https://www.youtube.com/watch?v=A7eJb8n8zAw
 //What is the biggest tangent of a prime?
 * Moritz Ernst Jacob
19 hours ago (edited)
There's actually a way to generate those hits efficiently:
1. Get a rational approximation for pi = a/b, where a is even.
2. Your magic number x with tan(x) = large is calculated as: x = a*(k+1/2).
3. Cranking up k will make the approximation worse and worse, so at some point you'll have to find the next rational approximation for pi to generate more numbers.

4. Regarding primality: Since a has to be even, multiplying by (k+1/2) will always result in a composite number for k>0. So for each family of magic numbers, only the very first can be prime. Or in other words: only those apprimations of pi where the numerator is 2*prime will give us primes in this process.


 */

/*Update youtube comment:
 * Samuel Li
Samuel Li
1 day ago
It has 1017 digits, the first 10 of which are 2308358707.
 */

//TODO: rename class name:
public class ExtremeTanXCalculator {

	
	public static String tmpFileName = "testNumbers.txt";
	public static PrintWriter writer = null;

	public static void initializeListOfPrimes() {
		BigIntegerSmallPrimesList.initialize();
	}
	
	public static void intializeFile() {
		try {
			writer = new PrintWriter(new File(tmpFileName));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static final BigInteger TWO = new BigInteger("2");
	
	public static Process process = null;
	
	public static boolean attemptTanXCheckUsePiApproxNoDoublePiOn2(Fraction piOn2ApproxToDeriveX, Fraction currentPrecisePiOn2, int j) {

			BigInteger X = piOn2ApproxToDeriveX.getNumerator();

			//Quickly filter filter out non-primes O(n)
			if(BigIntegerSmallPrimesList.isProbPrime(X) == false) {
				return false;
			}

			//Filter out cases where tan(X) < X:
			if(XisWithinRange(piOn2ApproxToDeriveX, currentPrecisePiOn2) == false) {
				return true;
			}
			
			//I have yet to see j -> 2 or higher, but couldn't disprove it's existence
			//I did convince myself that it would be rare though...
			System.out.println("j -> " + j);
			
			//Probable prime check:
			
			//Used external program to do a primality test.
			//It's around 10x faster than the implementation I came up with.
			//I used Samual Li's program. (If you can't beat 'em, join 'em)
			// https://samuelj.li/blog/2020-08-20-prime-tangents/
			try {
				
				/*boolean foundProbPrime = false;
				if(process != null) {
					InputStream is = process.getInputStream();
					InputStreamReader isr = new InputStreamReader(is);
					BufferedReader br = new BufferedReader(isr);
					String line;

					while ((line = br.readLine()) != null) {
					  System.out.println(line);
					  if(line.contains("is 3-PRP!")) {
						  foundProbPrime = true;
					  }
					}
				}
				
				if(foundProbPrime) {
					System.out.println("Found probably prime!");
				}*/
				
				
				printNumberToFile(X);
				process = new ProcessBuilder("C:\\Users\\Michael\\Desktop\\pfgw_win_4.0.1\\distribution\\pfgw64.exe",tmpFileName).start();
				
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return false;
	}
	
	public static void printNumberToFile(BigInteger number) 
			  throws IOException {
		
				System.out.println("Printing to file");
			    
			    writer.write(number.toString() + "\n");
			    writer.flush();
			    
			}
	
	//O(n^2) check if tanX > X
	public static boolean XisWithinRange(Fraction piOn2ApproxToDeriveX, Fraction currentPrecisePiOn2) {
		
		Fraction A = new Fraction(piOn2ApproxToDeriveX.getNumerator(), BigInteger.ONE);
		Fraction B = new Fraction(piOn2ApproxToDeriveX.getDenominator(),  BigInteger.ONE);
		
		Fraction OneOverA = Fraction.divide( Fraction.ONE, A);
		
		Fraction Apart = Fraction.plus(A, OneOverA);
		
		
		Fraction BPiOver2 = Fraction.mult(B, currentPrecisePiOn2);
		
		//23083587078255883156161718650455908419871
		if(Fraction.minus(Apart, BPiOver2).greaterThan0()) {

			System.out.println(A.getNumerator() + " + " + OneOverA.getDecimalFormat(15));
			System.out.println("vs " + B.getNumerator() + " * (pi/2) = (" + BPiOver2.getDecimalFormat(15) +")");
			
			System.out.println("Got passed fast Check!");
			System.out.println("Factor LHS/RHS: "  + Fraction.divide(A, BPiOver2).getDecimalFormat(15));
			return true;
		}
		
		return false;
	}


	//TODO: Repurpose this function to double check tan p > p:
	//This function isn't being used because I found a faster check
	//This is O(n^3) and the faster check is O(n^2)
	
	//Function to double check solutions after I found them:
	//pre: x is near pi.
	//post: return 1 /cos x
	//if x is near pi, then tan x = sin x / cos x almost = 1 /cosx
	public static Fraction tanApproxAtPiOver2(Fraction x, Fraction cosGoalNumber, Fraction currentPrecisePiOn2) {
		
		//Make up some precision:
		int numDigitsPrecision = 3 * cosGoalNumber.getDenominator().toString().length();
		//End make up some precision
		
		return Fraction.divide(Fraction.ONE, cosApprox(x, cosGoalNumber, currentPrecisePiOn2, numDigitsPrecision));
	}
	
	
	
	//I made it figure out when it has enough info to just stop so it could go slightly faster.	
	public static Fraction cosApprox(Fraction x, Fraction cosGoalNumber, Fraction currentPrecisePiOn2, int numDigitsPrecision) {
		
		if(Fraction.minus(x, currentPrecisePiOn2).greaterThan0() == true) {
			
			System.out.println("ERROR: in cosApprox: X seems slightly too big (i.e. tan x is negative). Don't do that!");

			System.exit(1);
			return Fraction.ONE;
		}
		
		//xSquared should be just smaller than pi/2...
		Fraction xSquared = Fraction.mult(x, x);
		
		Fraction xPowerN = Fraction.ONE;
		
		Fraction output = Fraction.ONE;
		Fraction factorial = Fraction.ONE;
		
		System.out.println("Cos of : " + x.getDecimalFormat(20));
		
		for(int i=1; true; i++) {
			System.out.println(i);
			
			factorial = Fraction.mult(factorial, new Fraction(i, 1));
			factorial = approx(factorial, numDigitsPrecision);
			
			if(i %2 == 0) {
				boolean signIsPositive = false;
				if(i % 4 == 0) {
					signIsPositive = true;
				} else {
					signIsPositive = false;
				}

				//TODO: approx based on size of factorial... (nah...)
				xPowerN = Fraction.mult(xPowerN, xSquared);
				xPowerN = approx(xPowerN, numDigitsPrecision);
				
				
				Fraction currentTerm =  Fraction.divide(xPowerN, factorial);

				//Approximate a little bit:
				//truncate term to be "only" 1000+ digits in numerator
				currentTerm = approx(currentTerm, numDigitsPrecision);
				
				if(signIsPositive) {
					output = Fraction.plus(output, currentTerm);
				} else {
					output = Fraction.minus(output,  currentTerm);
				}
				
				
				//Cut short but no guarantee that it's above 0.
				if(signIsPositive) {
					if( Fraction.minus(cosGoalNumber, output).greaterThan0()) {
						System.out.println("Early stop 1");
						return output;
						
					}
				} else {
					if( Fraction.minus(cosGoalNumber, output).greaterThan0() == false) {
						System.out.println("Early stop 2");
						return output;
					}
				}
				
				
				System.out.println("i = " + i + ": " + output.getDecimalFormat(40));
				
			}
			
		}
		
		
		
	}
	
	public static Fraction approx(Fraction input, int limitDenomSize) {
		BigInteger numeratorTerm = input.getNumerator();
		BigInteger denominatorTerm = input.getDenominator();
		
		if(numeratorTerm.toString().length() > limitDenomSize) {
			
			int digitsCut = numeratorTerm.toString().length() - limitDenomSize;
			
			System.out.println("Cutting " + digitsCut + " digits of the term's fraction");
			
			String numTermString = numeratorTerm.toString();
			String denomTermString = denominatorTerm.toString();
			
			numeratorTerm = new BigInteger(numTermString.substring(0, numTermString.length() - digitsCut));
			denominatorTerm = new BigInteger(denomTermString.substring(0, denomTermString.length() - digitsCut));
			
			input = new Fraction(numeratorTerm, denominatorTerm);
		}
		
		return input;
	}
	
	
	//Find close enough fractions:
	//(K1a+K2c)/(K1c+K2d)
	//(Not best approx... but passes tan x > x test
	
}
