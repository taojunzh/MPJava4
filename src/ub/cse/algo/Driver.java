package ub.cse.algo;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

/**
 * Class for running the grader. Will take in a command line argument specifying
 * the number of testcases to run.
 */
public class Driver { 

    // The following variables might need to be changed.
    private static String inputFilename;
    private static long studentTime = 0;

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Please provide the number of runs as the argument");
        }
        inputFilename = args[0];
    	
    
        
        MP4Utility mpu = new MP4Utility();
        MP4Utility mpu2 = new MP4Utility(); 
        
        Graph graph = mpu.readFile(inputFilename);
        Graph graphCopy = mpu2.readFile(inputFilename);
        
        Spectrum spectrum = mpu.readInfo(inputFilename + "-info");
        
        Spectrum spectrumCopy = mpu2.readInfo(inputFilename + "-info");
        
 
        Solution student = new Solution(spectrum);
        Spectrum studSpectrum = student.outputSpectrum();
        
        
        if(studSpectrum != null){
        	
        	HashMap<Integer,List<Integer>> outputPath = studSpectrum.getOutputPath(); 
        //	float[] bandwidths = studSpectrum.getBandwidths();
        //	float[] updatedBandwidths = studSpectrum.getUpdatedBandwidths(); // updatedBandwidths are the same as bandwidths unless updated
        	
        	
        	
        	if(outputPath != null) {
     
        	Simulator s = new Simulator(spectrumCopy);
        	HashMap<Integer,List<Node>> outputNodePath = new HashMap<Integer,List<Node>>();
        	
        	HashMap<Integer,Node> nodes = new HashMap<Integer,Node>();
        	
        	for(Integer cId : outputPath.keySet()) {
        		List<Integer> path = outputPath.get(cId);
        		List<Node> nodePath = new ArrayList<Node>();
        		
        		for(Integer nodeId : path) {
        			if(!nodes.containsKey(nodeId)) {
        				Node node = new Node(nodeId,studSpectrum.getUpdatedBandwidths()[nodeId],graphCopy.get(nodeId));
        				nodes.put(nodeId, node);
        			}
        			nodePath.add(nodes.get(nodeId));
        		}
        		outputNodePath.put(cId,nodePath);
        	}
        	s.run(graphCopy, spectrumCopy.getListClients(),outputNodePath,studSpectrum.getUpdatedBandwidths(),studSpectrum.getPriorities(),spectrumCopy.getRuralClients());
        	
      
        	HashMap<Integer,Client> clients = s.getClients();
        	
        	
        	Revenue r = new Revenue();
        	
        	List<Client> fccClientList = new ArrayList<Client>();
        	
        	for(int i = 0; i < spectrumCopy.getFccClients().length; i++) {
        		if(spectrumCopy.getFccClients()[i] == 1)
        			fccClientList.add(clients.get(i));
        	}
        	
        
        	
        	TraversalsSolution ts = new TraversalsSolution(graphCopy, spectrumCopy.getISP(),spectrumCopy.getListClients(),studSpectrum);
        	HashMap<Integer,List<Integer>> bfsPaths = ts.bfs();
        	HashMap<Integer,Integer> shortestDelays = new HashMap<Integer,Integer>();
        	for(Integer node : bfsPaths.keySet())
        		shortestDelays.put(node, bfsPaths.get(node).size()-1);
        	
       
        
        	
        	List<Client> clientList = new ArrayList<Client>();
        	
        	for(Integer client : clients.keySet()) {
        		clientList.add(clients.get(client));
        	}
      
        	
        	
        	int totalRevenue = r.revenue(clientList, spectrumCopy.getAlphas(), spectrumCopy.getBetas(),shortestDelays,
        			spectrumCopy.getClientPayments(), spectrumCopy.getLawsuit(), spectrumCopy.getRhoLawsuit(), spectrumCopy.getFccFine(), spectrumCopy.getRhoFcc(), fccClientList,
        			true, true, spectrumCopy.getBandwidths(), studSpectrum.getUpdatedBandwidths(), spectrumCopy.getCostBandwidth(),
        			graphCopy, spectrumCopy.getISP(),spectrumCopy.getRuralClients());
        	
        	System.out.println("Total revenue generated by your routing algorithm is $" + totalRevenue);
        	
        	
        }
        	
        	else 
        		System.out.println("Your solution is empty!");
        
           
        } else {
            System.out.println("You've returned a null value");
        }
        //System.out.println("=======================================================================================================");
    }
}
