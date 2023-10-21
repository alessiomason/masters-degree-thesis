package it.polito.oop.milliways;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Hall {
	
	private Integer id;
	private List<String> facilities = new ArrayList<>();
	private List<Party> seats = new ArrayList<>();

	public Hall(Integer id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void addFacility(String facility) throws MilliwaysException {
		if (facilities.contains(facility))
				throw new MilliwaysException();
		
		facilities.add(facility);
	}

	public List<String> getFacilities() {
        return facilities.stream().sorted().collect(Collectors.toList());
	}
	
	public int getNumFacilities() {
        return facilities.size();
	}

	public boolean isSuitable(Party party) {
		List<String> requirements = party.getRequirements();
		
		for (String r:requirements) {
			if (!facilities.contains(r))
				return false;
		}
		
		return true;
	}
	
	public void addParty(Party p) {
		seats.add(p);
	}
	
	public List<Party> getSeats() {
		return seats;
	}
}
