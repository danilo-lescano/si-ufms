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

//ESTRUTURAS
// Define a struct for ARP header
typedef struct _arp_hdr arp_hdr;
struct _arp_hdr {
	uint16_t htype;			//hardware type
	uint16_t ptype;			//protocol type
	uint8_t hlen;			//hardware address length
	uint8_t plen;			//protocol address length
	uint16_t opcode;		//operation
	uint8_t sender_mac[6];	//sender hardware address - SHA
	uint8_t sender_ip[4];	//sender protocal addres - SPA
	uint8_t target_mac[6];	//target hardware address -THA
	uint8_t target_ip[4];	//target protocol address - TPA
};
//Estrutura ad interface
struct iface {
	int		sockfd;
	int		ttl;
	int		mtu;
	char		ifname[MAX_IFNAME_LEN];
	unsigned char	mac_addr[6];
	unsigned int	ip_addr;
	unsigned int	rx_pkts;
	unsigned int	rx_bytes;
	unsigned int	tx_pkts;
	unsigned int	tx_bytes;
};
//killall xarpd (para finalizar os deamons)
void daemonize(){
    if (fork() != 0) 
	    exit(1);
}
// This function should be one thread for each interface.
void read_iface(struct iface *ifn){
	socklen_t	saddr_len;
	struct sockaddr	saddr;
	unsigned char	*packet_buffer;
	int		n;
	
	saddr_len = sizeof(saddr);	
	packet_buffer = malloc(MAX_PACKET_SIZE);
	if (!packet_buffer) {
		printf("\nCould not allocate a packet buffer\n");		
		exit(1);
	}
	
	while(1) {
		n = recvfrom(ifn->sockfd, packet_buffer, MAX_PACKET_SIZE, 0, &saddr, &saddr_len);
		if(n < 0) {
			fprintf(stderr, "ERROR: %s\n", strerror(errno));
			exit(1);
		}
		doProcess(packet_buffer, n);
	}
}
// Print the expected command line for the program
void print_usage(){
	printf("\n%sxarpd %s<interface> %s[%s<interface> %s...]\n", KBLU, KGRN, KNRM, KGRN, KNRM);
	exit(1);
}

int main(int argc, char* argv[]){
    int i;

	if(argc == 1) print_usage();

    daemonize();

    if (optind < argc) {
        //validar as interfaces
        while (optind < argc){
            printf ("%s%s\n%s", KYEL, argv[optind++], KNRM);
        }
    }
}