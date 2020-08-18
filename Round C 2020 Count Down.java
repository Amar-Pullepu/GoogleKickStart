import java.util.*;
import java.io.*;
import java.math.*;
import static java.lang.System.*;


class Temp implements Runnable{
    static StringBuilder ans;
    static String[] nTemps;
    public static void solve(int caseNo) throws IOException{
        nTemps = br.readLine().split(" ");
        int n = Integer.parseInt(nTemps[0]), k = Integer.parseInt(nTemps[1]);
        nTemps = br.readLine().split(" ");
        long[] nInputs = new long[n];
        for(int itr = 0; itr < n; itr++)    nInputs[itr] = Integer.parseInt(nTemps[itr]);
        long count = 0, dK = k;
        for(int itr = 0; itr < n; itr++){
            if(nInputs[itr] == dK)
                dK--;
            else{
                dK = k;
                if(nInputs[itr] == dK)  dK--;
            }
                
            if(dK == 0){
                count++;
                dK = k;
            }
        }
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