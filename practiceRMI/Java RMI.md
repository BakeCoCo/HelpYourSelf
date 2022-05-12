## Java RMI(Java Remote Method Invocating)란?

= 자바 원격 메소드 호출





javac -d testClasses RSInterface.java RSMain.java RCMain.java -encoding UTF-8

rmiregistry -J-classpath -Jclasses

java -classpath testClasses test.rmi.RCMain 1 2 3 4 5

rmic -classpath . LoggingTimeImpl



PS C:\IDEA\workspace\practiceRMI\src\main\java\test\rmi> rmic -classpath . test.rmi.RSMain


Warning: generation and use of skeletons and static stubs for JRMP
is deprecated. Skeletons are unnecessary, and static stubs have
been superseded by dynamically generated stubs. Users are
encouraged to migrate away from using rmic to generate skeletons and static
stubs. See the documentation for java.rmi.server.UnicastRemoteObject.


PS C:\IDEA\workspace\practiceRMI\src\main\java\test\rmi>



UnicastRemoteObject 가 있는 Server가 Stub만듬
