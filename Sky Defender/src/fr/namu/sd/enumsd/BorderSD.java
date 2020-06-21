package fr.namu.sd.enumsd;

public enum BorderSD {
	  BORDER_MAX(2000, "Taille Bordure Initial"),
	  BORDER_MIN(300, "Taille Bordure Finale");
	  
	  private String appearance;
	  
	  private int value;
	  
	  BorderSD(int value, String appearance) {
	    this.value = value;
	    this.appearance = appearance;
	  }
	  
	  public int getValue() {
	    return this.value;
	  }
	  
	  public void setValue(int value) {
		  this.value = value;
	  }
	  
	  public String getAppearance() {
	    return this.appearance;
	  }
	  
	}

