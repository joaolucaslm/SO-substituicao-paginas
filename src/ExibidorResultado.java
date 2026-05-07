public class ExibidorResultado {
    private ExibidorResultado() {
    }

    public static void exibir(ResultadoSimulacao resultado) {
        System.out.println();
        System.out.println("Resultado da simulacao:");
        System.out.println("Metodo 1 (FIFO) - " + resultado.getFifo() + " faltas de pagina");
        System.out.println("Metodo 2 (LRU) - " + resultado.getLru() + " faltas de pagina");
        System.out.println("Metodo 3 (Relogio) - " + resultado.getRelogio() + " faltas de pagina");
        System.out.println("Metodo 4 (Otimo) - " + resultado.getOtimo() + " faltas de pagina");
    }
}
