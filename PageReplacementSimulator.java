import java.util.*;

public class PageReplacementSimulator {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Digite a sequência de páginas (separadas por espaço):");
        String[] input = sc.nextLine().split(" ");
        int[] pages = Arrays.stream(input).mapToInt(Integer::parseInt).toArray();

        System.out.println("Digite o tamanho do quadro de páginas:");
        int frameSize = sc.nextInt();

        System.out.println("Método Relógio - Faltas de página: " + relogio(pages, frameSize));
        System.out.println("Método LRU - Faltas de página: " + lru(pages, frameSize));
        System.out.println("Método FIFO - Faltas de página: " + fifo(pages, frameSize));
        System.out.println("Método LFU - Faltas de página: " + lfu(pages, frameSize));
    }

    public static int fifo(int[] paginas, int tamFrame) {
        List<Integer> frames = new LinkedList<>();
        int faltaPaginas = 0;

        for (int page : paginas) {
            if (!frames.contains(page)) {
                if (frames.size() == tamFrame) {
                    frames.remove(0);
                }
                frames.add(page);
                faltaPaginas++;
            }
        }
        return faltaPaginas;
    }

    public static int lru(int[] paginas, int tamFrame) {
        Map<Integer, Integer> frames = new LinkedHashMap<>();
        int faltaPaginas = 0;

        for (int i = 0; i < paginas.length; i++) {
            int page = paginas[i];

            if (frames.containsKey(page)) {
                frames.remove(page);
            } else {
                faltaPaginas++;

                if (frames.size() == tamFrame) {
                    int lruPage = frames.keySet().iterator().next();
                    frames.remove(lruPage);
                }
            }

            frames.put(page, i);
        }
        return faltaPaginas;
    }

    public static int lfu(int[] paginas, int tamFrame) {
        Map<Integer, PageData> frames = new HashMap<>();
        int faltaPaginas = 0;
        int tempo = 0;

        for (int page : paginas) {
            tempo++;

            if (frames.containsKey(page)) {
                frames.get(page).frequencia++;
            } else {
                faltaPaginas++;

                if (frames.size() == tamFrame) {
                    int lfuPage = menosUsado(frames);
                    frames.remove(lfuPage);
                }

                frames.put(page, new PageData(1, tempo));
            }
        }
        return faltaPaginas;
    }

    private static int menosUsado(Map<Integer, PageData> frames) {
        return frames.entrySet().stream()
                .min((entry1, entry2) -> {
                    int freqCompare = Integer.compare(entry1.getValue().frequencia, entry2.getValue().frequencia);
                    if (freqCompare == 0) {
                        return Integer.compare(entry1.getValue().tempo, entry2.getValue().tempo);
                    }
                    return freqCompare;
                }).get().getKey();
    }

    static class PageData {
        int frequencia;
        int tempo;

        PageData(int frequencia, int tempo) {
            this.frequencia = frequencia;
            this.tempo = tempo;
        }
    }

    public static int relogio(int[] paginas, int tamFrame) {
        int[] frames = new int[tamFrame];
        boolean[] segundaChance = new boolean[tamFrame];
        int ponteiro = 0;
        int faltaPaginas = 0;

        Arrays.fill(frames, -1);

        for (int page : paginas) {
            boolean hit = false;
            for (int i = 0; i < tamFrame; i++) {
                if (frames[i] == page) {
                    segundaChance[i] = true;
                    hit = true;
                    break;
                }
            }

            if (!hit) {
                faltaPaginas++;
                while (true) {
                    if (!segundaChance[ponteiro]) {
                        frames[ponteiro] = page;
                        segundaChance[ponteiro] = false;
                        ponteiro = (ponteiro + 1) % tamFrame;
                        break;
                    } else {
                        segundaChance[ponteiro] = false;
                        ponteiro = (ponteiro + 1) % tamFrame;
                    }
                }
            }
        }

        return faltaPaginas;
    }
}
