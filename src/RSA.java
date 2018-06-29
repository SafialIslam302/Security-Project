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

public class RSA extends JFrame 
{
	private static final String String = null;

	String path1 = null;
	BigInteger p, q, phiN, d, ef;
	int num1; 
	
	BigInteger N;

	JButton blogin1 = new JButton("Encrypt");
	JButton blogin2 = new JButton("Decrypt");
	JButton blogin3 = new JButton("Back");
	JPanel panel = new JPanel();
	
	public static void main(String[] args) throws IOException 
	{
		RSA frameTabel = new RSA(String );
	}
	
	@SuppressWarnings("resource")
	public RSA(String password) throws IOException
	{
		super("RSA Encryption Decryption");
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
		            while(true)
		            {
		                p = generateProbablePrime(5);
		            	q = generateProbablePrime(4);
		            	ef = generateProbablePrime(4);
		               	N = p.multiply(q);
		               	num1 = N.intValue();
		               	if(num1 > 127 && num1 < 256)
		               		break;
		            } 
	                phiN = phi(p,q);
	                d = inverse(ef,phiN);
	                //System.out.println("In Encryption");
	                //System.out.println(path);
	                //System.out.println("P :"+p+" Q : "+q+" EF : "+ef+" N : "+N+" PHIN : "+phiN+" D : "+d);
		            for(int i=0;i<encoded.length;i++)
		            {
		            	encrypt = encoded[i];
		            	encrypt = encrypt & 0xFF;
		            	num[i] = encrypt(ef,p,q,encrypt);
		            }

		            try { 
		                FileOutputStream fileOuputStream = new FileOutputStream(path+".encrypt"); 
		                for (int i = 0; i < encoded.length; i++)
		                	fileOuputStream.write(num[i]);   // num
		                fileOuputStream.close();
		             } catch (IOException e1) {
		             	
		             }          
		        } catch (IOException e1) {
		        	
		        }
		        
		        File file = new File(path1);
		        file.delete();
			}

			private BigInteger generateProbablePrime(int i) {
				return BigInteger.probablePrime(i, new Random());
			}

		    private BigInteger phi(BigInteger p, BigInteger q){
		        BigInteger phiN = p.subtract(BigInteger.ONE);
		        phiN = phiN.multiply(q.subtract(BigInteger.ONE));
		        return phiN;
		    }
		    
		    private BigInteger inverse (BigInteger a, BigInteger N){
		    	int a1,n1;
		    	a1 = a.intValue();
		    	n1 = N.intValue();
		    	int i = n1, v = 0, d = 1;
	            while (a1 > 0)
	            {
	                int t = i / a1, x = a1;
	                a1 = i % x;
	                i = x;
	                x = d;
	                d = v - t * x;
	                v = x;
	            }
	            v %= n1;
	            if (v < 0)
	                v = (v + n1) % n1;
	            BigInteger m3 = BigInteger.valueOf(v);
	            return m3;
		    }
		    
		    public int crt(BigInteger d, BigInteger p, BigInteger q, int m){
		        BigInteger dp, dq, qInverse, m1, m2, h;
		        BigInteger m3 = BigInteger.valueOf(m);
		        int m4;
		        dp = d.mod(p.subtract(BigInteger.ONE));
		        dq = d.mod(q.subtract(BigInteger.ONE));
		        qInverse = inverse(q,p);

		        m1 = m3.modPow(dp,p);
		        m2 = m3.modPow(dq,q);
		        h = qInverse.multiply(m1.subtract(m2)).mod(p);
		        m3 = m2.add(h.multiply(q));
		        m4 = m3.intValue();
		        return m4;
		    }
		    
		    public int encrypt(BigInteger e, BigInteger p, BigInteger q,int m){
		        return crt(e,p,q,m);
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
	            //System.out.println("In Decryption");
	            //System.out.println(path);
                //System.out.println("P :"+p+" Q : "+q+" EF : "+ef+" N : "+N+" PHIN : "+phiN+" D : "+d);
	            for(int i=0;i<encoded.length;i++)
	            {
	            	decrypt = encoded[i];
	            	if(decrypt<-1 && decrypt>-128)
	            	{
	            		decrypt = decrypt & 0xFF;
	            	}

	            	num[i] = decrypt(d,p,q,decrypt);
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
	      
	      public BigInteger [] extendedEuclid (BigInteger a, BigInteger N){
		        BigInteger [] ans = new BigInteger[3];
		        BigInteger ax, yN;

		        if (N.equals(BigInteger.ZERO)) {
		            ans[0] = a;
		            ans[1] = BigInteger.ONE;
		            ans[2] = BigInteger.ZERO;
		            return ans;
		        }
		        ans = extendedEuclid (N, a.mod (N));
		        ax = ans[1];
		        yN = ans[2];
		        ans[1] = yN;
		        BigInteger temp = a.divide(N);
		        temp = yN.multiply(temp);
		        ans[2] = ax.subtract(temp);
		        return ans;
		    }
	      
	      private BigInteger inverse (BigInteger a, BigInteger N){
		        BigInteger [] ans = extendedEuclid(a,N);

		        if (ans[1].compareTo(BigInteger.ZERO) == 1)
		            return ans[1];
		        else return ans[1].add(N);
		  }
	      
	      public int crt(BigInteger d, BigInteger p, BigInteger q, int m){
	    	  BigInteger dp, dq, qInverse, m1, m2, h;
		        BigInteger m3 = BigInteger.valueOf(m);
		        int m4;
		        dp = d.mod(p.subtract(BigInteger.ONE));
		        dq = d.mod(q.subtract(BigInteger.ONE));
		        qInverse = inverse(q,p);

		        m1 = m3.modPow(dp,p);
		        m2 = m3.modPow(dq,q);
		        h = qInverse.multiply(m1.subtract(m2)).mod(p);
		        m3 = m2.add(h.multiply(q));
		        m4 = m3.intValue();
		        return m4;
	      }

	      public int decrypt(BigInteger d, BigInteger p, BigInteger q,int m){
	          return crt(d,p,q,m);
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