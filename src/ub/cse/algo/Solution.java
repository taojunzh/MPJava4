package ub.cse.algo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Solution {

	private Spectrum spectrum;
	HashMap<Integer, List<Integer>> paths;
	private int[] bandwidth;
	// private int fcc = 0;
	// private int lawSuit = 0;
	// private int Sn = 0;
	private int costB = 0;
	private float[] updatedB;

	public Solution(Spectrum spectrum) {
		this.spectrum = spectrum;
		paths = new HashMap<Integer, List<Integer>>();
		bandwidth = new int[spectrum.getBandwidths().length];
		updatedB = new float[spectrum.getBandwidths().length];
		for (int i = 0; i < spectrum.getGraph().size(); i++) {
			bandwidth[i] = (int) spectrum.getBandwidths()[i];
			updatedB[i] = spectrum.getBandwidths()[i];
			// if (spectrum.getFccClients()[i] == 1) {
			// Sn++;
			// }
		}
		// float max = 0;
		// System.out.println(spectrum.getFccFine());
		// System.out.println(spectrum.getLawsuit());
		// System.out.println(spectrum.getCostBandwidth());
		// while (max < spectrum.getRhoFcc()) {
		// fcc++;
		// max = fcc / Sn;
		// }
		// max = 0;
		// while (max < spectrum.getRhoLawsuit()) {
		// lawSuit++;
		// max = lawSuit / spectrum.getListClients().size();
		// }
	}

	/**
	 * This method must be implemented by you. You may add other methods and
	 * subclasses as you see fit, but they must remain within the Solution class.
	 * 
	 * @return A Spectrum object that encapsulates the paths and other data
	 *         generated by you
	 */
	public Spectrum outputSpectrum() {
		for (Integer i : spectrum.getGraph().keySet()) {
			Collections.sort(spectrum.getGraph().get(i), new cmp1());
		}
		BFS1 y = new BFS1(spectrum.getGraph(), spectrum.getISP(), spectrum.getListClients());
		paths = y.bfs();
		spectrum.setUpdatedBandwidths(updatedB);
		spectrum.setOutputPath(paths);
		return this.spectrum;
	}

	class cmp implements Comparator<Integer> {
		@Override
		public int compare(Integer c1, Integer c2) {
			if (spectrum.getAlphas()[c1] > spectrum.getAlphas()[c2])
				return 1;
			if (spectrum.getAlphas()[c1] == spectrum.getAlphas()[c2]) {
				return 0;
			}
			return -1;
		}
	}

	class cmp1 implements Comparator<Integer> {

		@Override
		public int compare(Integer c1, Integer c2) {

			if (spectrum.getClientPayments()[c1] < spectrum.getClientPayments()[c2])
				return 1;
			if (spectrum.getClientPayments()[c1] == spectrum.getClientPayments()[c2]) {
				cmp2 x = new cmp2();
				return x.compare(c1, c2);
			}
			return -1;
		}
	}

	class cmp2 implements Comparator<Integer> {

		@Override
		public int compare(Integer c1, Integer c2) {
			if (spectrum.getBandwidths()[c1] < spectrum.getBandwidths()[c2])
				return 1;
			if (spectrum.getBandwidths()[c1] == spectrum.getBandwidths()[c2]) {
				cmp x = new cmp();
				return x.compare(c1, c2);
			}
			return -1;
		}
	}

	class fcc implements Comparator<Integer> {

		@Override
		public int compare(Integer c1, Integer c2) {
			if (spectrum.getFccClients()[c1] != spectrum.getFccClients()[c2]) {
				if (spectrum.getFccClients()[c1] > spectrum.getFccClients()[c2]) {
					return -1;
				}
				return 1;
			}
			cmp1 x = new cmp1();
			return x.compare(c1, c2);
		}
	}

	class lawSuit implements Comparator<Integer> {

		@Override
		public int compare(Integer c1, Integer c2) {
			if (spectrum.getFccClients()[c1] != spectrum.getFccClients()[c2]) {
				if (spectrum.getFccClients()[c1] > spectrum.getFccClients()[c2]) {
					return 1;
				}
				return -1;
			}
			cmp1 x = new cmp1();
			return x.compare(c1, c2);
		}
	}

	public class BFS1 {

		private Graph graph;
		private Integer ISP; // source node
		private Integer dest; // destination node
		private int[] distance; // mapping from node id's to their shortest (bfs) distance
		private int[] predecessor; // mapping from node id's to their predecessor node id's in BFS
		private boolean[] visited; // mapping from node id's to a boolean value indicating if the node was visited
									// in BFS
		private List<Integer> listClients;
		private double[] weights; // mapping from node id's to node weights
		private Solution solution;
		// private Solution solution;

		public BFS1(Graph graph, Integer ISP, List<Integer> listClients) {
			this.graph = graph;
			this.ISP = ISP;
			this.dest = dest;
			this.listClients = listClients;
			this.solution = solution;
			// this.solution = solution;

			this.weights = weights;
			distance = new int[graph.size()];
			predecessor = new int[graph.size()];
			visited = new boolean[graph.size()];
			for (int i = 0; i < graph.size(); ++i) {
				distance[i] = 0;
				predecessor[i] = -1;
				visited[i] = false;
			}
		}

		public HashMap<Integer, List<Integer>> bfs() {

			// Outputs a mapping from every client node id to the shortest path from ISP
			// node to that client node

			LinkedList<Integer> queue = new LinkedList<Integer>();
			visited[this.ISP] = true;
			distance[ISP] = 0;
			queue.add(ISP);

			while (queue.size() > 0) {
				Integer current = queue.poll();
				for (Integer neighbor : graph.get(current)) {
					if (!visited[neighbor]) {
						bandwidth[current]--;
						visited[neighbor] = true;
						distance[neighbor] = distance[current] + 1;
						predecessor[neighbor] = current;
						queue.addLast(neighbor);
						if (bandwidth[current] <= 0) {
							bandwidth[current]++;
							updatedB[current]++;
							int id = neighbor;
							while (predecessor[id] != -1) {
								id = predecessor[id];
								updatedB[id]++;
							}
						}
					}
				}
			}
			return outputPaths();
		}

		public HashMap<Integer, List<Integer>> outputPaths() {

			// Outputs a mapping from every client node id to the shortest path from ISP
			// node to that client node

			HashMap<Integer, List<Integer>> paths = new HashMap<Integer, List<Integer>>();
			for (Integer cId : this.listClients) {
				int crawl = cId;
				List<Integer> outputList = new ArrayList<Integer>();
				outputList.add(crawl);

				while (predecessor[crawl] != -1) {
					outputList.add(predecessor[crawl]);
					crawl = predecessor[crawl];
				}
				Collections.reverse(outputList);
				paths.put(cId, outputList);
			}

			return paths;
		}
	}
}
