package application;
import java.util.ArrayList;
import java.util.Arrays;

public class Minimax {
	
	ArrayList<Minimax> children = new ArrayList<Minimax>();
	
	public byte profondeur;
	public int estim;
	public byte coup;
	public byte[][] tableau;
	public byte[] haut;
	public byte nbcoup;
	public byte joueur;
	public byte end;
	public int score;
	public byte joueurmax;
	
	
	public Minimax(byte profondeur, int estim, byte coup, byte[][] tableau, byte[] haut, byte nbcoup, byte joueur, byte joueurmax){

		this.profondeur =  profondeur;
		this.estim = estim;
		this.coup = coup;
		this.tableau = tableau;
		this.haut = haut;
		this.nbcoup = nbcoup;
		this.joueur = joueur;
		this.joueurmax = joueurmax;
		
		if(joueur==1) {
			score = Integer.MIN_VALUE/2;
			estim = Integer.MAX_VALUE/2;
		}
		else {
			score = Integer.MAX_VALUE/2;
			estim = Integer.MIN_VALUE/2;
		}
		
		if(nbcoup>0) {
			this.tableau[haut[coup]][coup] = joueur;
			this.haut[coup]--;
			this.feuille();
		}
		
		if(this.end==0 && nbcoup<profondeur){
			byte a = 0;
			for(int i=0 ; i<7 ; i++){
				if(haut[i]>=0) {
					children.add(new Minimax(profondeur,score,(byte)i,this.cloneTab(tableau),haut.clone(),(byte)(nbcoup+1),(byte)(joueur%2+1),joueurmax));
					if(joueur==1 && children.get(a).score>score) {
						score = children.get(a).score;
					}
					if(joueur==1 && score>estim) {
						break;
					}
					if(joueur==1 && children.get(a).score == Integer.MAX_VALUE/2) {
						break;
					}
					
					if(joueur==2 && children.get(a).score<score) {
						score = children.get(a).score;
					}
					if(joueur==2 && score<estim) {
						break;
					}
					if(joueur==2 && children.get(a).score == Integer.MIN_VALUE/2) {
						break;
					}
					a++;
				}
			}
		}
	}
	
	public int max(){
		
		int val = Integer.MIN_VALUE/2;
		
		for(int i=0 ; i<children.size() ; i++) {
			if(children.get(i).score>val) {
				val = children.get(i).score;
			}
		}
		return val;
	}
	
	public int min(){
		
		int val = Integer.MAX_VALUE/2;
		
		for(int i=0 ; i<children.size() ; i++) {
			if(children.get(i).score<val) {
				val = children.get(i).score;
			}
		}
		return val;
	}
	
	public byte[][] cloneTab(byte[][] tableau){
		
		byte[][] copie = new byte[6][7];
		for(int i=0 ; i<6 ; i++) {
			copie[i] = tableau[i].clone();
		}
		return copie;
	}
	
	public void feuille() {
		
		if(win(haut[coup]+1,coup)==1) {
			end = 1;
			
			if(joueur==2) {
				score = Integer.MAX_VALUE/2;
			}
			else {
				score = Integer.MIN_VALUE/2;
			}
		}
		
		else if(nbcoup==profondeur) {
			end = 1;
			score = heuristique();
		}	
	}
	

	public int win(int y, int x){
		
		//Recherche horizontale:
		int val = -1;
		int copy_x = x;
		int copy_y = y;
		while((val<4) && (copy_x>=0)){
			if(tableau[copy_y][copy_x] != joueur) {
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
			if(tableau[copy_y][copy_x] != joueur) {
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
			if(tableau[copy_y][copy_x] != joueur) {
				break;
			}
			val++;
			copy_y--;
		}
		if(val>=4) {
			return 1;
		}
		copy_y=y;
		while((val<4) && (copy_y<=5)){
			if(tableau[copy_y][copy_x] != joueur) {
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
			if(tableau[copy_y][copy_x] != joueur) {
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
			if(tableau[copy_y][copy_x] != joueur) {
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
			if(tableau[copy_y][copy_x] != joueur) {
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
			if(tableau[copy_y][copy_x] != joueur) {
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
	
	public int heuristique(){
		
		int val = 0;
		int x;
		int y;
		
		
		for(int j=0 ; j<6 ; j++) {
			for(int i=0 ; i<7 ; i++) {
				if(tableau[j][i]==joueur) {
					val-=8;
					x=i;
					y=j;
					
					while(x>=0) {
						if(tableau[y][x]!=joueur) {
							break;
						}
						val++;
						x--;
					}
					
					x=i;
					y=j;
					while(x<7) {
						if(tableau[y][x]!=joueur) {
							break;
						}
						val++;
						x++;
					}
					
					x=i;
					y=j;
					while(y>=0) {
						if(tableau[y][x]!=joueur) {
							break;
						}
						val++;
						y--;
					}
					
					x=i;
					y=j;
					while(y<6) {
						if(tableau[y][x]!=joueur) {
							break;
						}
						val++;
						y++;
					}
					
					////////
					x=i;
					y=j;
					while(x>=0 && y>=0) {
						if(tableau[y][x]!=joueur) {
							break;
						}
						val++;
						x--;
						y--;
					}
					
					x=i;
					y=j;
					while(x<7 && y<6) {
						if(tableau[y][x]!=joueur) {
							break;
						}
						val++;
						x++;
						y++;
					}
					
					////////
					x=i;
					y=j;
					while(x>=0 && y<6) {
						if(tableau[y][x]!=joueur) {
							break;
						}
						val++;
						x--;
						y++;
					}
									
					x=i;
					y=j;
					while(x<7 && y>=0) {
						if(tableau[y][x]!=joueur) {
							break;
						}
						val++;
						x++;
						y--;
					}

				
				}
				
				if(tableau[j][i]==joueur%2+1) {
					val+=8;
					x=i;
					y=j;
					
					while(x>=0) {
						if(tableau[y][x]!=joueur) {
							break;
						}
						val--;
						x--;
					}
				
					x=i;
					y=j;
					while(x<7) {
						if(tableau[y][x]!=joueur) {
							break;
						}
						val--;
						x++;
					}
					
					x=i;
					y=j;
					while(y>=0) {
						if(tableau[y][x]!=joueur) {
							break;
					}
						val--;
						y--;
					}
					
					x=i;
					y=j;
					while(y<6) {
						if(tableau[y][x]!=joueur) {
							break;
						}
						val--;
						y++;
					}
					
					////////
					x=i;
					y=j;
					while(x>=0 && y>=0) {
						if(tableau[y][x]!=joueur) {
							break;
						}
						val--;
						x--;
						y--;
					}
					
					x=i;
					y=j;
					while(x<7 && y<6) {
						if(tableau[y][x]!=joueur) {
							break;
						}
						val--;
						x++;
						y++;
					}
					
					////////
					x=i;
					y=j;
					while(x>=0 && y<6) {
						if(tableau[y][x]!=joueur) {
							break;
						}
						val--;
						x--;
						y++;
					}
									
					x=i;
					y=j;
					while(x<7 && y>=0) {
						if(tableau[y][x]!=joueur) {
							break;
						}
						val--;
						x++;
						y--;
					}			
				}
			}
		}
		return val;
	}
}
