package hydraulic;

/**
 * Represents a tap that can interrupt the flow.
 * 
 * The status of the tap is defined by the method
 * {@link #setOpen(boolean) setOpen()}.
 */

public class Tap extends Element {

	private boolean open;

	/**
	 * Constructor
	 * @param name name of the tap element
	 */
	public Tap(String name) {
		super(name);
	}

	/**
	 * Set whether the tap is open or not. The status is used during the simulation.
	 *
	 * @param open opening status of the tap
	 */
	public void setOpen(boolean open){
		this.open = open;
	}

	@Override
	void simulate(SimulationObserver observer, double inFlow, boolean enableMaxFlowCheck) {
		if(enableMaxFlowCheck && inFlow > getMaxFlow()){
			observer.notifyFlowError("Tap", getName(), inFlow, getMaxFlow());
		}
		double outFlow = open?inFlow:0.0;
		observer.notifyFlow("Tap", getName(), inFlow, outFlow);
		getOutput().simulate(observer,outFlow, enableMaxFlowCheck);
	}

	@Override
	void layout(String padding, StringBuffer buffer) {
		buffer.append("[").append(getName()).append("]Tap -> ");
		if(getOutput()==null) buffer.append("*");
		else getOutput().layout(blanks(padding.length()+9+getName().length()),buffer);
	}

}
