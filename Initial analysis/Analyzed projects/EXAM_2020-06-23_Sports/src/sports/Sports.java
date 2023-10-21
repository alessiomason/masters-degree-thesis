package sports;
import java.util.*;
import static java.util.Comparator.*;
import static java.util.stream.Collectors.*;


/**
 * Facade class for the research evaluation system
 *
 */
public class Sports {
	
	private Map<String, Activity> activities = new HashMap<>();
	private Map<String, Category> categories = new HashMap<>();
	private Map<String, Product> products = new HashMap<>();
	private List<Rating> ratings = new ArrayList<>();

    //R1
    /**
     * Define the activities types treated in the portal.
     * The method can be invoked multiple times to add different activities.
     * 
     * @param actvities names of the activities
     * @throws SportsException thrown if no activity is provided
     */
    public void defineActivities (String... activities) throws SportsException {
    	if (activities.length == 0)
    		throw new SportsException("Missing activities");
    	
    	for (String a:activities) {
    		Activity att = new Activity(a);
    		this.activities.put(a, att);
    	}
    }

    /**
     * Retrieves the names of the defined activities.
     * 
     * @return activities names sorted alphabetically
     */
    public List<String> getActivities() {
        return activities.values().stream().map(a -> a.getName())
        						  		   .sorted()
        						  		   .collect(toList());
    }


    /**
     * Add a new category of sport products and the linked activities
     * 
     * @param name name of the new category
     * @param activities reference activities for the category
     * @throws SportsException thrown if any of the specified activity does not exist
     */
    public void addCategory(String name, String... linkedActivities) throws SportsException {
    	Category category = new Category(name);
    	
    	for (String a:linkedActivities) {
    		Activity activity = activities.get(a);
    		if (activity == null)
    			throw new SportsException("Missing activity");
    		category.addActivity(activity);
    	}
    	
    	this.categories.put(name, category);
    }

    /**
     * Retrieves number of categories.
     * 
     * @return categories count
     */
    public int countCategories() {
        return categories.size();
    }

    /**
     * Retrieves all the categories linked to a given activity.
     * 
     * @param activity the activity of interest
     * @return list of categories (sorted alphabetically)
     */
    public List<String> getCategoriesForActivity(String activity) {
    	List<String> lista = new ArrayList<String>();
    	Activity att = activities.get(activity);
    	if (att == null)
    		return lista;
    	
    	for (Category c:categories.values()) {
    		if (c.hasActivity(att))
    			lista.add(c.getName());
    	}
        return lista.stream().sorted().collect(toList());
    }

    //R2
    /**
     * Add a research group and the relative disciplines.
     * 
     * @param name name of the research group
     * @param disciplines list of disciplines
     * @throws SportsException thrown in case of duplicate name
     */
    public void addProduct(String name, String activityName, String categoryName) throws SportsException {
    	Product p = products.get(name);
    	if (p != null)
    		throw new SportsException("Already existing product");
    	
    	Activity activity = activities.get(activityName);
    	Category category = categories.get(categoryName);
    	
    	p = new Product(name, activity, category);
    	products.put(name, p);
    }

    /**
     * Retrieves the list of products for a given category.
     * The list is sorted alphabetically.
     * 
     * @param categoryName name of the category
     * @return list of products
     */
    public List<String> getProductsForCategory(String categoryName) {
    	List<String> lista = new ArrayList<String>();
    	Category category = categories.get(categoryName);
    	if (category == null)
    		return lista;
    	
    	for (Product p:products.values()) {
    		if (p.getCategory().equals(category))
    			lista.add(p.getName());
    	}
        return lista.stream().sorted().collect(toList());
    }

    /**
     * Retrieves the list of products for a given activity.
     * The list is sorted alphabetically.
     * 
     * @param activityName name of the activity
     * @return list of products
     */
    public List<String> getProductsForActivity(String activityName){
    	List<String> lista = new ArrayList<String>();
    	Activity activity = activities.get(activityName);
    	if (activity == null)
    		return lista;
    	
    	for (Product p:products.values()) {
    		if (p.getActivity().equals(activity))
    			lista.add(p.getName());
    	}
        return lista.stream().sorted().collect(toList());
    }

