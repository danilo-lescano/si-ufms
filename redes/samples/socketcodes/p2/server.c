#include <errno.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <sys/types.h> 
#include <sys/socket.h>
#include <netinet/in.h>

#define LISTEN_ENQ 5

int main(int argc, char** argv) {
	int sockfd;
	int portno;
	int clilen;
	int newsockfd;
	char* retvalue;
	char buffer[256];
	//man 7 ip
	//man unix
	struct sockaddr_in serv_addr;
	struct sockaddr_in cli_addr;
  
	if(argc != 2) {
		fprintf(stderr, "Usage: %s <port>\n", argv[0]);
		exit(1);
	}
  
	//man socket
	sockfd = socket(AF_INET, SOCK_STREAM, 0);
	if(sockfd < 0) {
		fprintf(stderr, "ERROR: %s\n", strerror(errno));
		exit(1);
	}
   
	memset((char*) &serv_addr, 0, sizeof(serv_addr));
  
	portno = atoi(argv[1]);
	serv_addr.sin_family = AF_INET;
	serv_addr.sin_addr.s_addr = INADDR_ANY;
	serv_addr.sin_port = htons(portno);
	//man bind
	if(bind(sockfd, (struct sockaddr*) &serv_addr, sizeof(serv_addr)) < 0) {
		fprintf(stderr, "ERROR: %s\n", strerror(errno));
		exit(1);
	}
  
	//man listen
	if(listen(sockfd, LISTEN_ENQ) < 0) {
		fprintf(stderr, "ERROR: %s\n", strerror(errno));
		exit(1);
	}

	clilen = sizeof(cli_addr);
	//man accept
	newsockfd = accept(sockfd, (struct sockaddr*) &cli_addr, (unsigned int*) &clilen);
	if(newsockfd < 0) {
		fprintf(stderr, "ERROR: %s\n", strerror(errno));
		exit(1);
	}

	while(1) {
		memset(buffer, 0, sizeof(buffer));
  
		//man recv
		if(recv(newsockfd, buffer, sizeof(buffer), 0) < 0) {
			fprintf(stderr, "ERROR: %s\n", strerror(errno));
			exit(1);
		}
		
		printf("Mensagem recebida: %s\n", buffer);

		printf("Digite a mensagem: ");
		memset(buffer, 0, sizeof(buffer));
		
		do {
			retvalue = fgets(buffer, sizeof(buffer), stdin);
		} while(retvalue == NULL);
		
		//man send
		if(send(newsockfd, buffer, strlen(buffer), 0) < 0) {
			fprintf(stderr, "ERROR: %s\n", strerror(errno));
			exit(1);
		}
	}

	close(newsockfd);
	close(sockfd);
	
	return 0; 
}
