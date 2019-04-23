
JFLAGS = -g -cp src -Xlint:deprecation 
JC = javac
JVM = java
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	src/t1comp/App.java \
	src/t1comp/model/TokenType.java \
	src/t1comp/model/Token.java \
	src/t1comp/model/AnalisadorLexico.java \
	src/t1comp/model/SymbolsTable.java \
	src/t1comp/model/TableEntry.java \
	src/t1comp/view/View.java \
	src/utils/FileHanddler.java 



default: classes

classes: $(CLASSES:.java=.class)

run: 
	cd src && java t1comp.App

clean:
	$(RM) *.class




