/* CS 314 STUDENTS: FILL IN THIS HEADER.
 *
 * Student information for assignment:
 *
 *  On my honor, Danyal Saeed, this programming assignment is my own work
 *  and I have not provided this code to any other student.
 *
 *  UTEID: das6689
 *  email address: danyalsaeed@utexas.edu
 *  TA name: Gracelynn
 */

/*
 * Question.
 *
 * 1. The assignment presents three ways to rank teams using graphs.
 * The results, especially for the last two methods are reasonable.
 * However if all results from all college football teams are included
 * some unexpected results occur. 
 * 
 * Explain the unexpected results. You may
 * have to do some research on the various college football divisions to
 * make an informed answer. (What are the divisions within college
 * football? Who do teams play? How would this affect the 
 * structure of the graph?)
 * 
 * There could be many reasons why there were unexpected outcomes. The first big reason may be due 
 * to the algorithms being based on connectivity and teams that come from smaller divisions have 
 * less chances to compete against one another due to funding and location. This would probably 
 * result in teams from lower divisions being ranked higher than would be expected had they played 
 * a similar amount of teams as the upper divisions. This could also result in higher division teams
 * being ranked lower than expected. Lower divisions also have a lower level of play and this is not
 * reflected in the graph model. This would possibly fluff teams higher division teams that play
 * lower division teams. A better model would take this into account and account for relative 
 * difficulties and rank teams within their own divisions.
  */

public class GraphAndRankTester {

	/**
	 * Runs tests on Graph classes and FootballRanker class. Experiments involve
	 * results from college football teams. Central nodes in the graph are compared
	 * to human rankings of the teams.
	 * 
	 * @param args None expected.
	 */
	public static void main(String[] args) {
		studentTests();

	}

	private static void studentTests() {
		System.out.println("Student Tests:");

		String[][] g1Edges = { { "A", "B", "4" }, { "A", "C", "3" }, { "B", "D", "16" },
				{ "C", "D", "6" }, { "D", "E", "3" }, { "D", "F", "5" }, { "F", "G", "1" },
				{ "E", "G", "1" } };
		Graph g1 = getGraph(g1Edges, false);

		// Test 1 dijkstra
		g1.dijkstra("A");
		String actualPath = g1.findPath("G").toString();
		String expected = "[A, C, D, E, G]";
		if (actualPath.equals(expected)) {
			System.out.println("Passed dijkstra path test graph 1");
		} else {
			System.out.println("Failed dijkstra path test graph 1. Expected: " + expected
					+ " actual " + actualPath);
		}

		// Test 2 dijkstra
		String[][] g2Edges = { { "A", "B", "2" }, { "B", "C", "4" }, { "B", "D", "13" },
				{ "C", "F", "4" }, { "A", "G", "8" }, { "D", "F", "5" }, { "D", "G", "6" },
				{ "D", "E", "7" } };
		Graph g2 = getGraph(g2Edges, false);

		g2.dijkstra("A");
		actualPath = g2.findPath("E").toString();
		expected = "[A, G, D, E]";
		if (actualPath.equals(expected)) {
			System.out.println("Passed dijkstra path test graph 2.");
		} else {
			System.out.println("Failed dijkstra path test graph 1. Expected: " + expected
					+ " actual " + actualPath);
		}

		// test 1 all paths

		String[] expectedPaths = { "Name: D                    cost per path: 1.3333, num paths: 6",
				"Name: B                    cost per path: 1.8333, num paths: 6",
				"Name: C                    cost per path: 1.8333, num paths: 6",
				"Name: E                    cost per path: 1.8333, num paths: 6",
				"Name: F                    cost per path: 1.8333, num paths: 6",
				"Name: A                    cost per path: 2.3333, num paths: 6",
				"Name: G                    cost per path: 2.3333, num paths: 6" };
		doAllPathsTests("Graph 1", g1, false, 4, 4.0, expectedPaths);

		// test 2 all paths
		expectedPaths = new String[] {
				"Name: D                    cost per path: 6.6667, num paths: 6",
				"Name: E                    cost per path: 7.1667, num paths: 6",
				"Name: C                    cost per path: 7.6667, num paths: 6",
				"Name: G                    cost per path: 7.6667, num paths: 6",
				"Name: F                    cost per path: 8.5000, num paths: 6",
				"Name: A                    cost per path: 9.1667, num paths: 6",
				"Name: B                    cost per path: 12.5000, num paths: 6" };
		doAllPathsTests("Graph 1", g1, true, 4, 18.0, expectedPaths);
	}

	// return a Graph based on the given edges
	private static Graph getGraph(String[][] edges, boolean directed) {
		Graph result = new Graph();
		for (String[] edge : edges) {
			result.addEdge(edge[0], edge[1], Double.parseDouble(edge[2]));
			// If edges are for an undirected graph add edge in other direction too.
			if (!directed) {
				result.addEdge(edge[1], edge[0], Double.parseDouble(edge[2]));
			}
		}
		return result;
	}

	// Tests the all paths method. Run each set of tests twice to ensure the Graph
	// is correctly reseting each time
	private static void doAllPathsTests(String graphNumber, Graph g, boolean weighted,
			int expectedDiameter, double expectedCostOfLongestShortestPath,
			String[] expectedPaths) {

		System.out.println("\nTESTING ALL PATHS INFO ON " + graphNumber + ". ");
		for (int i = 0; i < 2; i++) {
			System.out.println("Test run = " + (i + 1));
			System.out.println("Find all paths weighted = " + weighted);
			g.findAllPaths(weighted);
			int actualDiameter = g.getDiameter();
			double actualCostOfLongestShortesPath = g.costOfLongestShortestPath();
			if (actualDiameter == expectedDiameter) {
				System.out.println("Passed diameter test.");
			} else {
				System.out.println("FAILED diameter test. " + "Expected = " + expectedDiameter
						+ " Actual = " + actualDiameter);
			}
			if (actualCostOfLongestShortesPath == expectedCostOfLongestShortestPath) {
				System.out.println("Passed cost of longest shortest path. test.");
			} else {
				System.out.println("FAILED cost of longest shortest path. " + "Expected = "
						+ expectedCostOfLongestShortestPath + " Actual = "
						+ actualCostOfLongestShortesPath);
			}
			testAllPathsInfo(g, expectedPaths);
			System.out.println();
		}

	}

	// Compare results of all paths info on Graph to expected results.
	private static void testAllPathsInfo(Graph g, String[] expectedPaths) {
		int index = 0;

		for (AllPathsInfo api : g.getAllPaths()) {
			if (expectedPaths[index].equals(api.toString())) {
				System.out.println(expectedPaths[index] + " is correct!!");
			} else {
				System.out.println("ERROR IN ALL PATHS INFO: ");
				System.out.println("index: " + index);
				System.out.println("EXPECTED: " + expectedPaths[index]);
				System.out.println("ACTUAL: " + api.toString());
				System.out.println();
			}
			index++;
		}
		System.out.println();
	}

}
