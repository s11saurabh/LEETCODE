class Solution {
    public int numWaterBottles(int numBottles, int numExchange) {
        int totalBottles = numBottles;

        while (numBottles >= numExchange) {
            totalBottles += numBottles / numExchange;
            numBottles = (numBottles / numExchange) + (numBottles % numExchange);
        }

        return totalBottles;
    }
}



class Solution {
    public int findTheWinner(int n, int k) {
        ArrayList<Integer> circle = new ArrayList<>();
        for (int i = 1; i <= n; ++i) {
            circle.add(i);
        }
        int cur_ind = 0;

        while (circle.size() > 1) {
            int next_to_remove = (cur_ind + k - 1) % circle.size();
            circle.remove(next_to_remove);
            cur_ind = next_to_remove;
        }

        return circle.get(0);
    }
}



class Solution {
    public double averageWaitingTime(int[][] customers) {
        int n = customers.length;
        double time_waiting = customers[0][1];
        int finished_prev = customers[0][0] + customers[0][1];

        for (int customer_ind = 1; customer_ind < n; ++customer_ind) {
            int[] times = customers[customer_ind];
            int arrive = times[0];

            int start_cook = Math.max(arrive, finished_prev);
            int end_time = start_cook + times[1];
            finished_prev = end_time;
            time_waiting += end_time - arrive;
        }

        return time_waiting / n;
    }
}

class Solution {
    public int minOperations(String[] logs) {
        Stack<String> paths_stack = new Stack<>();

        for (String log : logs) {
            if (log.equals("../")) {
                if (!paths_stack.isEmpty()) {
                    paths_stack.pop();
                }
            } else if (!log.equals("./")) {
                paths_stack.push(log);
            }
        }

        return paths_stack.size();
    }
}


class Solution {
    int i = 0;
    public String reverseParentheses(String s) {
        char[] ar = s.toCharArray();
        return helper(ar);
    }

    public String helper(char[] s){
        StringBuilder sb = new StringBuilder();

        while(i < s.length){
            if(s[i] == ')'){
                i++;
                return sb.reverse().toString();
            }else if(s[i] == '('){
                i++;
                String st  = helper(s);
                sb.append(st);
            }else{
                sb.append(s[i]);
                i++;
            }
        }
        return sb.toString();

    }
}