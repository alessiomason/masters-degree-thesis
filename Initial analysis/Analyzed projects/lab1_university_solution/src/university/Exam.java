package university;

/**
 * Represents the result of an exam
 */
class Exam {
	private final int grade;
	
	Exam(Student student, Course course, int grade) {
		super();
		this.grade = grade;
		student.addExam(this);
		course.addExam(this);
	}

	public int getGrade() {
		return grade;
	}

}
