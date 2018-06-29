import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class Login extends JFrame 
{

	JButton blogin = new JButton("Login");
	JPanel panel = new JPanel();
	JTextField txuser = new JTextField(15);
	JPasswordField pass = new JPasswordField(15);
	
	public static void main(String[] args) throws IOException 
	{
		Login frameTabel = new Login();
	}
	
	@SuppressWarnings("resource")
	public Login() throws IOException
	{
		super("Login Autentification");
		setSize(380,280);
		setLocation(400,280);
		panel.setLayout (null);
		
		JLabel l2, l3;
		l2 = new JLabel("Username");
		l3 = new JLabel("Password");
		l2.setBounds(60, 80, 250, 20);
		l3.setBounds(60, 115, 250, 20);
		
		txuser.setBounds(130,80,150,20);
		pass.setBounds(130,115,150,20);
		blogin.setBounds(150,150,80,20);
		
		panel.add(l2);
		panel.add(l3);
		panel.add(blogin);
		panel.add(txuser);
		panel.add(pass);
		
		getContentPane().add(panel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
        
		Scanner s1,s2;
        s1=new Scanner(new FileInputStream("d:\\log.txt"));
        s2=new Scanner(System.in);
        
		blogin.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent ae) 
			{   
		        boolean flag=false;
		        String name,pword,n,p;
				String puname = txuser.getText();
				n = puname;
				String ppaswd = pass.getText();
				p=ppaswd;
				
				while(s1.hasNext()) {
		            name=s1.next();
		            pword=s1.next();
		            if(n.equals(name) && p.equals(pword)) {
		            	algorithm regFace;
						try {
							regFace = new algorithm(pword);
							regFace.setVisible(true);
							dispose();
							flag=true;
			                break;
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		            } 
		        }
				if(!flag) 
				{
			        //System.out.println("Incorrect password.");
					String infoMessage = "Incorrect password";
					String titleBar = "Error";
					JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
	}
		
}