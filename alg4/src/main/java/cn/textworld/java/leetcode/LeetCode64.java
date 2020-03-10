package cn.textworld.java.leetcode;

import java.util.Arrays;

public class LeetCode64 {
    public int minPathSum(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        int[] f = new int[n+1];
        Arrays.fill(f, Integer.MAX_VALUE);

        for(int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if(i == 0 && j == 0) {
                    f[j+1] = grid[i][j];
                }else{
                    f[j+1] = Math.min(f[j], f[j+1]) + grid[i][j];
                }
            }
        }

        return f[n];
    }

    public static void main(String[] args) {
        int[][] grid = new int[][]{
                {1,3,1},
                {1,5,1},
                {4,2,1}
        };

        LeetCode64 leetCode64 = new LeetCode64();
        System.out.println(leetCode64.minPathSum(grid));
    }
}
