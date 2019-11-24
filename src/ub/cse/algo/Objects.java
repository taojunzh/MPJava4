package ub.cse.algo;
import java.util.List;


class Node{ 

	private int id;
	private float bandwidth;
	private List<Integer> neighbors;


	public Node(){
		// default constructor (members initialized to default values)
	}

	public Node(int id, float bandwidth, List<Integer> neighbors){
		this.id = id;
		this.bandwidth = bandwidth;
		this.neighbors = neighbors;
	}

	// getters and setters

	public int getId(){
		return this.id;
	}

	public void setId(int id){
		this.id = id;
	}

	public float getBandwidth(){
		return this.bandwidth;
	}

	public void setBandwidth(float bandwidth){
		this.bandwidth = bandwidth;
	}

	public List<Integer> getNeighbors(){
		return this.neighbors;
	}

	public void setNeighbors(List<Integer> neighbors){
		this.neighbors = neighbors;
	}

	@Override
	public boolean equals(Object o){ // makes it easier to compare Node objects
		if(!(o instanceof Node))
		return false;
		Node otherNode = (Node) o;
		if(this.getId() == otherNode.getId())
		return true;
		return false;
	}

	@Override
	public int hashCode(){ // had to be overridden because equals() is overridden
		return this.getId();
	}
}


class Client extends Node{ // Every client is a router

	private int delay;
	private int delayOptimal;
	private List<Node> path; // determined by student algorithm
	private boolean isRural;
	private boolean hasReceived = false;
	private Packet pkt;

	public Client(){
		// default constructor
	}

	public Client(int id, float bandwidth, List<Integer> neighbors, int delay,int delayOptimal,List<Node> path, boolean isRural,boolean hasReceived){

		super(id,bandwidth,neighbors);
		this.delay = delay;
		this.delayOptimal = delayOptimal;
		this.path = path;
		this.isRural = isRural;
		this.hasReceived = hasReceived;
		this.pkt = pkt;

	}

	// getters and setters

	public int getDelay(){
		return this.delay;
	}

	public void setDelay(int delay){
		this.delay = delay;
	}

	public int getDelayOptimal(){
		return this.delayOptimal;
	}

	public void setDelayOptimal(int delayOptimal){
		this.delayOptimal = delayOptimal;
	}

	public List<Node> getPath(){
		return this.path;
	}

	public void setPath(List<Node> path){
		this.path = path;
	}

	public boolean getIsRural(){
		return this.isRural;
	}

	public void setIsRural(boolean isRural){
		this.isRural = isRural;
	}

	public boolean getHasReceived(){
		return this.hasReceived;
	}

	public void setHasReceived(boolean hasReceived){
		this.hasReceived = hasReceived;
	}

}

class Packet{ 

	
	private Client client;
	private int location;
	private int priority;
	private List<Node> path;
	private int delay;

	public Packet(Client client, int location, int priority, List<Node> path){
		this.client = client;
		this.location = location;
		this.priority = priority;
		this.path = path;
	}

	// getters and setters

	public Client getClient(){
		return this.client;
	}

	public void setClient(Client client){
		this.client = client;
	}

	public int getLocation(){
		return this.location;
	}

	public void setLocation(int location){
		this.location = location;
	}

	public List<Node> getPath(){
		return this.path;
	}

	public void setPath(List<Node> path){
		this.path = path;
	}

	public int getDelay(){
		return this.delay;
	}

	public void setDelay(int delay){
		this.delay = delay;
	}
	
	public int getPriority() {
		return this.priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}



}
