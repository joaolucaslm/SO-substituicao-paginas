import java.util.ArrayList;
import java.util.List;

public class AlgoritmoLru {
    private AlgoritmoLru() {
    }

    public static int simular(int[] paginas, int quantidadeQuadros) {
        List<Integer> memoria = new ArrayList<>();
        int faltas = 0;

        for (int pagina : paginas) {
            int indice = memoria.indexOf(pagina);

            if (indice != -1) {
                memoria.remove(indice);
                memoria.add(pagina);
                continue;
            }

            faltas++;
            if (memoria.size() == quantidadeQuadros) {
                memoria.remove(0);
            }
            memoria.add(pagina);
        }

        return faltas;
    }
}
