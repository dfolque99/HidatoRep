package CapaDomini.Tauler;


import CapaDomini.Misc.Position;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Classe encarregada de la generació aleatòria de hidatos.
 * @author David
 */

public class GeneratorController {
    
    /**
     * Hidato que s'esta generant
     */
    private Hidato h;
    
    /**
     * Dimensions del hidato que s'esta generant
     */
    private int n, m;
    
    /**
     * Nombre de vegades que s'ha cridat la funcio del backtracking
     */
    private int iter;
    
    /**
     * Valor que s'estava posant quan s'ha trobat particionament al hidato
     */
    private int val_ref;
    
    /**
     * Nombre de caselles no omplertes al voltant de la casella (i,j)
     */
    private int D[][]; 
    
    /**
     * Posicio del hidato on es troba cada valor i
     */
    private Position L[];
    
    /**
     * Proxim valor predeterminat despres del valor i
     */
    private int nextGiven[];
    
    /**
     * Nombre total de caselles que s'han d'omplir al hidato
     */
    private int totalCaselles;
    
    /**
     * Proporcio del cami que s'ha de conservar al tirar endarrere en el
     * backtracking al trobar particionament
     */
    private double factor;
    
    /**
     * Nombre d'iteracions del backtracking que passen sense comprovar el
     * particionament
     */
    private final int N = 30;
    
    /**
     * Array de nodes, representa el hidato, s'utilitza en l'algorisme UHC
     */
    private Node[][] quad;
    
    
    /**
     * Pre: cert
     * Post: retorna el hidato generat a partir de h
     * h s'interpreta de la manera seguent:
     *      * si el valor d'una casella es 0 es que hi pot anar qualsevol nombre
     *      * si el valor d'una casella no es 0, hi ha d'anar aquell nombre
     *      * si el tipus d'una casella es VOID, haura de ser VOID
     *      * si el tipus d'una casella es GIVEN, haura de ser GIVEN
     *      * si el tipus d'una casella es BLANK, sera BLANK o GIVEN, depenent
     *        de si cal donar mes nombres inicials
     *  retorna null si no hi ha casella inicial, o si es impossible de generar
    */
    public Hidato generateHidato(Hidato h) {
        SolverControllerStop.allow();
        this.h = new Hidato(h);
        n = h.getSizeX();
        m = h.getSizeY();
        
        comptaCaselles();
        if (!hidatoValid()) return null;
        
        factor = 5.0/10;
        if (!completarCami()) {
            factor = 8.0/10;
            if (!completarCami()) return null;
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
        
        iter = 0;
        int vegades = 0;
        boolean trobat = false;
        
        // provem 7 vegades de trobar el cami afegint caselles void a l'atzar
        // si no trobem cami, provem 3 vegades sense afegir caselles void
        while (trobat == false && vegades < 10){
            if (vegades >= 7) {
                posarCasellesVoid(0);
            }
            else {
                posarCasellesVoid();
            }
            comptaCaselles();
            trobat = generarCamiUHC();
            vegades++;
        }
        
        // si fins ara no hem trobat cap cami, fem un backtracking exhaustiu
        if (trobat == false) {
            h = new Hidato(sizeX, sizeY);
            h.getCell(0,0).setVal(0);
            h.getCell(n-1, m-1).setVal(0);
            iter = 0;
            comptaCaselles();
            h.getCell(randomNum(0,n-1), randomNum(0,m-1)).setVal(1);
            factor = 1;
            trobat = completarCami();
        }
        if (trobat == false) return null;
        omplirL();
        posarPistes();
        return h;
    }
    
    
    /*================================UHC=====================================*/
    
    /**
     * Classe utilitzada per a l'algorisme UHC.
     * Consta principalment d'una posicio al taulell, i de nodes anterior i
     * seguent, així com també un array amb els seus veïns al taulell, i flags
     * posat i esVoid
     */
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
    }
    
