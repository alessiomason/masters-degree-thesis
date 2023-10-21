package hydraulic;

/**
 * Main class that act as a container of the elements for
 * the simulation of an hydraulics system 
 * 
 */
public class HSystemExt extends HSystem {
	
	/**
	 * Prints the layout of the system starting at each Source
	 */
	public String layout() {
		StringBuilder layoutString = new StringBuilder();
		
		for (int i=0; i<=lastElement; i++) {
			if (elementi[i] == null)
				continue;
			
			if (elementi[i] instanceof Source) {
				int nRighe = ((ElementExt) elementi[i]).sinkAValle() *2-1;
				StringBuilder righeLayout[] = new StringBuilder[nRighe];
				for (int j=0; j<righeLayout.length; j++)
					righeLayout[j] = new StringBuilder("");
				
				((ElementExt) elementi[i]).layout(righeLayout);
				
				for (StringBuilder r:righeLayout)
					layoutString.append(r);
			}
		}
		
		return layoutString.toString();
	}
	
	/**
	 * Deletes a previously added element with the given name from the system
	 */
	public void deleteElement(String name) {
		boolean trovato = false;
		for (int i=0; i<=lastElement; i++) {
			
			if (!trovato && elementi[i].getName().equals(name)) {
				((ElementExt) elementi[i]).delete();
				trovato = true;
			}
			
			if (trovato && i<elementi.length-1)
				elementi[i] = elementi[i+1];
		}
		
		lastElement--;
	}

	/**
	 * starts the simulation of the system; if enableMaxFlowCheck is true,
	 * checks also the elements maximum flows against the input flow
	 */
	public void simulate(SimulationObserverExt observer, boolean enableMaxFlowCheck) {
		for (Element e:elementi) {
			if (e != null && e instanceof Source)
				((ElementExt) e).simulate(SimulationObserver.NO_FLOW, observer, enableMaxFlowCheck);
		}
	}
}
