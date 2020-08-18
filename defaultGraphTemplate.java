import java.util.*;
import java.io.*;
import java.math.*;
import static java.lang.System.*;

class Node{
    int id, totWeight;
    HashSet<Node> children;
    Node(int id){
        this.id = id;   this.totWeight = Integer.MAX_VALUE;
        children = new HashSet<>();
    }
}

class Temp implements Runnable{
    static void minimumWeights(Node curr, int weight){
        if(weight > curr.totWeight) return;
        curr.totWeight = weight;
        Iterator itr = curr.children.iterator();
        while(itr.hasNext()){
            minimumWeights((Node)itr.next(), weight+1);
        }
    }

    static StringBuilder ans;
    static HashMap<Integer, Node> allNodes;
    public static void solve(int caseNo) throws IOException{
        String[] nTemps = br.readLine().split(" ");
        //Step 0 : Create Nodes
        int n = Integer.parseInt(nTemps[0]), e = Integer.parseInt(nTemps[1]);
        for(int itr = 1; itr <= n; itr++){
            allNodes.put(itr, new Node(itr));
        }
        //Step 1: Create Connections
        for(int itr = 0; itr < e; itr++){
            nTemps = br.readLine().split(" ");
            int left = Integer.parseInt(nTemps[0]), right = Integer.parseInt(nTemps[1]);
            allNodes.get(left).children.add(allNodes.get(right));
            allNodes.get(right).children.add(allNodes.get(left));
        }
        //Step 2: Minimum Weights from a node to other nodes
        minimumWeights(allNodes.get(1), 0);

        long count = 0;
        
        ans.append("Case #"+caseNo+": "+String.valueOf(count)+"\n");
    }
    final static BufferedReader br = new BufferedReader(new InputStreamReader(in));
    @Override
    public void run(){
        try{
            ans = new StringBuilder();
            int t = Integer.parseInt(br.readLine());
            allNodes = new HashMap<>();
            for(int itr = 1; itr <= t; itr++){
                solve(itr);
                allNodes.clear();
            }
            out.print(ans);
            br.close();
        }catch(Exception e){
            Thread t = Thread.currentThread();
            t.getUncaughtExceptionHandler().uncaughtException(t, e);
        }
    }
}

class Solution {
    public static void main(String[] args) throws Exception
    {
        Thread t = new Thread(null, new Temp(), "Temp", 1 << 30);
        t.start();
        t.join();
    }
}