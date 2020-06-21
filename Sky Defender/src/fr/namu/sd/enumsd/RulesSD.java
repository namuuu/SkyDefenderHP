package fr.namu.sd.enumsd;

public enum RulesSD {
	
	DIAMOND_ARMOR_NB("Nombre maximal de pièces en diamant autorisées", 4),
	DIAMOND_SHARPNESS("Limite d'enchantement sur les épées en diamant", 2),
	DIAMOND_PROTECTION("Limite d'enchantement sur les armures en diamant", 2),
	
	IRON_SHARPNESS("Limite d'enchantement sur les épées en fer", 3),
	IRON_PROTECTION("Limite d'enchantement sur les armures en fer", 3),
	
	BOW_POWER("Limite d'enchantement sur les Arcs", 3),
	
	FINAL_HEAL("§eFinal Heal", 0),
	XP_BOOST("§eBoost d'XP", 5),
	;
	
	private final String appearance;
	
	private int value;
	
	RulesSD(String appearance, int value) {
		this.value = value;
		this.appearance = appearance;
	}
	
	public String getAppearance() {
		return this.appearance;
	}
	
	public int getValue() {
		return this.value;
	}
	
	public void setValue(int value) {
		this.value = value;
	}
}
