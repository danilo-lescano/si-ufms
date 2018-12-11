#!/bin/bash
cd redes
cd trabalho3
gcc -pthread arp.c -o arp &
./arp "192.168.1.2"
./arp "192.168.1.3"
./arp "192.168.1.4"
./arp "192.168.1.5"
./arp "192.168.1.6"
./arp "192.168.1.7"