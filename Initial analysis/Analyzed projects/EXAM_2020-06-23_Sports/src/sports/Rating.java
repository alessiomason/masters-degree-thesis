package sports;

public class Rating {
	private Product product;
	private String userName;
	private Integer stars;
	private String comment;
	
	public Rating(Product product, String userName, Integer stars, String comment) {
		super();
		this.product = product;
		this.userName = userName;
		this.stars = stars;
		this.comment = comment;
	}
	
	@Override
	public String toString() {
		return stars + " : " + comment;
	}

	public Product getProduct() {
		return product;
	}

	public Integer getStars() {
		return stars;
	}
}
