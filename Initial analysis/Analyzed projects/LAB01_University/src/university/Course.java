package university;

class Course {	// non metto public, solo classi all'interno del package possono accedere
	
	public final int MAX_ISCRITTI = 100;
	
	private int codice;
	private String titolo;
	private String nomeDocente;
	
	// iscritti ai corsi
	private int[] iscritti = new int[MAX_ISCRITTI];
	private int nextIscritto = 0;
	
	// voti
	private int[] voti = new int[MAX_ISCRITTI];
	
	public Course(String titolo, String nomeDocente, int codice) {
		this.titolo = titolo;
		this.nomeDocente = nomeDocente;
		this.codice = codice;
		
		for (int i=0; i<MAX_ISCRITTI; i++)
			voti[i] = -1;
	}
	
	public int getCodice() {
		return codice;
	}
	
	public String toString() {
		return Integer.toString(codice) + ", " + titolo + ", " + nomeDocente;
	}
	
	public void iscrStud(int matrStud) {
		if (nextIscritto < MAX_ISCRITTI)
			iscritti[nextIscritto++] = matrStud;
	}
	
	public int[] elencoIscritti() {
		
		return iscritti;
	}
	
	public void aggVoto(int matrStud, int voto) {
		
		for (int i=0; i<nextIscritto; i++) {
			if (iscritti[i] == matrStud)
				voti[i] = voto;
		}
	}
	
	public String media() {
		float sommaVoti = 0;
		int nVoti = 0;
		
		if (nextIscritto > 0) {
			for (int i=0; i<nextIscritto; i++) {
				if (voti[i] != -1) {
					sommaVoti += voti[i];
					nVoti++;
				}
			}
			
			return "The average for the course " + titolo + " is: " + (sommaVoti/nVoti);
		}
		
		return "No student has taken the exam in " + titolo;
	}
}