#!/bin/bash

# Check if it's the first run
if [ ! -f /root/first_run_completed ]; then
    apt-get update -y &&
    apt-get upgrade -y &&
    apt install curl -y &&
    apt install nano -y &&
    apt install gcc g++ make -y &&

        #<install java>
    curl "https://download.java.net/java/GA/jdk20.0.1/b4887098932d415489976708ad6d1a4b/9/GPL/openjdk-20.0.1_linux-x64_bin.tar.gz" -o java.tar.gz &&
    tar -xvzf java.tar.gz &&
    export JAVA_HOME=/jdk-20.0.1 &&
    
    echo "Setup successful." &&
    touch /root/first_run_completed
fi

#<Run container indefinitely>
sleep infinity