package hydraulic;

/**
 * Represents the generic abstract element of an hydraulics system.
 * It is the base class for all elements.
 *
 * Any element can be connect to a downstream element
 * using the method {@link #connect(Element) connect()}.
 */
public abstract class Element {
	
	private String name;
	private Element[] outputElements;
	
	/**
	 * Constructor
	 * @param name the name of the element
	 */
	public Element(String name) {
		this.name = name;
		outputElements = new Element[1];
	}
	
	public Element(String name, int nOutputs) {
		this.name = name;
		outputElements = new Element[nOutputs];
	}
	
	public String toString() {
		String type = this.getClass().getName();
		StringBuilder output = new StringBuilder(type + ", " + getName() + ", ");
		
		for (int i=0; i<outputElements.length; i++) {
			output.append(outputElements[i] != null ? outputElements[i].getName() : "nessuna uscita " + i);
			output.append(" | ");
		}
		
		return output.substring(0, output.length()-3);
	}

	/**
	 * getter method
	 * @return the name of the element
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Connects this element to a given element.
	 * The given element will be connected downstream of this element
	 * @param elem the element that will be placed downstream
	 */
	// aggiungendo riferimento a inputElement potrei verificare di non connettere
	// elementi già connessi in precedenza
	public void connect(Element elem) {
		this.outputElements[0] = elem;
	}
	
	public void connect(Element elem, int nOutput) {
		if (nOutput < 0 || nOutput >= outputElements.length)
			return;
		
		this.outputElements[nOutput] = elem;
	}
	
	/**
	 * Retrieves the element connected downstream of this
	 * @return downstream element
	 */
	public Element getOutput() {
		return outputElements[0];
	}
	
	/**
	 * returns the downstream elements
	 * @return array containing the two downstream element
	 */
    public Element[] getOutputs() {
    	return outputElements;
    }
    
    public abstract void simulate(double inFlow, SimulationObserver observer);
}
