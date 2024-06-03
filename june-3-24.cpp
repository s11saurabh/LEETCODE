class Solution {
public:
    int appendCharacters(string s, string t) {
        int i = 0, j = 0;
        int sLen = s.length(), tLen = t.length();
        
        // Iterate through both strings
        while (i < sLen && j < tLen) {
            if (s[i] == t[j]) {
                // If characters match, move to the next character in t
                j++;
            }
            i++;
        }
        
        // Return the number of characters needed to be appended
        return tLen - j;
    }
};