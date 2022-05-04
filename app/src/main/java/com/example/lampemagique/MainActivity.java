package com.example.lampemagique;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import static java.lang.Integer.toHexString;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener {

    private Button grosbouton;      // variable qui definit le bouton principale (gros bouton)
    private ArrayList<Button> btns = new ArrayList<Button>();   // collection de boutons
    // tableau d'entiers de nom colors qui regroupe toutes les couleurs, qui afficheront lorsqu'on cliquera sur le "grosboutons"
    private int colors [] = {R.color.pink, R.color.purple, R.color.bleu, R.color.teal_200, R.color.green, R.color.yellow, R.color.orange, R.color.red};
    private int rouge, vert, bleu;      // declaration des variables rouge, vert et bleu qu'on utilisera apres (en bas)

    // methodes a faire d'apres les transparents "vie des activités", afin d'afficher un message dans la console précisant les noms de la méthode et de l'activite
    @Override
    protected void onStart() {
        super.onStart();
        Log.i("onStart()", this.getLocalClassName());
    }

    // methodes a faire d'apres les transparents "vie des activités", afin d'afficher un message dans la console précisant les noms de la méthode et de l'activite
    @Override
    protected void onResume() {
        super.onResume();
        Log.i("onResume()", this.getLocalClassName());
    }

    // methodes a faire d'apres les transparents "vie des activités", afin d'afficher un message dans la console précisant les noms de la méthode et de l'activite
    @Override
    protected void onPause() {
        super.onPause();
        Log.i("onPause()", this.getLocalClassName());
    }

    // methode pour la persistance courte
    @Override
    // sauvegarde les informations de ces cases : rouge, vert et bleu declare en haut
    protected void onSaveInstanceState (Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("rouge", rouge);
        outState.putInt("vert", vert);
        outState.putInt("bleu", bleu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("onCreate()", this.getLocalClassName());
        setContentView(R.layout.activity_main);

        // Recupere les objets de l'interface -> les boutons crer dans les deux pages activity_main.xml
        grosbouton = findViewById(R.id.grosbouton);
        btns.add(findViewById(R.id.rougeplus));
        btns.add(findViewById(R.id.rougemoins));
        btns.add(findViewById(R.id.vertplus));
        btns.add(findViewById(R.id.vertmoins));
        btns.add(findViewById(R.id.bleuplus));
        btns.add(findViewById(R.id.bleumoins));

        // on parcourt une boucle pour associer un écouteur aux 6 boutons permettant de contrôler la zone de couleur
        // on peut cliquer sur ces 6 boutons
        for(int i=0; i<btns.size(); i++) {
            btns.get(i).setOnClickListener(this);
        }

        // pareil on met un ecouteur au grosbouton
        grosbouton.setOnClickListener(this);

        // recuperer les couleurs de lampeMagique
        Intent obj = getIntent();
        rouge = obj.getIntExtra("rouge", 255);
        vert = obj.getIntExtra("vert", 0);
        bleu = obj.getIntExtra("bleu", 0);

        // recuperer les trois couleurs
        if (savedInstanceState != null) {
            rouge = savedInstanceState.getInt("rouge");
            vert = savedInstanceState.getInt("vert");
            bleu = savedInstanceState.getInt("bleu");
        }

        // appel a la methode luminence
        colorStringLuminance();
    }

    // methode luminence
    public void colorStringLuminance() {
        // on transforme les quantites de rouge, vert, bleu
        grosbouton.setBackgroundColor(Color.rgb(rouge, vert, bleu));
        // etape luminance, changement de la couleur des chiffres
        // (noir ou blanc) en fonction de la couleur du fond
        if(Color.luminance(Color.rgb(rouge, vert, bleu)) > 0.5) {
            grosbouton.setTextColor(Color.BLACK);
        } else {
            grosbouton.setTextColor(Color.WHITE);
        }
        grosbouton.setText("ROUGE : " + rouge + " ; VERT : " + vert + " ; BLEU : " + bleu);
    }

    // Associez un écouteur aux 6 boutons permettant de contrôler la zone de couleur
    public void onClick(View v) {
        if (v.getId() == R.id.rougeplus) {
            rouge += 16;
            if(rouge > 255) {
                rouge = 255;
            }
        } else if(v.getId() == R.id.rougemoins) {
            rouge -= 16;
            if(rouge < 0) {
                rouge = 0;
            }
        } else if(v.getId() == R.id.vertplus) {
            vert += 16;
            if(vert > 255) {
                vert = 255;
            }
        } else if(v.getId() == R.id.vertmoins) {
            vert -= 16;
            if (vert < 0) {
                vert = 0;
            }
        } else if(v.getId() == R.id.bleuplus) {
            bleu += 16;
            if(bleu > 255) {
                bleu = 255;
            }
        } else if(v.getId() == R.id.bleumoins) {
            bleu -= 16;
            if (bleu < 0) {
                bleu = 0;
            }
        } else if(v.getId() == R.id.grosbouton) {
            WaitingThread th = new WaitingThread();     // creation d'une instance de la classe waitingThread
            th.execute();       // on execute et on appelle cette classe

            Communication com = new Communication();    // creation d'une instance de la classe communication
            com.execute();      // on execute et on appelle cette classe
        }
        // appel a la methode luminence
        colorStringLuminance();
    }

    // methodes a faire d'apres les transparents "vie des activités", afin d'afficher un message dans la console précisant les noms de la méthode et de l'activite
    @Override
    protected void onStop() {
        super.onStop();
        Log.i("onStop()", this.getLocalClassName());
    }

    // methodes a faire d'apres les transparents "vie des activités", afin d'afficher un message dans la console précisant les noms de la méthode et de l'activite
    @Override
    protected void onDestroy() {
        Log.i("onDestroy()", this.getLocalClassName());
        super.onDestroy();
    }

    // methodes a faire d'apres les transparents "vie des activités", afin d'afficher un message dans la console précisant les noms de la méthode et de l'activite
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("onRestart()", this.getLocalClassName());
    }

    private class WaitingThread extends AsyncTask<Void, Integer, Integer> {     // modifie lea couleur du fond
        int valRouge;
        int valVert;
        int valBleu;

        // partie complementaire -> degradation de couleurs
        private int callColor(int interval, int value) {
            int x = (1530/interval)*value;  // calcul une valeur
            int r = 255;
            int g = 0;
            int b = 0;

            if(x>=0 && x<255) {
                r = 255;
                g = x;
                b = 0;
            }
            if(x>=255 && x<510) {
                r = 510-x;
                g = 255;
                b = 0;
            }
            if(x>=510 && x<765) {
                r = 0;
                g = 255;
                b = x-510;
            }
            if(x>=765 && x<1020){
                r = 0;
                g = 1020-x;
                b = 255;
            }
            if(x>=1020 && x<1275){
                r = x-1020;
                g = 0;
                b = 255;
            }
            if(x>=1275 && x<=1530){
                r = 255;
                g = 0;
                b = 1530-x;
            }
            return Color.rgb(r, g, b);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            valRouge = rouge;
            valVert = vert;
            valBleu = bleu;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            try {
                for(int i=0; i<=255; i++) {
                    publishProgress(callColor(255,i));      // sert a dire quon a changer une valeur, affichage en fonction des mise a jour
                    Thread.sleep(50);   // on met une pause de quelque seconde, pas tres lent car on veut que les couleurs s'affichent en se degradant
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... counts) {
            super.onProgressUpdate(counts);
            rouge = Color.red(counts[0]);     // on modif les couleurs rouge, vert et bleu
            vert = Color.green(counts[0]);
            bleu = Color.blue(counts[0]);
            colorStringLuminance();     // on appelle la methode de la luminence
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            rouge = valRouge;
            vert = valVert;
            bleu = valBleu;
            colorStringLuminance();
        }
    }

    private class Communication extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            String red = Integer.toHexString(rouge);
            String green = Integer.toHexString(vert);
            String blue = Integer.toHexString(bleu);

            // on ajoute une lettre qd notre chiffre hexa a qu'une seule lettre ou un chiffre
            if(red.length() == 1) red = "0" + red;
            if(green.length() == 1) green = "0" + green;
            if(blue.length() == 1) blue = "0" + blue;

            try {
                Socket socket = new Socket("chadok.info", 9998);    // Ouvrir une socket client sur le serveur
                PrintWriter printer = new PrintWriter(socket.getOutputStream(), true);  // on envoie un msg au serveur (chaine de 8 caracteres)
                printer.println("04" + red + green + blue);     // on affiche sur la console la couleur du fond (grosbouton) en hexadecimal
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream())); // on recupere un msg du serveur
                System.out.println(reader.readLine());
                //System.out.println("04" + red + green + blue);  // on affiche sur la console la couleur du fond (grosbouton) en hexadecimal
                printer.flush();
                socket.close();     // Fermer la socket
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}

