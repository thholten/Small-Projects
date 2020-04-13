#! /usr/bin/env bash

#outputs the number of ping spikes (determined either by hard threshold or number of standard deviations from the mean) over a given period of time
#arguments: <time>

total=0
avg=0
std=0.0
count=0
outlier_count=0
global_count=0

outlier=10000.00

outlier_stds=3
threshold=75

ping -i 0.5 -c $((2*$1)) 3.80.0.0 > pinglog.txt

while read line; do
    for token in $line; do
        if [[ $token =~ time=[0-9]*.[0-9]*\n? ]]; then
            num=${token#time=}
            if (( $(echo "$num < $threshold" | bc) )); then
                count=$((count + 1))
                total=$( bc <<< "scale=5; $total+$num" )
                avg=$( bc <<< "scale=5; $total/$count")
                if [[ $count > 240 ]]; then
                    std=$( bc <<< "scale=5; sqrt((( $std^2 * $count ) + ( $num - $avg )^2 ) / $count )" )
                    outlier=$( bc <<< "scale=5; $avg + ($outlier_stds * $std)" )
                fi
                global_count=$((global_count + 1))
            else
                echo Outlier: $num at time $( bc <<< "scale=5; 0.5 * $global_count")
                outlier_count=$((outlier_count + 1))
                global_count=$((global_count + 1))
            fi
        fi
    done
done < pinglog.txt

rm pinglog.txt

echo Average: $avg
echo Outliers: $outlier_count
echo STD: $std
