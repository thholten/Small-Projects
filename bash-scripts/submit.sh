#!/usr/bin/env bash

#Removes package declarations and adds comments necessary for homework submission
#run from parent folder of package
#arguments: <assignment type> <assignment number>

    cd ${1}$2
    echo $(pwd)
    mkdir submissions/
    for file in *; do
        if [[ ! $file =~ \S*_copy.java ]]; then
            if [[ $file =~ \S*.java ]]; then
                sed "s!package ${1}$2;!//Thomas von Holten~//112765879~//CSE 160~//Section 1~//${1} #$2!" $file | tr "~" "\n" > temp_$file.txt
                cat temp_$file.txt > submissions/$file
                rm temp_$file.txt
            fi
        fi
    done

