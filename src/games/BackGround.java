package games;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
 
public class BackGround {
    //显示当前场景图片
    private BufferedImage bgImage = null;
    //记录当前第几个场景
    private int sort;
    //判断是否为最后一个场景
    private boolean flag;
    //用于显示旗杆
    private BufferedImage gan = null;
    //用于显示城堡
    private BufferedImage tower = null;
    //判断马里奥是否到达旗杆位置
    private boolean isReach = false;
    //判断旗子是否落地
    private boolean isBase = false;
    // 添加一个新的障碍物类型，用于表示可以上下移动的砖块
    public static final int MOVING_BRICK = 10;
    // 存放金币
    private static BufferedImage start = null;
    //用于存放所有敌人
    private List<Enemy> enemyList = new ArrayList<>();
    //用于存放所有障碍物
    private List<Obstacle> obstacleList = new ArrayList<>();
 
    // 空参构造法
    public BackGround() {
    }
 
    // 两个参数构造法
    public BackGround(int sort, boolean flag) {
        this.sort = sort;
        this.flag = flag;
        start = StaticValue.start;
        if (sort==1) {
            // 最后一个场景
            bgImage = StaticValue.bg2;
        }
        else if(sort==2) {
            bgImage = StaticValue.bg;
        }
        else if(sort==3) {
            bgImage = StaticValue.bg;
        }
        if (sort == 1) {
            //地面
            //type是staticvalue类的障碍物
            //绘制第一个的地面上地面type=1  下地面type=2
            for (int i = 0; i < 30; i++) {
                obstacleList.add(new Obstacle(i * 60, 840, 1, this));
            }
            for (int j = 0; j <= 240; j += 60) {
                for (int i = 0; i < 30; i++) {
                    obstacleList.add(new Obstacle(i * 60, 1140 - j, 2, this));
                }
            }
            //绘制砖块(0-180)
            for (int i = 0; i <= 180; i += 60) {
                obstacleList.add(new Obstacle(i, 300, 7, this));
            }
            for (int i = 120; i <= 240; i += 60) {
            	if (i == 180 || i == 240) {
            		obstacleList.add(new Obstacle(i, 420, 7, this));
                } else {
                    //普通砖块
                    obstacleList.add(new Obstacle(i, 420, 0, this));
                }
            }
            obstacleList.add(new Obstacle(0, 480, 7, this));
            for (int i = 0; i <= 180; i += 60) {
                obstacleList.add(new Obstacle(i, 600, 7, this));
            }
            //绘制砖块(240-420)
            obstacleList.add(new Obstacle(300, 240, 7, this));    
            obstacleList.add(new Obstacle(360, 300, 7, this));   
            //绘制砖块（竖着）
            for (int j = 660; j >= 360; j -= 60) {
                obstacleList.add(new Obstacle(420, j, 7, this));
            }
            obstacleList.add(new Obstacle(420, 180, 0, this));
            obstacleList.add(new Obstacle(360, 480, 7, this));  
            obstacleList.add(new Obstacle(360, 600, 7, this)); 
            for (int i = 240; i <= 540; i += 60) {
                obstacleList.add(new Obstacle(i, 720, 7, this));
            }
            //绘制砖块(480-660)
            enemyList.add(new Enemy(480,375, true, 1, this));
            for (int i = 480; i <= 720; i += 60) {
                	obstacleList.add(new Obstacle(i, 420, 7, this));
            }
            obstacleList.add(new Obstacle(540, 720, 7, this));
            for (int i = 240; i <= 360; i += 60) {
                obstacleList.add(new Obstacle(i, 720, 7, this));
            }
            //绘制砖块B-F
            for (int i = 600; i <= 1140; i += 60) {
                if (i == 720 || i == 780 || i == 960 || i == 1020 || i == 1080) {
                    obstacleList.add(new Obstacle(i, 600, 7, this));
                } else {
                    //普通砖块
                    obstacleList.add(new Obstacle(i, 600, 0, this));
                }
            }
            //砖块G
            for (int i = 840; i <= 900; i += 60) {
                obstacleList.add(new Obstacle(i, 480, 7, this));
            }
            //绘制砖块(900-1080)
            obstacleList.add(new Obstacle(900, 360, 0, this));
            for (int i = 960; i <= 1020; i += 60) {
                obstacleList.add(new Obstacle(i, 360, 7, this));
            }
          //绘制砖块(1140-1320)
            for (int i = 1200; i <= 1320; i += 60) {
                obstacleList.add(new Obstacle(i, 720, 7, this));
            }
            // 绘制第一关的蘑菇敌人
            enemyList.add(new Enemy(360,795, true, 1, this));
            //食人花
            enemyList.add(new Enemy(960, 840, true, 2, 790, 850, this));

        }

        
        
        
        //绘制第二关节面
        if (sort == 2) {
            for (int i = 0; i <30; i++) {
                obstacleList.add(new Obstacle(i * 60, 840, 1, this));
            }
            for (int j = 0; j <= 240; j += 60) {
                for (int i = 0; i < 27; i++) {
                    obstacleList.add(new Obstacle(i * 60, 1140 - j, 2, this));
                }
            }
            //绘制砖块(0-180)
            for (int i = 0; i <= 180; i += 60) {
            	if(i==0)
                obstacleList.add(new Obstacle(i, 300, 0, this));
            	else
                obstacleList.add(new Obstacle(i, 300, 7, this));
            }
            for (int i = 120; i <= 240; i += 60) {
            	if (i == 180 || i == 240) {
            		obstacleList.add(new Obstacle(i, 420, 7, this));
                } else {
                    obstacleList.add(new Obstacle(i, 420, 0, this));
                }
            }
            obstacleList.add(new Obstacle(0, 480, 7, this));
            obstacleList.add(new Obstacle(60, 600, 7, this));
            obstacleList.add(new Obstacle(180, 600, 7, this));
            for (int i = 120; i <= 240; i += 60) {
                obstacleList.add(new Obstacle(i, 720, 7, this));
            }
            //绘制砖块(240-420)
            obstacleList.add(new Obstacle(360, 420, 7, this));
            obstacleList.add(new Obstacle(300, 540, 7, this));
            for (int i = 420; i <= 540; i += 60) {
            	obstacleList.add(new Obstacle(i, 540, 7, this));
        }
            //绘制砖块(480-660)
            obstacleList.add(new Obstacle(540, 720, 7, this));
            //三角
            obstacleList.add(new Obstacle(600, 660, 7, this));
            for (int i = 540; i <= 660; i += 60) {
                obstacleList.add(new Obstacle(i, 720, 7, this));
        }
            for (int i = 480; i <= 720; i += 60) {
                    obstacleList.add(new Obstacle(i, 780, 7, this));
            }
            for (int i = 480; i <= 600; i += 60) {
                obstacleList.add(new Obstacle(i, 420, 7, this));
        }

            //绘制砖块(720-840)
            for (int i = 780; i <= 1380; i += 120) {
                obstacleList.add(new Obstacle(i, 420, 0, this));
            }
            for (int i = 720; i <= 1620; i += 120) {
                obstacleList.add(new Obstacle(i, 600, 7, this));
            }
            for (int i = 720; i <= 1620; i += 120) {
                obstacleList.add(new Obstacle(i, 360, 0, this));
            }
            //食人花敌人
            for (int i = 780; i <= 1620; i += 60) {
            	enemyList.add(new Enemy(i+5, 840, true, 2, 790, 850, this));
            }
            //蘑菇敌人
            enemyList.add(new Enemy(400, 795, true, 1, this));
        }
        
        
        
        
        if (sort == 3) {
            for (int i = 0; i < 27; i++) {
                obstacleList.add(new Obstacle(i * 60, 840, 1, this));
            }
            for (int j = 0; j <= 240; j += 60) {
                for (int i = 0; i < 27; i++) {
                    obstacleList.add(new Obstacle(i * 60, 1140 - j, 2, this));
                }
            }
            // "C" - 上面
            for (int i = 240; i <= 480; i += 60) {
                if (i == 240 ) {
                    obstacleList.add(new Obstacle(i, 240, 7, this));
                    obstacleList.add(new Obstacle(i, 180, 7, this));
                    obstacleList.add(new Obstacle(i, 120, 7, this));
                } else {
                    obstacleList.add(new Obstacle(i, 60, 0, this));
                    obstacleList.add(new Obstacle(i, 300, 7, this));
                }
            }

            // "U" - 上面
            for (int i = 600; i <= 840; i += 60) {
                if (i == 600 || i == 840) {
                    obstacleList.add(new Obstacle(i, 240, 7, this));
                    obstacleList.add(new Obstacle(i, 180, 7, this));
                    obstacleList.add(new Obstacle(i, 120, 7, this));    
                    obstacleList.add(new Obstacle(i, 60, 7, this));
                } else {
                    obstacleList.add(new Obstacle(i,300, 0, this));
                }
            }

            // "G" - 下面
            for (int i = 240; i <= 540; i += 60) {
                if (i == 240 ) {
                    obstacleList.add(new Obstacle(i, 600, 7, this));
                    obstacleList.add(new Obstacle(i, 540, 7, this));
                    obstacleList.add(new Obstacle(i, 480, 7, this));
                }else {
                    obstacleList.add(new Obstacle(i, 660, 7, this));
                }
                if (i == 540) {
               	 obstacleList.add(new Obstacle(i, 600, 7, this));
                    obstacleList.add(new Obstacle(i, 540, 7, this));
               }
                if (i == 480) {
                       obstacleList.add(new Obstacle(i, 540, 7, this));
                  }
                if(i>=300&&i<=480) {
              		 obstacleList.add(new Obstacle(i, 420, 0, this));
                }
            }

            // "B" - 下面
            for (int i = 660; i <= 900; i += 60) {
                if (i == 660 || i == 900) {
                	if(i==900) {
                		 obstacleList.add(new Obstacle(i, 660, 7, this));
                         obstacleList.add(new Obstacle(i, 600, 7, this));
                         obstacleList.add(new Obstacle(i, 540, 7, this));
                	}else {
                		 obstacleList.add(new Obstacle(i, 720, 7, this));
                    	 obstacleList.add(new Obstacle(i, 660, 7, this));
                        obstacleList.add(new Obstacle(i, 600, 7, this));
                        obstacleList.add(new Obstacle(i, 540, 7, this));
                        obstacleList.add(new Obstacle(i, 480, 7, this));
                	}
                	
                }else {
                	 obstacleList.add(new Obstacle(i, 480, 7, this));
                    obstacleList.add(new Obstacle(i, 600, 7, this));
                    obstacleList.add(new Obstacle(i, 720, 7, this));
                }
            }
            obstacleList.add(new Obstacle(0, 780, 7, this));
            obstacleList.add(new Obstacle(60, 660, 7, this));
            obstacleList.add(new Obstacle(120, 540, 7, this));
            //绘制旗杆
            gan = StaticValue.gan;
            //绘制城堡
            tower = StaticValue.tower;
            //将旗子绘制到旗杆上
            obstacleList.add(new Obstacle(1030, 440, 8, this));
            //蘑菇
            for (int i = 0; i <= 720; i += 120) {
                enemyList.add(new Enemy(i + 30, 795, true, 1, this));
            } 
        }
      
    }
 
 
    // 顶金币
    public void createMoney(int x, int y, int type) {
        obstacleList.add(new Obstacle(x, y, type, this));
    }
 
    public BufferedImage getBgImage() {
        return bgImage;
    }
 
    public int getSort() {
        return sort;
    }
 
    public boolean isFlag() {
        return flag;
    }
 
    public List<Obstacle> getObstacleList() {
        return obstacleList;
    }
 
    public BufferedImage getGan() {
        return gan;
    }
 
    public BufferedImage getTower() {
        return tower;
    }
 
    public boolean isReach() {
        return isReach;
    }
 
    public void setReach(boolean reach) {
        isReach = reach;
    }
 
    public boolean isBase() {
        return isBase;
    }
 
    public void setBase(boolean base) {
        isBase = base;
    }
 
    public List<Enemy> getEnemyList() {
        return enemyList;
    }
 
    //获取未开始图片
    public static BufferedImage getStart() {
        return start;
    }
 
 
}
 