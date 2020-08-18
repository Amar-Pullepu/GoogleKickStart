import java.util.*;
import java.io.*;
import java.math.*;
import static java.lang.System.*;


class Temp implements Runnable{
    static StringBuilder ans;
    static String[] nTemps;
    static HashMap<Integer, Integer> sumFreqs;
    public static void solve(int caseNo) throws IOException{
        nTemps = br.readLine().split(" ");
        int n = Integer.parseInt(nTemps[0]);
        nTemps = br.readLine().split(" ");
        int sum = 0, mSum = 0;
        sumFreqs.put(0, 1);
        long count = 0;
        for(int itr = 0; itr < n; itr++){
            int curr = Integer.parseInt(nTemps[itr]);
            sum+=curr;
            mSum = Math.min(mSum, sum);
            for(int i = 0; sum-i*i >= mSum; i++){
                count+=sumFreqs.getOrDefault(sum-(i*i), 0);
            }
            sumFreqs.put(sum, sumFreqs.getOrDefault(sum, 0)+1);
        }
        ans.append("Case #"+caseNo+": "+String.valueOf(count)+"\n");
    }
    final static BufferedReader br = new BufferedReader(new InputStreamReader(in));
    @Override
    public void run(){
        try{
            ans = new StringBuilder();
            int t = Integer.parseInt(br.readLine());
            sumFreqs = new HashMap<>();
            for(int itr = 1; itr <= t; itr++){
                sumFreqs.clear();
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