    /**
     * Genera un camí aleatori en el hidato, utilitzant l'algorisme UHC.
     * Retorna un booleà que diu si s'ha trobat el camí.
     */
    private boolean generarCamiUHC() {
        quad = new Node[n][m];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                quad[i][j] = new Node(i,j);
                if (h.getCell(i, j).getType() == Type.VOID) quad[i][j].esVoid = true;
            }
        }
        afegirVeins();
        Node s = buscarInicial();
        if (s == null) return false;
        
        Node ndp = s;
        s.posat = true;
        int Psize = 1;
        boolean solucio = false;
        iter = 0;
        int maxim = Math.max(n,m);
        int nn = maxim*maxim;
        double aproximador = nn*Math.log(nn)*Math.log(nn)*5;
        
        while (iter < Math.max(aproximador*5, 10000)) {
            ++iter;
            // 2
            // a
            if (Psize == totalCaselles) {
                solucio = true;
                break;
            }
            // b
            Node v = select(ndp);
            // i
            if (v == null) {
                solucio = false;
                break;
            }
            // ii
            if (!v.posat) {
                v.posat = true;
                ++Psize;
                ndp.seg = v;
                v.ant = ndp;
                ndp = v;
            }
            // iii
            else if (v.posat) {
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
    
    /**
     * Omple l'arrayList de veins dels nodes
     */
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
    
    /**
     * Escull un node no void com a node inicial
     */
    private Node buscarInicial() {
        int vegades = 100;
        while (vegades-- > 0) {
            int r1 = randomNum(0,n-1);
            int r2 = randomNum(0,m-1);
            Node inicial = quad[r1][r2];
            if (!inicial.esVoid) return inicial;
        }
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                if (quad[i][j].esVoid == false) return quad[i][j];
            }
        }
        return null;
    }
    
    /**
     * Retorna un vei aleatori d'entre els del node ndp
     */
    private Node select (Node ndp) {
        if (ndp.veins.isEmpty()) return null;
        Node ret = ndp.veins.get(randomNum(0,ndp.veins.size()-1));
        return ret;
    }
    
    
    /*===========================BACKTRACKING=================================*/
    
    /**
     * Funció que encapsula la crida al backtracking.
     * Actualitza D i h, abans i despres de la crida
     */
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
    
    /**
     * Funció principal del mètode de resolució amb backtracking.
     * Després de fer unes comprovacions per optimitzar la poda del 
     * backtracking, fa la crida recursiva a la funció als seus veïns
     */
    private boolean backtracking (int val, Position p) {
        if(SolverControllerStop.isStopped()) return false;
        if (massaLluny(val,p)) return false;
        L[val-1] = p;
        if (val == totalCaselles) return true;
        if (!controlParticionament(val)) return false;
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
    
    /**
     * Retorna la casella del hidato que té el valor 1
     */
    private Position buscaCasellaInicial() {
        for (int i = 0; i < n; ++i){
            for (int j = 0; j < m; ++j) {
                if (h.getCell(i,j).getVal() == 1) return new Position(i,j);
            }
        }
        return null;
    }
    
    /**
     * Retorna un array amb els veïns on es pot moure el backtracking
     */
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
    
    /**
     * Mira si hi ha un veí que té per valor donat el següent al backtracking
     * i el retorna si el troba
     */
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
    
    /**
     * Si alguna de les caselles de veins només te un veí disponible el retorna
     */
    private Position buscaVeiRecomanable(ArrayList<Position> veins) {
        for (int i = 0; i < veins.size(); ++i) {
            Position vei = veins.get(i);
            if (D[vei.getX()][vei.getY()] == 1) {
                return vei;
            }
        }
        return null;
    }
    
    /**
     * Fa els preparatius per efectuar la generació i fa la crida inicial del
     * backtracking
     */
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
    
    /**
     * Efectua el control de particionament del taulell
     */
    private boolean controlParticionament(int val) {
        if (++iter == N) {
            iter = 0;
            if (particionat(val-1)) {
                val_ref = val;
                return false;
            }
        }
        return true;
    }
    
    /**
     * Mètode abreujat per trobar el valor d'una cel·la del hidato
     */
    private int GC (int i, int j) {
        return h.getCell(i, j).getVal();
    }
    
    /**
     * Mira si la posicio p amb valor val està massa lluny de la seguent pista,
     * com per poder-hi arribar
     */
    private boolean massaLluny(int val, Position p) {
        int valNextGiven = nextGiven[val-1];
        if (valNextGiven == 0) return false;
        return (Position.distance(p, L[valNextGiven-1]) > valNextGiven-val);
    }
    
    /**
     * Omple l'array D correctament
     */
    private void omplirD () {
        D = new int[n][m];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                sumarVoltantD(i,j,1);
            }
        }
    }
    
    /**
     * Omple L i nextGiven amb les dades de les caselles prederminades
     */
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
    
    /**
     * Comprova si hi ha particionament, comptant les caselles omplertes amb
     * valors inferiors a val com a omplenades, i les que tenen valors superiors
     * com a no omplenades
     */
    private boolean particionat(int val) {
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
    
    /**
     * Retorna un boolea que es true amb probabilitat del 40%, que ens marca
     * si hem d'accedir als veins en ordre òptim per trobar un hidato bo, o en
     * ordre aleatori
     */
    private boolean probabilitatCamiSegur() {
        return randomNum(1,100) <= 40; // probabilitat del 40%
    }
    
    /**
     * Mètode abreujat per posar el valor d'una cel·la del hidato
     */
    private void SC (int i, int j, int val) {
        h.getCell(i, j).setVal(val);
    }
    
    /**
     * Si (x,y) es una posicio valida, suma val a D[x][y]
     */
    private void sumarValD (int x, int y, int val) {
        if (x >= 0 && x < n && y >= 0 && y < m) D[x][y] += val;
    }
    
    /**
     * Suma val a totes les posicions valides al voltant de (x,y)
     */
    private void sumarVoltantD (int x, int y, int val) {
        for (int i = x-1; i <= x+1; ++i) {
            for (int j = y-1; j <= y+1; ++j) {
                if (i == x && j == y) continue;
                    sumarValD(i, j, val);
              }
          }
    }
    
    
    
    /*=============================DE TOT===================================*/
    
    /**
     * Escull el nombre de pistes que es donaran a l'atzar entre 1/5 del total
     * i 1/3 (+1 per evitar errors) del total
     */
    private int calculaNumPistes() {
        return randomNum(totalCaselles/5, totalCaselles/3+1);
    }
    
    /**
     * Dona a totalCaselles el valor adequat
     */
    private void comptaCaselles() {
        totalCaselles = 0;
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                if (h.getCell(i,j).getType() != Type.VOID) ++totalCaselles;
            }
        }
    }
    
    
    /**
     * Comprova que cada numero apareixi com a molt una vegada, que el hidato
     * no estigui parcitionat, i que els numeros estiguin a dins del rang
     */
    private boolean hidatoValid() {
        if (particionat()) return false;
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
    
    /**
     * Omple l'array L correctament
     */
    private void omplirL() {
        L = new Position[totalCaselles];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                if (h.getCell(i,j).getType() != Type.VOID) {
                    int val = h.getCell(i, j).getVal();
                    L[val-1] = new Position(i,j);
                }
            }
        }
    }
    
    /**
     * Comprova si hi ha particionament, comptant totes les caselles VOID
     */
    private boolean particionat () {
        int x0 = 0, y0 = 0;
        boolean BFS[][] = new boolean[n][m];
        for (int i = 0; i < n; ++i) {
          for (int j = 0; j < m; ++j) {
            BFS[i][j] = true;
            if (!h.getCell(i, j).getType().equals(Type.VOID)) {
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
    
    /**
     * Amb probabilitat de 70%, posa algunes caselles void aleatoriament (entre
     * 1 i n*m/2 aleatòriament)
     */
    private void posarCasellesVoid() {
        if (randomNum(1,100) <= 30) {
            posarCasellesVoid(0);
        }
        else {
            do {
                int buides = randomNum(1,n*m/2);
                posarCasellesVoid(buides);
            } while (!hidatoValid());
        }
    }
    
    /**
     * Posa tantes caselles void a l'atzar com el valor de buides
     */
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
    
    /**
     * Posa les pistes que s'han preescrit i les necessaries perquè n'hi hagi
     * el nombre retornat per calculaNumPistes()
     */
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
    
    /**
     * Retorna un numero aleatori de [min,max]
     */
    private int randomNum (int min, int max) {
        return (int)(Math.random() * (max - min + 1)) + min;
    }
    
    
}