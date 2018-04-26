/**
 * Message Transmission with HDLC protocol
 * Created by
 * Peerachai Banyongrakkul Sec.1 5988070
 * Boonyada Lojanarungsiri Sec.1 5988070
 * TransmissionTester.java
 */
import java.util.Scanner;
public class TransmissionTester 
{
    /* this function is to collect the data from sender, which are source address, destination address, message and type of message.  */
    public static dataPacket sender()
    {
       System.out.println("------------Sender------------");
       Scanner sc = new Scanner(System.in);
       System.out.print("Source Address: ");
       char s = sc.next().charAt(0);
       String temp = sc.nextLine();
       System.out.print("Destination Address: ");
       char d = sc.next().charAt(0);
       temp = sc.nextLine();
       System.out.print("Type: ");
       String t = sc.nextLine();
       System.out.print("Message: ");
       String m = sc.nextLine();
        /** check whether the message is exceed 100 bits or not. **/
       if(m.length() > 100)
       {
           System.out.print("Length of your input string is ");
           System.out.println(m.length());
           System.out.println("Error Detected!! Maximum length of your input string is 100.");
           System.exit(0);
       }
       System.out.println();
        /* call createPacket function to create packet */
       return createPacket(s,d,t,m);
    }
    
    /* this function is to create packet in binary form*/
    public static dataPacket createPacket(char s,char d, String type, String m)
    {
        String sa = "";
        String da = "";
        String t = "";
        byte[] bytes;
        StringBuilder Type = new StringBuilder();
        StringBuilder Message = new StringBuilder();
        /* source address can be only A, B, C and D */
        switch (s)
        {
            case 'A':
                sa = Integer.toBinaryString('A');
                break;
            case 'B':
                sa = Integer.toBinaryString('B');
                break;
            case 'C':
                sa = Integer.toBinaryString('C');
                break;
            case 'D':
                sa = Integer.toBinaryString('D');
                break;
            default:
                System.out.println("Wrong Input. You allow to input only \"A\",\"B\",\"C\",\"D\" for address.");
                System.exit(0);    
        }
        /* destination address can be only A, B ,C and D */
        switch (d)
        {
            case 'A':
                da = Integer.toBinaryString('A');
                break;
            case 'B':
                da = Integer.toBinaryString('B');
                break;
            case 'C':
                da = Integer.toBinaryString('C');
                break;
            case 'D':
                da = Integer.toBinaryString('D');
                break;
            default:
                System.out.println("Wrong Input. You allow to input only \"A\",\"B\",\"C\",\"D\" for address.");
                System.exit(0);    
        }
        /* types of message compose of high priority and low priority */
        switch (type)
        {
            case "high":
                t = type;
                bytes = t.getBytes();
                for (byte b : bytes)
                {
                   int val = b;
                   for (int i = 0; i < 8; i++)
                   {
                      Type.append((val & 128) == 0 ? 0 : 1);
                      val <<= 1;
                   }
                }
                break;
            case "low":
                t = type;
                bytes = t.getBytes();
                for (byte b : bytes)
                {
                   int val = b;
                   for (int i = 0; i < 8; i++)
                   {
                      Type.append((val & 128) == 0 ? 0 : 1);
                      val <<= 1;
                   }
                }
                break;
            default:
                System.out.println("Wrong Input. You allow to input only \"high\",\"low\" for type.");
                System.exit(0);    
        }
        /* convert message from string to binary */
        bytes = m.getBytes();
        for(byte b : bytes)
        {
            int val = b;
            for (int i = 0; i < 8; i++)
            {
                Message.append((val & 128) == 0 ? 0 : 1);
                val <<= 1;
            }
        }
        /* print out the converted information of the packet */
        dataPacket pk = new dataPacket(sa,da,Type.toString(),Message.toString());
        System.out.println("****************HDLC Format****************");
        System.out.println("Source Address: "+ pk.getSrcAddr());
        System.out.println("Destination Address: "+ pk.getDestAddr());
        System.out.println("Type: "+ pk.getType());
        System.out.println("Message: "+ pk.getMsg());
        System.out.println("*******************************************");     
        return pk;
    }
    
    /* this function is to send a packet to receiver */
    public static void send(dataPacket packet)
    {
        receiver(packet);
    }
    
    /* this function is to decode bits to normal text and display it. */
    public static void receiver(dataPacket packet)
    {
        int charCode;
        String msg = "";
        String type = "";
        System.out.println("\n------------Receiver------------");
        /* decode message from binary to string */
        System.out.print("Message from sender: ");
        for (int i = 0; i < packet.getMsg().length()/8; i++) 
        {
            int a = Integer.parseInt(packet.getMsg().substring(8*i,(i+1)*8),2);
            msg += (char)(a);
        }
        System.out.println(msg);
        System.out.print("Source address: ");
        charCode = Integer.parseInt(packet.getSrcAddr(), 2);
        String srcAddr = new Character((char)charCode).toString();
        System.out.println(srcAddr);
        System.out.print("Destination address: ");
        charCode = Integer.parseInt(packet.getDestAddr(), 2);
        String destAddr = new Character((char)charCode).toString();
        System.out.println(destAddr);
        /* decode message from binary to string */
        System.out.print("Type : ");
        for (int i = 0; i < packet.getType().length()/8; i++) {

            int a = Integer.parseInt(packet.getType().substring(8*i,(i+1)*8),2);
            type += (char)(a);
        }
        System.out.println(type);
        /* check whether the received message is "Thank you" or not*/
        if("Thank you".equalsIgnoreCase(msg)&&"high".equals(type))
        {
            System.out.println("\nYou're Welcome");
        }
        else
        {
            System.exit(0);
        }
    }
    
    
    public static void main(String[] args)
    {
        dataPacket packet = sender();
        send(packet);
    }
}
