package hydraulic;

/**
 *
 * Represents the sink, i.e. the terminal element of a system
 *
 */
public class Sink extends ElementExt {
	
	public Sink(String name) {
		super(name);
		this.setClassName("Sink");
	}
	
	@Override
	public void connect(Element elem) {
		// non ha effetto, Sink non può connettersi più a nulla
	}

	@Override
	public void simulate(double inFlow, SimulationObserver observer) {
		String type = this.getClassName();
		String name = this.getName();
		double outFlow = SimulationObserver.NO_FLOW;
		
		observer.notifyFlow(type, name, inFlow, outFlow);
	}
	
	@Override
	public void simulate(double inFlow, SimulationObserverExt observer, boolean enableMaxFlowCheck) {
		String type = this.getClassName();
		String name = this.getName();
		double outFlow = SimulationObserver.NO_FLOW;
		double maxFlow = this.getMaxFlow();
		
		if (enableMaxFlowCheck && inFlow > maxFlow)
			observer.notifyFlowError(type, name, inFlow, maxFlow);
		
		observer.notifyFlow(type, name, inFlow, outFlow);
	}
	
	@Override
	public int sinkAValle() {
		return 1;
	}

	@Override
	public void layout(StringBuilder[] layoutStrings) {
		layoutStrings[0].append("[" + this.getName() + "]" + this.getClassName());

		layoutStrings[0].append(" *\n");
	}
}
