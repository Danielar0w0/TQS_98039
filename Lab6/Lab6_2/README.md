The project passed the defined quality gate.
However, the project still has one critical bug (rank D severity), and a security hotspot that is 0% reviewed (rank E severity).

Here, we analyze the results of Sonar analysis, along with discussing possible solutions to fix the problems found.

# Bugs

## Problem
Save and re-use of "Random generator = new Random();".

## Description
Creating a new Random object each time a random value is needed is inefficient and may produce numbers which are not random depending on the JDK.
For better efficiency and randomness, create a single Random, then store, and reuse it.

The Random() constructor tries to set the seed with a distinct value every time.
However, there is no guarantee that the seed will be random or even uniformly distributed. 
Some JDK will use the current time as seed, which makes the generated numbers not random at all.
This rule finds cases where a new Random is created each time a method is invoked.

## Solution
private Random rand = SecureRandom.getInstanceStrong();  // SecureRandom is preferred to Random

public void doSomethingCommon() {
int rValue = this.rand.nextInt();
//...

# Vulnerabilities
No found vulnerabilities.

# Security hotspots

## Problem
Lack of methods to ensure that using the pseudorandom number generator is safe ("Random generator = new Random();").

## Description
Using pseudorandom number generators (PRNGs) is security-sensitive. For example, it has led in the past to the following vulnerabilities:
CVE-2013-6386
CVE-2006-3419
CVE-2008-4102

When software generates predictable values in a context requiring unpredictability, it may be possible for an attacker to guess the next value that will be generated, and use this guess to impersonate another user or access sensitive information.

As the java.util.Random class relies on a pseudorandom number generator, this class and relating java.lang.Math.random() method should not be used for security-critical applications or for protecting sensitive data. In such context, the java.security.SecureRandom class which relies on a cryptographically strong random number generator (RNG) should be used in place.

## Solution
Use a cryptographically strong random number generator (RNG) like "java.security.SecureRandom" in place of this PRNG.
Use the generated random values only once.
You should not expose the generated random value. If you have to store it, make sure that the database or file is secure.

---
SecureRandom random = new SecureRandom(); // Compliant for security-sensitive use cases
byte bytes[] = new byte[20];
random.nextBytes(bytes);

# Major code smell

## Problem
The return type of this method should be an interface such as "List" rather than the implementation "ArrayList".

## Description

A for loop stop condition should test the loop counter against an invariant value (i.e. one that is true at both the beginning and ending of every loop iteration). Ideally, this means that the stop condition is set to a local variable just before the loop begins.

Stop conditions that are not invariant are slightly less efficient, as well as being difficult to understand and maintain, and likely lead to the introduction of errors in the future.

This rule tracks three types of non-invariant stop conditions:

When the loop counters are updated in the body of the for loop
When the stop condition depend upon a method call
When the stop condition depends on an object property, since such properties could change during the execution of the loop.

# Solution
for (int i = 0; i < 10; i++) {...}

