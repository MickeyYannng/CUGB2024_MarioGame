package games;

import javazoom.jl.decoder.JavaLayerException;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
  

public class Mario implements Runnable {
    //用于表示当前马里奥的横纵坐标
    private int x;
    private int y;
    private boolean hasDied = false;
    //用于表示当前的状态
    private String status;
    //用于显示当前状态对应的图像
    private BufferedImage show = null;
    //定义一个BackGround对象  用于获取障碍物信息
    private BackGround backGround = new BackGround();
    //用来实现马里奥的动作
    private Thread thread = null;
    //定义一个索引
    private int index;
    //马里奥的移动速度
    private int xSpeed;
    Victory vic=null;
    //马里奥的跳跃速度
    private int ySpeed;
    int gravity = 15; // 重力大小，可以调整
    int fallSpeed = 0; // 初始下落速度
    //表示马里奥的上升的时间
    private int upTime = 0;
    //判断马里奥是否走到了 城堡的门口
    public static boolean isOK;
    //用于判断马里奥是否死亡
    private boolean isDeath = false;
    // 积分,金币
    public int score = 0;
    public int gold = 0;
 
    public int getScore() {
        return score;
    }
 
    public int getGold() {
        return gold;
    }
 
    //马里奥死亡方法
    public void death() {
    	if (!hasDied) {
    		try {
    			new Music(4);
        	} catch (FileNotFoundException | JavaLayerException | InterruptedException e) {
        	e.printStackTrace();
        	}
        	hasDied = true;
        }
        isDeath = true;
    }
    public boolean isDeath() {
        return isDeath;
    }
 
    public boolean isOK() {
        return isOK;
    }
 
    //马里奥跳跃
    public void jump() {
        // 判断马里奥是否为跳跃状态
        if (status.indexOf("jump") == -1) {
            //判断马里奥的跳跃方向是否为左
            if (status.indexOf("left") != -1) {
                status = "jump--left";
            } else {
                //不为左  则为右
                status = "jump--right";
            }
            ySpeed = -15;
            upTime = 9;
        }
        //判断马里奥是否碰到了旗子
        if (backGround.isReach()) {
            ySpeed = 10;
        }
 
    }
 
    //马里奥下落
    public void fall() {
        //判单马里奥的跳跃方向是否为左
        if (status.indexOf("left") != -1) {
            status = "jump--left";
        } else {
            //不为左  则为   右
            status = "jump--right";
        }
        ySpeed = 15;
    }
 
    public Mario() {
 
    }
 
    public Mario(int x, int y) {
        this.x = x;
        this.y = y;
        //初始马里奥向右站立
        show = StaticValue.stand_R;
        //马里奥当前状态
        this.status = "status--right";
        thread = new Thread(this);
        thread.start();
    }
 
    //向左移动
    public void leftMove() {
        //改变速度
        xSpeed = -10;
        //判断马里奥是否碰到了旗子
        if (backGround.isReach()) {
            xSpeed = 0;
        }
        //判断马里奥是否处于空中
        if (status.indexOf("jump") != -1) {
            status = "jump--left";
        } else {
            status = "move--left";
        }
    }
 
    //向右移动
    public void rightMove() {
        xSpeed = 10;
        //判断马里奥是否碰到了旗子
        if (backGround.isReach()) {
            xSpeed = 0;
        }
        //判断马里奥
        //是否处于空中
        
        if (status.indexOf("jump") != -1) {
            status = "jump--right";
        } else {
            status = "move--right";
        }
    }
 
    //向左停止
    public void leftStop() {
        xSpeed = 0;
        //判断马里奥是否处于空中
        if (status.indexOf("jump") != -1) {
            status = "jump--left";
        } else {
            status = "stop--left";
        }
    }
 
    //向右停止
    public void rightStop() {
        xSpeed = 0;
        //判断马里奥是否处于空中
        if (status.indexOf("jump") != -1) {
            status = "jump--right";
        } else {
            status = "stop--right";
        }
    }
 
    public int getX() {
        return x;
    }
 
    public void setX(int x) {
        this.x = x;
    }
 
    public int getY() {
        return y;
    }
 
    public void setY(int y) {
        this.y = y;
    }
 
    public BufferedImage getShow() {
        return show;
    }
 
    public void setShow(BufferedImage show) {
        this.show = show;
    }
 
    public void setBackGround(BackGround backGround) {
        this.backGround = backGround;
    }
 
    public String getStatus() {
        return status;
    }
 
    public BackGround getBackGround() {
        return backGround;
    }
 
    public Thread getThread() {
        return thread;
    }
 
