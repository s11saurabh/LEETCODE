//Find the Safest Path in a Grid
//You are given a 0-indexed 2D matrix grid of size n x n, where (r, c) represents:

//A cell containing a thief if grid[r][c] = 1
//An empty cell if grid[r][c] = 0
//You are initially positioned at cell (0, 0). In one move, you can move to any adjacent cell in the grid, including cells containing thieves.

//The safeness factor of a path on the grid is defined as the minimum manhattan distance from any cell in the path to any thief in the grid.

//Return the maximum safeness factor of all paths leading to cell (n - 1, n - 1).

//An adjacent cell of cell (r, c), is one of the cells (r, c + 1), (r, c - 1), (r + 1, c) and (r - 1, c) if it exists.

//The Manhattan distance between two cells (a, b) and (x, y) is equal to |a - x| + |b - y|, where |val| denotes the absolute value of val.



//Constraints:

//1 <= grid.length == n <= 400
//grid[i].length == n
//grid[i][j] is either 0 or 1.
//There is at least one thief in the grid.

#include <iostream>
#include <vector>
#include <queue>
#include <algorithm> // For max function

using namespace std;

class Solution {
public:
    int maximumSafenessFactor(vector<vector<int>>& grid) {
        // Calculate safeness factors for each cell
        vector<vector<int>> safeness = calculateSafenessFactors(grid);
        // Find the maximum safeness value from the safeness matrix
        int maxSafeness = findMaxSafeness(safeness);
        // Perform binary search to find the maximum safeness factor
        return findMaxSafenessFactor(grid, safeness, maxSafeness);
    }

private:
    // Method to calculate the safeness factor for each cell using multi-source BFS
    vector<vector<int>> calculateSafenessFactors(vector<vector<int>>& grid) {
        int n = grid.size();
        vector<vector<int>> safeness(n, vector<int>(n, INT_MAX)); // Initialize safeness matrix with maximum integer value
        queue<pair<int, int>> q;

        // Add all cells with gold to the queue and set their safeness to 0
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    q.push({i, j});
                    safeness[i][j] = 0;
                }
            }
        }

        vector<int> dirX = {-1, 1, 0, 0};
        vector<int> dirY = {0, 0, -1, 1};

        // Perform BFS to calculate safeness factors
        while (!q.empty()) {
            auto cell = q.front(); q.pop();
            int x = cell.first;
            int y = cell.second;

            for (int i = 0; i < 4; i++) {
                int newX = x + dirX[i];
                int newY = y + dirY[i];

                // Check if the new cell is within bounds and update safeness if necessary
                if (newX >= 0 && newX < n && newY >= 0 && newY < n && safeness[newX][newY] > safeness[x][y] + 1) {
                    safeness[newX][newY] = safeness[x][y] + 1;
                    q.push({newX, newY});
                }
            }
        }

        return safeness;
    }

    // Method to find the maximum safeness value from the safeness matrix
    int findMaxSafeness(vector<vector<int>>& safeness) {
        int maxSafeness = 0;
        for (auto& row : safeness) {
            for (auto val : row) {
                maxSafeness = max(maxSafeness, val);
            }
        }
        return maxSafeness;
    }

    // Method to perform binary search to find the maximum safeness factor
    int findMaxSafenessFactor(vector<vector<int>>& grid, vector<vector<int>>& safeness, int maxSafeness) {
        int left = 0, right = maxSafeness, result = 0;

        // Binary search to find the maximum safeness factor
        while (left <= right) {
            int mid = left + (right - left) / 2;
            // Check if the current mid value can be achieved
            if (canAchieveSafeness(grid, safeness, mid)) {
                result = mid; // Update result if the current mid is achievable
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return result;
    }

    // Method to check if a path with the given safeness factor exists using BFS
    bool canAchieveSafeness(vector<vector<int>>& grid, vector<vector<int>>& safeness, int threshold) {
        int n = grid.size();
        if (safeness[0][0] < threshold) return false; // If the starting cell's safeness is less than the threshold

        queue<pair<int, int>> q;
        vector<vector<bool>> visited(n, vector<bool>(n, false));
        q.push({0, 0});
        visited[0][0] = true;

        vector<int> dirX = {-1, 1, 0, 0};
        vector<int> dirY = {0, 0, -1, 1};

        // Perform BFS to check if the bottom-right corner can be reached
        while (!q.empty()) {
            auto cell = q.front(); q.pop();
            int x = cell.first;
            int y = cell.second;

            if (x == n - 1 && y == n - 1) return true; // If we reach the bottom-right corner

            for (int i = 0; i < 4; i++) {
                int newX = x + dirX[i];
                int newY = y + dirY[i];

                // Check if the new cell is within bounds and its safeness is above the threshold
                if (newX >= 0 && newX < n && newY >= 0 && newY < n && !visited[newX][newY] && safeness[newX][newY] >= threshold) {
                    q.push({newX, newY});
                    visited[newX][newY] = true;
                }
            }
        }

        return false;
    }
};