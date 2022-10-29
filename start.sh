export CLASSPATH=$(pwd)/po-uilib/po-uilib.jar:$(pwd)/prr-core/prr-core.jar:$(pwd)/prr-app/prr-app.jar
make
if [ $# -eq 0 ]
then
    java prr.app.App
else
    java -Dimport=./tests/imports/$1.import prr.app.App
fi
