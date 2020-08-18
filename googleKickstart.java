import java.util.*;
import java.io.*;
import java.math.*;
import static java.lang.System.*;


class Temp_01{
    static StringBuilder ans;
    public static void solve(int caseNo) throws IOException{
        int n = Integer.parseInt(br.readLine());
        String[] nTemps = br.readLine().split(" ");
        long[] nInputs = new long[n];
        for(int itr = 0; itr < n; itr++)    nInputs[itr] = Integer.parseInt(nTemps[itr]);
        long max = Integer.MIN_VALUE, count = 0;
        for(int itr = 0; itr < n; itr++){
            if(max<nInputs[itr]){
                max = nInputs[itr]; count++;
                if(itr+1 < n && nInputs[itr] <= nInputs[itr+1]){
                    count--;
                }
            }
        }
        ans.append("Case #"+caseNo+": "+String.valueOf(count)+"\n");
    }
    final static BufferedReader br = new BufferedReader(new InputStreamReader(in));
    public static void main(String...args) throws IOException{
        ans = new StringBuilder();
        int t = Integer.parseInt(br.readLine()), caseNo = 0;
        while(t-- > 0){
            solve(++caseNo);    
        }
        out.print(ans);
        br.close();
    }
}