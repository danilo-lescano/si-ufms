#!/bin/bash
cd redes
cd trabalho3
gcc -pthread xarpd_esqueleto.c -o xarpd
./xarpd enp0s3