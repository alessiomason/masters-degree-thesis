package hydraulic;

public abstract class ElementExt extends Element {
	
	private String className;
	private ElementExt inputElement;
	private double maxFlow;

	public ElementExt(String name) {
		super(name);
	}

	public ElementExt(String name, int nOutputs) {
		super(name, nOutputs);
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}
	
	public ElementExt getInputElement() {
		return inputElement;
	}

	public void setInputElement(ElementExt inputElement) {
		this.inputElement = inputElement;
	}

	public void setMaxFlow(double maxFlow) {
		this.maxFlow = maxFlow;
	}

	public double getMaxFlow() {
		return maxFlow;
	}
	
	@Override
	public void connect(Element elem) {
		super.connect(elem);
		((ElementExt) elem).setInputElement(this);
	}
	
	@Override
	public void connect(Element elem, int nOutput) {
		super.connect(elem, nOutput);
		((ElementExt) elem).setInputElement(this);
	}

	/**
	 * Conta il numero di Sink presenti a valle dell'elemento
	 * @return numero di Sink
	 */
	public int sinkAValle() {
		int nSink = 0;
		Element[] outputElements = this.getOutputs();
		
		for (Element e:outputElements)
			if (e != null)
				nSink += ((ElementExt) e).sinkAValle();
		
		return nSink;
	}
	
	public abstract void layout(StringBuilder[] layoutStrings);
	
	public void delete() {
		((ElementExt) this.getOutput()).setInputElement(inputElement);
		inputElement.connect(this.getOutput());
	}
	
	public abstract void simulate(double inFlow, SimulationObserverExt observer, boolean enableMaxFlowCheck);
}
