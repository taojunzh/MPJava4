package ub.cse.algo;

import java.util.LinkedList;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Collections;
import java.io.Serializable;




public class Spectrum implements Serializable{

	private Graph graph;
	private int ISP;
	private HashMap<Integer,List<Integer>> outputPath;
	private List<Integer> listNodes;
	private List<Integer> listClients;
	private List<Integer> S;
	private int[] ruralClients;
	private int[] fccClients;
	private float[] bandwidths;
	private float[] updatedbandwidths;
	private int[] priorities;
	private float[] clientPayments;
	private float[] alphas;
	private float[] betas;
	private float rhoFcc;
	private float rhoLawsuit;
	private Problem problem;
	private int lawsuit;
	private int fccFine;
	private int costBandwidth;

	public Spectrum(Graph graph, int ISP, HashMap<Integer,List<Integer>>outputPath,List<Integer> nodes, List<Integer> listClients, List<Integer> S,int[] ruralClients,int[] fccClients,float[] bandwidths, int[] priorities, float[] clientPayments, float[] alphas, float[] betas, float rhoFcc, float rhoLawsuit, Problem problem, int lawsuit, int fccFine, int costBandwidth){

		this.graph = graph;
		this.ISP = ISP;
		this.outputPath = outputPath;
		this.listNodes = listNodes;
		this.listClients = listClients;
		this.bandwidths = bandwidths;
		this.priorities = priorities;
		this.clientPayments = clientPayments;
		this.alphas = alphas;
		this.betas = betas;
		this.rhoFcc = rhoFcc;
		this.rhoLawsuit = rhoLawsuit;
		this.problem = problem;
		this.S = S;
		this.ruralClients = ruralClients;
		this.lawsuit = lawsuit;
		this.fccFine = fccFine;
		this.costBandwidth = costBandwidth;
		this.fccClients = fccClients;
	}
	
	public Spectrum() { 
		
	}

	

	public Graph getGraph(){
		return this.graph;
	}

	public void setGraph(Graph graph){
		this.graph = graph;
	}

	public HashMap<Integer,List<Integer>> getOutputPath(){
		return this.outputPath;
	}

	public void setOutputPath(HashMap<Integer,List<Integer>> outputPath){
		this.outputPath = outputPath;
	}

  public List<Integer> getListNodes(){
		return this.listNodes;
	}

	
	public List<Integer> getListClients(){
		return this.listClients;
	}

	public void setListClients(List<Integer> listClients){
		this.listClients = listClients;
	}

	public List<Integer> getS(){
		return this.S;
	}

	public void setS(List<Integer> S){
		this.S = S;
	}

	public int[] getRuralClients(){
		return this.ruralClients;
	}

	public void setRuralClients(int[] ruralClients){
		this.ruralClients = ruralClients;
	}

	public float[] getBandwidths(){
		return this.bandwidths;
	}

	public void setBandwidths(float[] bandwidths){
		this.bandwidths = bandwidths;
	}

	public int[] getPriorities(){
		return this.priorities;
	}

	public void setPriorities(int[] priorities){
		this.priorities = priorities;
	}

	public float[] getClientPayments(){
		return this.clientPayments;
	}

	public void setClientPayments(float[] clientPayments){
		this.clientPayments = clientPayments;
	}

	public float[] getAlphas(){
		return this.alphas;
	}

	public void setAlphas(float[] alphas){
		this.alphas = alphas;
	}

	public float[] getBetas(){
		return this.betas;
	}

	public void setBetas(float[] betas){
		this.betas = betas;
	}

	public float getRhoFcc(){ 
		return this.rhoFcc;
	}
	
	public void setRhoFcc(float rhoFcc){
		this.rhoFcc = rhoFcc;
	}

	public float getRhoLawsuit(){
		return this.rhoLawsuit;
	}

	public void setRhoLawsuit(float rhoLawsuit){
		this.rhoLawsuit = rhoLawsuit;
	}

	public Problem getProblem(){
		return this.problem;
	}

	public void setProblem(Problem problem){
		this.problem = problem;
	}

	public int getISP(){
		return this.ISP;
	}

	public void setISP(int ISP){
		this.ISP = ISP;
	}

	public int getLawsuit(){
		return this.lawsuit;
	}
	
	public void setLawsuit(int lawsuit){
		this.lawsuit = lawsuit;
	}

	public int getFccFine(){
		return this.fccFine;
	}
	
	public void setFccFine(int fccFine){
		this.fccFine = fccFine;
	}

	public int getCostBandwidth(){
		return this.costBandwidth;
	}
	
	public void setCostBandwidth(int costBandwidth){
		this.costBandwidth = costBandwidth;
	}

	public int[] getFccClients(){
		return this.fccClients;
	}
	
	public void setFccClients(int[] fccClients){
		this.fccClients = fccClients;
	}
	
	public float[] getUpdatedBandwidths(){
		return this.updatedbandwidths;
	}
	
	public void setUpdatedBandwidths(float[] updatedBandwidths){
		this.updatedbandwidths = updatedBandwidths;
	}
}
