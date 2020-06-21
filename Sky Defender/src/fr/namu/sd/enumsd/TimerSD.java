package fr.namu.sd.enumsd;

public enum TimerSD {
	  		  			  
			  PVP(2100, "PVP"),			  
			  BORDER_BEGIN(3600, "Dde la Bordure"),
			  DAY_DURATION(300, "Durdu Jour/Nuit"),
			  
			  ;
			  private int value;
			  
			  private final String appearance;
			  
			  TimerSD(int value, String appearance) {
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