package domini.Tauler;

import domini.Partida.Difficulty;
import domini.Misc.*;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * 
 * @author David
 * 
 */

public class GeneratorController {
    
    private Hidato h;
    private int n, m;
    private int iter, val_ref;
    private int D[][]; // implementarlo de manera que digui els llocs disponibles segons el numero que hi fiquem (aixi tenim en compte les pistes)
    private Position L[];
    private int nextGiven[];
    private int totalCaselles = 0;
    private boolean controlarPart = true;
    private double factor = 1.0 * 5/10;
    private final int N = 30;
    public double iteracions, bfss, controls, controlsfallats;
    // reordenar el backtracking de manera que comenci a fer els trams entre pistes mes properes i acabi pels trams mes llargs
    
    public GeneratorController() {
        
    }
    
    public Hidato uhc (int sizeX, int sizeY) {
        this.h = new Hidato(sizeX, sizeY);
        n = sizeX;
        m = sizeY;
        h.getCell(0,0).setVal(0);
        h.getCell(n-1, m-1).setVal(0);
        
        iteracions = 0;
        
        int vegades = 10;
        boolean trobat = false;
        while (trobat == false && vegades-- > 0){
            posarCasellesVoid();
            comptaCaselles();
            int x0, y0;
            do {
                x0 = randomNum(0,n-1);
                y0 = randomNum(0,m-1);
            } while (h.getCell(x0, y0).getType() == Type.VOID);
            h.getCell(x0, y0).setVal(1);
            trobat = completarCamiUHC();
            if (trobat == false) h.getCell(x0, y0).setVal((0));
        }
        if (trobat == false) return null;
        System.out.print(Utils.toString(h));
        posarPistes();
        return h;
    }
    
    
    /**
     * Pre: cert
     * Post: retorna el hidato generat a partir de h
     * h s'interpreta de la manera seguent:
     *      * si el valor d'una casella es 0 es que hi pot anar qualsevol nombre
     *      * si el valor d'una casella no es 0, hi ha d'anar aquell nombre
     *      * si el tipus d'una casella es VOID, haura de ser VOID
     *      * si el tipus d'una casella es GIVEN, haura de ser GIVEN
     *      * si el tipus d'una casella es BLANK, sera BLANK o GIVEN, depenent
     *        de si cal donar mes nombres inicials per satisfer la dificultat
     *  retorna null si no hi ha casella inicial, o si es impossible de generar
    */
    public Hidato generateHidato(Hidato h) {
        this.h = new Hidato(h);
        n = h.getSizeX();
        m = h.getSizeY();
        comptaCaselles();
        
        iteracions = 0;
        bfss = 0;
        controls = 0;
        controlsfallats = 0;
        if (hidatoValid() == false) return null;
        
        System.out.print("Factor 5/10...\n");
        factor = 5.0/10;
        if (completarCami() == false) {
            System.out.print("Factor 8/10...\n");
            factor = 8.0/10;
            if (completarCami() == false) return null;
        }
        
        posarPistes();
        return this.h;
    }
    
    /**
     * Pre: sizeX, sizeY >= 0
     * Post: retorna el hidato generat aleatoriament de mida sizeX x sizeY
    */
    public Hidato generateHidato(int sizeX, int sizeY) {
        h = new Hidato(sizeX, sizeY);
        n = sizeX;
        m = sizeY;
        h.getCell(0,0).setVal(0);
        h.getCell(n-1, m-1).setVal(0);
        
        iteracions = 0;
        
        int vegades = 10;
        boolean trobat = false;
        while (trobat == false && vegades-- > 0){
            posarCasellesVoid();
            comptaCaselles();
            trobat = completarCamiUHC();
        }
        
        if (trobat == false) return null;
        System.out.print(Utils.toString(h) + "\n\n");
        //posarPistes();
        return h;
    }
    
    
    /*=============================PRIVADES===================================*/
    
