#!/bin/bash

sizes=( 86 128 256 512 768 1024 1280 1448 1518)
input_dir="Resultaten/UDP-IPV6/SERVER/SplitFiles"
output_dir="Resultaten/UDP-IPV6/SERVER/charts/"
types=( throughput jitter packet )

if [ "$#" -gt 0 ]; then
	input_dir=$1
fi
if [ "$#" -gt 1 ]; then
	output_dir=$2
fi

for size in "${sizes[@]}"
do
    for type in "${types[@]}"
    do
    java -jar Grapher.jar ${type} ${output_dir}/${type}_${size}.txt "${type} ${size}B" ${input_dir}/${size}B
    done
done

python fetcher.py ${output_dir}
