import java.util.Arrays;

public class AlgoritmoRelogio {
    private AlgoritmoRelogio() {
    }

    public static int simular(int[] paginas, int quantidadeQuadros) {
        int[] quadros = new int[quantidadeQuadros];
        boolean[] bitsReferencia = new boolean[quantidadeQuadros];
        Arrays.fill(quadros, -1);

        int ponteiro = 0;
        int faltas = 0;

        for (int pagina : paginas) {
            int indiceEncontrado = encontrarPagina(quadros, pagina);

            if (indiceEncontrado != -1) {
                bitsReferencia[indiceEncontrado] = true;
                continue;
            }

            faltas++;
            while (bitsReferencia[ponteiro]) {
                bitsReferencia[ponteiro] = false;
                ponteiro = (ponteiro + 1) % quantidadeQuadros;
            }

            quadros[ponteiro] = pagina;
            bitsReferencia[ponteiro] = true;
            ponteiro = (ponteiro + 1) % quantidadeQuadros;
        }

        return faltas;
    }

    private static int encontrarPagina(int[] quadros, int pagina) {
        for (int i = 0; i < quadros.length; i++) {
            if (quadros[i] == pagina) {
                return i;
            }
        }
        return -1;
    }
}
