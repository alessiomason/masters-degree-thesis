SELECT SUBSTRING(C.kee, 31, 7) AS student, R.name AS rule, COUNT(*)
FROM issues I, rules R, components C
WHERE I.rule_uuid = R.uuid AND I.component_uuid = C.uuid AND C.kee LIKE 'it.polito.oop:Exam_2023-06-27:s%'
GROUP BY SUBSTRING(C.kee, 31, 7), rule_uuid, R.name
ORDER BY student