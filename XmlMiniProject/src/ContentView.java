import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class ContentView extends JPanel{
	JTextArea txtArea;
	JScrollPane scroll;
	
	public ContentView() {
		setBackground(Color.white);
		setBounds(0, 0, 500, 500);
		setBorder(BorderFactory.createTitledBorder("CONTENT"));
		setLayout(null);
		
		txtArea = new JTextArea();
		txtArea.setEditable(false);
		txtArea.setFont(new Font("Verdana", Font.PLAIN, 12));
		scroll = new JScrollPane(txtArea);
		scroll.setBounds(5, 15, 490, 480);
		scroll.setVisible(true);
		scroll.getVerticalScrollBar().setUI(new MyScrollbarUI());
		scroll.getHorizontalScrollBar().setUI(new MyScrollbarUI());
		add(scroll);
		
		AppManager.getInstance().setContentView(this);
	}
	
	public JTextArea getTexTArea() {
		return this.txtArea;
	}
	
	class MyScrollbarUI extends BasicScrollBarUI {

		@Override
        protected JButton createDecreaseButton(int orientation) {
            return createZeroButton();
        }

        @Override    
        protected JButton createIncreaseButton(int orientation) {
            return createZeroButton();
        }

        private JButton createZeroButton() {
            JButton jbutton = new JButton();
            jbutton.setPreferredSize(new Dimension(0, 0));
            jbutton.setMinimumSize(new Dimension(0, 0));
            jbutton.setMaximumSize(new Dimension(0, 0));
            return jbutton;
        }
    }
}
