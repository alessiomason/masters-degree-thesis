package it.polito.oop.milliways;

import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;

public class Race implements Comparable<Race> {
	
	private String name;
	private List<String> requirements = new ArrayList<>();
    
	public Race(String name) {
		this.name = name;
	}

	public void addRequirement(String requirement) throws MilliwaysException {
		for (String r:requirements) {
			if (r.equals(requirement))
				throw new MilliwaysException();
		}
		
		requirements.add(requirement);
	}
	
	public List<String> getRequirements() {
        return requirements.stream().sorted().collect(Collectors.toList());
	}
	
	public String getName() {
        return name;
	}
	
	@Override
	public String toString() {
		return name;
	}

	@Override
	public int compareTo(Race r) {
		return this.name.compareTo(r.getName());
	}
}
