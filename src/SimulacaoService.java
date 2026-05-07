public class SimulacaoService {
    private SimulacaoService() {
    }

    public static ResultadoSimulacao executar(int[] paginas, int quantidadeQuadros) {
        int fifo = AlgoritmoFifo.simular(paginas, quantidadeQuadros);
        int lru = AlgoritmoLru.simular(paginas, quantidadeQuadros);
        int relogio = AlgoritmoRelogio.simular(paginas, quantidadeQuadros);
        int otimo = AlgoritmoOtimo.simular(paginas, quantidadeQuadros);

        return new ResultadoSimulacao(fifo, lru, relogio, otimo);
    }
}
