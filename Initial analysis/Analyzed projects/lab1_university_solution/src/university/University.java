package university;

import java.util.logging.Logger;

/**
 * This class represents a university education system.
 * 
 * It manages students and courses.
 *
 */
public class University {
	// System-level parameters (constants)
	
	public static final int MAX_STUDENTS = 1000;
	public static final int MAX_COURSES = 50;
	public static final int MAX_COURSES_PER_STUDENT = 25;
	public static final int MAX_STUDENTS_PER_COURSE = 100;

	public final static int INITIAL_ID = 10000;
	public final static int INITIAL_CODE = 10;
	
	// Attributes
	private final String name;
	private String rector;
	
	private Student[] students; // = null
	private int nextId = INITIAL_ID;
	
	private Course[] offers;
	private int nextCode = INITIAL_CODE;


// R1
	/**
	 * Constructor
	 * @param name name of the university
	 */
	public University(String name){
		this.name = name;
		this.rector = "<none>";
		
		students = new Student[MAX_STUDENTS];
		offers = new Course[MAX_COURSES];
	}
	
	/**
	 * Getter for the name of the university
	 * 
	 * @return name of university
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Defines the rector for the university
	 * 
	 * @param first first name of the rector
	 * @param last	last name of the rector
	 */
	public void setRector(String first, String last){
		this.rector = first + " " + last;
	}
	
	/**
	 * Retrieves the rector of the university
	 * 
	 * @return name of the rector
	 */
	public String getRector(){
		return rector;
	}
	
// R2
	/**
	 * Enrol a student in the university
	 * 
	 * @param first first name of the student
	 * @param last last name of the student
	 * 
	 * @return unique ID of the newly enrolled student
	 */
	public int enroll(String first, String last){
		Student s = new Student( nextId , first, last);
		students[ nextId - INITIAL_ID ] = s;
		
		logger.info("New student enrolled: " + nextId + ", " + first + " " + last ); // R7

		return nextId++;
	}
	
	/**
	 * Retrieves the information for a given student
	 * 
	 * @param id the ID of the student
	 * 
	 * @return information about the student
	 */
	public String student(int id){
		if(id<INITIAL_ID||id>=nextId){
			logger.info("Error Student " + id + " is not enrolled in university " + name);
			return "";
		}
		Student s = students[ id - INITIAL_ID ];
		return s.toString();
	}
	
// R3
	/**
	 * Activates a new course with the given teacher
	 * 
	 * @param title title of the course
	 * @param teacher name of the teacher
	 * 
	 * @return the unique code assigned to the course
	 */
	public int activate(String title, String teacher){
		Course c = new Course(nextCode,title,teacher);
		offers[nextCode - INITIAL_CODE] = c;
		
		logger.info("New course activated: " + nextCode + ", " + title + " " + teacher ); // R7

		return nextCode++;
	}
	
	/**
	 * Retrieve the information for a given course.
	 * 
	 * The course information is formatted as a string containing 
	 * code, title, and teacher separated by commas, 
	 * e.g., {@code "10,Object Oriented Programming,James Gosling"}.
	 * 
	 * @param code unique code of the course
	 * 
	 * @return information about the course
	 */
	public String course(int code){
		if( code >= nextCode ){
			logger.info("ERROR: course " + code + " is not activated in university " + name);
			return "";
		}
		return offers[code-INITIAL_CODE].toString();
	}
	
// R4
	/**
	 * Register a student to attend a course
	 * @param studentID id of the student
	 * @param courseCode id of the course
	 */
	public void register(int studentID, int courseCode){
		Student s = students[studentID-INITIAL_ID];
		Course c = offers[courseCode-INITIAL_CODE];

		if(s==null || c==null){
			logger.info("ERROR: Invalid arguments to method register: existing student and course required.");
			return;
		}

		s.enroll(c);
		c.enroll(s);
		
		logger.info("Student " + studentID + " signed up for course " + courseCode);
	}
	
	/**
	 * Retrieve a list of attendees
	 * 
	 * @param courseCode unique id of the course
	 * @return list of attendees separated by "\n"
	 */
	public String listAttendees(int courseCode){
		Course c = offers[courseCode-INITIAL_CODE];
		if(c==null){
			logger.info("ERROR: course " + courseCode + " is not activated in university " + name);
			return "";
		}
		return c.attendees();
	}

	/**
	 * Retrieves the study plan for a student.
	 * 
	 * The study plan is reported as a string having
	 * one course per line (i.e. separated by '\n').
	 * The courses are formatted as describe in method {@link #course}
	 * 
	 * @param studentID id of the student
	 * 
	 * @return the list of courses the student is registered for
	 */
	public String studyPlan(int studentID){
		Student s = students[studentID-INITIAL_ID];
		if(s==null){
			logger.info("ERROR: Student " + studentID + " is not enrolled in university " + name);
			return "";
		}
		return s.courses();
	}

// R5
	/**
	 * Retrieves a student given its id.
	 * Internal method that encapsulates the mapping between id and index
	 * 
	 * @param studentId id of the student
	 * @return the student with the given id
	 */
	private Student findStudent(int studentId) {
		if(studentId<INITIAL_ID || studentId >= nextId) return null;
		return students[studentId-INITIAL_ID];
	}

