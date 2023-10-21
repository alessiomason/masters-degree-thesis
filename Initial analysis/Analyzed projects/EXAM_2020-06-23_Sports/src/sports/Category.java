package sports;
import java.util.*;

public class Category {
	private String name;
	private List<Activity> linkedActivities = new ArrayList<>();
	
	public Category(String name) {
		super();
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public void addActivity(Activity a) {
		linkedActivities.add(a);
	}
	
	public boolean hasActivity(Activity att) {
		if (linkedActivities.contains(att))
			return true;
		return false;
	}
}
