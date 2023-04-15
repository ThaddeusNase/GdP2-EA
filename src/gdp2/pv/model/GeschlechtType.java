package gdp2.pv.model;

import java.util.Arrays;

public enum GeschlechtType {

	
	MAENNLICH("maenlich"),
	WEIBLICH("weiblich"),
	DIVERS("divers");

	
	private final String displayName;
	
	GeschlechtType(String displayName) {
		this.displayName = displayName;
	}
	
	public String getDisplayName() {
		return this.displayName;
	}
	
	
	/**
	 * helper function to determin wether or not a given value is a enum value (MAENNLICH, WEIBLICH, DIVERS)
	 * @param value
	 * @return
	 * @throws GeschlechtException when value is not found in the enum values (MAENNLICH, WEIBLICH, DIVERS )
	 */
	public static GeschlechtType findByValue(String value) throws GeschlechtException {
		
		GeschlechtType result = null;
	    for (GeschlechtType geschlecht : values()) {
	        if (geschlecht.toString().equalsIgnoreCase(value)) {
	            result = geschlecht;
	            break;
	        }
	    }
	    if (result == null ) {
	    	throw new GeschlechtException("value "+ value + " not found in the enum values: "+ Arrays.toString(GeschlechtType.values())); 	
	    }
	    return result;
	}
	
}
