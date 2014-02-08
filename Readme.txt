Packages generation :
mvn clean package

Releases management :
mvn release:prepare -DautoVersionSubmodules
mvn release:perform -Dgoals=package

Prototyping :
 mvn portlet-prototyping:run
 and go on http://localhost:8080/pluto