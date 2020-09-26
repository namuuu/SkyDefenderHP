package fr.namu.sd.enumsd;

public enum BorderSD {
    MAX_BORDER("Grande Bordure", 2000),
    MIN_BORDER("Petite Bordure", 500),
    ;

    private final String name;

    private int value;

    BorderSD(String name, int value) {
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
