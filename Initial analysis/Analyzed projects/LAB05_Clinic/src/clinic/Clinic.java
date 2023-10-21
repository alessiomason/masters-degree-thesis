package clinic;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.Reader;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import static java.util.stream.Collectors.*;
import static java.util.Comparator.*;

/**
 * Represents a clinic with patients and doctors.
 * 
 */
public class Clinic {
	
	private Map<String, Patient> pazienti = new TreeMap<>();
	private Map<Integer, Doctor> dottori = new TreeMap<>();

	/**
	 * Add a new clinic patient.
	 * 
	 * @param first first name of the patient
	 * @param last last name of the patient
	 * @param ssn SSN number of the patient
	 */
	public void addPatient(String first, String last, String ssn) {
		Patient p = new Patient(first, last, ssn);
		pazienti.put(ssn, p);
	}


	/**
	 * Retrieves a patient information
	 * 
	 * @param ssn SSN of the patient
	 * @return the object representing the patient
	 * @throws NoSuchPatient in case of no patient with matching SSN
	 */
	public String getPatient(String ssn) throws NoSuchPatient {
		Patient p = pazienti.get(ssn);
		if (p == null)
			throw new NoSuchPatient();
		return p.toString();
	}

	/**
	 * Add a new doctor working at the clinic
	 * 
	 * @param first first name of the doctor
	 * @param last last name of the doctor
	 * @param ssn SSN number of the doctor
	 * @param docID unique ID of the doctor
	 * @param specialization doctor's specialization
	 */
	public void addDoctor(String first, String last, String ssn, int docID, String specialization) {
		Doctor d = new Doctor(first, last, ssn, docID, specialization);
		pazienti.put(ssn, d);
		dottori.put(docID, d);
	}

	/**
	 * Retrieves information about a doctor
	 * 
	 * @param docID ID of the doctor
	 * @return object with information about the doctor
	 * @throws NoSuchDoctor in case no doctor exists with a matching ID
	 */
	public String getDoctor(int docID) throws NoSuchDoctor {
		Doctor d = dottori.get(docID);
		if (d == null)
			throw new NoSuchDoctor();
		return d.toString();
	}
	
	/**
	 * Assign a given doctor to a patient
	 * 
	 * @param ssn SSN of the patient
	 * @param docID ID of the doctor
	 * @throws NoSuchPatient in case of not patient with matching SSN
	 * @throws NoSuchDoctor in case no doctor exists with a matching ID
	 */
	public void assignPatientToDoctor(String ssn, int docID) throws NoSuchPatient, NoSuchDoctor {
		Patient p = pazienti.get(ssn);
		if (p == null)
			throw new NoSuchPatient();
		Doctor d = dottori.get(docID);
		if (d == null)
			throw new NoSuchDoctor();
		
		p.setAssignedDoctor(d);
		d.addAssignedPatient(p);
	}
	
	/**
	 * Retrieves the id of the doctor assigned to a given patient.
	 * 
	 * @param ssn SSN of the patient
	 * @return id of the doctor
	 * @throws NoSuchPatient in case of not patient with matching SSN
	 * @throws NoSuchDoctor in case no doctor has been assigned to the patient
	 */
	public int getAssignedDoctor(String ssn) throws NoSuchPatient, NoSuchDoctor {
		Patient p = pazienti.get(ssn);
		if (p == null)
			throw new NoSuchPatient();
		
		Doctor d = p.getAssignedDoctor();
		if (d == null)
			throw new NoSuchDoctor();
		
		return d.getBadgeID();
	}
	
	/**
	 * Retrieves the patients assigned to a doctor
	 * 
	 * @param id ID of the doctor
	 * @return collection of patient SSNs
	 * @throws NoSuchDoctor in case the {@code id} does not match any doctor 
	 */
	public Collection<String> getAssignedPatients(int id) throws NoSuchDoctor {
		Doctor d = dottori.get(id);
		if (d == null)
			throw new NoSuchDoctor();
		
		return d.getAssignedPatients().stream().map(p -> p.getSSN()).collect(toList());
	}


