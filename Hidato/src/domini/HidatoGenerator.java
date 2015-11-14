/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domini;

import java.util.LinkedList;

/**
 *
 * @author David
 */
public class HidatoGenerator {
    
    private Hidato h;
    private int R[][]; // s'ha d'eliminar
    private int totalCaselles;
    
    public HidatoGenerator(int sizeX, int sizeY) {
        this.h = new Hidato(sizeX, sizeY);
        R = new int[sizeX][sizeY]; // canviar
        comptaCaselles();
    }
    public HidatoGenerator(Hidato h) {
        this.h = new Hidato(h);
        R = new int[h.getSizeX()][h.getSizeY()]; // canviar
        comptaCaselles();
    }
    
    
    /*
        completa el hidato h a un complet
        retorna null si no hi ha casella inicial o si es impossible de generar
    */
    public Hidato generateHidato(String difficulty) {
        if (noCasellaInicial()) return null;
        if (completarCami() == false) return null;
        posarPistes(difficulty);
        return h;
    }
    
    
    
    
    /*============================== PRIVADES ================================*/
    
    private class Position {
        int x;
        int y;
        public Position(int x, int y) {this.x = x; this.y = y;}
    }
    
    private boolean noCasellaInicial() {
        boolean ret = true;
        for (int i = 0; i < h.getSizeY(); ++i){
            for (int j = 0; j < h.getSizeX(); ++j) {
                if (h.getCell(i,j).getVal() == 1) ret = false;
            }
        }
        return ret;
    }
    
    private boolean completarCami() {
        
    }
    
    public boolean particionat(int val) {
        if (val >= totalCaselles) return false;
        // fa un bfs des de la primera posicio buida que troba
        int x0 = 0, y0 = 0; //inicialitzem perque no doni warning
        /* busca una casella buida (y0,x0) i posa totes les caselles buides 
         * fins al moment a false en BFS i les plenes a true
         */
        int n = h.getSizeX();
        int m = h.getSizeY();
        boolean BFS[][] = new boolean[n][m];
        for (int i = 0; i < n; ++i) {
          for (int j = 0; j < m; ++j) {
            BFS[i][j] = true;
            if (R[i][j] == 0 || R[i][j] > val) {
              x0 = i; y0 = j;
              BFS[i][j] = false;
            }
          }
        }
        // a partir d'ara BFS[i][j] = true vol dir que esta a la cua o que ja esta plena
        BFS[x0][y0] = true;
        // Q.push(par(y0,x0));
        LinkedList<Position> Q = new LinkedList<>();
        Q.addLast(new Position(x0,y0));
        while (Q.size() > 0) {
            int a = Q.getFirst().x, b = Q.getFirst().y;
            for (int i = Math.max(a-1, 0); i <= Math.min(a+1, n-1); ++i) {
                for (int j = Math.max(b-1, 0); j <= Math.min(b+1, m-1); ++j) {
                    if (BFS[i][j] == false) {
                        BFS[i][j] = true;
                        Q.addLast(new Position(i,j));
                    }
                }
            }
            Q.removeFirst();
        }
        boolean part = false;
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                part = part || !BFS[i][j];
            }
        }
  
  return part;
    }
    
    private void posarPistes(String difficulty) {
        
    }
    
    private void comptaCaselles() {
        totalCaselles = 0;
        for (int i = 0; i < h.getSizeX(); ++i) {
            for (int j = 0; j < h.getSizeY(); ++j) {
                if (h.getCell(i,j).getType() != "No Valida") ++totalCaselles;
            }
        }
    }
    
}