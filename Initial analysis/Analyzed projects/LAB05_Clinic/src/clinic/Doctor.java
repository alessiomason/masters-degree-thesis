package clinic;

import java.util.Set;
import java.util.HashSet;

public class Doctor extends Patient{
	private Integer badgeID;
	private String specialization;
	private Set<Patient> assignedPatients = new HashSet<>();
	
	public Doctor(String firstName, String lastName, String ssn, Integer badgeID, String specialization) {
		super(firstName, lastName, ssn);
		this.badgeID = badgeID;
		this.specialization = specialization;
	}
	
	@Override
	public String toString() {
		return super.toString() + " [" + badgeID + "]: " + specialization;
	}
	
	public Integer getBadgeID() {
		return badgeID;
	}

	public String getSpecialization() {
		return specialization;
	}

	public void addAssignedPatient(Patient assignedPatient) {
		assignedPatients.add(assignedPatient);
	}
	
	public Integer getNumberOfPatients() {
		return assignedPatients.size();
	}
	
	public Set<Patient> getAssignedPatients() {
		return assignedPatients;
	}
}
