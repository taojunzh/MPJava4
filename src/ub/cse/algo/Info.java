package ub.cse.algo;

// TODO: Enum class for determining where in the testcase-info files each input value is present
// I guess it's optional, but I found it useful just in case we have to change the location at which the
// alpha is present in the info file, say 

public class Info{
	
	public static int IS_CLIENT = 0;
	public static int BANDWIDTHS = 1;
	public static int ALPHAS = 2;
	public static int PAYMENTS = 3;
	public static int BETAS = 4;
	public static int IS_FCC = 5;
	public static int IS_RURAL = 6;
	
}

class Heuristics{
	public static final int NUM_SHORTEST_PATHS = 0;
	public static final int SHORTEST_BANDWIDTH_RATIO = 1;
}


