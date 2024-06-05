class Solution {
public:
    vector<string> commonChars(vector<string>& words) {
        vector<int> freq(26, 0);  // Frequency array to store character counts
        
        // Count the frequency of each character in the first word
        for (char c : words[0]) {
            freq[c - 'a']++;
        }
        
        // Update the frequency array based on the character counts in the remaining words
        for (int i = 1; i < words.size(); i++) {
            vector<int> temp(26, 0);
            for (char c : words[i]) {
                temp[c - 'a']++;
            }
            for (int j = 0; j < 26; j++) {
                freq[j] = min(freq[j], temp[j]);
            }
        }
        
        vector<string> result;
        
        // Add the common characters to the result array
        for (int i = 0; i < 26; i++) {
            while (freq[i] > 0) {
                result.push_back(string(1, i + 'a'));
                freq[i]--;
            }
        }
        
        return result;
    }
};