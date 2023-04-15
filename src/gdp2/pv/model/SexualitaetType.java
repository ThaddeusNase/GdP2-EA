package gdp2.pv.model;

import java.util.Arrays;

public enum SexualitaetType {
	
	HETEROSEXUELL("heterosexuell"),
	HOMOSEXUELL("homosexuell");

	
	private final String displayName;
	
	SexualitaetType(String displayName) {
		this.displayName = displayName;
	}
	
	public String getDisplayName() {
		return this.displayName;
	}
	
	
	
	/**
	 * helper function to determin wether or not a given value is a enum value (HETEROSEXUELL, HOMOSEXUELL)
	 * @param value
	 * @return
	 * @throws SexualitaetException when value is not found in the enum values (HETEROSEXUELL, HOMOSEXUELL)
	 */
	public static SexualitaetType findByValue(String value) throws SexualitaetException {
		
		SexualitaetType result = null;
	    for (SexualitaetType sexualitaet : values()) {
	        if (sexualitaet.toString().equalsIgnoreCase(value)) {
	            result = sexualitaet;
	            break;
	        }
	    }
	    if (result == null ) {
	    	throw new SexualitaetException("value "+ value + " not found in the enum values: "+ Arrays.toString(SexualitaetType.values())); 	
	    }
	    return result;
	}


}
