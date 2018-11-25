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

struct mensagem{
	unsigned char	ip[4];
	unsigned char	mac[6];
    int             ttl;
    char            *comando;
};

void print_usage(){
    fprintf(stderr, "Usage: xarp <address> <port>\n");
    exit(1);
}

int main(int argc, char** argv) {
	int sockfd;
	int portno;
	char* retvalue;
	char buffer[256];
    char *val_aux;
    int val_aux_int;

    int i, flag;
    char *flag_type[5] = {"show", "res", "add", "del", "ttl"};

    char ip[] = "127.0.0.1";
    char porta[] = "5050";

	struct sockaddr_in serv_addr;

    struct mensagem msg;

	if(argc == 1) print_usage();
    
    for(flag = 0; flag < 5 && strcmp(argv[1], flag_type[flag]) != 0; flag++);
    if(flag == 5) print_usage();

	portno = atoi(porta);
	//man socket
	sockfd = socket(AF_INET, SOCK_STREAM, 0);
  
	if(sockfd < 0) {
		fprintf(stderr, "ERROR1: %s\n", strerror(errno));
		exit(1);
	}
  
	memset((char*) &serv_addr, 0, sizeof(serv_addr));
  
	serv_addr.sin_family = AF_INET;
	serv_addr.sin_addr.s_addr = inet_addr(ip);
	serv_addr.sin_port = htons(portno);
  
	//man connect
	if(connect(sockfd, (struct sockaddr*) &serv_addr, sizeof(serv_addr)) < 0) {
		fprintf(stderr, "ERROR2: %s\n", strerror(errno));
		exit(1);
	}

	memset(buffer, 0, sizeof(buffer));
    msg.comando = (char *) malloc(sizeof(char) * strlen(flag_type[flag]));
    strncpy(msg.comando, flag_type[flag], (int) sizeof(char) * strlen(flag_type[flag]));
    printf("flag %d \n", flag);

    if(flag == 0){
        printf("%s \n", msg.comando);
    }
    else if(flag == 1){
        if(argc != 3) print_usage();
        i = 0;
        while ((val_aux = strsep(&argv[2], "."))){
            msg.ip[i++] = (char) atoi(val_aux);
        }

        printf("%s \n", msg.comando);
        printf("%d.%d.%d.%d \n", msg.ip[0], msg.ip[1], msg.ip[2], msg.ip[3]);
    }
    else if(flag == 2){
        if(argc != 4) print_usage();
        i = 0;
        while ((val_aux = strsep(&argv[2], "."))){
            msg.ip[i++] = (char) atoi(val_aux);
        }

        i = 0;
        while ((val_aux = strsep(&argv[3], ":"))){
		    strncpy(&msg.mac[i++], val_aux, (int) sizeof(unsigned char));
            msg.mac[i - 1] = (char)strtol(val_aux, NULL, 16);
        }
        printf("%s \n", msg.comando);
        printf("%d.%d.%d.%d \n", msg.ip[0], msg.ip[1], msg.ip[2], msg.ip[3]);
        printf("%02X:%02X:%02X:%02X:%02X:%02X\n",
	       msg.mac[0], msg.mac[1], msg.mac[2],
	       msg.mac[3], msg.mac[4], msg.mac[5]);
    }
    else if(flag == 3){
        if(argc != 3) print_usage();
        i = 0;
        while ((val_aux = strsep(&argv[2], "."))){
            msg.ip[i++] = (char) atoi(val_aux);
        }

        printf("%s \n", msg.comando);
        printf("%d.%d.%d.%d \n", msg.ip[0], msg.ip[1], msg.ip[2], msg.ip[3]);
    }
    else if(flag == 4){
        printf("%s \n", msg.comando);
    }
    memcpy(buffer, &msg, sizeof(msg));

	//man send
	if(send(sockfd, buffer, strlen(buffer), 0) < 0) {
		fprintf(stderr, "ERROR3: %s\n", strerror(errno));
		exit(1);
	}
  
	memset(buffer, 0, sizeof(buffer));
	//man recv
	if(recv(sockfd, buffer, sizeof(buffer), 0) < 0) {
		fprintf(stderr, "ERROR4: %s\n", strerror(errno));
		exit(1);
	}

	printf("%s\n", buffer);

	close(sockfd);
	
	return 0;
}
