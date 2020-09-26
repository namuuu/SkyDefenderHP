package fr.namu.sd.enumsd;

public enum TimerSD {

    PVP("PVP", 30),
    BORDER_SHRINK("Réduction de la Bordure", 2100),
    FINAL_HEAL("Final Heal", 0),

    ;

    private final String name;

    private int value;

    TimerSD(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return this.name;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void addValue(int value) { this.value = this.value + value; }

    public int getValue() {
        return this.value;
    }
}
