package university;

class Student {	// non metto public, solo classi all'interno del package possono accedere
	
	public final int MAX_INS = 25;
	
	private String firstName;
	private String lastName;
	private int matricola;
	
	// piano di studi
	private int[] corsi = new int[MAX_INS];
	private int nextCorso = 0;
	
	// esami sostenuti
	private int[] esami = new int[MAX_INS];
	
	public Student(String firstName, String lastName, int matricola) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.matricola = matricola;
		
		for (int i=0; i<MAX_INS; i++)
			esami[i] = -1;
	}
	
	public int getMatricola() {
		return matricola;
	}
	
	public String toString() {
		// matricola implicitamente trasformato in String dalla concatenazione
		return matricola + " " + firstName + " " + lastName;
	}
	
	public void aggIns(int codIns) {
		if (nextCorso < MAX_INS)
			corsi[nextCorso++] = codIns;
	}
	
	public int[] elencoCorsi() {
		return corsi;
	}
	
	public void aggEsame(int codIns, int voto) {
		for (int i=0; i<nextCorso; i++) {
			if (corsi[i] == codIns) {
				esami[i] = voto;
				break;
			}
		}
	}
	
	public String media() {
		float sommaVoti = 0;
		int nVoti = 0;
		
		if (nextCorso > 0) {
			for (int i=0; i<nextCorso; i++) {
				if (esami[i] != -1) {
					sommaVoti += esami[i];
					nVoti++;
				}
			}
			
			return "Student " + matricola + ": " + (sommaVoti/nVoti);
		}
		
		return "Student " + matricola + " hasn't taken any exams";
	}
	
	public float mediaBonus() {
		float sommaVoti = 0;
		int nVoti = 0;
		
		if (nextCorso > 0) {
			for (int i=0; i<nextCorso; i++) {
				if (esami[i] != -1) {
					sommaVoti += esami[i];
					nVoti++;
				}
			}
			
			return (sommaVoti/nVoti) + (((float)nVoti)/nextCorso) * 10;	// return media + bonus (=nEsami/nCorsi*10)
		}
		
		return 0;
	}
	
	public String mediaBonusString() {
		return firstName + " " + lastName + ": " + this.mediaBonus();
	}
}