    @Override
    public void run() {
        while (true) {
            //判断马里奥是否在障碍物上
            boolean onObstacle = false;
            //判断是否可以向右走
            boolean canRight = true;
            //判断是否可以向左走
            boolean canLeft = true;
            // 处理重力影响
            if (status.contains("fall")) {
                // 增加下落速度
                fallSpeed += gravity;
                // 更新马里奥的Y坐标
                y += fallSpeed;
            } else {
                // 减少下落速度，以模拟上升
                fallSpeed = Math.max(0, fallSpeed - gravity);
                // 更新马里奥的Y坐标
                y += fallSpeed;
            }

            //判断是否到达旗杆位置
            if (backGround.isFlag() && this.x >= 1000) {
 
                this.backGround.setReach(true);
                if (this.backGround.isBase()) {
                    status = "move--right";
                    if (x < 1380 ) {
                    	if(y>=800)
                        x += 5;
                    	else {
                    		y+=10;
                    	}
                    } else {
                    	
                        //表示马里奥已经到了城堡处
                    	if(vic==null) {
                        	vic=new Victory();
                        }
                    }
                    //如果旗子没有下落完成
                }  else {
                    //判断马里奥是否在空中
                    if (y < 800) {
 
                        xSpeed = 0;
                        this.y += 5;
                        status = "jump--right";
                    }
                    //如果马里奥罗到了地上
                    if (y > 800) {
                        this.y = 800;
                        status = "stop--right";
                    }
                }
            } else {
 
                //遍历当前场景的所有障碍物
                for (int i = 0; i < backGround.getObstacleList().size(); i++) {
                    Obstacle ob = backGround.getObstacleList().get(i);
                    if (ob.getY() == this.y +50  && (ob.getX() > this.x - 60 && ob.getX() < this.x + 50)) {
                        onObstacle = true;
                    }
                    
                    //判断跳起是否顶到了砖块
                    if ((ob.getY() >= this.y - 60 && ob.getY() <= this.y - 40) && (ob.getX() > this.x - 30 && ob.getX() < this.x + 30)) {
                        if (ob.getType() == 0) {
                            backGround.getObstacleList().remove(ob);
                            backGround.createMoney(ob.getX(), ob.getY() - 40, 9); //金币
                            try {
                            	new Music(2);
                            } catch (FileNotFoundException | JavaLayerException | InterruptedException e) {
                            	e.printStackTrace();
                            }
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            score += 50;
                        }
                        upTime = 0;
                    }
                    if (ob.getType() == 9) {
                        backGround.getObstacleList().remove(ob);
                        backGround.createMoney(ob.getX(), ob.getY() + 40, 10);
                        gold += 1;
                    }
               	 if (this.getX() >= 1550 && this.getGold() <= 3) {
                     canRight = false;
                 	 }
                    //判断是否可以往右走
                    if (ob.getX() == this.x + 50 && (ob.getY() - 50< this.y && this.y < ob.getY() + 50)) {
                        canRight = false;
                    }
                    //判断是否可以往左走
                    if (ob.getX() == this.x - 50 && (ob.getY() - 50< this.y && this.y < ob.getY() + 50)) {
                        canLeft = false;
                    }
                }
                //判断马里奥是否碰到敌人死亡  或者踩死敌人
                for (int i = 0; i < backGround.getEnemyList().size(); i++) {
                    Enemy e = backGround.getEnemyList().get(i); //当前敌人
                    if (e.getY() == this.y + 50  && (e.getX() - 50 <= this.x && e.getX() + 70 >= this.x)) {
                        if (e.getType() == 1) {
                            e.death();
                            upTime = 3;
                            ySpeed = -15;
                            score += 100;
                        } else if (e.getType() == 2) {
                            //马里奥死亡
                            death();
                        }
                    }
                    if ((e.getX() + 70 > this.x && e.getX() - 50 < this.x) && (e.getY() + 70 > this.y && e.getY() - 40 < this.y)) {
                        death();
                    }
                }
 
                //进行马里奥跳跃的操作
                if (onObstacle && upTime == 0) {
                    if (status.indexOf("left") != -1) {
                        if (xSpeed != 0) {
                            status = "move--left";
                        } else {
                            status = "stop--left";
                        }
                    } else {
                        if (xSpeed != 0) {
                            status = "move--right";
                        } else {
                            status = "stop--right";
                        }
                    }
                } else {
                    if (upTime != 0) {
                        upTime--;
                    } else {
                    	
                        fall();
                    }
                    y += ySpeed;
                }
            }
            //判断马里奥是否在运动
            if ((canLeft && xSpeed < 0) || (canRight && xSpeed > 0)) {
                //改变马里奥坐标
                x += xSpeed;
                //判断马里奥是否移动到了屏幕最左边
                if (x < 0) {
                    x = 0;
                }
            }
            //判断当前是否在移动状态
            if (status.contains("move")) {
                index = index == 0 ? 1 : 0;
            }
            //判断是否是向左移动
            if ("move--left".equals(status)) {
                show = StaticValue.run_L.get(index);
            }
            //判断是否是向右移动
            if ("move--right".equals(status)) {
                show = StaticValue.run_R.get(index);
            }
            //判断是否向左停止
            if ("stop--left".equals(status)) {
                show = StaticValue.stand_L;
            }
            //判断是否向右停止
            if ("stop--right".equals(status)) {
                show = StaticValue.stand_R;
            }
            //判断马里奥是否向左跳跃
            if ("jump--left".equals(status)) {
                show = StaticValue.jump_L;
            }
            //判断马里奥是否向右跳跃
            if ("jump--right".equals(status)) {
                show = StaticValue.jump_R;
            }
 
            try {
                //让线程休眠50毫秒
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
class Victory extends JFrame {
    int i=0;
    BufferedImage[] images; 
    public static String path = System.getProperty("user.dir") + "/src/imgs/";
    
    public Victory() {
    	super("胜利界面");
    	
        images = new BufferedImage[2];
        try {
			images[0] = ImageIO.read(new File(path +"剧情.14.jpeg"));
		} catch (IOException e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}
        
        try {
			images[1] = ImageIO.read(new File(path +"剧情.15.jpg"));
		} catch (IOException e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}
        
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	if(i<1) {
            		i=i+1;
            		repaint();
            	}
            	else if(i==1) {
            		Mario.isOK = true;
            	}
            	
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
        JPanel pan =new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(images[i], 0, 0, getWidth(), getHeight(), this);
            }
        };
        pan.setLayout(new GridBagLayout()); // 使用GridBagLayout布局管理器
        add(pan);
        setSize(1600,1200);
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	setVisible(true);
    	Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) screensize.getWidth() / 2 - getWidth() / 2;
        int y = (int) screensize.getHeight() / 2 - getHeight() / 2;
        setLocation(x, y);
    }
}