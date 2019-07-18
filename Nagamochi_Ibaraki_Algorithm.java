import java.util.*;

//create an implementation of the Nagamochi-Ibaraki algorithm (see in the lecture notes) for 
//finding a minimum cut in an undirected graph, and experiment with it.

//Explain how your implementation of the algorithm works. Provide pseudo code for the description, 
//with sufficient comments to make it readable and understandable by a human.

//Write a computer program that implements the algorithm. You may use C/C++ or java for programming.

//Run the program on randomly generated examples. Let the number of nodes be fixed at n = 25, 
//while the number m of edges will vary between 50 and 550, increasing in steps of 5. 
//Once a value of m is selected, the program creates a graph with n = 25 nodes and m edges. 
//The actual edges are selected randomly among all possible ones, with parallel edges allowed, but self-loops are excluded.

//Submit the code.

//Screen shots of program execution.

//submit text me file for other parts of assignment

public class Nagamochi_Ibaraki_Algorithm {

	public static void main(String[] args) {
//		for (int i = 50; i <= 500; i += 5) {
//			int[][] graph = generateGraph(i);
//			System.out.println(Arrays.deepToString(graph));
//		}
//		int[][] graph = generateGraph(550);
//		int[] MA = generateMA(graph);
//		int u = MA[MA.length - 2];
//		int v = MA[MA.length - 1];
//		String[] rep = Arrays.deepToString(graph).substring(1, Arrays.deepToString(graph).length()).split("],");
//		StringBuilder sb = new StringBuilder();
//		for (String i : rep) {
//			sb.append(i.replaceAll("[", ""));
//			sb.append("\n");
//		}
//		System.out.println(sb.toString());
//		System.out.println(nagamochiIbarakiAlgo(graph));
		for (int i = 50; i <= 550; i += 5) {
			int[][] test = generateGraph(i);
			System.out.println("The connectivity for the graph is: " + nagamochiIbarakiAlgo(test));
		}
		// System.out.println(Arrays.toString(MA));
		// System.out.println(Arrays.deepToString(mergeGraph(graph, u, v)));
	}

	static int nagamochiIbarakiAlgo(int[][] graph) {
		if (graph.length == 2) {
			return graph[0][1];
		} else {
			int[] MA = generateMA(graph);
			int u = MA[MA.length - 2];
			int v = MA[MA.length - 1];
			int deg = 0;
			for (int i = 0; i < graph.length; i++) {
				deg += graph[v][i];
			}
			return Math.min(deg, nagamochiIbarakiAlgo(mergeGraph(graph, u, v)));
		}

	}

	static int[][] generateGraph(int n) {
		int[][] graph = new int[25][25];
		Random rand = new Random();
		int edges = 0;
		while (edges < n) {
			int rand1 = rand.nextInt(25);
			int rand2 = rand.nextInt(25);
			if (rand1 == rand2) {
				continue;
			} else {
				graph[rand1][rand2]++;
				graph[rand2][rand1]++;
				edges++;
			}
		}
		return graph;
	}

	static int[][] mergeGraph(int[][] graph, int v, int x) {
		int prevNum = graph.length;
		int[][] newGraph = new int[prevNum - 1][prevNum - 1];
		for (int i = 0; i < prevNum; i++) {
			if (i != v || i != x) {
				graph[v][i] = graph[v][i] + graph[x][i];
				graph[i][v] = graph[v][i];
			}
		}
		graph[v][x] = 0;
		graph[x][v] = 0;
		for (int i = 0; i < prevNum; i++) {
			for (int j = 0; j < prevNum; j++) {
				if (i == x || j == x) {
					continue;
				} else if (i > x && j > x) {
					newGraph[i - 1][j - 1] = graph[i][j];
				} else if (i > x) {
					newGraph[i - 1][j] = graph[i][j];
				} else if (j > x) {
					newGraph[i][j - 1] = graph[i][j];
				} else {
					newGraph[i][j] = graph[i][j];
				}
			}
		}
		return newGraph;
	}

	static int[] generateMA(int[][] graph) {
		Set<Integer> used = new HashSet<>();
		Set<Integer> notUsed = new HashSet<>();
		int[] MA = new int[graph.length];
		used.add(0);
		for (int i = 1; i <= graph.length - 1; i++) {
			notUsed.add(i);
		}
		int seq = 0;
		MA[seq] = 0;
		while (!notUsed.isEmpty()) {
			int next = 0, maxD = Integer.MIN_VALUE;
			for (int x : notUsed) {
				int edge = 0;
				for (int m : used) {
					edge += graph[x][m];
				}
				if (edge > maxD) {
					next = x;
					maxD = edge;
				}
			}
			MA[++seq] = next;
			notUsed.remove(next);
			used.add(next);
		}
		return MA;
	}
}