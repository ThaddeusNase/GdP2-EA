package model;

import java.time.LocalDate;
import java.util.UUID;
import java.io.Serializable;

public class Profil implements Serializable{
	
	//default serialVersionUID
	private static final long serialVersionUID = 1L;
	
	private UUID uuid;
	//partnerschaftsrelevante Eigenschaften
	private String name;
	private LocalDate dayOfBirth;
	private Gender gender;
	private String orientation;
	private String location;
	private String profession;
	private String[] hobbies = new String[10];
	private int hobbyZaehler = 0;
	
	public Profil(UUID uuid, String name) {
		this.uuid = uuid;
		this.name = name;
	}
	
	/**
	 * Getter-Methode UUID
	 * @return uuid
	 */
	public UUID getUUID() {
		return uuid;
	}
	
	/**
	 * Setter-Methode UUID
	 * @param uuid UUID
	 */
	public void setUUID(UUID uuid) {
		this.uuid = uuid;
	}
	
	/**
	 * Getter-Methode name
	 * @return name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Setter-Methode name
	 * @param name Name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Getter-Methode dayOfBirth
	 * @return dayOfBirth
	 */
	public LocalDate getDayOfBirth() {
		return dayOfBirth;
	}
	
	/**
	 * Setter-Methode dayOfBirth
	 * @param year Jahr
	 * @param month Monat
	 * @param day Tag
	 */
	public void setDayOfBirth(int year, int month, int day) {
		dayOfBirth = LocalDate.of(year, month, day);
	}
	
	/**
	 * Getter-Methode gender
	 * @return gender
	 */
	public Gender getGender() {
		return gender;
	}
	
	/**
	 * Setter-Methode gender, moegliche Eingaben m,w oder d
	 * @param gender Geschlecht
	 */
	public void setGender(char g) {
		
		switch (g) {
		case 'm':
			gender = Gender.MAENNLICH;
			break;
		case 'w':
			gender = Gender.WEIBLICH;
			break;
		case 'd':
			gender = Gender.DIVERS;
			break;
		default:
			System.out.println("Gueltige Eingaben sind die Literale m, w, oder d");
			break;
		}
	}
	
	/**
	 * Getter-Methode orientation
	 * @return orientation
	 */
	public String getOrientation() {
		return orientation;
	}
	
	/**
	 * Setter-Methode orientation
	 * @param orientation
	 */
	public void setOrientation(String orientation) {
		this.orientation = orientation;
	}
	
	/**
	 * Getter-Methode location
	 * @return location
	 */
	public String getLocation() {
		return location;
	}
	
	/**
	 * Setter-Methode location
	 * @param location
	 */
	public void setLocation(String location) {
		this.location = location;
	}
	
	/**
	 * Getter-Methode profession
	 * @return profession
	 */
	public String getProfession() {
		return profession;
	}
	
	/**
	 * Setter-Methode profession
	 * @param profession
	 */
	public void setProfession(String profession) {
		this.profession = profession;
	}
	
	/**
	 * Getter-Methode hobbies
	 * @return hobbies
	 */
	public String[] getHobbies() {
		return hobbies;
	}
	
	/**
	 * Setter-Methode hobbies, maximal 10 moeglich
	 * @param h Hobby-Array
	 */
	public void setHobbies(String hobby) {
		
		if (hobbyZaehler == 10 ) {
			System.out.println("Es koennen maximal 10 Hobbys angegeben werden");
		}
		else {
			hobbies[hobbyZaehler] = hobby;
			hobbyZaehler++;
		}
	}
	
	/**
	 * Hilfsmethode um Inhalte des String-Arrays hobbies auszugeben
	 * @return hobbyList
	 */
	public String printHobbies() {
		String hobbyList = "";
		//Zaehler zur Ausgabe-Strukturierung
		int i = 0;
		for(String hobby : hobbies) {
			if (hobby == null) {
				continue;
			}
			else {
				hobbyList += (i++) + ". " + hobby + "\n";
			}
		}
		return hobbyList;
	}
	
	@Override
	public String toString() {
		return this.name + " ist " + this.gender + " und " + this.dayOfBirth + " Jahre alt " + ", hat die Orientierung " +
				this.orientation + ", wohnt in " + this.location + ", arbeitet als " + this.profession +
				" und hat folgende Hobbies " + this.printHobbies() + ".";
	}
}
