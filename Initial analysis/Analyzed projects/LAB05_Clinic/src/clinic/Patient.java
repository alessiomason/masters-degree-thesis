package clinic;

public class Patient {
	private String firstName;
	private String lastName;
	private String ssn;
	private Doctor assignedDoctor;
	
	public Patient(String firstName, String lastName, String ssn) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.ssn = ssn;
	}

	@Override
	public String toString() {
		return lastName + " " + firstName + " (" + ssn + ")";
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getSSN() {
		return ssn;
	}

	public void setAssignedDoctor(Doctor assignedDoctor) {
		this.assignedDoctor = assignedDoctor;
	}

	public Doctor getAssignedDoctor() {
		return assignedDoctor;
	}
}
