package application;

public class Game {

	public byte[][] tableau;
	public byte[] haut;
	public byte nbcoup;
	public byte joueur;
	public byte end;


	public Game(){
		
		tableau = new byte[6][7];
		haut = new byte[7];
		joueur = 1;
		nbcoup = 0;
		end = 0;

		for(int i=0; i<6; i++){
		    for(int j=0; j<7; j++){
			tableau[i][j]=0;
		    }
		}
		
		for(int i=0; i<7; i++){
			haut[i]=5;
		}
	}
	
	public byte[][] cloneTab(byte[][] tableau){
		
		byte[][] copie = new byte[6][7];
		for(int i=0 ; i<6 ; i++) {
			copie[i] = tableau[i].clone();
		}
		return copie;
	}
	
	public void coup(int col){
		
		tableau[haut[col]][col] = joueur;
		haut[col]--;
		nbcoup++;
		joueur = (byte) (joueur%2 + 1);
	}
	
	public int win(int y, int x){
		
		//Recherche horizontale:
		int val = -1;
		int copy_x = x;
		int copy_y = y;
		while((val<4) && (copy_x>=0)){
			if(tableau[copy_y][copy_x] != joueur%2 + 1) {
				break;
			}
			val++;
			copy_x--;
		}
		if(val>=4) {
			return 1;
		}
		copy_x=x;
		while((val<4) && (copy_x<=6)){
			if(tableau[copy_y][copy_x] != joueur%2 + 1) {
				break;
			}
			val++;
			copy_x++;
		}
		if(val>=4) {
			return 1;
		}
	
		//Recherche verticale:
		val=-1;
		copy_x = x;
		copy_y = y;
		while((val<4) && (copy_y>=0)){
			if(tableau[copy_y][copy_x] != joueur%2 + 1) {
				break;
			}
			val++;
			copy_y--;
		}
		if(val>=4) {
			System.out.println("HELLO1");
			return 1;
		}
		copy_y=y;
		while((val<4) && (copy_y<=5)){
			if(tableau[copy_y][copy_x] != joueur%2 + 1) {
				break;
			}
			val++;
			copy_y++;
		}
		if(val>=4){
			return 1;
		}
		
		//Recherche diagonale haut-gauche:
		val=-1;
		copy_x = x;
		copy_y = y;
		while((val<4) && (copy_x>=0) && (copy_y>=0)){
			if(tableau[copy_y][copy_x] != joueur%2 + 1) {
				break;
			}
			val++;
			copy_x--;
			copy_y--;
		}
		if(val>=4) {
			return 1;
		}
		copy_x = x;
		copy_y = y;
		while((val<4) && (copy_x<=6) && (copy_y<=5)){
			if(tableau[copy_y][copy_x] != joueur%2 + 1) {
				break;
			}
			val++;
			copy_x++;
			copy_y++;
		}
		if(val>=4){
			return 1;
		}
				
		//Recherche diagonale haut-droit:
		val=-1;
		copy_x = x;
		copy_y = y;
		while((val<4) && (copy_x>=0) && (copy_y<=5)){
			if(tableau[copy_y][copy_x] != joueur%2 + 1) {
				break;
			}
			val++;
			copy_x--;
			copy_y++;
		}
		if(val>=4) {
			return 1;
		}
		copy_x = x;
		copy_y = y;
		while((val<4) && (copy_x<=6) && (copy_y>=0)){
			if(tableau[copy_y][copy_x] != joueur%2 + 1) {
				break;
			}
			val++;
			copy_x++;
			copy_y--;
		}
		if(val>=4){
			return 1;
		}
		
		//Pas de win :
		return 0;

	}
}
