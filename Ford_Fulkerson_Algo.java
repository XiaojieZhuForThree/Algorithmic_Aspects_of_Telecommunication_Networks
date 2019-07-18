package ford_fulkerson;

import java.util.*;

public class Ford_Fulkerson_Algo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		char[] vertices = new char[] { 's', 'w', 'x', 'z', 'y', 't' };
		int s = 0, t = 5;
		int[][] graph = new int[][] { { 0, 4, 7, 10, 0, 0 }, { 0, 0, 0, 0, 2, 10 }, { 0, 2, 0, 2, 10, 0 },
				{ 0, 0, 0, 0, 2, 6 }, { 0, 0, 0, 0, 0, 7 }, { 0, 0, 0, 0, 0, 0 } };
		int[][] test = new int[][] { { 0, 4, 7, 10, 0, 0 }, { 0, 0, 0, 0, 2, 10 }, { 0, 2, 0, 2, 10, 0 },
				{ 0, 0, 0, 0, 2, 6 }, { 0, 0, 0, 0, 0, 7 }, { 0, 0, 0, 0, 0, 0 } };
		System.out.print("The maximum flow that could be achieved in this graph is: ");
		System.out.println(fordFulkersonAlgo(test, s, t));
		System.out.println("\nTo achieve the flow we need the following flow for each edge: ");
		System.out.println(getFlow(graph, test, vertices).toString());
		System.out.println("The mincut source vertices are: ");
		Set<Character> sources = getSource(graph, test, vertices);
		Set<Character> sinks = getSink(sources, graph, vertices);
		System.out.println(sources.toString());
		System.out.println("The mincut sink vertices are: ");
		System.out.println(sinks.toString());
		System.out.println("The mincut edges vertices are: ");
		System.out.println(getEdge(sources, sinks, graph, test, vertices).toString());
	}
	static Set<String> getEdge(Set<Character> sources, Set<Character> sinks, int[][] graph, int[][] test, char[] vertices) {
		Set<String> edges = new HashSet<>();
		for (int i = 0; i < graph.length; i++) {
			for (int j = 0; j < graph.length; j++) {
				if (sources.contains(vertices[i]) && sinks.contains(vertices[j]) && graph[i][j] != 0 && test[i][j] == 0) {
					StringBuilder sb = new StringBuilder();
					sb.append(vertices[i]);
					sb.append(vertices[j]);
					edges.add(sb.toString());
				}
			}
		}
		return edges;
	}
	static Set<Character> getSource(int[][] original, int[][] after, char[] vertices) {
		Queue<Integer> queue = new LinkedList<>();
		queue.add(0);
		Set<Character> source = new HashSet<>();
		boolean[] seen = new boolean[original.length];
		while (!queue.isEmpty()) {
			int now = queue.poll();
			source.add(vertices[now]);
			for (int i = 0; i < original.length; i++) {
				if (i != now && !seen[i] && original[now][i] != 0 && after[now][i] != 0) {
					queue.add(i);
				}
			}
		}
		return source;
	}

	static Set<Character> getSink(Set<Character> source, int[][] original, char[] vertices) {
		Set<Character> sink = new HashSet<>();
		for (int i = 0; i < original.length; i++) {
			if (!source.contains(vertices[i])) {
				sink.add(vertices[i]);
			}
		}
		return sink;
	}

	static List<String> getFlow(int[][] original, int[][] after, char[] vertices) {
		List<String> list = new ArrayList<>();
		for (int i = 0; i < original.length; i++) {
			for (int j = 0; j < original[0].length; j++) {
				if (original[i][j] != 0) {
					StringBuilder sb = new StringBuilder();
					sb.append(vertices[i]);
					sb.append(vertices[j]);
					sb.append(": ");
					sb.append(original[i][j] - after[i][j]);
					sb.append("/");
					sb.append(original[i][j]);
					list.add(sb.toString());
				}

			}
		}
		return list;
	}

	static int fordFulkersonAlgo(int[][] graph, int s, int t) {
		int flow = 0;
		int[] path = edmondsKarpAlgo(graph, s, t);
		while (path[s] != 0) {
			int bottleneck = Integer.MAX_VALUE;
			for (int v = t; v != s; v = path[v]) {
				int u = path[v];
				bottleneck = Math.min(bottleneck, graph[u][v]);
			}
			flow += bottleneck;
			for (int v = t; v != s; v = path[v]) {
				int u = path[v];
				graph[u][v] -= bottleneck;
				graph[v][u] += bottleneck;
			}
			path = edmondsKarpAlgo(graph, s, t);
		}
		return flow;
	}

	static int[] edmondsKarpAlgo(int[][] graph, int s, int t) {
		boolean[] visited = new boolean[graph.length];
		int[] path = new int[graph.length];
		path[s] = -1;
		visited[0] = true;
		LinkedList<Integer> queue = new LinkedList<>();
		queue.add(s);
		while (!queue.isEmpty()) {
			int i = queue.poll();
			for (int j = 0; j <= t; j++) {
				if (!visited[j] && graph[i][j] > 0) {
					queue.add(j);
					path[j] = i;
					visited[j] = true;
				}
			}
		}
		return visited[t] == true ? path : new int[graph.length];
	}
}
