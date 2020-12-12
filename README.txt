
This is a console-based application that scrapes information from the trending GitHub page and generates a CSV file containing the repository name, number of stars and programming language of the trending repositories (under today).

Technologies used
--------------------
Java 8
JSoup
Maven

Steps to run application on console
--------------------
With Maven:
1) Check that valid versions of Java 8 and Maven are installed with updated system PATH variables.
2) From root application folder, run mvn clean compile assembly:single. A target folder should be compiled and generated.
3) Go to the generated /target directory in the root application folder and run java -jar TrendingGitRepos-1.0-SNAPSHOT-jar-with-dependencies.jar. A CSV file will be generated in the same folder.

Alternatively,
1) Go to folder out/artifacts/TrendingGitRepos_jar
2) Run java -jar TrendingGitRepos.jar. The CSV file will be generated in the same folder.