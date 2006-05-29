LIBDIR=../../../../cygwin/home/Nakano/chorus3d-0.5.6/jni
#
# Linux
#
#CLASSPATH=.:$(LIBDIR):C:/Program\ Files/Java/jdk1.5.0_06/lib/ext/vecmath.jar
#
# Windows
#
CLASSPATH=.\;$(LIBDIR)\;C:/Program\ Files/Java/jdk1.5.0_06/lib/ext/vecmath.jar\;C:/Program\ Files/Java/jdk1.5.0_06/lib/ext/j3dcore.jar\;C:/Program\ Files/Java/jdk1.5.0_06/lib/ext/j3dutils.jar
#
# common
#
#JAVAC=javac -g -classpath $(CLASSPATH)
JAVAC=javac -O -classpath $(CLASSPATH)
JAVA=java -classpath $(CLASSPATH)
JDB=jdb -classpath $(CLASSPATH)
RM=rm -f

all: test.class

chorus3d: 
#	cd $(LIBDIR); make chorus3d

test.class: chorus3d test.java
	$(JAVAC) test.java

test_run: #test.class
	LD_LIBRARY_PATH=$(LIBDIR) PATH="$(LIBDIR):$(PATH)" $(JAVA) test

test_debug: test.class
	LD_LIBRARY_PATH=$(LIBDIR) PATH="$(LIBDIR):$(PATH)" $(JDB) test

clean:
	cd $(LIBDIR); make clean
	$(RM) *.class
