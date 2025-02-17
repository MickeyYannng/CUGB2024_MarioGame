package games;
import javax.imageio.ImageIO;
import javax.swing.*;
import javazoom.jl.decoder.JavaLayerException;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
 
// KeyListener接口 键盘事件类
public class MyFrame extends JFrame implements KeyListener, Runnable {
    // 用于存储所有背景
    private List<BackGround> allBg = new ArrayList<>();
    //用于存储当前的背景
    private BackGround nowBg = new BackGround();
    //用于双缓存
    private Image offScreenImage = null;
    //马里奥对象
    private Mario mario = new Mario();
    //定义一个线程对象用于实现马里奥的运动
    private Thread thread = new Thread(this);
 
    public static final int START = 0;
    public static final int RUNNING = 1;
    public static int state = START;
    Story sto=null;
    public MyFrame() {
        //设置窗口的大小
        this.setSize(1600, 1200);
        // 设置窗口居中显示
        this.setLocationRelativeTo(null);
        //设置窗口可见
        this.setVisible(true);
        //设置点击窗口关闭见关闭程序
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //设置窗口大小不可变
        this.setResizable(false);
        //向键盘添加键盘监听器  KeyListener接口
        this.addKeyListener(this);
        //设置窗口名称
        this.setTitle("超级玛丽");
        // 初始化图片
        StaticValue.init();
        //初始化马里奥对象
        mario = new Mario(10,100);
        //创建全部场景
        for (int i = 1; i <= 3; i++) {
            allBg.add(new BackGround(i, i == 3 ? true : false));
        }
        // 将第一个场景设置为当前场景
        nowBg = allBg.get(0);
        mario.setBackGround(nowBg);
        //调用鼠标点击方法
        action();
        //若游戏未开始 无限 停止线程
        while (state == START) {
            Thread.yield();
        }
        if(state==RUNNING) {
        	//绘制图像
        repaint();
        thread.start();
        }
        //加入音乐
        try {
            Music m1 = new Music(1);
        } catch (FileNotFoundException | JavaLayerException | InterruptedException e) {
            e.printStackTrace();
        }
  }
 
