import java.io.*;

public class bufferWriter {

    public static void main(String[] args) {

        try {
            FileOutputStream file = new FileOutputStream("exemplo.txt");
            file.write(7);
        } catch (Exception e) {
            System.out.print(e);
        }

        String[] names = {"Joao", "Barbara", "Carol"};
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));
            writer.write("Writing to a file");
            writer.write("\nWriting another line.");
            
            for(String i : names){
                writer.write("\n" + i);
            }

            writer.close();
          
            } catch(IOException e){
                e.printStackTrace();
            } 


        try {
            BufferedReader reader = new BufferedReader(new FileReader("output.txt"));
            String line;

            while((line = reader.readLine()) != null){
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
}
