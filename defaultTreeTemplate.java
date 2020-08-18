import java.util.*;
import java.io.*;
import java.math.*;
import static java.lang.System.*;

class Node{
    int id, depth;
    HashSet<Node> children;
    Node(int id){
        this.id = id;   this.depth = -1;
        children = new HashSet<>();
    }
}

class Temp implements Runnable{
    static void calculateDepths(Node curr, int depth){
        curr.depth = depth;
        Iterator itr = curr.children.iterator();
        while(itr.hasNext()){
            calculateDepths((Node)itr.next(), depth+1);
        }
    }
    static StringBuilder ans;
    static Node[] allNodes;
    public static void solve(int caseNo) throws IOException{
        //Step 0 : Create Nodes
        String[] nTemps = br.readLine().split(" ");
        int n = Integer.parseInt(nTemps[0]);
        allNodes = new Node[n+1];
        for(int itr = 1; itr <= n; itr++)
            allNodes[itr] = new Node(itr);
        //Step 1: Create Connections
        nTemps = br.readLine().split(" ");
        for(int itr = 0; itr < n-1; itr++){
            int parent = Integer.parseInt(nTemps[itr]), child = itr+2;
            allNodes[parent].children.add(allNodes[child]);
        } 
        //Step 2: Find Depths  
        calculateDepths(allNodes[1], 0);
        //Step 3: Do Your Thing!
        long count = 0;
        
        ans.append("Case #"+caseNo+": "+String.valueOf(count)+"\n");
    }
    final static BufferedReader br = new BufferedReader(new InputStreamReader(in));
    @Override
    public void run(){
        try{
            ans = new StringBuilder();
            int t = Integer.parseInt(br.readLine());
            for(int itr = 1; itr <= t; itr++){
                solve(itr);
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