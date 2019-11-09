package sd;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JOptionPane;

public class CalcServer {
	public static String str;
    
    public static void soma(float x, float y){
    	str = Float.toString(x+y) + "    ";
    }
    public static void subtracao(float x, float y){
    	str = Float.toString(x-y) + "    ";       
    }
    public static void multiplicacao(float x, float y){
    	str = Float.toString(x*y) + "    ";       
    }
    public static void divisao(float x, float y){
        if(y == 0)
        	str = "Nao existe Divisao por zero";
            else 
            	str = Float.toString(x/y) + "    ";       
    }

    public static void main(String[] args) 	{
        try {
            ServerSocket s = new ServerSocket(45678);
       
            System.out.println("Servidor ligado! "); //Essa linha eh apenas para teste (pode ser Excluida)
            while (true) {
                Socket c = s.accept();
                InputStream i = c.getInputStream();// Objeto responsavel por coletar a entrada do cliente
                OutputStream o = c.getOutputStream(); // Objeto responsavel pelo envio para o cliente
                o.flush();
                do {            	
                    byte[] line = new byte[100];//Objeto que armazena a informacao
                    i.read(line);// Ler a solicitacao do cliente
                    str = new String(line);  // Convertendo de Byte para String
                    String[] conta = str.split(" "); //Separa a string pelo "Espaco" e armazena cada palavra em uma posicao do vetor conta
                    if("+".equals(conta[1])){ //conta[1] armazena o operador
                    	soma(Float.parseFloat(conta[0]), Float.parseFloat(conta[2])); //chama o metodo soma convertendo os parametros String pra Float
                    } else
                        if("-".equals(conta[1])){ //conta[1] armazena o operador
                        	subtracao(Float.parseFloat(conta[0]), Float.parseFloat(conta[2])); //chama o metodo subtracao convertendo os parametros String pra Float
                    } else
                        if("*".equals(conta[1])){ //conta[1] armazena o operador
                        	multiplicacao(Float.parseFloat(conta[0]), Float.parseFloat(conta[2])); //chama o metodo multiplicacao convertendo os parametros String pra Float
                    } else
                    	if("/".equals(conta[1])){ //conta[1] armazena o operador
                    		divisao(Float.parseFloat(conta[0]), Float.parseFloat(conta[2])); //chama o metodo divisao convertendo os parametros String pra Float
                    	}
                    line = str.getBytes(); //Converte str de String para bytes e atribui a line
                    o.write(line);  // Envia a informacao p/ o cliente  
                } while ( !str.trim().equals("bye") );
                c.close();
            }
        }
        catch (Exception err){
            System.err.println(err);
        }
    }

}