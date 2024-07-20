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





class Solution {
    public int maximumGain(String s, int x, int y) {
        int res = 0;
        String top, bot;
        int top_score, bot_score;

        if (y > x) {
            top = "ba";
            top_score = y;
            bot = "ab";
            bot_score = x;
        } else {
            top = "ab";
            top_score = x;
            bot = "ba";
            bot_score = y;
        }

        // Removing first top substrings cause they give more points
        StringBuilder stack = new StringBuilder();
        for (char ch : s.toCharArray()) { // Changed 'char' to 'ch'
            if (ch == top.charAt(1) && stack.length() > 0 && stack.charAt(stack.length() - 1) == top.charAt(0)) {
                res += top_score;
                stack.setLength(stack.length() - 1);
            } else {
                stack.append(ch);
            }
        }

        // Removing bot substrings cause they give less or equal amount of scores
        StringBuilder new_stack = new StringBuilder();
        for (char ch : stack.toString().toCharArray()) { // Changed 'char' to 'ch'
            if (ch == bot.charAt(1) && new_stack.length() > 0 && new_stack.charAt(new_stack.length() - 1) == bot.charAt(0)) {
                res += bot_score;
                new_stack.setLength(new_stack.length() - 1);
            } else {
                new_stack.append(ch);
            }
        }

        return res;
    }
}


class Solution {
    public List<Integer> survivedRobotsHealths(int[] positions, int[] healths, String directions) {
        int n = positions.length;
        List<int[]> robots = new ArrayList<>();

        for (int i = 0; i < n; ++i) {
            robots.add(new int[]{positions[i], healths[i], directions.charAt(i), i});
        }

        Collections.sort(robots, (a, b) -> Integer.compare(a[0], b[0]));

        Stack<int[]> stack = new Stack<>();

        for (int[] robot : robots) {
            if (robot[2] == 'R' || stack.isEmpty() || stack.peek()[2] == 'L') {
                stack.push(robot);
                continue;
            }

            if (robot[2] == 'L') {
                boolean add = true;
                while (!stack.isEmpty() && stack.peek()[2] == 'R' && add) {
                    int last_health = stack.peek()[1];
                    if (robot[1] > last_health) {
                        stack.pop();
                        robot[1] -= 1;
                    } else if (robot[1] < last_health) {
                        stack.peek()[1] -= 1;
                        add = false;
                    } else {
                        stack.pop();
                        add = false;
                    }
                }

                if (add) {
                    stack.push(robot);
                }
            }
        }

        List<int[]> resultList = new ArrayList<>(stack);
        resultList.sort(Comparator.comparingInt(a -> a[3]));

        List<Integer> result = new ArrayList<>();
        for (int[] robot : resultList) {
            result.add(robot[1]);
        }

        return result;
    }
    class Solution {
    public String countOfAtoms(String formula) {
        int n = formula.length();
        Map<String, Integer> result_counter = new HashMap<>();
        Deque<Map<String, Integer>> parenthesis_stack = new ArrayDeque<>();
        int cur_ind = 0;

        while (cur_ind < n) {
            char cur_char = formula.charAt(cur_ind);

            if (cur_char == '(') {
                cur_ind++;
                parenthesis_stack.push(new HashMap<>());
                continue;
            }

            if (cur_char == ')') {
                StringBuilder mult_str = new StringBuilder();
                cur_ind++;

                while (cur_ind < n && Character.isDigit(formula.charAt(cur_ind))) {
                    mult_str.append(formula.charAt(cur_ind));
                    cur_ind++;
                }

                int mult = mult_str.length() == 0 ? 1 : Integer.parseInt(mult_str.toString());
                Map<String, Integer> last_counter = parenthesis_stack.pop();
                Map<String, Integer> target = parenthesis_stack.isEmpty() ? result_counter : parenthesis_stack.peek();
                
                for (Map.Entry<String, Integer> entry : last_counter.entrySet()) {
                    target.put(entry.getKey(), target.getOrDefault(entry.getKey(), 0) + entry.getValue() * mult);
                }
                continue;
            }

            StringBuilder cur_elem = new StringBuilder();
            StringBuilder cur_counter_str = new StringBuilder();
            Map<String, Integer> target = parenthesis_stack.isEmpty() ? result_counter : parenthesis_stack.peek();

            while (cur_ind < n && formula.charAt(cur_ind) != '(' && formula.charAt(cur_ind) != ')') {
                if (Character.isAlphabetic(formula.charAt(cur_ind))) {
                    if (Character.isUpperCase(formula.charAt(cur_ind)) && cur_elem.length() > 0) {
                        target.put(cur_elem.toString(), target.getOrDefault(cur_elem.toString(), 0) + (cur_counter_str.length() == 0 ? 1 : Integer.parseInt(cur_counter_str.toString())));
                        cur_elem = new StringBuilder();
                        cur_counter_str = new StringBuilder();
                    }
                    cur_elem.append(formula.charAt(cur_ind));
                } else {
                    cur_counter_str.append(formula.charAt(cur_ind));
                }
                cur_ind++;
            }

            target.put(cur_elem.toString(), target.getOrDefault(cur_elem.toString(), 0) + (cur_counter_str.length() == 0 ? 1 : Integer.parseInt(cur_counter_str.toString())));
        }

        List<String> parts = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : result_counter.entrySet()) {
            parts.add(entry.getKey() + (entry.getValue() == 1 ? "" : entry.getValue()));
        }
        Collections.sort(parts);

