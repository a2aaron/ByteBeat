package math;

public class PrimeGenerator {
	int currentPrime;
	
	public PrimeGenerator() {
		currentPrime = 2;
	}
	
	public PrimeGenerator(int start) {
		if (start < 2) {
			throw new IllegalArgumentException("PrimeGenerator expects a positive integer value above one.");
		}
		currentPrime = start;
	}
	
	public int nextPrime() {
		currentPrime++;
		
		while (!isPrime(currentPrime)) {
			currentPrime++;
		}
		
		return currentPrime;
	}
	
	public int previousPrime() {
		if (currentPrime == 2) {
			return currentPrime;
		}
		currentPrime--;
		
		while (!isPrime(currentPrime)) {
			currentPrime--;
		}
		
		return currentPrime;
	}
	
	public static boolean isPrime(int n) {
		if (n % 2 == 0 || n < 1) {
			return false;
		}

		if (n == 2) {
			return true;
		}
		
		for (int i = 3; i * i <= n; i += 2) {
			if (n % i == 0) {
				return false;
			}
		}

		return true;
	}
}
