package skoczki;

public enum Color {
    BLACK,
    WHITE;

    public Color opposite(){
        if (this.equals(BLACK))return WHITE;
        return BLACK;
    }
}
