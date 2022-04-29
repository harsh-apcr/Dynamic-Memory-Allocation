import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Driver_alt {
    public static void main(String args[]) {
        try {
            String filename = "D:\\Course Projects\\COL106-DataStruct\\Dynamic-Memory-Allocation\\COL106_A2\\src\\tests\\testout500k.txt";
            PrintWriter outputStream = new PrintWriter(filename);
            File myObj = new File("D:\\Course Projects\\COL106-DataStruct\\Dynamic-Memory-Allocation\\COL106_A2\\src\\tests\\test500k.txt");
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
                            try {
                                result = obj.Allocate(argument);
                            } catch (IllegalArgumentException exc) {result = -1;}
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