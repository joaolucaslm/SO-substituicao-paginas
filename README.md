# SO-substituicao-paginas

Simulador academico de substituicao de paginas com duas abordagens:

- simulador original em console, preservado em `src/`
- dashboard web com JSF + PrimeFaces, agora executado com Spring Boot embarcado em `src/main/`

## Algoritmos implementados

1. FIFO
2. LRU
3. Relogio (Clock)
4. Otimo

## Dashboard web

O dashboard foi organizado com foco em separacao de responsabilidades:

- `controller`: controllers JSF e redirecionamento inicial
- `service`: orquestracao e adaptacao da simulacao
- `dto`: transferencia de dados para a interface
- `model`: enums e modelos de dominio
- `simulator`: implementacoes detalhadas dos algoritmos
- `util`: parse e validacoes simples

Recursos da interface:

- painel de simulacao com AJAX
- tabela detalhada da memoria por passo
- cards de metricas
- comparacao entre algoritmos
- graficos PrimeFaces
- visualizacao didatica do algoritmo Relogio

## Como executar o dashboard

Pre-requisitos:

- Java 17+
- Maven 3.9+

Subir localmente:

```bash
mvn spring-boot:run
```

Depois abra:

```text
http://localhost:8080/
```

Gerar artefato executavel:

```bash
mvn clean package
java -jar target/page-replacement-dashboard.war
```

## Como executar o simulador console

Compile:

```bash
javac src/*.java
```

Execute:

```bash
java -cp src SimuladorSubstituicaoPaginas
```
