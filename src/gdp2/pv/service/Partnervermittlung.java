package gdp2.pv.service;

import gdp2.pv.model.Profil;
import javax.swing.JFileChooser;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

import java.io.Closeable;
import java.io.Console;
import java.io.IOException;
import java.util.UUID;
import java.io.File;


/** Service-Klasse Partnervermittlung zur Verwaltung und zum Matching von Profilen
 */ 
public class Partnervermittlung {
	
	private static final int MAX_PROFILE = 100;

	private Profil[] profile = new Profil[MAX_PROFILE];	//Array zum Speichern der Profile

	private int zaehler = 0;							//zaehler, um neue Profile an der richtigen Stelle abzulegen

	
	/** Nimmt ein Profil entgegen und traegt es in den Profil-Container ein.
      * @param profil Das zu speichernde Profil.
	 */ 
	public void profilEintragen(Profil profil) {
			
		if (zaehler >= profile.length - 1) 	//noch Platz vorhanden?
			return;							//wenn nein, Methode verlassen

			//Profil eintragen und danach zaehler inkrementieren
			profile[zaehler++] = profil;

	}

	
	/** Ermittelt das Profil mit der uebergebenen UUID, sofern vorhanden.
	  * @param uuid Die UUID des gesuchten Profils.
	  * @return Das Profil mit der uebergebenen UUID. null, falls UUID nicht existent.
	 */ 
	public Profil profilSuchen(UUID uuid) {
		//Profile maximal bis zum Ende durchlaufen
		for (Profil profil : profile) {
			if (profil != null && profil.getUUID().compareTo(uuid) == 0) {	//gefunden
				return profil;												//Profil zurueckgeben
			}
		} //for
		return null;	//nicht gefunden
	}
	

	/** Loescht das Profil mit der uebergebenen UUID, sofern vorhanden.
	  * @param uuid Die UUID des zu loeschenden Profils.
	  * @return true, falls Loeschen erfolgreich, sonst false.
	 */ 
	public boolean profilLoeschen(UUID uuid) {
		//Profile maximal bis zum Ende durchlaufen
		for (int i = 0; i < profile.length; i++) {
			if (profile[i] != null && profile[i].getUUID().compareTo(uuid) == 0) {	//gefunden
				profile[i] = null;		//loeschen
				return true;
			}
		} //for
		return false;	//nichts geloescht
	}
	

	/** Gibt eine Liste mit allen Profilen als String zurueck.
	 * @return Die Profile als String. null, falls keine Profile vorhanden
	 */ 
	public String gibProfileAlsString() {

		String profileText = "";

		//Jedes Profil zum Rueckgabestring hinzufuegen
		//alternative Implementierung der Methode:
		int i = 1;  
		for (Profil profil : profile) {
			if (profil != null) {
				profileText += (i++) + ". " + profil.toString() + "\n";
			}
		} //for
		return profileText.equals("") ? null : profileText;
	}
	
	
	/** Speichert die Profile in einer Datei. 
	 * @return true, falls Speicherung erfolgreich, false sonst
	 */
	public boolean profileSpeichern() {
		// Erstelle File-Dialog
		System.out.println("executed3");
	    JFileChooser fileChooser = new JFileChooser();
	    fileChooser.setDialogTitle("Speichern unter");
	    
	    // Setze den Start-Ordner auf den aktuellen Ordner
	    File currentDir = new File(".");
	    fileChooser.setCurrentDirectory(currentDir.getAbsoluteFile());
	    
	    // Zeige den Dialog an und warte auf die Benutzeraktion
	    int result = fileChooser.showSaveDialog(null);
	    System.out.println("executed4");
	    if (result == JFileChooser.APPROVE_OPTION) { // Wenn der Benutzer auf "Speichern" klickt
	        try {
	            File selectedFile = fileChooser.getSelectedFile();
	            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(selectedFile));
	            try {
					for (int i = 0; i < profile.length; i++) {
						out.writeObject(profile[i]);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            out.close();
	            System.out.println("Profile abgelegt unter:" + selectedFile);
	            return true;
	        } catch (IOException e) {
	            e.printStackTrace();
	            System.out.println("executed ERROR oof");
	            return false;
	            
	        }
	    } else { // Wenn der Benutzer auf "Abbrechen" klickt oder das Fenster schließt
	        return false;
	    }
	}
	
	
	/** Laedt Profile aus einer Datei. 
	 * @return true, falls Laden erfolgreich, false sonst
	 */
	public boolean profileLaden() {
		alleProfileLoeschen();
		
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("."));
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                FileInputStream fileIn = new FileInputStream(selectedFile);
                ObjectInputStream objectIn = new ObjectInputStream(fileIn);
                int i = 0;
                while (true) {
                	if (i >= this.profile.length) break;
                	Profil profil = (Profil) objectIn.readObject();
                	this.profile[i] = profil;
                	if (profil != null) System.out.println(i+") Profil name: " + profil.getName());
                	i++;
                }
                objectIn.close();
                
                return true;
            } catch (Exception ex) {
                ex.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
	}
	

	/** Loescht alle Profile aus dem Datencontainer.
	 * Wird vor dem Laden aus einer Datei benoetigt, um Altdaten aus dem Container zu entfernen.
	 */
	public void alleProfileLoeschen() {
		for (int i = 0; i < profile.length; i++) {
			profile[i] = null;
		}
	}
	
	
	/** Gibt Informationen zur Datei der Objektserialisierung (sofern vorhanden) als String zur�ck.
	 * @return Die Dateiinfos als String
	 */
	public String gibDateiInfos(File datei) {

    	String dateiInfos = "";
        if (datei != null && datei.exists()) {
	        dateiInfos += "\nInformationen zu " + datei.getName() + ":\n\n";
	        // ## to do ##
        } else {
        	dateiInfos += "Datei nicht gefunden.\n";
        }
        return dateiInfos;
	}

		
	/** Hilfsmethode zum Schliessen von Streams, die von den Speichern/Laden-Methoden aufgerufen wird.
	 *  Das Schliessen kann in Ausnahmefaellen eine IOException ausloesen, die aber so unwahrscheinlich ist
	 *  (Platte voll, ...), dass sie nicht behandelt wird.
	 * @param closeable der zu schliessende Stream
	 */
	private void closeQuietly(Closeable closeable) {
	    if (closeable != null) {
	        try {
	            closeable.close();
	        } catch (IOException ex) {
	            // ignore
	        }
	    }
	}
	
}