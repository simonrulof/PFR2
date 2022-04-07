#ifndef INDEXIMAGE
#define INDEXIMAGE

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>
#include "struct.h"

unsigned int quantification(int pixelR, int pixelV, int pixelB);

int indexationImage(char * dossierFichier, char * nomFichierAIndexer, char * nomFichierStockage);

int comparaisonImage(char * dossierFichier, char * nomFichierAComparer, char * fichierDescripteur, FICHIER * listeFichiers);

#endif