package client.core.handler.io;

import java.io.OutputStream;
import java.io.PrintWriter;

public class ClientOutput {

    private PrintWriter out;

    public ClientOutput(OutputStream outputStream) {
        this.out = new PrintWriter(outputStream, true);
    }
}
