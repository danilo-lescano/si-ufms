#include <errno.h>
#include <stdio.h>
#include <netdb.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <sys/types.h>
#include <arpa/inet.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <net/ethernet.h>

#define MAX_PACKET_SIZE 65536
#define MIN_PACKET_SIZE 64
/* */
struct ether_hdr {
	unsigned char	ether_dhost[6];	// Destination address
	unsigned char	ether_shost[6];	// Source address
	unsigned short	ether_type;	// Type of the payload
};
/* */
struct ip_hdr {
	unsigned char 	ip_v:4,		// IP Version
					ip_hl:4;	// Header length
	unsigned char	ip_tos;		// Type of service
	unsigned short	ip_len;		// Datagram Length
	unsigned short	ip_id;		// Datagram identifier
	unsigned short	ip_offset;	// Fragment offset
	unsigned char	ip_ttl;		// Time To Live
	unsigned char	ip_proto;	// Protocol
	unsigned short	ip_csum;	// Header checksum
	unsigned int	ip_src;		// Source IP address
	unsigned int	ip_dst;		// Destination IP address
};
/* */
/* */
// Bind a socket to a interface
int bind_iface_name(int fd, char *iface_name){
	return setsockopt(fd, SOL_SOCKET, SO_BINDTODEVICE, iface_name, strlen(iface_name));
}
/* */
// Print an Ethernet address
void print_eth_address(char *s, unsigned char *eth_addr){
	printf("%s %02X:%02X:%02X:%02X:%02X:%02X", s,
	       eth_addr[0], eth_addr[1], eth_addr[2],
	       eth_addr[3], eth_addr[4], eth_addr[5]);
}
/* */
// Break this function to implement the functionalities of your packet analyser
void doProcess(unsigned char* packet, int len){
	if(!len || len < MIN_PACKET_SIZE)
		return;

	struct ether_hdr* eth = (struct ether_hdr*) packet;

	print_eth_address("\nDst =", eth->ether_dhost);
	print_eth_address(" Src =", eth->ether_shost);
	printf(" Ether Type = 0x%04X Size = %d", ntohs(eth->ether_type), len);
	
	if(eth->ether_type == htons(0x0800)) {
		//IP

		//...
	} else if(eth->ether_type == htons(0x0806)) {
		//ARP
		
		//...
	}
	fflush(stdout);
}
/* */
// Print the expected command line for the program
void print_usage(){
	printf("\nxnoop -i <interface> [options] [filter]\n");
	exit(1);
}
/* */
// main function
int main(int argc, char** argv) {
	int		n;
	int		sockfd; //The  sockfd  argument  is  a file descriptor that refers to a socket of
       				//type SOCK_STREAM or SOCK_SEQPACKET.

	socklen_t	s_address_length;
	struct sockaddr	s_address;
	unsigned char	*packet_buffer;

	if (argc < 3)
		print_usage();
	
	if (strcmp(argv[1], "-i"))
		print_usage();	
	
	s_address_length = sizeof(s_address);
	sockfd = socket(AF_PACKET, SOCK_RAW, htons(ETH_P_ALL));  
	if(sockfd < 0) {
		fprintf(stderr, "ERROR1: %s\n", strerror(errno));
		exit(1);
	}
	
	if (bind_iface_name(sockfd, argv[2]) < 0) {
		perror("Server-setsockopt() error for SO_BINDTODEVICE");
		printf("%s\n", strerror(errno));
		close(sockfd);
		exit(1);
	}

	packet_buffer = malloc(MAX_PACKET_SIZE);
	if (!packet_buffer) {
		printf("\nCould not allocate a packet buffer\n");		
		exit(1);
	}
	
	while(1) {
		n = recvfrom(sockfd, packet_buffer, MAX_PACKET_SIZE, 0, &s_address, &s_address_length);
		if(n < 0) {
			fprintf(stderr, "ERROR2: %s\n", strerror(errno));
			exit(1);
		}
		doProcess(packet_buffer, n);
	}

	free(packet_buffer);
	close(sockfd);

	return 0;
}
/* */