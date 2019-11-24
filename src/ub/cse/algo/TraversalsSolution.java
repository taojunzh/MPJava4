package ub.cse.algo;

import java.util.LinkedList;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;



import java.util.Collections;



//TODO: Optional, but keep track of basic traversals here so that it can be separated from the heuristics if needed

// Credits for modified BFS algorithm : https://www.geeksforgeeks.org/shortest-path-unweighted-graph/

//TODO: implement interated Dijkstra

public class TraversalsSolution{

	private Graph graph;
	private Integer ISP; // source node
	private int[] distance; // mapping from node id's to their shortest (bfs) distance
	private int[] predecessor; // mapping from node id's to their predecessor node id's in BFS
	private boolean[] visited; // mapping from node id's to a boolean value indicating if the node was visited in BFS
	private List<Integer> listClients;
	private Spectrum spectrum;

	//private Solution solution;



	public TraversalsSolution(Graph graph, Integer ISP,List<Integer> listClients, Spectrum spectrum){
		this.graph = graph;
		this.ISP = ISP;
		this.listClients = listClients;
		this.spectrum = spectrum;
		distance = new int[graph.size()];
		predecessor= new int[graph.size()];
		visited = new boolean[graph.size()];
		for(int i = 0; i < graph.size(); ++i){
			distance[i] = 0;
			predecessor[i] = -1;
			visited[i] = false;
		}
	}




	public HashMap<Integer,List<Integer>> bfs(){ 
		
		// Outputs a mapping from every client node id to the shortest path from ISP node to that client node
		
		LinkedList<Integer> queue = new LinkedList<Integer>();
		visited[this.ISP] = true;
		distance[ISP] = 0;
		queue.add(ISP);

		while(queue.size() > 0){
			Integer current = queue.poll();
			for(Integer neighbor : graph.get(current)){
				if(!visited[neighbor]){
					visited[neighbor] = true;
					distance[neighbor] = distance[current] +1;
					predecessor[neighbor] = current;
					queue.addLast(neighbor);
				}
			}
		}
		return outputPaths();
	}


	public HashMap<Integer,List<Integer>> outputPaths(){

		// Outputs a mapping from every client node id to the shortest path from ISP node to that client node

		HashMap<Integer,List<Integer>> paths = new HashMap<Integer,List<Integer>>();
		for(Integer cId : this.listClients){
			int crawl = cId;
			List<Integer> outputList = new ArrayList<Integer>();
			outputList.add(crawl);

			while(predecessor[crawl] != -1){
					outputList.add(predecessor[crawl]);
					crawl = predecessor[crawl];
			}
			Collections.reverse(outputList); 
			paths.put(cId,outputList);
	}

		return paths;
	}   
}

