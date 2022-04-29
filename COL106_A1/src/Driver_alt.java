import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Driver_alt {
    public static void main(String args[]) throws IOException{
        try {
            String filename = "D:\\Course Projects\\COL106-DataStruct\\Dynamic-Memory-Allocation\\COL106_A1\\src\\tests\\out_test16.txt";
            PrintWriter outputStream = new PrintWriter(filename);
            File myObj = new File("D:\\Course Projects\\COL106-DataStruct\\Dynamic-Memory-Allocation\\COL106_A1\\src\\tests\\test16.txt");
            Scanner sc = new Scanner(myObj);
            int numTestCases;
            numTestCases = sc.nextInt();
            while (numTestCases-- > 0) {
                int size;
                size = sc.nextInt();
                A1DynamicMem obj = new A1DynamicMem(size);
                int numCommands = sc.nextInt();
                while (numCommands-- > 0) {
                    String command;
                    command = sc.next();
                    int argument;
                    argument = sc.nextInt();
                    int result = -5;
                    switch (command) {
                        case "Allocate":
                            try {
                                result = obj.Allocate(argument);
                            } catch(IllegalArgumentException exc) {
                                result = -1;
                            }
                            break;
                        case "Free":
                            result = obj.Free(argument);
                            break;
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