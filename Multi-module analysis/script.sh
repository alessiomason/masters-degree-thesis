#! /bin/bash

parentArtifactId=$1
sonarToken=$2

if [ "$parentArtifactId" = "" ]; then
	echo "Missing project name in arguments."
	exit
fi

if [ "$parentArtifactId" = "parentArtifactId" ]; then
	echo "Project name not allowed."
	exit
fi

if [ "$sonarToken" = "" ]; then
	echo "Missing SonarQube token in arguments."
	exit
fi

echo "Preparing the necessary pom files for building the submodules..."

# substitute the artifactId in the parent pom
sed "s|<artifactId>parentArtifactId</artifactId>|<artifactId>$parentArtifactId</artifactId>|" ./templates/parent_pom.xml >pom.xml

# find only directories in current folder, excluding hidden ones
subfolders=$(find . -mindepth 1 -maxdepth 1 -type d -regex '\./[^\.].*')

for dir in $subfolders; do
	if [ "$dir" = "./target" ] || [ "$dir" = "./templates" ]; then
		continue
	fi

	childArtifactId=$(echo "$dir" | cut -d '/' -f 2) # cut the string on / and take the second field (the folder name)

	if [ "$childArtifactId" = "childArtifactId" ] || [ "$childArtifactId" = "module" ]; then
		echo "Subfolder name $childArtifactId not allowed."
		exit
	fi

	# create the child pom
	cd "$dir" || {
		echo "Directory $dir not found!"
		exit
	}
	sed "s|<artifactId>parentArtifactId</artifactId>|<artifactId>$parentArtifactId</artifactId>|" ../templates/child_pom.xml >tmp.xml
	sed "s|<artifactId>childArtifactId</artifactId>|<artifactId>$childArtifactId</artifactId>|" tmp.xml >pom.xml
	rm tmp.xml
	cd ..

	# add the submodule to the parent pom
	cp pom.xml tmp.xml
	sed "s|<module>module</module>|<module>$childArtifactId</module>\n    <module>module</module>|" tmp.xml >pom.xml
	rm tmp.xml
done

# finalize parent pom
cp pom.xml tmp.xml
sed "/<module>module<\/module>/d" tmp.xml >pom.xml
rm tmp.xml

# run analysis
echo "Building the projects and then running the analysis..."
mvn clean install --fail-never sonar:sonar -Dsonar.token="$sonarToken"
