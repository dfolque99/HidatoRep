package domini.Partida;

import domini.Usuari.HidatoUser;

/**
 * Stub del controlador DB de la partida: hauria de proporcionar la informacio de les partides
 * a la capa de domini. Actualment conte unicament les capcaleres per evitar
 * que les classes de la capa de domini tinguin errors de compilacio
 * @author Pau
 */
public class GameDBController {

    public void saveGame(Game game) {}

    public Game getGame(String name, HidatoUser loggedUser) {
        return null;
    }

    public void deleteGame(String name, HidatoUser loggedUser) {}
    
}
