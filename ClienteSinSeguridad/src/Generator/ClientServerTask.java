package Generator;

import java.io.PrintStream;

import Generator.Task;
import UnidadDeDistribución.ClienteSinSeguridad;

public class ClientServerTask extends Task {
    @Override
    public void execute() {
    	try {
			@SuppressWarnings("unused")
			ClienteSinSeguridad cliente = new ClienteSinSeguridad();
			
		} catch (Exception e) {
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
}

