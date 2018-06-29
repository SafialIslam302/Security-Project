import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Random;
import java.util.Scanner;

public class algorithm extends JFrame 
{
	private static final String String = null;

	String path1 = null;
	BigInteger p, q, phiN, d, ef;
	int num1; 

	BigInteger N;

	JButton blogin1 = new JButton("RSA");
	JButton blogin2 = new JButton("EL-GAMAL");
	JButton blogin3 = new JButton("CAEASAR CIPHER");
	JPanel panel = new JPanel();
	
	public static void main(String[] args) throws IOException 
	{
		algorithm frameTabel = new algorithm(String );
	}
	
	@SuppressWarnings("resource")
	public algorithm(String password) throws IOException
	{
		super("File Encryption Decryption");
		setSize(450,350);
		setLocation(400,280);
		panel.setLayout (null);

		blogin1.setBounds(170, 51,  135, 33);
		blogin2.setBounds(170, 151, 135, 33);
		blogin3.setBounds(170, 251, 135, 33);
		
		panel.add(blogin1);
		panel.add(blogin2);
		panel.add(blogin3);
		
		getContentPane().add(panel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		blogin1.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent ae) 
			{   
				RSA regFace;
				try {
					regFace = new RSA(password);
					regFace.setVisible(true);
					dispose();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} 		
		});
		
		blogin2.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent ae) 
			{   
				Elgamal regFace;
				try {
					regFace = new Elgamal(password);
					regFace.setVisible(true);
					dispose();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} 		
		});
		
		blogin3.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent ae) 
			{   
				Caesar regFace;
				try {
					regFace = new Caesar(password);
					regFace.setVisible(true);
					dispose();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} 		
		});
	}
}