#!/bin/bash

# number of tests passed
passed=0
failed=0

for x in $1/*.in; do
    if [ -e ${x%.in}.import ]; then
        java -Dimport=${x%.in}.import -Din=$x -Dout=${x%.in}.outhyp prr.app.App;
    else
        java -Din=$x -Dout=${x%.in}.outhyp prr.app.App;
    fi

    diff -cB -w ${x%.in}.out ${x%.in}.outhyp > ${x%.in}.diff ;
    if [ -s ${x%.in}.diff ]; then
        failed=$((failed+1))
        echo "FAIL: $x. See file ${x%.in}.diff " ;
    else
        passed=$((passed+1))
        echo "PASS: $x" ;
        rm -f ${x%.in}.diff ${x%.in}.outhyp ; 
    fi
done

echo "Passed: $passed"
echo "Failed: $failed"

#rm -f saved*

echo "Done."

