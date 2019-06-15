//FONTES PARA O TRABALHO
//https://blog.abhijeetr.com/2010/04/very-simple-http-server-writen-in-c.html
//https://rosettacode.org/wiki/Hello_world/Web_server

#include<stdio.h>
#include<string.h>
#include<stdlib.h>
#include<unistd.h>
#include<sys/types.h>
#include<sys/stat.h>
#include<sys/socket.h>
#include<arpa/inet.h>
#include<netdb.h>
#include<signal.h>
#include<fcntl.h>

#define LISTEN_BACKLOG 5//The backlog argument defines the maximum length to which the  queue  of
						//pending  connections  for  sockfd  may  grow.   If a connection request
						//arrives when the queue is full, the client may receive an error with an
						//indication  of  ECONNREFUSED  or,  if  the underlying protocol supports
						//retransmission, the request may be ignored so that a later reattempt at
						//connection succeeds.
#define BYTES 1024

char *ROOT;
int listenfd, clients[LISTEN_BACKLOG];
void error(char *);
void startServer(char *);
void respond(int);

char http404[] = "HTTP/1.1 404 File Not Found\r\n";
char http400[] = "HTTP/1.0 400 Bad Request\n";
char http200[] = "HTTP/1.1 200 OK\r\n";

char cabecalho[] = "Server: FACOM-RC-2016/1.0\r\n"
"Content-Type: */*; charset=UTF-8\r\n\r\n";

char html404[] = "<!DOCTYPE html><html><head>"
"<title>Danilo's server</title>"
"<style>body { background-color: #111 }"
"h1 { font-size:2cm; text-align: center; color: black;"
" text-shadow: 0 0 2mm red}</style></head><body>"
"<h1>FILE NOT FOUND!</h1><br><h1>404 =(</h1>"
"</body></html>\r\n";



int main(int argc, char* argv[]){
	struct sockaddr_in client_address;
	socklen_t client_address_lenght;
	char c; //auxiliar for parsing args

	//Default Values PATH = PWD and PORT=8080
	char PORT[6];
	ROOT = getenv("PWD"); //PWD = print working directory / getenv gets value of 'var' from enviromental variable
	strcpy(PORT,"8080");

	int slot=0;

	//Parsing the command line arguments
	while ((c = getopt (argc, argv, "p:r:")) != -1){
		switch (c){
			case 'r':
				ROOT = malloc(strlen(optarg));
				strcpy(ROOT,optarg);
				break;
			case 'p':
				strcpy(PORT,optarg);
				break;
			case '?':
				fprintf(stderr,"Wrong arguments given!!!\n");
				exit(1);
			default:
				exit(1);
		}
	}
	
	printf("Server started at port no. %s%s%s with root directory as %s%s%s\n","\033[92m",PORT,"\033[0m","\033[92m",ROOT,"\033[0m");
	// Setting all elements to -1: signifies there is no client connected
	int i;
	for (i=0; i<LISTEN_BACKLOG; i++)
		clients[i]=-1;
	startServer(PORT);

	// ACCEPT connections
	while (1){
		client_address_lenght = sizeof(client_address);
		clients[slot] = accept (listenfd, (struct sockaddr *) &client_address, &client_address_lenght);

		if (clients[slot]<0)
			error ("accept() error");
		else{
			if ( fork()==0 ){
				respond(slot);
				exit(0);
			}
		}

		while (clients[slot]!=-1) slot = (slot+1)%LISTEN_BACKLOG; //ta feio isso aqui
	}

	return 0;
}

//start server
void startServer(char *port){
	struct addrinfo hints, *res, *p;

	// getaddrinfo for host
	memset (&hints, 0, sizeof(hints));
	hints.ai_family = AF_INET;
	hints.ai_socktype = SOCK_STREAM;
	hints.ai_flags = AI_PASSIVE;
	if (getaddrinfo( NULL, port, &hints, &res) != 0){
		perror ("getaddrinfo() error");
		exit(1);
	}
	// socket and bind
	for (p = res; p!=NULL; p=p->ai_next){
		listenfd = socket (p->ai_family, p->ai_socktype, 0);
		if (listenfd == -1) continue;
		if (bind(listenfd, p->ai_addr, p->ai_addrlen) == 0) break;
	}
	if (p==NULL){
		perror ("socket() or bind()");
		exit(1);
	}

	freeaddrinfo(res);

	// listen for incoming connections
	if ( listen (listenfd, 1000000) != 0 ){
		perror("listen() error");
		exit(1);
	}
}

//client connection
void respond(int n){
	char mesg[99999], *reqline[3], data_to_send[BYTES], path[99999];
	int rcvd, fd, bytes_read;

	memset( (void*)mesg, (int)'\0', 99999 );

	rcvd=recv(clients[n], mesg, 99999, 0);

	if (rcvd<0)    // receive error
		fprintf(stderr,("recv() error\n"));
	else if (rcvd==0)    // receive socket closed
		fprintf(stderr,"Client disconnected upexpectedly.\n");
	else{    // message received

		printf("%s", mesg);
		reqline[0] = strtok (mesg, " \t\n");
		if ( strncmp(reqline[0], "GET\0", 4)==0 ){
			reqline[1] = strtok (NULL, " \t");
			reqline[2] = strtok (NULL, " \t\n");
			if ( strncmp( reqline[2], "HTTP/1.0", 8)!=0 && strncmp( reqline[2], "HTTP/1.1", 8)!=0 ){
				write(clients[n], http400, sizeof(http400));
			}
			else{
				if ( strncmp(reqline[1], "/\0", 2)==0 )
					reqline[1] = "/index.html";        //Because if no file is specified, index.html will be opened by default (like it happens in APACHE...

				strcpy(path, ROOT);
				strcpy(&path[strlen(ROOT)], reqline[1]);
				printf("file: %s\n", path);

				if ( (fd=open(path, O_RDONLY))!=-1 ){    //FILE FOUND
					send(clients[n], http200, sizeof(http200) - 1, 0);
					send(clients[n], cabecalho, sizeof(cabecalho) - 1, 0);

					while ( (bytes_read=read(fd, data_to_send, BYTES))>0 )
						write (clients[n], data_to_send, bytes_read);
				}
				else{
					send(clients[n], http404, sizeof(http404) - 1, 0);
					send(clients[n], cabecalho, sizeof(cabecalho) - 1, 0);
					send(clients[n], html404, sizeof(html404) - 1, 0);
				}
			}
		}
	}

	//Closing SOCKET
	shutdown (clients[n], SHUT_RDWR);         //All further send and recieve operations are DISABLED...
	close(clients[n]);
	clients[n]=-1;
}