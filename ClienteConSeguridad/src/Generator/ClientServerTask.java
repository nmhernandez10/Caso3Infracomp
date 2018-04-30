package Generator;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Serializable;

import Generator.Task;
import UnidadDeDistribución.ClienteSeguro;

public class ClientServerTask extends Task {  
    
	private int numIteracion;
	
    public int getNumIteracion() {
		return numIteracion;
	}



	public void setNumIteracion(int numIteracion) {
		this.numIteracion = numIteracion;
	}
	
	@Override
    public void execute() {
    	try 
    	{    		
			@SuppressWarnings("unused")
			ClienteSeguro cliente = new ClienteSeguro(numIteracion);			
		} 
    	catch (Exception e) 
    	{
    		System.out.println(e.getMessage());
		}
    }
    
    
    
    @Override
    public void fail() {
        System.out.println("FAIL_TEST");
    }

    @Override
    public void success() {
        System.out.println("OK_TEST");
    }
}

