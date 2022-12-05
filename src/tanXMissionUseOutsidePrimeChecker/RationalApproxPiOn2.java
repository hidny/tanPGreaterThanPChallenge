package tanXMissionUseOutsidePrimeChecker;

import java.math.BigInteger;

import UtilityFunctions.Fraction;


public class RationalApproxPiOn2 {

	public static final int ONE_THOUSAND = 1000;
	public static final int HALF_ONE_THOUSAND = 500;

	public static final Fraction TWO = new Fraction(2, 1);
	
	//The numerator I use for PI should be at least 3 times the length of the numerator of the rational approx to pi: 
	public static final double PI_PRECISION_FACTOR = 4.0;
	
	
	public static int NUM_CPUS = 4;
	public static boolean useIterationIndex[] = new boolean[NUM_CPUS];
	
	public static void setRunningCPUIndex(int index) {
		useIterationIndex[index % NUM_CPUS] = true;
		ExtremeTanXCalculator.tmpFileName += "" + index;
	}
	
   public static void main(String[] args) {

	  // check if we're split the work by CPU...
	  int numCpusUsed = 0;
	  for(int i=0; i<useIterationIndex.length; i++) {
		  if(useIterationIndex[i]) {
			  numCpusUsed++;
		  }
	  }
	  
	  if(numCpusUsed == 0) {
		  for(int i=0; i<useIterationIndex.length; i++) {
			  useIterationIndex[i] = true;
		  }
	  }
	  
	  ExtremeTanXCalculator.intializeFile();

      int prevSizeDebug = -1;
	 
      Fraction left  = new Fraction(0, 1);
      Fraction right = new Fraction(1, 0);

      int numDigitsApproxPi = ONE_THOUSAND;
      Fraction currentPrecisePiOver2 = Fraction.divide(PI.getPiForNDigits(numDigitsApproxPi), TWO);
    
      System.out.println("Length precise pi: " + currentPrecisePiOver2.getNumerator().toString().length());
      System.out.println("Using PI precision factor " + PI_PRECISION_FACTOR);
      
      ExtremeTanXCalculator.initializeListOfPrimes();
      
      int numIterApproachingFromLeft = 0;
      
      for(int i=0; left.getNumerator().toString().length() < 150*ONE_THOUSAND; i++) {
 
         // compute next possible rational approximation of
    	  // A/B = pi/2 where A&B coprime, A/B < pi/2, and B is odd.

    	  //Instead of just finding the next mediant, find the one that's right before a streak
    	  // of left mediants or right mediants:
    	  //left mediant steak amount = floor(tmp1)
          BigInteger tmp1 = left.getDenominator().multiply(currentPrecisePiOver2.getNumerator())
          .subtract(left.getNumerator().multiply(currentPrecisePiOver2.getDenominator()));

    	  //left mediant steak amount = floor(tmp2)
          BigInteger tmp2 = right.getNumerator().multiply(currentPrecisePiOver2.getDenominator())
                  .subtract(right.getDenominator().multiply(currentPrecisePiOver2.getNumerator()));
          
          
    	  if (tmp2.compareTo(tmp1) > 0) {
            
            long n1 =tmp2.divide(tmp1).longValue();

            System.out.println("n1: " + n1);
            
            //Do all the mediant steps from the right in row when we can:
            right = new Fraction((left.getNumerator().multiply(new BigInteger(n1 + ""))).add(right.getNumerator()),
            		(left.getDenominator().multiply(new BigInteger(n1 + ""))).add(right.getDenominator()));

            
    	  }else{

            long n2 = tmp1.divide(tmp2).longValue();
            System.out.println("n2: " + n2);

        	numIterApproachingFromLeft++;

	      	if(numIterApproachingFromLeft % 1000 == 0) {
	      	  System.out.println("Num iterations of combo-mediants: " + numIterApproachingFromLeft);
	      	  if(numIterApproachingFromLeft >= 100000) {
	      		numIterApproachingFromLeft = 0;
	      	  }
	      	}
	      	

        	BigInteger D = right.getDenominator();
        	
	      	//Split the work by CPU index
	      	//(so I could run this program on multiple CPUs at once)
            if(useIterationIndex[numIterApproachingFromLeft % NUM_CPUS]) {
	            
            	System.out.println("**Seach for solution here**");
    	      	
            	Fraction trial = null;
	            
        		
	            for(int j=0; j<n2; j++) {
	            	

	            	BigInteger curA = (right.getNumerator().multiply(new BigInteger((n2 - j) + ""))).add(left.getNumerator());
	            	BigInteger curB = (right.getDenominator().multiply(new BigInteger((n2 - j) + ""))).add(left.getDenominator());

	            	
	            	if(j > 0) {
	            		
	            		//I worked out this formula from pen & paper:
	            		//I found that:
	            		//If tan(A) > B
	            		// and (A+kC)/(B+kD) is also a mediant (where C/D is the mediant on the right side)
	            		// then: k*A < B + k*D
	            		// Therefore: A < B/k + D (divide k)
	            		//lazy approx:
	            		// Therefore A < B + D (don't divide by k because lazy)
	            		
	            		
	            		//But what if (A+kC)/(D+kD) simplifies and tan((A+kC)/s) > (A+kC)/s?
	            		//Answer: It doesn't simplify! See: Stern–Brocot trees
	            		//"The Stern-Brocot tree provides an enumeration of all positive rational numbers via mediants in lowest terms, obtained purely by iterative computation of the mediant according to a simple algorithm."
	            		//https://en.wikipedia.org/wiki/Mediant_(mathematics)
	            		
	            		if(curA.compareTo(curB.add(D) ) > 0) {
	            			break;
	            		}
	            		System.out.print("J past the rough formula: " + j);
	            	}
	            	
	            	trial = new Fraction(curA, curB);   		;
	            	
	            	
	            	//AX + B(pi/2) < 0 but B has to be odd or else tanX will be a very small number...
	            	if(trial.getDenominator().mod(new BigInteger("2")).equals(BigInteger.ONE)) {
	            		
	            		ExtremeTanXCalculator.attemptTanXCheckUsePiApproxNoDoublePiOn2(trial, currentPrecisePiOver2, j);
	            		
	            	} //End of check X in rang

	            } //End of FOR loop
	            
            }//END of IF iteration index
           
            BigInteger nextA = (right.getNumerator().multiply(new BigInteger(n2  + ""))).add(left.getNumerator());
        	BigInteger nextB = (right.getDenominator().multiply(new BigInteger(n2 + ""))).add(left.getDenominator());

            left =  new Fraction(nextA, nextB);
        	
            
    	 } // End of approaching the mediants on the left

    	  
    	  //DEBUG print after size of A in A/B increases:
		 if(prevSizeDebug < left.getNumerator().toString().length()) {
	
		  	 int numeratorSize = left.getNumerator().toString().length();
	    	 System.out.println("New numerator size: " + numeratorSize);
	    	 prevSizeDebug =  left.getNumerator().toString().length();
	    	 
	    	 if(PI_PRECISION_FACTOR * numeratorSize > numDigitsApproxPi) {
	    		 numDigitsApproxPi += ONE_THOUSAND;
	    		 currentPrecisePiOver2 = Fraction.divide(PI.getPiForNDigits(numDigitsApproxPi), TWO);
	    		 
	    		 System.out.println("Upgrading size of pi guide...");
	    		 
	    		 if(numDigitsApproxPi % (10 * ONE_THOUSAND) == 0) {
	    			 printCurrentPoint(left, right);
	    		 }
	    	 }
	    	 
		 }
      }

   }
      


