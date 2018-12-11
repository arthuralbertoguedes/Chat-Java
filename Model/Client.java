/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author ArthurA
 */
public class Client {
//pacotes utilizado: Socket 
//Primeiro passo é criar um socket para o lado do cliente
//define o ip e a porta utilizada

    ObjectOutputStream escreverAoServidor;
    ObjectInputStream lerDoServidor;
    Socket socket;
    String msg, nome;

    public Client(String msg, String nome) {
        this.msg = msg;
        this.nome = nome;
    }

    //Construtor especifico para criar uma comunicaçao e consultar servidor
    public Client() {

    }

    public ArrayList criaCliente() {

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

            escreverAoServidor.writeUTF(nome + " diz: " + msg);
            escreverAoServidor.flush();
            //String mensagemDoServidor = lerDoServidor.readUTF();

            //Recebe o ArrayList do servidor
            ArrayList<String> mensagemRecebida = (ArrayList) lerDoServidor.readObject();

            entradaDados.close();
            saidaDados.close();
            socket.close();
            return mensagemRecebida;

        } catch (Exception e) {
            System.out.println("Erro " + e);
            e.printStackTrace();
            return null;
        }

    }

    /*public static void main(String args[]) {
        try {

            Client cliente = new Client();
            while(true)
            cliente.criaCliente();

        } catch (Exception e) {
d
    }*/
    public ArrayList solicitaChat() throws IOException, ClassNotFoundException {

        Socket socket = new Socket("localhost", 4444);
        
        OutputStream dadosParaServidor = socket.getOutputStream();
        InputStream dadosDoServidor = socket.getInputStream();
        
        ObjectOutputStream saida = new ObjectOutputStream(dadosParaServidor);
        ObjectInputStream dados = new ObjectInputStream(dadosDoServidor);
        
        saida.writeUTF("");
        saida.flush();
        
        ArrayList<String> conversas = (ArrayList) dados.readObject();
       
        dados.close();
        dadosDoServidor.close();
        socket.close();
        return conversas;
       
      
       
    }

}
