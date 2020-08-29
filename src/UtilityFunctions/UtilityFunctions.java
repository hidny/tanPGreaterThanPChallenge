package UtilityFunctions;
import java.math.BigInteger;
import java.util.Comparator;

//TODO: learn to make sieves!
public class UtilityFunctions {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	}

	public static int getSum(int array[]) {
		int sum=0;
		for(int i=0; i<array.length; i++) {
			sum += array[i];
		}
		return sum;
	}

	public static long[][] createPascalTriangle(int size) {
		size = size+1;
		long pascalTriangle[][] = new long[size][size];
		
		for(int i=0; i<size; i++) {
			for(int j=0; j<size; j++) {
				pascalTriangle[i][j] = 0;
			}
		}
		
		pascalTriangle[0][0] = 1;
				
		for(int i=1; i<size; i++) {
			for(int j=0; j<size; j++) {
				pascalTriangle[i][j] = pascalTriangle[i-1][j];
				if(j>0) {
					pascalTriangle[i][j] += pascalTriangle[i-1][j-1];
				}
			}
		}
		
		return pascalTriangle;
	}
	
	public static long[][] createPascalTriangle(int size, long modulo) {
		size = size+1;
		long pascalTriangle[][] = new long[size][size];
		
		for(int i=0; i<size; i++) {
			for(int j=0; j<size; j++) {
				pascalTriangle[i][j] = 0;
			}
		}
		
		pascalTriangle[0][0] = 1;
				
		for(int i=1; i<size; i++) {
			for(int j=0; j<size; j++) {
				pascalTriangle[i][j] = pascalTriangle[i-1][j];
				if(j>0) {
					pascalTriangle[i][j] = (pascalTriangle[i][j] + pascalTriangle[i-1][j-1]) % modulo;
				}
			}
		}
		
		return pascalTriangle;
	}

	public static void printTriangle(long pascalTriangle[][]) {
		String space = "          ";
		
		for(int i=0; i<pascalTriangle.length; i++) {
			for(int j=0; j<pascalTriangle[0].length; j++) {
				System.out.print(pascalTriangle[i][j] + space.substring( ("" + pascalTriangle[i][j]).length()));
				
			}
			System.out.println();
		}
	}

	public static boolean isPrime(long num) {
		if(num<=1) {
			return false;
		}
		
		int sqrt = (int)Math.sqrt(num);
		for(int i=2; i<=sqrt ; i++) {
			if(num%i == 0) {
				return false;
			}
		}
		
		return true;
	}

	public static int[] getDigits(long num) {
		int digits[] = new int[10];
		
		num = Math.abs(num);
		
		//Philosophy: 0 has 1 digit:
		if(num == 0) {
			digits[0] = 1;
			return digits;
		}
		
		while(num>=1) {
			digits[(int)(num%10)]++;
			num = num/10;
		}
		return digits;
	}

	//pre: digits is length 10.
	public static boolean sameDigits(int digits1[], int digits2[]) {
		for(int i=0; i<digits1.length; i++) {
			if(digits1[i]!=digits2[i]) {
				return false;
			}
		}
		return true;
	}
	
	public static long getSmallFactorial(int n) {
		long result = 1;
		for(int i=1; i<=n; i++) {
			result *= i;
		}
		
		return result;
	}
	
	public static BigInteger getBigFactorial(long n) {
		BigInteger result = BigInteger.ONE;
		for(int i=1; i<=n; i++) {
			result = result.multiply(new BigInteger(i + ""));
		}
		
		return result;
	}
	
	
	public static long[] getAllDivisors(long n) {
		long half[] = getFirstHalfDivisors(n);
		long table[];
		boolean perfectSquare;
		
		if(half[half.length - 1] * half[half.length - 1] == n) {
			table = new long[2*half.length-1];
			perfectSquare = true;
		} else {
			table = new long[2*half.length];
			perfectSquare = false;
		}
		
		for(int i=0; i<half.length; i++) {
			table[i] = half[i];
		}
		
		for(int i=0; i< table.length-half.length; i++) {
			if(perfectSquare == false) {
				table[half.length + i] = (long) (n/table[half.length - 1 - i]);
			} else {
				table[half.length + i] = (long) (n/table[half.length - 2 - i]);
			}
		}
		
		return table;
	}
	
	//todo: get second half of divisors with first half.
	public static long[] getFirstHalfDivisors(long n) {
		int numDivisors = 0;
		long sqrt = (long)Math.sqrt(n);
		for(int i=1; i<=sqrt; i++) {
			if(n % i == 0) {
				numDivisors++;
			}
		}
		
		long divisors[] = new long[numDivisors];
		
		int currentIndex = 0;
		for(long i=1; i<=sqrt; i++) {
			if(n % i == 0) {
				divisors[currentIndex] = i;
				currentIndex++;
			}
		}
		
		return divisors;
	}
	
	public static long[] getPrimeDivisors(long n) {
		long nTemp = n;
		long LIMIT = (long)Math.sqrt(n);
		
		int numPrimeDivisors = 0;
		for(long i=2; i<=LIMIT; i++) {
			if(nTemp % i == 0 && isPrime(i)) {
				numPrimeDivisors++;
				while(nTemp % i == 0) {
					nTemp /= i;
				}
			}
		}
		if(nTemp > 1) {
			numPrimeDivisors++;
		}
		
		long divisors[] = new long[numPrimeDivisors];
		
		nTemp = n;
		int currentIndex = 0;
		for(long i=1; i<=LIMIT; i++) {
			if(nTemp % i == 0 && isPrime(i)) {
				divisors[currentIndex] = i;
				currentIndex++;
				while(nTemp % i == 0) {
					nTemp /= i;
				}
			}
		}
		if(nTemp > 1) {
			divisors[currentIndex] = nTemp;
			currentIndex++;
		}
		
		
		return divisors;
	}
	
	public static long[] getPrimeDivisorsOldAndSlow(long n) {
		long nTemp = n;
		int numPrimeDivisors = 0;
		for(long i=2; i<=nTemp; i++) {
			if(nTemp % i == 0 && isPrime(i)) {
				numPrimeDivisors++;
				while(nTemp % i == 0) {
					nTemp /= i;
				}
			}
		}
		
		long divisors[] = new long[numPrimeDivisors];
		
		nTemp = n;
		int currentIndex = 0;
		for(long i=1; i<=nTemp; i++) {
			if(nTemp % i == 0 && isPrime(i)) {
				divisors[currentIndex] = i;
				currentIndex++;
				while(nTemp % i == 0) {
					nTemp /= i;
				}
			}
		}
		
		
		return divisors;
	}
	
	public static boolean sameArrayContents(int a[], int b[]) {
		if(a.length != b.length) {
			return false;
		}
		for(int i=0; i<a.length; i++) {
			if(a[i] != b[i]) {
				return false;
			}
		}
		
		return true;
	}
	
	//My get gcd method that I don't currently use.
	// it works though.
	public static long getGCD(long a, long b) {
		if(b>a) {
			return getGCD(b, a);
		}
		
		long ret = a % b;
		
		if(ret == 0) {
			return b;
		} else{
			return getGCD(b, ret);
		}
	}
	
	public static long getLCM(long a, long b) {
		return (a*b)/getGCD(a, b);
	}
	
	//WARNING: probably not fast than math.sqrt()
	//gets the square root using newtons method: http://mathworld.wolfram.com/NewtonsIteration.html
	public static BigInteger getBigSqrt(BigInteger n) {
		BigInteger iteration = BigInteger.ONE;
		BigInteger next = BigInteger.ONE;
		do {
			iteration = next;
			if(iteration.compareTo(BigInteger.ZERO) == 0) {
				System.out.println("getBigSqrt WTF");
			}
			next = getNextNewtonIteration(n, iteration);
			
		} while(next.subtract(iteration).abs().doubleValue() > 1);
		
		return iteration;
	}
	
	
	//Newton's Iteration
	private static BigInteger getNextNewtonIteration(BigInteger x, BigInteger lastIteration) {
		if(lastIteration.compareTo(BigInteger.ZERO) == 0) {
			System.out.println("getBigSqrt WTF2");
		}
		BigInteger ret = x.divide(lastIteration);
		ret = ret.add(lastIteration);
		ret = ret.divide(new BigInteger("2"));
		
		return ret;
	}
	
	
	  //Another function for the permutation of objects in general. (not just digits)
			//Note that 0 counts as the original ordering.
			//generates an int length numDigits that is the permNumberth permutation
			//ex: numDigits = 3 permNumber = 0
			//return 123
	 public static String[] generatePermutation(String objects[], long permNumber) {
			String result[] = new String[objects.length];
			
			int nthObject;
			
			boolean objectsUsed[] = new boolean[objects.length];
			
			
			for(int i=0; i<objects.length; i++) {
				nthObject = (int)(permNumber / UtilityFunctions.getSmallFactorial(objects.length - 1 - i));
			
				permNumber = permNumber % UtilityFunctions.getSmallFactorial(objects.length - 1 - i);
				
				result[i] = getNthuntakenObject(objects, nthObject, objectsUsed);
				
				
			}
			return result;
		}
		
	//get the nth untaken object and updates the objects taken array:
		private static String getNthuntakenObject(String objects[], int n, boolean objectsTaken[]) {
			int numUntakenSoFar = 0;
			for(int i=0; i<objectsTaken.length; i++) {
				if(objectsTaken[i] == false) {
					if(n == numUntakenSoFar) {
						objectsTaken[i] = true;
						return objects[i];
					}
					numUntakenSoFar++;
					continue;
				}
			}
			System.out.println("WTF. Ran out of permutations.");
			return null;
		}
		
		
		
		//get the next combonation of true values given current array
		public static boolean[] getNextCombination(boolean current[]) {
			/*Example: series of executions:
			 *  11000
				10100
				10010
				10001
				01100
				01010
				01001
				00110
				00101
				00011
			 */
			//we know that we are going to readjust at least 1 element:
			int numToReadjust = 1;
			
			boolean foundSpaceToFill = false;
			
			int spaceToFill;
			
			//this loops counts the number of elements we have to readjust
			//and finds out if there exist a space to fill.
			for(spaceToFill=current.length - 1; spaceToFill>=0; spaceToFill--) {
				if(current[spaceToFill] == false) {
					foundSpaceToFill = true;
					break;
				} else {
					numToReadjust++;
				}
			}
			
			if(foundSpaceToFill) {
				//Find the rightmost 1 that we will have to move to the right:
				int indexToMove;
				for(indexToMove = spaceToFill-1; indexToMove>=0; indexToMove--) {
					if(current[indexToMove] == true) {
						break;
					}
				}
				
				if(indexToMove>=0) {
					current[indexToMove] = false;
					//goal:
					//got from: 00010111
					//to        00001111
					int startInput1s = indexToMove+1;
					int stopInput1s = startInput1s + numToReadjust;
					for(int i=startInput1s; i<stopInput1s; i++) {
						current[i] = true;
					}
					//input 0s for the rest:
					for(int i=stopInput1s; i<current.length; i++) {
						current[i] = false;
					}
					
				} else {
					//This should only happen is we've gone through all of the combinations
					//or there is no element in current that is true.
					return null;
				}
				
			} else {
				//This should only happen if current[] is filled with only true.
				return null;
			}
			
			return current;
		}
		
		
		//TODO: Test this (I only used it for problem 155 so far (and it worked))
		public static Object[] quickSort(Comparable a[], int length) {
			return quickSort(a, 0, length);
		}
		
		private static Object[] quickSort(Comparable a[], int start, int end) {
			int randIndex = start + (int)((end - start) * Math.random());
			if(end - start > 100000) {
				System.out.println(start + " to " + end);
			}
			//Base case: how to sort an array length 1:
			if(end - start <= 1) {
				return a;
			}
			
			swap(a, start, randIndex);
			
			//a[start] is now the pivot...
			
			int leftIndex = start + 1;
			
			int rightIndex = end - 1;
			
			
			while(leftIndex<rightIndex) {
				if((rightIndex - leftIndex) % 100000 == 0) {
					System.out.println(rightIndex - leftIndex);
				}
				if(a[start].compareTo(a[leftIndex]) < 0) {
					swap(a, leftIndex, rightIndex);
					rightIndex--;
				} else {
					leftIndex++;
				}
			}
			
			int pivot;
			if(a[leftIndex] == null || a[start].compareTo(a[leftIndex]) < 0) {
				swap(a, start, leftIndex-1);
				pivot = leftIndex-1;
			} else {
				swap(a, start, leftIndex);
				pivot = leftIndex;
			}
			
			if(pivot == end) {
				System.out.println("WTF!!! pivot = end");
				System.exit(0);
			}
			
			//Hack code to efficiently deal with tables that have many duplicate entries:
			int leftPivot = pivot;
			int rightPivot = pivot;
			while(leftPivot>start && a[leftPivot].compareTo(a[leftPivot - 1]) == 0) {
				leftPivot--;
			}
			while(rightPivot + 1 < end && a[rightPivot].compareTo(a[rightPivot + 1]) == 0) {
				rightPivot++;
			}
			//end hack code.
			
			quickSort(a, start, leftPivot);
			quickSort(a, rightPivot+1, end);
			
			return a;
		}
		
		private static void swap(Comparable a[], int i, int j) {
			Comparable temp = a[i];
			a[i] = a[j];
			a[j] = temp;
		}
		
		
		//I really really really hate BigInteger.
		
		public static BigInteger getAPowerPmodMOD(BigInteger a, BigInteger pow, BigInteger MOD) {
			//base case for power:
			if(pow.compareTo(BigInteger.ZERO) == 0) {
				if(a.compareTo(BigInteger.ZERO) == 0 ) {
					System.out.println("0^0 is I don't know!!!");
					System.exit(1);
				} else if(pow.compareTo(BigInteger.ZERO) < 0 ) {
					System.out.println("No negative powers!" +  a + " to the power of " + pow + "?" );
					System.exit(1);
				}
				return BigInteger.ONE;
			} else if(a.compareTo(BigInteger.ZERO) == 0) {
				return BigInteger.ZERO;
			}
			
			//System.out.println(a + " to the power of " + pow);
			
			int lengthPowTable = 0;
			BigInteger current = BigInteger.ONE;
			while(current.compareTo(pow) <= 0) {
				lengthPowTable++;
				current = current.multiply(new BigInteger("2"));
			}
			
			//System.out.println("Length: " + lengthPowTable);
			
			//Setup the power of 2 table
			BigInteger pow2Table[] = new BigInteger[lengthPowTable];
			pow2Table[0] = a;
			
			
			for(int i=1; i<lengthPowTable; i++) {
				pow2Table[i] = (pow2Table[i-1].multiply(pow2Table[i-1])).mod(MOD);
			}
			//End setup the power of 2 table.
		
			current = pow;
			BigInteger answer = BigInteger.ONE;
			
			
			for(int i=0; i<lengthPowTable && current.compareTo(BigInteger.ZERO) > 0; i++) {
				if(current.mod(new BigInteger("2")).compareTo(BigInteger.ONE) == 0) {
					answer = (answer.multiply(pow2Table[i])).mod(MOD);
					current = current.subtract(BigInteger.ONE);
				}
				current = current.divide(new BigInteger("2"));
			}
			
			return answer;
		}
		

		public static long getNumMultforFactorial(long n, long multiple) {
			long ret = 0;
			for(long current=multiple; current<=n; current=current*multiple) {
				ret += n/current;
			}
			return ret;
		}

		//TODO: test this
		public static boolean[] getBoolListOfPrimesUpToN(int n) {
			boolean primeTableBool[] = new boolean[(int)(n+1)];
			for(int i=0; i<primeTableBool.length; i++) {
				primeTableBool[i] = true;
			}
			primeTableBool[0] = false;
			primeTableBool[1] = false;
			
			for(long i=2; i<primeTableBool.length; i++) {
				if(primeTableBool[(int)i] == true) {
					for(int j=2; i*j<=n; j++) {
						primeTableBool[(int)(i*j)] = false;
					}
				}
			}
			return primeTableBool;
		}
		public static long[] getListofPrimeUpToNLong(int n) {
			//START prob put this in util functions:
			boolean primeTableBool[] = new boolean[(int)(n+1)];
			for(int i=0; i<primeTableBool.length; i++) {
				primeTableBool[i] = true;
			}
			primeTableBool[0] = false;
			primeTableBool[1] = false;
			
			for(long i=2; i<primeTableBool.length; i++) {
				if(primeTableBool[(int)i] == true) {
					for(int j=2; i*j<=n; j++) {
						primeTableBool[(int)(i*j)] = false;
					}
				}
			}
			
			int numPrimes = 0;
			for(int i=0; i<primeTableBool.length; i++) {
				if(primeTableBool[i]) {
					numPrimes++;
				}	
			}
			
			long primeTable[]=  new long[numPrimes];
			int index = 0;
			for(int i=0; i<primeTableBool.length; i++) {
				if(primeTableBool[i]) {
					primeTable[index] = i;
					index++;
				}	
			}
			
			return primeTable;
		}
		
		public static int[] getListofPrimeUpToN(int n) {
			//START prob put this in util functions:
			boolean primeTableBool[] = new boolean[(int)(n+1)];
			for(int i=0; i<primeTableBool.length; i++) {
				primeTableBool[i] = true;
			}
			primeTableBool[0] = false;
			primeTableBool[1] = false;
			
			for(long i=2; i<primeTableBool.length; i++) {
				if(primeTableBool[(int)i] == true) {
					for(int j=2; i*j<=n; j++) {
						primeTableBool[(int)(i*j)] = false;
					}
				}
			}
			
			int numPrimes = 0;
			for(int i=0; i<primeTableBool.length; i++) {
				if(primeTableBool[i]) {
					numPrimes++;
				}	
			}
			
			int primeTable[]=  new int[numPrimes];
			int index = 0;
			for(int i=0; i<primeTableBool.length; i++) {
				if(primeTableBool[i]) {
					primeTable[index] = i;
					index++;
				}	
			}
			
			return primeTable;
		}
		
		public static long getTotient(long n) {
			long primeDiv[];
			primeDiv = UtilityFunctions.getPrimeDivisors(n);
			long ret = n;
			
			for(int i=0; i<primeDiv.length; i++) {
				ret = ret/primeDiv[i] *(primeDiv[i]-1);
			}
			
			return ret;
			
		}
		
		
		//START OF MATRIX FUNCTIONS:
		public static Fraction[] solveMatrixSlow(Fraction matrix[][]) {
			Fraction result[][] = hardCopyMatrix(matrix);
			result = getRowEchelonForm(result);
			result = getReducedRowEchelonForm(result);
			
			Fraction answer[] = new Fraction[result.length];
			
			for(int i=0; i<answer.length; i++) {
				answer[i] = result[i][result[0].length - 1];
			}
			
			return answer;
		}
		
		private static Fraction[][] hardCopyMatrix(Fraction matrix[][]) {
			Fraction copy[][] = new Fraction[matrix.length][matrix[0].length];
			for(int i=0; i<matrix.length; i++) {
				for(int j=0; j<matrix[0].length; j++) {
					copy[i][j] = matrix[i][j];
				}
			}
			
			return copy;
		}
		
		private static Fraction[][] getRowEchelonForm(Fraction matrix[][]) {
			Fraction result[][] = hardCopyMatrix(matrix);
			
			boolean foundPivot = false;
			
			for(int i=0; i<result.length; i++) {
				
				//Make sure the pivot? is non zero...
				if(result[i][i].compareTo(Fraction.ZERO) == 0) {
					
					for(int j=i+1; j<result.length; j++) {
						
						if(result[j][i].compareTo(Fraction.ZERO) != 0) {
							foundPivot = true;
							swapRows(result, i, j);
							break;
						}
					}
				} else {
					foundPivot = true;
				}
				
				if(foundPivot == false) {
					System.out.println("ERROR: couldn't find pivot!");
					System.exit(0);
				}
				
				
				Fraction pivot = result[i][i];
				if(pivot.compareTo(Fraction.ZERO) != 0) {
					result[i] = multiplyRow(result[i], Fraction.divide(Fraction.ONE, pivot));
				
					for(int j=i+1; j<result.length; j++) {
						result[j] = addRows(result[j], multiplyRow(result[i], Fraction.mult(new Fraction(-1, 1), result[j][i])));
					}
				}
			}
			
			return result;
		}
		
		private static Fraction[][] getReducedRowEchelonForm(Fraction matrix[][]) {
			Fraction result[][] = hardCopyMatrix(matrix);
			
			for(int i=0; i<result.length; i++) {
				if(result[i][i].compareTo(Fraction.ONE) == 0) {
					for(int j=i-1; j>=0; j--) {
						result[j] = addRows(result[j], multiplyRow(result[i], Fraction.mult(new Fraction(-1, 1), result[j][i])));
					}
				}
			}
			
			return result;
		}
		
		private static Fraction[] multiplyRow(Fraction matrix[], Fraction mult) {
			Fraction ret[] = new Fraction[matrix.length];
			
			for(int i=0; i<ret.length; i++) {
				ret[i] = Fraction.mult(matrix[i], mult);
			}
			return ret;
		}

		private static Fraction[] addRows(Fraction row1[], Fraction row2[]) {
			Fraction ret[] = new Fraction[row1.length];
			for(int i=0; i<row1.length; i++) {
				ret[i] = Fraction.plus(row1[i], row2[i]);
			}
			return ret;
		}
		
		private static void swapRows(Fraction matrix[][], int i, int j) {
			Fraction tempRow[];
			tempRow = matrix[i];
			matrix[i] = matrix[j];
			matrix[j] = tempRow;
		}
		
		public static void printMatrix(Fraction matrix[][]) {
			for(int i=0; i<matrix.length; i++) {
				for(int j=0; j<matrix[0].length; j++) {
					System.out.print(matrix[i][j] + "   ");
				}
				System.out.println();
			}
		}
		//END OF MATRIX FUNCTIONS
		
		public static Fraction getCombTEST(int n, int r) {
			if(r > n) {
				return new Fraction(0, 1);
			}
			if(n-r < r) {
				r = n-r;
			}
			BigInteger answer = BigInteger.ONE;
			for(int i=0; i<r; i++) {
				answer = answer.multiply(new BigInteger((n-i) + ""));
			}
			
			BigInteger div = BigInteger.ONE;
			for(int i=0; i<r; i++) {
				div = div.multiply(new BigInteger((i+1) + ""));
			}
			
			return new Fraction(answer, div);
		}
		
		
		//dirty trick to get perfect square:
		public static boolean isPerfectSquare(long x) {
			if(((long)Math.sqrt(x)) *((long)Math.sqrt(x)) == x) {
				return true;
			} else {
				return false;
			}
		}
		
		
		
}
