import ADT.MyLListQueue;
import ADT.MyNode;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class DNA {

    private char BASES[] = {'A', 'G', 'C', 'T'};
    private double PROBABILITY_ONE = 0.02; //probability of rule one
    private double PROBABILITY_TWO = 0.06; //probability of rule two
    private double PROBABILITY_THREE = 0.08; //probability of rule three

    private int L; //max length of any gene
    private int V; //number of valid genes
    private int D; //number of diseased genes
    private int totalGeneNumber; //number of valid and diseased genes
    private int M; //max number of mutations
    private int G; //number of genes of test

    private String[] valid; //list of all valid genes
    private String[] diseased; //list of all diseased genes
    private String[] allGenes; //list of all genes (valid and diseased)

    private double[] prob; // probability to get from the start gene to any other gene
    private boolean[] vis; //whether a gene has been visited
    private int[] mutations; //number of mutations to get to any gene from starting gene
    private MyLListQueue<MyNode<Integer>> queue; //queue for bfs

    double graph[][];

    //Binary search to return the index of the gene it looks for. Returns -1 if gene is not found
    private int binSearch(String gene) {
        int high = allGenes.length - 1, low = 0;

        while (low <= high) {
            int mid = (high + low) / 2;

            if (allGenes[mid].equals(gene))
                return mid;

            //if the current element(mid) is lexigraphically less than gene
            if (allGenes[mid].compareTo(gene) < 0)
                low = mid + 1;

            else high = mid - 1;

        }

        //if gene is not found
        return -1;

    }

    private void bfs(String initial, String mutated) {
        int st = binSearch(initial);
        int end = binSearch(mutated);

        queue = new MyLListQueue<>();
        queue.enqueue(new MyNode<>(st));

        vis = new boolean[totalGeneNumber];
        mutations = new int[totalGeneNumber];
        prob = new double[totalGeneNumber];

        prob[st] = 1;
        vis[st] = true;

        while (!queue.isEmpty()) {

            int gene = Integer.parseInt(queue.dequeue().getValue().toString());

            ruleThree(gene);
            ruleTwo(gene);
            ruleOne(gene);

            if(vis[end]) {
                System.out.println("YES");
                System.out.println(prob[end]);
                return;
            }
            /*for (int g = 0; g < totalGeneNumber; g++) {
                if (graph[gene][g] != 0 && !vis[g]) {
                    if (graph[gene][g] > 0 && !vis[g]) {
                        if (g == end && mutations[gene] + 1 <= M) {


                            return;
                        }
                    }
                    vis[g] = true;
                    mutations[g] = mutations[gene] + 1;
                    prob[g] = prob[gene] * graph[gene][g];
                    queue.enqueue(new MyNode<>(g));
                }
            }*/
        }

        System.out.println("NO");

    }

    private void ruleOne(int geneId) {
        String gene = allGenes[geneId];

        String mutated = gene.charAt(gene.length() - 1)
                + gene.substring(1, gene.length() - 1)
                + gene.charAt(0);
        int mutatedId = binSearch(mutated);

        //if mutated gene is found
        if (mutatedId != -1 && !vis[mutatedId]) {
            prob[mutatedId] = prob[geneId] * PROBABILITY_ONE;
            vis[mutatedId] = true;
            mutations[mutatedId] = mutations[geneId] + 1;
            queue.enqueue(new MyNode<>(mutatedId));
        }
    }

    private void ruleTwo(int geneId) {
        String gene = allGenes[geneId];

        for (int c = 0; c < gene.length() - 1; c++) {
            if (gene.charAt(c) == gene.charAt(c + 1)) {
                for (char base : BASES) {
                    String mutated = gene.substring(0, c) + base + gene.substring(c + 2);
                    int mutatedId = binSearch(mutated);

                    //if mutated gene is found
                    if (mutatedId != -1) {
                        prob[mutatedId] = prob[geneId] * PROBABILITY_TWO;
                        vis[mutatedId] = true;
                        mutations[mutatedId] = mutations[geneId] + 1;
                        queue.enqueue(new MyNode<>(mutatedId));
                    }
                }
            }
        }
    }

    private void ruleThree(int geneId) {
        String gene = allGenes[geneId];

        for (int c = 0; c < gene.length() - 1; c++) {
            if ((gene.charAt(c) == 'G' && gene.charAt(c + 1) == 'T') ||
                    (gene.charAt(c) == 'T' && gene.charAt(c + 1) == 'G')) {
                for (char base : BASES) {
                    String mutated = gene.substring(0, c + 1) + base + gene.substring(c + 1);
                    int mutatedId = binSearch(mutated);

                    //if mutated gene is found
                    if (mutatedId != -1) {
                        prob[mutatedId] = prob[geneId] * PROBABILITY_THREE;
                        vis[mutatedId] = true;
                        mutations[mutatedId] = mutations[geneId] + 1;
                        queue.enqueue(new MyNode<>(mutatedId));

                    }
                }
            }
        }
    }


    private void createGraph() {
        graph = new double[totalGeneNumber][totalGeneNumber];

        for (int geneId = 0; geneId < totalGeneNumber; geneId++) {
            String gene = allGenes[geneId];
            //rule one
            String mutated1 = gene.charAt(gene.length() - 1)
                    + gene.substring(1, gene.length() - 1)
                    + gene.charAt(0);
            int mutated1Id = binSearch(mutated1);

            //if mutated gene is found
            if (mutated1Id != -1) {
                graph[geneId][mutated1Id] = Math.max(PROBABILITY_ONE, graph[geneId][mutated1Id]);
                graph[mutated1Id][geneId] = Math.max(PROBABILITY_ONE, graph[mutated1Id][geneId]);
            }

            //will not check for the rule two and three if there are less than two bases in the gene
            if (gene.length() < 2) continue;

            //rule two
            for (int c = 0; c < gene.length() - 1; c++) {
                if (gene.charAt(c) == gene.charAt(c + 1)) {
                    for (char base : BASES) {
                        String mutated2 = gene.substring(0, c) + base + gene.substring(c + 2);
                        int mutated2Id = binSearch(mutated2);

                        //if mutated gene is found
                        if (mutated2Id != -1) {
                            graph[geneId][mutated2Id] = Math.max(PROBABILITY_TWO, graph[geneId][mutated2Id]);
                            graph[mutated2Id][geneId] = Math.max(PROBABILITY_TWO, graph[mutated2Id][geneId]);
                        }
                    }
                }
            }

            //will not check for rule three if base cannot be inserted without exceeding L
            if (gene.length() + 1 > L) continue;

            //rule three
            for (int c = 0; c < gene.length() - 1; c++) {
                if ((gene.charAt(c) == 'G' && gene.charAt(c + 1) == 'T') ||
                        (gene.charAt(c) == 'T' && gene.charAt(c + 1) == 'G')) {
                    for (char base : BASES) {
                        String mutated3 = gene.substring(0, c + 1) + base + gene.substring(c + 1);
                        int mutated3Id = binSearch(mutated3);

                        //if mutated gene is found
                        if (mutated3Id != -1) {
                            graph[geneId][mutated3Id] = Math.max(PROBABILITY_THREE, graph[geneId][mutated3Id]);
                            graph[mutated3Id][geneId] = Math.max(PROBABILITY_THREE, graph[mutated3Id][geneId]);
                        }
                    }
                }
            }
        }
    }

    private void findProbability() {

        try {
            long startTime = System.nanoTime();

            File data = new File("Test.txt");
            Scanner scan = new Scanner(data);

            L = scan.nextInt();
            V = scan.nextInt();
            D = scan.nextInt();
            totalGeneNumber = D + V;

            valid = new String[V];
            diseased = new String[D];
            allGenes = new String[totalGeneNumber];

            for (int i = 0; i < V; i++) {
                valid[i] = scan.next();
                allGenes[i] = valid[i];
            }
            for (int i = 0; i < D; i++) {
                diseased[i] = scan.next();
                allGenes[V + i] = diseased[i];
            }
            Arrays.sort(allGenes);

            //createGraph();

            M = scan.nextInt();
            G = scan.nextInt();

            for (int i = 0; i < G; i++) {
                String P = scan.next();
                String Q = scan.next();
                bfs(P, Q);
            }

            System.out.println((System.nanoTime() - startTime) * 1E-9);
        } catch (FileNotFoundException e) {
            System.out.println("File not found :(, error: " + e);
        }
    }

    public static void main(String[] args) {
        DNA dna = new DNA();
        dna.findProbability();
    }

}
