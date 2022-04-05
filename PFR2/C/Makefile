application : indexationTexte.c fonctionsAdmin.c
	gcc -c indexationTexte.c
	gcc -c IndexImage.c
	gcc -c fonctionsAdmin.c
	gcc -o fonctionsAdmin.out fonctionsAdmin.o indexationTexte.o
	./fonctionsAdmin.out

main : indexationTexte.c fonctionsAdmin.c main.c 
	gcc -c indexationTexte.c
	gcc -c IndexImage.c
	gcc -c Audio.c
	gcc -c fonctionsAdmin.c
	gcc -c main.c
	gcc -o main.out main.o fonctionsAdmin.o indexationTexte.o IndexImage.o Audio.o -lm -fno-stack-protector

texte : 
	gcc -c indexationTexte.c
	gcc -o indexationTexte.out indexationTexte.o -fno-stack-protector

audio : 
	gcc -c Audio.c
	gcc -o Audio.out Audio.o