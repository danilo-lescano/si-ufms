#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h> 
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h>
#include <arpa/inet.h>
#include <err.h>

#define LISTEN_BACKLOG 5//The backlog argument defines the maximum length to which the  queue  of
						//pending  connections  for  sockfd  may  grow.   If a connection request
						//arrives when the queue is full, the client may receive an error with an
						//indication  of  ECONNREFUSED  or,  if  the underlying protocol supports
						//retransmission, the request may be ignored so that a later reattempt at
						//connection succeeds.


//<SP> = espaco simples
//<crlf> = \r\n
char response[] = "HTTP/1.1 200 OK\r\n"
"Server: FACOM-RC-2016/1.0\r\n"
"Content-Type: text/html; charset=UTF-8\r\n\r\n"
"<!DOCTYPE html><html><head><title>Hello World</title>"
"<style>body { background-color: #111 }"
"h1 { font-size:4cm; text-align: center; color: black;"
" text-shadow: 0 0 2mm red}</style></head>"
"<body><h1>Hello, world!</h1></body></html>\r\n";
char response2[] = "HTTP/1.1 404 File Not Found\r\n"
"Server: FACOM-RC-2016/1.0\r\n"
"Content-Type: text/html; charset=UTF-8\r\n\r\n"
"<!DOCTYPE html><html><head><title>404</title>"
"<style>body { background-color: #111 }"
"h1 { font-size:4cm; text-align: center; color: black;"
" text-shadow: 0 0 2mm red}</style></head>"
"<body><h1>FILE NOT FOUND =(!</h1><br><h1>404</h1></body></html>\r\n";
 
int main(int argc, char** argv){
	int one = 1, client_fd;
	struct sockaddr_in server_address, client_address;
	socklen_t client_address_lenght = sizeof(client_address);

	int server_socket = socket(AF_INET, SOCK_STREAM, 0);
	if(server_socket < 0)
		err(1, "DESCRIPTION: Can't open socket\nERROR");

	server_address.sin_family = AF_INET;
	server_address.sin_addr.s_addr = INADDR_ANY;
	server_address.sin_port = htons(atoi("5050"));

	if (bind(server_socket, (struct sockaddr *) &server_address, sizeof(server_address)) == -1) {
		close(server_socket);
		err(1, "DESCRIPTION: Can't bind\nERROR");
	}

	listen(server_socket, LISTEN_BACKLOG);
	while (1) {
		client_fd = accept(server_socket, (struct sockaddr *) &client_address, &client_address_lenght);
		printf("got connection\n");

		if(argc > 2){
			fprintf(stdout, "%s\n", argv[2]);
		}

		if (client_fd == -1) {
			perror("DESCRIPTION: Can't accept\nERROR");
			continue;
		}
		if (send(client_fd, response, sizeof(response) - 1, 0) < 0){ /*-1:'\0'*/
			perror("DESCRIPTION: Message was not sent\nERROR");
			continue;
		}
		close(client_fd);
	}
}