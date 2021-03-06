package com.example.buscamina;


/**
 * Clase que genera el tablero con bombas, numeros y secciones vacias
 * @author Jonathan Mendieta
 * @author Dennise Pintado
 * @author Janina Costa
 */


import java.util.ArrayList;
import java.util.Random;


public class Tablero{
private Casilla[][] tabla;
private ArrayList <Casilla> bombas, vacios;
private String dificultad;

/** 
 * Parametros de la clase tablero
 * @param tabla arreglo bidimensional que genera el tablero en el layout
 * @param bombas se colocan bombas en posiciones aleatorias del arreglo 
 * @param vacios se generan casillas vacias en el tablero
 * @param dificultad string que servira de base para la dimension que se generara del tablero
*/

public Tablero(String dificultad){
	this.bombas = new ArrayList<Casilla>();
	this.vacios = new ArrayList<Casilla>();
	this.dificultad = dificultad;
    this.tabla = creartablero(dificultad);
}
        

private Casilla[][] creartablero(String dificultad){
	Casilla[][] tabla = null;
	if(dificultad.equals("facil")){
		tabla = new Casilla[9][9];	
	}
	else if(dificultad.equals("intermedio")){
		tabla = new Casilla[16][16];
	}
	else if(dificultad.equals("dificil")){
		tabla = new Casilla[16][30];
	}
	else
		System.out.println("Internal Error");
	for (int i = 0; i < tabla.length; i++) {
        for (int j = 0; j < tabla[0].length; j++) {
        	tabla[i][j]= new Casilla(i,j,"start");
        }}
	return tabla;}

public void llenartablerobombas(int i, int j){
	int cont=0,x=0,y=0;
	Random random = new Random();
    Casilla bomba;
	if(this.dificultad.equals("facil")){
		while(cont<10){
            x=random.nextInt(9);
			y=random.nextInt(9);
            	if(((i*10)+j)!=((x*10)+y) && this.tabla[x][y].getId().equals("start")){
            		bomba = new Casilla(x,y,"bomba");
            		bomba.setNumvalue(9);
                    this.tabla[x][y]=bomba;
                    bombas.add(bomba);
                    cont++;
                        }
                }
        }
        else if(this.dificultad.equals("intermedio")){
            while(cont<40){
			x=random.nextInt(16);
			y=random.nextInt(16);
			if(((i*10)+j)!=((x*10)+y) && this.tabla[x][y].getId().equals("start")){
				bomba = new Casilla(x,y,"bomba");
				bomba.setNumvalue(9);
                this.tabla[x][y]=bomba;
                bombas.add(bomba);
				cont++;
                        }}}
        else if(this.dificultad.equals("dificil")){
            while(cont<99){
			x=random.nextInt(16);
			y=random.nextInt(30);
			if(((i*10)+j)!=((x*10)+y) && this.tabla[x][y].getId().equals("start")){
				bomba = new Casilla(x,y,"bomba");
				bomba.setNumvalue(9);
                this.tabla[x][y]=bomba;
                bombas.add(bomba);
				cont++;
                        }}
        }
        else
            System.out.println("Internal error");
		llenartableronumeros();
		llenartablerovacios();
}

private void llenartableronumeros(){
    Casilla numero;
        for(Casilla temp: this.bombas){
            for(int i=temp.getX()-1;i<=temp.getX()+1;i++){
                if(i>=0 && i<this.tabla.length){
                for(int j=temp.getY()-1;j<=temp.getY()+1;j++){
                    if(j>=0 && j<this.tabla[0].length){
                        if(this.tabla[i][j].getId().equals("start")){
                        	numero = new Casilla(i,j,"numero");
                        	numero.setNumvalue(1);
                            this.tabla[i][j]=numero;
                        }
                        else if((this.tabla[i][j].getNumvalue())!=9){
                        	this.tabla[i][j].setNumvalue(this.tabla[i][j].getNumvalue()+1);
                        }
                    }
                }}
            }
        }
}
private void llenartablerovacios(){
	Casilla vacio;
		for(int i=0;i<this.tabla.length;i++){
			for(int j=0;j<this.tabla[0].length;j++){
				if(this.tabla[i][j].getId().equals("start")){
					vacio = new Casilla(i,j,"vacio");
					vacio.setNumvalue(0);
					this.tabla[i][j]=vacio;
					this.vacios.add(vacio);}}}
}

/** Arreglo de casillas con bombas
 * @return casillas donde hay bombas
 */

public ArrayList <Casilla> getBombas(){
    return this.bombas;
}

/** Arreglo de casillas vacias
 * @return casillas vacias
 */
public ArrayList <Casilla> getVacios(){
    return this.vacios;
}

public Casilla[][] getTabla(){
    return this.tabla;
}
}