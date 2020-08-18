import java.util.*;
import java.io.*;
import java.math.*;
import static java.lang.System.*;

class Temp implements Runnable{
    static StringBuilder ans;
    static String[] nTemps;
    static  HashMap<Integer, ArrayList<Integer>> queryMap;
    public static long calculate(int[] arr){
        long ans = 0;
        int pMul = 0, nMul = 0, pCoun = 0, nCoun = 0;
        long pCounter = 0, nCounter = 0;
        HashMap<Integer, Integer> pMap = new HashMap<>(), nMap = new HashMap<>(), nCounMap = new HashMap<>(), pCounMap = new HashMap<>();
        for(int itr = 0; itr < arr.length; itr++){
            if(queryMap.containsKey(itr)){
                if(pMul == 0 || pCoun%2 == 0){
                    pMul += queryMap.get(itr).size();
                    for(int eItr = 0; eItr < queryMap.get(itr).size(); eItr++){
                        int end = queryMap.get(itr).get(eItr);
                        pMap.put(end, pMap.getOrDefault(end, 0)+1);
                        pCounMap.put(end, pCounMap.getOrDefault(end, 0)+(end-itr+1));
                    }
                }else{
                    nMul += queryMap.get(itr).size();
                    for(int eItr = 0; eItr < queryMap.get(itr).size(); eItr++){
                        int end = queryMap.get(itr).get(eItr);
                        nMap.put(end, nMap.getOrDefault(end, 0)+1);
                        nCounMap.put(end, nCounMap.getOrDefault(end, 0)+(end-itr+1));
                    }
                }
            }
            //out.println("At itr "+itr+": pMul = "+pMul+", nMul = "+nMul+", pMap = "+pMap.getOrDefault(itr, 0)+", nMap = "+nMap.getOrDefault(itr, 0));
            if(pMul>0){
                pCoun++;    pCounter+=pMul;
                if((pCoun&1) == 0)    ans-=(pCounter*arr[itr]); else if((pCoun&1) != 0)    ans+=(pCounter*arr[itr]);
            }    
            if(nMul>0){
                nCoun++;    nCounter+=nMul;
                if((nCoun&1) == 0)    ans-=(nCounter*arr[itr]); else if((nCoun&1) != 0)    ans+=(nCounter*arr[itr]);
            } 
            //out.println("At itr "+itr+": pMul = "+pMul+", nMul = "+nMul+", pCounter = "+pCounter+" , nCounter = "+nCounter+", pCoun = "+pCoun+" , nCoun = "+nCoun);
            int pMulDec = pMap.getOrDefault(itr, 0), nMulDec = nMap.getOrDefault(itr, 0);
            pMul -= pMulDec;    nMul -= nMulDec;
            pCounter -= pCounMap.getOrDefault(itr, 0);  nCounter -= nCounMap.getOrDefault(itr, 0);
            if(nMul == 0)   nCoun = 0;
            if(pMul == 0){
                pMul = nMul;    pCoun = nCoun;  pCounter = nCounter;
                nMul = 0;   nCoun = 0;  nCounter = 0;
                HashMap<Integer, Integer> temp = pMap;
                pMap = nMap;    nMap = temp;
                temp = pCounMap;
                pCounMap = nCounMap;    nCounMap = temp;
            }
        }
        return ans;
    }
    public static void solve(int caseNo) throws IOException{
        nTemps = br.readLine().split(" ");
        int n = Integer.parseInt(nTemps[0]), q = Integer.parseInt(nTemps[1]);
        nTemps = br.readLine().split(" ");
        int[] nInputs = new int[n];
        for(int itr = 0; itr < n; itr++)    nInputs[itr] = Integer.parseInt(nTemps[itr]);
        long count = 0;
        queryMap.clear();
        boolean queried = false;
        while(q-- > 0){
            nTemps = br.readLine().split(" ");
            if(nTemps[0].equals("Q")){
                queried = true;
                int s = Integer.parseInt(nTemps[1])-1, e = Integer.parseInt(nTemps[2])-1;
                if(!queryMap.containsKey(s))    queryMap.put(s, new ArrayList<>());
                queryMap.get(s).add(e);
            }
            else{
                if(queried){
                    long curr = calculate(nInputs);
                    count+=curr;
                    queried = false;    
                }
                nInputs[Integer.parseInt(nTemps[1])-1] = Integer.parseInt(nTemps[2]);
                queryMap.clear();
            }
        }
        if(queried){
            long curr =calculate(nInputs);
            count+=curr;
            queried = false;
        }
        ans.append("Case #"+caseNo+": "+String.valueOf(count)+"\n");
    }
    final static BufferedReader br = new BufferedReader(new InputStreamReader(in));
    @Override
    public void run(){
        try{
            ans = new StringBuilder();
            int t = Integer.parseInt(br.readLine());
            queryMap = new HashMap<>();
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