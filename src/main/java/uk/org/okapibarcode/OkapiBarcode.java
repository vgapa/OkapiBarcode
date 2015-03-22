
package uk.org.okapibarcode;

import uk.org.okapibarcode.gui.OkapiUI;
import com.beust.jcommander.*;

/**
 *
 * @author Robin Stuart <rstuart114@gmail.com>
 * @author Robert Elliott <jakel2006@me.com>
 */
public class OkapiBarcode {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Settings settings = new Settings();
        new JCommander(settings, args);

        if (settings.isSupressGui() == false) {
            OkapiUI okapiUi = new OkapiUI();
            okapiUi.setVisible(true);
        }
    }
}
