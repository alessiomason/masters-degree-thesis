package hydraulic;


import java.util.Arrays;

/**
 * Represents the generic abstract element of an hydraulics system.
 * It is the base class for all elements.
 *
 * Any element can be connect to a downstream element
 * using the method {@link #connect(Element) connect()}.
 * 
 * The class is abstract since it is not intended to be instantiated,
 * though all methods are defined to make subclass implementation easier.
 */
public abstract class Element {

	private final String name;
	private final Element[] outputs;
	private Element input;
	private double maxFlow;

	Element(String name){
		this.name=name;
		outputs = new Element[1];
	}

	Element(String name, final int n){
		this.name=name;
		outputs = new Element[n];
	}

	/**
	 * getter method for the name of the element
	 * 
	 * @return the name of the element
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Connects this element to a given element.
	 * The given element will be connected downstream of this element
	 * 
	 * In case of element with multiple outputs this method operates on the first one,
	 * it is equivalent to calling {@code connect(elem,0)}. 
	 * 
	 * @param elem the element that will be placed downstream
	 */
	public void connect(Element elem) {
		outputs[0] = elem;
		elem.input = this;
	}
	
	/**
	 * Connects a specific output of this element to a given element.
	 * The given element will be connected downstream of this element
	 * 
	 * @param elem the element that will be placed downstream
	 * @param index the output index that will be used for the connection
	 */
	public void connect(Element elem, int index){
		outputs[index] = elem;
		elem.input = this;
	}
	
	/**
	 * Retrieves the single element connected downstream of this element
	 * 
	 * @return downstream element
	 */
	public Element getOutput(){
		return outputs[0];
	}

	/**
	 * Retrieves the elements connected downstream of this element
	 * 
	 * @return downstream element
	 */
	public Element[] getOutputs(){
		return Arrays.copyOf(outputs, outputs.length);
	}
	
	/**
	 * Defines the maximum input flow acceptable for this element
	 * 
	 * @param maxFlow maximum allowed input flow
	 */
	public void setMaxFlow(double maxFlow) {
		this.maxFlow = maxFlow;
	}

	abstract void simulate(SimulationObserver observer, double inFlow, boolean enableMaxFlowCheck);

	abstract void layout(String padding, StringBuffer buffer);

	protected String blanks(int n){
		return String.format("%"+n+"s","");
	}

	Element getInput(){
		return input;
	}

	public void replaceWith(Element current, Element output){
		for(int i=0; i<outputs.length; ++i){
			if(outputs[i] == current){
				outputs[i] = output;
				if(output!=null) {
					output.input = this;
				}
			}
		}
	}

	public void setInput(Element input){
		this.input = input;
	}

	protected double getMaxFlow(){
		return maxFlow;
	}
}
