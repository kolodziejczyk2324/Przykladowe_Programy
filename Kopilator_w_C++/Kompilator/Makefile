OBJS = Biblioteki/library.cpp Biblioteki/MyStack.cpp
make:	scanner.l parser.y
	bison -d parser.y
	flex scanner.l
	g++ -o $@ parser.tab.c lex.yy.c $(OBJS)
