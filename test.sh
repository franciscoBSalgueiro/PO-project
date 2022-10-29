export CLASSPATH=$(pwd)/po-uilib/po-uilib.jar:$(pwd)/prr-core/prr-core.jar:$(pwd)/prr-app/prr-app.jar
make
if [ $# -eq 0 ]
then
    cd ./tests; ./runtests.sh tests-ef
else
    cd ./tests; ./runtests.sh $1
fi
