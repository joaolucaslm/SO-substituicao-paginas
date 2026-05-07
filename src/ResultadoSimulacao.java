public class ResultadoSimulacao {
    private final int fifo;
    private final int lru;
    private final int relogio;
    private final int otimo;

    public ResultadoSimulacao(int fifo, int lru, int relogio, int otimo) {
        this.fifo = fifo;
        this.lru = lru;
        this.relogio = relogio;
        this.otimo = otimo;
    }

    public int getFifo() {
        return fifo;
    }

    public int getLru() {
        return lru;
    }

    public int getRelogio() {
        return relogio;
    }

    public int getOtimo() {
        return otimo;
    }
}
