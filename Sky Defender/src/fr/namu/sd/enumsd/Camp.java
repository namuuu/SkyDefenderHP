package fr.namu.sd.enumsd;

public enum Camp {
	ATTAQUE("�cAttaquant", 9), 
	DEFENSE("�9D�fenseurs", 9),
	
	RED("�4�quipe Rouge", 9),
	ORANGE("�6�quipe Orange", 9),
	YELLOW("�e�quipe Jaune", 9),
	GREEN("�a�quipe Verte", 9),
	AQUA("�b�quipe Cyan", 9),
	PINK("�d�quipe Rose", 9),
	PURPLE("�5�quipe Mauve", 9),
	
	NULL("�fAucune �quipe", 99);
	
	private int value;
	
	private final String teamName;
	
	Camp(String teamName, int value) {
		this.teamName = teamName;
		this.value = value;
	}
	
	public int getValue() {
		return this.value;
	}
	
	public String getName() {
		return this.teamName;
	}
	
	public void setValue(int value) {
		this.value = value;
	}
}
