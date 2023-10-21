package mountainhuts;

import static java.util.stream.Collectors.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.HashMap;
import java.util.Optional;
import java.util.OptionalInt;

/**
 * Class {@code Region} represents the main facade
 * class for the mountains hut system.
 * 
 * It allows defining and retrieving information about
 * municipalities and mountain huts.
 *
 */
public class Region {
	
	private String name;
	private List<AltitudeRange> ranges = new ArrayList<AltitudeRange>();
	private Map<String, Municipality> municipalities = new HashMap<>();
	private Map<String, MountainHut> mountainHuts = new HashMap<>();

	/**
	 * Create a region with the given name.
	 * 
	 * @param name
	 *            the name of the region
	 */
	public Region(String name) {
		this.name = name;
	}

	/**
	 * Return the name of the region.
	 * 
	 * @return the name of the region
	 */
	public String getName() {
		return name;
	}

	/**
	 * Create the ranges given their textual representation in the format
	 * "[minValue]-[maxValue]".
	 * 
	 * @param ranges
	 *            an array of textual ranges
	 */
	public void setAltitudeRanges(String... ranges) {
		for (String s:ranges)
			this.ranges.add(new AltitudeRange(s));
	}

	/**
	 * Return the textual representation in the format "[minValue]-[maxValue]" of
	 * the range including the given altitude or return the default range "0-INF".
	 * 
	 * @param altitude
	 *            the geographical altitude
	 * @return a string representing the range
	 */
	public String getAltitudeRange(Integer altitude) {
		for (AltitudeRange r:ranges) {
			if (r.includes(altitude))
				return r.getRange();
		}
		
		return AltitudeRange.defaultValue;
	}

	/**
	 * Create a new municipality if it is not already available or find it.
	 * Duplicates must be detected by comparing the municipality names.
	 * 
	 * @param name
	 *            the municipality name
	 * @param province
	 *            the municipality province
	 * @param altitude
	 *            the municipality altitude
	 * @return the municipality
	 */
	public Municipality createOrGetMunicipality(String name, String province, Integer altitude) {
		if (municipalities.containsKey(name))
			return municipalities.get(name);
		
		Municipality m = new Municipality(name, province, altitude);
		municipalities.put(name, m);
		return m;
	}

	/**
	 * Return all the municipalities available.
	 * 
	 * @return a collection of municipalities
	 */
	public Collection<Municipality> getMunicipalities() {
		// costruttore di copia per evitare modifiche dall'esterno
		return new ArrayList<>(municipalities.values());
	}

	/**
	 * Create a new mountain hut if it is not already available or find it.
	 * Duplicates must be detected by comparing the mountain hut names.
	 *
	 * @param name
	 *            the mountain hut name
	 * @param category
	 *            the mountain hut category
	 * @param bedsNumber
	 *            the number of beds in the mountain hut
	 * @param municipality
	 *            the municipality in which the mountain hut is located
	 * @return the mountain hut
	 */
	public MountainHut createOrGetMountainHut(String name, String category, Integer bedsNumber,
			Municipality municipality) {
		if (mountainHuts.containsKey(name))
			return mountainHuts.get(name);
		
		MountainHut mh = new MountainHut(name, category, bedsNumber, municipality);
		mountainHuts.put(name, mh);
		return mh;
	}

	/**
	 * Create a new mountain hut if it is not already available or find it.
	 * Duplicates must be detected by comparing the mountain hut names.
	 * 
	 * @param name
	 *            the mountain hut name
	 * @param altitude
	 *            the mountain hut altitude
	 * @param category
	 *            the mountain hut category
	 * @param bedsNumber
	 *            the number of beds in the mountain hut
	 * @param municipality
	 *            the municipality in which the mountain hut is located
	 * @return a mountain hut
	 */
	public MountainHut createOrGetMountainHut(String name, Integer altitude, String category, Integer bedsNumber,
			Municipality municipality) {
		if (mountainHuts.containsKey(name))
			return mountainHuts.get(name);
		
		MountainHut mh = new MountainHut(name, altitude, category, bedsNumber, municipality);
		mountainHuts.put(name, mh);
		return mh;
	}

	/**
	 * Return all the mountain huts available.
	 * 
	 * @return a collection of mountain huts
	 */
	public Collection<MountainHut> getMountainHuts() {
		// costruttore di copia per evitare modifiche dall'esterno
		return new ArrayList<>(mountainHuts.values());
	}

	/**
	 * Factory methods that creates a new region by loading its data from a file.
	 * 
	 * The file must be a CSV file and it must contain the following fields:
	 * <ul>
	 * <li>{@code "Province"},
	 * <li>{@code "Municipality"},
	 * <li>{@code "MunicipalityAltitude"},
	 * <li>{@code "Name"},
	 * <li>{@code "Altitude"},
	 * <li>{@code "Category"},
	 * <li>{@code "BedsNumber"}
	 * </ul>
	 * 
	 * The fields are separated by a semicolon (';'). The field {@code "Altitude"}
	 * may be empty.
	 * 
	 * @param name
	 *            the name of the region
	 * @param file
	 *            the path of the file
	 */
	public static Region fromFile(String name, String file) {
		Region r = new Region(name);
		
		List<String> lines = readData(file);
		if (lines == null)
			return null;
		
		lines.remove(0);
		
		for (String s:lines)
			processLine(s, r);
		
		return r;
	}

	/**
	 * Internal class that can be used to read the lines of
	 * a text file into a list of strings.
	 * 
	 * When reading a CSV file remember that the first line
	 * contains the headers, while the real data is contained
	 * in the following lines.
	 * 
	 * @param file the file name
	 * @return a list containing the lines of the file
	 */
	private static List<String> readData(String file) {
		try (BufferedReader in = new BufferedReader(new FileReader(file))) {
			return in.lines().collect(toList());
		} catch (IOException e) {
			System.err.println(e.getMessage());
			return null;
		}
	}
	
