import java.util.*;
import java.io.*;
import java.math.*;
import static java.lang.System.*;

class Node{
    int id;
    HashSet<Node> children;
    Node(int id){
        this.id = id;
        children = new HashSet<>();
    }
}

class Temp_Tree implements Runnable{
    static void calculateDepths(Node curr, Integer distance, Integer orbit){
        dp[curr.id][orbit] = 1;
        depthStack.add(curr.id);
        Iterator itr = curr.children.iterator();
        while(itr.hasNext()){
            calculateDepths((Node)itr.next(), distance, orbit);
        }
        depthStack.remove(depthStack.size()-1);
        if(depthStack.size() >= distance)
            dp[depthStack.get(depthStack.size()-distance)][orbit] += dp[curr.id][orbit];    
    }
    static StringBuilder ans;

    static int[][] dp;
    static ArrayList<Integer> depthStack;
    static Stack<Node> DFS;
    static Node[] allNodes;

    public static void solve(int caseNo) throws IOException{
        //Step 0 : Create Nodes
        String[] nTemps = br.readLine().split(" ");
        int n = Integer.parseInt(nTemps[0]), aDis = Integer.parseInt(nTemps[1]), bDis = Integer.parseInt(nTemps[2]);
        //int n = 5*(int)1e5, aDis = 1, bDis = 1;
        allNodes = new Node[n];
        for(int itr = 0; itr < n; itr++){
            allNodes[itr] = new Node(itr);
        }
        //Step 1: Create Connections
        nTemps = br.readLine().split(" ");
        for(int itr = 0; itr < n-1; itr++){
            int parent = Integer.parseInt(nTemps[itr])-1, child = itr+1;
            //int parent = itr, child = itr+1;
            allNodes[parent].children.add(allNodes[child]);
        }
        //Step 2: Find aDis & bDis Depth children count.
        DFS.clear();
        depthStack.clear();
        calculateDepths(allNodes[0], aDis, 0);
        depthStack.clear();
        DFS.clear();
        calculateDepths(allNodes[0], bDis, 1);
        //Step 3: Do Your Thing!
        double tAns = 0;
        for(int itr = 0; itr < n; itr++){
            tAns+=(long)dp[itr][0]*n;
            tAns+=(long)dp[itr][1]*n;
            tAns-=(long)dp[itr][0]*dp[itr][1];
        }
        tAns/=Math.pow(n, 2);
        ans.append("Case #"+caseNo+": "+String.valueOf(tAns)+"\n");
    }
    final static BufferedReader br = new BufferedReader(new InputStreamReader(in));
    public void run(){
        try{
            ans = new StringBuilder();
            int t = Integer.parseInt(br.readLine()), caseNo = 0;
            dp = new int[5*(int)1e5][2];
            depthStack = new ArrayList<>();
            DFS = new Stack<>();
            while(t-- > 0){
                solve(++caseNo);    
            }
            out.print(ans);
            br.close();
        }
        catch(Exception e){
            return;
        }
    }
}

class Solution {
    public static void main(String[] args) throws InterruptedException
    {
        Thread t = new Thread(null, new Temp_Tree(), "Temp_Tree", 1 << 30);
        t.start();
        t.join();
    }
}