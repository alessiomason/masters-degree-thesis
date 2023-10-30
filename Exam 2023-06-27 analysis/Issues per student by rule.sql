-- select all students numbers (having at least one issue), then left join to append number of issues of the student (if present)
SELECT DISTINCT SUBSTRING(kee, 31, 7) AS student, issues_per_student.count
FROM components
LEFT JOIN (
    -- select student number and number of issues for every student for a specific rule
    -- the components table contains the name of the folder (the component, in the column kee): that is, the student number
    SELECT SUBSTRING(C.kee, 31, 7) AS student, COUNT(*) AS count
    FROM issues I, components C
    WHERE I.component_uuid = C.uuid AND C.kee LIKE 'it.polito.oop:Exam_2023-06-27:s%'
      AND rule_uuid = 'AYgvIl4PPrvCAqFZdVNH'
    GROUP BY SUBSTRING(C.kee, 31, 7)
) AS issues_per_student ON SUBSTRING(components.kee, 31, 7) = student
WHERE kee LIKE 'it.polito.oop:Exam_2023-06-27:s%'
ORDER BY student