package gdp2.pv.model;

import java.io.Serializable;
import java.util.UUID;

public class Profil implements Serializable{

	/**
	 * 
	 */
	// private static final long serialVersionUID = 1L;
	// Altersgruppe, Geschlecht, Interessen, Wohnort/Region, Foto
	private UUID uuid;
	private String name;
	private int alter;
	private GeschlechtType geschlecht;
	private SexualitaetType sexualitaet;
	private String interessen;
	private String beruf;
	private String wohnort;
	private String profilBildUrl;
	
	
	// TODO: construcotr für Profil class
	public Profil(
		UUID uuid, 
		String name,
		int alter,
		GeschlechtType geschlecht,
		SexualitaetType sexualitaet,
		String interessen,
		String beruf,
		String wohnort,
		String profilBildUrl
	) {
		this.uuid = uuid;
		this.name = name;
		this.alter = alter;
		this.geschlecht = geschlecht;
		this.sexualitaet = sexualitaet;
		this.interessen = interessen;
		this.beruf = beruf;
		this.wohnort = wohnort;
		this.profilBildUrl = profilBildUrl;
	} 
	
	
	public UUID getUUID() {
		return uuid;
	}

	public void setUUID(UUID uuid) {
		this.uuid = uuid;
	}
	
	
	// name
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name; 
	}
	
	
	// alter
	public int getAlter() {
		return this.alter;
	}
	
	
	public void setAlter(int alter) {
		this.alter = alter;
	}
	
	
	// geschlecht
	public GeschlechtType getGeschlecht() {
		return this.geschlecht;
	}
	
	public void setGeschlecht(GeschlechtType geschlecht) {
		this.geschlecht = geschlecht;
	}
	
	
	// sexualität
	public SexualitaetType getSexualitaet() {
		return this.sexualitaet;
	}
	
	public void setSexualitaet(SexualitaetType sexualitaet) {
		this.sexualitaet = sexualitaet;
	}
	
	
	// interessen
	public String getInteressen() {
		return this.interessen;
	}
	
	
	public void setInteressen(String interessen) {
		this.interessen = interessen;
	}
	
	
	// beruf
	public String getBeruf() {
		return this.beruf;
	}
	
	
	public void setBeruf(String beruf) {
		this.beruf = beruf;
	}
	
	
	// wohnort
	public String getWohnort() {
		return this.wohnort;
	}
	
	
	public void setWohnort(String wohnort) {
		this.wohnort = wohnort;
	}
	
	
	// profilBildUrl;
	public String getProfilBildUrl() {
		return this.profilBildUrl;
	}
	
	
	public void setProfilBildUrl(String profilBildUrl) {
		this.profilBildUrl = profilBildUrl;
	}
	
	
	
	@Override
	public String toString() {
		return this.uuid + ", " + this.name + ", " + this.alter + ", " + this.geschlecht  + ", "+ this.sexualitaet + ", " + this.interessen+ ", " +this.beruf;
	}
	
	
	
	
}
