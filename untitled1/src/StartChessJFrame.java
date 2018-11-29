import java.awt.event.*;
import java.awt.*;

import javax.swing.*;

public class StartChessJFrame extends JFrame {
    private ChessBoard chessBoard;
    private JPanel toolbar;
    private JButton startButton,exitButton;

    private JMenuBar menuBar;
    private JMenu sysMenu;
    private JMenuItem startMenuItem,exitMenuItem;
    public StartChessJFrame(){
        chessBoard=new ChessBoard();
        Container contentPane=getContentPane();
        contentPane.add(chessBoard);
        chessBoard.setOpaque(true);
        menuBar =new JMenuBar();
        sysMenu=new JMenu("系统");
        startMenuItem=new JMenuItem("重新开始");
        exitMenuItem =new JMenuItem("退出");
        sysMenu.add(startMenuItem);
        sysMenu.add(exitMenuItem);
        MyItemListener lis=new MyItemListener();
        this.startMenuItem.addActionListener(lis);
        exitMenuItem.addActionListener(lis);
        menuBar.add(sysMenu);
        setJMenuBar(menuBar);
        toolbar=new JPanel();
        startButton=new JButton("重新开始");
        exitButton=new JButton("退出");
        toolbar.setLayout(new FlowLayout(FlowLayout.LEFT));
        toolbar.add(startButton);
        toolbar.add(exitButton);
        startButton.addActionListener(lis);
        exitButton.addActionListener(lis);
        add(toolbar,BorderLayout.SOUTH);
        add(chessBoard);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
    }
    public static void main(String[] args){
        StartChessJFrame f=new StartChessJFrame();
        f.setVisible(true);

    }

    private class MyItemListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            Object obj=e.getSource();
            if(obj==StartChessJFrame.this.startMenuItem||obj==startButton){
                System.out.println("重新开始");
                chessBoard.restartGame();
            }
            else if (obj==exitMenuItem||obj==exitButton)
                System.exit(0);
        }
    }
}

//主視窗面板
