import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
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
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Random;
import java.util.Scanner;

public class Caesar extends JFrame 
{
	private static final String String = null;

	String path1 = null;
	Random rand = new Random();
	int  n = rand.nextInt(50) + 1;

	JButton blogin1 = new JButton("Encrypt");
	JButton blogin2 = new JButton("Decrypt");
	JButton blogin3 = new JButton("Back");
	
	JPanel panel = new JPanel();
	
	public static void main(String[] args) throws IOException 
	{
		Caesar frameTabel = new Caesar(String );
	}
	
	@SuppressWarnings("resource")
	public Caesar(String password) throws IOException
	{
		super("Caesar Cipher Encryption Decryption");
		setSize(450,300);
		setLocation(400,280);
		panel.setLayout (null);

		blogin1.setBounds(170, 51,  127, 33);
		blogin2.setBounds(170, 151, 127, 33);
		blogin3.setBounds(192, 201, 87,  23);
		
		panel.add(blogin1);
		panel.add(blogin2);
		panel.add(blogin3);
		
		getContentPane().add(panel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
                
		blogin1.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{   
				JFileChooser fileChooser1 = new JFileChooser();
				 
		        // For File
		        fileChooser1.setFileSelectionMode(JFileChooser.FILES_ONLY);

		        fileChooser1.setAcceptAllFileFilterUsed(false);
		        
		        int rVal = fileChooser1.showOpenDialog(null);
		        if (rVal == JFileChooser.APPROVE_OPTION) {
		          path1 = fileChooser1.getSelectedFile().toString();

		        }
		        String path = path1;
		        try {
		            byte[] encoded = Files.readAllBytes(Paths.get(path));		            
		            int encrypt = 0;
		            int[] num = new int[encoded.length];
		            for(int i=0;i<encoded.length;i++)
		            {
		            	encrypt = encoded[i];
		            	encrypt = encrypt & 0xFF;
		            	num[i] = encrypt + n;
		            }
		            
		            try { 
		            	FileOutputStream fileOuputStream = new FileOutputStream(path+".encrypt"); 
		                for (int i = 0; i < encoded.length; i++)
		                {
		                	fileOuputStream.write(num[i]);
		                }
		                fileOuputStream.close();
		             } catch (IOException e1) {
		             	
		             }          
		        } catch (IOException e1) {
		        	
		        }
		        
		        File file = new File(path1);
		        file.delete();
			}
		    
		});
		
		//For Decrypt Button  
		blogin2.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent e) {
	        JFileChooser fileChooser2 = new JFileChooser();
	 
	        // For File
	        fileChooser2.setFileSelectionMode(JFileChooser.FILES_ONLY);

	        fileChooser2.setAcceptAllFileFilterUsed(false);
	        String path2 = null;
	        int rVal = fileChooser2.showOpenDialog(null);
	        if (rVal == JFileChooser.APPROVE_OPTION) {
	          path2 = fileChooser2.getSelectedFile().toString();

	        }
	        
	        String path = path2;
	        try {
	            byte[] encoded = Files.readAllBytes(Paths.get(path));
	        	int decrypt = 0;
	        	int[] num = new int[encoded.length];

	            for(int i=0;i<encoded.length;i++)
	            {
	            	decrypt = encoded[i];
	            	if(decrypt<-1 && decrypt>-128)
	            	{
	            		decrypt = decrypt & 0xFF;
	            	} 
		            num[i] = decrypt - n;
	            }
	            
	            try { 
	                FileOutputStream fileOuputStream = new FileOutputStream(path1); 
	                for (int i = 0; i < encoded.length; i++)
	                	fileOuputStream.write(num[i]);
	                fileOuputStream.close();
	             } catch (IOException e1) {
	             	
	             }
	        } catch (IOException e1) {
	        	
	        }
	        
	        File file = new File(path2);
	        file.delete();
	      }
	      
	      
	    });
		
		blogin3.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent ae) 
			{   
				algorithm regFace;
				try {
					regFace = new algorithm(password);
					regFace.setVisible(true);
					dispose();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} 		
		});
	}
		
}