    private boolean backtracking_enc (int val, Position p) {
        int antVal = GC(p.getX(),p.getY());
        SC(p.getX(),p.getY(),val);
        sumarVoltantD(p.getX(),p.getY(),-1);
        boolean result = backtracking(val,p);
        if (result == true) return true;
        sumarVoltantD(p.getX(),p.getY(),1);
        SC(p.getX(),p.getY(),antVal);
        return false;
    }
    
    private boolean backtracking (int val, Position p) {
        ++iteracions;
        if (massaLluny(val,p)) return false;
        L[val-1] = p;
        if (val == totalCaselles) return true;
        if (controlarPart) {
            if (!controlParticionament(val)) return false;
        }
        Position veiObligat = buscaVeiObligat(val, p);
        if (veiObligat != null) return backtracking_enc(val+1, veiObligat);
        ArrayList<Position> veins = buscaVeins(p);
        if (veins.isEmpty()) return false;
        Position veiRecomanable = buscaVeiRecomanable(veins);
        if (veiRecomanable != null) return backtracking_enc(val+1,veiRecomanable);
        int random;
        if (probabilitatCamiSegur()) {
            veins.sort((p1, p2) -> D[p1.getX()][p1.getY()] - D[p2.getX()][p2.getY()]);
            random = 0;
        }
        else random = randomNum(0,veins.size()-1);
        for (int i = 0; i < veins.size(); ++i) {
            Position next = veins.get((i+random)%veins.size());
            boolean result = backtracking_enc(val+1,next);
            if (result) return true;
            if (val_ref != 0) {
                if (val > val_ref*factor) return false;
                if (particionat(val)) {
                    val_ref = val;
                    return false;
                }
                val_ref = 0;
            }
        }
        return false;
    }
    
    private Position buscaCasellaInicial() {
        for (int i = 0; i < n; ++i){
            for (int j = 0; j < m; ++j) {
                if (h.getCell(i,j).getVal() == 1) return new Position(i,j);
            }
        }
        return null;
    }
    
    private ArrayList<Position> buscaVeins(Position p) {
        ArrayList<Position> ret = new ArrayList<>();
        for (int i = Math.max(p.getX()-1, 0); i <= Math.min(p.getX()+1, n-1); ++i) {
            for (int j = Math.max(p.getY()-1, 0); j <= Math.min(p.getY()+1, m-1); ++j) {
                if (i == p.getX() && j == p.getY()) continue;
                if (GC(i,j) == 0 && !h.getCell(i,j).getType().equals(Type.VOID)) {
                    ret.add(new Position(i,j));
                }
            }
        }
        return ret;
    }
    
    private Position buscaVeiObligat(int val, Position p) {
        for (int i = Math.max(p.getX()-1, 0); i <= Math.min(p.getX()+1, n-1); ++i) {
            for (int j = Math.max(p.getY()-1, 0); j <= Math.min(p.getY()+1, m-1); ++j) {
                if (i == p.getX() && j == p.getY()) continue;
                if (GC(i,j) == val+1 && h.getCell(i,j).getType() != Type.VOID) {
                    return new Position(i,j);
                }
            }
        }
        return null;
    }
    
    private Position buscaVeiRecomanable(ArrayList<Position> veins) {
        for (int i = 0; i < veins.size(); ++i) {
            Position vei = veins.get(i);
            if (D[vei.getX()][vei.getY()] == 1) {
                return vei;
            }
        }
        return null;
    }
    
    private int calculaNumPistes() {
        return randomNum(totalCaselles/5, totalCaselles/3+1);
    }
    
    private boolean completarCami() {
        Position casellaInicial = buscaCasellaInicial();
        if (casellaInicial == null) return false;
        L = new Position[totalCaselles];
        omplirD();
        omplirNextGivenIL();
        iter = 0;
        val_ref = 0;
        return backtracking_enc(1, casellaInicial);
    }
    
