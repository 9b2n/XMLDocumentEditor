import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import org.w3c.dom.Attr;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlDocumentController {
	XmlDocumentView _view;
	ContentView _contView;
	String fileName, nodeName, text;

	public XmlDocumentController() {
		this._view = AppManager.getInstance().getView();
		this._view.addMenuButtonListener(new MenuButtonListener());
		this._view.addRadioButtonListener(new RadioButtonListener());
		this._view.addMenuButtonHovering(new Hovering());
		this._view.addGoUpButtonListener(new GoUpListener());
		this._view.addGoDownButtonListener(new GoDownListener());
		this._contView = AppManager.getInstance().getContentView();
	}

	private class RadioButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Object obj = e.getSource();

			_view.txtIndexInsert.setVisible(false);
			_view.setVisibleNodeOptionComponents(false);
			_view.setVisibleRadioInputComponents(false);
			_view.setVisibleBasicOptionComponents(true);
			_view.removeInputButtonListener();

			for (int i = 0; i < 4; i++) {
				if (obj == _view.rbtnNodeTypeArray[i]) {
					switch (i) {
					case ButtonConstants.ELEMENT:
						_view.lblInput.setText("Input Element Name");
						_view.addInputButtonListener(new ElementSearchListener());
						break;
					case ButtonConstants.ATTRIBUTE:
						_view.lblInput.setText("Input Attribute Name");
						_view.addInputButtonListener(new AttributeSearchListener());
						break;
					case ButtonConstants.TEXT:
						_view.lblInput.setText("Input Text Value");
						_view.addInputButtonListener(new TextSearchListener());
						break;
					case ButtonConstants.COMMENT:
						_view.lblInput.setText("Input Comment Value");
						_view.addInputButtonListener(new CommentSearchListener());
						break;
					}
				}
			}

		}

	}

	private class ElementSearchListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (!_view.txtInput.getText().equals("")) {
				new FindElement(_view.txtInput.getText());
				_view.setVisibleRadioInputComponents(false);
				_view.txtIndexOfNode[ButtonConstants.ELEMENT].setVisible(true);
				_view.btnInputArray[ButtonConstants.ELEMENT].setVisible(true);
			} else {
				_view.addMessageLabel(new JLabel("Enter Element Name."));
				_view.viewMessages();
			}

		}

	}

	private class AttributeSearchListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (!_view.txtInput.getText().equals("")) {
				new FindAttribute(_view.txtInput.getText());
				_view.setVisibleRadioInputComponents(false);
				_view.txtIndexOfNode[ButtonConstants.ATTRIBUTE].setVisible(true);
				_view.btnInputArray[ButtonConstants.ATTRIBUTE].setVisible(true);
			} else {
				_view.addMessageLabel(new JLabel("Enter Element Name."));
				_view.viewMessages();
			}

		}

	}

	private class TextSearchListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (!_view.txtInput.getText().equals("")) {
				new FindText(_view.txtInput.getText());
				_view.setVisibleRadioInputComponents(false);
				_view.txtIndexOfNode[ButtonConstants.TEXT].setVisible(true);
				_view.btnInputArray[ButtonConstants.TEXT].setVisible(true);
			} else {
				_view.addMessageLabel(new JLabel("Enter Text Value."));
				_view.viewMessages();
			}

		}

	}

	private class CommentSearchListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (!_view.txtInput.getText().equals("")) {
				new FindComment(" "+_view.txtInput.getText()+" ");
				_view.setVisibleRadioInputComponents(false);
				_view.txtIndexOfNode[ButtonConstants.COMMENT].setVisible(true);
				_view.btnInputArray[ButtonConstants.COMMENT].setVisible(true);
			} else {
				_view.addMessageLabel(new JLabel("Enter Comment Value."));
				_view.viewMessages();
			}
		}

	}

	private class MenuButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Object obj = e.getSource();

			for (int i = 0; i < 9; i++) {
				if (obj == _view.btnMenuArray[i]) {

					switch (i) {
					case ButtonConstants.LOAD:
						_view.buttonMode = ButtonConstants.LOAD;
						_view.removeInputButtonListener();
						_view.lblInput.setText("Input File Name");
						_view.txtInput.setText("");
						_view.setVisibleAllOptionComponents(false);
						_view.setVisibleBasicOptionComponents(true);
						_view.addInputButtonListener(new LoadInputListener());
						_view.makeButtonMessageLabel();
						_view.viewMessages();
						break;
					case ButtonConstants.MAKE:
						if (_view.getEditMode() == 0) {
							_view.setButtonMode(ButtonConstants.SAVE);
							_view.makeButtonMessageLabel();
							_view.removeInputButtonListener();
							_view.viewMessages();
							_view.txtInput.setText("");
							_view.setVisibleAllOptionComponents(false);
							_view.setVisibleBasicOptionComponents(true);
							_view.lblInput.setText("Input file name to save the previous file.");
							_view.addInputButtonListener(new SaveInputListener());
							_view.setButtonMode(ButtonConstants.MAKE);
						}
						if (_view.getEditMode() == 1 || _view.getEditMode() == 9) {
							_view.buttonMode = ButtonConstants.MAKE;
							_view.removeInputButtonListener();
							_view.setVisibleAllOptionComponents(false);
							_view.setVisibleBasicOptionComponents(true);
							_view.addInputButtonListener(new MakeInputListener());
							_view.makeButtonMessageLabel();
							_view.viewMessages();
						}
						break;
					case ButtonConstants.FIND:
						if (_view.editMode == 0 || _view.editMode == 1) {
							_view.setButtonMode(ButtonConstants.FIND);
							_view.setVisibleAllOptionComponents(false);
							_view.setVisibleRadioButtons(true);
							_view.txtInput.setText("");
							_view.makeButtonMessageLabel();
							_view.viewMessages();
							_view.removeInputButtonListener();
							_view.addRadioInputButtonListener(new FindInputListener());
						} else {
							JOptionPane.showMessageDialog(_view, "Please Load or Make first.");
						}
						_view.lblInput.setText("Input Node Name");
						break;
					case ButtonConstants.SAVE:
						if (_view.editMode == 0 || _view.editMode == 1) {
							_view.buttonMode = ButtonConstants.SAVE;
							_view.removeInputButtonListener();
							_view.setVisibleAllOptionComponents(false);
							_view.setVisibleBasicOptionComponents(true);
							_view.txtInput.setText("");
							_view.lblInput.setText("Input file name to save file.");
							_view.makeButtonMessageLabel();
							_view.viewMessages();
							_view.addInputButtonListener(new SaveInputListener());
						} else {
							JOptionPane.showMessageDialog(_view, "Please Load or Make first.");
						}
						_view.lblInput.setText("Input File Name");
						break;
					case ButtonConstants.PRINT:
						_view.setButtonMode(ButtonConstants.PRINT);
						if (_view.editMode == 0 || _view.editMode == 1) {
							new PrintXmlFile();
							_view.makeCompleteMessageLabel();
							_view.viewMessages();
						} else {
							JOptionPane.showMessageDialog(_view, "Please Load or Make first.");
						}
						break;
					case ButtonConstants.INSERT:
						if (_view.editMode == 0 || _view.editMode == 1) {
							_view.buttonMode = ButtonConstants.INSERT;
							_view.removeInputButtonListener();
							_view.setVisibleAllOptionComponents(false);
							_view.setVisibleNodeOptionComponents(true);
							_view.getTxtElem().setBounds(90, 25, 76, 20);
							_view.txtIndexInsert.setVisible(true);
							_view.lblCurrentNode.setText(AppManager.getInstance().getCurrentNode().getNodeName());
							if (AppManager.getInstance().getParentNode() == null)
								_view.lblParentNode.setText("null");
							else
								_view.lblParentNode.setText(AppManager.getInstance().getParentNode().getNodeName());
							_view.addInputButtonListener(new InsertInputListener());
							_view.lblInput.setText("Input Node Name");
						} else {
							JOptionPane.showMessageDialog(_view, "Please Load or Make first.");
						}
						break;
					case ButtonConstants.UPDATE:
						if (_view.editMode == 0 || _view.editMode == 1) {
							_view.buttonMode = ButtonConstants.UPDATE;
							_view.removeInputButtonListener();
							_view.setVisibleAllOptionComponents(false);
							_view.setVisibleRadioButtons(true);
							_view.getTxtElem().setBounds(90, 25, 76, 20);
							_view.lblInput.setText("Input Node Name");
							_view.removeRadioInputButtonListener();
							_view.addRadioInputButtonListener(new UpdateInputListener());
						} else {
							JOptionPane.showMessageDialog(_view, "Please Load or Make first.");
						}
						break;
					case ButtonConstants.DELETE:
						if (_view.editMode == 0 || _view.editMode == 1) {
							_view.buttonMode = ButtonConstants.DELETE;
							_view.removeInputButtonListener();
							_view.setVisibleAllOptionComponents(false);
							_view.setVisibleRadioButtons(true);
							_view.getTxtElem().setBounds(90, 25, 76, 20);
							_view.lblInput.setText("Input Node Name");
							_view.removeRadioInputButtonListener();
							_view.addRadioInputButtonListener(new DeleteInputListener());
						} else {
							JOptionPane.showMessageDialog(_view, "Please Load or Make first.");
						}
						break;
					case ButtonConstants.EXIT:
						_view.setButtonMode(ButtonConstants.EXIT);
						if (_view.editMode == 0 || _view.editMode == 1) {
							_view.setButtonMode(ButtonConstants.SAVE);
							_view.makeButtonMessageLabel();
							_view.removeInputButtonListener();
							_view.viewMessages();
							_view.txtInput.setText("");
							_view.setVisibleAllOptionComponents(false);
							_view.setVisibleBasicOptionComponents(true);
							_view.lblInput.setText("Input file name to save the previous file.");
							_view.addInputButtonListener(new SaveInputListener());
							_view.setButtonMode(ButtonConstants.EXIT);
						}
						else System.exit(1);
						break;
					}
					break;
				}
			}

		}

	}

	private class Hovering implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			JButton btnEvent = (JButton) e.getSource();
			btnEvent.setBackground(ButtonConstants.MENU_COLOR[2]);
			btnEvent.setForeground(ButtonConstants.MENU_COLOR[3]);

		}

		@Override
		public void mouseExited(MouseEvent e) {
			JButton btnEvent = (JButton) e.getSource();
			btnEvent.setBackground(ButtonConstants.MENU_COLOR[0]);
			btnEvent.setForeground(ButtonConstants.MENU_COLOR[1]);

		}

	}

	private class GoUpListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			if (AppManager.getInstance().getParentNode() == null
					|| _view.getButtonMode() == ButtonConstants.MAKE && AppManager.getInstance().getParentNode() == AppManager.getInstance().getDocument()) {
				_view.addMessageLabel(new JLabel("Can't Go Up."));
				_view.viewMessages();
			}  else {
				AppManager.getInstance().setCurrentNode(AppManager.getInstance().getParentNode());
				AppManager.getInstance().setParentNode(AppManager.getInstance().getCurrentNode().getParentNode());
				_view.lblCurrentNode.setText(AppManager.getInstance().getCurrentNode().getNodeName());
				if (AppManager.getInstance().getCurrentNode().equals(AppManager.getInstance().getDocument()))
					_view.lblParentNode.setText("null");
				else
					_view.lblParentNode.setText(AppManager.getInstance().getParentNode().getNodeName());
			}

		}

	}

	private class GoDownListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (_view.txtIndexChild.getText().equals("")
					|| AppManager.getInstance().getCurrentNode().getChildNodes().getLength() == 0
					|| Integer.valueOf(_view.txtIndexChild.getText()) > AppManager.getInstance().getCurrentNode()
							.getChildNodes().getLength()
					|| _view.txtIndexInsert.getText().equals("0")) {
				_view.addMessageLabel(new JLabel("Can't Go Down."));
				_view.viewMessages();
			} else {
				Node current = AppManager.getInstance().getCurrentNode();
				Node parent = AppManager.getInstance().getParentNode();
				int index = Integer.valueOf(_view.txtIndexChild.getText());
				NodeList list = current.getChildNodes();

				if (current.getNodeType() == Node.DOCUMENT_NODE || current.getChildNodes().getLength() == 1) {
					AppManager.getInstance().setParentNode(current);
					AppManager.getInstance().setCurrentNode(list.item(index - 1));
				} else {
					AppManager.getInstance().setParentNode(current);
					AppManager.getInstance().setCurrentNode(list.item(index * 2 - 1));
				}

				_view.lblCurrentNode.setText(AppManager.getInstance().getCurrentNode().getNodeName());
				_view.lblParentNode.setText(AppManager.getInstance().getParentNode().getNodeName());
			}
			_view.txtIndexChild.setText("");
		}

	}

	private class LoadInputListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			new LoadXmlFile(_view.txtInput.getText() + ".xml");
		}
	}

	private class MakeInputListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			fileName = _view.txtInput.getText();
			_view.setEditMode(ButtonConstants.MAKE);
			_view.lblInput.setText("Insert Root Node Name");
			_view.setVisibleAllOptionComponents(false);
			_view.setVisibleRootNodeOptionComponents(true);
			_view.setVisibleUpOptionComponents(false);
			_view.removeInputButtonListener();
			_view.addInputButtonListener(new NodeInputListener());
		}

	}

	private class FindInputListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Object obj = e.getSource();

			for (int i = 0; i < 4; i++) {
				if (obj == _view.btnInputArray[i]) {
					switch (i) {
					case ButtonConstants.ELEMENT:
						new FindNodes(_view.txtInput.getText(), i, Integer.valueOf(_view.txtIndexOfNode[i].getText()));
						_view.txtInput.setText("");
						break;
					case ButtonConstants.ATTRIBUTE:
						new FindNodes(_view.txtInput.getText(), i, Integer.valueOf(_view.txtIndexOfNode[i].getText()));
						_view.txtInput.setText("");
						_view.txtIndexOfNode[ButtonConstants.ATTRIBUTE].setText("");
						break;
					case ButtonConstants.TEXT:
						new FindNodes(_view.txtInput.getText(), i, Integer.valueOf(_view.txtIndexOfNode[i].getText()));
						_view.txtInput.setText("");
						break;
					case ButtonConstants.COMMENT:
						new FindNodes(" "+_view.txtInput.getText()+" ", i, Integer.valueOf(_view.txtIndexOfNode[i].getText()));
						
						_view.txtInput.setText("");
						break;
					}
				}
			}

		}

	}

	private class InsertInputListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			if (AppManager.getInstance().getParentNode() == null || AppManager.getInstance().getCurrentNode().getNodeType() != Node.ELEMENT_NODE) {
				_view.addMessageLabel(new JLabel("Can't Insert"));
				_view.viewMessages();
				return;
			}
			
			_contView.txtArea.setText("");

			if (!_view.getTxtElem().getText().equals("")) {
				Node current = AppManager.getInstance().getCurrentNode();
				Document doc = AppManager.getInstance().getDocument();
				Element item = doc.createElement(_view.getTxtElem().getText());
				NodeList children = current.getChildNodes();

				if (!current.hasChildNodes())
					current.appendChild(doc.createTextNode("\n"));
				if (_view.txtIndexInsert.getText().equals("")
						|| Integer.valueOf(_view.txtIndexInsert.getText()) > children.getLength() / 2) {
					current.appendChild(item);
					current.appendChild(doc.createTextNode("\n"));
				} else {
					current.insertBefore(doc.createTextNode("\n"),
							children.item(Integer.valueOf(_view.txtIndexInsert.getText()) * 2 - 1));
					current.insertBefore(item, children.item(Integer.valueOf(_view.txtIndexInsert.getText())));
				} // not blink insert

				AppManager.getInstance().setParentNode(AppManager.getInstance().getCurrentNode());
				AppManager.getInstance().setCurrentNode(item);
				_view.getTxtElem().setText("");
				_view.lblCurrentNode.setText(AppManager.getInstance().getCurrentNode().getNodeName());
				_contView.txtArea.append("Element Node: " + item.getNodeName() + "\n");
			} // element

			if (!_view.getTxtAttr().getText().equals("")) {

				Attr attrItem = AppManager.getInstance().getDocument().createAttribute(_view.getTxtAttr().getText());
				
				_contView.txtArea.append("Attribute Node: " + attrItem.getNodeName() + "\n");
				if (!_view.txtAttrVal.getText().equals("")) {
					attrItem.setValue(_view.txtAttrVal.getText());
					_contView.txtArea.append("Attribute Value: " + attrItem.getNodeValue() + "\n");
				}

				((Element) AppManager.getInstance().getCurrentNode()).setAttributeNode(attrItem);
				_view.getTxtAttr().setText("");
				_view.txtAttrVal.setText("");
			}

			if (!_view.getTxtText().getText().equals("")) {
				((Element) AppManager.getInstance().getCurrentNode())
						.appendChild(AppManager.getInstance().getDocument().createTextNode(_view.getTxtText().getText()));
				_contView.txtArea.append("Text Node: " + AppManager.getInstance().getCurrentNode().getFirstChild().getNodeValue() + "\n");
				_view.getTxtText().setText("");
			}

			if (!_view.getTxtCmt().getText().equals("")) {
				Node current = AppManager.getInstance().getCurrentNode();
				Node parent = AppManager.getInstance().getParentNode();
				Document doc = AppManager.getInstance().getDocument();
				Comment item = doc.createComment(_view.getTxtCmt().getText());

				if (current != doc.getDocumentElement()) {
					current.getParentNode().insertBefore(item, current);
					current.getParentNode().insertBefore(doc.createTextNode("\n"), current);
				}

				_contView.txtArea.append("Comment Node: " + item.getNodeValue() + "\n");
				_view.getTxtCmt().setText("");

			}

		}

	}

	private class UpdateInputListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Object obj = e.getSource();

			for (int i = 0; i < 4; i++) {
				if (obj == _view.btnInputArray[i]) {
					switch (i) {
					case ButtonConstants.ELEMENT:
						new FindNodes(_view.txtInput.getText(), i, Integer.valueOf(_view.txtIndexOfNode[i].getText()));
						_view.txtInput.setText("");
						_view.removeInputButtonListener();
						_view.addInputButtonListener(new ChangeElementInputListener());
						break;
					case ButtonConstants.ATTRIBUTE:
						new FindNodes(_view.txtInput.getText(), i, Integer.valueOf(_view.txtIndexOfNode[i].getText()));
						_view.txtInput.setText("");
						_view.txtIndexOfNode[ButtonConstants.ATTRIBUTE].setText("");
						_view.removeInputButtonListener();
						_view.addInputButtonListener(new ChangeElementInputListener());
						break;
					case ButtonConstants.TEXT:
						new FindNodes(_view.txtInput.getText(), i, Integer.valueOf(_view.txtIndexOfNode[i].getText()));
						_view.txtInput.setText("");
						_view.removeInputButtonListener();
						_view.addInputButtonListener(new ChangeTextInputListener());
						break;
					case ButtonConstants.COMMENT:
						new FindNodes(_view.txtInput.getText(), i, Integer.valueOf(_view.txtIndexOfNode[i].getText()));
						_view.txtInput.setText("");
						_view.removeInputButtonListener();
						_view.addInputButtonListener(new ChangeTextInputListener());
						break;
					}
				}
			}
		}

	}

	private class ChangeElementInputListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			AppManager.getInstance().getDocument().renameNode(AppManager.getInstance().getCurrentNode(), null,
					_view.txtInput.getText());
			if (!_view.txtIndexOfNode[ButtonConstants.ATTRIBUTE].getText().equals("")) {
				AppManager.getInstance().getCurrentNode()
						.setNodeValue(_view.txtIndexOfNode[ButtonConstants.ATTRIBUTE].getText());
				_view.txtIndexOfNode[ButtonConstants.ATTRIBUTE].setText("");
			}
			_view.txtInput.setText("");
		}

	}

	private class ChangeTextInputListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			AppManager.getInstance().getCurrentNode().setNodeValue(_view.txtInput.getText());
			_view.txtInput.setText("");
		}

	}

	private class DeleteInputListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Object obj = e.getSource();

			for (int i = 0; i < 4; i++) {
				if (obj == _view.btnInputArray[i]) {

					switch (i) {
					case ButtonConstants.ELEMENT:
						new FindNodes(_view.txtInput.getText(), i, Integer.valueOf(_view.txtIndexOfNode[i].getText()));
						new DeleteXmlFile(_view.txtInput.getText(), i);
						_view.txtIndexOfNode[i].setText("");
						_view.txtInput.setText("");
						break;
					case ButtonConstants.ATTRIBUTE:
						new FindNodes(_view.txtInput.getText(), i, Integer.valueOf(_view.txtIndexOfNode[i].getText()));
						new DeleteXmlFile(_view.txtInput.getText(), i);
						_view.txtIndexOfNode[i].setText("");
						_view.txtInput.setText("");
						break;
					case ButtonConstants.TEXT:
						new FindNodes(_view.txtInput.getText(), i, Integer.valueOf(_view.txtIndexOfNode[i].getText()));
						new DeleteXmlFile(_view.txtInput.getText(), i);
						_view.txtIndexOfNode[i].setText("");
						_view.txtInput.setText("");
						break;
					case ButtonConstants.COMMENT:
						new FindNodes(_view.txtInput.getText(), i, Integer.valueOf(_view.txtIndexOfNode[i].getText()));
						new DeleteXmlFile(_view.txtInput.getText(), i);
						_view.txtIndexOfNode[i].setText("");
						_view.txtInput.setText("");
						break;
					}
				}
			}
		}

	}

	private class NodeInputListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			_view.setEditMode(ButtonConstants.MAKE);

			if (AppManager.getInstance().getParentNode() == null) {
				if (!_view.getTxtElem().getText().equals("")) {

					MakeXmlFile make = new MakeXmlFile();
					Element root = AppManager.getInstance().getDocument().createElement(_view.getTxtElem().getText());

					AppManager.getInstance().getDocument().appendChild(root);
					make.makeXmlFile(fileName + ".xml");
					_view.setEditMode(ButtonConstants.MAKE);
					AppManager.getInstance().setParentNode(AppManager.getInstance().getDocument());
					AppManager.getInstance().setCurrentNode(root);
					_view.lblInput.setText("Input Node Name");
					_view.getTxtElem().setText("");
					_view.setVisibleNodeOptionComponents(true);
					_view.setVisibleDownOptionComponents(false);
					_view.lblCurrentNode.setText(AppManager.getInstance().getCurrentNode().getNodeName());
				} else
				return;
			} // insert root node

			if (!_view.getTxtElem().getText().equals("")) {
				Node current = AppManager.getInstance().getCurrentNode();
				Document doc = AppManager.getInstance().getDocument();
				Element item = doc.createElement(_view.getTxtElem().getText());

				if (!current.hasChildNodes())
					current.appendChild(doc.createTextNode("\n"));

				current.appendChild(item);
				current.appendChild(doc.createTextNode("\n"));

				AppManager.getInstance().setParentNode(AppManager.getInstance().getCurrentNode());
				AppManager.getInstance().setCurrentNode(item);
				_view.getTxtElem().setText("");
				_view.lblCurrentNode.setText(AppManager.getInstance().getCurrentNode().getNodeName());
			} // insert element

			if (!_view.getTxtAttr().getText().equals("")) {

				Attr attrItem = AppManager.getInstance().getDocument().createAttribute(_view.getTxtAttr().getText());

				if (!_view.txtAttrVal.getText().equals(""))
					attrItem.setValue(_view.txtAttrVal.getText());

				((Element) AppManager.getInstance().getCurrentNode()).setAttributeNode(attrItem);
				_view.getTxtAttr().setText("");
				_view.txtAttrVal.setText("");
			} // insert attr

			if (!_view.getTxtText().getText().equals("")) {
				if (AppManager.getInstance().getCurrentNode().hasChildNodes())
					((Element) AppManager.getInstance().getCurrentNode()).appendChild(
							AppManager.getInstance().getDocument().createTextNode(_view.getTxtText().getText()));
				_view.getTxtText().setText("");
			} // insert text

			if (!_view.getTxtCmt().getText().equals("")) {
				Node current = AppManager.getInstance().getCurrentNode();
				Node parent = AppManager.getInstance().getParentNode();
				Document doc = AppManager.getInstance().getDocument();
				Comment item = doc.createComment(_view.getTxtCmt().getText());

				if (current != doc.getDocumentElement()) {
					current.getParentNode().insertBefore(item, current);
					current.getParentNode().insertBefore(doc.createTextNode("\n"), current);
				} else {
					_view.addMessageLabel(new JLabel("Can't insert Comment."));
					_view.viewMessages();
				}

				_view.getTxtCmt().setText("");
			} // inset comment

		}

	}

	private class SaveInputListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			new SaveXmlFile(_view.txtInput.getText() + ".xml");
			_view.makeCompleteMessageLabel();
			_view.viewMessages();
			_view.lblInput.setText("Input File Name");
			_view.txtInput.setText("");

			if (_view.getButtonMode() == ButtonConstants.MAKE) {
				_view.removeInputButtonListener();
				_view.addInputButtonListener(new MakeInputListener());
				_view.makeButtonMessageLabel();
				_view.viewMessages();
				_view.setEditMode(ButtonConstants.MAKE);
			} else {
				_view.removeInputButtonListener();
				_view.addInputButtonListener(new LoadInputListener());
				_view.setButtonMode(ButtonConstants.LOAD);
				_view.makeButtonMessageLabel();
				_view.viewMessages();
				_view.setEditMode(9);
			}
		}
	}

}
