
package controller;

import Model.Client;
import Model.Server;
import java.net.Socket;


public class CriarServidor {
    
    
    
    
    public static void main(String args []){
        try{
        Server novoServidor = new Server();    
        novoServidor.criarServerSocket(4444);
        Socket socket = novoServidor.criarConexao();
        
        Client cliente = new Client();
        System.out.println("Esperando cliente ...");
        cliente.criaCliente();
        System.out.println("Cliente conectado !");
        
        novoServidor.gerenciarConexao(socket);
       
        
        }
        catch(Exception e){
        
            System.out.println("ERRO " + e);    
        
        }
        
            
        
}
    
}
