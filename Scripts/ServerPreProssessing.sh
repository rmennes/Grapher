#!/bin/bash
#parameters is {input file} [{output directory}]
#default output directory is OUTPUT
output="OUTPUT"
if [ "$#" -eq 2 ]; then
	output=$2
fi
mkdir $output
echo "Start Splitting the files"
java -jar FileSplitter.jar $1 $output
echo "Start the preprossessing"
java -jar PreProssessing.jar $output
echo "End Preprossessing"
echo "Distributed the files in correct directories! than run jar file"

