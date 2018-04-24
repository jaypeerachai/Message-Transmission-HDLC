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
       if(m.length() > 100)
       {
           System.out.print("Length of your input string is ");
           System.out.println(m.length());
           System.out.println("Error Detected!! Maximum length of your input string is 100.");
           System.exit(0);
       }
       System.out.println();
       return createPacket(s,d,t,m);
    }
    
    
    public static dataPacket createPacket(char s,char d, String type, String m)
    {
        String sa = "";
        String da = "";
        String t = "";
        byte[] bytes;
        StringBuilder Type = new StringBuilder();
        StringBuilder Message = new StringBuilder();
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
        dataPacket pk = new dataPacket(sa,da,Type.toString(),Message.toString());
        System.out.println("****************HDLC Format****************");
        System.out.println("Source Address: "+ pk.getSrcAddr());
        System.out.println("Destination Address: "+ pk.getDestAddr());
        System.out.println("Type: "+ pk.getType());
        System.out.println("Message: "+ pk.getMsg());
        System.out.println("*******************************************");     
        return pk;
    }
    
    
    public static void send(dataPacket packet)
    {
        receiver(packet);
    }
    
    
    public static void receiver(dataPacket packet)
    {
        int charCode;
        String msg = "";
        String type = "";
        System.out.println("\n------------Receiver------------");
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
        System.out.print("Type : ");
        for (int i = 0; i < packet.getType().length()/8; i++) {

            int a = Integer.parseInt(packet.getType().substring(8*i,(i+1)*8),2);
            type += (char)(a);
        }
        System.out.println(type);
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
