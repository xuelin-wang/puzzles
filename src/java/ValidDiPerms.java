public class ValidDiPerms {
    public static int[][] dp(String dis) {
        int n = dis.length();
        int[][] dpArr = new int[n + 1][n + 1];
        for (int i = 0; i <= n; i++) {
            if (i == 0) {
                for (int j = 0; j <= n; j++) {
                    dpArr[i][j] = 1;
                }
            }
            else {
                int accu = 0;
                boolean isInc = dis.charAt(i-1) == 'I';
                if (isInc) {
                    for (int j = 0; j <= n-i; j++) {
                        accu += dpArr[i-1][j];
                        dpArr[i][j] = accu;
                    }
                }
                else {
                    for (int j = n-i; j >= 0; j--) {
                        accu += dpArr[i-1][j+1];
                        dpArr[i][j] = accu;
                    }
                }
            }
        }
        return dpArr;
    }

    public static void main(String[] args) {
        for (String dis: new String[]{
            "I", "D", "ID", "DI", "II", "DD", "IDI", "DID", "III", "DDD", "IID"
        }) {
            int[][] dpArr = dp(dis);
            System.out.println("dis: " + dis);
            int n = dis.length();
            System.out.println("count: " + dpArr[n][0]);
        }
    }
}
