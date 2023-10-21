package hydraulic;

/**
 * Represents a source of water, i.e. the initial element for the simulation.
 *
 * The status of the source is defined through the method
 * {@link #setFlow(double) setFlow()}.
 */
public class Source extends ElementExt {
	
	private double portata;	// metri cubi al secondo erogati dalla sorgente

	public Source(String name) {
		super(name);
		this.setClassName("Source");
		portata = 0;
	}

	/**
	 * defines the flow produced by the source
	 * 
	 * @param flow
	 */
	public void setFlow(double flow) {
		this.portata = flow;
	}
	
	@Override
	public void setMaxFlow (double maxFlow) {
		// non ha effetto, Source non ha una portata in ingresso
	}
	
	/**
	 * retrieves the flow produced by the source
	 * 
	 * @return flow
	 */
	public double getFlow() {
		return portata;
	}
	
	@Override
	public void setInputElement(ElementExt inputElement) {
		super.setInputElement(null);
	}

	@Override
	public void simulate(double inFlow, SimulationObserver observer) {
		String type = this.getClassName();
		String name = this.getName();
		Element outputElement = this.getOutput();
		
		observer.notifyFlow(type, name, SimulationObserver.NO_FLOW, portata);
		
		outputElement.simulate(portata, observer);
	}

	@Override
	public void simulate(double inFlow, SimulationObserverExt observer, boolean enableMaxFlowCheck) {
		String type = this.getClassName();
		String name = this.getName();
		Element outputElement = this.getOutput();
		
		observer.notifyFlow(type, name, SimulationObserver.NO_FLOW, portata);
		
		((ElementExt) outputElement).simulate(portata, observer, enableMaxFlowCheck);
	}

	@Override
	public void layout(StringBuilder[] layoutStrings) {
		layoutStrings[0].append("[" + this.getName() + "]" + this.getClassName());
		
		Element outputElement = this.getOutput();
		if (outputElement == null)
			layoutStrings[0].append(" *\n");
		else {
			layoutStrings[0].append(" -> ");
			((ElementExt) outputElement).layout(layoutStrings);
		}
	}
}
