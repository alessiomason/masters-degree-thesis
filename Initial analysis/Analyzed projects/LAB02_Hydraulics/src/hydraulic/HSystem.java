package hydraulic;

import java.util.Arrays;

/**
 * Main class that act as a container of the elements for
 * the simulation of an hydraulics system 
 * 
 */
public class HSystem {
	
	// attributi
	protected Element[] elementi = new Element[10];
	protected int lastElement = -1;
	
	/**
	 * Adds a new element to the system
	 * @param elem
	 */
	public void addElement(Element elem) {
		if (lastElement >= elementi.length)
			elementi = Arrays.copyOf(elementi, 2*elementi.length);
		
		elementi[++lastElement] = elem;
	}
	
	/**
	 * returns the element added so far to the system.
	 * If no element is present in the system an empty array (length==0) is returned.
	 * 
	 * @return an array of the elements added to the hydraulic system
	 */
	public Element[] getElements() {
		return Arrays.copyOf(elementi, lastElement+1);
	}
	
	/**
	 * Prints the layout of the system starting at each Source
	 */
	public void basicLayout() {
		for (int i=0; i<=lastElement; i++) {
			if (elementi[i] == null)
				continue;
			
			if (elementi[i] instanceof Source)
				HSystem.basicLayoutR(elementi[i]);
		}
	}
	
	private static void basicLayoutR(Element currentElement) {
		Element[] outputElements = new Element[Split.MAX_OUTPUTS];
		
		if (currentElement == null)
			return;
		
		System.out.println(currentElement);
		
		if (currentElement instanceof Split) {
			outputElements = ((Split) currentElement).getOutputs();
			for (int i=0; i<Split.MAX_OUTPUTS; i++)
				HSystem.basicLayoutR(outputElements[i]);
		}
		
		else {
			outputElements[0] = currentElement.getOutput();
			HSystem.basicLayoutR(outputElements[0]);
		}
		
	}
	
	/**
	 * starts the simulation of the system
	 */
	public void simulate(SimulationObserver observer) {
		for (Element e:elementi) {
			if (e != null && e instanceof Source)
				e.simulate(SimulationObserver.NO_FLOW, observer);
		}
	}
}