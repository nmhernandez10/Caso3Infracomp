package Generator;

import java.io.PrintStream;

import Generator.Task;
import UnidadDeDistribución.ClienteSeguro;

public class ClientServerTask extends Task {  
	
    private boolean fallido = false;
    
    @Override
    public void execute() {
    	try 
    	{    		
			@SuppressWarnings("unused")
			ClienteSeguro cliente = new ClienteSeguro();			
		} 
    	catch (Exception e) 
    	{
			fallido = true;
			System.out.println("For if the flies");
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
    
    public boolean isFallido()
    {
    	return fallido;
    }
}

