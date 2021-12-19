import java.util.*;
import java.util.Arrays;

public class Question1{
    
    // this is a private function to convert char to integer
    private static int convertCharToIndex(char c) {
        return c - 'a';
    }
    
    // this is a private function to convert integer to char
    private static char convertIndexToChar(int i) {
        return (char) (i + 'a');
    }

    // this private function creates the adjeceny list that we used to store our graph
    private static ArrayList<Character>[] createAdjencyList() {
        final int NUM_OF_NODES = 8;     // this cariable stores total numebr of nodes
        
        ArrayList<Character>[] adjencyList = new ArrayList[NUM_OF_NODES];   // initialization of the array
        for (int i = 0; i < NUM_OF_NODES; i++) {
            adjencyList[i] = new ArrayList<Character>();

            // add the edges given in the png to the adjecency list
            if (i == convertCharToIndex('a')) {
                adjencyList[i].add('b');
                adjencyList[i].add('d');
                adjencyList[i].add('h');
            }
            if (i == convertCharToIndex('b')) {
                adjencyList[i].add('a');
                adjencyList[i].add('c');
                adjencyList[i].add('d');
            }
            if (i == convertCharToIndex('c')) {
                adjencyList[i].add('b');
                adjencyList[i].add('d');
                adjencyList[i].add('f');
            }
            if (i == convertCharToIndex('d')) {
                adjencyList[i].add('a');
                adjencyList[i].add('b');
                adjencyList[i].add('c');
                adjencyList[i].add('e');
            }
            if (i == convertCharToIndex('e')) {
                adjencyList[i].add('d');
                adjencyList[i].add('f');
                adjencyList[i].add('h');
            }
            if (i == convertCharToIndex('f')) {
                adjencyList[i].add('c');
                adjencyList[i].add('e');
                adjencyList[i].add('g');
            }
            if (i == convertCharToIndex('g')) {
                adjencyList[i].add('f');
                adjencyList[i].add('h');
            }
            if (i == convertCharToIndex('h')) {
                adjencyList[i].add('a');
                adjencyList[i].add('e');
                adjencyList[i].add('g');
            }
        }
        return adjencyList;   
    }
    
    // this function returns the shortest path length in given adjacency list.
    private static int getShortestPathLength(ArrayList<Character>[] adjencyList) {
        final int NUM_OF_NODES = 8;
        
        int[] distance = new int[NUM_OF_NODES];
        HashSet<Character> notDoneChars = new HashSet<Character>();
        for (int i = 0; i < NUM_OF_NODES; i++) {
            distance[i] = Integer.MAX_VALUE;
            notDoneChars.add(convertIndexToChar(i));
        }
        distance[convertCharToIndex('a')] = 0;
        
        while (!notDoneChars.isEmpty()) {
            int min = Integer.MAX_VALUE;
            char minChar = '\0';
            for (Character c: notDoneChars) {
                if (distance[convertCharToIndex(c)] <= min) {
                    min = distance[convertCharToIndex(c)];
                    minChar = c;
                }
            }
            notDoneChars.remove(minChar);
            
            for (Character c: adjencyList[convertCharToIndex(minChar)]) {
                distance[convertCharToIndex(c)] = Math.min(distance[convertCharToIndex(c)], distance[convertCharToIndex(minChar)] + 1);
            }
        }
        return distance[convertCharToIndex('h')];
    }
    
    // this is a helper function that recurses in the given adjacency list.
    public static void getPathsHelper(ArrayList<Character>[] adjencyList, ArrayList<ArrayList<Character>> allPaths, ArrayList<Character> curPath, boolean[] visited, char curNode) {
 
        curPath.add(curNode);
        visited[convertCharToIndex(curNode)] = true;
        if (curNode == 'h') {
            allPaths.add(curPath);
            return;
        }
        
        for (Character c: adjencyList[convertCharToIndex(curNode)]) {
            if (!visited[convertCharToIndex(c)]) {
                ArrayList<Character> copyList = new ArrayList(curPath);
                boolean copyVisited[] = Arrays.copyOf(visited, 8);
                getPathsHelper(adjencyList, allPaths, copyList, copyVisited, c);
            }
        }
    }
    
    // this function returns an array list of paths available in the graph from a to h vertices
    public static ArrayList<ArrayList<Character>> getPaths(ArrayList<Character>[] adjencyList) {
        ArrayList<ArrayList<Character>> allPaths = new ArrayList<ArrayList<Character>>();
        ArrayList<Character> curPath = new ArrayList<Character>();
        boolean[] visited = new boolean[8];
        for (int i = 0; i < 8; i++)
            visited[i] = false;
        
        getPathsHelper(adjencyList, allPaths, curPath, visited, 'a');
        
        return allPaths;
    }


    // MAIN function
    public static void main(String []args){
        ArrayList<Character>[] adjencyList = createAdjencyList();
        

     System.out.println("Answer to question a. \n");
        ArrayList<ArrayList<Character>> allPaths = getPaths(adjencyList);
        for (int i = 0; i < allPaths.size(); i++) {
            System.out.print((i+1) + " path:" );
            for (int j = 0; j < allPaths.get(i).size(); j++) {
                System.out.print(" " + allPaths.get(i).get(j));
            }
            System.out.println("");
        }
        
        int leastNumOfHops = getShortestPathLength(adjencyList);
        System.out.println("Answer to question b. Least Number of Hops: "+ leastNumOfHops);
    }
}