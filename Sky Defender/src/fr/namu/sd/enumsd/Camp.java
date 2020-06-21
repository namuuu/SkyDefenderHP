package fr.namu.sd.enumsd;

public enum Camp {
	ATTAQUE("ßcAttaquant", 9), 
	DEFENSE("ß9DÈfenseurs", 9),
	
	RED("ß4…quipe Rouge", 9),
	ORANGE("ß6…quipe Orange", 9),
	YELLOW("ße…quipe Jaune", 9),
	GREEN("ßa…quipe Verte", 9),
	AQUA("ßb…quipe Cyan", 9),
	PINK("ßd…quipe Rose", 9),
	PURPLE("ß5…quipe Mauve", 9),
	
	NULL("ßfAucune …quipe", 99);
	
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
