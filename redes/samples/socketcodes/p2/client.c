#include <errno.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h>
#include <arpa/inet.h>

int main(int argc, char** argv) {
	int sockfd;
	int portno;
	char* retvalue;
	char buffer[256];
	//man 7 ip
	//man unix
	struct sockaddr_in serv_addr;

	if(argc != 3) {
		fprintf(stderr, "Usage: %s <address> <port>\n", argv[0]);
		exit(1);
	}
  
	portno = atoi(argv[2]);
	//man socket
	sockfd = socket(AF_INET, SOCK_STREAM, 0);
  
	if(sockfd < 0) {
		fprintf(stderr, "ERROR: %s\n", strerror(errno));
		exit(1);
	}
  
	memset((char*) &serv_addr, 0, sizeof(serv_addr));
  
	serv_addr.sin_family = AF_INET;
	serv_addr.sin_addr.s_addr = inet_addr(argv[1]);
	serv_addr.sin_port = htons(portno);
  
	//man connect
	if(connect(sockfd, (struct sockaddr*) &serv_addr, sizeof(serv_addr)) < 0) {
		fprintf(stderr, "ERROR: %s\n", strerror(errno));
		exit(1);
	}

	while(1) {
		memset(buffer, 0, sizeof(buffer));
		
		printf("Digite a mensagem: ");

		do {
			retvalue = fgets(buffer, sizeof(buffer), stdin);
		} while(retvalue == NULL);
  
		//man send
		if(send(sockfd, buffer, strlen(buffer), 0) < 0) {
			fprintf(stderr, "ERROR: %s\n", strerror(errno));
			exit(1);
		}
  
		memset(buffer, 0, sizeof(buffer));
		//man recv
		if(recv(sockfd, buffer, sizeof(buffer), 0) < 0) {
			fprintf(stderr, "ERROR: %s\n", strerror(errno));
			exit(1);
		}

		printf("Mensagem recebida: %s\n", buffer);

	}
	
	close(sockfd);
	
	return 0;
}
