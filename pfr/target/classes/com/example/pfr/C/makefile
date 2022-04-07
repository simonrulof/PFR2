MODULE_NAME=admin
SRCS = \
	fonctionsAdmin.c \

CC=gcc
JAVA_HOME= /lib/jvm/java-11-openjdk-amd64
DEPS=-I$(JAVA_HOME)/include -I$(JAVA_HOME)/include/linux
CFLAGS=-fPIC
OBJ=fonctionsAdmin.o $(MODULE_NAME)_wrap.o
lib:
	swig -java -package swig $(MODULE_NAME).i
	gcc $(CFLAGS) -c  $(MODULE_NAME)_wrap.c $(DEPS)
	gcc $(CFLAGS) -c $(SRCS)
	ld -G $(OBJ) -o lib$(MODULE_NAME).so
	sudo mv ./lib$(MODULE_NAME).so /usr/lib/lib$(MODULE_NAME).so