package testing;

import java.math.BigInteger;
import java.util.Scanner;

import UtilityFunctions.Fraction;
import tanXMissionUseOutsidePrimeChecker.ExtremeTanXCalculator;
import tanXMissionUseOutsidePrimeChecker.PI;

public class testMediants {

	public static int ONE_THOUSAND = 1000;
	public static int PI_PRECISION_FACTOR = ONE_THOUSAND;
	
	public static int TWO = 2;
	
	public static void main(String[] args) {

		  Fraction left  = new Fraction(0, 1);
	      Fraction right = new Fraction(1, 0);

	      int numDigitsApproxPi = ONE_THOUSAND;
	      Fraction currentPrecisePiOver2 = Fraction.divide(PI.getPiForNDigits(numDigitsApproxPi), TWO);
	    
	      System.out.println("Length precise pi: " + currentPrecisePiOver2.getNumerator().toString().length());
	      System.out.println("Using PI precision factor " + PI_PRECISION_FACTOR);
	      
	      //ExtremeTanXCalculator.initializeListOfPrimes();
	      
	      Scanner in = new Scanner(System.in);
	      
	      for(int i=0; left.getNumerator().toString().length() < 6 * 1000; i++) {
	    	  
	    	  Fraction mediant = new Fraction(left.getNumerator().add(right.getNumerator()),
	            		left.getDenominator().add(right.getDenominator()));

	    	  if(Fraction.minus(mediant, currentPrecisePiOver2).greaterThan0()) {
	    		  
	    		  right = mediant;
	    	  } else {
	    		  left = mediant;
	    	  }
	    	  
	    	  System.out.println("Predict 1:");
	    	  BigInteger tmp = right.getNumerator().multiply(left.getDenominator())
	    			  .subtract(left.getNumerator().multiply(right.getDenominator()));
	    	  System.out.println(tmp);
	    	  if(tmp.compareTo(BigInteger.ONE) != 0) {
	    		  System.out.println("ERROR: didn't get 1! Test1 ");
	    		  System.exit(1);
	    	  }
	    	  
	    	  System.out.println("New mediant:");
	    	  System.out.println(mediant.getNumerator().toString());
	    	  System.out.println("----------------------");
	    	  System.out.println(mediant.getDenominator());
	    	  System.out.println();
	    	  System.out.println();
	    	  System.out.println();
	    	  
	    	  //in.next();
	      }
	}

}
