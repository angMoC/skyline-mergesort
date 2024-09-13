/**
 * Clase que representa un par de coordenadas (x, y).
 */
public class Coordenadas {
    private int x;
    private int y;

    /**
     * Constructor de la clase Coordenadas.
     * @param x Valor de la coordenada x.
     * @param y Valor de la coordenada y.
     */
    public Coordenadas(int x, int y){
        this.x = x;
        this.y = y;
    }

    /**
     * Método para obtener el valor de la coordenada x.
     * @return El valor de la coordenada x.
     */
    public int getX() {
        return x;
    }

    /**
     * Método para obtener el valor de la coordenada y.
     * @return El valor de la coordenada y.
     */
    public int getY() {
        return y;
    }
}
