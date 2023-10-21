package mountainhuts;

public class AltitudeRange {
	public final static String defaultValue = "0-INF";
	
	private String range;
	private int minValue;
	private int maxValue;
	
	public AltitudeRange(String range) {
		this.range = range;
		
		String[] values = range.split("-");
		this.minValue = Integer.parseInt(values[0]);
		this.maxValue = Integer.parseInt(values[1]);
	}
	
	public String getRange() {
		return range;
	}

	public boolean includes(Integer altitude) {
		if (altitude >= minValue && altitude <= maxValue)
			return true;
		
		return false;
	}
}
