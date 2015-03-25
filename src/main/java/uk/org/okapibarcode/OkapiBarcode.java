/*
 * Copyright 2015 Robin Stuart
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.org.okapibarcode;

import uk.org.okapibarcode.gui.OkapiUI;
import com.beust.jcommander.*;
//import java.io.File;
import uk.org.okapibarcode.backend.Barcode;
//import uk.org.okapibarcode.gui.OpenFile;

/**
 *
 * @author Robin Stuart <rstuart114@gmail.com>
 * @author Robert Elliott <jakel2006@me.com>
 */
public class OkapiBarcode {

    private static final String[] tBarcodeType = {
        "BARCODE_CODE11", "BARCODE_C25MATRIX", "BARCODE_C25INTER", "BARCODE_C25IATA", "",
        "BARCODE_C25LOGIC", "BARCODE_C25IND", "BARCODE_CODE39", "BARCODE_EXCODE39", "", // 10
        "", "", "BARCODE_EANX", "", "",
        "", "", "BARCODE_CODABAR", "", "", "BARCODE_CODE128", // 20
        "BARCODE_DPLEIT", "BARCODE_DPIDENT", "BARCODE_CODE16K", "BARCODE_CODE49", "BARCODE_CODE93",
        "", "", "", "BARCODE_RSS14", "BARCODE_RSS_LTD", // 30
        "BARCODE_RSS_EXP", "BARCODE_TELEPEN", "", "BARCODE_UPCA", "",
        "", "BARCODE_UPCE", "", "", "BARCODE_POSTNET", // 40
        "", "", "", "", "",
        "", "BARCODE_MSI_PLESSEY", "", "", "BARCODE_LOGMARS", // 50
        "BARCODE_PHARMA", "BARCODE_PZN", "BARCODE_PHARMA_TWO", "", "BARCODE_PDF417",
        "BARCODE_PDF417TRUNC", "BARCODE_MAXICODE", "BARCODE_QRCODE", "", "", "", // 60
        "", "", "BARCODE_AUSPOST", "", "",
        "BARCODE_AUSREPLY", "BARCODE_AUSROUTE", "BARCODE_AUSREDIRECT", "", "BARCODE_RM4SCC", // 70
        "BARCODE_DATAMATRIX", "", "", "", "",
        "BARCODE_JAPANPOST", "BARCODE_KOREAPOST", "", "BARCODE_RSS14STACK", "BARCODE_RSS14STACK_OMNI", // 80
        "BARCODE_RSS_EXPSTACK", "BARCODE_PLANET", "", "BARCODE_MICROPDF417", "BARCODE_ONECODE",
        "BARCODE_MSI_PLESSEY", "BARCODE_TELEPEN_NUM", "", "BARCODE_ITF14", "BARCODE_KIX", // 90
        "", "BARCODE_AZTEC", "", "", "",
        "", "BARCODE_MICROQR", "BARCODE_HIBC_128", "BARCODE_HIBC_39", "", // 100
        "", "BARCODE_HIBC_DM", "", "BARCODE_HIBC_QR", "",
        "BARCODE_HIBC_PDF", "", "BARCODE_HIBC_MICPDF", "", "", // 110
        "", "BARCODE_HIBC_AZTEC", "", "", "",
        "", "", "", "", "", // 120
        "", "", "", "", "",
        "", "", "BARCODE_AZRUNE", "BARCODE_CODE23", "BARCODE_EANX", // 130
        "BARCODE_CODE128", "BARCODE_RSS14", "BARCODE_RSS_LTD", "BARCODE_RSS_EXP", "BARCODE_UPCA",
        "BARCODE_UPCE", "BARCODE_RSS14STACK", "BARCODE_RSS14STACK_OMNI", "BARCODE_RSS_EXPSTACK", "BARCODE_CHANNEL", // 140
        "BARCODE_CODEONE", "BARCODE_GRIDMATRIX"
    };
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Settings settings = new Settings();
        new JCommander(settings, args);

