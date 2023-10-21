package it.polito.oop.milliways;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.LinkedHashMap;
import static java.util.Comparator.*;
import static java.util.stream.Collectors.*;

public class Restaurant {
	
	private Map<String, Race> races = new HashMap<>();
	private Map<Integer, Hall> halls = new LinkedHashMap<>();

    public Restaurant() {
    	
	}
	
	public Race defineRace(String name) throws MilliwaysException {
		if (races.containsKey(name))
			throw new MilliwaysException();
		
		Race r = new Race(name);
		races.put(name, r);
	    return r;
	}
	
	public Party createParty() {
	    return new Party();
	}
	
	public Hall defineHall(int id) throws MilliwaysException {
		if (halls.containsKey(id))
			throw new MilliwaysException();
		
		Hall h = new Hall(id);
		halls.put(id, h);
	    return h;
	}

	public List<Hall> getHallList() {
		return new ArrayList<Hall>(halls.values());
	}

	public Hall seat(Party party, Hall hall) throws MilliwaysException {
		if (!hall.isSuitable(party))
			throw new MilliwaysException();
		
		hall.addParty(party);
		party.seat(hall);
        return hall;
	}

	public Hall seat(Party party) throws MilliwaysException {
		for (Hall h:halls.values()) {
			if (h.isSuitable(party)) {
				h.addParty(party);
				party.seat(h);
				return h;
			}
		}

		throw new MilliwaysException();
	}

	public Map<Race, Integer> statComposition() {
		Map<Race, Integer> mappa = new TreeMap<>();
		List<Party> parties = halls.values().stream()
											.flatMap(h -> h.getSeats().stream())
											.collect(toList());
		
		for (Party p:parties) {
			for (Map.Entry<Race, Integer> e : p.getDescriptionByRace().entrySet()) {
				Integer n = mappa.get(e.getKey());
				
				if (n == null)
					mappa.put(e.getKey(), e.getValue());
				else
					mappa.put(e.getKey(), n+e.getValue());
			}
		}
		
        return mappa;
	}

	public List<String> statFacility() {
		return halls.values().stream().flatMap(h -> h.getFacilities().stream())
							   .distinct()
							   .sorted(comparing((String f) -> facilityAvailableInHallsNumber(f), reverseOrder())
									   .thenComparing(naturalOrder()))
							   .collect(toList());
	}
	
	private Integer facilityAvailableInHallsNumber(String facility) {
		Integer n = 0;
		
		for (Hall h : halls.values()) {
			if (h.getFacilities().contains(facility))
				n++;
		}
		
		return n;
	}
	
	public Map<Integer,List<Integer>> statHalls() {
		return halls.values().stream().collect(groupingBy(
														  Hall::getNumFacilities,
														  TreeMap::new,
														  mapping(Hall::getId,
																  collectingAndThen(toList(),
																		  			l -> {Collections.sort(l);
																		  				  return l;}))));
	}
}
