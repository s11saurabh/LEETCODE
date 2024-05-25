/*


Word Break II

Given a string s and a dictionary of strings wordDict, add spaces in s to construct a sentence where each word is a valid dictionary word. Return all such possible sentences in any order.

Note that the same word in the dictionary may be reused multiple times in the segmentation.

 

Example 1:

Input: s = "catsanddog", wordDict = ["cat","cats","and","sand","dog"]
Output: ["cats and dog","cat sand dog"]
Example 2:

Input: s = "pineapplepenapple", wordDict = ["apple","pen","applepen","pine","pineapple"]
Output: ["pine apple pen apple","pineapple pen apple","pine applepen apple"]
Explanation: Note that you are allowed to reuse a dictionary word.
Example 3:

Input: s = "catsandog", wordDict = ["cats","dog","sand","and","cat"]
Output: []
 

Constraints:

1 <= s.length <= 20
1 <= wordDict.length <= 1000
1 <= wordDict[i].length <= 10
s and wordDict[i] consist of only lowercase English letters.
All the strings of wordDict are unique.
Input is generated in a way that the length of the answer doesn't exceed 105.


*/




class Solution {
private:
    void backtrack(string& s, int index, unordered_set<string>& set, string curr, vector<string>& res){
        if (index == s.length()) {res.push_back(curr); return;}
        string t = "";
        for (int i = index; i < s.size(); i++){
            t += s[i];
            if (set.find(t) != set.end()){
                backtrack(s, i+1, set, curr + t + (i < s.length()-1 ? " ":""), res);
            }
        }
    }
public:
    vector<string> wordBreak(string s, vector<string>& wordDict) {
        vector<string> res;
        unordered_set<string> set(wordDict.begin(), wordDict.end());
        backtrack(s, 0, set, "", res);
        return res;
    }
};