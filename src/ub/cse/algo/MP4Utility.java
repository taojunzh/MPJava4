package ub.cse.algo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;


/**
 * Utility class for reading of stable matching problem input files.
 *
 * @param <E> Type of object returned by reading an input file
 */
public class MP4Utility {

    //TODO: Implement functionality to read in the supplemental input values

    /**
     * Reads the file at the specified location and parses it to get the needed
     * information for the problem.
     *
     * @param inputFilePath File path for problem input
     * @return Object containing all needed data structures for the problem
     */
	
	private Graph graph; 
	private Spectrum spectrum;
	
    
    public Graph readFile(String inputFilePath) {
        Integer ISP;
       // Graph graph = null;
        BufferedReader bufferedReader = null;
        
      

        try {
            bufferedReader = new BufferedReader(new FileReader(inputFilePath));
        }
        catch (FileNotFoundException e) {
            System.err.println("Unable to open the file " + inputFilePath);
            e.printStackTrace();
        }

        try{
            ISP = Integer.valueOf(bufferedReader.readLine());
            this.graph = new Graph(ISP);
            this.graph.setISP(ISP);
            String line = null;
            Integer node = 0;
            while((line = bufferedReader.readLine()) != null) {
                graph.put(node, new ArrayList<Integer>());
                String[] parts = line.trim().split("\\s+");
                for(String neighbor : parts){
                    Integer n = Integer.parseInt(neighbor);
                    graph.get(node).add(n);
                }
                node++;
            }

        }
        catch (IOException e) {
            e.printStackTrace();
        }
        this.graph = graph;
        return graph;
    }
    
    public Spectrum readInfo(String inputFilePath){
    	
    	List<Integer> listClients = new ArrayList<Integer>();
    	int numNodes = this.graph.size();
    	float[] bandwidths = new float[numNodes];
    	float[] updatedBandwidths = new float[numNodes];
    	float[] alphas = new float[numNodes];
    	float[] betas = new float[numNodes];
    	float[] payments = new float[numNodes];
    	int[] isRurals = new int[numNodes];
    	int[] isFccs = new int[numNodes];
    	for(int i = 0; i < numNodes; ++i){
    		alphas[i] = 0.0f;
    		betas[i] = 0.0f;
    		payments[i] = 0.0f;
    		isRurals[i] = 0;
    		isFccs[i] = 0;
    	}
    	float rho1 = 0.0f;
    	float rho2 = 0.0f;
    	int lawsuit = 0;
    	int fccFine = 0;
    	int costBandwidth = 0;
    	Spectrum spectrum = null;
    	
    	   BufferedReader bufferedReader = null;

           try {
               bufferedReader = new BufferedReader(new FileReader(inputFilePath));
           }
           catch (FileNotFoundException e) {
               System.err.println("Unable to open the file " + inputFilePath);
               e.printStackTrace();
           }
           
           try{
        	   if(Problem.PROBLEM == 3 || Problem.PROBLEM == 4){
        		   rho1 = Float.parseFloat(bufferedReader.readLine());
        		   rho2 = Float.parseFloat(bufferedReader.readLine());
        		   lawsuit = Integer.parseInt(bufferedReader.readLine());
        		   fccFine = Integer.parseInt(bufferedReader.readLine());
        	   }
        	   
        	   if(Problem.PROBLEM == 3 || Problem.PROBLEM == 4 || Problem.PROBLEM == 5){
        		   costBandwidth = Integer.parseInt(bufferedReader.readLine());
        	   }
        	   
        	   String line = null;
        	   int node = 0;
        	   
        	   while((line = bufferedReader.readLine()) != null){
        		   String[] spec = line.trim().split("\\s+");
        		   int specLength = spec.length;
        		   if(Integer.parseInt(spec[Info.IS_CLIENT]) == 1){
        			   listClients.add(node);
        			   alphas[node] = Float.parseFloat(spec[Info.ALPHAS]);
        			   payments[node] = Integer.parseInt(spec[Info.PAYMENTS]);
        		   }
        		   
        		   if(Problem.PROBLEM == 3 || Problem.PROBLEM == 4){
        			   if(Integer.parseInt(spec[Info.IS_CLIENT]) == 1) {
        			   betas[node] = Float.parseFloat(spec[Info.BETAS]);
        			   /* int fccNode*/ isFccs[node] = Integer.parseInt(spec[Info.IS_FCC]);
        		//	   if(fccNode == 1)
        		//		   isFccs[node] = 1;
        			   }
        		   }
        		  if(Problem.PROBLEM == 4){
        			  if(Integer.parseInt(spec[Info.IS_CLIENT]) == 1)
        				  isRurals[node] = Integer.parseInt(spec[Info.IS_RURAL]);
        		  }

        		  bandwidths[node] = Float.parseFloat(spec[Info.BANDWIDTHS]);
        		  if (bandwidths[node] < 0){
        		  	bandwidths[node] = Float.MAX_VALUE;
				  }
        		  if(isRurals[node] == 1)
        			  alphas[node] = Float.MAX_VALUE;
        		  node += 1;
        	   }
        	   
        	   
        	  for(int i = 0; i < bandwidths.length; i++)
        		  updatedBandwidths[i] = bandwidths[i];
        	 
        	  
        	
        	  
        	spectrum = new Spectrum();
        	spectrum.setISP(this.graph.getISP());
        	spectrum.setListClients(listClients);
        	spectrum.setBandwidths(bandwidths);
        	spectrum.setUpdatedBandwidths(updatedBandwidths);
        	spectrum.setAlphas(alphas);
        	spectrum.setBetas(betas);
        	spectrum.setClientPayments(payments);
        	spectrum.setRuralClients(isRurals);
        	spectrum.setFccClients(isFccs);
        	spectrum.setRhoFcc(rho2);
        	spectrum.setRhoLawsuit(rho1);
        	spectrum.setLawsuit(lawsuit);
        	spectrum.setFccFine(fccFine);
        	spectrum.setCostBandwidth(costBandwidth);
        	
        
        	/*for(int i = 0; i < this.graph.size(); ++i) { // looks good! 
        		System.out.println(i + ", " + spectrum.getBandwidths()[i] + ", " + spectrum.getAlphas()[i] + ", " + spectrum.getClientPayments()[i] + ", " + spectrum.getBetas()[i] + ", " + spectrum.getFccClients()[i]);
        	}*/ 
        	
        	spectrum.setGraph(this.graph);
        	int[] priorities = new int[this.graph.size()];
        	for(int i = 0; i < priorities.length; ++i) {
        		priorities[i] = 0; // 0 is default priority
        	}
        	spectrum.setPriorities(priorities);
        	   
           }
           catch (IOException e){
        	   e.printStackTrace();
           }
    	
    	return spectrum;
    }

 }
