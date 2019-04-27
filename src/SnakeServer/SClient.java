package SnakeServer;

import game.Message;
import static game.Message.Message_Type.Selected;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import static java.lang.Thread.sleep;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SClient {
    int id;
    public String name = "NoName";
    private Socket soket;
    ObjectOutputStream sOutput;
    ObjectInputStream sInput;
    //clientten gelenleri dinleme threadi
    Listen listenThread;

    public SClient(Socket gelenSoket, int id) {
        this.soket = gelenSoket;
        this.id = id;
        try {
            this.sOutput = new ObjectOutputStream(this.soket.getOutputStream());
            this.sInput = new ObjectInputStream(this.soket.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(SClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        //thread nesneleri
        this.listenThread = new Listen(this);
    }

    //client mesaj gönderme
    public void Send(Message message) {
        try {
            this.sOutput.writeObject(message);
        } catch (IOException ex) {
            Logger.getLogger(SClient.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //client dinleme threadi
    //her clientin ayrı bir dinleme thredi var
    class Listen extends Thread {
        SClient TheClient;
        //thread nesne alması için yapıcı metod
        Listen(SClient TheClient) {
            this.TheClient = TheClient;
        }

        public void run() {
            //client bağlı olduğu sürece dönsün
            while (TheClient.soket.isConnected()) {
                try {
                    //mesajı bekleyen kod satırı
                    Message received = (Message) (TheClient.sInput.readObject());
                    //mesaj gelirse bu satıra geçer
                    //mesaj tipine göre işlemlere ayır
                    switch (received.type) {
                        case Name:
                            TheClient.name = received.content.toString();
                            // isim verisini gönderdikten sonra eşleştirme işlemine başla
                            break;
                        case UpArrow:
                            if ((!Board.downDirection)) {
                                Board.leftDirection = false;
                                Board.upDirection = true;
                                Board.rightDirection = false;
                            }
                            break;
                        case DownArrow:
                            if ((!Board.upDirection)) {
                                Board.leftDirection = false;
                                Board.rightDirection = false;
                                Board.downDirection = true;
                            }
                            break;
                        case LeftArrow:
                            if ((!Board.rightDirection)) {
                                Board.leftDirection = true;
                                Board.upDirection = false;
                                Board.downDirection = false;
                            }
                            break;
                        case RightArrow:
                            if ((!Board.leftDirection)) {
                                Board.rightDirection = true;
                                Board.upDirection = false;
                                Board.downDirection = false;
                            }
                            break;
                        case Disconnect:
                            break;
                    }

                } catch (IOException | ClassNotFoundException ex) {
                    Logger.getLogger(SClient.class.getName()).log(Level.SEVERE, null, ex);
                    //client bağlantısı koparsa listeden sil
                    Server.Clients.remove(TheClient);
                    this.stop();
                }
                //client bağlantısı koparsa listeden sil

            }

        }
    }
}

