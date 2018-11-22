import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;

import javax.swing.*;


public class ChessBoard extends JPanel implements MouseListener {
    public static final int MARGIN=30;
    public static final int GRID_SPAN=35;
    public static final int ROWS=15;
    public static final int COLS=15;

    Point[] chessList=new Point[(ROWS+1)*(COLS+1)];
    boolean isBlack=true;//黑棋先
    boolean gameOver=false;//GAME是否结束
    int chessCount;//當前棋子的數目
    int xIndex,yIndex;//剛下棋子索引

    Image img;
    Image shadows;
    Color colortemp;
    public ChessBoard(){

        img=Toolkit.getDefaultToolkit().getImage("board.jpg");
        shadows=Toolkit.getDefaultToolkit().getImage("shadows.jpg");
        addMouseListener(this);
        addMouseMotionListener(new MouseMotionListener(){
            public void mouseDragged(MouseEvent e){
            }
            public void mouseMoved(MouseEvent e){
                int x1=(e.getX()-MARGIN+GRID_SPAN/2)/GRID_SPAN;
                int y1=(e.getY()-MARGIN+GRID_SPAN/2)/GRID_SPAN;
                if(x1<0||x1>ROWS||y1<0||y1>COLS||gameOver||findChess(x1,y1))
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                else setCursor(new Cursor(Cursor.HAND_CURSOR));
                //設定結束不能下棋了
            }
        });
    }




    public void paintComponent(Graphics g){

        super.paintComponent(g);//畫棋盤

        int imgWidth= img.getWidth(this);
        int imgHeight=img.getHeight(this);
        int FWidth=getWidth();
        int FHeight=getHeight();
        int x=(FWidth-imgWidth)/2;
        int y=(FHeight-imgHeight)/2;
        g.drawImage(img, x, y, null);


        for(int i=0;i<=ROWS;i++){
            g.drawLine(MARGIN, MARGIN+i*GRID_SPAN, MARGIN+COLS*GRID_SPAN, MARGIN+i*GRID_SPAN);
        }
        for(int i=0;i<=COLS;i++){
            g.drawLine(MARGIN+i*GRID_SPAN, MARGIN, MARGIN+i*GRID_SPAN, MARGIN+ROWS*GRID_SPAN);

        }

        //棋子
        for(int i=0;i<chessCount;i++){

            int xPos=chessList[i].getX()*GRID_SPAN+MARGIN;
            int yPos=chessList[i].getY()*GRID_SPAN+MARGIN;
            g.setColor(chessList[i].getColor());
            colortemp=chessList[i].getColor();
            if(colortemp==Color.black){
                RadialGradientPaint paint = new RadialGradientPaint(xPos-Point.DIAMETER/2+25, yPos-Point.DIAMETER/2+10, 20, new float[]{0f, 1f}
                        , new Color[]{Color.WHITE, Color.BLACK});
                ((Graphics2D) g).setPaint(paint);
                ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_DEFAULT);

            }
            else if(colortemp==Color.white){
                RadialGradientPaint paint = new RadialGradientPaint(xPos-Point.DIAMETER/2+25, yPos-Point.DIAMETER/2+10, 70, new float[]{0f, 1f}
                        , new Color[]{Color.WHITE, Color.BLACK});
                ((Graphics2D) g).setPaint(paint);
                ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_DEFAULT);

            }

            Ellipse2D e = new Ellipse2D.Float(xPos-Point.DIAMETER/2, yPos-Point.DIAMETER/2, 34, 35);
            ((Graphics2D) g).fill(e);