        StringBuilder result = new StringBuilder();
        for (String part : parts) {
            result.append(part);
        }

        return result.toString();
    }
}
}


    StringBuilder ans = new StringBuilder();
        int target = 0;
        boolean startAdding = false;
        char[] charArray = s.toCharArray();
        
        for (char c : charArray) {
            if (startAdding) {
                ans.append(c);
            }
            if (c == ch) {
                target++;
                if (target == count) {
                    startAdding = true;
                }
            }
        }
        
        return ans.toString();





class Solution {
    public List<Integer> luckyNumbers (int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        
        int[] row_minimums = new int[rows];
        Arrays.fill(row_minimums, Integer.MAX_VALUE);
        int[] col_maximums = new int[cols];
        
        for (int row_ind = 0; row_ind < rows; ++row_ind) {
            for (int col_ind = 0; col_ind < cols; ++col_ind) {
                int el = matrix[row_ind][col_ind];
                row_minimums[row_ind] = Math.min(row_minimums[row_ind], el);
                col_maximums[col_ind] = Math.max(col_maximums[col_ind], el);
            }
        }
        
        for (int row_ind = 0; row_ind < rows; ++row_ind) {
            for (int col_ind = 0; col_ind < cols; ++col_ind) {
                int el = matrix[row_ind][col_ind];
                if (el == row_minimums[row_ind] && el == col_maximums[col_ind]) {
                    return Collections.singletonList(el);
                }
            }
        }
        
        return Collections.emptyList();
    }
}




class Solution {
    public int[][] restoreMatrix(int[] rowSum, int[] colSum) {
        int rows = rowSum.length;
        int cols = colSum.length;
        int cur_row = 0, cur_col = 0;
        int[][] res = new int[rows][cols];

        while (cur_row < rows || cur_col < cols) {
            if (cur_row >= rows) {
                res[rows - 1][cur_col] = colSum[cur_col];
                cur_col++;
                continue;
            } else if (cur_col >= cols) {
                res[cur_row][cols - 1] = rowSum[cur_row];
                cur_row++;
                continue;
            }

            int value_to_put = Math.min(rowSum[cur_row], colSum[cur_col]);
            rowSum[cur_row] -= value_to_put;
            colSum[cur_col] -= value_to_put;
            res[cur_row][cur_col] = value_to_put;

            // I write this as this because it's possible that rowSum[cur_row] == colSum[cur_col] and we'll want to move both row and col pointers
            if (rowSum[cur_row] == 0) {
                cur_row++;
            }
            if (colSum[cur_col] == 0) {
                cur_col++;
            }
        }
        return res;
    }
}
