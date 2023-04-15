package gdp2.pv.view;

import gdp2.pv.model.GeschlechtException;
import gdp2.pv.model.GeschlechtType;
import gdp2.pv.model.Profil;
import gdp2.pv.model.SexualitaetException;
import gdp2.pv.model.SexualitaetType;
import gdp2.pv.service.Partnervermittlung;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;
import java.util.UUID;


/** Konsolen-UI fuer Partnervermittlung
*/
public class PartnervermittlungUI {
		
	/** Liest Profildaten von der Standardeingabe ein, speichert diese in einem 
	 * Profil-Objekt und gibt das Objekt zurueck.
	 * @return Das erfolgreich erfasste Profil, null im Fehlerfall.
	 * @throws IOException 
	 */ 
	public static Profil profilErfassen() throws IOException {
		
		try {
			//Profildaten von der Konsole einlesen
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

			// name input
			System.out.println("tippe name ein & drücke danach enter: ");
			String name = br.readLine();

			// alter input
			System.out.println("tippe age ein & drücke danach enter: ");
			int alter = Integer.parseInt(br.readLine());
			
			// geschlecht input
			System.out.println("tippe geschlecht ein & drücke danach enter: " + Arrays.toString(GeschlechtType.values()));
			GeschlechtType geschlecht = GeschlechtType.findByValue(br.readLine());
			
			// sexualitaet input
			System.out.println("tippe geschlecht ein & drücke danach enter: " + Arrays.toString(SexualitaetType.values()));
			SexualitaetType sexualitaet = SexualitaetType.findByValue(br.readLine());
			
			// interessen input;
			System.out.println("tippe interessen ein & drücke danach enter: ");
			String interessen = br.readLine();
			
			// beruf
			System.out.println("tippe beruf ein & drücke danach enter: ");
			String beruf = br.readLine();
			
			// beruf
			System.out.println("tippe wohnort ein & drücke danach enter: ");
			String wohnort = br.readLine();
			
			// profilebild url
			String defaultProfilBildUrl = "https://us.123rf.com/450wm/photoplotnikov/photoplotnikov1703/photoplotnikov170300032/74051448-standard-m%C3%A4nnlich-avatar-profilbild-symbol-grauer-mann-foto-platzhalter-vektor-illustration.jpg";
			
			System.out.println(
					"\n" +
					"------ ZUSAMMENFASSUNG ------" + "\n" +
					"userName: "+ name + "\n" + 
					"alter: "+ alter + "\n" +
					"geschlecht: " + geschlecht.getDisplayName() +"\n" + 
					"sexualität: "+ sexualitaet.getDisplayName() + "\n" +
					"interessen: " + interessen + "\n" +
					"beruf: " + beruf + "\n" +
					"wohnort: " + wohnort + "\n" +
					"profilbild url: " + defaultProfilBildUrl
			);
			
			UUID uuid = UUID.randomUUID();		//ID fuer das Profil erzeugen
			
			Profil neuesProfil = new Profil(
				uuid,
				name,
				alter,
				geschlecht,
				sexualitaet,
				interessen,
				beruf,
				wohnort,
				defaultProfilBildUrl
			);
			
			return neuesProfil;						
		}

		catch (IOException e) {
			System.err.println("Fehler bei der Dateneingabe: " + e.getMessage());
			return null;
		}

		catch (NumberFormatException e) {
			System.err.println("Fehler bei der Zahleneingabe: " + e.getMessage());
			return null;
		}
		catch (GeschlechtException e) {
			System.out.println(e.getMessage());
			return null;
		}
		catch (SexualitaetException e) {
			System.out.println(e.getMessage());
			return null;
		}
		
	}

	
	/** Zeigt das Hauptmenue und Untermenues an, gibt Eingaben an die Service-Klasse Partnervermittlung
	 * weiter, nimmt deren Ausgaben entgegen und zeigt sie an.
	 * @param pv Partnervermittlung-Objekt, mit dem kommuniziert wird.
	 * @throws IOException 
	 */
	public static void zeigeMenue(Partnervermittlung pv) throws IOException {
		boolean ok;				//Rueckgabewert von Lese-/Schreiboperationen
		boolean ende = false;	//Abbruchbedingung fuer die Menueschleife
		int ziffer;				//fuer Menueauswahl
		Profil profil;			//fuer die Zwischenspeicherug von erfassten Profilen
		String uuidString;
		UUID uuid;

		if (pv == null) return;	//ohne Service-Objekt geht nichts
		
		//BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		//alternativ mit java.util.Scanner:			
		Scanner sc = new Scanner(System.in);

		//solange nicht Programmende gewaehlt, nach jeder Aktion wieder Menue anzeigen
		while (!ende) {
			System.out.println("\n(1) Profil erfassen");
			System.out.println("(2) Profil suchen");
			System.out.println("(3) Profil loeschen");
			System.out.println("(4) Profile ausgeben");
			System.out.println("(5) Profile speichern");
			System.out.println("(6) Profile laden");
			System.out.println("(7) Programm beenden");

			try {
				//ziffer = Integer.parseInt(br.readLine());	//Menueauswahl einlesen
				ziffer = Integer.parseInt(sc.nextLine());	//Menueauswahl einlesen

				switch (ziffer) {					
					case 1: //Profil erfassen
							profil = profilErfassen();
							if (profil != null) {
								pv.profilEintragen(profil);
								System.out.println("Erfassung erfolgreich.");
							}
							break;
							
					case 2: //Profil anhand seiner UUID suchen
							ok = false;
							do {
								System.out.println("Profil-UUID (nur Return -> zurueck): ");
								//uuidString = br.readLine();
								uuidString = sc.nextLine();
								if (uuidString.equals("")) break;	//zurueck zum Menue
								try {
									uuid = UUID.fromString(uuidString);
									ok = true;

									profil = pv.profilSuchen(uuid);
									if (profil != null) {
										System.out.println(profil.toString());
									} else {
										System.out.println("Nicht gefunden.");
									}
								} catch (IllegalArgumentException e) {
									System.err.println("Fehler: " + e.getMessage()); 
								}
							} while (!ok);
							
							break;
							
					case 3: //Profil anhand seiner UUID loeschen
							ok = false;
							do {
								System.out.println("Profil-UUID (nur Return -> zurueck): ");
								//uuidString = br.readLine();
								uuidString = sc.nextLine();
								if (uuidString.equals("")) break;	//zurueck zum Menue
								try {
									uuid = UUID.fromString(uuidString);

									ok = pv.profilLoeschen(uuid);
									if (ok) {
										System.out.println("Erfolgreich geloescht.");
									} else {
										System.out.println("Loeschen fehlgeschlagen.");
									}
								} catch (IllegalArgumentException e) {
									System.err.println("Fehler: " + e.getMessage()); 
								}
							} while (!ok);

							break;
						
					case 4: //Liste aller Profile ausgeben
							String profileAlsString = pv.gibProfileAlsString();
							if (profileAlsString != null) {
								System.out.println(profileAlsString);
							} else {
								System.out.println("Keine Profile gefunden.");
							}
							break;
							
					case 5: //Profile speichern
							ok = pv.profileSpeichern();
							if (ok) {
								System.out.println("Speicherung erfolgreich.");
							} else {
								System.out.println("Fehler beim Speichern.");
							}
							break;
							
					case 6: //Profile laden
							pv.alleProfileLoeschen();		//zunaechst Altdaten entfernen
							ok = pv.profileLaden();
							if (ok) {
								System.out.println("Laden erfolgreich.");
							} else {
								System.out.println("Fehler beim Laden.");
							}
							break;

					case 7: System.out.println("Programm wird beendet.");
							ende = true; 
							break;
							
					default: System.out.println("Keine gueltige Eingabe.");
				} //switch
			} catch (NumberFormatException e) {
				System.err.println("Keine gueltige Eingabe.");
			}
		} //while
		//br.close();
		sc.close();
	}

	/** main-Routine zur Menueanzeige fuer die Partnervermittlung.
	  * @param args Kommandozeilenparameter, derzeit nicht verwendet
	 * @throws IOException 
	 */ 
	public static void main(String[] args) throws IOException {
		//Service-Objekt erzeugen
		Partnervermittlung pv = new Partnervermittlung();
		
		//Menue zur User-Interaktion mit der Partnervermittlung anzeigen
		zeigeMenue(pv);
	}
}