package tanXMissionUseOutsidePrimeChecker;

import java.math.BigInteger;

import UtilityFunctions.UtilityFunctions;

public class BigIntegerPrimesList {

	public static BigInteger listOfPrimes[] = null;
	
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
