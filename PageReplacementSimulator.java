import java.util.*;

public class PageReplacementSimulator {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Digite a sequência de páginas (separadas por espaço):");
        String[] input = sc.nextLine().split(" ");
        int[] pages = Arrays.stream(input).mapToInt(Integer::parseInt).toArray();

        System.out.println("Digite o tamanho do quadro de páginas:");
        int frameSize = sc.nextInt();

        System.out.println("Método Relógio - Faltas de página: " + clock(pages, frameSize));
        System.out.println("Método LRU - Faltas de página: " + lru(pages, frameSize));
        System.out.println("Método FIFO - Faltas de página: " + fifo(pages, frameSize));
        System.out.println("Método LFU - Faltas de página: " + lfu(pages, frameSize));
    }

    public static int fifo(int[] pages, int frameSize) {
        List<Integer> frames = new LinkedList<>();
        int pageFaults = 0;

        for (int page : pages) {
            if (!frames.contains(page)) {
                if (frames.size() == frameSize) {
                    frames.remove(0);
                }
                frames.add(page);
                pageFaults++;
            }
        }
        return pageFaults;
    }

    public static int lru(int[] pages, int frameSize) {
        Map<Integer, Integer> frames = new LinkedHashMap<>();
        int pageFaults = 0;

        for (int i = 0; i < pages.length; i++) {
            int page = pages[i];

            if (frames.containsKey(page)) {
                frames.remove(page);
            } else {
                pageFaults++;

                if (frames.size() == frameSize) {
                    int lruPage = frames.keySet().iterator().next();
                    frames.remove(lruPage);
                }
            }

            frames.put(page, i);
        }
        return pageFaults;
    }

    public static int lfu(int[] pages, int frameSize) {
        Map<Integer, PageData> frames = new HashMap<>();
        int pageFaults = 0;
        int time = 0;

        for (int page : pages) {
            time++;

            if (frames.containsKey(page)) {
                frames.get(page).frequency++;
            } else {
                pageFaults++;

                if (frames.size() == frameSize) {
                    int lfuPage = findLeastFrequentlyUsed(frames);
                    frames.remove(lfuPage);
                }

                frames.put(page, new PageData(1, time));
            }
        }
        return pageFaults;
    }

    private static int findLeastFrequentlyUsed(Map<Integer, PageData> frames) {
        return frames.entrySet().stream()
                .min((entry1, entry2) -> {
                    int freqCompare = Integer.compare(entry1.getValue().frequency, entry2.getValue().frequency);
                    if (freqCompare == 0) {
                        return Integer.compare(entry1.getValue().time, entry2.getValue().time);
                    }
                    return freqCompare;
                }).get().getKey();
    }

    static class PageData {
        int frequency;
        int time;

        PageData(int frequency, int time) {
            this.frequency = frequency;
            this.time = time;
        }
    }

    public static int clock(int[] pages, int frameSize) {
        int[] frames = new int[frameSize];
        boolean[] secondChance = new boolean[frameSize];
        int pointer = 0;
        int pageFaults = 0;

        Arrays.fill(frames, -1);

        for (int page : pages) {
            boolean hit = false;
            for (int i = 0; i < frameSize; i++) {
                if (frames[i] == page) {
                    secondChance[i] = true;
                    hit = true;
                    break;
                }
            }

            if (!hit) {
                pageFaults++;
                while (true) {
                    if (!secondChance[pointer]) {
                        frames[pointer] = page;
                        secondChance[pointer] = false;
                        pointer = (pointer + 1) % frameSize;
                        break;
                    } else {
                        secondChance[pointer] = false;
                        pointer = (pointer + 1) % frameSize;
                    }
                }
            }
        }

        return pageFaults;
    }
}
