package service;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.UUID;
import java.util.function.Predicate;

import model.Profil;


/** Service-Klasse Partnervermittlung zur Verwaltung und zum Matching von Profilen
 */ 
public class Partnervermittlung {
	
	private static final int MAX_PROFILE = 100;

	private Profil[] profile = new Profil[MAX_PROFILE];	//Array zum Speichern der Profile

	private int zaehler = 0;							//zaehler, um neue Profile an der richtigen Stelle abzulegen
	
	private static final File DATEI_LAST_SESSION = new File(System.getProperty("user.dir") + "\\letzteZugriffe\\letzterZugriff.txt"); //Referenz fuer Profile aus letzter Session
	
	private File datei = null;
	
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
	
	/**
	 * Gibt Liste mit allen Profilen als String zurueck, die dem im Parameter mitgegebenen
	 * Kriterium entsprechen.
	 * @return Die Profile als String. null, falls keine Profile vorhanden
	 */
	public String filterProfile(Predicate<Profil> tester) {
		String profileText = "";
		int i = 1;
		
		for (Profil p : profile) {
			if(p == null) {
				continue;
			} else if(tester.test(p)) {
				profileText += (i++) + ". " + p.toString() + "\n";
			}
		}
		
		return profileText.equals("") ? null : profileText;
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
	public boolean profileSpeichern(String pfad, String dateiName) {
		
		ObjectOutputStream ooStream = null;
		boolean ok;
		
		try {
			//File-Objekt um Existenz des Pfades zu testen
			File pfadTest = new File(pfad);
			
			//muessen Ordner neu angelegt werden?
			if(!pfadTest.exists()) {
				pfadTest.mkdirs();
			}
			//neue Datei in Pfad anlegen
			datei = new File(pfad + File.separatorChar + dateiName);
			datei.createNewFile();
			datei.setWritable(true);
			
			//Ausgabestrom, um Profile in Datei zu speichern
			ooStream = new ObjectOutputStream(new FileOutputStream(datei));
			
			//Profile einzeln in Datei speichern
			for (Profil profil : profile) {
				if(profil != null) {
					ooStream.writeObject(profil);
				}
			}
			//Ausgabe von Datei-Infos automatisch nach Speicherung
			System.out.println(gibDateiInfos(datei));
					
			ok = true;
		}
		catch(FileNotFoundException e) {
			System.err.println("Fehler bei der Datei-Findung: " + e.getMessage());
			ok = false;
		}
		catch(IOException e) {
			System.err.println("Fehler bei der Dateneingabe: " + e.getMessage());
			ok = false;
		}
		finally {
			closeQuietly(ooStream);
		}
		return ok;			
	}
	
	
	/** Laedt Profile aus einer Datei. 
	 * @return true, falls Laden erfolgreich, false sonst
	 */
	public boolean profileLaden() {
		boolean ok;
		ObjectInputStream oiStream = null;
		zaehler = 0; //Zaehler zuruecksetzen
		try {
			oiStream = new ObjectInputStream(new FileInputStream(DATEI_LAST_SESSION));
						
			//Profile laden und in profile schreiben
			while(oiStream.available() > 0) {
				if(zaehler >= profile.length) {
					break;
				}
				profile[zaehler] = (Profil) oiStream.readObject();
				zaehler++;
			}
			ok = true;
		}
		catch (ClassNotFoundException e) {
			System.err.println("Fehler bei der Klassen-Findung: " + e.getMessage());
			ok = false;
		}
		catch(FileNotFoundException e) {
			System.err.println("Fehler bei der Datei-Findung: " + e.getMessage());
			ok = false;
		}
		catch(IOException e) {
			System.err.println("Fehler bei der Dateneingabe: " + e.getMessage());
			ok = false;
		}
		finally {
			closeQuietly(oiStream);
		}
		return ok;
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
	        dateiInfos += "\nAbsoluter Pfad = " + datei.getAbsolutePath() + "\n";
	        dateiInfos += "\nGroesse = " + datei.length() + "\n";
	        dateiInfos += "\nzuletzt geaendert = " + datei.lastModified() + "\n";
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