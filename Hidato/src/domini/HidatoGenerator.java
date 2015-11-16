/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domini;


import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author David
 * 
 *      
 */


public class HidatoGenerator {
    
    private Hidato h;
    private int n, m;
    private int iter, val_ref;
    private int D[][]; // D[i][j] = caselles disponibles al voltant de (i,j)
    private Position L[]; // L[i] = lloc del numero i
    private int nextGiven[];
    private int totalCaselles;
    private String error;
    private long iteracions_totals, bfs_totals;
    
    public HidatoGenerator(int sizeX, int sizeY) {
        this.h = new Hidato(sizeX, sizeY);
        n = sizeX;
        m = sizeY;
        totalCaselles = sizeX*sizeY;
    }
    
    public HidatoGenerator(Hidato h) {
        this.h = new Hidato(h);
        n = h.getSizeX();
        m = h.getSizeY();
        comptaCaselles();
    }
    
    
    /**
        completa el hidato h a un complet
        retorna null si no hi ha casella inicial o si es impossible de generar
    */
    
    public Hidato generateHidato(Difficulty difficulty) {
        
        if (hidatoValid() == false) {
            error = "Hidato inicial no valid: " + error;
            return null;
        }
        if (completarCami() == false) {
            error = "No hi ha cami possible: " + error;
            return null;
        }
        posarPistes(difficulty);
        
        return h;
    }
    
    
    
    
    public String getError() {
        return error;
    }
    
    /*============================== PRIVADES ================================*/
    
    private void posarPistes (Difficulty difficulty) {
        h.getCell(L[0].getX(), L[0].getY()).setType(Type.GIVEN);
        h.getCell(L[totalCaselles-1].getX(), L[totalCaselles-1].getY()).setType(Type.GIVEN);
        
        int pistesTotals = calculaNumPistes(difficulty);
        int pistesFixades = 0;
        
        ArrayList<Integer> pistes = new ArrayList<>();
        for (int i = 0; i < totalCaselles; ++i) {
            if (h.getCell(L[i].getX(), L[i].getY()).getType() == Type.BLANK) pistes.add(i);
            else if (h.getCell(L[i].getX(), L[i].getY()).getType() == Type.GIVEN) ++pistesFixades;
        }
        
        int pistesAddicionals = pistesTotals - pistesFixades;
        for (int i = 0; i < pistesAddicionals; ++i) {
            int j = randomNum(i,pistes.size()-1);
            Integer aux = pistes.get(i);
            pistes.set(i, pistes.get(j));
            pistes.set(j,aux);
            Position p = L[pistes.get(i)];
            h.getCell(p.getX(), p.getY()).setType(Type.GIVEN);
        }
        
    }
    
    private int calculaNumPistes(Difficulty difficulty) {
        if (difficulty == Difficulty.EASY) return totalCaselles/3;
        else if (difficulty == Difficulty.MEDIUM) return totalCaselles/4;
        return totalCaselles/5;
    }
    
    private boolean completarCami() {
        Position casellaInicial = buscaCasellaInicial();
        if (casellaInicial == null) {
            error = "no hi ha casella inicial";
            return false;
        }
        L = new Position[totalCaselles];
        omplirD();
        omplirNextGivenIL();
        iter = 0;
        iteracions_totals = 0;
        bfs_totals = 0;
        val_ref = 0;
        
        error = "impossible (o molt dificil)";
        return backtracking(1, casellaInicial);
    }
    
    public long getIt() {
        return iteracions_totals;
    }
    public long getBfs() {
        return bfs_totals;
    }
    
