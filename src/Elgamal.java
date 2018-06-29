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

public class Elgamal extends JFrame 
{
	private static final String String = null;

	String path1 = null;
	private static final SecureRandom rand = new SecureRandom();
	BigInteger[] num1 = new BigInteger[10000000];
	BigInteger p, a, k, alpha, beta, y1, temp;
	BigInteger two = new BigInteger("2");
	BigInteger one = new BigInteger("1");

	JButton blogin1 = new JButton("Encrypt");
	JButton blogin2 = new JButton("Decrypt");
	JButton blogin3 = new JButton("Back");
	
	JPanel panel = new JPanel();
	
	public static void main(String[] args) throws IOException 
	{
		Elgamal frameTabel = new Elgamal(String );
	}
	
	@SuppressWarnings("resource")
	public Elgamal(String password) throws IOException
	{
		super("EL-GAMAL Encryption Decryption");
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
		            BigInteger tempencrypt,temp1,temp2;
		            BigInteger[] y2 = new BigInteger[encoded.length];
		            int[] tempy2 = new int[encoded.length];
		            
		            while (true)
		            {
		            	p = BigInteger.probablePrime(8, rand);
		                temp = p;
		                if (!p.isProbablePrime(1)){
		                    continue;
		                } else {
		                    while (true){
		                        boolean ch1= false,ch2= false,ch3 = false;
		                        alpha = BigInteger.probablePrime(5, rand);
		                        if (!alpha.mod(p).equals(BigInteger.ONE)){
		                            ch1 = true;
		                        }
		                        if (!alpha.modPow(two,p).equals(BigInteger.ONE)){
		                            ch2 = true;
		                        }
		                        if (!alpha.modPow(temp,p).equals(BigInteger.ONE)){
		                            ch3 = true;
		                        }
		                        if (ch1 && ch2 && ch3){
		                            break;
		                        }
		                    }
		                    break;
		                }
		            }
		            
		            // Private Number a
		            a = new BigInteger(5, rand);
		            while ((a.compareTo(p.subtract(two)) == 1)) {
		                a = new BigInteger(5, rand);
		            }

		            // Random Number k
		            k = new BigInteger(5, rand);
		            while (k.compareTo(p.subtract(one)) >= 2 && !(k.gcd(p.subtract(one)).equals(BigInteger.ONE))) {
		                k = new BigInteger(5, rand);
		            }

		            // Calculate Beta
		            beta = alpha.modPow(a,p);
		            
		            y1 = alpha.modPow(k,p);
		            
		            for(int i=0;i<encoded.length;i++)
		            {
		            	encrypt = encoded[i];
		            	encrypt = encrypt & 0xFF;
		            	tempencrypt = BigInteger.valueOf(encrypt);
		            	//System.out.print(" "+tempencrypt);
		            	temp1 = beta.modPow(k, p);
		            	y2[i] = tempencrypt.multiply(temp1);
		            	//System.out.print(" "+y2[i]);
		            	temp2 = y2[i];
		            	y2[i] = y2[i].mod(p);
		            	num1[i] = temp2.divide(p);
		            	//y2[i] = temp1.subtract(p.multiply(temp1.divide(p)));
		            }
		            
		            
		            for(int i=0;i<encoded.length;i++)
		            {	
		            	tempy2[i] = y2[i].intValue();
		            }

		            try { 
		            	FileOutputStream fileOuputStream = new FileOutputStream(path+".encrypt"); 
		                for (int i = 0; i < encoded.length; i++)
		                {
		                	fileOuputStream.write(tempy2[i]);
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
		
  
		blogin2.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent e) {
	        JFileChooser fileChooser2 = new JFileChooser();
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
	        	BigInteger dp,d,tempendecrypt,temp10,temp11,temp12;
	        	BigInteger[] num = new BigInteger[encoded.length];
	        	int[] tempnum = new int[encoded.length];
	            
	            for(int i=0;i<encoded.length;i++)
	            {
	            	decrypt = encoded[i];

	            	if(decrypt<-1 && decrypt>-128)
	            	{
	            		decrypt = decrypt & 0xFF;
	            	} 
	            	temp12 = BigInteger.valueOf(decrypt);
	            	temp10 = p.multiply(num1[i]);
	            	temp11 = temp10.add(temp12);
	            	decrypt = temp11.intValue();	
	            	tempendecrypt = BigInteger.valueOf(decrypt);
	            	dp = y1.modPow(a, p);
	            	d = dp.modInverse(p);
	            	num[i] = d.multiply(tempendecrypt).mod(p);  
   	
	            }
	            for(int i=0;i<encoded.length;i++)
	            {
	            	tempnum[i] = num[i].intValue();
	            }
	            
	            try { 
	                FileOutputStream fileOuputStream = new FileOutputStream(path1); 
	                for (int i = 0; i < encoded.length; i++)
	                	fileOuputStream.write(tempnum[i]);
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