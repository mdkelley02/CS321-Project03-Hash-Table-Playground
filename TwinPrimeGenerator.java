/**
 * Utility class for generating a twin prime within a certain range.
 * 
 * @author Matthew Kelley
 * @date Mon Nov 8 13:57:44 MST 2021
 */

public class TwinPrimeGenerator {
    /**
     * Determines whether integer n is prime or composite.
     * 
     * @param n - The integer in question.
     * @return true if n is prime, false if n is composite.
     */
    private static boolean isPrime(int n) {
        if (n <= 1)
            return false;
        else if (n == 2)
            return true;
        else if (n % 2 == 0)
            return false;

        for (int i = 3; i <= Math.sqrt(n); i += 2) {
            if (n % i == 0)
                return false;
        }
        return true;
    }

    /**
     * Generates the second prime of the smallest twin prime pair in the provided
     * range.
     *
     * @param lowerBound - lower bound that twin prime pair resides in.
     * @param upperBound - upper bound that twin prime pair resides in.
     * @return -1 if there does not exist a twin prime in the range. Otherwise, the
     *         first integer of the smallest twin prime pair in the provided range.
     */
    public static int generateSmallestTwinPrimeInRange(int lowerBound, int upperBound) {
        while (lowerBound <= upperBound) {
            if (isPrime(lowerBound) && isPrime(lowerBound + 2)) {
                return lowerBound + 2;
            }
            lowerBound++;
        }
        return -1;
    }
}
