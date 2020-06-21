package fr.namu.sd.enumsd;

public enum ScenarioSD {
	  VANILLA_PLUS(Boolean.valueOf(false), "§eVanilla+"),
	  ROD_LESS(Boolean.valueOf(false), "§eRodless"),
	  HORSE_LESS(Boolean.valueOf(false), "§eHorseless"),
	  FIRE_LESS(Boolean.valueOf(false), "§eFireless"),
	  CUT_CLEAN(Boolean.valueOf(true), "§eCutclean"),
	  DIAMOND_LIMIT(Boolean.valueOf(false), "§eDiamond Limit"),
	  FAST_SMELTING(Boolean.valueOf(true), "§eFastMelting"),
	  HASTEY_BOYS(Boolean.valueOf(true), "§eHasteyBoys"),
	  NO_FALL(Boolean.valueOf(false), "§eNofall"),
	  SNOWBALL(Boolean.valueOf(false), "§eBoules de Neige"),
	  POISON(Boolean.valueOf(false), "§ePoison"),
	  XP_BOOST(Boolean.valueOf(false), "§eBoost d'XP"),
	  COMPASS_TARGET_LAST_DEATH(Boolean.valueOf(false), "§eLa boussole pointe sur le lieu de la dernimort"),
	  NO_CLEAN_UP(Boolean.valueOf(false), "§eNo Clean Up"),
	  NO_NAME_TAG(Boolean.valueOf(false), "§eNo Name Tag"),
	  GOLDEN_HEAD(Boolean.valueOf(true), "§eGolden Head"),
	  CAT_EYES(Boolean.valueOf(true), "§eCat Eyes");
	  
	  private Boolean value;
	  
	  private final String appearance;
	  
	  ScenarioSD(Boolean value, String appearance) {
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

