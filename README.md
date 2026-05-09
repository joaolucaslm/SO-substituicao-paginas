# Link do Projeto no GitHub

<https://github.com/joaolucaslm/SO-substituicao-paginas.git>

---

# UNIVERSIDADE DE FORTALEZA
## CENTRO DE CIÊNCIAS TECNOLÓGICAS
## CIÊNCIA DA COMPUTAÇÃO

# SIMULADOR DE ALGORITMOS DE SUBSTITUIÇÃO DE PÁGINAS

**Autor 1:** Paulo de Tarso Neto - 2410522  
**Autor 2:** João Lucas Lima - 2410532

## Resumo

Este projeto apresenta um simulador acadêmico de substituição de páginas com duas abordagens complementares. A primeira preserva a versão original em console, voltada para execução simples e direta dos algoritmos. A segunda oferece um dashboard web com interface interativa construída em Java, Spring Boot, JSF e PrimeFaces, permitindo acompanhar passo a passo o comportamento da memória, os eventos de hit e page fault, a comparação entre algoritmos e a visualização didática do algoritmo Relógio. O objetivo do projeto é facilitar a compreensão prática dos conceitos de gerência de memória estudados na disciplina de Sistemas Operacionais.

**Palavras-chave:** sistemas operacionais; substituição de páginas; FIFO; LRU; relógio; ótimo.

## Introdução

Os algoritmos de substituição de páginas são fundamentais para o estudo de memória virtual em Sistemas Operacionais, pois determinam quais páginas devem ser removidas da memória principal quando ocorre a necessidade de carregar novas páginas. Este trabalho reúne, em um único projeto, implementações dos algoritmos FIFO, LRU, Relógio e Ótimo, oferecendo tanto uma versão em console quanto uma versão web com foco didático.

A proposta do dashboard web é tornar a análise mais visual e acessível, permitindo observar a evolução do estado dos quadros de memória a cada referência, além de comparar o desempenho dos algoritmos em uma mesma sequência de páginas.

## Desenvolvimento

O projeto foi organizado em duas partes:

1. O simulador original em console permanece na pasta `src/`, preservando a implementação acadêmica base.
2. A aplicação web foi estruturada no padrão Maven em `src/main/`, utilizando Spring Boot como base de execução, JSF para renderização de páginas e PrimeFaces para os componentes visuais.

Na versão web, a estrutura principal está dividida da seguinte forma:

- `controller`: controla o fluxo da interface e o redirecionamento inicial.
- `service`: centraliza a orquestração das simulações.
- `simulator`: contém as implementações detalhadas dos algoritmos.
- `dto`: transporta os dados exibidos no dashboard.
- `model`: define os enums e estruturas auxiliares.
- `util`: concentra parse e validações simples.

Entre os recursos disponíveis no dashboard, destacam-se:

- execução interativa da simulação;
- tabela detalhada com o estado da memória a cada passo;
- cartões de métricas com hits, faults e taxa de acerto;
- gráficos comparativos;
- painel específico para visualização do algoritmo Relógio.

## Requisitos

Para executar o projeto localmente, é necessário ter instalado:

- Java 17 ou superior;
- Maven 3.9 ou superior.

Observação: o projeto não possui `mvnw`, portanto o Maven precisa estar disponível no `PATH` da máquina.

## Execução da Aplicação Web

O projeto está configurado para empacotamento `jar` com Spring Boot e servidor embarcado Jetty.

Para iniciar a aplicação web em ambiente local:

```bash
mvn spring-boot:run
```

Após a inicialização, acesse:

```text
http://localhost:8080/
```

Para gerar o artefato executável:

```bash
mvn clean package
```

Depois, execute:

```bash
java -jar target/page-replacement-dashboard.jar
```

## Execução do Simulador em Console

Para compilar a versão original em console:

```bash
javac src/*.java
```

Para executar:

```bash
java -cp src SimuladorSubstituicaoPaginas
```

## Conclusão

O projeto oferece uma base prática para estudo dos algoritmos de substituição de páginas, combinando uma implementação tradicional em console com uma interface web voltada para análise visual e comparação de resultados. Dessa forma, o trabalho contribui para a compreensão dos impactos de cada estratégia de substituição sobre o número de page faults, hits e eficiência geral da memória.

## Referências

SPRING. Spring Boot. Disponível em: <https://spring.io/projects/spring-boot>. Acesso em: 8 maio 2026.

JOINFACES. JoinFaces Documentation. Disponível em: <https://joinfaces.org/>. Acesso em: 8 maio 2026.

PRIMEFACES. PrimeFaces. Disponível em: <https://www.primefaces.org/>. Acesso em: 8 maio 2026.
