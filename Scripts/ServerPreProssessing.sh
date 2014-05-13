#!/bin/bash
#parameters is {input file} [{output directory}]
#default output directory is OUTPUT
output="OUTPUT"
sizes=( 86 128 256 512 768 1024 1280 1448 1518)
if [ "$#" -eq 2 ]; then
	output=$2
fi
mkdir $output
for size in "${sizes[@]}"
do
	name="$output/$size"
	mkdir $name
done
echo "Start Splitting the files"
java -jar FileSplitter.jar $1 $output
echo "Start the preprossessing"
java -jar PreProssessing.jar $output
echo "End Preprossessing"
echo "Distributed the files in correct directories! than run jar file"

