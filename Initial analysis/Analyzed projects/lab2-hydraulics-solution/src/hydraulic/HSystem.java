package hydraulic;

import java.util.Arrays;
//import java.util.Collection;
//import java.util.ArrayList;

/**
 * Main class that acts as a container of the elements for
 * the simulation of an hydraulics system 
 */
public class HSystem {
//	private Collection<Elements> elements = new ArrayList<>();
	private Element[] elements = new Element[10];
	private int next;

// R1
	/**
	 * Adds a new element to the system
	 * @param elem the new element to be added to the system
	 */
	public void addElement(Element elem){
		//elements.add(elem);
		if(next==elements.length){
			elements = Arrays.copyOf(elements,elements.length*2);
		}
		elements[next++] = elem;
	}
	
	/**
	 * returns the element added so far to the system
	 * @return an array of elements whose length is equal to 
	 * 							the number of added elements
	 */
	public Element[] getElements(){
		return Arrays.copyOf(elements,next);
//		return elements.toArray(new Element[elements.size()]);
	}

// R4
	/**
	 * Starts the simulation of the system
	 */
	public void simulate(SimulationObserver observer){
		simulate(observer,false);
	}

// R6
	/**
	 * Prints the layout of the system starting at each Source
	 */
	public String layout(){
		StringBuffer res = new StringBuffer();
		for(Element e : elements){
			if( e instanceof Source){
				e.layout("", res);
			}
		}
		return res.toString();
	}

// R7
	/**
	 * Deletes a previously added element with the given name from the system
	 */
	public boolean deleteElement(String name) {
		for(Element current : elements){
			if(current.getName().equals(name)){
				Element output= null;
				if( current instanceof Split ){
					int count = 0;
					for(Element e : current.getOutputs()){
						if(e!=null){
							count++;
							output = e;
						}
					}
					if(count>1){
						return false;
					}
				}else{
					output = current.getOutput();
				}

				Element input = current.getInput();
				if(input!=null) {
					input.replaceWith(current, output);
				} else {
					if( output != null ) output.setInput(null);
				}
				remove(current);
				break;
			}
		}
		return true;
	}

	private void remove(Element e){
		boolean found=false;
		for(int i=0; i<next-1; ++i){
			if( elements[i] == e ){
				found = true;
			}
			if(found){
				elements[i] = elements[i+1];
			}
		}
		elements[next-1] = null;
		next--;
	}

// R8
	/**
	 * starts the simulation of the system; if {@code enableMaxFlowCheck} is {@code true},
	 * checks also the elements maximum flows against the input flow
	 */
	public void simulate(SimulationObserver observer, boolean enableMaxFlowCheck) {
		for(Element e : elements){
			if( e instanceof Source ){
				e.simulate(observer,SimulationObserver.NO_FLOW,enableMaxFlowCheck);
			}
		}
	}
}
