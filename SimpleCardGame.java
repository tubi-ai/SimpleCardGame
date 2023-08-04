
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.Component;
import java.awt.Font;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Objects;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

public class SimpleCardGame extends JFrame {

    private final JComboBox<String> cmbx;
    JLabel label, dLabel;
    JRadioButton[] radio;
    static String[] cards = {"ace", "2", "3", "4", "5", "6", "7", "8",
            "9", "10", "jack", "queen", "king"};

    static String[] suit = {"club", "heart", "diamond", "spade"};
    JButton button;
    static final String check = "CHECK";
    private final Icon[] icons = new Icon[53];
    private final Random randnum;
    String PCard, P1Card ;
    String DCard ;
    private int total, mult;
    private JTextField currentScore;
    Container cp;

    final ButtonGroup groupSizes = new ButtonGroup();
    JPanel suitbox, combox;
    JTextArea textArea;

    public SimpleCardGame() {

        super("Simple Card Game");
        cp = getContentPane();
        cp.setLayout(new FlowLayout());
        int mult=0;

        radio = new JRadioButton[suit.length];
        dLabel = new JLabel(icons[52]);
        dLabel.setIcon(icons[52]);
        dLabel.setEnabled(true);

        dLabel.setHorizontalTextPosition(SwingConstants.CENTER);
        dLabel.setVerticalAlignment(SwingConstants.BOTTOM);
        add(dLabel);
        randnum = new Random();
        //DCard = getCard(52);
        total = 10;
        PCard="";
        P1Card="";

        cmbx = new JComboBox<String>(cards);
        JPanel options = makeOptionsPanel();
        add(options);
        setSize(800, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);


    } public void setPCard(String s){
        PCard =s;
    }
    /*public void setP1Card(String s){
        P1Card =  s+P1Card;
    }*/
    public int setScore(int t){
        total+=t;
        return total;
    }
    private JPanel makeOptionsPanel() {
        suitbox = addRadioBoxes(suit, "Suits",
                radio, groupSizes);
        combox = addComboBoxes(cards, cmbx);

        JPanel type = new JPanel();
        type.setLayout(new GridLayout(1, 2, 15, 10));
        type.add(suitbox);

        JPanel buttonControls = new JPanel();
        buttonControls.setBorder(BorderFactory.createEmptyBorder(1,
                10, 1, 10));
        JButton chk = new JButton("CHECK");
        chk.setActionCommand(check);

        chk.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int ttl;
                        int card = (randnum.nextInt(52));
                        dLabel.setIcon(icons[card]);
                        DCard = getCard(card);

                        if(PCard.equals(DCard)) {
                            ttl=setScore(10);
                        }
                        else if(PCard.charAt(PCard.length()-5)==
                                DCard.charAt(DCard.length()-5)) {
                            ttl=setScore(3);
                        }
                        else if (PCard.substring(0,PCard.length()-5).
                                equals(DCard.substring(0,DCard.length()-5))) {
                            ttl= setScore(5);
                        }
                        else {
                            ttl=setScore(-1);
                        }
                        if (total == 0) {
                            JOptionPane.showMessageDialog(SimpleCardGame.this,"You Lost!!!" );
                            total=10;
                        } else if(total>=25){

                            JOptionPane.showMessageDialog(SimpleCardGame.this,"You won!");
                            total=10;
                        }
                        score(ttl);
                    }
                }
        );

        buttonControls.add(chk);
        setLayout(new FlowLayout()); // set frame layout
        for (int i=0; i<52; i++){

            icons[i] = new ImageIcon(Objects.requireNonNull(getClass().getResource(getCard(i))));
        } icons[52] = new ImageIcon(Objects.requireNonNull(getClass().getResource(getCard(52))));
        add(cmbx); // add combobox to JFrame
        label = new JLabel(icons[0]); // display first icon
        dLabel = new JLabel(icons[52]);
        add(label); // add label to JFrame
        add(dLabel);

        JPanel si_ty_cont = new JPanel();

        si_ty_cont.setLayout(new GridLayout(2, 1, 15, 15));
        si_ty_cont.add(type);
        si_ty_cont.add(buttonControls);

        JPanel p = new JPanel();
        p.add(combox);
        p.add(si_ty_cont);
        p.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel completePanel = new JPanel();

        completePanel.setLayout(new BoxLayout(completePanel,
                BoxLayout.PAGE_AXIS));
        completePanel.setBackground(Color.DARK_GRAY);

        textArea = new JTextArea();
        textArea.setRows(1);
        JScrollPane sp = new JScrollPane(textArea);
        sp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        completePanel.add(Box.createVerticalGlue());
        completePanel.add(p);
        completePanel.add(Box.createVerticalGlue());
        //completePanel.add(jlb);
        completePanel.add(sp);

        return completePanel;
    }
    private int score(int tot) {

        textArea.setText("");
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Player's Score = %d", total));
        textArea.setText(sb.toString());
        textArea.setEditable(false);
        return tot+total;
    }
    private JPanel addRadioBoxes(String[] opts, String title, JRadioButton jrb[],
                                 ButtonGroup bg)
    {
        int rows = opts.length;
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));

        JPanel p1 = new JPanel();
        p1.setLayout(new GridLayout(rows, 1));


        for (int i = 0; i < rows; i++) {
            jrb[i] = new JRadioButton(opts[i]);
            int j=i;
            jrb[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    SimpleCardGame.this.label.setIcon(icons[mult+j *13]);
                    setPCard(getCard(mult+j*13));

                }
            });
            p1.add(jrb[i]);
            bg.add(jrb[i]);
        }
        p.add(p1);
        return p;
    }



    private JPanel addComboBoxes(String[] cards, final JComboBox<String> combobox)
    {
        int rows = cards.length;
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));

        JPanel p1 = new JPanel();
        p1.setLayout(new GridLayout(rows, 1));

        combobox.setMaximumRowCount(6);

        combobox.setBounds(600,300,50,50);
        combobox.addItemListener(
                new ItemListener() // anonymous inner class
                {   public int t;
                    @Override
                    public void itemStateChanged(ItemEvent event) {
                        // determine whether item selected
                        if (event.getStateChange() == ItemEvent.SELECTED) {
                            mult = combobox.getSelectedIndex();

                            SimpleCardGame.this.label.setIcon(icons[
                                    combobox.getSelectedIndex()]);

                        }
                    } // end anonymous inner class

                }); // end call to addItemListener

        cp.add(combobox);
        return p;
    }
    public static String getCardName(int c) {
        String[] cards = {"ace", "2", "3", "4", "5", "6", "7", "8",
                "9", "10", "jack", "queen", "king"};
        return cards[c];
    }
    public static char getSuit(int s) {
        char[] suit = {'c', 'h', 'd', 's'};
        return suit[s];
    }
    public static String getCard(int c) {
        final String[][] cardSuit = new String[4][13];
        final String[] cardSuits = new String[53];
        String str=" ";
        for (int i=0; i<4; i++){
            for (int j=0; j<13; j++){
                cardSuit[i][j] = getCardName(j)+getSuit(i)+".jpg";
            }
        }
        for (int i=0; i<52; i++){
            cardSuits[i] = cardSuit[i/13][i%13];
        } cardSuits[52] = "back.jpg";
        return cardSuits[c];
    }

}




