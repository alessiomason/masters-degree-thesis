package hydraulic;

/**
 * Represents the sink, i.e. the terminal element of a system
 *
 */
public class Sink extends Element {

	/**
	 * Constructor
	 * @param name name of the sink element
	 */
	public Sink(String name) {
		super(name);
	}

	@Override
	public void connect(Element elem){
		// cannot connect a sink to anything...
		setInput(elem);
	}

	@Override
	void simulate(SimulationObserver observer, double inFlow, boolean enableMaxFlowCheck) {
		if(enableMaxFlowCheck && inFlow > getMaxFlow()){
			observer.notifyFlowError("Sink", getName(), inFlow, getMaxFlow());
		}

		observer.notifyFlow("Sink", getName(), inFlow, SimulationObserver.NO_FLOW);
	}

	@Override
	void layout(String padding, StringBuffer buffer) {
		buffer.append("[").append(getName()).append("]Sink");
	}

}
