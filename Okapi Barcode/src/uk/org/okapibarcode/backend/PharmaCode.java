package uk.org.okapibarcode.backend;

/**
 *
 * @author Robin Stuart <rstuart114@gmail.com>
 */
public class PharmaCode extends Symbol {

    @Override
    public boolean encode() {
        int tester = 0;
        int i;

        String inter = "";
        String dest = "";

        if (content.length() > 6) {
            error_msg = "Input too long";
            return false;
        }

        if (!(content.matches("[0-9]+?"))) {
            error_msg = "Invalid characters in data";
            return false;
        }

        for (i = 0; i < content.length(); i++) {
            tester *= 10;
            tester += Character.getNumericValue(content.charAt(i));
        }

        if ((tester < 3) || (tester > 131070)) {
            error_msg = "Data out of range";
            return false;
        }

        do {
            if ((tester & 1) == 0) {
                inter += "W";
                tester = (tester - 2) / 2;
            } else {
                inter += "N";
                tester = (tester - 1) / 2;
            }
        } while (tester != 0);

        for (i = inter.length() - 1; i >= 0; i--) {
            if (inter.charAt(i) == 'W') {
                dest += "32";
            } else {
                dest += "12";
            }
        }

        readable = "";
        pattern = new String[1];
        pattern[0] = dest;
        row_count = 1;
        row_height = new int[1];
        row_height[0] = -1;
        plotSymbol();
        return true;
    }
}