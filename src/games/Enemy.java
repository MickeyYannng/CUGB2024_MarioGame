package games;

import java.awt.image.BufferedImage;

public class Enemy implements Runnable{
	//存储当前的坐标
    private int x;
    private int y;
    //存储敌人的类型
    private int type;
    //判断敌人运动的方向
    private boolean face_tc = true;
    //用于显示当前敌人的图像
    private BufferedImage show;
    //定义一个背景图像
    private BackGround bg;
    //食人花运动的极限范围
    private int max_up = 0;
    private int max_down = 0;
    //定义线程对象
    private Thread thread = new Thread(this);
    //定义当前图片的状态
    private int image_type = 0;
 
    //蘑菇敌人的构造函数
    public Enemy(int x, int y, boolean face_tc, int type, BackGround bg) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.face_tc = face_tc;
        this.bg = bg;
        this.show = StaticValue.mogu.get(0);
        //启动线程实现蘑菇的移动
        thread.start();
    }
 
    //食人花敌人的构造函数
    public Enemy(int x, int y, boolean face_tc, int type, int max_up, int max_down, BackGround bg) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.face_tc = face_tc;
        this.show = StaticValue.flower.get(0);
        this.bg = bg;
        this.max_up = max_up;
        this.max_down = max_down;
        //启动线程实现食人花的移动
        thread.start();
    }
 
    //乌龟敌人的构造函数
    public Enemy(int x, int y, int type, BackGround bg) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.show = StaticValue.flower.get(0);
        this.bg = bg;
        //启动线程实现乌龟的移动
        thread.start();
    }
 
    // 敌人的死亡方法
    public void death() {
        show = StaticValue.mogu.get(2);
        this.bg.getEnemyList().remove(this);
    }
 
    public int getType() {
        return type;
    }
 
    public BufferedImage getShow() {
        return show;
    }
 
    public int getX() {
        return x;
    }
 
    public int getY() {
        return y;
    }
 
    @Override
    public void run() {
        while (true) {
            //判断是否是蘑菇敌人
            if (type == 1) {
                if (face_tc) {
                    this.x -= 2;
                } else {
                    this.x += 2;
                }
                image_type = image_type == 1 ? 0 : 1;
                show = StaticValue.mogu.get(image_type);
            }
            //定义两个布尔变量
            boolean canLeft = true;
            boolean canRight = true;
            //遍历每一个障碍物
            for (int i = 0; i < bg.getObstacleList().size(); i++) {
                Obstacle ob1 = bg.getObstacleList().get(i);
                //判断是否可以往右走
                if (ob1.getX() == this.x + 36 && (ob1.getY() + 65 > this.y && ob1.getY() - 35 < this.y)) {
                    canRight = false;
                }
                //判断是否可以继续往左走
                if (ob1.getX() == this.x - 36 && (ob1.getY() + 65 > this.y && ob1.getY() - 35 < this.y)) {
                    canLeft = false;
                }
            }
            if (face_tc && !canLeft || this.x == 0) {
                face_tc = false;
            } else if ((!face_tc) && (!canRight) || this.x == 764) {
                face_tc = true;
            }
            //判断是否是食人花敌人
            if (type == 2) {
                if (face_tc) {
                    this.y -= 2;
                } else {
                    this.y += 2;
                }
                image_type = image_type == 1 ? 0 : 1;
                //食人花是否到达了极限位置
                if (face_tc && (this.y == max_up)) {
                    face_tc = false;
                }
                if ((!face_tc) && (this.y == max_down)) {
                    face_tc = true;
                }
                show = StaticValue.flower.get(image_type);
            }
            if (type == 3) {
                this.x -= 1;
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                image_type = image_type == 0 ? 1 : 0;
                show = StaticValue.turtle.get(image_type);
            }
 
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
