---
- Programming Project #3: Experiments with Hashing
- CS321
- 11/10/2021
- Matthew Kelley
---

OVERVIEW:

Program to test the effectiveness of different hash collision resolution techniques

INCLUDED FILES:

- Hashtable.java - Abstract class for the two different hash tables
- LinearTable.java - Hash table with a single hash
- DoubleHashTable.java - Hash table with double hashing
- HashtableTest.java - Entry point for the program, runs the test
- TwinPrimeGenerator.java - Utility class used to generate a twin prime
- README.md - this file

COMPILING AND RUNNING:

From the directory containing all source files, compile the
driver class with the command:
$ javac HashtableTest.java

Run the compiled class file with the command:
$ java HashtableTest <input type code> <load factor> [<debug level>]

Input Type Code:
1 - randomly generate integers
2 - System.currentTimeMillis()
3 - tokens from word-list.txt

Load Factor:
The number of unique items in the table / the table size
0 < load factor < 1

Debug Level (optional):
0 - Prints summary of the test (default debug level).
1 - prints a summary of the test and outputs contents of the table to a .txt file.
2 - prints summary of the test and gives detail on each insert into table.

Console output will give the results after the program finishes.

PROGRAM DESIGN AND IMPORTANT CONCEPTS:

The entry point for the program is HashtableTest.java, hash table test calls the class Run()
which handles mapping the command line arguments to the desired output.

Run() inserts the specified data into both the linear and double hash table.
Then outputs either a line by line summary of each insert, a summary of the program or
outputs to a text file the content of each table.

The main logic for the program occurs in the abstract class Hashtable. Hashtable contains instance variables of each
of the data points we are trying to track.

LinearTable and DoubleHashTable differ in their insert methods and the hashing functions. The linear table only uses a single
hashing function while double hash utilizes two.

On an insert, the program generates a hash mapping that HashObject to an index in the table. If the spot is occupied by a non-equal
HashObject we iteratively generate another hash off the number of probes that have occurred until we find a free spot in the array.

The goal of hash collision resolution is to reduce the number of probes that occur.

TESTING:

Running diff on the sample output and the output of my program yields a slight difference that I cannot explain.

Comparing the average number of probes in my program to the sample output yields nearly identical results, however, mine has slightly fewer probes than the example. Another phenomenon I cannot explain.

DISCUSSION:

Hashtables yield the benefit of a near-constant search from an array.
To accomplish this task, hash tables generate an index to store the object off hashing its key. Sometimes the hash function for a key will yield a duplicate index causing what is known as a "hash collision".
In this implementation, a hash collision is resolved by inserting the
object into the next available location relative to the original hash.
retrieving an object from a hash table requires us to probe hash the
key of the object, and probe iteratively after the hash index until we
find our desired object.

This resolution tactic is decent, however, as the load factor of the table increases a hash collision becomes more likely. As hash collisions occur and inserts start happening adjacent to their original hash, our objects will start clumping together and in turn, cause more probes for retrieval.

To avoid the clumping problem, we do what is known as double hashing. We space the objects out from one another in a predictable manner based on the second hashing function. This in turn yields far fewer probes to generate an object's actual index.

The proof is in the data. Utilizing a double hashing collision resolution tactic yields far fewer probes than a linear probe.

Input Type 1 (randomly generated number):

| Alpha | Linear    | Double   |
| ----- | --------- | -------- |
| 0.5   | 1.49517   | 1.382245 |
| 0.6   | 1.766594  | 1.532336 |
| 0.7   | 2.140916  | 1.721165 |
| 0.8   | 3.019652  | 2.017499 |
| 0.9   | 5.373382  | 2.55400  |
| 0.95  | 9.837124  | 3.140590 |
| 0.98  | 21.825791 | 4.011132 |
| 0.99  | 50.044615 | 4.705338 |

Input Type 2 (System.currentTimeMillis()):
| Alpha | Linear | Double |
|-------|--------|--------|
| 0.5 | 1.0 | 1.0 |
| 0.6 | 1.0 | 1.0 |
| 0.7 | 1.0 | 1.0 |
| 0.8 | 1.0 | 1.0 |
| 0.9 | 1.0 | 1.0 |
| 0.95 | 1.0 | 1.0 |
| 0.98 | 1.0 | 1.0 |
| 0.99 | 1.0 | 1.0 |

Input Type 3 (word-list.txt):

| Alpha | Linear     | Double   |
| ----- | ---------- | -------- |
| 0.5   | 1.586542   | 1.388279 |
| 0.6   | 2.071109   | 1.531588 |
| 0.7   | 3.433815   | 1.719972 |
| 0.8   | 6.395352   | 2.014746 |
| 0.9   | 18.818865  | 2.568285 |
| 0.95  | 102.240533 | 3.187743 |
| 0.98  | 308.981305 | 4.011750 |
| 0.99  | 457.082365 | 4.651908 |