    /**
     * Retrieves the list of products for a given activity and a set of categories
     * The list is sorted alphabetically.
     * 
     * @param activityName name of the activity
     * @param categoryNames names of the categories
     * @return list of products
     */
    public List<String> getProducts(String activityName, String... categoryNames){
    	List<String> lista = new ArrayList<String>();
    	Activity activity = activities.get(activityName);
    	if (activity == null)
    		return lista;
    	List<Category> categoriesList = new ArrayList<Category>();
    	for (String c:categoryNames) {
    		Category cat = this.categories.get(c);
    		if (cat != null)
    			categoriesList.add(cat);
    	}
    	
    	for (Product p:products.values()) {
    		if (p.getActivity().equals(activity) && categoriesList.contains(p.getCategory()))
    			lista.add(p.getName());
    	}
        return lista.stream().sorted().collect(toList());
    }

    //    //R3
    /**
     * Add a new product rating
     * 
     * @param productName name of the product
     * @param userName name of the user submitting the rating
     * @param numStars score of the rating in stars
     * @param comment comment for the rating
     * @throws SportsException thrown numStars is not correct
     */
    public void addRating(String productName, String userName, int numStars, String comment) throws SportsException {
    	Product p = products.get(productName);
    	if (numStars < 0 || numStars > 5)
    		throw new SportsException("Stars not valid");
    	
    	Rating r = new Rating(p, userName, numStars, comment);
    	ratings.add(r);
    }



    /**
     * Retrieves the ratings for the given product.
     * The ratings are sorted by descending number of stars.
     * 
     * @param productName name of the product
     * @return list of ratings sorted by stars
     */
    public List<String> getRatingsForProduct(String productName) {
    	Product p = products.get(productName);
        return ratings.stream().filter(r -> r.getProduct().equals(p))
        					   .sorted(comparing(Rating::getStars, reverseOrder()))
        					   .map(Rating::toString)
        					   .collect(toList());
    }


    //R4
    /**
     * Returns the average number of stars of the rating for the given product.
     * 
     * 
     * @param productName name of the product
     * @return average rating
     */
    public double getStarsOfProduct(String productName) {
    	Product p = products.get(productName);
        return ratings.stream().filter(r -> r.getProduct().equals(p))
        					   .mapToInt(r -> r.getStars())
        					   .average().orElse(Double.NaN);
    }

    /**
     * Computes the overall average stars of all ratings
     *  
     * @return average stars
     */
    public double averageStars() {
    	return ratings.stream().mapToInt(r -> r.getStars())
				   			   .average().orElse(Double.NaN);
    }

    //R5 Statistiche
    /**
     * For each activity return the average stars of the entered ratings.
     * 
     * Activity names are sorted alphabetically.
     * 
     * @return the map associating activity name to average stars
     */
    public SortedMap<String, Double> starsPerActivity() {
    	return ratings.stream().collect(groupingBy(
    											   r -> r.getProduct().getActivity().getName(),
    											   TreeMap::new,
    											   averagingDouble(Rating::getStars)
    								   ));
    }

    /**
     * For each average star rating returns a list of
     * the products that have such score.
     * 
     * Ratings are sorted in descending order.
     * 
     * @return the map linking the average stars to the list of products
     */
    public SortedMap<Double, List<String>> getProductsPerStars() {
    	SortedMap<Double, List<String>> productsPerStars = new TreeMap<>(reverseOrder());
    	List<Double> avgStars = new ArrayList<>();
    	
    	for (Product p:products.values()) {
    		Double productStars = getStarsOfProduct(p.getName());
    		
    		if (!productStars.isNaN())
    			avgStars.add(productStars);
    	}
    	
    	for (Double stars:avgStars) {
    		List<Product> prodsWithThisAvg = new ArrayList<>();
    		
    		for (Product p:products.values()) {
    			Double productStars = getStarsOfProduct(p.getName());
        		
        		if (productStars.equals(stars))
        			prodsWithThisAvg.add(p);
    		}
    		
    		productsPerStars.put(stars, prodsWithThisAvg.stream().map(Product::getName).sorted().collect(toList()));
    	}
    	
        return productsPerStars;
    }

}