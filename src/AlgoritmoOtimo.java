import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlgoritmoOtimo {
    private AlgoritmoOtimo() {
    }

    public static int simular(int[] paginas, int quantidadeQuadros) {
        List<Integer> memoria = new ArrayList<>();
        int faltas = 0;

        for (int i = 0; i < paginas.length; i++) {
            int paginaAtual = paginas[i];

            if (memoria.contains(paginaAtual)) {
                continue;
            }

            faltas++;
            if (memoria.size() < quantidadeQuadros) {
                memoria.add(paginaAtual);
                continue;
            }

            int indiceSubstituicao = encontrarPaginaMaisDistante(memoria, paginas, i + 1);
            memoria.set(indiceSubstituicao, paginaAtual);
        }

        return faltas;
    }

    private static int encontrarPaginaMaisDistante(List<Integer> memoria, int[] paginas, int inicioBusca) {
        Map<Integer, Integer> proximoUso = new HashMap<>();

        for (int pagina : memoria) {
            proximoUso.put(pagina, Integer.MAX_VALUE);
        }

        for (int i = inicioBusca; i < paginas.length; i++) {
            int pagina = paginas[i];
            if (proximoUso.containsKey(pagina) && proximoUso.get(pagina) == Integer.MAX_VALUE) {
                proximoUso.put(pagina, i);
            }
        }

        int indiceSubstituicao = 0;
        int usoMaisDistante = -1;

        for (int i = 0; i < memoria.size(); i++) {
            int pagina = memoria.get(i);
            int distancia = proximoUso.get(pagina);
            if (distancia > usoMaisDistante) {
                usoMaisDistante = distancia;
                indiceSubstituicao = i;
            }
        }

        return indiceSubstituicao;
    }
}
