package games;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
 
 
public class StaticValue {
    //背景
    public static BufferedImage bg = null;
    public static BufferedImage bg1 = null;
    public static BufferedImage bg2 = null;
    //跳跃
    public static BufferedImage jump_L = null;
    public static BufferedImage jump_R = null;
    //站立
    public static BufferedImage stand_L = null;
    public static BufferedImage stand_R = null;
    //城堡
    public static BufferedImage tower = null;
    //旗杆
    public static BufferedImage gan = null;
    //旗杆
    public static BufferedImage gold1 = null;
    //障碍物
    public static List<BufferedImage> obstacle = new ArrayList<>();
    //跑
    public static List<BufferedImage> run_L = new ArrayList<>();
    public static List<BufferedImage> run_R = new ArrayList<>();
    //蘑菇
    public static List<BufferedImage> mogu = new ArrayList<>();
    //食人花
    public static List<BufferedImage> flower = new ArrayList<>();
    //乌龟
    public static List<BufferedImage> turtle = new ArrayList<>();
    //路径
    public static String path = System.getProperty("user.dir") + "/src/imgs/";
    //未开始界面
    public static BufferedImage start = null;
 
    //初始化方法
    public static void init() {
        try {
            //加载未开始图片
            start = ImageIO.read(new File(path + "start.jpg"));
            //加载背景图片
            bg = ImageIO.read(new File(path + "bg.jpg"));
            bg = ImageIO.read(new File(path + "bg1.jpg"));
            bg2 = ImageIO.read(new File(path + "bg2.jpg"));
            //加载马里奥向左跳跃
            jump_L = ImageIO.read(new File(path + "s_mario_jump1_L.png"));
            //加载马里奥向右跳跃
            jump_R = ImageIO.read(new File(path + "s_mario_jump1_R.png"));
            //加载马里奥向左站立
            stand_L = ImageIO.read(new File(path + "s_mario_stand_L.png"));
            //加载马里奥向右站立
            stand_R = ImageIO.read(new File(path + "s_mario_stand_R.png"));
            //加载城堡
            tower = ImageIO.read(new File(path + "tower.png"));
            //加载旗杆
            gan = ImageIO.read(new File(path + "gan.png"));
            //加载金币
            gold1 = ImageIO.read(new File(path + "gold1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
     // 加载马里奥向左跑
        for (int i = 1; i <= 7; i++) {
            try {
                run_L.add(ImageIO.read(new File(path + "s_mario_run" + i + "_L.png")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // 加载马里奥向右跑
        for (int i = 1; i <=4 ; i++) {
            try {
                run_R.add(ImageIO.read(new File(path + "s_mario_run" + i + "_R.png")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //加载障碍物
        try {
            obstacle.add(ImageIO.read(new File(path + "brick2.png")));
            obstacle.add(ImageIO.read(new File(path + "soil_up.png")));
            obstacle.add(ImageIO.read(new File(path + "soil_base.png")));
 
        } catch (IOException e) {
            e.printStackTrace();
        }
        //加载水管
        for (int i = 1; i <= 4; i++) {
            try {
                obstacle.add(ImageIO.read(new File(path + "pipe" + i + ".png")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //加载不可破坏的砖块和旗子
        try {
            obstacle.add(ImageIO.read(new File(path + "brick1.png")));
            obstacle.add(ImageIO.read(new File(path + "flag.png")));
            // 加载金币
            obstacle.add(ImageIO.read(new File(path + "gold1.png")));
            // 加载无金币砖块
            obstacle.add(ImageIO.read(new File(path + "brick3.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
 
        //加载蘑菇敌人
        for (int i = 1; i <= 3; i++) {
            try {
                mogu.add(ImageIO.read(new File(path + "fungus" + i + ".png")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //加载食人花敌人
        for (int i = 1; i <= 2; i++) {
            try {
                flower.add(ImageIO.read(new File(path + "flower1." + i + ".png")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //加载w乌龟敌人
        for (int i = 1; i <= 3; i++) {
            try {
                turtle.add(ImageIO.read(new File(path + "tortoise" + i + ".png")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
 
    }
}