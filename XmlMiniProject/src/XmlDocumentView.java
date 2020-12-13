import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class XmlDocumentView extends JPanel {

	private JPanel menuPanel, messagePanel;
	private JPanel btnPanel, inputPanel;
	public JButton btnMenuArray[], btnInputArray[];
	public JLabel lblInput, lblNodeTypeArray[];
	public JLabel lblCurrent, lblCurrentNode, lblParent, lblParentNode;	
	public JTextField txtInput, txtAttrVal, txtIndexChild, txtIndexInsert;
	public JTextField txtIndexOfNode[], txtNodeTypeArray[];
	public JButton btnInput, btnUp, btnDown;
	private ContentView contView;
	public JRadioButton[] rbtnNodeTypeArray;
	private ButtonGroup rbtnGroup;
	private Font font = new Font("verdana", Font.BOLD, 10);
	private Color btnColor = new Color(163, 184, 204);
	public int editMode = 9; // default value
	public int buttonMode = 9; // default value
	ArrayList<JLabel> arrHistory;
	JLabel lblHistory;

	public XmlDocumentView() {
		setBackground(Color.white);
		setPreferredSize(new Dimension(800, 700));
		setLayout(null);

		arrHistory = new ArrayList<JLabel>();
		lblHistory = new JLabel();
		lblHistory.setSize(450, 30);

		AppManager.getInstance().setView(this);

		contView = new ContentView();
		add(contView);

		menuPanel = new JPanel();
		menuPanel.setBackground(Color.white);
		menuPanel.setBounds(500, 0, 298, 697);
		menuPanel.setLayout(null);
		menuPanel.setBorder(BorderFactory.createTitledBorder("MENU"));
		add(menuPanel);

		messagePanel = new JPanel();
		messagePanel.setBackground(Color.white);
		messagePanel.setBounds(0, 500, 500, 197);
		messagePanel.setLayout(null);
		messagePanel.setBorder(BorderFactory.createTitledBorder("MESSAGE"));
		add(messagePanel);

		btnPanel = new JPanel();
		btnPanel.setBackground(Color.white);
		btnPanel.setBounds(5, 20, 290, 190);
		btnPanel.setLayout(new GridLayout(3, 3));
		menuPanel.add(btnPanel);

		inputPanel = new JPanel();
		inputPanel.setBackground(btnColor);
		inputPanel.setBounds(5, 210, 288, 290);
		inputPanel.setLayout(null);
		menuPanel.add(inputPanel);

		btnMenuArray = new JButton[9];
		for (int i = 0; i < 9; i++) {
			btnMenuArray[i] = new JButton(ButtonConstants.MENU[i]);
			btnMenuArray[i].setBackground(btnColor);
			btnMenuArray[i].setForeground(Color.black);
			btnMenuArray[i].setFont(font);
			btnMenuArray[i].setBorder(BorderFactory.createBevelBorder(0, Color.white, Color.black));
			btnPanel.add(btnMenuArray[i]);
		}

		lblInput = new JLabel("Input File Name");
		lblInput.setBounds(5, 2, 278, 20);
		lblInput.setHorizontalAlignment(SwingConstants.CENTER);
		lblInput.setBackground(Color.white);
		lblInput.setOpaque(true);
		lblInput.setBorder(BorderFactory.createBevelBorder(0, Color.gray, Color.black));
		inputPanel.add(lblInput);

		txtInput = new JTextField();
		txtInput.setBounds(5, 25, 198, 20);
		txtInput.setVisible(false);
		txtInput.setBorder(BorderFactory.createBevelBorder(1, Color.gray, Color.gray));

		btnInput = new JButton("Input");
		btnInput.setBounds(218, 25, 65, 20);
		btnInput.setBackground(Color.black);
		btnInput.setForeground(Color.white);
		btnInput.setBorder(BorderFactory.createBevelBorder(0, Color.white, Color.gray));

		inputPanel.add(txtInput);
		inputPanel.add(btnInput);

		lblNodeTypeArray = new JLabel[4];
		for (int i=0;i<4;i++) {
			switch(i) {
			case ButtonConstants.ELEMENT: lblNodeTypeArray[i] = new JLabel("Element:"); break;
			case ButtonConstants.ATTRIBUTE: lblNodeTypeArray[i] = new JLabel("Attribute:"); break;
			case ButtonConstants.TEXT: lblNodeTypeArray[i] = new JLabel("Text:"); break;
			case ButtonConstants.COMMENT: lblNodeTypeArray[i] = new JLabel("Comment:"); break;
			}
			lblNodeTypeArray[i].setBounds(15, 25+(i*30), 70, 20);
			inputPanel.add(lblNodeTypeArray[i]);
		}

		lblCurrent = new JLabel("Current:");
		lblCurrent.setBounds(15, 145, 70, 20);

		lblCurrentNode = new JLabel("doc");
		lblCurrentNode.setBounds(90, 145, 113, 20);
		
		lblParent = new JLabel("Parent:");
		lblParent.setBounds(15, 175, 70, 20);

		lblParentNode = new JLabel();
		lblParentNode.setBounds(90, 175, 76, 20);
		
		txtIndexChild = new JTextField();
		txtIndexChild.setBounds(166, 175, 37, 20);

		inputPanel.add(lblCurrent);
		inputPanel.add(lblCurrentNode);
		inputPanel.add(lblParent);
		inputPanel.add(lblParentNode);
		inputPanel.add(txtIndexChild);
		
		txtNodeTypeArray = new JTextField[4];
		for (int i=0;i<4;i++) {
			txtNodeTypeArray[i] = new JTextField();
			if(i != ButtonConstants.ATTRIBUTE)
				txtNodeTypeArray[i].setBounds(90, 25+(i*30), 113, 20);
			else txtNodeTypeArray[i].setBounds(90, 55, 56, 20);
			inputPanel.add(txtNodeTypeArray[i]);
		}
		
		txtIndexInsert = new JTextField();
		txtIndexInsert.setBounds(166, 25, 37, 20);
		txtIndexInsert.setVisible(false);

		txtAttrVal = new JTextField();
		txtAttrVal.setBounds(146, 55, 57, 20);
		
		inputPanel.add(txtAttrVal);
		inputPanel.add(txtIndexInsert);

		btnUp = new JButton("▲");
		btnUp.setBounds(218, 145, 65, 20);
		btnUp.setBackground(Color.white);
		btnDown = new JButton("▼");
		btnDown.setBounds(218, 175, 65, 20);
		btnDown.setBackground(Color.white);

		inputPanel.add(btnUp);
		inputPanel.add(btnDown);
		
		rbtnGroup = new ButtonGroup();
		rbtnNodeTypeArray = new JRadioButton[4];
		for (int i = 0; i < 4; i++) {
			rbtnNodeTypeArray[i] = new JRadioButton(ButtonConstants.TYPE[i]);
			rbtnNodeTypeArray[i].setBounds(5, 55+(i*30), 100, 20);
			rbtnNodeTypeArray[i].setBackground(btnColor);
			inputPanel.add(rbtnNodeTypeArray[i]);
			rbtnGroup.add(rbtnNodeTypeArray[i]);
		}
		
		txtIndexOfNode = new JTextField[4];
		for (int i = 0; i < 4; i++) {
			txtIndexOfNode[i] = new JTextField();
			txtIndexOfNode[i].setBounds(110, 55, 90, 20);
			inputPanel.add(txtIndexOfNode[i]);
		}
		
		btnInputArray = new JButton[4];
		for (int i = 0; i < 4; i++) {
			btnInputArray[i] = new JButton("Input");
			btnInputArray[i].setBounds(218, 55, 65, 20);
			btnInputArray[i].setBackground(Color.white);
			inputPanel.add(btnInputArray[i]);
		}
		
		setVisibleAllOptionComponents(false);
	}

	public void addMessageLabel(JLabel addLabel) {
		arrHistory.add(0, addLabel);
	}

	public void makeButtonMessageLabel() {
		lblHistory = new JLabel();
		switch (buttonMode) {
		case ButtonConstants.LOAD:
			lblHistory.setText("Input a xml filename without extension.");
			arrHistory.add(0, lblHistory);
			break;
		case ButtonConstants.MAKE:
			lblHistory.setText("Input a xml filename without extension.");
			arrHistory.add(0, lblHistory);
			break;
		case ButtonConstants.FIND:
			lblHistory.setText("Find the node with the entered name.");
			arrHistory.add(0, lblHistory);
			break;
		case ButtonConstants.SAVE:
			lblHistory.setText("Save as the entered filename.");
			arrHistory.add(0, lblHistory);
			break;
		case ButtonConstants.PRINT:
			lblHistory.setText("Print the loaded file.");
			arrHistory.add(0, lblHistory);
			break;
		case ButtonConstants.INSERT:
			lblHistory.setText("Insert the node with the entered name.");
			arrHistory.add(0, lblHistory);
			break;
		case ButtonConstants.UPDATE:
			lblHistory.setText("Update");
			arrHistory.add(0, lblHistory);
			break;
		case ButtonConstants.DELETE:
			lblHistory.setText("Delete the node with the entered name.");
			arrHistory.add(0, lblHistory);
			break;
		}
	}

	public void makeCompleteMessageLabel() {
		lblHistory = new JLabel();
		switch (buttonMode) {
		case ButtonConstants.LOAD:
			lblHistory.setText("File loading completed.");
			arrHistory.add(0, lblHistory);
			break;
		case ButtonConstants.MAKE:
			lblHistory.setText("File creation completed.");
			arrHistory.add(0, lblHistory);
			break;
		case ButtonConstants.FIND:
			lblHistory.setText("Node found.");
			arrHistory.add(0, lblHistory);
			break;
		case ButtonConstants.SAVE:
			lblHistory.setText("File saved successfully.");
			arrHistory.add(0, lblHistory);
			break;
		case ButtonConstants.PRINT:
			lblHistory.setText("Print completed.");
			arrHistory.add(0, lblHistory);
			break;
		case ButtonConstants.INSERT:
			lblHistory.setText("Inserting node completed.");
			arrHistory.add(0, lblHistory);
			break;
		case ButtonConstants.UPDATE:
			lblHistory.setText("Node update completed.");
			arrHistory.add(0, lblHistory);
			break;
		case ButtonConstants.DELETE:
			lblHistory.setText("Node delete completed.");
			arrHistory.add(0, lblHistory);
			break;
		}
	}

	public void setContentAreaFilledAllButton() {
		
	}
	
	public void removeInputButtonListener() {
		for (ActionListener al : this.btnInput.getActionListeners()) {
			btnInput.removeActionListener(al);
		}
	}

	public void viewMessages() {
		int i = 0;
		messagePanel.removeAll();
		for (JLabel history : arrHistory) {
			if (i > 8)
				break;
			history.setFont(new Font("Verdana", Font.PLAIN, 18));
			history.setBounds(5, 180 - (i++ * 20 + 10), 490, 20);
			messagePanel.add(history);
		}
		messagePanel.repaint();
	}

	public void setVisibleBasicOptionComponents(boolean flag) {
		lblInput.setVisible(flag);
		txtInput.setVisible(flag);
		btnInput.setVisible(flag);
	}

	public void setVisibleNodeOptionComponents(boolean flag) {
		for(int i=0;i<4;i++)
			lblNodeTypeArray[i].setVisible(flag);
		
		for(int i=0;i<4;i++)
			txtNodeTypeArray[i].setVisible(flag);

		txtAttrVal.setVisible(flag);

		lblInput.setVisible(flag);
		btnInput.setVisible(flag);

		lblCurrent.setVisible(flag);
		lblCurrentNode.setVisible(flag);
		lblParent.setVisible(flag);
		lblParentNode.setVisible(flag);
		txtIndexChild.setVisible(flag);
		btnUp.setVisible(flag);
		btnDown.setVisible(flag);
	}

	public void setVisibleRootNodeOptionComponents(boolean flag) {
		lblInput.setVisible(flag);
		lblNodeTypeArray[0].setVisible(flag);
		txtNodeTypeArray[0].setVisible(flag);
		btnInput.setVisible(flag);
		lblCurrent.setVisible(flag);
		lblCurrentNode.setVisible(flag);
		btnUp.setVisible(flag);
	}
	
	public void setVisibleUpOptionComponents(boolean flag) {
		lblCurrent.setVisible(flag);
		lblCurrentNode.setVisible(flag);
		btnUp.setVisible(flag);
	}
	
	public void setVisibleDownOptionComponents(boolean flag) {
		lblParent.setVisible(flag);
		lblParentNode.setVisible(flag);
		txtIndexChild.setVisible(flag);
		btnDown.setVisible(flag);
	}

	public void setVisibleRadioButtons(boolean flag) {
		for(int i=0;i<4;i++)
			rbtnNodeTypeArray[i].setVisible(flag);
	}
	
	public void setVisibleRadioInputComponents(boolean flag) {
		for(int i=0;i<4;i++)
			txtIndexOfNode[i].setVisible(flag);
		
		for(int i=0;i<4;i++)
			btnInputArray[i].setVisible(flag);
	}
	
	public void setVisibleAllOptionComponents(boolean flag) {
		for(int i=0;i<4;i++)
			txtIndexOfNode[i].setVisible(flag);
		
		for(int i=0;i<4;i++)
			btnInputArray[i].setVisible(flag);
		
		for(int i=0;i<4;i++)
			rbtnNodeTypeArray[i].setVisible(flag);
		
		for(int i=0;i<4;i++)
			lblNodeTypeArray[i].setVisible(flag);
		
		for(int i=0;i<4;i++)
			txtNodeTypeArray[i].setVisible(flag);
		
		txtAttrVal.setVisible(flag);
		txtIndexInsert.setVisible(flag);
		
		lblInput.setVisible(flag);
		btnInput.setVisible(flag);
		txtInput.setVisible(flag);
		
		lblCurrent.setVisible(flag);
		lblCurrentNode.setVisible(flag);
		lblParent.setVisible(flag);
		lblParentNode.setVisible(flag);
		txtIndexChild.setVisible(flag);
		btnUp.setVisible(flag);
		btnDown.setVisible(flag);
		
	}
	
	public JTextField getTxtElem() {
		return txtNodeTypeArray[0];
	}
	
	public JTextField getTxtAttr() {
		return txtNodeTypeArray[1];
	}
	
	public JTextField getTxtText() {
		return txtNodeTypeArray[2];
	}
	
	public JTextField getTxtCmt() {
		return txtNodeTypeArray[3];
	}
	
	public int getEditMode() {
		return this.editMode;
	}

	public void setEditMode(int changeMode) {
		this.editMode = changeMode;
	}

	public int getButtonMode() {
		return this.buttonMode;
	}

	public void setButtonMode(int changeMode) {
		this.buttonMode = changeMode;
	}

	public void addMenuButtonListener(ActionListener listener) {
		for (int i = 0; i < 9; i++)
			btnMenuArray[i].addActionListener(listener);
	}
	
	public void addRadioInputButtonListener(ActionListener listener) {
		for (int i = 0; i < 4; i++)
			btnInputArray[i].addActionListener(listener);
	}
	
	public void removeRadioInputButtonListener() {
		for (int i = 0; i < 4; i++)
			for (ActionListener al : this.btnInputArray[i].getActionListeners()) {
				btnInputArray[i].removeActionListener(al);
			}
	}
	
	public void addRadioButtonListener(ActionListener listener) {
		for (int i = 0; i < 4; i++)
			rbtnNodeTypeArray[i].addActionListener(listener);
	}

	public void addInputButtonListener(ActionListener listener) {
		btnInput.addActionListener(listener);
	}

	public void addGoUpButtonListener(ActionListener listener) {
		btnUp.addActionListener(listener);
	}
	
	public void addGoDownButtonListener(ActionListener listener) {
		btnDown.addActionListener(listener);
	}

	public void addMenuButtonHovering(MouseListener listener) {
		for (int i = 0; i < 9; i++)
			btnMenuArray[i].addMouseListener(listener);
	}

	public JTextField getTextField() {
		return this.txtInput;
	}

	public String getTextInTextField() {
		return this.txtInput.getText();
	}

	public ArrayList<JLabel> getArrHistory() {
		return this.arrHistory;
	}

}
