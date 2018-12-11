/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.ArrayList;

public class Server {

    //Servidor da aplicacao
    private ServerSocket server;
    
    //Preciso de uma estrutura que salvará todas as mensagens q chegam ao servidor, encapsulara,
    //e mandará ate as telas dos clientes.
    ArrayList<String> mensagensDosClientes = new ArrayList<>();
    
    public void criarServerSocket(int porta) {
        try {
            //Instancia um serverSocket, responsavel por receber requisicoes pela rede e retornar
            //um resultado
            //Cria o server na porta especificada
            
            server = new ServerSocket(porta);
            System.out.println("Servidor criado ! ");
        } catch (Exception e) {
            System.out.println("Erro ao criar server " + e);
        }

    }

    public Socket criarConexao() throws IOException {

        //o serverSocket espera por uma requisicao e apos solicitado cria um Socket entre o cliente
        //requisitante e o serverSocket
        System.out.println("Esperando conexão ...");
        Socket socket = server.accept();
        return socket;

    }

    public void gerenciarConexao(Socket socket) {
        try {
            //O socket faz a ponte entre o cliente e servidor
            //Para cada cliente, existira um socket
            //Cliente ------ SOCKET ------- SERVIDOR
            
           ObjectOutputStream fluxoSaida = new ObjectOutputStream(socket.getOutputStream());
           ObjectInputStream fluxoEntrada = new ObjectInputStream(socket.getInputStream());
           
           //controle para que não seja permitido mandar mensagens em branco
           String mensagem = fluxoEntrada.readUTF();
           
           if(mensagem.equals((String)"")){
               System.out.println("Mensagem em branco encontrada ");
           }
           else{
           //mensagensDosClientes.add(fluxoEntrada.readUTF());
           mensagensDosClientes.add(mensagem);
           }
           fluxoSaida.writeObject((ArrayList) mensagensDosClientes);
           //fluxoSaida.writeUTF(fluxoEntrada.readUTF());
           fluxoSaida.flush();
           
           fluxoSaida.close();
           fluxoEntrada.close();
            
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
    
    
    public void novoServidor(){
      try{
        Server novoServidor = new Server();    
        novoServidor.criarServerSocket(4444);
        //O laço de repetição deve estar no método de esperar a conexão com o cliente
        //Não queremos ficar recriando o servidor a cada novo cliente conectado.
        while(true){
        Socket socket = novoServidor.criarConexao();
        System.out.println("Cliente conectado ");
        
        
        novoServidor.gerenciarConexao(socket);
       
        }
        }
        catch(Exception e){
        
            System.out.println("ERRO NA CRIAÇAO DO SERVIDOR " + e);    
        
        }
}
    
    
    
        public static void main(String args []){
        try{
        Server novoServidor = new Server();    
        novoServidor.criarServerSocket(4444);
        //O laço de repetição deve estar no método de esperar a conexão com o cliente
        //Não queremos ficar recriando o servidor a cada novo cliente conectado.
        while(true){
        Socket socket = novoServidor.criarConexao();
        System.out.println("Cliente conectado ");
        
        
        novoServidor.gerenciarConexao(socket);
       
        }
        }
        catch(Exception e){
        
            System.out.println("ERRO NA CRIAÇAO DO SERVIDOR " + e);    
        
        }
        
            
        
}
}


