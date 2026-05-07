import java.util.Scanner;

public class LeitorEntrada {
    private LeitorEntrada() {
    }

    public static int lerQuantidadeQuadros(Scanner scanner) {
        while (true) {
            String linha = scanner.nextLine().trim();
            try {
                int valor = Integer.parseInt(linha);
                if (valor > 0) {
                    return valor;
                }
            } catch (NumberFormatException ignored) {
            }
            System.out.print("Informe um numero inteiro maior que zero: ");
        }
    }

    public static int[] lerSequenciaPaginas(Scanner scanner) {
        while (true) {
            String linha = scanner.nextLine().trim();
            if (linha.isEmpty()) {
                System.out.print("Informe ao menos uma pagina: ");
                continue;
            }

            String[] partes = linha.split("\\s+");
            int[] paginas = new int[partes.length];

            try {
                for (int i = 0; i < partes.length; i++) {
                    paginas[i] = Integer.parseInt(partes[i]);
                }
                return paginas;
            } catch (NumberFormatException ignored) {
                System.out.print("Use apenas numeros inteiros separados por espaco: ");
            }
        }
    }
}
