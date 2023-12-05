# Tesi di Laurea Magistrale

Questo repository contiene file, codice e dati tratti dal mio lavoro di tesi magistrale. La tesi in sé verrà inclusa una volta completata.

[🌐 Here in English](README.md)

### Titolo e abstract

**"Valutazione di esercizi di programmazione tramite debito tecnico"**

Negli ultimi anni, il concetto di debito tecnico sta venendo sempre più adottato per descrivere il problema introdotto - più o meno consciamente - dalle imperfezioni nel codice nel tentativo di ridurre tempi e costi dello sviluppo del software.

Lo scopo di questa tesi è quello di verificare se il debito tecnico, concetto solitamente applicato nelle grandi aziende, possa essere applicato al software sviluppato da studenti nei loro esercizi di programmazione.

L'idea potrebbe essere utile sia per gli studenti che per i docenti.  
Gli studenti potrebbero trovare utile l'aiuto di un'analisi automatica dei propri programmi per comprendere quali argomenti non hanno ancora interiorizzato completamente e in quali errori o sviste sono incorsi durante lo studio.  
Per i docenti, d'altra parte, analizzare il codice prodotto dagli studenti potrebbe essere utile in due diverse occasioni: durante il corso, per comprendere quali argomenti non sono ancora del tutto chiari agli studenti e probabilmente necessitano ancora di un ripasso. In seguito, dopo la valutazione finale, l'analisi del debito tecnico potrebbe essere utile per valutare la generale comprensione degli argomenti più difficili o persino per premiare gli studenti che hanno scritto codice non soltanto funzionante ma anche pulito e manutenibile, che è un'abilità spesso non considerata nei corsi iniziali ma che sicuramente deve essere acquisita prima di affacciarsi al mondo del lavoro.

Nello specifico, questa tesi esamina un contesto specifico, il corso di _Programmazione ad oggetti_ della Laurea triennale in Ingegneria Informatica del Politecnico di Torino: veri progetti passati sono stati analizzati verificando quali problematiche si rilevano come le più comuni tra gli studenti, se queste hanno senso in un contesto di studenti ai primi approcci con la programmazione e la loro possibile correlazione con la valutazione finale ricevuta.

Questo studio si è rivelato utile a comprendere gli errori comunemente ripetuti tra gli studenti e ad elaborare un modo per automatizzare la suddetta analisi, cosicché, in futuro, si possa condurre anche durante il corso per meglio comprendere la preparazione degli studenti.

### Contenuto delle cartelle

La cartella "Initial analysis" contiene una breve presentazione e un riassunto dei dati raccolti e una sottocartella contenente tutti i progetti analizzati in questa fase iniziale.

La cartella "Multi-project analysis" contiene i comandi per creare il container Docker di SonarQube e per eseguire l'analisi, in aggiunta ad una cartella utilizzata per testare la gerarchia e il collocamento delle cartelle e dei file necessari per questa soluzione.

La cartella "Multi-module analysis" contiene nuovamente una cartella di test, con tutte le sottocartelle e i file collocati correttamente per verificare la viabilità di questa soluzione, i template per i POM padre e figlio, lo script per automatizzare l'analisi e il comando per lanciarlo.

La cartella "Exam 2023-06-27 analysis" contiene una presentazione dei principali bug e code smell rilevati nell'analisi, gli script SQL impiegati per recuperare i dati dal database che SonarQube utilizza e i due file Excel riassuntivi di tutti i dati raccolti, che sono stati utilizzati per eseguire l'analisi statistica - un file contenente tutti gli studenti analizzati e l'altro solo quelli che hanno effettivamente ricevuto una valutazione per l'esame: questo secondo file (_Exam 2023-06-27 only marks_) è quello utilizzato per tutte le varie analisi statistiche.  
L'effettiva cartella contenente tutti i progetti analizzati non è inclusa per ragioni di privacy degli studenti (nei file Excel le marticole degli studenti sono anonimizzate).