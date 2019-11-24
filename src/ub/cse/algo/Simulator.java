package ub.cse.algo;
import java.util.*;

public class Simulator{


	private HashMap<Integer,Packet> packets; // Mapping between client id's and their corresponding Packet objects
	private HashMap<Integer,Integer> clientPriorities; // Mapping between client id's and their corresponding priorities
	private HashMap<Integer,Client> clients; // Mapping between client id's and their corresponding Client
	private Spectrum spectrum;

	public HashMap<Integer,Client> getClients(){
		return this.clients;
	}

	public Simulator(Spectrum spectrum){
		this.packets = new HashMap<Integer,Packet>();
		this.clientPriorities = new HashMap<Integer,Integer>();
		this.clients = new HashMap<Integer,Client>();
		this.spectrum = spectrum;
	}

	private boolean validateEdge(Node node1, Node node2){
	//	System.out.println("node1:" + node1 + ", node2:" + node2);
		
		List<Integer> node2List = node2.getNeighbors();
		for(Integer nodeId : node2List){
			if(nodeId == node1.getId()) 
				return true;
			
		}
		return false;
	}

	public void run(Graph graph, List<Integer> listClients, HashMap<Integer,List<Node>> paths, float[] bandwidths, int[] priorities, int[] isRurals){

		TraversalsSolution tg = new TraversalsSolution(spectrum.getGraph(), spectrum.getISP(),listClients,this.spectrum);
		HashMap<Integer,List<Integer>> bfsPath = tg.bfs();
		List<Node> active = new LinkedList<Node>();

		for(Integer cId : listClients){
			Client c = new Client(cId,bandwidths[cId],graph.get(cId),0,0,paths.get(cId),isRurals[cId]==1,false);
			Packet p = new Packet(c,0,0,paths.get(cId));
			clientPriorities.put(cId, priorities[cId]);
			packets.put(cId, p);
			clients.put(cId, c);
		}
		

		HashMap<Integer,Node> nodes = new HashMap<Integer,Node>();
		Set<Integer> nodeIds = graph.keySet();
		for(Integer id : nodeIds ){
			Node node = new Node(id,bandwidths[id],graph.get(id));
			nodes.put(id, node);
		}

		Collections.sort(listClients, new PriorityComparator(clientPriorities));

		LinkedList<Integer> llistClients = new LinkedList<Integer>();
		for(Integer s: listClients)
			llistClients.add(s);

		
		while(llistClients.size() > 0){

			for(Iterator<Integer> it = llistClients.iterator(); it.hasNext();){

				Integer i = it.next();
				Packet packet = packets.get(i);
				Node currentNode = packet.getPath().get(packet.getLocation());
			//	Client client = packet.getClient();
				
			//	int id = packet.getClient().getId();

                                //Check if first node in path is ISP or not
                                if (packet.getPath().get(0).getId() != spectrum.getISP())
                                {
                                    Client receivingClient = packet.getClient();
                                    receivingClient.setDelay(Integer.MAX_VALUE);
                                    it.remove();
                                    continue;
                                }
				
				
				if(packet.getLocation() == packet.getPath().size()-1){
					
					if(packet.getPath().size() < bfsPath.get(packet.getClient().getId()).size()) { // This ALSO handles the empty path case!
						Client receivingClient = packet.getClient();
						receivingClient.setDelay(Integer.MAX_VALUE);
						it.remove(); 
						continue;
					}
					
					if(currentNode.getId() == packet.getClient().getId()){

						Client receivingClient = packet.getClient();
						receivingClient.setDelay(packet.getDelay());
						it.remove(); // remove this client
					}

					else{
						Client receivingClient = packet.getClient();
						receivingClient.setDelay(Integer.MAX_VALUE);
						it.remove(); // remove this client
					}
					continue;
				
				}
				
				packet.setDelay(packet.getDelay()+1); // packet has not yet reached it's destination
				
				if(currentNode.getBandwidth() > 0){ // only forward packets if the bandwidth is not exhausted yet
					if(!validateEdge(currentNode, packet.getPath().get(packet.getLocation()+1))) {
						packet.getClient().setDelay(Integer.MAX_VALUE);
						it.remove(); // remove this client from llistClient
						continue;
					}
					active.add(currentNode);
					currentNode.setBandwidth(currentNode.getBandwidth()-1);
					packet.setLocation(packet.getLocation()+1);

					 
				}
			}

			for(Node node : active){
				Float bandwidth = bandwidths[node.getId()];
			/*	if (bandwidth.equals(-1.0)){
					bandwidth = Float.MAX_VALUE;
				}*/

				node.setBandwidth(bandwidth);
			}
			active.clear();
		}
		//System.out.println("exiting simulator run");
	}

	public HashMap<Integer,Integer> getDelays(){

		
		HashMap<Integer,Integer> delayMap = new HashMap<Integer,Integer>();
		for(Integer cId : clients.keySet()) {
		delayMap.put(cId, this.clients.get(cId).getDelay());
		}
		return delayMap;

	}

	public List<Client> getClients(List<Integer> listClients){ // not used
		List<Client> clientList = new ArrayList<Client>();
		for(Integer cId : listClients)
		clientList.add(this.clients.get(cId));
		return clientList;
	}


}



class PriorityComparator implements Comparator<Integer>{

	HashMap<Integer,Integer> clientPriorities = new HashMap<Integer,Integer>();

	public PriorityComparator(){
		//default constructor
	}

	public PriorityComparator(HashMap<Integer,Integer> clientPriorities){
		this.clientPriorities = clientPriorities;
	}

	@Override
	public int compare(Integer c1, Integer c2){
		int c1Priority = this.clientPriorities.get(c1);
		int c2Priority = this.clientPriorities.get(c2);

		if(c1Priority > c2Priority) return -1;

		else if(c1Priority < c2Priority) return 1;

		else return c1-c2;
	}
}

class IllegalOutputPathException extends RuntimeException{

	public IllegalOutputPathException(String s){
		super(s);
	}
}
