package sports;

public class Product {
	private String name;
	private Activity activity;
	private Category category;
	
	public Product(String name, Activity activity, Category category) {
		super();
		this.name = name;
		this.activity = activity;
		this.category = category;
	}

	public String getName() {
		return name;
	}

	public Activity getActivity() {
		return activity;
	}

	public Category getCategory() {
		return category;
	}
	
	
}