    private void comptaCaselles() {
        totalCaselles = 0;
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                if (h.getCell(i,j).getType() != Type.VOID) ++totalCaselles;
            }
        }
    }
    
    private boolean controlParticionament(int val) {
        if (++iter == N) {
            ++controls;
            iter = 0;
            if (particionat(val-1)) {
                ++controlsfallats;
                val_ref = val;
                return false;
            }
        }
        return true;
    }
    
    private int GC (int i, int j) {
        return h.getCell(i, j).getVal();
    }
    
    private boolean hidatoValid() {
        /** comprova que cada numero apareixi com a molt un cop, que no estigui
         * particionat, i que els numeros estiguin dins del rang
         */
        if (particionat(0)) return false;
        ArrayList<Integer> aparicions = new ArrayList<>();
        for (int i = 0; i < totalCaselles; ++i) aparicions.add(0);
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                if (h.getCell(i,j).getType() == Type.VOID) continue;
                if (h.getCell(i,j).getVal() == 0) continue;
                int valor = h.getCell(i,j).getVal()-1;
                if (valor < 0 || valor >= totalCaselles) return false;
                aparicions.set(valor, aparicions.get(valor)+1);
            }
        }
        for (int i = 0; i < totalCaselles; ++i) {
            if (aparicions.get(i) > 1) return false;
        }
        return true;
    }
    
    private boolean massaLluny(int val, Position p) {
        int valNextGiven = nextGiven[val-1];
        if (valNextGiven == 0) return false;
        return (Position.distance(p, L[valNextGiven-1]) > valNextGiven-val);
    }
    
    private void omplirD () {
        D = new int[n][m];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                sumarVoltantD(i,j,1);
            }
        }
    }
    
    private void omplirNextGivenIL() {
        nextGiven = new int[totalCaselles];
        ArrayList<Integer> donats = new ArrayList<>();
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                if (h.getCell(i,j).getType() == Type.VOID) continue;
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
    }
    
    private boolean particionat(int val) {
        ++bfss;
        if (val >= totalCaselles) return false;
        int x0 = 0, y0 = 0;
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
        BFS[x0][y0] = true;
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
    
    private void posarCasellesVoid() {
        if (randomNum(1,100) <= 30) return;
        do {
            int buides = randomNum(1,n*m/3);
            posarCasellesVoid(buides);
        } while (!hidatoValid());
    }
    
    private void posarCasellesVoid(int buides) {
        ArrayList<Integer> llista = new ArrayList<>();
        for (int i = 0; i < n*m; ++i) llista.add(i);
        for (int i = 0; i < buides; ++i) {
            int j = randomNum(i,n*m-1);
            Integer aux = llista.get(i);
            llista.set(i, llista.get(j));
            llista.set(j, aux);
        }
        for (int i = 0; i < n*m; ++i) {
            Integer num = llista.get(i);
            if (i < buides) h.getCell(num/m, num%m).setType(Type.VOID);
            else h.getCell(num/m, num%m).setType(Type.BLANK);
        }
    }
    
    private void posarPistes () {
        h.getCell(L[0].getX(), L[0].getY()).setType(Type.GIVEN);
        h.getCell(L[totalCaselles-1].getX(), L[totalCaselles-1].getY()).setType(Type.GIVEN);
        int pistesTotals = calculaNumPistes();
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
    
    private boolean probabilitatCamiSegur() {
        return randomNum(1,100) <= 40; // probabilitat del 40%
    }
    
    private int randomNum (int min, int max) {
        return (int)(Math.random() * (max - min + 1)) + min;
    }
    
    private void SC (int i, int j, int val) {
        h.getCell(i, j).setVal(val);
    }
    
    private void sumarValD (int x, int y, int val) {
        if (x >= 0 && x < n && y >= 0 && y < m) D[x][y] += val;
    }
    
    private void sumarVoltantD (int x, int y, int val) {
        for (int i = x-1; i <= x+1; ++i) {
            for (int j = y-1; j <= y+1; ++j) {
                if (i == x && j == y) continue;
                    sumarValD(i, j, val);
              }
          }
    }
    
    
    private class Node {
        int x, y;
        Node seg, ant;
        ArrayList<Node> veins;
        boolean posat;
        boolean esVoid;
        
        Node (int x, int y) {
            this.x = x;
            this.y = y;
            esVoid = false;
            posat = false;
            veins = new ArrayList<>();
        }
        
        public int getInt() {
            return x*m+y;
        }
    }
    
    
    Node[][] quad;
    ArrayList<Node> llista;
    
    private boolean completarCamiUHC() {
        
        quad = new Node[n][m];
        llista = new ArrayList<>();
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                quad[i][j] = new Node(i,j);
                llista.add(quad[i][j]);
                if (h.getCell(i, j).getType() == Type.VOID) quad[i][j].esVoid = true;
            }
        }
        afegirVeins();
        Node s = buscarInicial();
        Node t = buscarFinal(s);
        if (s == null || t == null) return false;
        
        Node ndp = s;
        s.posat = true;
        int Psize = 1;
        boolean solucio;
        
        while (true) {
            ++iteracions;
            // 2
            // a
            if (Psize == totalCaselles-1) {
                if (ndp.veins.contains(t)) {
                    ndp.seg = t;
                    t.ant = ndp;
                    solucio = true;
                    break;
                }
            }
            // b
            Node v = select(ndp);
            // i
            if (v == null) {
                solucio = false;
                break;
            }
            // ii
            if (!v.equals(t) && !v.posat) {
                v.posat = true;
                ++Psize;
                ndp.seg = v;
                v.ant = ndp;
                ndp = v;
            }
            // iii
            else if (!v.equals(t) && v.posat) {
                Node u = v.seg;
                Node aux;
                
                ndp.seg = v;
                u.ant = null;
                while (u != v) {
                    aux = u.seg;
                    u.seg = u.ant;
                    u.ant = aux;
                    u = u.ant;
                }
                u = v.seg;
                v.seg = ndp;
                ndp = u;
            }
            // iv
            else {
                // no fer res (i tornem a 2)
            }
        }
        
        if (solucio) {
            int val = 1;
            Node v = s;
            while (v != null) {
                h.getCell(v.x, v.y).setVal(val++);
                v = v.seg;
            }
            return true;
        }
        
        return false;
    }
    
    private void afegirVeins() {
        for (int x = 0; x < n; ++x) {
            for (int y = 0; y < m; ++y) {
                if (quad[x][y].esVoid) continue;
                for (int i = Math.max(0, x-1); i <= Math.min(n-1, x+1); ++i) {
                    for (int j = Math.max(0, y-1); j <= Math.min(m-1, y+1); ++j) {
                        if (i == x && j == y) continue;
                        if (quad[i][j].esVoid) continue;
                        quad[x][y].veins.add(quad[i][j]);
                    }
                }
            }
        }
    }
    
    private Node buscarInicial() {
        int vegades = 100;
        while (vegades-- > 0) {
            Node inicial = llista.get(randomNum(0,n*m-1));
            if (!inicial.esVoid) return inicial;
        }
        for (Node inicial : llista) {
            if (inicial.esVoid == false) return inicial;
        }
        System.out.print("incial null\n");
        return null;
    }
    
    private Node buscarFinal(Node inicial) {
        int vegades = 100;
        while (vegades-- > 0) {
            Node fin = llista.get(randomNum(0,n*m-1));
            if (!fin.esVoid && fin != inicial) return fin;
        }
        for (Node fin : llista) {
            if (!fin.esVoid && fin != inicial) return fin;
        }
        System.out.print("final null\n");
        return null;
    }
    
    private Node select (Node ndp) {
        if (ndp.veins.isEmpty()) return null;
        Node ret = ndp.veins.get(randomNum(0,ndp.veins.size()-1));
        // ndp.veins.remove(ret);
        // ret.veins.remove(ndp);
        return ret;
    }
}