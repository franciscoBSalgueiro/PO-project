export CLASSPATH=$(pwd)/po-uilib/po-uilib.jar:$(pwd)/prr-core/prr-core.jar:$(pwd)/prr-app/prr-app.jar
make
if [ $# -eq 0 ]
then
    cd ./prr-tests-ei-daily; ./runtests.sh tests
else
    cd ./prr-tests-ei-daily; ./runtests.sh $1
fi
