package Main;

public class LevelData {
    static String[] ZombieTypes = {"Object.Enemies.NormalZombie", "Object.Enemies.ConeHeadZombie"};
    static int[][] SpawnProbability = {{0, 97}, {98, 99}};

    public static void changeSpawnProbability(){
        if (GamePanel.progress > 300){
            SpawnProbability = new int[][] {{0, 49}, {50, 99}};
        } else if (GamePanel.progress > 700) {
            SpawnProbability = new int[][] {{0, 1}, {2, 99}};
        }
    }

}