	private static void processLine(String line, Region region) {
		final String SEP = ";";
		String[] values = line.split(SEP); 
		
		String province = values[0];
		String municipalityName = values[1];
		Integer municipalityAltitude = Integer.parseInt(values[2]);
		String mountainHutName = values[3];
		Integer mountainHutAltitude;
		try {
			mountainHutAltitude = Integer.parseInt(values[4]);
		} catch (NumberFormatException e) {
			mountainHutAltitude = null;
		}
		String category = values[5];
		Integer bedsNumber = Integer.parseInt(values[6]);
		
		Municipality municipality = region.createOrGetMunicipality(municipalityName, province, municipalityAltitude);
		
		if (mountainHutAltitude == null)
			region.createOrGetMountainHut(mountainHutName, category, bedsNumber, municipality);
		else
			region.createOrGetMountainHut(mountainHutName, mountainHutAltitude, category, bedsNumber, municipality);
	}

	/**
	 * Count the number of municipalities with at least a mountain hut per each
	 * province.
	 * 
	 * @return a map with the province as key and the number of municipalities as
	 *         value
	 */
	public Map<String, Long> countMunicipalitiesPerProvince() {
		return municipalities.values().stream().collect(groupingBy(Municipality::getProvince,
																   counting()));
	}

	/**
	 * Count the number of mountain huts per each municipality within each province.
	 * 
	 * @return a map with the province as key and, as value, a map with the
	 *         municipality as key and the number of mountain huts as value
	 */
	public Map<String, Map<String, Long>> countMountainHutsPerMunicipalityPerProvince() {
		return municipalities.values().stream().collect(groupingBy(Municipality::getProvince,
														groupingBy(Municipality::getName,
																   counting())));
	}

	/**
	 * Count the number of mountain huts per altitude range. If the altitude of the
	 * mountain hut is not available, use the altitude of its municipality.
	 * 
	 * @return a map with the altitude range as key and the number of mountain huts
	 *         as value
	 */
	public Map<String, Long> countMountainHutsPerAltitudeRange() {
		Map<String, Long> mappa =  mountainHuts.values().stream().collect(groupingBy(m ->
								this.getAltitudeRange(m.getAltitude().orElse(m.getMunicipality().getAltitude())),
													  counting()));
		
		// aggiungo intervalli di altitudine in cui non ci sono rifugi
		for (AltitudeRange r:ranges) {
			if (!mappa.containsKey(r.getRange()))
				mappa.put(r.getRange(), Long.valueOf(0));
		}
		
		return mappa;
	}

	/**
	 * Compute the total number of beds available in the mountain huts per each
	 * province.
	 * 
	 * @return a map with the province as key and the total number of beds as value
	 */
	public Map<String, Integer> totalBedsNumberPerProvince() {
		return mountainHuts.values().stream().collect(groupingBy(m -> m.getMunicipality().getProvince(),
																 summingInt(MountainHut::getBedsNumber)));
	}

	/**
	 * Compute the maximum number of beds available in a single mountain hut per
	 * altitude range. If the altitude of the mountain hut is not available, use the
	 * altitude of its municipality.
	 * 
	 * @return a map with the altitude range as key and the maximum number of beds
	 *         as value
	 */
	public Map<String, Optional<Integer>> maximumBedsNumberPerAltitudeRange() {
		Map<String, Optional<Integer>> mappa = new HashMap<>();
//					=  mountainHuts.values().stream().collect(groupingBy(m ->
//					this.getAltitudeRange(m.getAltitude().orElse(m.getMunicipality().getAltitude()))),
//							  mapping(MountainHut::getBedsNumber, maxBy(Comparator.naturalOrder())));
		
		for (AltitudeRange r:ranges) {
			Optional<Integer> value;
			
			OptionalInt optValue = mountainHuts.values().stream()
				.filter(m -> (this.getAltitudeRange(m.getAltitude().orElse(m.getMunicipality().getAltitude()))).equals(r.getRange()))
				.mapToInt(MountainHut::getBedsNumber).max();
			
			try {
				value = Optional.of(optValue.getAsInt());
			} catch (NoSuchElementException e) {
				value = Optional.empty();
			}
			
			mappa.put(r.getRange(), value);
		}
		
//		return mountainHuts.values().stream().collect(groupingBy(m ->
//		this.getAltitudeRange(m.getAltitude().orElse(m.getMunicipality().getAltitude())),
//							  counting()));
		return mappa;
	}

	/**
	 * Compute the municipality names per number of mountain huts in a municipality.
	 * The lists of municipality names must be in alphabetical order.
	 * 
	 * @return a map with the number of mountain huts in a municipality as key and a
	 *         list of municipality names as value
	 */
	public Map<Long, List<String>> municipalityNamesPerCountOfMountainHuts() {
		Map<String, Long> nRifugiPerComune = new HashMap<>();
		Map<Long, List<String>> mappaFinale = new HashMap<>();
		
		nRifugiPerComune = mountainHuts.values().stream().collect(groupingBy(m -> m.getMunicipality().getName(),
														counting()));
		
		for (Long l:nRifugiPerComune.values()) {
			List<String> comuni = municipalities.values().stream().filter(m -> numeroDiRifugi(m).equals(l))
												.map(Municipality::getName)
												.sorted()
												.collect(toList());
			mappaFinale.put(l, comuni);
		}
		
		return mappaFinale;
	}
	
	private Long numeroDiRifugi(Municipality mu) {
		return mountainHuts.values().stream().filter(mh -> mh.getMunicipality().equals(mu)).count();
	}
}
