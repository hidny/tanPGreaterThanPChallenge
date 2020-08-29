package tanXMissionUseOutsidePrimeChecker;

import java.math.BigInteger;

import UtilityFunctions.UtilityFunctions;

public class BigIntegerSmallPrimesList {

	public static BigInteger listOfPrimes[] = null;
	

	//Samuel Li recommended the quick check if X is prime should only check with primes up to 10^6.
	//A bigger number might be better when the number of digits of X is high, but 
	//I like seeing the program solve the small digits quickly...
	//Let me be emotional...
	//https://samuelj.li/blog/2020-08-20-prime-tangents/
	
	public static void initialize() {
		int primes[] = UtilityFunctions.getListofPrimeUpToN(1000000);
		
		listOfPrimes = new BigInteger[primes.length];
		for(int i=0; i<listOfPrimes.length; i++) {
			listOfPrimes[i] = new BigInteger(primes[i] + "");
		}
		
	}
	
	public static boolean isProbPrime(BigInteger n) {
		
		for(int i=0; i < listOfPrimes.length; i++) {
			if(n.remainder(listOfPrimes[i]).equals(BigInteger.ZERO)) {
				return false;
			}
		}
		
		return true;
		
	}

	public static boolean isProbPrime(BigInteger n, int numPrimesToCheck) {
		
		for(int i=0; i < numPrimesToCheck; i++) {
			if(n.remainder(listOfPrimes[i]).equals(BigInteger.ZERO)) {
				return false;
			}
		}
		
		return true;
		
	}
}
