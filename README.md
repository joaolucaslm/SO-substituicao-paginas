# SO-substituicao-paginas


Simulador em Java para comparar algoritmos de substituicao de paginas no gerenciamento de memoria virtual.


## Algoritmos implementados


1. FIFO
2. LRU
3. Relogio
4. Otimo


## Como executar


Compile:


```bash
javac src/*.java
```


Execute:


```bash
java -cp src SimuladorSubstituicaoPaginas
```


## Fluxo da aplicacao


O programa segue este fluxo:


1. `SimuladorSubstituicaoPaginas` inicia a aplicacao e coordena a execucao.
2. `LeitorEntrada` le a quantidade de quadros e a sequencia de paginas informadas pelo usuario.
3. `SimulacaoService` recebe os dados de entrada e executa todos os algoritmos de substituicao.
4. Cada algoritmo roda em sua propria classe:
   `AlgoritmoFifo`, `AlgoritmoLru`, `AlgoritmoRelogio` e `AlgoritmoOtimo`.
5. Os resultados sao agrupados em `ResultadoSimulacao`.
6. `ExibidorResultado` mostra na tela a quantidade de faltas de pagina de cada metodo.


Resumo do fluxo:


```text
SimuladorSubstituicaoPaginas
        -> LeitorEntrada
        -> SimulacaoService
            -> AlgoritmoFifo
            -> AlgoritmoLru
            -> AlgoritmoRelogio
            -> AlgoritmoOtimo
        -> ResultadoSimulacao
        -> ExibidorResultado
```


## Estrutura das classes


- `SimuladorSubstituicaoPaginas`: ponto de entrada da aplicacao.
- `LeitorEntrada`: valida e converte os dados digitados pelo usuario.
- `SimulacaoService`: centraliza a chamada dos algoritmos.
- `ResultadoSimulacao`: armazena os resultados finais da simulacao.
- `ExibidorResultado`: formata e exibe a saida no console.
- `AlgoritmoFifo`: implementa o algoritmo FIFO.
- `AlgoritmoLru`: implementa o algoritmo LRU.
- `AlgoritmoRelogio`: implementa o algoritmo Relogio.
- `AlgoritmoOtimo`: implementa o algoritmo Otimo.


## Entrada esperada


O programa solicita:


- quantidade de quadros de memoria
- sequencia de paginas separadas por espaco


Exemplo:


```text
Quantidade de quadros: 3
Sequencia de paginas: 7 0 1 2 0 3 0 4 2 3 0 3 2
```


## Saida esperada


```text
Metodo 1 (FIFO) - X faltas de pagina
Metodo 2 (LRU) - X faltas de pagina
Metodo 3 (Relogio) - X faltas de pagina
Metodo 4 (Otimo) - X faltas de pagina
```