   public static void printCurrentPoint(Fraction left, Fraction right) {
 	  System.out.println("Checkpoint:");
 	  System.out.println(left.getNumerator().toString());
 	  System.out.println("------------------------------");
 	  System.out.println(left.getDenominator().toString());
 	  System.out.println();
 	  System.out.println();
 	  System.out.println(right.getNumerator().toString());
 	  System.out.println("------------------------------");
 	  System.out.println(right.getDenominator().toString());
 	  System.out.println("end Checkpoint");
   }
}

//Unsettling thoughts I didn't address:
   //TODO: what if I do (2*a+c)/(2*b+d)
  //What if I did mediant between 1 and 3.5? Would I find other solutions?
  //what if I trial less ideal candidates?
  
   
   /*
    * 
Samuel Li
1 day ago
It has 1017 digits, the first 10 of which are 2308358707.

    */
   //What I found:
   //Found X = 230835870782558831561617186504559084198719501221763995608082253627620752053749345488376393822837250198036536001853828659466202612019525543362322174085744303421231446484541625047630462908919109308644634605051209877750956648014568322183373423523622941806761765245932401727973436579786298208782013178059220103271409347616696556052706562092799953175234183483071403726145726928572372071037042523626350312132351311366806233135093893271182587352730075523143635168510803804031460442796778933680674070124730971307185688425634077096234482442639666385695677866015904370207368846631450100939158029908242779848800640038255592227473300237596577845602369215568916732445980431078426390412264603773550384039765410088966381694110344811198325354315338629604946794192217817288101344643511450133142277670683067655250506551517767422160650566385017503208608678491109517443585115317845289832567015746473548492179557935154400719019569904865219030736244089287736334048402066257337090606092966121806567484954460809024219605952851728610326005069 where tan X = Infinity

   //TODO: You need to have the first 100 K digits of Pi to attempt it, but here it is:

   /*
    * 
Samuel Li
1 day ago
In addition to the 1017-digit prime that several people have sent you by now, I've just found a 35,085 digit prime with tan(p) > p. The first 10 digits are 4094619989.
*/
   
   
   
   
   /* Youtube conv: Aug 29th, 2020:
    * 
Erin2718
1 week ago
I have been well and truly nerd-sniped, and have yet to find another prime with p < tan(p) - Will update if I get one.

164


Justin Murtagh
Justin Murtagh
1 week ago
I've checked a few hundred million numbers. Haven't found one yet

3


Brian Clark
Brian Clark
1 week ago
Very good, impatiently awaiting a second.

1


Σ5
Σ5
1 week ago
What about tan(n) > n^2
Or tan(n) > exp(n)

1


spyc nub
spyc nub
1 week ago
@Σ5 To answer the question tan(n) > n^2: (n is in radians)
I checked for all positive integers with 8 digits or less. 
The only solution (I believe) is n = 1, with tan(1) = 1.557 and 1^2 = 1.
But if you take |tan(n)| < n^2, then n = 11 is another solution.
I will run my program again for bigger numbers, but at night.



Stand-up Maths
Stand-up Maths
1 week ago
No spoilers but I can confirm that another one exists and so far two people have found it. Good luck!

53


Erin2718
Erin2718
1 week ago
@Stand-up Maths Can I have a vague order of magnitude just to see if I should bother trying with my potato computer?

6


J. Frank Mortimer
J. Frank Mortimer
1 week ago
@Justin Murtagh A linear search won't get you a 46-digit number

3


Bill Kong
Bill Kong
1 week ago
Any published lists of exact large primes?



Samuel Li
Samuel Li
1 week ago
It has 1017 digits, the first 10 of which are 2308358707.

7


Bill Cook
Bill Cook
1 week ago
Did you test 37? That’s always a good number to test.

7


J L
J L
1 week ago
@Σ5 For all integers n, we will always have |tan(n)| < 2 ( (2n+2)/pi )^41 (proof below).
 Since exp(n) is greater than this once n>200, there will be no solutions to |tan(n)| > exp(n) once n goes past 200. It's easy enough to check the first 200 values of n and see that there are no solutions, so there are no positive integers n such that |tan(n)| > exp(n).

Proof: This uses a result by Mahler (see equation (15) in https://carma.newcastle.edu.au/resources/mahler/docs/119.pdf ): if p/q is any rational number, then the distance of p/q from pi is at least 1/q^42.



Suppose n is very close to pi/2+k*pi (as the video explains). Using the bound |tan(pi/2 - x)|<1/|x| for x close to 0 (this follows from |tan(x)|>|x| and the identity tan(x)=1/tan(pi/2-x)), we have

|tan(n)| = |tan(pi/2 - (pi/2 + k*pi - n))|
< 1/|pi/2 + k*pi - n|
= (2/(2k+1)) / |pi - 2n/(2k+1)|
< (2/(2k+1)) (2k+1)^42
= 2 (2k+1)^41.

Now since we were using a value of n that's close to pi/2+k*pi, we must certainly have pi/2 + k*pi < n+1. This implies 2k+1 < (2/pi)(n+1), giving us the desired result.

6


Hu Go
Hu Go
1 week ago
@J. Frank Mortimer Well, depends on where you start.

1


Samuel Li
Samuel Li
1 week ago
@Stand-up Maths I've just found a third solution with 35085 digits, the first 10 of which are 4094619989.

5


J L
J L
1 week ago
@Samuel Li wow, nice find! How did you test for primality? I haven't heard of any general-purpose primality testers that have worked for primes beyond around 15k-20k digits

1


J L
J L
1 week ago
@Samuel Li (for anyone who wants to try verifying it for themselves, his solution can be found as the numerator of the 67,985th convergent of pi/2, or as half the numerator of the 68,199th convergent of pi.

The next solution will likely have around a million digits, so good luck finding that one haha)

1


Samuel Li
Samuel Li
1 week ago (edited)
@J L For the 1017-digit solution, I used gmpy2, but it started slowing down excessively at around ~20k digits. After that, I switched to writing the prime to a file and calling OpenPFGW, which is a bit hacky but much faster. Both of these are of course probable prime tests (full proof is way too slow at these sizes).

2


GEL
GEL
1 week ago
I have a rough heuristic which says that the ratio of the number of digits of successive prime solutions should be around exp(pi) on average, or about 23. If you have one with 35k digits, then I expect we need around 800k digits for the next. But, it is a random heuristic, you could get lucky, or maybe be unlucky and have another factor of 23.



Samuel Li
Samuel Li
1 week ago
@GEL It appears we got lucky: the next known solution has 43,176 digits, the first 10 of which are 1086855570. (This one wasn't found by me, my method skipped over it somehow.)

1


J. Frank Mortimer
J. Frank Mortimer
5 days ago
@Samuel Li I also think that we expect each solution to be about e^pi times the number of the digits of the previous, so the 43176-digit solution might be more more suspicious and less lucky. Any idea why toyr coded didn;t find that number? Can you estimate the chance of finding a fourth value as "small" as 43K digits to see if this is statistically reasonable? Also, did you manage to check this number yourself?



Vilim Lendvaj
Vilim Lendvaj
15 hours ago
@Samuel Li Are you sure you didn't skip over it because your method found that it's actually composite? We are talking about only probably-prime numbers after all.



Samuel Li
Samuel Li
2 hours ago
I have verified that the 43176 digit solution is indeed prime and satisfies tan(p) > p. I believe the person who found it was searching through convergents of pi/2 instead of pi, which behave similarly but not exactly the same. For what it's worth, I've checked up to 120,000 digits and found no other solutions.



*/