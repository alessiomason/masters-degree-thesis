package hydraulic;

/**
 * Represents a split element, a.k.a. T element
 * 
 * During the simulation each downstream element will
 * receive a stream that is half the input stream of the split.
 */

public class Split extends Multisplit {
	
	public static final int MAX_OUTPUTS = 2;

	/**
	 * Constructor
	 * @param name
	 */
	public Split(String name) {
		super(name, MAX_OUTPUTS);
		this.setClassName("Split");
		setProportions(1.0/MAX_OUTPUTS, 1.0/MAX_OUTPUTS);
	}

}
