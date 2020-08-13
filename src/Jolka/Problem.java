package Jolka;

import java.util.ArrayList;

public abstract class Problem<T> {
	
	long firstSolutionTime;
	int firstSolutionNodesCount;
	int firstSolutionBacktracksCount;
	long totalTime;
	int totalNodesCount;
	int totalBacktracksCount;
	int solutionCount;
	boolean firstSolutionReached = false;
	
	public void test() {
		clearStats();
		long startTime = System.currentTimeMillis();
		solve();
		long endTime = System.currentTimeMillis();
		totalTime = endTime - startTime;
		firstSolutionTime -= startTime;
		printStats();
	}
	
	public void testFC() {
		clearStats();
		long startTime = System.currentTimeMillis();
		solveFC();
		long endTime = System.currentTimeMillis();
		totalTime = endTime - startTime;
		firstSolutionTime -= startTime;
		printStats();
	}
	
	private void clearStats() {
		firstSolutionTime = 0;
		firstSolutionNodesCount = 0;
		firstSolutionBacktracksCount = 0;
		totalTime = 0;
		totalNodesCount = 0;
		totalBacktracksCount = 0;
		solutionCount = 0;
		firstSolutionReached = false;
	}
	
	private void printStats() {
		/*
		System.out.printf("\nFirst solution time: %sms\nFirst solution nodes: %s\nFirst solution backtracks: %s"
				+ "\nTotal time: %sms\nTotal nodes: %s\nTotal backtracks: %s\nSolution count: %s\n",
				firstSolutionTime,
				firstSolutionNodesCount,
				firstSolutionBacktracksCount,
				totalTime,
				totalNodesCount,
				totalBacktracksCount,
				solutionCount);
				*/
		System.out.printf("%s %s %s"
				+ " %s %s %s %s\n",
				firstSolutionTime,
				firstSolutionNodesCount,
				firstSolutionBacktracksCount,
				totalTime,
				totalNodesCount,
				totalBacktracksCount,
				solutionCount);
	}
	
	


	public abstract ArrayList<JVariable> solve();
	public abstract ArrayList<JVariable> solveFC();
	
	
	
	
	
}
