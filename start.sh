export CLASSPATH=$(pwd)/po-uilib/po-uilib.jar:$(pwd)/prr-core/prr-core.jar:$(pwd)/prr-app/prr-app.jar
make
java prr.app.App