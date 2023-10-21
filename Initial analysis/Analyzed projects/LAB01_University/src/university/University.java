package university;

/**
 * This class represents a university education system.
 * 
 * It manages students and courses.
 *
 */
public class University {
	
	// static: valore non dipende dall'istanza, ogni università avrà lo stesso; potrò scrivere University.MAX_STUD
	protected static final int MAX_STUD = 1000;
	protected static final int OFFSET_MATR = 10000;
	protected static final int MAX_INS = 50;
	protected static final int OFFSET_INS = 10;
	
	private String name;
	private String firstNameRector;
	private String lastNameRector;
	
	protected Student studenti[] = new Student[MAX_STUD];
	protected int nextStudentId = 0;
	
	protected Course corsi[] = new Course[MAX_INS];
	protected int nextInsId = 0;

	
	/**
	 * Constructor
	 * @param name name of the university
	 */
	public University(String name) {
		
		this.name = name;
	}
	
	/**
	 * Getter for the name of the university
	 * @return name of university
	 */
	public String getName() {
		
		return name;
	}
	
	/**
	 * Defines the rector for the university
	 * 
	 * @param first
	 * @param last
	 */
	public void setRector(String first, String last) {
		
		firstNameRector = first;
		lastNameRector = last;
	}
	
	/**
	 * Retrieves the rector of the university
	 * 
	 * @return
	 */
	public String getRector() {
		
		return firstNameRector + " " + lastNameRector;
	}
	
	/**
	 * Enroll a student in the university
	 * 
	 * @param first first name of the student
	 * @param last last name of the student
	 * @return
	 */
	public int enroll(String first, String last) {
		
		if (nextStudentId < MAX_STUD) {
			studenti[nextStudentId] = new Student(first, last, nextStudentId+OFFSET_MATR);
		
			return studenti[nextStudentId++].getMatricola();
		}
		
		return -1;
	}
	
	/**
	 * Retrieves the information for a given student
	 * 
	 * @param matricola the id of the student
	 * @return information about the student
	 */
	public String student(int matricola) {
		
		int id = matricola - OFFSET_MATR;
		
		if (id < 0 || id >= MAX_STUD)
			return null;
		
		return studenti[id].toString();
	}
	
	/**
	 * Activates a new course with the given teacher
	 * 
	 * @param title title of the course
	 * @param teacher name of the teacher
	 * @return the unique code assigned to the course
	 */
	public int activate(String title, String teacher){
		
		if (nextInsId < MAX_INS) {
			corsi[nextInsId] = new Course(title, teacher, nextInsId+OFFSET_INS);
		
			return corsi[nextInsId++].getCodice();
		}
		
		return -1;
	}
	
	/**
	 * Retrieve the information for a given course
	 * 
	 * @param codice unique code of the course
	 * @return information about the course
	 */
	public String course(int codice){
		
		int id = codice - OFFSET_INS;
		
		if (id < 0 || id >= MAX_INS)
			return null;
		
		return corsi[id].toString();
	}
	
	/**
	 * Register a student to attend a course
	 * @param matrStud id of the student
	 * @param courseCode id of the course
	 */
	public void register(int matrStud, int courseCode) {
		
		int studId = matrStud - OFFSET_MATR;
		int codIns = courseCode - OFFSET_INS;
		
		if (studId < 0 || studId >= nextStudentId || codIns < 0 || codIns >= nextInsId)
			return;
		
		corsi[codIns].iscrStud(matrStud);
		studenti[studId].aggIns(courseCode);
	}
	
	/**
	 * Retrieve a list of attendees
	 * 
	 * @param courseCode unique id of the course
	 * @return list of attendees separated by "\n"
	 */
	public String listAttendees(int courseCode) {
		
		int codIns = courseCode - OFFSET_INS;
		
		if (codIns < 0 || codIns >= nextInsId)
			return null;
		
		int[] iscritti = corsi[codIns].elencoIscritti();
		String elencoIscr = new String();
		
		for (int i:iscritti) {
			
			if (i == 0)		// sono finiti gli iscritti
				break;
			
			int studId = i - OFFSET_MATR;
			
			elencoIscr += studenti[studId].toString() + "\n";
		}
		
		// rimuovo l'ultimo \n
		elencoIscr = elencoIscr.substring(0, elencoIscr.length()-1);
		
		return elencoIscr;
	}

	/**
	 * Retrieves the study plan for a student
	 * 
	 * @param matrStud id of the student
	 * @return list of courses the student is registered for
	 */
	public String studyPlan(int matrStud) {
		
		int studId = matrStud - OFFSET_MATR;
		
		if (studId < 0 || studId >= nextStudentId)
			return null;
		
		int[] corsiStud = studenti[studId].elencoCorsi();
		String elencoCorsiStud = new String();
		
		for (int c:corsiStud) {
			
			if (c == 0)		// sono finiti i corsi
				break;
			
			int insId = c - OFFSET_INS;
			
			elencoCorsiStud += corsi[insId].toString() + "\n";
		}
		
		// rimuovo l'ultimo \n
		elencoCorsiStud = elencoCorsiStud.substring(0, elencoCorsiStud.length()-1);
		
		return elencoCorsiStud;
	}
}
