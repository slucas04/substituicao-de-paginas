# Page Replacement Simulator


## Algoritmos Implementados
1. **FIFO (First-In, First-Out)**: Substitui a página mais antiga no quadro.
2. **LRU (Least Recently Used)**: Substitui a página que não foi usada há mais tempo.
3. **LFU (Least Frequently Used)**: Substitui a página que é menos frequentemente usada, com critério de desempate para a página mais antiga.
4. **Algoritmo do Relógio (Clock)**: Utiliza um ponteiro circular que concede uma "segunda chance" para páginas antes de serem substituídas.


**Instruções de uso**:
   - Quando o programa solicitar, insira a sequência de páginas, separadas por espaços (por exemplo, `1 2 3 4 1 2 5 1 2 3 4 5`).
   - Insira o tamanho do quadro de páginas, que define a quantidade de páginas que a memória pode armazenar de uma só vez.

O programa exibirá o número de faltas de página para cada um dos algoritmos, como no exemplo abaixo:
   ```
   Método Relógio - Faltas de página: 9
   Método LRU - Faltas de página: 8
   Método FIFO - Faltas de página: 10
   Método LFU - Faltas de página: 7
   ```

## Estrutura do Código
- **Main Method**: Recebe a entrada do usuário e executa cada algoritmo.
- **Algoritmo FIFO**: Implementado no método `fifo`, utiliza uma lista ligada para armazenar páginas em ordem de chegada.
- **Algoritmo LRU**: Implementado no método `lru`, utiliza um mapa de ordenação para manter o histórico de uso de cada página.
- **Algoritmo LFU**: Implementado no método `lfu`, utiliza um mapa para armazenar a frequência e o tempo de cada página.
- **Algoritmo do Relógio**: Implementado no método `clock`, usa um array circular e um array de bits para gerenciar as páginas e sua "segunda chance".

## Exemplo de Entrada e Saída
- **Entrada**:
  ```
  Sequência de páginas: 1 2 3 4 1 2 5 1 2 3 4 5
  Tamanho do quadro de páginas: 3
  ```

- **Saída**:
  ```
  Método Relógio - Faltas de página: 9
  Método LRU - Faltas de página: 8
  Método FIFO - Faltas de página: 10
  Método LFU - Faltas de página: 7
  ```
