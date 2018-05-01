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
	public static final int NUMBER_OF_TASKS = 400; // 400-200-80
	public static final int GAP_BETWEEN_TASKS = 20; // 20-40-100 (amarrado a tasks)
	public static final int NUM_THREADS = 8; // 1-2-8
	public static final int NUM_ITERACIONES = 2;
	
	public Generator(int numIteracion)
	{
		Task work = createTask(numIteracion);
		generator = new LoadGenerator("Client - Server Load Test", NUMBER_OF_TASKS, work, GAP_BETWEEN_TASKS);
		generator.generate();
	}
	
	public Task createTask(int numIteracion){
		ClientServerTask taskCliente = new ClientServerTask();
		taskCliente.setNumIteracion(numIteracion);
		return taskCliente;
	}
	
	public static void main (String[] args) throws IOException{
		
		for (int i = 0; i < NUM_ITERACIONES; i++) 
		{
			System.out.println("============================================== ITERACIÓN "+(i+9)+"===========================================================");
			@SuppressWarnings("unused")
			Generator generator = new Generator(i+9);			
		}		
	}
}

