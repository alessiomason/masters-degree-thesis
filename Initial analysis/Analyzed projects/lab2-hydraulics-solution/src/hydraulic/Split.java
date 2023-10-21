package hydraulic;

/**
 * Represents a split element, a.k.a. T element
 * 
 * During the simulation each downstream element will
 * receive a stream that is half the input stream of the split.
 */

public class Split extends Element {

	/**
	 * Constructor
	 * @param name name of the split element
	 */
	public Split(String name) {
		super(name,2);
	}

	Split(String name, int numOutput) {
		super(name,numOutput);
	}

	@Override
	void simulate(SimulationObserver observer, double inFlow, boolean enableMaxFlowCheck) {
		if(enableMaxFlowCheck && inFlow > getMaxFlow()){
			observer.notifyFlowError("Split", getName(), inFlow, getMaxFlow());
		}
		observer.notifyFlow("Split", getName(), inFlow, inFlow/2,inFlow/2);
		for(Element e : getOutputs()){
			e.simulate(observer,inFlow/2, enableMaxFlowCheck);
		}
	}

	@Override
	void layout(String padding, StringBuffer buffer) {
		buffer.append("[").append(getName()).append("]Split ");
		String prePad = padding + blanks(8+getName().length());

		Element[] outputs = getOutputs();
		for(int i=0; i<outputs.length; ++i) {
			String pad = prePad + (i==outputs.length-1?"    ":"|   ");
			if(i>0) buffer.append(prePad);
			buffer.append("+-> ");
			if (outputs[i] == null) buffer.append("*");
			else outputs[i].layout(pad, buffer);
			//buffer.append("\n");
			if(i<outputs.length-1) buffer.append("\n").append(prePad).append("|\n");
		}
	}

}
