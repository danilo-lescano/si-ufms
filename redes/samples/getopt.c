#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include <unistd.h>

int main (int argc, char *argv[])
{
	int opcao;		// Opção passada pelo usuário ao programa.
	int indice;		// Usado para pegar argumentos que não são opções
				// Nem argumentos usados em opções.

	int xflag =  0;		// Opção -x desativada por default
	int yflag =  0;		// Opção -y desativada por default

	char *zarg = NULL;      // Irá apontar para o argumento de z
				// se for passado.

	// Desativa as mensagens de erro da função getopt
	opterr = 0;

	// Faz um loop pegando as opções passados pelo usuário. Note
	// o ':' depois do 'z'. Isso quer dizer que deve haver um
	// argumento depois dessa opção.
	while ((opcao = getopt (argc, argv, "xyz:")) != -1)
	{
		switch (opcao)
		{
			// Usuário passou a opção -x, habilita:
			case 'x':
				xflag = 1;
				break;

			// Usuário passou a opção -y, habilita:
			case 'y':
				yflag = 1;
				break;

			// Usuário escolheu a opção -z, pegar o argumento
			case 'z':
				zarg = optarg;
				break;

			// Se houve algum problema, vamos diagnosticar e enviar
			// nossas próprias mensagens de erro.
			case '?':
				if (optopt == 'z')	// Esqueceu um argumento
					fprintf (stderr, "Opção '-%c' requer argumento.\n", optopt);
				else if (isprint (optopt))
				     	fprintf(stderr, "Opção '-%c' desconhecida.\n", optopt);
			        else
			            fprintf(stderr, "Caractere '\\x%x' de opção desconhecido.\n", optopt );
				exit (1);
		}
	}

	printf ("         xflag = %s\n", xflag ? "sim" : "não");
	printf ("         yflag = %s\n", yflag ? "sim" : "não");
	printf ("          zarg = %s\n", zarg  ?  zarg : "nenhum");

	printf ("Outros argumentos:\n");

	for (indice = optind; indice < argc; indice++)
		printf ("                 %s\n", argv[indice]);

	return 0;
}