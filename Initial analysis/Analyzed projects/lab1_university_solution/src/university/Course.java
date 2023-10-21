package university;

/**
 * Represents the description of a course
 */
class Course {
	
	private static final String SEPARATOR = ",";
	private final int code;
	private final String title;
	private final String teacher;
	private Student[] students;
	private Exam[] exams;
	private int nextExam=0;

	public Course(int code, String title, String teacher) {
		this.code = code;
		this.title = title;
		this.teacher = teacher;
		this.students = new Student[University.MAX_STUDENTS_PER_COURSE];
		this.exams = new Exam[University.MAX_COURSES_PER_STUDENT];
	}
	
	public String toString(){
		return code + SEPARATOR + title + SEPARATOR + teacher;
	}

	public void enroll(Student s) {
		for (int i = 0; i < students.length; i++) {
			if(students[i] == null){
				students[i]=s;
				break;
			}
		}
	}
	
	public String attendees(){
		StringBuilder result = new StringBuilder();
		
		for(Student s : students){
			if(s!=null){
				result.append(s.toString()).append("\n");
			}
		}
		return result.toString();
	}

	public void addExam(Exam exam) {
		exams[nextExam++] = exam;
	}
	
	double average() {
		if(nextExam==0) return Double.NaN;
		double average = 0.0;
		for(Exam e : exams) {
			if(e==null) break;
			average += e.getGrade();
		}
		return average/nextExam;
	}
	
	public static boolean isValid(double x) {
		return !Double.isNaN(x);
	}
	
	public String getTitle() {
		return this.title;
	}
}
