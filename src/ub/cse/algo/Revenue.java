package ub.cse.algo;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;



class Revenue {

	private List<Client> complainingClients;
	
	

	public Revenue() {
		this.complainingClients = new ArrayList<Client>();
	}
	

	public float pen_0(Client client, int optimalDelay, float alpha, float pmt, int[] isRural) {
		
		if(isRural[client.getId()] == 1) {
			//System.out.println("yes isRural is true");
			return pmt;
		
		}
		if ((float) client.getDelay() > (alpha * optimalDelay)) {
		
		
			return 0.0f;
		}
		
		return pmt;

	}

	public int pen_1(int penAmount, float rho, List<Client> clientList, Graph graph, int ISP, float[] betas, HashMap<Integer,Integer> delayMap) {
		

		if (rho > 1 || rho < 0)
			throw new IllegalArgumentException("Value of Rho is either greater than 1 or lesser than 0");

		this.findComplainingClients(clientList, graph, ISP, betas, delayMap);

		if (this.complainingClients.size() >  (int)Math.floor((rho * clientList.size()))) {
			
			return -penAmount;
		}

		return 0;

	}

	public int pen_2(int fineAmount, float rho, List<Client> fccClients, Graph graph, int ISP, float[] betas, HashMap<Integer,Integer> delayMap) {
		

		if (rho > 1 || rho < 0)
			throw new IllegalArgumentException("Value of Rho is either greater than 1 or lesser than 0");
		

		
		this.findComplainingClients(fccClients, graph, ISP, betas, delayMap);
		
	
		if (this.complainingClients.size() >  (int)Math.floor((rho * fccClients.size()))) {
		
			return -fineAmount;
		} 
		
		return 0;



	}

	public int pen_bandwidth(float[] originalBandwidths, float[] updatedBandwidths, int upCost) {
		
		int cost = 0;
		if (updatedBandwidths == null){
			return 0;
		}
		if (originalBandwidths.length != updatedBandwidths.length)
			throw new IndexOutOfBoundsException("originalBandwidths size is not the same as updatedBandwidths size");

		for (int i = 0; i < originalBandwidths.length; ++i) {
			int diff = (int) (updatedBandwidths[i] - originalBandwidths[i]);
			if(diff < 0)
				throw new IllegalArgumentException("You are NOT allowed to decrease the bandwidths");
		
			if(diff > 0) 
				cost += upCost * diff;
		}
		return -cost;
	}
	
	

	public int pen_p5(List<Client> clientList, float[] alphaValues, int[] optimalDelays, float[] paymentValues,
			float[] originalBandwidths, float[] updatedBandwidths, int upCost) {

		if (this.complainingClients.size() > 0)
			return pen_bandwidth(originalBandwidths, updatedBandwidths, upCost);
		else
			return revenue(clientList, alphaValues, null,  null, paymentValues, 0, 0.0f, 0, 0.0f, null, true,
					false, originalBandwidths, updatedBandwidths, upCost, null, 0,null); // notion
																							// of
																							// complaining
																							// clients
																							// different
																							// for
																							// p5

	}

	
	public int revenue(List<Client> clients, float[] alphaValues, float[] betaValues, HashMap<Integer,Integer> delayMap,
			float[] paymentValues, int lawsuit, float rhoLawsuit, int fccFine, float rhoFcc, List<Client> fccClients,
			boolean pen_1, boolean pen_2, float[] originalBandwidths, float[] updatedBandwidths, int upCost,
			Graph graph, Integer ISP,int[] isRural) {

		int finalRevenue = 0;
		
		

		for (Client client : clients) {
			int currentRevenue = (int) this.pen_0(client, delayMap.get(client.getId()), alphaValues[client.getId()],
					paymentValues[client.getId()], isRural);
			finalRevenue += currentRevenue;
		}
	
		
	//	System.out.println("before pen_1:" + finalRevenue);
	
		if (pen_1)
			finalRevenue += this.pen_1(lawsuit, rhoLawsuit, clients,graph,ISP,betaValues,delayMap);
		
	//	System.out.println("before pen_2:" +finalRevenue);
		

		if (pen_2)
			finalRevenue += this.pen_2(fccFine, rhoFcc, fccClients,graph,ISP,betaValues,delayMap);
		
	//	System.out.println("before pen_bandwidth:" +finalRevenue);
		

		finalRevenue += this.pen_bandwidth(originalBandwidths, updatedBandwidths, upCost);
		
	//	System.out.println("after pen_bandwidth:" +finalRevenue);
		
	//	System.out.println("-------------------------------------------------------------------------------------------");

	

		return finalRevenue;

	}

	// Generates a list of complaining clients from the input list of clients
	public void findComplainingClients(List<Client> clients, Graph graph, Integer ISP, float[] betas, HashMap<Integer,Integer> delayMap) { // needs
									
	
	 this.complainingClients = new ArrayList<Client>();
		
		
		for(Client c : clients) {
			if(c.getDelay() > (int)(betas[c.getId()] * delayMap.get(c.getId()))) {
				this.complainingClients.add(c);
			}
		}
	}

	public int calculateDelay(List<Integer> path) {
		return path.size();
	}

}
