# Master's degree thesis

This repository contains files, code and data from my Master's degree thesis work. The thesis paper itself is contained in the "Thesis" folder, or is accessible from the [Politecnico di Torino's Webthesis portal](https://webthesis.biblio.polito.it/29379/).

[🌐 Qui in italiano](README_it.md)

### Title and abstract

**"Programming assignments evaluation using technical debt"**

In recent years, the idea of technical debt is gaining adoption to describe the problem introduced - more or less consciously - by imperfections in code in an attempt to reduce time and costs of software development.

The goal of this thesis is to investigate whether technical debt, a concept usually applied to large industrial software, can be applied to the software developed by students in their programming assignments.

The idea could be useful both for students and for teachers.  
Students could find the aid of an automated analysis of their programs helpful to understand which topics they still have not understood completely and what mistakes or oversights they incurred during their preparation.  
For teachers, on the other hand, analysing the code produced by students might be useful at two different times: throughout the course, to understand whether some topics are still a bit obscure to a number of students and might require a revision. Then, following the final evaluation, technical debt analysis may be useful to understand the overall comprehension of the most difficult concepts or even to award the students who wrote not only working but also clean and maintainable code, which is a skill that is not usually considered in early-on courses but that most definitely has to be picked up before entering the labour market.

Specifically, this paper examines a known context, the _Object-oriented programming_ course for the Computer Engineering Bachelor's degree of Politecnico di Torino: real past projects have been analysed verifying which problems arise as the commonest amongst the students, whether they make sense in the context of students' initial approaches to programming and their possible correlation with the final evaluation received.

This study did prove useful to understand the repeating issues amongst the students and to work out a way to automate said analysis, so that, in the future, it could be carried out even during the course to better comprehend the students' preparation.

### Folders content

The "Initial analysis" folder contains a short presentation and summary of the gathered data and a subfolder containing all the projects analyzed for this initial phase.

The "Multi-project analysis" folder contains the commands to create the Docker container hosting SonarQube and to run the analysis, in addition to a folder used for testing the hierarchy and placement of folders and files needed for this solution.

The "Multi-module analysis" folder contains again a test folder, with all subfolders and files placed correctly to verify the viability of this solution, the templates for the parent and child POM, the script to automate the analysis and the command to launch it.

The "Exam 2023-06-27 analysis" folder contains a presentation of the major bugs and code smells found in the analysis, the SQL scripts employed to retrieve the data from the database SonarQube uses and two Excel files summarizing all gathered data, which were used to perform the statistical analysis - one file containing all the analysed students and the other only the ones who actually received an evaluation for the exam: this second file (_Exam 2023-06-27 only marks_) is the one used for all the different statistical analyses.  
The actual folder containing all the analysed projects is not included due to privacy concerns for the students (in the Excel files the students numbers are anonymized).

The "Thesis" folder contains the thesis itself, the summary and the presentation used for the final exam.