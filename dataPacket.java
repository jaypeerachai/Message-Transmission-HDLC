/**
 * Message Transmission by HDLC protocol
 * Created by
 * Peerachai Banyongrakkul Sec.1 5988070
 * Boonyada Lojanarungsiri Sec.1 5988070
 * dataPacket.java
 */
public class dataPacket 
{
    private final String srcAddr; 
    private final String destAddr;
    private final String type;
    private final String msg;
    
    public dataPacket(String src, String dest, String t, String m)
    {
        this.srcAddr = src;
        this.destAddr = dest;
        this.type = t;
        this.msg = m;
    }
    
    public String getSrcAddr()
    {
        return this.srcAddr;
    }
    
    public String getDestAddr()
    {
        return this.destAddr;
    }
    
    public String getType()
    {
        return this.type;
    }
    
    public String getMsg()
    {
        return this.msg;
    }
}