    private boolean backtracking (int val, Position p) {
        ++iteracions_totals;
        if (massaLluny(val,p)) return false;
        L[val-1] = p;
        if (val == totalCaselles) return true;
        if (!controlParticionament(val)) return false;
        Position veiObligat = buscaVeiObligat(val, p);
        if (veiObligat != null) return backtracking(val+1, veiObligat);
        LinkedList<Position> veins = buscaVeins(p);
        if (veins.size() == 0) return false;
        int random;
        if (probabilitatCamiSegur()) {
            veins.sort((p1, p2) -> D[p1.getX()][p1.getY()] - D[p2.getX()][p2.getY()]);
            random = 0;
        }
        else random = randomNum(0,veins.size()-1);
        for (int i = 0; i < veins.size(); ++i) {
            Position next = veins.get((i+random)%veins.size());
            int antVal = GC(next.getX(),next.getY());
            SC(next.getX(),next.getY(),val+1);
            sumaVoltantD(next.getX(),next.getY(),-1);
            boolean result = backtracking(val+1,next);
            if (result) return true;
            sumaVoltantD(next.getX(),next.getY(),1);
            SC(next.getX(),next.getY(),antVal);
            if (val_ref != 0) {
                if (val > val_ref*1/2) return false;
                if (particionat(val)) {
                    val_ref = val;
                    return false;
                }
                val_ref = 0;
            }
        }
        return false;
    }
    
    private boolean probabilitatCamiSegur() {
        return randomNum(1,100) <= 40; // probabilitat del 40%
    }
    
    private LinkedList<Position> buscaVeins(Position p) {
        LinkedList<Position> ret = new LinkedList<>();
        for (int i = Math.max(p.getX()-1, 0); i <= Math.min(p.getX()+1, n-1); ++i) {
            for (int j = Math.max(p.getY()-1, 0); j <= Math.min(p.getY()+1, m-1); ++j) {
                if (i == p.getX() && j == p.getY()) continue;
                if (GC(i,j) == 0 && !h.getCell(i,j).getType().equals(Type.VOID)) {
                    ret.addLast(new Position(i,j));
                }
            }
        }
        return ret;
    }
    
    private Position buscaVeiObligat(int val, Position p) {
        for (int i = Math.max(p.getX()-1, 0); i <= Math.min(p.getX()+1, n-1); ++i) {
            for (int j = Math.max(p.getY()-1, 0); j <= Math.min(p.getY()+1, m-1); ++j) {
                if (i == p.getX() && j == p.getY()) continue;
                if (GC(i,j) == val+1 && !h.getCell(i,j).getType().equals(Type.VOID)) {
                    return new Position(i,j);
                }
            }
        }
        return null;
    }
    
    private boolean controlParticionament(int val) {
        if (++iter == 30) {
            iter = 0;
            if (particionat(val-1)) {
                val_ref = val;
                return false;
            }
        }
        return true;
    }
    
    private Position buscaCasellaInicial() {
        for (int i = 0; i < n; ++i){
            for (int j = 0; j < m; ++j) {
                if (h.getCell(i,j).getVal() == 1) return new Position(i,j);
            }
        }
        return null;
    }
    
