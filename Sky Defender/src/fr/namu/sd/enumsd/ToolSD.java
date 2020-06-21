package fr.namu.sd.enumsd;

public enum ToolSD {
	RANDOM_TEAM(Boolean.valueOf(false), "�e�quipes al�atoires"),
	MULTIPLE_TEAM(Boolean.valueOf(true), "�eMultiples �quipes"),
	PREY(Boolean.valueOf(false), "�eProies des d�fenseurs"),
	  ;
	  
	  private Boolean value;
	  
	  private final String appearance;
	  
	  ToolSD(Boolean value, String appearance) {
	    this.value = value;
	    this.appearance = appearance;
	  }
	  
	  public Boolean getValue() {
	    return this.value;
	  }
	  
	  public void setValue(Boolean value) {
		  this.value = value;
	  }
	  
	  public String getAppearance() {
	    return this.appearance;
	  }
	}
