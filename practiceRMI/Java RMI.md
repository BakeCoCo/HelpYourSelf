## Java RMI(Java Remote Method Invocating)란?

= 자바 원격 메소드 호출





javac -d testClasses RSInterface.java RSMain.java RCMain.java -encoding UTF-8

rmiregistry -J-classpath -Jclasses

java -classpath testClasses test.rmi.RCMain 1 2 3 4 5

