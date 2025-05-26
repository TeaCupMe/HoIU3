public class MapTile  {
    String representation;
    String[] decorations = new String[2];

    MapTile(String representation) {
        this.representation = representation;
        decorations[0] = "";
        decorations[1] = "";
    }

    public String toString() {
        return decorations[0] + representation + decorations[1];
    }
}
