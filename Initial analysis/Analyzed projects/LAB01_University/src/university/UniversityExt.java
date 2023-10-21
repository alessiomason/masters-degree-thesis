package university;

import java.util.logging.Logger;

public class UniversityExt extends University {
	
	private final static int MAX_BEST_STUDENTS = 3;
	private final static Logger logger = Logger.getLogger("University");

	public UniversityExt(String name) {
		super(name);
		// Example of logging
		logger.info("Creating extended university object");
	}
	
	@Override
	public int enroll(String first, String last) {

		int matr = super.enroll(first, last);
		logger.info("New student enrolled: " + matr + " " + first + " " + last);
		return matr;
	}
	
	@Override
	public int activate(String title, String teacher){
		
		int codice = super.activate(title, teacher);
		logger.info("New course activated: " + codice + ", " + title + ", " + teacher);
		return codice;
	}
	
	@Override
	public void register(int matrStud, int courseCode) {
		
		super.register(matrStud, courseCode);
		logger.info("Student " + matrStud + " signed up for course " + courseCode);
	}
	
	public void exam(int matrStud, int courseCode, int voto) {
		
		int studId = matrStud - OFFSET_MATR;
		int codIns = courseCode - OFFSET_INS;
		
		if (studId < 0 || studId >= nextStudentId || codIns < 0 || codIns >= nextInsId)
			return;
		
		if (voto < 0 || voto > 30)
			return;
		
		corsi[codIns].aggVoto(matrStud, voto);
		studenti[studId].aggEsame(courseCode, voto);
		
		logger.info("Student " + matrStud + " took an exam in course " + courseCode + " with grade " + voto);;
	}

	public String studentAvg(int matrStud) {
		int studId = matrStud - OFFSET_MATR;
		
		if (studId < 0 || studId >= nextStudentId)
			return null;
		
		return studenti[studId].media();
	}
	
	public String courseAvg(int courseCode) {
		int codIns = courseCode - OFFSET_INS;
		
		if (codIns < 0 || codIns >= nextInsId)
			return null;
		
		return corsi[codIns].media();
	}
	
	public String topThreeStudents() {
		int[] topStudsId = new int[MAX_BEST_STUDENTS];
		float[] topStudsAvg = new float[MAX_BEST_STUDENTS];
		StringBuilder topStuds = new StringBuilder();
		
		for (int i=0; i<MAX_BEST_STUDENTS; i++) {
			topStudsId[i] = -1;
			topStudsAvg[i] = -1;
		}
		
		for (int s=0; s<nextStudentId; s++) {
			for (int i=0; i<MAX_BEST_STUDENTS; i++) {
				if (studenti[s].mediaBonus() <= topStudsAvg[i])
					continue;
				
				for (int j=MAX_BEST_STUDENTS-1; j>i; j--) {	// scorro posizioni
					topStudsId[j] = topStudsId[j-1];
					topStudsAvg[j] = topStudsAvg[j-1];
				}
				
				topStudsId[i] = s;
				topStudsAvg[i] = studenti[s].mediaBonus();
				break;	// ho già scritto lo studente, altrimenti sovrascrivo i successivi
			}
		}
		
		for (int i=0; i<MAX_BEST_STUDENTS; i++) {
			if (topStudsId[i] != -1) {
				topStuds.append(studenti[topStudsId[i]].mediaBonusString()).append("\n");
			}
		}
		
		return topStuds.substring(0, topStuds.length()-1);	// tolgo l'ultimo \n
	}
}