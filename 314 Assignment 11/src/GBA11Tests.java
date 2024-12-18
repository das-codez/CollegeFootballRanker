public class GBA11Tests {

    public static void main(String[] args) {
        myGraphTests();
        //mikeGraphTests();
    }

    private static void myGraphTests() {
        System.out.println("GARY'S TESTS BEGIN HERE");
        myGraph1Tests();
        myGraph2Tests();
    }

    private static void myGraph1Tests() {
        // Graph from A* Demo slides
        String [][] g1Edges =  {{"A", "B", "2"},
                        {"A", "C", "4"},
                        {"B", "E", "11"},
                        {"C", "F", "5"},
                        {"E", "G", "6"},
                        {"F", "G", "1"},
                        {"D", "A", "2"},
                        {"D", "B", "3"},
                        {"D", "C", "2"},
                        {"D", "E", "7"},
                        {"D", "F", "8"},
                        {"D", "G", "4"}};
        Graph g1 = getGraph(g1Edges, true);

        g1.dijkstra("A");
        String actualPath = g1.findPath("B").toString();
        String expected = "[A, B]";
        if (actualPath.equals(expected)) {
            System.out.println("Passed dijkstra path test 1 graph 1.");
        } else {
            System.out.println("Failed dijkstra path test 1 graph 1. Expected: " 
                    + expected + " actual " + actualPath);
        }

        g1.dijkstra("A");
        actualPath = g1.findPath("G").toString();
        expected = "[A, C, F, G]";
        if (actualPath.equals(expected)) {
            System.out.println("Passed dijkstra path test 2 graph 1.");
        } else {
            System.out.println("Failed dijkstra path test 2 graph 1. Expected: " 
                    + expected + " actual " + actualPath);
        }

        g1.dijkstra("D");
        actualPath = g1.findPath("F").toString();
        expected = "[D, C, F]";
        if (actualPath.equals(expected)) {
            System.out.println("Passed dijkstra path test 3 graph 1.");
        } else {
            System.out.println("Failed dijkstra path test 3 graph 1. Expected: " 
                    + expected + " actual " + actualPath);
        }

        String[] expectedPaths = {"Name: D                    cost per path: 1.0000, num paths: 6",
                        "Name: A                    cost per path: 1.8000, num paths: 5",
                        "Name: B                    cost per path: 1.5000, num paths: 2",
                        "Name: C                    cost per path: 1.5000, num paths: 2",
                        "Name: E                    cost per path: 1.0000, num paths: 1",
                        "Name: F                    cost per path: 1.0000, num paths: 1"};
        doAllPathsTests("Graph 1", g1, false, 3, 3.0, expectedPaths);

        // now do all paths weighted
        expectedPaths = new String[]{"Name: D                    cost per path: 4.1667, "
            + "num paths: 6",
                        "Name: A                    cost per path: 7.6000, num paths: 5",
                        "Name: C                    cost per path: 5.5000, num paths: 2",
                        "Name: B                    cost per path: 14.0000, num paths: 2",
                        "Name: F                    cost per path: 1.0000, num paths: 1",
                        "Name: E                    cost per path: 6.0000, num paths: 1"};
        doAllPathsTests("Graph 1", g1, true, 2, 17.0, expectedPaths);
    }

    private static void myGraph2Tests() {
        // From my CS311 Notes
        String [][] g2Edges =  {{"A", "B", "1"},
                        {"A", "C", "5"},
                        {"B", "C", "3"},
                        {"D", "E", "39"},
                        {"F", "G", "15"},
                        {"G", "H", "15"}};
        Graph g2 = getGraph(g2Edges, false);

        g2.dijkstra("A");
        String actualPath = g2.findPath("C").toString();
        String expected = "[A, B, C]";
        if (actualPath.equals(expected)) {
            System.out.println("Passed dijkstra path test 1 graph 2.");
        } else {
            System.out.println("Failed dijkstra path test 1 graph 2. Expected: " 
                    + expected + " actual " + actualPath);
        }

        g2.dijkstra("D");
        actualPath = g2.findPath("E").toString();
        expected = "[D, E]";
        if (actualPath.equals(expected)) {
            System.out.println("Passed dijkstra path test 2 graph 2.");
        } else {
            System.out.println("Failed dijkstra path test 2 graph 2. Expected: " 
                    + expected + " actual " + actualPath);
        }

        g2.dijkstra("F");
        actualPath = g2.findPath("H").toString();
        expected = "[F, G, H]";
        if (actualPath.equals(expected)) {
            System.out.println("Passed dijkstra path test 3 graph 2.");
        } else {
            System.out.println("Failed dijkstra path test 3 graph 2. Expected: " 
                    + expected + " actual " + actualPath);
        }

        String[] expectedPaths = {"Name: A                    cost per path: 1.0000, "
            + "num paths: 2",
                        "Name: B                    cost per path: 1.0000, num paths: 2",
                        "Name: C                    cost per path: 1.0000, num paths: 2",
                        "Name: G                    cost per path: 1.0000, num paths: 2",
                        "Name: F                    cost per path: 1.5000, num paths: 2",
                        "Name: H                    cost per path: 1.5000, num paths: 2",
                        "Name: D                    cost per path: 1.0000, num paths: 1",
                        "Name: E                    cost per path: 1.0000, num paths: 1"};
        doAllPathsTests("Graph 2", g2, false, 2, 2.0, expectedPaths);

        // now do all paths weighted
        expectedPaths = new String[] {"Name: B                    cost per path: 2.0000, "
            + "num paths: 2",
                        "Name: A                    cost per path: 2.5000, num paths: 2",
                        "Name: C                    cost per path: 3.5000, num paths: 2",
                        "Name: G                    cost per path: 15.0000, num paths: 2",
                        "Name: F                    cost per path: 22.5000, num paths: 2",
                        "Name: H                    cost per path: 22.5000, num paths: 2",
                        "Name: D                    cost per path: 39.0000, num paths: 1",
                        "Name: E                    cost per path: 39.0000, num paths: 1"};
        doAllPathsTests("Graph 2", g2, true, 1, 39.0, expectedPaths);
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
                System.out.println("FAILED diameter test. "
                                + "Expected = "  + expectedDiameter +
                                " Actual = " + actualDiameter);
            }
            if (actualCostOfLongestShortesPath == expectedCostOfLongestShortestPath) {
                System.out.println("Passed cost of longest shortest path. test.");
            } else {
                System.out.println("FAILED cost of longest shortest path. "
                                + "Expected = "  + expectedCostOfLongestShortestPath +
                                " Actual = " + actualCostOfLongestShortesPath);
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

    private static void mikeGraphTests() {
        System.out.println("MIKE'S TESTS BEGIN HERE");
        System.out.println("PERFORMING TESTS ON SIMPLE GRAPHS\n");
        graph0Tests();
        graph1Tests();
        graph2Tests();
        graph3Tests();
    }

    /* The graph used here is the same one from the example
     * used to show Dijktra's algorithm from the slides on
     * Graphs. It may be useful to print out the priority queue
     * at the top of the main while loop in the dijktra method
     * to see if it matches the one in the slides. Note, the Java
     * PriorityQueue (which you CAN use in the dijkstra method)
     * breaks ties in an arbitrary manner, so the order of equal
     * elements in the priority queue may be different than those
     * shown in the slides. (And that is not a problem.)
     */
    private static void graph0Tests() {
        System.out.println("Graph #0 Tests:");
        // first a simple path test
        // Graph #0
        String [][] g1Edges =  {{"A", "B", "1"},
                        {"B", "C", "3"},
                        {"A", "C", "7"},
                        {"B", "D", "21"},
                        {"C", "F", "3"},
                        {"A", "G", "17"},
                        {"D", "F", "4"},
                        {"D", "G", "5"},
                        {"D", "E", "6"}};
        Graph g1 = getGraph(g1Edges, false);

        g1.dijkstra("A");
        String actualPath = g1.findPath("E").toString();
        String expected = "[A, B, C, F, D, E]";
        if (actualPath.equals(expected)) {
            System.out.println("Passed dijkstra path test graph 0.");
        } else {
            System.out.println("Failed dijkstra path test graph 0. Expected: " + expected + " actual " + actualPath);
        }

        // now do all paths unweighted
        String[] expectedPaths = {"Name: D                    cost per path: 1.3333, num paths: 6",
                        "Name: B                    cost per path: 1.5000, num paths: 6",
                        "Name: A                    cost per path: 1.6667, num paths: 6",
                        "Name: C                    cost per path: 1.6667, num paths: 6",
                        "Name: F                    cost per path: 1.6667, num paths: 6",
                        "Name: G                    cost per path: 1.6667, num paths: 6",
                        "Name: E                    cost per path: 2.1667, num paths: 6"};
        doAllPathsTests("Graph 0", g1, false, 3, 3.0, expectedPaths);

        // now do all paths weighted
        expectedPaths = new String[] {  "Name: F                    cost per path: 6.5000, num paths: 6",
                        "Name: C                    cost per path: 7.0000, num paths: 6",
                        "Name: D                    cost per path: 7.1667, num paths: 6",
                        "Name: B                    cost per path: 8.5000, num paths: 6",
                        "Name: A                    cost per path: 9.3333, num paths: 6",
                        "Name: G                    cost per path: 11.3333, num paths: 6",
                        "Name: E                    cost per path: 12.1667, num paths: 6"};
        doAllPathsTests("Graph 0", g1, true, 5, 17.0, expectedPaths);
    }

    private static void graph1Tests() {
        System.out.println("Graph #1 Tests:");
        // first a simple path test
        // Graph #1
        String [][] g1Edges =  {{"A", "B", "1"},
                        {"B", "C", "3"},
                        {"B", "D", "12"},
                        {"C", "F", "3"},
                        {"A", "G", "7"},
                        {"D", "F", "4"},
                        {"D", "G", "5"},
                        {"D", "E", "6"}};
        Graph g1 = getGraph(g1Edges, false);

        g1.dijkstra("A");
        String actualPath = g1.findPath("E").toString();
        String expected = "[A, B, C, F, D, E]";
        if (actualPath.equals(expected)) {
            System.out.println("Passed dijkstra path test graph 1.");
        } else {
            System.out.println("Failed dijkstra path test graph 1. Expected: " + expected + " actual " + actualPath);
        }

        // now do all paths unweighted
        String[] expectedPaths = {"Name: D                    cost per path: 1.3333, num paths: 6",
                        "Name: B                    cost per path: 1.5000, num paths: 6",
                        "Name: F                    cost per path: 1.8333, num paths: 6",
                        "Name: G                    cost per path: 1.8333, num paths: 6",
                        "Name: A                    cost per path: 2.0000, num paths: 6",
                        "Name: C                    cost per path: 2.0000, num paths: 6",
                        "Name: E                    cost per path: 2.1667, num paths: 6"};
        doAllPathsTests("Graph 1", g1, false, 3, 3.0, expectedPaths);

        // now do all paths weighted
        expectedPaths = new String[] {  "Name: F                    cost per path: 6.5000, num paths: 6",
                        "Name: C                    cost per path: 6.8333, num paths: 6",
                        "Name: D                    cost per path: 7.1667, num paths: 6",
                        "Name: B                    cost per path: 7.3333, num paths: 6",
                        "Name: A                    cost per path: 7.8333, num paths: 6",
                        "Name: G                    cost per path: 8.5000, num paths: 6",
                        "Name: E                    cost per path: 12.1667, num paths: 6"};
        doAllPathsTests("Graph 1", g1, true, 5, 17.0, expectedPaths);
    }

    private static void graph2Tests() {
        System.out.println("Graph #2 Tests:");
        // first a simple path test
        // Graph #1
        String[][] g2Edges = {{"E", "G", "9.6"},
                        {"G", "E", "19.2"},
                        {"D", "F", "4.0"},
                        {"F", "D", "8.0"},
                        {"E", "B", "8.0"},
                        {"B", "E", "16.0"},
                        {"F", "A", "6.0"},
                        {"A", "F", "12.0"},
                        {"F", "C", "4.0"},
                        {"C", "F", "8.0"},
                        {"C", "E", "6.9"},
                        {"E", "C", "13.8"},
                        {"D", "G", "8.0"},
                        {"G", "D", "16.0"},
                        {"E", "A", "5.7"},
                        {"A", "E", "11.4"},
                        {"C", "A", "0.4"},
                        {"A", "C", "0.8"},
                        {"D", "A", "6.1"},
                        {"A", "D", "12.2"},
                        {"D", "B", "7.9"},
                        {"B", "D", "15.8"},
                        {"C", "G", "5.4"},
                        {"G", "C", "10.8"},
                        {"A", "B", "7.1"},
                        {"B", "A", "14.2"},
                        {"E", "F", "4.4"},
                        {"F", "E", "8.8"}};
        Graph g2 = getGraph(g2Edges, true);



        // do all paths weighted
        String[] expectedPaths = new String[] { "Name: C                    cost per path: 6.8000, num paths: 6",
                        "Name: A                    cost per path: 7.1333, num paths: 6",
                        "Name: D                    cost per path: 7.6167, num paths: 6",
                        "Name: F                    cost per path: 7.6833, num paths: 6",
                        "Name: E                    cost per path: 7.7667, num paths: 6",
                        "Name: G                    cost per path: 15.4667, num paths: 6",
                        "Name: B                    cost per path: 16.8667, num paths: 6"};
        doAllPathsTests("Graph 2", g2, true, 3, 20.4, expectedPaths);
    }

    // Graph 3 is an unconnected Graph
    private static void graph3Tests() {
        System.out.println("Graph 3 Tests. Graph 3 is not fully connected. ");
        String [][] g3Edges =
                    {{"A", "B", "13"},
                                    {"A", "C", "10"},
                                    {"A", "D", "2"},
                                    {"B", "E", "5"},
                                    {"C", "B", "1"},
                                    {"D", "C", "5"},
                                    {"E", "G", "1"},
                                    {"E", "F", "4"},
                                    {"F", "C", "3"},
                                    {"F", "E", "2"},
                                    {"G", "F", "2"},
                                    {"H", "I", "10"},
                                    {"H", "J", "5"},
                                    {"H", "K", "22"},
                                    {"I", "K", "3"},
                                    {"I", "J", "1"},
                                    {"J", "L", "8"}};
        Graph g3 = getGraph(g3Edges, true);

        // do all paths weighted
        String[] expectedPaths = {"Name: A                    cost per path: 10.0000, num paths: 6",
                        "Name: D                    cost per path: 9.6000, num paths: 5",
                        "Name: F                    cost per path: 3.0000, num paths: 4",
                        "Name: E                    cost per path: 4.2500, num paths: 4",
                        "Name: G                    cost per path: 4.2500, num paths: 4",
                        "Name: C                    cost per path: 5.7500, num paths: 4",
                        "Name: B                    cost per path: 7.5000, num paths: 4",
                        "Name: H                    cost per path: 10.2500, num paths: 4",
                        "Name: I                    cost per path: 4.3333, num paths: 3",
                        "Name: J                    cost per path: 8.0000, num paths: 1"};
        doAllPathsTests("Graph 3", g3, true, 6, 16.0, expectedPaths);
    }
}
