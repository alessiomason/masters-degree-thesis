package hydraulic;

/**
 * Represents a tap that can interrupt the flow.
 * 
 * The status of the tap is defined by the method
 * {@link #setOpen(boolean) setOpen()}.
 */

public class Tap extends ElementExt {
	
	private boolean open;

	public Tap(String name) {
		super(name);
		this.setClassName("Tap");
		open = false;
	}
	
	/**
	 * Defines whether the tap is open or closed.
	 * 
	 * @param open  opening level
	 */
	public void setOpen(boolean open) {
		this.open = open;
	}
	
	/**
	 * Tells whether the tap is open or closed.
	 * 
	 * @return open
	 */
	public boolean getOpen() {
		return open;
	}

	@Override
	public void simulate(double inFlow, SimulationObserver observer) {
		String type = this.getClassName();
		String name = this.getName();
		Element outputElement = this.getOutput();
		double outFlow = open ? inFlow : 0;
		
		observer.notifyFlow(type, name, inFlow, outFlow);

		outputElement.simulate(outFlow, observer);
	}
	
	@Override
	public void simulate(double inFlow, SimulationObserverExt observer, boolean enableMaxFlowCheck) {
		String type = this.getClassName();
		String name = this.getName();
		Element outputElement = this.getOutput();
		double outFlow = open ? inFlow : 0;
		double maxFlow = this.getMaxFlow();
		
		if (enableMaxFlowCheck && inFlow > maxFlow)
			observer.notifyFlowError(type, name, inFlow, maxFlow);
		
		observer.notifyFlow(type, name, inFlow, outFlow);
		
		((ElementExt) outputElement).simulate(outFlow, observer, enableMaxFlowCheck);
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
