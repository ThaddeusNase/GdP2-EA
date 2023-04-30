package view;

import model.Profil;
import service.Partnervermittlung;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.UUID;


/** Konsolen-UI fuer Partnervermittlung
*/
public class PartnervermittlungUI {
		
	/** Liest Profildaten von der Standardeingabe ein, speichert diese in einem 
	 * Profil-Objekt und gibt das Objekt zurueck.
	 * @return Das erfolgreich erfasste Profil, null im Fehlerfall.
	 */ 
	public static Profil profilErfassen() {
		
		try {
			//Zwischenspeicher fuer Profil und Eingabe
			Profil profil;
			String eingabe;
						
			UUID uuid = UUID.randomUUID();		//ID fuer das Profil erzeugen
						
			//Profildaten von der Konsole einlesen
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			
			//Namen einlesen und neues Profil-Objekt erstellen
			System.out.println("Hi! Du willst ein Profil anlegen?\nDann verrate mir zuerst deinen Namen:\n");
			eingabe = stringEingabetesten(br.readLine());
			profil = new Profil(uuid, eingabe);
			
			//Geburtsdatum einlesen
			System.out.println("Als naechstes brauche ich dein Geburtsdatum. In welchem Jahr bist du geboren?\n"
					+ "Bitte gib es in dem Format YYYY ein, also z.B. 1980.\n");
			int year = intEingabeTesten(br.readLine());
			System.out.println("Jetzt der Monat...\n");
			int month = intEingabeTesten(br.readLine());
			System.out.println("und der Tag...\n");
			int day = intEingabeTesten(br.readLine());
			profil.setDayOfBirth(year, month, day);
						
			//Geschlecht einlesen
			System.out.println("Welches Geschlecht soll gespeichert werden? Bitte gib m, w, oder d ein.\n");
			eingabe  = stringEingabetesten(br.readLine());
			
			//Abbruch-Variable für while-Schleife
			boolean richtig = false;
			//Eingabe kontrollieren
			while(!richtig) {
				if(eingabe.equals("m") || eingabe.equals("w") || eingabe.equals("d")) {
					richtig = true;
				}
				else {
					System.out.println("Das war keine gueltige Angabe. Es kann nur m, w oder d eingegeben werden.\n");
				}
			}
			//Type-Cast zu Char
			profil.setGender(eingabe.charAt(0));
			
			//Orientierung einlesen
			System.out.println("Mit welcher sexuellen Orientierung identifizierst du dich?\n"
					+ "Falls du es nicht verraten willst schreibe \"keine Angabe\".\n");
			eingabe  = stringEingabetesten(br.readLine());
			profil.setOrientation(eingabe);
			
			//Wohnort einlesen
			System.out.println("Welcher Ort soll in deinem Profil angegeben werden?\n"
					+ "Falls du es nicht verraten willst schreibe \"keine Angabe\".\n");
			eingabe  = stringEingabetesten(br.readLine());
			profil.setLocation(eingabe);
			
			//Beruf einlesen
			System.out.println("Was ist dein Beruf?\nFalls du es nicht verraten willst schreibe \"keine Angabe\".\n");
			eingabe  = stringEingabetesten(br.readLine());
			profil.setProfession(eingabe);
			
			//Hobbys einlesen
			System.out.println("Welche Bobbys hast du? Du kannst bist zu 10 angeben.\n"
					+ "Falls du es nicht verraten willst schreibe \"keine Angabe\".\n");
			//Zaehlvariable fuer Hobbys
			int zaehler = 1;
			
			//sukzessive Hobbys einlesen
			while(zaehler < 11) {
				System.out.println("So, fangen wir mal an. Falls du mit der Angabe fertig bist schreibe \"stop\".\n"
						+ "Nenne mir Hobby Nummer " + zaehler + ":\n");
				eingabe  = stringEingabetesten(br.readLine());
				//wurde Eingabe mit stop abgebrochen
				if(eingabe.equals("stop"))
					break;
				//Hobbys angegeben
				else{
					profil.setHobbies(eingabe);
					zaehler++;
				}
			}
			System.out.println("Fertig, dein Profil ist nun vollständig angelegt.");
			
			return profil;
		}

		catch (IOException e) {
			System.err.println("Fehler bei der Dateneingabe: " + e.getMessage());
			return null;
		}
		catch (NumberFormatException e) {
			System.err.println("Fehler bei der Zahleneingabe: " + e.getMessage());
			return null;
		}
	}
	
	/**
	 * Methode, um String-Eingaben zu testen.
	 * @param eingabe Eingabe
	 * @return getestete String-Eingabe
	 */
	
	public static String stringEingabetesten(String eingabe) {
		try {
			//Eingaben von Konsole einlesen
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String input = eingabe;
			boolean ok = false;
		
			while (!ok) {
				if(input.equals("")) {
					System.out.println("Upps, da fehlte wohl der Text, versuche es nochmal...\n");
					input = br.readLine();
				}
				else {
					ok = true;
				}
			}
			return input;
		}
		catch(IOException e) {
			System.err.println("Fehler bei der Dateneingabe: " + e.getMessage());
			return null;
		}
	}
	
