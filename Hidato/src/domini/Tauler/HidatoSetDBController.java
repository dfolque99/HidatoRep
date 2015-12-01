package domini.Tauler;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author felix.axel.gimeno
 */
public final class HidatoSetDBController {
    
    private static final String hsFile = "HidatoSetFile.obj";
 
    public static void saveAll(HidatoSet hs) {
        try {
            new File(hsFile).createNewFile();
            try (FileOutputStream fos = new FileOutputStream(hsFile)) {
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(hs);
            }
        } catch (IOException ex) {
            Logger.getLogger(HidatoSetDBController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static HidatoSet loadAll() {
        try {
            if(  new File(hsFile).createNewFile() ) {HidatoSetDBController.saveAll(new HidatoSet());}
            try (FileInputStream fis = new FileInputStream(hsFile)) {
                ObjectInputStream oos = new ObjectInputStream(fis);
                return (HidatoSet) oos.readObject();
            }
        } catch (IOException | ClassNotFoundException ex) {
            return new HidatoSet();
        } 
    }
    
    private HidatoSetDBController() {
        throw new UnsupportedOperationException();}
    
}
