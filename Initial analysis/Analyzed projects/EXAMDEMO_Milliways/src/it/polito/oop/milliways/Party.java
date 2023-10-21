package it.polito.oop.milliways;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.HashMap;

public class Party {
	
	private Map<Race, Integer> description = new HashMap<>();
	private Hall seatedIn;

    public Party() {
		super();
	}

	public void addCompanions(Race race, int num) {
    	Integer n = description.get(race);
    	if (n == null)
    		description.put(race, num);
    	else
    		description.put(race, n+num);
	}

	public int getNum() {
        return (int) description.values().stream().mapToInt(i -> i).sum();
	}

	public int getNum(Race race) {
		Integer n = description.get(race);
		
		if (n == null)
			return 0;
		
		return n;
	}

	public List<String> getRequirements() {
        return description.keySet().stream().flatMap(r -> r.getRequirements().stream())
        									.distinct()
        									.sorted()
        									.collect(Collectors.toList());
	}

    public Map<String,Integer> getDescription() {
        Map<String, Integer> mappa = new HashMap<>();
        description.entrySet().stream().forEach(e -> mappa.put(e.getKey().getName(), e.getValue()));
        return mappa;
    }

    public Map<Race, Integer> getDescriptionByRace() {
    	return description;
    }

	public Hall getSeat() {
		return seatedIn;
	}

	public void seat(Hall seatedIn) {
		this.seatedIn = seatedIn;
	}
}
