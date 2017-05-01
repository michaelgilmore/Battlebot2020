package main

//Prime digit replacements
//Problem 51
//By replacing the 1st digit of the 2-digit number *3, it turns out that six of the nine possible values: 13, 
//23, 43, 53, 73, and 83, are all prime.
//
//By replacing the 3rd and 4th digits of 56**3 with the same digit, this 5-digit number is the first example
//having seven primes among the ten generated numbers, yielding the family: 56003, 56113, 56333, 56443, 56663,
//56773, and 56993. Consequently 56003, being the first member of this family, is the smallest prime with this
//property.
//
//Find the smallest prime which, by replacing part of the number (not necessarily adjacent digits) with the
//same digit, is part of an eight prime value family.

import (
    "fmt"
    "math"
	"strconv"
	"strings"
)

// Only primes less than or equal to N will be generated
const N = 1000000

func isCandidate(prime int) bool {
	s := strconv.Itoa(prime)
	if b := strings.Contains(s, "0"); b == true {
		return true
	}
	if b := strings.Contains(s, "1"); b == true {
		return true
	}
	if b := strings.Contains(s, "2"); b == true {
		return true
	}
	return false
}

func checkPrimeFamilies(prime_flags [N]bool, prime int) {

}

func main() {
    var x, y, n, candidate_count int
    nsqrt := math.Sqrt(N)

    is_prime := [N]bool{}

    for x = 1; float64(x) <= nsqrt; x++ {
        for y = 1; float64(y) <= nsqrt; y++ {
            n = 4*(x*x) + y*y
            if n <= N && (n%12 == 1 || n%12 == 5) {
                is_prime[n] = !is_prime[n]
            }
            n = 3*(x*x) + y*y
            if n <= N && n%12 == 7 {
                is_prime[n] = !is_prime[n]
            }
            n = 3*(x*x) - y*y
            if x > y && n <= N && n%12 == 11 {
                is_prime[n] = !is_prime[n]
            }
        }
    }

    for n = 5; float64(n) <= nsqrt; n++ {
        if is_prime[n] {
            for y = n * n; y < N; y += n * n {
                is_prime[y] = false
            }
        }
    }

    is_prime[2] = true
    is_prime[3] = true

	fmt.Println("counting up")
	
    primes := make([]int, 0, 1270606)
    for x = 0; x < len(is_prime)-1; x++ {
        if is_prime[x] {
            primes = append(primes, x)
			if cand := isCandidate(x); cand == true {
				candidate_count += 1
			}
			//fmt.Println(x)
        }
    }

	fmt.Println("candidate_count=")
	fmt.Println(candidate_count)

	checkPrimeFamilies(is_prime, x)
}
