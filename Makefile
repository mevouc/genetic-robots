# Makefile for Genetic Robots project

all:
	${MAKE} -C src/

run:
	${MAKE} run -C src/

clean:
	rm -rf *.class *~
	${MAKE} clean -C src/

# END
