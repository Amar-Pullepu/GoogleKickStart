import java.util.*;
import java.io.*;
import java.math.*;
import static java.lang.System.*;

class Temp implements Runnable{
    static StringBuilder ans;
    static String[] nTemps;
    public static long calculate(int curr, int findS, int findE, int currS, int currE, long[] arr){
        if(currS >= findS && currE <= findE)    return arr[curr];
        else if((currS <= findS && findS <= currE) || (currS <= findE && findE <= currE))
            return calculate(2*curr+1, findS, findE, currS, (currS+currE)>>1, arr) + calculate(2*curr+2, findS, findE, ((currS+currE)>>1)+1, currE, arr);
        return 0;
    }
    public static void updateTree(int index, int val, long[] tree, int segLen, boolean spl){
        int treeIndex = segLen+Integer.parseInt(nTemps[1])-2;
        long xVal = tree[treeIndex];
        if(index%2 == 0)    val = -val;
        if(spl) val *= index;
        tree[treeIndex] -= xVal;
        tree[treeIndex] += val;
        treeIndex--;
        while(treeIndex >= 0){
            treeIndex/=2;
            tree[treeIndex] -= xVal;
            tree[treeIndex] += val;
            treeIndex--;
        }
    }
    public static void solve(int caseNo) throws IOException{
        //Step 0: Take Inputs
        nTemps = br.readLine().split(" ");
        int n = Integer.parseInt(nTemps[0]), q = Integer.parseInt(nTemps[1]);
        int segTreeLen = 1 << (int)Math.ceil(Math.log(n)/Math.log(2));
        nTemps = br.readLine().split(" ");
        long[] segTree = new long[2*segTreeLen-1];
        for(int itr = segTreeLen-1, mul = 1; itr < segTreeLen-1+n; itr++, mul++)
            if((mul&1)==1)   segTree[itr] = Integer.parseInt(nTemps[itr-segTreeLen+1]);    else   segTree[itr] = -Integer.parseInt(nTemps[itr-segTreeLen+1]);
        long[] mulSegTree = segTree.clone();
        for(int itr = segTreeLen-1, mul = 1; itr < segTreeLen-1+n; itr++, mul++)
            mulSegTree[itr] *= mul;
        for(int itr = segTree.length-1; itr > 0 ; itr--)    segTree[(itr-1)>>1] += segTree[itr];
        for(int itr = segTree.length-1; itr > 0 ; itr--)    mulSegTree[(itr-1)>>1] += mulSegTree[itr];
        long currAns = 0;
        while(q-- > 0){
            nTemps = br.readLine().split(" ");
            if(nTemps[0].equals("Q")){
                //For Each Value we will search in segment tree takes O(log(n))
                int s = Integer.parseInt(nTemps[1]), e = Integer.parseInt(nTemps[2]);
                long sum = calculate(0, s, e, 1, segTreeLen, segTree);
                long mulSum = calculate(0, s, e, 1, segTreeLen, mulSegTree);
                if((s&1) == 1) currAns+=mulSum-(s-1)*sum;
                else    currAns+=(s-1)*sum-mulSum;
            }else{
                //Each Update takes O(log n)
                int index = Integer.parseInt(nTemps[1]);
                int val = Integer.parseInt(nTemps[2]);
                updateTree(index, val, segTree, segTreeLen, false);
                updateTree(index, val, mulSegTree, segTreeLen, true);
            }
        }
        ans.append("Case #"+caseNo+": "+String.valueOf(currAns)+"\n");
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