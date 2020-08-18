import java.util.*;
import java.io.*;
import java.math.*;
import static java.lang.System.*;


class Temp implements Runnable{
    static StringBuilder ans;
    static String[] nTemps;
    public static void solve(int caseNo) throws IOException{
        nTemps = br.readLine().split(" ");
        int r = Integer.parseInt(nTemps[0]), c = Integer.parseInt(nTemps[1]);
        String[] wall = new String[r];
        for(int itr = 0; itr < r; itr++)    wall[itr] = br.readLine();
        StringBuilder currAns = new StringBuilder();
        HashMap<Character, HashSet<Character>> depends = new HashMap<>();
        for(int jItr = 0; jItr < c; jItr++){
            depends.put(wall[r-1].charAt(jItr), new HashSet<>());
        }
        for(int iItr = r-2; iItr >=0; iItr--){
            for(int jItr = 0; jItr < c; jItr++){
                char currChar = wall[iItr].charAt(jItr), depending = wall[iItr+1].charAt(jItr);
                if(currChar != depending){
                    if(!depends.containsKey(currChar)){
                        depends.put(currChar, new HashSet<>());
                    }
                    //out.println(currChar+" : "+depending);
                    depends.get(currChar).add(depending);
                }
            }
        }/* 
        for(char curr = 'A'; curr <='Z'; curr++){
            if(depends.containsKey(curr)){
                Iterator itr = depends.get(curr).iterator();
                out.print(curr+" : ");
                while(itr.hasNext())    out.print(itr.next()+" ");
                out.println();
            }
        } */
        boolean flag = true;
        while(flag){
            flag = false;
            boolean currFlag = true;
            //if(caseNo == 1) out.println("x");
            for(char curr = 'A'; curr <='Z'; curr++){
                if(depends.containsKey(curr) && depends.get(curr).isEmpty()){
                    currFlag = false;
                    depends.remove(curr);
                    currAns.append(curr);
                    for(char depended = 'A'; depended <='Z'; depended++){
                        if(depends.containsKey(depended)){
                            depends.get(depended).remove(curr);
                        }
                    }
                }
                else if(depends.containsKey(curr))
                    flag = true;
            }
            if(currFlag){
                currAns = new StringBuilder("-1");
                break;
            }
        }
        ans.append("Case #"+caseNo+": "+currAns.toString()+"\n");
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