        if (settings.isSupressGui() == false) {
            OkapiUI okapiUi = new OkapiUI();
            okapiUi.setVisible(true);
        } else {
            int returnValue;
            
            returnValue = commandLine(settings);
            
            if (returnValue != 0) {
                System.out.println("An error occurred");
            }
        }
    }
    
    private static int commandLine(Settings settings) {
        
        String inputData = settings.getInputData();
        String inputFile = settings.getInputFile();
        
        if (settings.isDisplayTypes()) {
            System.out.print(
                " 1: Code 11           51: Pharma One-Track         90: KIX Code\n" +
		" 2: Standard 2of5     52: PZN                      92: Aztec Code\n" +
		" 3: Interleaved 2of5  53: Pharma Two-Track         97: Micro QR Code\n" +
		" 4: IATA 2of5         55: PDF417                   98: HIBC Code 128\n" +
		" 6: Data Logic        56: PDF417 Trunc             99: HIBC Code 39\n" +
		" 7: Industrial 2of5   57: Maxicode                102: HIBC Data Matrix\n" +
		" 8: Code 39           58: QR Code                 104: HIBC QR Code\n" +
		" 9: Extended Code 39  60: Code 128-B              106: HIBC PDF417\n" +
		"13: EAN               63: AP Standard Customer    108: HIBC MicroPDF417\n" +
		"18: Codabar           66: AP Reply Paid           112: HIBC Aztec Code\n" +
		"20: Code 128          67: AP Routing              128: Aztec Runes\n" +
		"21: Leitcode          68: AP Redirection          129: Code 23\n" +
		"22: Identcode         70: RM4SCC                  130: Comp EAN\n" +
		"23: Code 16k          71: Data Matrix             131: Comp GS1-128\n" +
		"24: Code 49           76: Japanese Post           132: Comp Databar-14\n" +
		"25: Code 93           77: Korea Post              133: Comp Databar Ltd\n" +
		"29: Databar-14        79: Databar-14 Stack        134: Comp Databar Ext\n" +
		"30: Databar Limited   80: Databar-14 Stack Omni   135: Comp UPC-A\n" +
		"31: Databar Extended  81: Databar Extended Stack  136: Comp UPC-E\n" +
		"32: Telepen Alpha     82: Planet                  137: Comp Databar-14 Stack\n" +
		"34: UPC-A             84: MicroPDF                138: Comp Databar Stack Omni\n" +
		"37: UPC-E             85: USPS OneCode            139: Comp Databar Ext Stack\n" +
		"40: Postnet           86: UK Plessey              140: Channel Code\n" +
		"47: MSI Plessey       87: Telepen Numeric         141: Code One\n" +
		"50: Logmars           89: ITF-14                  142: Grid Matrix\n" );
        }
        
        if (inputData.isEmpty() && inputFile.isEmpty()) {
            System.out.println("error: No data received, no symbol generated");
            return 0;
        }
        
        if (inputFile.isEmpty()) {
            if (!(settings.isDataBinaryMode())) {
                inputData = escapeCharProcess(inputData);
            }
            makeBarcode(settings, inputData);
        } else {
            processFile(settings);
        }
        
        
        return 0;
    }
    
    private static void processFile(Settings settings) {
        
        //String inputFromFile;
        //inputFromFile = OpenFile.ReadFile(file, true);
        
        System.out.println("File handling coming soon!");
    }    
    
    private static String escapeCharProcess(String inputString) {
        String outputString = "";
        int i = 0;
        
        do {
            if (inputString.charAt(i) == '\\') {
                if (i < inputString.length() - 1) {
                    switch(inputString.charAt(i + 1)) {
                        case '0': /* Null */
                            outputString += 0x00; 
                            break; 
			case 'E': /* End of Transmission */
                            outputString += 0x04; 
                            break; 
			case 'a': /* Bell */
                            outputString += 0x07; 
                            break; 
			case 'b': /* Backspace */
                            outputString += 0x08; 
                            break; 
			case 't': /* Horizontal tab */
                            outputString += 0x09; 
                            break; 
			case 'n': /* Line feed */
                            outputString += 0x0a; 
                            break; 
			case 'v': /* Vertical tab */
                            outputString += 0x0b; 
                            break; 
			case 'f': /* Form feed */
                            outputString += 0x0c; 
                            break; 
			case 'r': /* Carriage return */
                            outputString += 0x0d; 
                            break; 
			case 'e': /* Escape */
                            outputString += 0x1b; 
                            break; 
			case 'G': /* Group Separator */
                            outputString +=0x1d; 
                            break; 
			case 'R': /* Record Separator */
                            outputString += 0x1e;
                            break; 
			default:    
                            outputString += '\\';
                            outputString += inputString.charAt(i); 
                            break;
                    }
                    i += 2;
                } else {
                    outputString += '\\';
                    i++;
                }
            } else {
                outputString += inputString.charAt(i);
                i++;
            }
        } while(i < inputString.length());
        
        return outputString;
    }
    
    private static void makeBarcode(Settings settings, String inputData) {
        Barcode barcode = new Barcode();
        String symbology;
        int type = settings.getSymbolType();
        boolean letThrough = false;
        
        int[] openPorts = {
            1, 2, 3, 4, 6, 7, 8, 9, 13, 18, 20, 21, 22, 23, 24, 25, 29, 30,
            31, 32, 34, 37, 40, 47, 50, 51, 52, 53, 55, 56, 57, 58, 60, 63,
            66, 67, 68, 70, 71, 76, 77, 79, 80, 81, 82, 84, 85, 86, 87, 89,
            90, 92, 97, 98, 99, 102, 104, 106, 108, 112, 128, 129, 130, 131,
            132, 133, 134, 135, 136, 137, 138, 139, 140, 141, 142
        };
        
        for(int i = 0; i < openPorts.length; i++) {
            if (type == openPorts[i]) {
                /* Check which symbologies are allowed */
                letThrough = true;
            }
        }
        
        if (letThrough == false) {
            System.out.println("Invalid barcode type");
            return;
        }
        
        // FIXME: What happened to Code 23 support?
        if (type == 129) {
            System.out.println("Code 23 not supported, yet!");
            return;
        }
        
        //FIXME: Now set up all the other options.
        
        symbology = tBarcodeType[type - 1];
        barcode.encode(symbology, inputData);
        
        System.out.println("Using barcode type " + type + " = " + symbology);
        
        //FIXME: Now do something with it!
    }
}
