package hydraulic;

/**
 * Represents a multi-split element, an extension of the Split that allows many outputs
 * 
 * During the simulation each downstream element will
 * receive a stream that is determined by the proportions.
 */

public class Multisplit extends Split {

	private double[] proportions;

	/**
	 * Constructor
	 * @param name the name of the multi-split element
	 * @param numOutput the number of outputs
	 */
	public Multisplit(String name, int numOutput) {
		super(name,numOutput);
	}
	
	/**
	 * Define the proportion of the output flows w.r.t. the input flow.
	 * 
	 * The sum of the proportions should be 1.0 and 
	 * the number of proportions should be equals to the number of outputs.
	 * Otherwise a check would detect an error.
	 * 
	 * @param proportions the proportions of flow for each output
	 */
	public void setProportions(double... proportions) {
		this.proportions = proportions;
	}

	@Override
	void simulate(SimulationObserver observer, double inFlow, boolean enableMaxFlowCheck) {
		if(enableMaxFlowCheck && inFlow > getMaxFlow()){
			observer.notifyFlowError("MultiSplit", getName(), inFlow, getMaxFlow());
		}

		double[] outFlows = new double[proportions.length];
		for(int i=0; i<proportions.length; ++i){
			outFlows[i] = inFlow * proportions[i];
		}
		observer.notifyFlow("MultiSplit", getName(), inFlow, outFlows);
		for(int i=0; i<proportions.length; ++i){
			getOutputs()[i].simulate(observer,outFlows[i], enableMaxFlowCheck);
		}
	}

}
