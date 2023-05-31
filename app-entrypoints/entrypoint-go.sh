#!/bin/bash

# Check if it's the first run
if [ ! -f /root/first_run_completed ]; then
    apt-get update -y &&
    apt-get upgrade -y &&
    apt install curl -y &&
    apt install nano -y &&
    apt install gcc g++ make -y &&

        #<install go>
    apt install golang -y &&
    
    echo "Setup successful." &&
    touch /root/first_run_completed
fi

#<Run container indefinitely>
sleep infinity