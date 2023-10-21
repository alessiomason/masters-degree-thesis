package hydraulic;

import java.util.Arrays;

/**
 * Represents a multisplit element, an extension of the Split that allows many outputs
 * 
 * During the simulation each downstream element will
 * receive a stream that is determined by the proportions.
 */

public class Multisplit extends ElementExt {
	
	double proporzioni[];

	/**
	 * Constructor
	 * @param name
	 * @param numOutput
	 */
	public Multisplit(String name, int nOutput) {
		super(name, nOutput);
		this.setClassName("Multisplit");
		proporzioni = new double[nOutput];
	}
	
	/**
	 * Define the proportion of the output flows w.r.t. the input flow.
	 * 
	 * The sum of the proportions should be 1.0 and 
	 * the number of proportions should be equals to the number of outputs.
	 * Otherwise a check would detect an error.
	 * 
	 * @param proportions the proportions of flow for each output
	 */
	public void setProportions(double... proportions) {
		for (int i=0; i<proporzioni.length; i++)
			proporzioni[i] = proportions[i];
	}
	
	
	@Override
	public void simulate(double inFlow, SimulationObserver observer) {
		String type = this.getClassName();
		String name = this.getName();
		Element outputElements[] = this.getOutputs();
		int nOutputs = outputElements.length;
		double outFlows[] = new double[nOutputs];
		for (int i=0; i<nOutputs; i++)
			outFlows[i] = inFlow*proporzioni[i];
		
		observer.notifyFlow(type, name, inFlow, outFlows);
		
		for (int i=0; i<nOutputs; i++)
			outputElements[i].simulate(outFlows[i], observer);
	}
	
	@Override
	public void simulate(double inFlow, SimulationObserverExt observer, boolean enableMaxFlowCheck) {
		String type = this.getClassName();
		String name = this.getName();
		Element outputElements[] = this.getOutputs();
		int nOutputs = outputElements.length;
		double outFlows[] = new double[nOutputs];
		for (int i=0; i<nOutputs; i++)
			outFlows[i] = inFlow * proporzioni[i];
		double maxFlow = this.getMaxFlow();
		
		if (enableMaxFlowCheck && inFlow > maxFlow)
			observer.notifyFlowError(type, name, inFlow, maxFlow);
		
		observer.notifyFlow(type, name, inFlow, outFlows);
		
		for (int i=0; i<nOutputs; i++)
			((ElementExt) outputElements[i]).simulate(outFlows[i], observer, enableMaxFlowCheck);
	}

	@Override
	public void layout(StringBuilder[] layoutStrings) {
		int primaRigaLibera = 0;
		int lunghPrimaRiga;
		int nRigheSuccessive;
		
		layoutStrings[0].append("[" + this.getName() + "]" + this.getClassName() + " ");
		
		lunghPrimaRiga = layoutStrings[primaRigaLibera].length();
		
		Element outputElements[] = this.getOutputs();
		
		for (int i=0; i<outputElements.length; i++) {
			if (layoutStrings[primaRigaLibera].length() == 0) {
				for (int j=0; j<lunghPrimaRiga; j++)
					layoutStrings[primaRigaLibera].append(" ");
			}
			layoutStrings[primaRigaLibera].append("+-> ");
			
			if (outputElements[i] == null) {
				layoutStrings[primaRigaLibera].append(" *\n");
				nRigheSuccessive = 1;
			}
			
			else
				nRigheSuccessive = ((ElementExt) outputElements[i]).sinkAValle() *2-1;
			
			for (int j=primaRigaLibera+1; j<=primaRigaLibera+nRigheSuccessive && j<layoutStrings.length; j++) {
				for (int k=0; k<lunghPrimaRiga; k++)
					layoutStrings[j].append(" ");
				
				layoutStrings[j].append("|\n");
			}
			
			if (outputElements[i] != null) {
				StringBuilder[] subsetLayoutStrings = Arrays.copyOfRange(layoutStrings, primaRigaLibera, primaRigaLibera+nRigheSuccessive);
				((ElementExt) outputElements[i]).layout(subsetLayoutStrings);
			}
			
			primaRigaLibera += nRigheSuccessive + 1;
		}
	}
	
	@Override
	public void delete() {
		// non ha effetto, impossibile eliminare un elemento con più uscite
	}
}