    public boolean particionat(int val) {
        ++bfs_totals;
        if (val >= totalCaselles) return false;
        // fa un bfs des de la primera posicio buida que troba
        int x0 = 0, y0 = 0; //inicialitzem perque no doni warning
        /* busca una casella buida (y0,x0) i posa totes les caselles buides 
         * fins al moment a false en BFS i les plenes a true
         */
        
        boolean BFS[][] = new boolean[n][m];
        for (int i = 0; i < n; ++i) {
          for (int j = 0; j < m; ++j) {
            BFS[i][j] = true;
            if ((GC(i,j) == 0 || GC(i,j) > val) && !h.getCell(i, j).getType().equals(Type.VOID)) {
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
            int a = Q.getFirst().getX(), b = Q.getFirst().getY();
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
    
    public boolean massaLluny(int val, Position p) {
        int valNextGiven = nextGiven[val-1];
        if (valNextGiven == 0) return false;
        return (Position.distance(p, L[valNextGiven-1]) > valNextGiven-val);
    }
    
    private void comptaCaselles() {
        totalCaselles = 0;
        for (int i = 0; i < h.getSizeX(); ++i) {
            for (int j = 0; j < h.getSizeY(); ++j) {
                if (h.getCell(i,j).getType() != Type.VOID) ++totalCaselles;
            }
        }
    }

    public int getTotalCaselles() {
        return totalCaselles;
    }
    
    private void sumarValD (int x, int y, int val) {
        if (x >= 0 && x < n && y >= 0 && y < m) D[x][y] += val;
    }
    
    private void sumaVoltantD (int x, int y, int val) {
        for (int i = x-1; i <= x+1; ++i) {
            for (int j = y-1; j <= y+1; ++j) {
                if (i == x && j == y) continue;
                    sumarValD(i, j, val);
              }
          }
    }
    
    private void omplirD () {
        D = new int[n][m];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                if(GC(i,j) == 0) sumaVoltantD(i,j,1);
            }
        }
    }
    
    private void omplirNextGivenIL() {
        nextGiven = new int[totalCaselles];
        ArrayList<Integer> donats = new ArrayList<>();
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                int valor = h.getCell(i, j).getVal();
                if (valor != 0) {
                    donats.add(valor);
                    L[valor-1] = new Position(i,j);
                }
            }
        }
        for (int i = 0; i < donats.size(); ++i) {
            int valor = donats.get(i);
            int j = valor-1;
            while (nextGiven[j] == 0 || nextGiven[j] > valor) {
                nextGiven[j] = valor;
                if (--j < 0) break;
            }
        }
        //for (int i = 0; i < totalCaselles; ++i) System.out.printf("(%d,%d) ", LG[nextGiven[i]-1].getX(),LG[nextGiven[i]-1].getY());
        //System.out.print("\n");
    }
    
    private int randomNum (int min, int max) {
        return (int)(Math.random() * (max - min + 1)) + min;
    }
    
    /** comprova que cada numero apareixi com a molt un cop, que no estigui
     * particionat, que els numeros estiguin dins del rang
     */
    private boolean hidatoValid() {
        if (particionat(0)) {
            error = "particionat";
            return false;
        }
        
        ArrayList<Integer> aparicions = new ArrayList<>();
        for (int i = 0; i < totalCaselles; ++i) aparicions.add(0);
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                if (h.getCell(i,j).getType() == Type.VOID) continue;
                if (h.getCell(i,j).getVal() == 0) continue;
                int valor = h.getCell(i,j).getVal()-1;
                if (valor < 0 || valor >= totalCaselles) {
                    error = "index fora de rang";
                    return false;
                }
                aparicions.set(valor, aparicions.get(valor)+1);
            }
        }
        for (int i = 0; i < totalCaselles; ++i) {
            if (aparicions.get(i) > 1) {
                error = "mes d'una aparicio del mateix numero";
                return false;
            }
        }
        
        return true;
    }
    
    private int GC (int i, int j) {
        return h.getCell(i, j).getVal();
    }
    private void SC (int i, int j, int val) {
        h.getCell(i, j).setVal(val);
    }
    
    private void dibuixa() {
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                    System.out.printf("%d ", GC(i,j));
                    if (GC(i,j) < 10) System.out.printf(" ");
                    if (GC(i,j) < 100) System.out.printf(" ");
            }
            System.out.printf("\n");
        }
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                    System.out.printf("%d ", D[i][j]);
                    if (D[i][j] < 10) System.out.printf(" ");
                    if (D[i][j] < 100) System.out.printf(" ");
            }
            System.out.printf("\n");
        }
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                if (h.getCell(i, j).getType() == Type.GIVEN) {
                    System.out.printf("%d ", GC(i,j));
                    if (GC(i,j) < 10) System.out.printf(" ");
                    if (GC(i,j) < 100) System.out.printf(" ");
                }
                else System.out.printf("__  ", GC(i,j));
            }
            System.out.printf("\n");
        }
    }
    
    
}