            if(i==chessCount-1){
                g.setColor(Color.red);
                g.drawRect(xPos-Point.DIAMETER/2, yPos-Point.DIAMETER/2,
                        34, 35);
            }
        }
    }

    public void mousePressed(MouseEvent e){


        if(gameOver) return;

        String colorName=isBlack?"黑棋":"白棋";


        xIndex=(e.getX()-MARGIN+GRID_SPAN/2)/GRID_SPAN;
        yIndex=(e.getY()-MARGIN+GRID_SPAN/2)/GRID_SPAN;


        if(xIndex<0||xIndex>ROWS||yIndex<0||yIndex>COLS)
            return;

        if(findChess(xIndex,yIndex))return;
        Point ch=new Point(xIndex,yIndex,isBlack?Color.black:Color.white);
        chessList[chessCount++]=ch;
        repaint();


        if(isWin()){
            String msg=String.format("恭喜，%s赢了！", colorName);
            JOptionPane.showMessageDialog(this, msg);
            gameOver=true;
        }
        isBlack=!isBlack;
    }
    public void mouseClicked(MouseEvent e){
    }

    public void mouseEntered(MouseEvent e){
    }
    public void mouseExited(MouseEvent e){
    }
    public void mouseReleased(MouseEvent e){
    }
    private boolean findChess(int x,int y){
        for(Point c:chessList){
            if(c!=null&&c.getX()==x&&c.getY()==y)
                return true;
        }
        return false;
    }


    private boolean isWin(){
        int continueCount=1;

        for(int x=xIndex-1;x>=0;x--){
            Color c=isBlack?Color.black:Color.white;
            if(getChess(x,yIndex,c)!=null){
                continueCount++;
            }else
                break;
        }
        for(int x=xIndex+1;x<=COLS;x++){
            Color c=isBlack?Color.black:Color.white;
            if(getChess(x,yIndex,c)!=null){
                continueCount++;
            }else
                break;
        }
        if(continueCount>=5){
            return true;
        }else
            continueCount=1;


        for(int y=yIndex-1;y>=0;y--){
            Color c=isBlack?Color.black:Color.white;
            if(getChess(xIndex,y,c)!=null){
                continueCount++;
            }else
                break;
        }
        for(int y=yIndex+1;y<=ROWS;y++){
            Color c=isBlack?Color.black:Color.white;
            if(getChess(xIndex,y,c)!=null)
                continueCount++;
            else
                break;

        }
        if(continueCount>=5)
            return true;
        else
            continueCount=1;


        for(int x=xIndex+1,y=yIndex-1;y>=0&&x<=COLS;x++,y--){
            Color c=isBlack?Color.black:Color.white;
            if(getChess(x,y,c)!=null){
                continueCount++;
            }
            else break;
        }
        for(int x=xIndex-1,y=yIndex+1;x>=0&&y<=ROWS;x--,y++){
            Color c=isBlack?Color.black:Color.white;
            if(getChess(x,y,c)!=null){
                continueCount++;
            }
            else break;
        }
        if(continueCount>=5)
            return true;
        else continueCount=1;



        for(int x=xIndex-1,y=yIndex-1;x>=0&&y>=0;x--,y--){
            Color c=isBlack?Color.black:Color.white;
            if(getChess(x,y,c)!=null)
                continueCount++;
            else break;
        }
        for(int x=xIndex+1,y=yIndex+1;x<=COLS&&y<=ROWS;x++,y++){
            Color c=isBlack?Color.black:Color.white;
            if(getChess(x,y,c)!=null)
                continueCount++;
            else break;
        }
        if(continueCount>=5)
            return true;
        else continueCount=1;

        return false;
    }


    private Point getChess(int xIndex,int yIndex,Color color){
        for(Point p:chessList){
            if(p!=null&&p.getX()==xIndex&&p.getY()==yIndex
                    &&p.getColor()==color)
                return p;
        }
        return null;
    }


    public void restartGame(){

        for(int i=0;i<chessList.length;i++){
            chessList[i]=null;
        }
        isBlack=true;
        gameOver=false;
        chessCount =0;
        repaint();
    }

    public Dimension getPreferredSize(){
        return new Dimension(MARGIN*2+GRID_SPAN*COLS,MARGIN*2
                +GRID_SPAN*ROWS);
    }
}