	/**
	 * Loads data about doctors and patients from the given stream.
	 * <p>
	 * The text file is organized by rows, each row contains info about
	 * either a patient or a doctor.</p>
	 * <p>
	 * Rows containing a patient's info begin with letter {@code "P"} followed by first name,
	 * last name, and SSN. Rows containing doctor's info start with letter {@code "M"},
	 * followed by badge ID, first name, last name, SSN, and specialization.<br>
	 * The elements on a line are separated by the {@code ';'} character possibly
	 * surrounded by spaces that should be ignored.</p>
	 * <p>
	 * In case of error in the data present on a given row, the method should be able
	 * to ignore the row and skip to the next one.<br>

	 * 
	 * @param reader linked to the file to be read
	 * @throws IOException in case of IO error
	 */
	public void loadData(Reader reader) throws IOException {
		BufferedReader input = new BufferedReader(reader);
		input.lines().forEach(l -> processLine(l));
	}
	
	private void processLine(String line) {
		Integer badgeID;
		String[] campi = line.split("\\s*;\\s*");
		
		switch (campi[0]) {
		case "P":
			if (campi.length != 4)
				break;
			
			Patient p = new Patient(campi[1], campi[2], campi[3]);
			pazienti.put(campi[3], p);
			break;
			
		case "M":
			if (campi.length != 6)
				break;
			
			try {
				badgeID = Integer.parseInt(campi[1]);
			} catch (NumberFormatException e) {
				break;
			}
			
			Doctor d = new Doctor(campi[2], campi[3], campi[4], badgeID, campi[5]);
			pazienti.put(campi[4], d);
			dottori.put(badgeID, d);
			break;

		default:
			break;
		}
	}


	/**
	 * Retrieves the collection of doctors that have no patient at all.
	 * The doctors are returned sorted in alphabetical order
	 * 
	 * @return the collection of doctors' IDs
	 */
	public Collection<Integer> idleDoctors() {
		return dottori.values().stream().filter(d -> d.getNumberOfPatients() == 0)
										.sorted(
												comparing(Doctor::getLastName)
												.thenComparing(Doctor::getFirstName))
										.map(d -> d.getBadgeID())
										.collect(toList());
	}

	/**
	 * Retrieves the collection of doctors having a number of patients larger than the average.
	 * 
	 * @return  the collection of doctors' IDs
	 */
	public Collection<Integer> busyDoctors() {
		Double mediaPazienti = dottori.values().stream().mapToDouble(Doctor::getNumberOfPatients).average().orElse(0.0);
		
		return dottori.values().stream().filter(d -> d.getNumberOfPatients() > mediaPazienti)
										.sorted(
												comparing(Doctor::getLastName)
												.thenComparing(Doctor::getFirstName))
										.map(d -> d.getBadgeID())
										.collect(toList());
	}

	/**
	 * Retrieves the information about doctors and relative number of assigned patients.
	 * <p>
	 * The method returns list of strings formatted as "{@code ### : ID SURNAME NAME}" where {@code ###}
	 * represent the number of patients (printed on three characters).
	 * <p>
	 * The list is sorted by decreasing number of patients.
	 * 
	 * @return the collection of strings with information about doctors and patients count
	 */
	public Collection<String> doctorsByNumPatients() {
		return dottori.values().stream().sorted(
												comparing(Doctor::getNumberOfPatients, reverseOrder()))
										.map(d -> {return String.format("%3d", d.getNumberOfPatients()) +
														  " : " + d.getBadgeID() + " " + d.getLastName() + " " + d.getFirstName();})
										.collect(toList());
	}
	
	/**
	 * Retrieves the number of patients per (their doctor's) speciality
	 * <p>
	 * The information is a collections of strings structured as {@code ### - SPECIALITY}
	 * where {@code SPECIALITY} is the name of the speciality and 
	 * {@code ###} is the number of patients cured by doctors with such speciality (printed on three characters).
	 * <p>
	 * The elements are sorted first by decreasing count and then by alphabetic speciality.
	 * 
	 * @return the collection of strings with speciality and patient count information.
	 */
	public Collection<String> countPatientsPerSpecialization() {
		Map<String, Long> pazientiPerSpec = dottori.values().stream().collect(groupingBy(
																						 Doctor::getSpecialization,
																						 counting()));
		
		
		return pazientiPerSpec.entrySet().stream().sorted(
												   		  Map.Entry.<String, Long>comparingByValue().reversed()
												   		  .thenComparing(Map.Entry.<String, Long>comparingByKey()))
										   .map(e -> {return String.format("%3d", e.getValue()) +
												   " - " + e.getKey();})
										   .collect(toList());
	}
	
}
