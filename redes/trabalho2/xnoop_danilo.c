#include <errno.h>
#include <stdio.h>
#include <netdb.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <signal.h>
#include <pthread.h>
#include <fcntl.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <arpa/inet.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <net/ethernet.h>

// Print the expected command line for the program
void print_usage(){
	printf("\nxnoop -i <interface> [options] [filter]\n");
	exit(1);
}

int main(int argc, char* argv[]){
	if(argc == 1) print_usage();

	char command; //auxiliar para parsing de argumentos

	//Parsing the command line arguments
	while ((command = getopt (argc, argv, "c:i:nvV")) != -1){
		switch (command){
			case 'c':
				break;
			case 'i':
				break;
			case 'n':
				break;
			case 'v':
				break;
			case 'V':
				break;
			case '?':
				if(optopt == 'i' || optopt == 'c')
					print_usage();
				break;
			default:
				break;
		}
	}
}