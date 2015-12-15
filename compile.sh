#!/bin/sh
# $1 = nama-file.java
# $2 = nama-file.jar

/home/hduser/Application/hadoop-2.7.1/bin/hadoop com.sun.tools.javac.Main $1
jar cf $2 *.class
rm *.class
