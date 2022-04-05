#include <stdio.h>
#include <stdlib.h>
#include <string.h>
void test(char ** mots){
    /*char *** patate = malloc(100*sizeof(char **));
    for(int i = 0 ; i< 100; i++){
    	patate[i] = malloc(2*sizeof(char *));
    	patate[i][0] = malloc(100*sizeof(char));
    	patate[i][1] = malloc(100*sizeof(char));
    	
    	strcpy(patate[i][0],"keske");
    	strcpy(patate[i][1],"owi");
    	}
    return patate;*/
    printf("%s", mots[0]);
    printf("%s", mots[1]);
    
    
}
