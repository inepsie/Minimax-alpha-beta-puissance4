package application;

import java.util.ArrayList;
import java.util.Arrays;
import java.awt.event.ActionEvent;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Translate;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Circle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.ChangeListener;
import java.io.*;
import javafx.util.Duration;


public class Main extends Application {
	
	Game partie = new Game();
	int ia = 0;
	byte difficulte = 1;

	
	
	@Override
	public void start(Stage primaryStage) {
		try{
			
			
			int largeur = 1000;
			int hauteur = 500;
			
			Group root = new Group();
			Scene scene = new Scene(root,largeur,hauteur);
			Group accueil = new Group();
			Group menu = new Group();
			Group jeu = new Group();
			Text name = new Text("Connect 4");
			Text win_red = new Text("Victoire\n rouge !");
			Text win_yellow = new Text("Victoire\n jaune !");
			Text match_nul = new Text("Match\n nul ...");
			RadioButton player1 = new RadioButton("Je veux être Joueur 1");
			RadioButton player2 = new RadioButton("Je veux être Joueur 2");
			RadioButton facile = new RadioButton("Facile");
			RadioButton moyen = new RadioButton("Moyen");
			RadioButton difficile = new RadioButton("Difficile");
			ToggleGroup tg1 = new ToggleGroup();
			ToggleGroup tg2 = new ToggleGroup();
			Button solo = new Button("1 Joueur");
			Button duo = new Button("2 Joueurs");
			Button start = new Button("Start");
			Button quit = new Button("Quit");
			Image img = new Image("puiss4.png");
			ImageView iv = new ImageView(img);

			
		
			name.setTextOrigin(VPos.TOP);
			name.setFont(Font.loadFont("file:Paragon Cleaners Black.ttf",150));
			name.setLayoutY(100);
			name.layoutXProperty().bind(scene.widthProperty().subtract(name.prefWidth(-1)).divide(2));
			
			win_red.setLayoutX(25);
			win_red.setLayoutY(85);
			win_red.setFont(Font.loadFont("file:Paragon Cleaners Black.ttf",75));
			win_red.setFill(Color.DARKRED);
			win_red.setVisible(false);
			
			win_yellow.setLayoutX(25);
			win_yellow.setLayoutY(85);
			win_yellow.setFont(Font.loadFont("file:Paragon Cleaners Black.ttf",75));
			win_yellow.setFill(Color.YELLOW);
			win_yellow.setVisible(false);
			
			match_nul.setLayoutX(25);
			match_nul.setLayoutY(85);
			match_nul.setFont(Font.loadFont("file:Paragon Cleaners Black.ttf",75));
			match_nul.setFill(Color.GREY);
			match_nul.setVisible(false);
			
			player1.setLayoutX(300);
			player1.setLayoutY(290);
			player1.setVisible(false);

			player2.setLayoutX(510);
			player2.setLayoutY(290);
			player2.setVisible(false);
			
			player1.setToggleGroup(tg1);
			player2 .setToggleGroup(tg1);
			player1.setSelected(false);
			player2.requestFocus();
			
			facile.setLayoutX(280);
			facile.setLayoutY(340);
			facile.setVisible(false);
			
			moyen.setLayoutX(440);
			moyen.setLayoutY(340);
			moyen.setVisible(false);
			
			difficile.setLayoutX(610);
			difficile.setLayoutY(340);
			difficile.setVisible(false);
			
			
			facile.setToggleGroup(tg2);
			moyen.setToggleGroup(tg2);
			difficile.setToggleGroup(tg2);
			
			solo.setLayoutX(340);
			solo.setLayoutY(330);
			
			duo.setLayoutX(530);
			duo.setLayoutY(330);

			
			start.setLayoutX(450);
			start.setLayoutY(400);
			start.setVisible(false);
			

			root.getChildren().add(accueil);
			accueil.getChildren().addAll(name,player1,player2,start,solo,duo,facile,moyen,difficile);
			
			primaryStage.setTitle("Connect 4");
			primaryStage.setResizable(false);
			primaryStage.setScene(scene);
			primaryStage.show();
			
			solo.setOnAction((event)->{
					duo.setVisible(false);
					solo.setVisible(false);
					player1.setVisible(true);
					player2.setVisible(true);
					start.setVisible(true);
					player1.setSelected(true);
					facile.setVisible(true);
					moyen.setVisible(true);
					difficile.setVisible(true);
					facile.setSelected(true);
			});
			
			duo.setOnAction((event)->{
				duo.setVisible(false);
				solo.setVisible(false);
				start.setVisible(true);
			});
			

			
			
			start.setOnAction((event)->{
				

				if(player2.isSelected()) {
					partie.joueur = 2;
					ia = 1;	
				}
				else if(player1.isSelected()) {
					ia = 2;
				}
				
				if(facile.isSelected()) {
					difficulte = 2;
				}
				else if(moyen.isSelected()) {
					difficulte = 4;
				}
				else if(difficile.isSelected()) {
					difficulte = 7;
				}
				
				
				
				
				root.getChildren().clear();
				root.getChildren().addAll(menu,jeu);
				
				
				
				Circle[] play_circles = new Circle[42];
				for(int i=0 ; i<42 ; i++) {
					play_circles[i] = new Circle(100,45,28);
					play_circles[i].setVisible(false);
					if(i%2==0) {
						play_circles[i].setFill(Color.DARKRED);
		
					}
					else {
						play_circles[i].setFill(Color.YELLOW);
					}
					jeu.getChildren().addAll(play_circles[i]);
				}
				
				
				Circle[] tablcirc1 = new Circle[7];
				for (int i=0 ; i<7 ; i++) {
					tablcirc1[i] = new Circle(405+i*70,45,28);
					tablcirc1[i].setFill(Color.DARKRED);
					tablcirc1[i].setVisible(false);
					jeu.getChildren().addAll(tablcirc1[i]);
				}
				
				Circle[] tablcirc2 = new Circle[7];
				for (int i=0 ; i<7 ; i++) {
					tablcirc2[i] = new Circle(405+i*70,45,28);
					tablcirc2[i].setFill(Color.YELLOW);
					tablcirc2[i].setVisible(false);
					jeu.getChildren().addAll(tablcirc2[i]);
				}
				
				
				quit.setLayoutX(100);
				quit.setLayoutY(350);
				
				
				iv.setLayoutX(320);
				iv.setLayoutY(50);
				jeu.getChildren().addAll(iv,quit,win_red,win_yellow,match_nul);
				
				Rectangle[] tablrect = new Rectangle[7];
				for (int i=0 ; i<7 ; i++) {
					tablrect[i] = new Rectangle(370+i*70,5,70,480);
					tablrect[i].setFill(Color.TRANSPARENT);
					jeu.getChildren().addAll(tablrect[i]);
				}
				
				
				if (partie.joueur==2 && ia>0) {
					animationCoup(3, play_circles, tablcirc1, tablcirc2, partie);
					 partie.coup(3);
				}
				
				primaryStage.show();
				
	
				tablrect[0].setOnMouseEntered(e-> {
					if(partie.haut[0]>=0) {
						if(partie.joueur==1 && (ia==2 || ia==0)) {
							tablcirc1[0].setVisible(true);
						}
						else {
							tablcirc2[0].setVisible(true);
						}
					}
				});				
				tablrect[0].setOnMouseExited(e-> {
					if(partie.joueur==1 && (ia==2 || ia==0)) {
						tablcirc1[0].setVisible(false);
					}
					else {
						tablcirc2[0].setVisible(false);
					}
				});
				
				tablrect[1].setOnMouseEntered(e-> {
					if(partie.haut[1]>=0) {
						if(partie.joueur==1 && (ia==2 || ia==0)) {
							tablcirc1[1].setVisible(true);
						}
						else {
							tablcirc2[1].setVisible(true);
						}
					}
				});				
				tablrect[1].setOnMouseExited(e-> {
					if(partie.joueur==1 && (ia==2 || ia==0)) {
						tablcirc1[1].setVisible(false);
					}
					else {
						tablcirc2[1].setVisible(false);
					}
				});
				
				tablrect[2].setOnMouseEntered(e-> {
					if(partie.haut[2]>=0) {
						if(partie.joueur==1 && (ia==2 || ia==0)) {
							tablcirc1[2].setVisible(true);
						}
						else {
							tablcirc2[2].setVisible(true);
						}
					}
				});				
				tablrect[2].setOnMouseExited(e-> {
					if(partie.joueur==1 && (ia==2 || ia==0)) {
						tablcirc1[2].setVisible(false);
					}
					else {
						tablcirc2[2].setVisible(false);
					}	
				});
				
				tablrect[3].setOnMouseEntered(e-> {
					if(partie.haut[3]>=0) {
						if(partie.joueur==1 && (ia==2 || ia==0)) {
							tablcirc1[3].setVisible(true);
						}
						else {
							tablcirc2[3].setVisible(true);
						}
					}
				});				
				tablrect[3].setOnMouseExited(e-> {
					if(partie.joueur==1 && (ia==2 || ia==0)) {
						tablcirc1[3].setVisible(false);
					}
					else {
						tablcirc2[3].setVisible(false);
					}
					
				});
				
				tablrect[4].setOnMouseEntered(e-> {
					if(partie.haut[4]>=0) {
						if(partie.joueur==1 && (ia==2 || ia==0)) {
							tablcirc1[4].setVisible(true);
						}
						else {
							tablcirc2[4].setVisible(true);
						}
					}
				});				
				tablrect[4].setOnMouseExited(e-> {
					if(partie.joueur==1 && (ia==2 || ia==0)) {
						tablcirc1[4].setVisible(false);
					}
					else {
						tablcirc2[4].setVisible(false);
					}
				});
				
				tablrect[5].setOnMouseEntered(e-> {
					if(partie.haut[5]>=0) {
						if(partie.joueur==1 && (ia==2 || ia==0)) {
							tablcirc1[5].setVisible(true);
						}
						else {
							tablcirc2[5].setVisible(true);
						}
					}
				});				
				tablrect[5].setOnMouseExited(e-> {
					if(partie.joueur==1 && (ia==2 || ia==0)) {
						tablcirc1[5].setVisible(false);
					}
					else {
						tablcirc2[5].setVisible(false);
					}
				});
				
				tablrect[6].setOnMouseEntered(e-> {
					if(partie.joueur==1 && (ia==2 || ia==0)) {
						tablcirc1[6].setVisible(true);
					}
					else {
						tablcirc2[6].setVisible(true);
					}
				});				
				tablrect[6].setOnMouseExited(e-> {
					if(partie.haut[6]>=0) {
						if(partie.joueur==1 && (ia==2 || ia==0)) {
							tablcirc1[6].setVisible(false);
						}
						else {
							tablcirc2[6].setVisible(false);
						}
					}
				});
				
				//CLICK-PLAY
				
				
				tablrect[0].setOnMouseClicked(e->{
						
					if(partie.haut[0]>=0 && partie.end==0) {					
						

						animationCoup(0, play_circles, tablcirc1, tablcirc2, partie);
						partie.coup(0);
						
						if(testWin(0, partie, win_yellow, win_red, match_nul)==0 && ia>0) {
							startMinimax(partie, 0, play_circles, tablcirc1, tablcirc2, win_yellow, win_red, match_nul);
						}
					}
				});
				
				
				tablrect[1].setOnMouseClicked(e->{
					
					//Affichage de l etat de la partie
					/*
					for(int j=0 ; j<6 ; j++) {
						for(int i=0 ; i<7 ; i++) {
							System.out.print(partie.tableau[j][i]);
						}
						System.out.println("");
					}
					*/
					
					if(partie.haut[1]>=0 && partie.end==0) {					
						
						animationCoup(1, play_circles, tablcirc1, tablcirc2, partie);
						partie.coup(1);
						

						if(testWin(1, partie, win_yellow, win_red, match_nul)==0 && ia>0) {
							startMinimax(partie, 1, play_circles, tablcirc1, tablcirc2, win_yellow, win_red, match_nul);
						}
					}
				});
				
				
				tablrect[2].setOnMouseClicked(e->{
					
					//Affichage de l etat de la partie
					/*
					for(int j=0 ; j<6 ; j++) {
						for(int i=0 ; i<7 ; i++) {
							System.out.print(partie.tableau[j][i]);
						}
						System.out.println("");
					}
					*/
					
					if(partie.haut[2]>=0 && partie.end==0) {					
						
						animationCoup(2, play_circles, tablcirc1, tablcirc2, partie);
						partie.coup(2);
						
						if(testWin(2, partie, win_yellow, win_red, match_nul)==0 && ia>0) {
							startMinimax(partie, 2, play_circles, tablcirc1, tablcirc2, win_yellow, win_red, match_nul);
						}
					}
				});
				
				tablrect[3].setOnMouseClicked(e->{
					
					//Affichage de l etat de la partie
					/*
					for(int j=0 ; j<6 ; j++) {
						for(int i=0 ; i<7 ; i++) {
							System.out.print(partie.tableau[j][i]);
						}
						System.out.println("");
					}
					*/
					
					if(partie.haut[3]>=0 && partie.end==0) {					
						
						animationCoup(3, play_circles, tablcirc1, tablcirc2, partie);
						partie.coup(3);
						
						if(testWin(3, partie, win_yellow, win_red, match_nul)==0 && ia>0) {
							startMinimax(partie, 3, play_circles, tablcirc1, tablcirc2, win_yellow, win_red, match_nul);
						}
					}
				});
				
				tablrect[4].setOnMouseClicked(e->{
					
					//Affichage de l etat de la partie
					/*
					for(int j=0 ; j<6 ; j++) {
						for(int i=0 ; i<7 ; i++) {
							System.out.print(partie.tableau[j][i]);
						}
						System.out.println("");
					}
					*/
					
					if(partie.haut[4]>=0 && partie.end==0) {					
						
						animationCoup(4, play_circles, tablcirc1, tablcirc2, partie);
						partie.coup(4);
						
						if(testWin(4, partie, win_yellow, win_red, match_nul)==0 && ia>0) {
							startMinimax(partie, 4, play_circles, tablcirc1, tablcirc2, win_yellow, win_red, match_nul);
						}
					}
				});
				
				tablrect[5].setOnMouseClicked(e->{
					
					//Affichage de l etat de la partie
					/*
					for(int j=0 ; j<6 ; j++) {
						for(int i=0 ; i<7 ; i++) {
							System.out.print(partie.tableau[j][i]);
						}
						System.out.println("");
					}
					*/
					
					if(partie.haut[5]>=0 && partie.end==0) {					
						
						animationCoup(5, play_circles, tablcirc1, tablcirc2, partie);
						partie.coup(5);
						
						if(testWin(5, partie, win_yellow, win_red, match_nul)==0 && ia>0) {
							startMinimax(partie, 5, play_circles, tablcirc1, tablcirc2, win_yellow, win_red, match_nul);
						}
					}
				});
				
				tablrect[6].setOnMouseClicked(e->{
					
					//Affichage de l etat de la partie
					/*
					for(int j=0 ; j<6 ; j++) {
						for(int i=0 ; i<7 ; i++) {
							System.out.print(partie.tableau[j][i]);
						}
						System.out.println("");
					}
					*/
					
					if(partie.haut[6]>=0 && partie.end==0) {					
						
						animationCoup(6, play_circles, tablcirc1, tablcirc2, partie);
						partie.coup(6);
								
						if(testWin(6, partie, win_yellow, win_red, match_nul)==0 && ia>0) {
							startMinimax(partie, 5, play_circles, tablcirc1, tablcirc2, win_yellow, win_red, match_nul);
						}
					}
				});
				
				
				
			});
			
			quit.setOnAction((event)->{
				System.exit(0);
			});

		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void animationCoup(int coup, Circle[] play_circles, Circle[] tablcirc1, Circle[] tablcirc2, Game partie){
		
		play_circles[partie.nbcoup].setCenterX(405+coup*70);
		TranslateTransition tr = new TranslateTransition();
		tr.setToY(40+70*partie.haut[coup]);
		tr.setNode(play_circles[partie.nbcoup]);
		play_circles[partie.nbcoup].setVisible(true);
		if(partie.joueur==1) {
			tablcirc1[coup].setVisible(false);
		}
		else {
			tablcirc2[coup].setVisible(false);
		}
		tr.play();
		
	
	}
	
	public int testWin(int coup, Game partie, Text win_yellow, Text win_red, Text match_nul){
		
		
		if(partie.win(partie.haut[coup]+1,coup)==1) {
			partie.end = 1;
			if(partie.joueur==2) {
				if(ia==1) {
					win_yellow.setVisible(true);
				}
				else {
					win_red.setVisible(true);
				}
			}
			else {
				if(ia==2) {
					win_yellow.setVisible(true);
				}
				else {
					win_red.setVisible(true);
				}
			}
			return 1;
		}
		
		if(partie.nbcoup>41) {
			partie.end = 1;
			match_nul.setVisible(true);
			return 1;
		}
		
		
		return 0;
	}
	
	public void startMinimax(Game partie, int coup, Circle[] play_circles, Circle[] tablcirc1, Circle[] tablcirc2, Text win_yellow, Text win_red, Text match_nul){
		

		Minimax arbre = new Minimax(difficulte,Integer.MIN_VALUE/2,(byte)coup,partie.cloneTab(partie.tableau),partie.haut.clone(),(byte)0,(byte)(partie.joueur%2+1),partie.joueur);

		for(int i=0 ; i<arbre.children.size() ; i++) {
			
			if(arbre.children.get(i).score == arbre.score) {

				animationCoup(arbre.children.get(i).coup, play_circles, tablcirc1, tablcirc2, partie);
				partie.coup(arbre.children.get(i).coup);
				testWin(arbre.children.get(i).coup, partie, win_yellow, win_red, match_nul);
				
				break;
			}
		}
		
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
