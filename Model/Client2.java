/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 *
 * @author ArthurA
 */
public class Client2 {



    ObjectOutputStream escreverAoServidor;
    ObjectInputStream lerDoServidor;
    Socket socket;
    

    public void criaCliente() {
        
        
        try {

            this.socket = new Socket("localhost", 4444);
            System.out.println("Cliente conectado");
            
            //Criando fluxo de dados de saída, ou seja, que serão enviados ao servidor
            //o método getOutputStream pega tudo aquilo que o cliente quer passar e manda para
            //frente 
            //InputStream = responsável por ler os dados (em forma de byte)de entrada de algum local
            //OutputStream = classe responsavel por escrever os dados(em forma de byte) em algum destino
            //Acontece que se olharmos na classe InputStream veremos que ela é abstrata
            //e nós somos obrigados a usar alguma outra classe que a implemente para fazer uso dos seus recursos. 
            
           
                OutputStream saidaDados = socket.getOutputStream();
                InputStream entradaDados = socket.getInputStream();
              
                //ObjectOutputStream e ObjectInputStream nos permite escrever os tipos de dados primitivos a um outputStream ou InputStream
                //tornando o processo de envio e recebimento de dados mais fácil do que utilizando o trabalho com bytes.
                escreverAoServidor = new ObjectOutputStream(saidaDados);
                lerDoServidor = new ObjectInputStream(entradaDados);
                        
              
                escreverAoServidor.writeUTF("Cliente responde: Cliente 2 mandando mensagem !");
                escreverAoServidor.flush();
                

                String mensagemDoServidor = lerDoServidor.readUTF();

                System.out.println(mensagemDoServidor);
                
                
                entradaDados.close();
                saidaDados.close();
                socket.close();
            
            
        } catch (Exception e) {
            System.out.println("Erro " + e);
            e.printStackTrace();
        }

        
    }

    public static void main(String args[]) {
        try {

            Client2 cliente = new Client2();
            while(true)
            cliente.criaCliente();

        } catch (Exception e) {

            System.out.println("ERRO NA CRIA~ÇÃO DO CLIENTE " + e);

        }

    }
}


