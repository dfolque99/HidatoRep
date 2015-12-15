package CapaPersistencia;

import CapaDomini.Tauler.HidatoSet;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * to save and load hidatosets
 *
 * @author felix.axel.gimeno
 * @see HidatoSet
 */
public final class HidatoSetDBController {

    /**
     * file name to save and load
     */
    private static final String hsFile = "HidatoSetFile.obj";

    /**
     * save hidatoset hs,
     *
     * @param hs
     */
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

    /**
     * load hidatoset saved, if it doesnt exist, it creates an empty one and
     * returns it
     *
     * @return
     */
    public static HidatoSet loadAll() {
        try {
            if (new File(hsFile).createNewFile()) {
                HidatoSetDBController.saveAll(new HidatoSet());
            }
            try (FileInputStream fis = new FileInputStream(hsFile)) {
                ObjectInputStream oos = new ObjectInputStream(fis);
                return (HidatoSet) oos.readObject();
            }
        } catch (IOException | ClassNotFoundException ex) {
            return new HidatoSet();
        }
    }

    /**
     * disabled
     */
    private HidatoSetDBController() {
        throw new UnsupportedOperationException();
    }

}
