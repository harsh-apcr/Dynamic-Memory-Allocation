import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Driver_alt {
    public static void main(String args[]) throws IOException{
        try {
            String filename = "OutputA2.txt";
            PrintWriter outputStream = new PrintWriter(filename);
            File myObj = new File("./Test_2_A2.txt");
            Scanner sc = new Scanner(myObj);
            int numTestCases;
            numTestCases = sc.nextInt();
            while (numTestCases-- > 0) {
                int size;
                size = sc.nextInt();
                A2DynamicMem obj = new A2DynamicMem(size,2);
                int numCommands = sc.nextInt();
                while (numCommands-- > 0) {
                    String command;
                    command = sc.next();
                    int argument;
                    argument = sc.nextInt();
                    int result = -5;
                    switch (command) {
                        case "Allocate":
                            result = obj.Allocate(argument);
                            break;
                        case "Free":
                            result = obj.Free(argument);
                            break;
                        case "Defragment":
                            obj.Defragment();
                            continue;
                        default:
                            break;
                    }
                    outputStream.print(result);
                    outputStream.println("");
                }
            }
            outputStream.close();
            System.out.println("Done");
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}