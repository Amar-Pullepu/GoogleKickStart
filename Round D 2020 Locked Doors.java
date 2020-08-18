import java.util.*;
import java.io.*;
import java.math.*;
import static java.lang.System.*;


class Temp{
    static StringBuilder ans;
    static String[] nTemps;
    static int[][] sparseTable;

    static void preProcess(int[] doors){
        sparseTable = new int[doors.length][18];
        for(int itr = 0; itr < doors.length; itr++){
            sparseTable[itr][0] = itr;
        }
        for(int pow = 1; pow < 18; pow++)
            for(int itr = 0; itr < doors.length; itr++){
                sparseTable[itr][pow] = sparseTable[itr][pow-1];
                int right = itr + (1<<(pow-1));
                if(right < doors.length && doors[sparseTable[right][pow-1]] > doors[sparseTable[itr][pow]])
                    sparseTable[itr][pow] = sparseTable[right][pow-1];
            }
    }

    static int query(int[]doors, int left, int right){
        if(left == right)   return right;
        int pow = 0;
        int len = right-left;
        while((1<<pow) <= len){
            pow++;
        }
        pow--;
        if(doors[sparseTable[left][pow]] > doors[sparseTable[right-(1<<pow)+1][pow]])
            return sparseTable[left][pow];
        return sparseTable[right-(1<<pow)+1][pow];
    }

    public static void solve(int caseNo) throws IOException{
        nTemps = br.readLine().split(" ");
        int n = Integer.parseInt(nTemps[0]), q = Integer.parseInt(nTemps[1]);
        nTemps = br.readLine().split(" ");
        int[] doors = new int[n+1];
        //Initially lets make an Infinite difficulty door on either edges of the museum
        doors[0] = Integer.MAX_VALUE; doors[n] = Integer.MAX_VALUE;
        for(int itr = 1; itr < n; itr++)    doors[itr] = Integer.parseInt(nTemps[itr-1]);
        //Calculate right window and left window for each door
        int[] leftWindow = new int[n+1], rightWindow = new int[n+1];
        Stack<Integer> windowCalc = new Stack<>();//right window
        for(int itr = 0; itr <= n; itr++){
            while(!windowCalc.empty() && doors[windowCalc.peek()] < doors[itr])
                rightWindow[windowCalc.pop()] = itr;
            windowCalc.push(itr);
        }   rightWindow[0] = n+1;   rightWindow[n] = n+1;
        windowCalc.clear();//Left Window
        for(int itr = n; itr >= 0; itr--){
            while(!windowCalc.empty() && doors[windowCalc.peek()] < doors[itr])
                leftWindow[windowCalc.pop()] = itr;
            windowCalc.push(itr);
        }   leftWindow[n] = -1; leftWindow[0] = -1;
        //Build Sparse Table for Id's
        preProcess(doors);
        //For Each Query check where the kth element is in the window range or else check for the
        // max element on the left side where it's right window is in the range of k
        ans.append("Case #"+caseNo+": ");
        while(q-- > 0){
            nTemps = br.readLine().split(" ");
            int start = Integer.parseInt(nTemps[0]), k = Integer.parseInt(nTemps[1]);
            //Check for k lies in the window range
            if(rightWindow[start-1]-start-1 > k-1){
                int currAns = start + k - 1;
                if (currAns > n)
                    currAns = n - k + 1;
                ans.append(String.valueOf(currAns)+" ");
                continue;
            }
            if(start - leftWindow[start] > k-1){
                int currAns = start - k + 1;
                if(currAns < 1)
                    currAns = k;
                ans.append(String.valueOf(currAns)+" ");
                continue;
            }
            //if not binary search for a maximum number whose right window is equal to k-2 
            //we'll open k-2 door such that last binary search
            int l = 1, r = Math.min(start, k-2);
            while(l<=r){
                int mid = (l+r)>>1;
                int maxLeft = query(doors, start-mid, start-1);
                int maxRightWindow = rightWindow[maxLeft];
                int doorsCovered = maxRightWindow-start+mid;
                if(doorsCovered > k-1)  r = mid-1;
                else    l = mid+1;
            }
            int leftVal = start-r;
            int rightVal = leftVal+k-2;
            if(rightVal > n){
                ans.append(String.valueOf(n-k+1)+" ");
                continue;
            }
            else{
                int maxPresent = query(doors, leftVal, rightVal);
                if(maxPresent >= start) ans.append(String.valueOf(rightVal+1)+" ");
                else    ans.append(String.valueOf(leftVal)+" ");
            }
        }
        ans.append("\n");
    }
    final static BufferedReader br = new BufferedReader(new InputStreamReader(in));
    public static void main(String...args) throws IOException{
        ans = new StringBuilder();
        int t = Integer.parseInt(br.readLine());
        for(int itr = 1; itr <= t; itr++){
            solve(itr);    
        }
        out.print(ans);
        br.close();
    }
}