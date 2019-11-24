package ub.cse.algo;

import java.util.ArrayList;
import java.util.HashMap;

@SuppressWarnings("serial")
public class Graph extends HashMap<Integer, ArrayList<Integer>>{
	private Integer ISP;
	
	public Graph(Integer ISP) {
		this.setISP(ISP);
	}

	public Integer getISP() {
		return ISP;
	}

	public void setISP(Integer ISP) {
		this.ISP = ISP;
	}
}