    private void action() {
        //鼠标侦听器
        MouseAdapter m = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //鼠标点击时若为游戏未开始状态则开始游戏
                if (state == START) {
                	if(sto==null)
                		sto=new Story();
                }
            }
        };
        this.addMouseListener(m);
        this.addMouseMotionListener(m);
    }
 
    public void paint(Graphics g) {
        //窗口
        if (offScreenImage == null) {
            offScreenImage = createImage(1620, 1200);
        }
        //画笔
        Graphics graphics = offScreenImage.getGraphics();
        //画窗口
        graphics.fillRect(0, 0, 1600, 1200);
        //画背景
        graphics.drawImage(nowBg.getBgImage(), 0, 0, this);
        //绘制敌人
        for (Enemy o : nowBg.getEnemyList()) {
            graphics.drawImage(o.getShow(), o.getX(), o.getY(), this);
        }
        //绘制障碍物
        //画障碍物
        for (Obstacle ob : nowBg.getObstacleList()) {
            graphics.drawImage(ob.getShow(), ob.getX(), ob.getY(), this);
        }
        //画城堡 旗杆
        graphics.drawImage(nowBg.getTower(), 1340, 500, this);
        graphics.drawImage(nowBg.getGan(), 1020, 440, this);
        //绘制马里奥
        graphics.drawImage(mario.getShow(), mario.getX(), mario.getY(), this);
        // 添加分数
        Color c = graphics.getColor();
        graphics.setColor(Color.BLACK);
        graphics.setFont(new Font("楷体", Font.BOLD, 24));
        graphics.drawString("当前分数:" + mario.getScore(), 16, 100);
        graphics.drawString("当前金币:" + mario.getGold(), 16, 123);
        graphics.setColor(c);
        //如果游戏未开始 画封面覆盖游戏画面
        if (state == START) {
            graphics.drawImage(BackGround.getStart(), 0, 0, this);
        }
        // 将图像绘制到窗口中
        g.drawImage(offScreenImage, 0, 0, this);
 
    }
 
    public static void main(String[] args) {
        MyFrame myFrame = new MyFrame();
    }
 
    @Override
    public void keyTyped(KeyEvent e) {
 
    }
 
    //当键盘按下时调用
    @Override
    public void keyPressed(KeyEvent e) {
        //向右移动
        if (e.getKeyCode() == 39) {
            mario.rightMove();
        }
        //向左移动
        if (e.getKeyCode() == 37) {
            mario.leftMove();
        }
        //跳跃
        if (e.getKeyCode() == 38) {
            mario.jump();
        }
    }
 
    //当键盘松开时
    @Override
    public void keyReleased(KeyEvent e) {
        //向左停止
        if (e.getKeyCode() == 37) {
            mario.leftStop();
        }
        //向右停止
        if (e.getKeyCode() == 39) {
            mario.rightStop();
        }
    }
 
    @Override
    public void run() {
        System.out.println(this.getName());
        while (true) {
            //调用鼠标点击方法
            action();
            //若游戏未开始 无限 停止线程
            while (state == START) {
                Thread.yield();
            }
            //如果马里奥达到了屏幕的最右  那么切换场景
            repaint();
            try {
                Thread.sleep(50);
                if (mario.getX() >= 1550 && mario.getGold() >= 3) {
                    nowBg = allBg.get(nowBg.getSort());
                    mario.setBackGround(nowBg);
                    mario.setX(10);
                    mario.setY(565);
                }
                //判断马里奥是否死亡
                if (mario.isDeath()) {
                    //弹窗
                    JOptionPane.showMessageDialog(this, "马里奥死亡!!!");
                    System.exit(0);
                }
                // 判断游戏是否结束
                if (mario.isOK()) {
                    JOptionPane.showMessageDialog(this, "恭喜你！成功通关了");
                    System.exit(0);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
 
        }
 
    }
}

class Story extends JFrame {
    int i=0;
    BufferedImage[] images; 
    public static String path = System.getProperty("user.dir") + "/src/imgs/";
    
    public Story() {
    	super("故事的开始......");

    	JLabel lab1=new JLabel("<html>在传说中魔王库巴抢走了公主！<br>马里奥为了拯救公主，在飞船上与库巴进行了大战！</html>");
    	Font customFont = new Font("宋体", Font.BOLD, 48);
        lab1.setFont(customFont);
        lab1.setForeground(Color.white);
        images = new BufferedImage[14];
        for(int i=1;i<13;i++) {
        	try {
			images[i] = ImageIO.read(new File(path +"剧情."+i+ ".jpg"));
		} catch (IOException e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}
        }
        try {
			images[13] = ImageIO.read(new File(path +"剧情.13.jpg"));
		} catch (IOException e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}
        

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    // 检查是否按下空格键
                    if (e.getComponent().isFocusOwner()) {
                    	lab1.setVisible(false);
                        if (e.getButton() == MouseEvent.BUTTON3) {
                            // 如果按下的是右键，则跳过剧情
                            dispose();
                            MyFrame.state = MyFrame.RUNNING;
                        } else if (e.getButton() == MouseEvent.BUTTON1) {
                            // 如果按下的是左键，则继续剧情
                            if (i < 13) {
                                i++;
                                repaint();
                            } else if (i == 13) {
                                dispose();
                                MyFrame.state = MyFrame.RUNNING;
                            }
                        }
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

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        pan.add(lab1, gbc);
        add(pan);
        pan.setBackground(Color.black);
        setSize(1600,1200);
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	setVisible(true);
    	Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) screensize.getWidth() / 2 - getWidth() / 2;
        int y = (int) screensize.getHeight() / 2 - getHeight() / 2;
        setLocation(x, y);
    }
}