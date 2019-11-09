package sd;

import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SimpleJavaServer implements Runnable {
    public Socket client;
    private static List<Socket> listaDeClientes = new ArrayList<Socket>();

    public SimpleJavaServer(Socket client){
        this.client = client;        
    }
    public String chamarSever (String lineString) throws IOException{
        byte[] line = new byte[100];
        line = lineString.getBytes();
        Socket s = new Socket("127.0.0.1", 45678);
        InputStream j = s.getInputStream();
        OutputStream k = s.getOutputStream();
        k.write(line);
        j.read(line); 	
    	return new String (line);
    }


    public static void main(String[] args)  throws IOException {

        //Cria um socket na porta 12345
        ServerSocket servidor = new ServerSocket (12345);
        System.out.println("Porta 12345 aberta!");
        
        // Aguarda alguém se conectar. A execução do servidor
        // fica bloqueada na chamada do método accept da classe
        // ServerSocket. Quando alguém se conectar ao servidor, o
        // método desbloqueia e retorna com um objeto da classe
        // Socket, que é uma porta da comunicação.
        System.out.println("Aguardando conexão do cliente...");
        

        while (true) {
            Socket client = servidor.accept();

            listaDeClientes.add(client);
            // Cria uma thread do servidor para tratar a conexão
            SimpleJavaServer tratamento = new SimpleJavaServer(client);
            Thread t = new Thread(tratamento);
            // Inicia a thread para o cliente conectado
            t.start();
        }
    }
    
    /* A classe Thread, que foi instancia no servidor, implementa Runnable.
       Então você terá que implementar sua lógica de troca de mensagens dentro deste método 'run'.
    */
    public void run(){
        System.out.println("Nova conexao com o cliente " + this.client.getInetAddress().getHostAddress());
        
        String str;

        try {
            InputStream i = this.client.getInputStream();// Objeto responsavel por coletar a entrada do cliente
            OutputStream o = this.client.getOutputStream(); // Objeto responsavel pelo envio para o cliente
          
            do {
                byte[] line = new byte[100];
                i.read(line);
                str = new String(line);
                listaDeClientes.forEach(e -> {
                    try {
                        o.write(chamarSever (new String(line)).getBytes()); // Envia a informacao para o cliente
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
            } while (!str.trim().equals("bye"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}