import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {
    private ImageIcon [] icons = new ImageIcon[29];
    private JButton[] jbtn = new JButton[28];
    private JButton jbtnstar = new JButton("開始");
    private JButton jbtnf = new JButton();
    private JButton jbtns = new JButton();
    String f="",s="";
    int [] rnd = new int[28];
    int num = 0,win = 0;

    MainFrame(){init();
    }

    void init() {
        setLayout(null);
        setTitle("暗棋");
        setSize(430, 360);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        for (int i = 0; i < icons.length; i++) {
            icons[i] = new ImageIcon(String.valueOf(i) + ".png");

        }
        jbtnstar.setBounds(450, 600, 100, 50);
        add(jbtnstar);
        jbtnstar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] ary = new int[]{1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8, 9, 9, 10, 10, 11, 11, 12, 12, 13, 13, 14, 14};
                int n = 0;
                int max = ary.length - 1;
                for (int i = 0; i < ary.length; i++) {
                    n = (int) Math.round((Math.random() * max));
                    rnd[i] = ary[n];
                    ary[n] = ary[max];
                    max--;
                    jbtn[i].setActionCommand(String.valueOf(rnd[i]));
                    jbtn[i].setToolTipText(String.valueOf(i));
                    jbtn[i].setIcon(icons[0]);
                    jbtn[i].setEnabled(true);
                }
            }
        });
        int x = 0, y = 0;
        for (int i = 0; i < jbtn.length; i++) {
            jbtn[i] = new JButton();
            jbtn[i].setBounds(x * 150, y * 150, 150, 150);
            jbtn[i].setIcon(icons[0]);
            this.add(jbtn[i]);
            jbtn[i].setEnabled(false);
            x++;
            if (i % 7 == 6) {
                y++;
                x = 0;
            }
            add(jbtn[i]);
            jbtn[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    num++;
                    if (num == 1) {
                        f = e.getActionCommand();
                        jbtnf = (JButton) e.getSource();
                        jbtn[Integer.parseInt(jbtnf.getToolTipText())].setIcon(icons[Integer.parseInt(f)]);
                    } else if (num == 2) {
                        s = e.getActionCommand();
                        jbtns = (JButton) e.getSource();
                        jbtn[Integer.parseInt(jbtns.getToolTipText())].setIcon(icons[Integer.parseInt(s)]);
                        if (f.equals(s) && jbtns != jbtnf) {
                            JOptionPane.showMessageDialog(null, "嘿嘿猜對了");
                            jbtnf.setEnabled(false);
                            jbtns.setEnabled(false);
                            win++;
                            if (win == 14) {
                                JOptionPane.showMessageDialog(null, "全部答對囉");
                            }
                        }else {
                            JOptionPane.showMessageDialog(null, "不對");
                            jbtnf.setIcon(icons[0]);
                            jbtns.setIcon(icons[0]);
                        }
                            f = "";
                            s = "";
                            num = 0;
                        }
                    }


            });
        }


    }
}

