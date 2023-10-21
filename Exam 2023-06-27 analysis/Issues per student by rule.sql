-- seleziona tutte le matricole (che hanno almeno un issue), poi left join per appendere numero di problemi dello studente (se presente)
SELECT DISTINCT SUBSTRING(kee, 31, 7) AS student, issues_per_student.count
FROM components
LEFT JOIN (
    -- seleziona matricola e numero di problemi di qualità per ogni studente relativo ad una specifica regola
    -- la tabella dei componenti contiene il nome della cartella (il componente, nella colonna kee), cioè la matricola dello studente
    SELECT SUBSTRING(C.kee, 31, 7) AS student, COUNT(*) AS count
    FROM issues I, components C
    WHERE I.component_uuid = C.uuid AND C.kee LIKE 'it.polito.oop:Exam_2023-06-27:s%'
      AND rule_uuid = 'AYgvIl4PPrvCAqFZdVNH'
    GROUP BY SUBSTRING(C.kee, 31, 7)
) AS issues_per_student ON SUBSTRING(components.kee, 31, 7) = student
WHERE kee LIKE 'it.polito.oop:Exam_2023-06-27:s%'
ORDER BY student