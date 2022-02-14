package skoczki;

public enum Color {
    BLACK,
    WHITE;

    public Color opposite(){
        if (this.equals(BLACK))return WHITE;
        return BLACK;
    }

    @Override
    public String toString() {
        if (this == BLACK) return "Black";
        return "White";
    }
}
