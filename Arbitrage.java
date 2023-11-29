import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

class Arbitrage {

    private HashMap<String, Integer> vertices;
    private double[][] matrix;

    public Arbitrage(int matrixSize, Set<String> vertexSet, ArrayList<Triple<String>> edges) {

        vertices = new HashMap<String, Integer>();
        matrix = new double[matrixSize][matrixSize];
        Integer nextInd = 0;

        for (String vertex: vertexSet){
            this.vertices.put(vertex, nextInd);
            nextInd++;
        }

        for (Triple<String> edge: edges){
            
            String currency1 = edge.val1;            
            String currency2 = edge.val2;

            int indcurrency1 = this.vertices.get(currency1);
            int indcurrency2 = this.vertices.get(currency2);

            matrix[indcurrency1][indcurrency2] = edge.cost;

        }

    }

    public boolean BellmanFord(String init){
       
        int n = vertices.size();
        double dist[] = new double[n];
        int src = vertices.get(init);

        for (int i = 0; i < dist.length; i++) {
            dist[i] = Double.POSITIVE_INFINITY;
        }

        dist[src] = -1.0;

        for (int i = 0; i < n-1; i++) {
            for (int u = 0; u < dist.length; u++) {
                for (int v = 0; v < dist.length; v++) {

                    if (matrix[u][v] == 0) {
                        continue;
                    }
                
                    //
                    if (dist[u] != Double.POSITIVE_INFINITY && dist[u]*matrix[u][v] < dist[v]){
                        dist[v] = dist[u]*matrix[u][v];
                    }

                }
            }
        }

        for (int u = 0; u < dist.length; u++) {
            for (int v = 0; v < dist.length; v++) {

                if (matrix[u][v] == 0) {
                    continue;
                }

                if (dist[u] != Double.POSITIVE_INFINITY && dist[u]*matrix[u][v] < dist[v]){
                    if (Math.abs(dist[u]*matrix[u][v] - dist[v]) > Math.pow(10, -10)) {
                        return true; 
                    }
                    
                }
            }
        }

        //
        return false;

    }

    public static void main(String[] args){
        try {

            File inputFile = new File("tasas.txt");
            Scanner reader = new Scanner(inputFile);

            HashSet<String> vertexSet = new HashSet<String>();
            ArrayList<Triple<String>> edges = new ArrayList<Triple<String>>();

            while (reader.hasNextLine()) {
                
                String data = reader.nextLine();
                String[] input = data.split(" ");

                vertexSet.add(input[0]);
                vertexSet.add(input[1]);

                Triple<String> edge = new Triple<String>(input[0], input[1], Double.parseDouble(input[2]));
                edges.add(edge);
            }
            reader.close();

            Arbitrage graph = new Arbitrage(vertexSet.size(), vertexSet, edges);
            

            for (String currency : vertexSet) {
                if (graph.BellmanFord(currency)){
                    System.out.println("DINERO FÁCIL DESDE TU CASA");
                    System.exit(0);
                }
            }

            System.out.println("TODO GUAY DEL PARAGUAY");

            
        } catch (FileNotFoundException e) {
            System.err.println("Ha ocurrido un error leyendo el archivo tasas.txt");
        }
    }
}