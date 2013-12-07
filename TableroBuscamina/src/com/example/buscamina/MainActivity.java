package com.example.buscamina;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MainActivity extends Activity implements OnTouchListener {
	private boolean inicio=true;
	private DibujarBoard  board;
	private Tablero tabla;
	private Acciones accion;
	private LinearLayout layout;
	
	private TextView textView1;
	private TextView textView2;
	
	
	
	public static String dificultad="facil";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		textView1 = (TextView) findViewById(R.id.textView1);
		textView2 = (TextView) findViewById(R.id.textView2);

		// set font style for timer and mine count to LCD style
		Typeface lcdFont = Typeface.createFromAsset(getAssets(),
		    "fonts/lcd2mono.ttf");
		textView1.setTypeface(lcdFont);
		textView2.setTypeface(lcdFont);
		
		
		
		tabla = new Tablero(dificultad);
		accion = new Acciones(tabla.getTabla(),tabla.getBombas());
		
		layout = (LinearLayout) findViewById(R.id.layout2);
		board = new DibujarBoard (this);
		board.setOnTouchListener(this);
		layout.addView(board);
		
	}
	

	
	class DibujarBoard extends View {

		public DibujarBoard (Context context) {
			super(context);
		}

		protected void onDraw(Canvas canvas) {
			int n=9;
			int ancho = 0;
			if (canvas.getWidth() < canvas.getHeight())
				ancho = board.getWidth();
			else
			ancho = board.getHeight();
			Casilla casilla;
			Typeface tf = Typeface.create("Helvetica",Typeface.BOLD);
			int tamCuad = 0;
		
			if(dificultad=="facil"){
				 //tamCuad = ancho / 9;
				n=9;
			}else if(dificultad=="intermedio"){
				 //tamCuad = ancho / 18;
				n=18;
			}else if(dificultad=="dificil"){
				n=36;
			}else{
				tamCuad = ancho / 9; //por defecto
			}
			
			tamCuad = ancho / n; //por defecto
			Paint pintar = new Paint();
			Paint pintaNums = new Paint();
			Paint pintaBomb = new Paint();
			pintar.setTypeface(tf);
			pintaBomb.setARGB(255, 0, 0, 0);
			int filaact = 0;
			for (int j = 0; j < tabla.getTabla().length; j++) {
				for (int i = 0; i < tabla.getTabla()[0].length; i++) {
					if(inicio){
						pintar.setARGB(153, 204, 204, 204);
						pintar.setStyle(Paint.Style.FILL_AND_STROKE);
						canvas.drawRect(i * tamCuad, filaact, i * tamCuad + tamCuad - 2, filaact + tamCuad - 2, pintar);
					}
					else{
					casilla = tabla.getTabla()[j][i];
					pintar.setStyle(Paint.Style.STROKE);
					casilla.fijarxy(i * tamCuad, filaact, tamCuad);
					if (casilla.isWrapped()){
						pintar.setARGB(153, 204, 204, 204);
						canvas.drawRect(i * tamCuad, filaact, i * tamCuad + tamCuad - 2, filaact + tamCuad - 2, pintar);}
					else{
						if(casilla.getId().equals("vacio")){
							pintar.setARGB(0, 0, 0, 0);
							canvas.drawRect(i * tamCuad, filaact, i * tamCuad + tamCuad - 2, filaact + tamCuad - 2, pintar);}
						else if(casilla.getId().equals("numero")){
							pintar.setARGB(153, 204, 204, 204);
							canvas.drawRect(i * tamCuad, filaact, i * tamCuad + tamCuad - 2, filaact + tamCuad - 2, pintar);
							pintaNums.setColor(Color.BLUE);
							canvas.drawText(casilla.getNumvalue()+"",i * tamCuad + (tamCuad / 2),filaact + (tamCuad / 2), pintaNums);
							}
						else if(casilla.getId().equals("bomba")){
							pintar.setARGB(255, 153, 153, 153);
							canvas.drawRect(i * tamCuad, filaact, i * tamCuad + tamCuad - 2, filaact + tamCuad - 2, pintar);
							canvas.drawCircle(i * tamCuad + (tamCuad / 2), filaact + (tamCuad / 2), 8, pintaBomb);}
					}
					
					//canvas.drawRect(i * tamCuad, filaact, i * tamCuad + tamCuad - 2, filaact + tamCuad - 2, pintar);
					//canvas.drawLine(i* tamCuad, filaact, i * tamCuad 	+ tamCuad, filaact, pintarlinea);
					//canvas.drawLine(i * tamCuad + tamCuad - 1, filaact, i * tamCuad + tamCuad - 1, filaact + tamCuad, pintarlinea);
				    /*if (cuad[j][i].contenido >= 1
							&& cuad[j][i].contenido <= 8
							&& cuad[j][i].isWrapped())
						canvas.drawText(
								String.valueOf(cuad[j][i].contenido), i * tamCuad + (tamCuad / 2) - 8, filaact + tamCuad / 2, pintar2);
					*//*
					if (cuad[j][i].contenido == 80
							&& cuad[j][i].isWrapped()) {
						Paint bomba = new Paint();
						bomba.setARGB(255,255, 0, 0);
						
					}*/
				}
				}
				filaact = filaact + tamCuad;
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		for (int i = 0; i < tabla.getTabla().length; i++) {
            for (int j = 0; j < tabla.getTabla()[0].length; j++) {
            	if(inicio){
            		tabla.llenartablerobombas(i,j);
            		inicio=false;
            		pintarTablero();
            		return true;
            	}else{
            	if (tabla.getTabla()[i][j].dentro((int) event.getX(),(int) event.getY())) {
            		accion.ActionUnwrap(tabla.getTabla()[i][j]);
                	pintarTablero();
                	return true;
                }}
            }
		}
		return false;
	}
	public void pintarTablero(){
		this.layout.removeView(this.board);
    	this.board = new DibujarBoard(this);
    	this.layout.addView(this.board);
    	this.board.setOnTouchListener(this);
	}
}