	/**
	 * Retrieves a course given its id.
	 * Internal method that encapsulates the mapping between id and index
	 * 
	 * @param courseId id of the course
	 * @return the course with the given id
	 */
	private Course findCourse(int courseId) {
		if(courseId<INITIAL_CODE || courseId >= nextCode) return null;
		return offers[courseId-INITIAL_CODE];
	}
	
	/**
	 * Retrieves the number of enrolled students.
	 * Internal method that encapsulates the mapping between id and index
	 * 
	 * @return the course with the given id
	 */
	private int numStudents() {
		return nextId - INITIAL_ID;
	}
	
	/**
	 * records the grade (integer 0-30) for an exam can 
	 * 
	 * @param studentId the ID of the student
	 * @param courseId	course code
	 * @param grade		grade ( 0-30)
	 */
	public void exam(int studentId, int courseId, int grade) {
		Student s = findStudent(studentId);
		Course c = findCourse(courseId);

		if(s==null || c==null){
			logger.info("ERROR: Invalid arguments to method exam: existing student and course required.");
			return;
		}
		if(c.attendees().contains(Integer.toString(studentId))) {
			new Exam(s,c,grade);
			logger.info("Student " + studentId + " took an exam in course " + courseId + " with grade" + grade);
		}else {
			logger.info("ERROR: student " + studentId + " not enrolled in course " + courseId + ": cannot assign a grade.");
		}
	}

	/**
	 * Computes the average grade for a student and formats it as a string
	 * using the following format 
	 * 
	 * {@code "Student STUDENT_ID : AVG_GRADE"}. 
	 * 
	 * If the student has no exam recorded the method
	 * returns {@code "Student STUDENT_ID hasn't taken any exams"}.
	 * 
	 * @param studentId the ID of the student
	 * @return the average grade formatted as a string.
	 */
	public String studentAvg(int studentId) {
		Student s = findStudent(studentId);
		if(s==null){
			logger.info("ERROR: student " + studentId + " not enrolled in university " + name);
			return "";
		}
		double avg = s.average();
		if(! Student.isValid(avg)) return String.format("Student %d hasn't taken any exams", s.getId());
		return String.format("Student %d : %.1f", s.getId(), avg);
	}
	
	/**
	 * Computes the average grades of all students that took the exam for a given course.
	 * 
	 * The format is the following: 
	 * {@code "The average for the course COURSE_TITLE is: COURSE_AVG"}.
	 * 
	 * If no student took the exam for that course it returns {@code "No student has taken the exam in COURSE_TITLE"}.
	 * 
	 * @param courseId	course code 
	 * @return the course average formatted as a string
	 */
	public String courseAvg(int courseId) {
		Course c = findCourse(courseId);
		if(c==null){
			logger.info("ERROR: course " + courseId + " not activated in university " + name);
			return "";
		}
		double avg = c.average();
		if(! Course.isValid(avg)) return String.format("No student has taken the exam in %s", c.getTitle());
		return String.format("The average for the course %s is: %.1f", c.getTitle(), avg);
	}
	

// R6
	/**
	 * Retrieve information for the best students to award a price.
	 * 
	 * The students' score is evaluated as the average grade of the exams they've taken. 
	 * To take into account the number of exams taken and not only the grades, 
	 * a special bonus is assigned on top of the average grade: 
	 * the number of taken exams divided by the number of courses the student is enrolled to, multiplied by 10.
	 * The bonus is added to the exam average to compute the student score.
	 * 
	 * The method returns a string with the information about the three students with the highest score. 
	 * The students appear one per row (rows are terminated by a new-line character {@code '\n'}) 
	 * and each one of them is formatted as: {@code "STUDENT_FIRSTNAME STUDENT_LASTNAME : SCORE"}.
	 * 
	 * @return info on the best three students.
	 */
	public String topThreeStudents() {
		int n = Math.min(3, numStudents() );
		Student[] top = new Student[n];
		for(int i=0; i<numStudents(); ++i){
			insert(students[i], top);
		}
		
		StringBuilder res= new StringBuilder();
		for(Student s : top) {
			if(s==null || ! Student.isValid(s.getScore()) ) continue;
			res.append(s.getLast()).append(" ").append(s.getFirst()).append(" : ").append(s.getScore()).append("\n");
		}
		return res.toString();
	}
	
	/**
	 * Adds a student into an array in order of decreasing score
	 * 
	 * @param s		the student to be added
	 * @param ary	the array of students
	 */
	private void insert(Student s, Student[] ary) {
		if(Student.isValid(s.getScore())) {
			for(int i=0; i<ary.length; ++i) {
				if(ary[i] == null || s.getScore() > ary[i].getScore()) {
//					for(int j=ary.length-1; j>i; j--) {
//						ary[j] = ary[j-1];
//					}
					// OR, more efficient:
					if (ary.length - 1 - i >= 0) System.arraycopy(ary, i, ary, i + 1, ary.length - 1 - i);
					ary[i] = s;
					break;
				}
			}
		}
	}

	
// R7
    /**
     * This field points to the logger for the class that can be used
     * throughout the methods to log the activities.
     */
    private final static Logger logger = Logger.getLogger("University");

}
