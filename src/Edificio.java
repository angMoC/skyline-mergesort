import java.util.List;

/**
 * Clase que representa un edificio compuesto por una lista de coordenadas.
 */
public class Edificio {

    private List<Coordenadas> listaCoordenadas;

    /**
     * Constructor de la clase Edificio.
     * @param listaCoordenadas Lista de coordenadas que define el edificio.
     */
    public Edificio(List<Coordenadas> listaCoordenadas) {
        this.listaCoordenadas = listaCoordenadas;
    }

    /**
     * Método para obtener la lista de coordenadas del edificio.
     * @return La lista de coordenadas del edificio.
     */
    public List<Coordenadas> getListaCoordenadas() {
        return listaCoordenadas;
    }

    /**
     * Método que devuelve la cantidad de coordenadas que componen el edificio.
     * @return La cantidad de coordenadas del edificio.
     */
    public int size() {
        return listaCoordenadas.size();
    }

    /**
     * Método que extrae las abscisas (coordenadas x) de todas las coordenadas del edificio.
     * @return Un array que contiene todas las abscisas del edificio.
     */
    public int[] extraerAbcisas() {

        int[] abcisas = new int[size()];
        for(int i = 0; i < size(); i++) {
            abcisas[i] = listaCoordenadas.get(i).getX();
        }
        return abcisas;
    }

    /**
     * Método que extrae las alturas (coordenadas y) de todas las coordenadas del edificio.
     * @return Un array que contiene todas las alturas del edificio.
     */
    public int[] extraerAlturas() {

        int[] alturas = new int[size()];
        for(int i = 0; i < size(); i++) {
            alturas[i] = listaCoordenadas.get(i).getY();
        }
        return alturas;
    }

    /**
     * Método que devuelve una representación en forma de cadena del edificio.
     * Esta representación incluye todas las coordenadas del edificio en formato (x, y).
     * @return Una cadena que representa el edificio.
     */
    @Override
    public String toString() {
        StringBuilder edificio_string = new StringBuilder();
        for(Coordenadas coordenada : this.listaCoordenadas) {
            edificio_string.append("(" + coordenada.getX() + "," + coordenada.getY() + ")");
        }
        return edificio_string.toString();
    }

    /**
     * Método que devuelve una representación en forma de cadena del edificio con trazas adicionales.
     * Esta representación incluye todas las trazas de la lista de trazas seguidas de las coordenadas del edificio en formato (x, y).
     * @param listaTrazas La lista de trazas a incluir en la representación del edificio.
     * @return Una cadena que representa el edificio con las trazas y las coordenadas.
     */
    public String toString(List<String> listaTrazas) {
        StringBuilder edificio_string = new StringBuilder();

        for (String traza : listaTrazas){
            edificio_string.append(traza);
        }
        for(Coordenadas coordenada : this.listaCoordenadas) {
            edificio_string.append("(" + coordenada.getX() + "," + coordenada.getY() + ")");
        }
        return edificio_string.toString();
    }
}
