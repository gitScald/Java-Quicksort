 * Author:		Benjamin VIAL
 * ID:			29590765
 * Course:		COMP 352
 * Instructor:		Stuart THIEL
 * Due date:		Monday, May 29th, 2017

 Implementation of the quicksort algorithm
 =========================================

Archive contents:
	- gen: 		Contains class definitions for RandomGen and FixedGen
	- sort: 		Contains class definitions for QSNormal and QSMedian
	- QSDriver: 		Class definition for quicksort driver class
	- qs_test.csv: 		CSV file with the running times (in nanoseconds) of QSNormal and QSMedian with both randomized and pathological inputs
	- quicksort_time.png: 	Chart with the running times contained in qs_test.csv for easy visualization

Usage:
	java QSDriver <sort> <gen> <length> <seed>
where:
	<sort>		QSNormal  -or- QSMedian
	<gen>		RandomGen -or- FixedGen
	<length>	(Positive) Input size
	<seed>		(Optional) Seed value for RandomGen

PS: Did you know that Starcraft was getting remastered (ETA this summer: https://starcraft.com/en-us/)?