 import java.net.*;
 import java.io.*;
 
 class Server
{
    ServerSocket server; //it is for server
    Socket socket; //it is for client
    BufferedReader br; //for reading data or msg coming from client
    PrintWriter out; //for writing data or msg for client
    public Server() //constructor
    {
        try
        {
        server=new ServerSocket(7777); //it will store msg of serversocket in server
        System.out.println("server is ready to accept connection");
         System.out.println("waiting...");
         socket=server.accept();
         br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
         out=new PrintWriter(socket.getOutputStream());
         startReading();
         startWriting();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public void startReading()
    {
        //thread for reading data
        Runnable r1=()->
        {
            System.out.println("reader started");
            while(true) //it will read data every time
            {
                try
                {
                String msg=br.readLine();
                if(msg.equals("exit"))
                {
                    System.out.println("client terminated"); //it will stop reader
                    break;
                }
                System.out.println("client: "+msg);
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }


            }


        };
        new Thread(r1).start();

    }
    public void startWriting()
    {
        Runnable r2=()->
        {
            System.out.println("writer started...");
            while(true)
            { //it will provide content to write every time
            try
            {
                BufferedReader br1=new BufferedReader(new InputStreamReader(System.in));
                String content=br1.readLine();
                out.println(content);
                out.flush();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            }

        };
        new Thread(r2).start();
    }
    public static void main(String[] args)
    {
        System.out.println("this is server");
        new Server();
    }
}