import java.util.Scanner;

public class SimuladorSubstituicaoPaginas {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Quantidade de quadros: ");
            int quantidadeQuadros = LeitorEntrada.lerQuantidadeQuadros(scanner);

            System.out.print("Sequencia de paginas: ");
            int[] paginas = LeitorEntrada.lerSequenciaPaginas(scanner);

            ResultadoSimulacao resultado = SimulacaoService.executar(paginas, quantidadeQuadros);
            ExibidorResultado.exibir(resultado);
        }
    }
}


