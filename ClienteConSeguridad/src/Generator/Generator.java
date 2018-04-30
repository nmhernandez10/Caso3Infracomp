/*
 * Decompiled with CFR 0_123.
 */
package Generator;

import Generator.LoadGenerator;
import Generator.Task;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import Generator.ClientServerTask;

public class Generator {
	private LoadGenerator generator;
	public static final int NUMBER_OF_TASKS = 200; 
	public static final int GAP_BETWEEN_TASKS = 100;
	public static final int NUM_THREADS = 1;
	public static final int NUM_ITERACIONES = 1;
	private int transaccionesFallidas;
	
	public Generator()
	{
		Task work = createTask();
		generator = new LoadGenerator("Client - Server Load Test", NUMBER_OF_TASKS, work, GAP_BETWEEN_TASKS);
		generator.generate();
	}
	
	public Task createTask(){
		ClientServerTask taskCliente = new ClientServerTask();
		if(taskCliente.isFallido())
		{
			transaccionesFallidas ++;
		}
		return taskCliente;
	}
	
	public int getTransaccionesFallidas()
	{
		return transaccionesFallidas;
	}
	
	public static void main (String[] args) throws IOException{
		
		for (int i = 0; i < NUM_ITERACIONES; i++) 
		{
			System.out.println("============================================== ITERACIÓN "+(i+1)+"===========================================================");
			@SuppressWarnings("unused")
			Generator generator = new Generator();			
		}		
	}
}

