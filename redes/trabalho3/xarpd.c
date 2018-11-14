#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <signal.h>

//fonte - http://ascii-table.com/ansi-escape-sequences.php
#define KNRM  "\x1B[0m"     //printf("%snormal\n", KNRM);
#define KBLK  "\x1B[30m"     //printf("%black\n", KBLK);
#define KRED  "\x1B[31m"    //printf("%sred\n", KRED);
#define KGRN  "\x1B[32m"    //printf("%sgreen\n", KGRN);
#define KYEL  "\x1B[33m"    //printf("%syellow\n", KYEL);
#define KBLU  "\x1B[34m"    //printf("%sblue\n", KBLU);
#define KMAG  "\x1B[35m"    //printf("%smagenta\n", KMAG);
#define KCYN  "\x1B[36m"    //printf("%scyan\n", KCYN);
#define KWHT  "\x1B[37m"    //printf("%swhite\n", KWHT);

#define PORTA 5050

/*void minhafuncao(int sig){
    printf("Aqui!\n");
    exit(1);
}*/
//killall xarpd (para finalizar os deamons)
void daemonize(){
    if (fork() != 0) 
	    exit(1);
}
// Print the expected command line for the program
void print_usage(){
	printf("\n%sxarpd %s<interface> %s[%s<interface> %s...]\n", KBLU, KGRN, KNRM, KGRN, KNRM);
	exit(1);
}

int main(int argc, char* argv[]){
    int i;

	if(argc == 1) print_usage();
    //signal(SIGINT, minhafuncao);

    daemonize();

    if (optind < argc) {
        //validar as interfaces
        while (optind < argc){
            printf ("%s%s\n%s", KYEL, argv[optind++], KNRM);
        }
    }
}