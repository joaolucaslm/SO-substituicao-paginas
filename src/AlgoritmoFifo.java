import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

public class AlgoritmoFifo {
    private AlgoritmoFifo() {
    }

    public static int simular(int[] paginas, int quantidadeQuadros) {
        Set<Integer> memoria = new HashSet<>();
        Deque<Integer> fila = new ArrayDeque<>();
        int faltas = 0;

        for (int pagina : paginas) {
            if (memoria.contains(pagina)) {
                continue;
            }

            faltas++;
            if (memoria.size() == quantidadeQuadros) {
                int removida = fila.removeFirst();
                memoria.remove(removida);
            }

            memoria.add(pagina);
            fila.addLast(pagina);
        }

        return faltas;
    }
}
