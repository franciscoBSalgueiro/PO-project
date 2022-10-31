#!/bin/bash

RED='\033[1;31m'
GREEN='\033[1;32m'
NC="\033[0m" # No Color
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
        echo -e "${RED} FAIL: $x. See file ${x%.in}.diff ${NC}"
    else
        passed=$((passed+1))
        echo -e "${GREEN} PASS: $x ${NC}"
        rm -f ${x%.in}.diff ${x%.in}.outhyp ; 
    fi
done

echo -e "${GREEN} Passed ${NC}: $passed"
echo -e "${RED} Failed ${NC}: $failed"

#rm -f saved*

echo "Done."

