#!/bin/sh
./clean
./compile.sh AuthorCounter.java authorcounter.jar
./compile.sh Order.java order.jar
./run.sh authorcounter.jar AuthorCounter /user/akhfa/input
./run.sh order.jar Order /user/akhfa/output
clear
./cat /user/akhfa/output/*