	/**
	 * Methode um Integer-Eingaben zu testen.
	 * @param eingabe Eingabe
	 * @return getestete Integer-Eingabe
	 */
	public static int intEingabeTesten(String eingabe) {
		try {
			//Eingaben von Konsole einlesen
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String input = eingabe;
			boolean ok = false;
		
			while (!ok) {
				if(eingabe.equals("")) {
					System.out.println("Upps, da fehlte wohl die Eingabe, versuche es nochmal...\n");
					input = br.readLine();
				}
				else {
					ok = true;
				}
			}
			return Integer.parseInt(input);
		}
		catch(IOException e) {
			System.err.println("Fehler bei der Dateneingabe: " + e.getMessage());
			return 0;
		}
		catch(NumberFormatException e) {
			System.err.println("Fehler bei der Zahleneingabe: " + e.getMessage());
			return 0;
		}
	}
	
	/** Zeigt das Hauptmenue und Untermenues an, gibt Eingaben an die Service-Klasse Partnervermittlung
	 * weiter, nimmt deren Ausgaben entgegen und zeigt sie an.
	 * @param pv Partnervermittlung-Objekt, mit dem kommuniziert wird.
	 */
	public static void zeigeMenue(Partnervermittlung pv) throws IOException{
		boolean ok;				//Rueckgabewert von Lese-/Schreiboperationen
		boolean ende = false;	//Abbruchbedingung fuer die Menueschleife
		int ziffer;				//fuer Menueauswahl
		Profil profil;			//fuer die Zwischenspeicherug von erfassten Profilen
		String uuidString;
		UUID uuid;

		if (pv == null) return;	//ohne Service-Objekt geht nichts
		
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
							String profileAlsString = null;
							String eingabe;
							int intEingabe;
							
							System.out.println("Sollen die Profile nach einem Kriterium gefiltert werden? Falls ja, tippen Sie j ein und sonst n fuer nein.");
							String filtern = sc.nextLine();
							if (filtern.equals("n")) {
								profileAlsString = pv.gibProfileAlsString();
							} else if (filtern.equals("j")) {
								System.out.println("Das Programm befindet sich noch im Aufbau, daher kann nur nach zwei Kriterien gefiltert werden.\n"
										+ "Zur Auswahl stehen:\n"
										+ "(1)Name\n"
										+ "(2)Geburtsjahr.\n"
										+ "\nTippen Sie die entsprechende Ziffer ein, um eine Auswahl zu tätigen...");
								ziffer = Integer.parseInt(sc.nextLine());
								
								switch (ziffer) {
								case 1:
									System.out.println("Gib einen Namen ein, nach dem gefiltert werden soll.");
									eingabe = sc.nextLine();
									profileAlsString = pv.filterProfile(p -> p.getName().equals(eingabe));
									break;
									
								case 2:
									System.out.println("Gib ein Geburtsjahr ein, nach dem gefiltert werden soll.");
									intEingabe = Integer.parseInt(sc.nextLine());
									profileAlsString = pv.filterProfile(p -> (intEingabe == p.getDayOfBirth().getYear()));
									break;
								
								default: System.out.println("Keine gueltige Eingabe.");
								}//switch							
							}//else if
							
							if (profileAlsString != null) {
								System.out.println(profileAlsString);
							} else {
								System.out.println("Keine Profile gefunden.");
							}
							break;
							
					case 5: //Profile speichern
														
							//Dateinamen einlesen
							System.out.println("Bitte gib den Namen für die alte oder neue Datei ein.");
							String dateiName = sc.nextLine();
							
							//Pfad einlesen
							System.out.println("Bitte gib den Pfad für die alte oder neue Datei ein. Achte bitte darauf das Zeichen,\n"
									+ "dass auf deinem System zum trennen von Verzeichnissen genutzt wird - in Windows bspw. \\ - \n"
									+ "immer doppelt einzugeben, also so:\n"
									+ "C:\\\\Pfadzeichen\\\\immer\\\\doppelt");
							String pfad = sc.nextLine();
							
							ok = pv.profileSpeichern(pfad, dateiName);
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

					case 7: 
							pv.profileSpeichern(System.getProperty("user.dir") + "\\letzteZugriffe", "letzterZugriff.txt");
							System.out.println("Programm wird beendet.");
							ende = true; 
							break;
							
					default: System.out.println("Keine gueltige Eingabe.");
				} //switch
			}
			catch (NumberFormatException e) {
				System.err.println("Keine gueltige Eingabe.");
			}
		} //while
		sc.close();
		
	}

	/** main-Routine zur Menueanzeige fuer die Partnervermittlung.
	  * @param args Kommandozeilenparameter, derzeit nicht verwendet
	 */ 
	public static void main(String[] args) throws IOException{
		//Service-Objekt erzeugen
		Partnervermittlung pv = new Partnervermittlung();
		
		//Menue zur User-Interaktion mit der Partnervermittlung anzeigen
		zeigeMenue(pv);
	}
}