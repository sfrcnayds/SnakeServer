
package game;



public class Message implements java.io.Serializable {
    //mesaj tipleri enum
    public  enum Message_Type {None, Name, Disconnect, Move, Selected, Bitis,Start,UpArrow,LeftArrow,RightArrow,DownArrow}
    //mesajın tipi
    public Message_Type type;
    //mesajın içeriği obje tipinde ki istenilen tip içerik yüklenebilsin
    public Object content;

    public Message(Message_Type t) {
        this.type=t;
    }

    public Message(Message_Type type, Object content) {
        this.type = type;
        this.content = content;
    }
}
