import java.io.*;
import java.nio.file.Files;


/**
 * Created by Дим(ON) on 17.08.2015.
 */
public class Flow extends Thread {


    public void run(File file) throws IOException
    {
        String pt = "C:\\d1\\";
        String fn=file.getName();
        int x1=fn.lastIndexOf('.');
        String ss1=fn.substring(0,x1);
        String ss2=fn.substring(x1,fn.length());
        File t = new File( pt + ss1+"(copy)"+ss2);
        try{

            if (t.exists())    //if copy is already exists its changing copies name
            {
                int copies = 1;
                while(t.exists())
                {
                    System.out.println("exception");
                    t= new File((pt+ss1+"(copy" + copies + ")" + ss2));
                    if(t.exists())
                        copies++;
                    else
                        break;
                }
                Files.copy(file.toPath(), t.toPath());


            }
            else
            {
                Files.copy(file.toPath(), t.toPath());
            }
            update(t);
            Program1.count--;
            System.out.println("done");
        }
        catch (java.nio.file.NoSuchFileException e)
        {
            new File("C:\\d1").mkdir();
            run(file);
        }



    }

    public void update(File f) throws IOException {
        FileWriter fw = new FileWriter(f, true);
        fw.append("someChange");
        fw.close();
    }
}
    class Program1
    {
        public static final int limit=4;

        public static int count =1;


        public static void main(String[] args) throws IOException
        {
            DoThing();
        }
        public static void DoThing() throws IOException
        {
            BufferedReader r=new BufferedReader(new InputStreamReader(System.in));
            System.out.println("write path");

            String s = r.readLine();
            if (s.equals("exit"))
                return;
            else {
                File folder = new File(s);
                File[] files = folder.listFiles();
                if (files == null)
                    System.out.println("wrong path");
                else {

                    int i = 0;
                    while (i < files.length && count <= limit) {
                        Flow f = new Flow();
                        f.run(files[i]);
                        f.start();
                        count++;
                        i++;
                    }
                }

                DoThing();
            }
        }
    }


