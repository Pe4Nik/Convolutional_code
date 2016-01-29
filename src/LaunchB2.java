import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class LaunchB2 {
	private Joint myFacadeClass;
	private Gui myGui;
	private double transProb;
	public static void main(String[] args) {
		String path = JOptionPane.showInputDialog("Пожалуйста назначьте файл с решетчетой диаграммой :","standartdia.txt");
		//String bscTransProbab = JOptionPane.showInputDialog("Вероятность ошибки в канале BSC", "0.1");
		String bscTransProbab = "0";
		
		new LaunchB2(path,bscTransProbab);

	}
	
	private LaunchB2(String path, String bscTransProbab){
		this.myFacadeClass = new Joint(path,Double.parseDouble(bscTransProbab));
		
		this.transProb = Double.parseDouble(bscTransProbab);
		
		
		this.myGui = new Gui(this.myFacadeClass.linkToTrellisPanel(),this.myFacadeClass.linkToTrellisShow());
	}
	
	
	
	class Gui extends JFrame{
		private JPanel trellisFr;
		private Container wordPn;
		private JPanel trellisShow;
		private Container globCont;
		
		
		private Container centerCont;
		private JPanel rightCont;
		
		public Gui(JPanel trellisFr, JPanel trellisShow){
			super("Сверточные коды");
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.trellisFr = trellisFr;
			this.trellisShow = trellisShow;
			
			this.globCont = this.getContentPane();
			this.globCont.setLayout(new FlowLayout());
			
			
			this.centerCont = new Container();
			this.centerCont.setLayout(new FlowLayout());
			
			this.centerCont.add(this.trellisFr);
			
			this.wordPn = new InputForm();
			this.centerCont.add(wordPn);
			
			
			this.rightCont = new JPanel();
			this.rightCont.add(trellisShow);
			
			this.centerCont.add(this.rightCont);
			
			this.globCont.add(this.centerCont);
			
			
			this.pack();
			this.setVisible(true);
			
		}
		
		private class InputForm extends Container{
			private JLabel inputLabel;
			private JTextField inputField;
			
			private JButton encodeAndSend;
			
			private JLabel encodedLabel;
			private JTextField encodedField;
			
			private JLabel TransProp;

			private JLabel errorLabel;
			private JTextField errorField;

			private JLabel arrivedLabel;
			private JTextField arrivedField;
			
			private JLabel decodedLabel;
			private JTextField decodedField;
			private JLabel decProp;
			
			private JButton buttonAbout;
			
			
			
			private InputForm(){
				super();
				this.setLayout(new GridLayout(0,1));
				
				this.inputLabel = new JLabel("Вставьте информационную последовательность [ 0,1 массив]: ");
				this.add(this.inputLabel);
				
				this.inputField = new JTextField(20);
				this.add(this.inputField);

				this.errorLabel = new JLabel("Ошибки в разрядах:");
				this.add(this.errorLabel);

				this.errorField = new JTextField(40);
				this.add(this.errorField);
				
				this.encodeAndSend = new JButton("Закодировать и Отправить!");
				this.encodeAndSend.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent arg0) {
						myFacadeClass.resetEnc();
						trellisShow = myFacadeClass.linkToTrellisShow();
						
						rightCont.remove(0);
						rightCont.add(trellisShow);
						
											
						trellisShow.repaint();
						
						
						
						
						InterexchangeSeq seq =  myFacadeClass.CodeTransfDecode4Win(inputField.getText(), errorField.getText());
						encodedField.setText(seq.getCodeS());
						arrivedField.setText(seq.getTrasS());
						decodedField.setText(seq.getDecS());
						TransProp.setText("Ошибки передачи: " + Integer.toString(seq.getCodingErr()) + " больше " + Integer.toString(myFacadeClass.getTotCodedBit()) + " символов");
						
						decProp.setText("Ошибки декодирования: " + Integer.toString(seq.getBitErr()) + " больше " + Integer.toString(myFacadeClass.getTotalInfobit())+ " битов");
						
						
					}
					
				});
				this.add(this.encodeAndSend);
				
				this.encodedLabel = new JLabel("Закодированная строка:");
				this.add(this.encodedLabel);
				this.encodedField = new JTextField(40);
				this.add(this.encodedField);
				
				/*JLabel BSCprop = new JLabel("Передача через BSC с вероятностью ошибки p=" + Double.toString(transProb),JLabel.RIGHT);
				BSCprop.setFont(new Font("sansserif", Font.ITALIC, 12));
				this.add(BSCprop);*/

				
				this.arrivedLabel = new JLabel("Принятая строка:");
				this.add(this.arrivedLabel);
							
				this.arrivedField = new JTextField(40);
				this.add(this.arrivedField);
				
				TransProp = new JLabel("Ошибки передачи: N/A",JLabel.RIGHT);
				TransProp.setFont(new Font("sansserif", Font.ITALIC, 12));
				this.add(TransProp);
				
				this.decodedLabel = new JLabel("Декодированая строка:");
				this.add(this.decodedLabel);
				this.decodedField = new JTextField(20);
				this.add(this.decodedField);
				
				decProp = new JLabel("Ошибки декодирования: N/A",JLabel.RIGHT);
				decProp.setFont(new Font("sansserif", Font.ITALIC, 12));
				this.add(decProp);
				
				this.buttonAbout = new JButton("О программе...");
				this.buttonAbout.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent arg0) {
						JOptionPane.showMessageDialog(null,
								"Программа по дисциплине Теория Кодирования\n" +
								"Работу выполнял:\n" +
										"Широбоков Н.В.\n" +
								"\n" +
								"\n" +
								
								"СПБГУТ 2015\n " +
								"" +
								"\n",
								"О программе..",
								JOptionPane.INFORMATION_MESSAGE
								);
					}
					
				});
					
				this.add(buttonAbout);
				
				
				
			}
		}
	}

}
