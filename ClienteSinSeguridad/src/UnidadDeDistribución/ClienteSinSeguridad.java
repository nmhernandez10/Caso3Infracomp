package UnidadDeDistribución;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.Socket;
import java.security.KeyPair;
import java.security.PublicKey;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.bouncycastle.util.encoders.Base64;

import Generator.Generator;


public class ClienteSinSeguridad 
{

	private static final int PUERTO = 8081;
	private static final String DIR = "localhost";
	private static final String algoritmoSimetrico = "AES";
	private static final String algoritmoAsimetrico = "RSA";
	private static final String algoritmoDigest = "HMACMD5";
	
	private Socket s;
	private PrintWriter out;
	private BufferedReader in;
	
	private PublicKey llaveServidor;
	private KeyPair llaveCliente;
	private SecretKey llaveSesion;
	
	//-------------------------
	private KeyPair keyAsin;
	private CifradorAsimetricoRSA cifradorAsim;
	private ManejadorCertificado mc;

	//Estadísticas

	private long tiempoActualización;

	
	private int numIteracion;
	
	public ClienteSinSeguridad(int pNumIteracion)
	{
		try
		{
			numIteracion = pNumIteracion;
			cifradorAsim = new CifradorAsimetricoRSA();
			keyAsin = cifradorAsim.darLlave();
			
			conectar();
			conversar();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void conectar() throws Exception
	{
			s = new Socket(DIR, PUERTO);
			out = new PrintWriter(s.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(s.getInputStream()));
	}

	public void conversar() throws Exception
	{
		//Etapa 1 Inicio de sesion
		
		inicioSesion();
		
		//Etapa 2 Intercambio de CD
		
		intercambioCD();		
		
		//Etapa 3 Autenticación
		
		continuarSesion();		
		
		//Etapa 4 Reporte y manejo de actualización
		
		reporteAct();	
		
		//Reporte de tiempos en archivos (Caso 3)
		
		registrarTiempos();
		
		System.out.println("¡Se termino!");
	}


	private void inicioSesion() throws Exception 
	{
		out.println("HOLA");
		String rta = in.readLine();
		if(!rta.equals("INICIO"))
		{
			throw new Exception("Respuesta inesperada. Etapa 1.");
		}
		out.println("ALGORITMOS:"+algoritmoSimetrico+":"+algoritmoAsimetrico+":"+algoritmoDigest);
		rta = in.readLine();		
		if ((!rta.contains(":")) || (!rta.split(":")[0].equals("ESTADO")))
		{
	        throw new Exception("Formato de mensaje inesperado.");
	    }
		rta = rta.split(":")[1];
		if (!rta.equals("OK"))
		{
	        throw new Exception("Algoritmos no soportados.");
	    }
	}

	private void intercambioCD() throws IOException
	{
		llaveServidor = ManejadorCertificado.creationYProcesamiento(keyAsin,s.getOutputStream(), s.getInputStream(), out, in);
	}

	private void continuarSesion() throws Exception
	{
		String rta = in.readLine();		
		if (!rta.equals("INICIO"))
		{
	        throw new Exception("Mensaje inesperado.");
	    }	
	}
	
	private void reporteAct() throws Exception
	{
		String pos = "41 24.2028, 2 10.4418";
		
		//Se controla que se ingrese un formato de posición correcto (AA BB, CC DD)
		if(pos.split(",").length == 2)
		{			
			String pos1 = pos.split(",")[0];
			String pos2 = pos.split(",")[1];
						
			if(pos1.trim().split(" ").length != 2 || pos2.trim().split(" ").length != 2)
			{
				throw new Exception("Formato de posición incorrecto.");		
			}
		}
		else
		{
			throw new Exception("Formato de posición incorrecto.");	
		}
		
		//ACT1
		
		tiempoActualización = System.nanoTime();
		
		String posEnviar = "ACT1";
		System.out.println(posEnviar);
		out.println(posEnviar);
		
		//ACT2		
		posEnviar = "ACT2";
		System.out.println(posEnviar);
		out.println(posEnviar);
		
		//Recibir confirmación
		String rta = in.readLine();		
		if ((!rta.contains(":")) || (!rta.split(":")[0].equals("ESTADO")))
		{
	        throw new Exception("Formato de mensaje inesperado.");
	    }
		rta = rta.split(":")[1];
		if (!rta.equals("OK"))
		{
	        throw new Exception("Error en actualización y reporte.");
	    }	
		
		tiempoActualización = System.nanoTime() - tiempoActualización;		
	}
	
	public void registrarTiempos()
	{
		try
		{
			File tiempos = new File("./data/" + "Tiempos_"+ Generator.NUMBER_OF_TASKS + "_" + Generator.GAP_BETWEEN_TASKS + "_" + Generator.NUM_THREADS+"_"+numIteracion);
			PrintWriter escritor = new PrintWriter(new FileWriter(tiempos, true));
			escritor.println("TiempoActualización:"+tiempoActualización);
			escritor.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
		
	//------ Metodos Auxiliares
	
	/**
	 * Recibe un array de bytes y lo convierte en hexadecimal (es para enviar datos)
	 * @param array de bytes
	 * @return hexa
	 */
	public static String toHexString(byte[] array) 
	{
	    return DatatypeConverter.printHexBinary(array);
	}
	
	/**
	 * Recibe un string en hexa y lo convierte a arreglo de bytes (es para recibir datos)
	 * @param s hexa
	 * @return bytes
	 */
	public static byte[] toByteArray(String s) 
	{
	    return DatatypeConverter.parseHexBinary(s);
	}	
}
