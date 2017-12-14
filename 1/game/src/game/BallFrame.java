package game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class BallFrame extends JPanel {
//实例化一个数组对象
private Ball[] ball=new Ball[100];
//实例化一个随机数对象
private Random r=new Random();
public static void main(String[] args){
	//实例化一个面板对象
	BallFrame bf=new BallFrame();
	//调用initUI方法
	bf.initUI();
}
//界面函数
public void initUI(){
	JFrame jf=new JFrame();//实例化面板对象
	jf.setSize(new Dimension(800,600));//设置面板大小
	jf.setResizable(true);//设置不可调节大小
	jf.setDefaultCloseOperation(3);//设置关闭
	jf.setLocationRelativeTo(null);//设置窗体居中
	this.setBackground(Color.white);//设置面板背景为白色
	jf.setVisible(true);//设置窗体可见
	jf.add(this,BorderLayout.CENTER);//将面板添加到窗体上
	
	for(int i=0;i<ball.length;i++){
		//实例化每个小球对象
		ball[i]=new Ball(new Color(r.nextInt(255),r.nextInt(255),
				r.nextInt(255)),r.nextInt(550),r.nextInt(550),50,r.nextInt(4)+1,r.nextInt(4)+1,this,i);
	}
	for(int i=0;i<ball.length;i++){
		//将每个小球线程运行起来
		ball[i].start();
	}
}//重写paint方法
public void paint(Graphics g){
	//调用父类的paint方法
	super.paint(g);
	for(int i=0;i<ball.length;i++){
		//从ball中获取颜色并设置
		g.setColor(ball[i].getcolor());
		//画出小球
		g.fillOval(ball[i].getX(), ball[i].getY(), ball[i].getRadiu(), ball[i].getRadiu());
		
		
	}//调用碰撞函数
	collision();
	
}
//碰撞函数
private void collision(){	
	//距离数组，储存两小球的距离
	double[][] dis=new double[ball.length][ball.length];
	for(int i=0;i<ball.length;i++){
		for(int j=0;j<ball.length;j++){
			//计算两个小球间的距离
			dis[i][j]=Math.sqrt(Math.pow(ball[i].getX()-ball[j].getX(),2)+Math.pow(ball[i].getY()-ball[j].getY(),2));
			
		}
	}
	for(int i=0;i<ball.length;i++){
		for(int j=i+1;j<ball.length;j++){
			if(dis[i][j]<(ball[i].getRadiu()+ball[j].getRadiu())/2){
				int t;
				//交换小球x方向的速度
				t=ball[i].getVx();
				ball[i].setVx(ball[j].getVx());
				ball[j].setVx(t);			
				//交换小球y方向的速度
				t=ball[i].getVy();
				ball[i].setVy(ball[j].getVy());
				ball[j].setVy(t);
				//确定碰撞后第二个小球的位置
				int x2=ball[j].getX()-ball[i].getX(),y2=ball[j].getY()-ball[i].getY();
				ball[j].setX(ball[i].getX()+x2);
				ball[j].setY(ball[j].getY()+y2);

			}else{
				
			}
		}
	}
 }
}
class Ball extends Thread {  
    // 初始化一些对象名  
    private Color color;  
    private int x, y, radiu, vx, vy;  
    private BallFrame bf;  
    private int id;  
  
    /** 
     * 构造函数 
     *  
     * @param color小球颜色 
     * @param x小球横坐标 
     * @param y小球纵坐标 
     * @param radiu小球直径 
     * @param vx小球横向速度 
     * @param vy小球纵向速度 
     * @param bf面板 
     * @param id标志 
     */  
    public Ball(Color color, int x, int y, int radiu, int vx, int vy,  
            BallFrame bf, int id) {  
        this.color = color;  
        this.x = x;  
        this.y = y;  
        this.radiu = radiu;  
        this.vx = vx;  
        this.vy = vy;  
        this.bf = bf;  
        this.id = id;  
    }  
  
    // 重写run方法  
    public void run() {  
        super.run();// 调用父类run方法  
        // 执行无限循环  
        while (true) {  
            // System.out.println("第"+id+"个球的x:"+x +"   y:"+y);  
            x += vx;// 改变x的速度  
            y += vy;// 改变y的速度  
            // 如果x越界  
            if (x <= 0 || x + radiu >= bf.getWidth()) {  
                vx = -vx;// x速度反向  
                if (x < 0)  
                    x = 0;  
                else if (x > bf.getWidth() - radiu)  
                    x = bf.getWidth() - radiu;  
                else {  
                }  
            }  
            // 如果y越界  
            else if (y <= 0 || y + radiu >= bf.getHeight()) {  
                vy = -vy;// y速度反向  
                if (y < 0)  
                    y = 0;  
                else if (y > bf.getHeight() - radiu)  
                    y = bf.getHeight() - radiu;  
                else {  
                }  
            } else {  
            }  
  
            try {  
                Thread.sleep(50);// 设置睡眠时间为10ms  
            } catch (InterruptedException e) {  
                e.printStackTrace();  
            }  
            // 重绘  
            bf.repaint();  
        }  
    }  
  
    public Color getcolor() {  
        return color;  
    }  
  
    public void setcolor(Color color) {  
        this.color = color;  
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
  
    public int getRadiu() {  
        return radiu;  
    }  
  
    public void setRadiu(int radiu) {  
        this.radiu = radiu;  
    }  
  
    public int getVx() {  
        return vx;  
    }  
  
    public void setVx(int vx) {  
        this.vx = vx;  
    }  
  
    public int getVy() {  
        return vy;  
    }  
  
    public void setVy(int vy) {  
        this.vy = vy;  
    }  
  
}  
