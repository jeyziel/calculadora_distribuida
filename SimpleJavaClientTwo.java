package sd;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

//Prefira implementar a interface Runnable do que extender a classe Thread, pois neste caso utilizaremos apena o método run.
public class SimpleJavaClientTwo {

    public static void main(String[] args) 	{
        try {
            Socket s = new Socket("127.0.0.1", 12345);
            InputStream i = s.getInputStream();
            OutputStream o = s.getOutputStream();
            String str;
            do {
                byte[] line = new byte[100];
                System.out.println("Digite uma conta (Ex: 1 + 1):");
                System.in.read(line);// Ler a informacao do usuario
                o.write(line);// Envia a informacao ao servidor
                i.read(line);// Recebe a informacao do servidor  
                str = new String(line);// Objeto auxilar responsavel por mostrar o que esta em --line--                
                System.out.println("RESULTADO: "+str+"\n");
            } while ( !str.trim().equals("bye") );
            s.close();
        }
        catch (Exception err) {
            System.err.println(err);
        }
    }
}