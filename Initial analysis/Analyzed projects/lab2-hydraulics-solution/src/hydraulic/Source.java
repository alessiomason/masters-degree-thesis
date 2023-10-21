package hydraulic;

/**
 * Represents a source of water, i.e. the initial element for the simulation.
 *
 * Lo status of the source is defined through the method
 * {@link #setFlow(double) setFlow()}.
 */
public class Source extends Element {

	private double flow;

	/**
	 * constructor
	 * @param name name of the source element
	 */
	public Source(String name) {
		super(name);
	}

	/**
	 * Define the flow of the source to be used during the simulation
	 *
	 * @param flow flow of the source (in cubic meters per hour)
	 */
	public void setFlow(double flow){
		this.flow = flow;
	}

	@Override
	void simulate(SimulationObserver observer, double inFlow, boolean enableMaxFlowCheck) {
		observer.notifyFlow("Source", getName(), SimulationObserver.NO_FLOW, flow);
		getOutput().simulate(observer,flow, enableMaxFlowCheck);
	}

	@Override
	void layout(String padding, StringBuffer buffer) {
		buffer.append("[").append(getName()).append("]Source -> ");
		if(getOutput()==null) buffer.append("*");
		else getOutput().layout(blanks(12+getName().length()),buffer);
	}

}
