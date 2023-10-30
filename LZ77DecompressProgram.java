import java.util.ArrayList;
import java.util.Scanner;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class LZ77DecompressProgram {
    public static String decompress(ArrayList<LZ77CompressProgram.Token> tokens) {
        StringBuilder result = new StringBuilder();
        for (LZ77CompressProgram.Token token : tokens) {
            int startPos = result.length() - token.offset;
            for (int i = 0; i < token.length; i++) {
                result.append(result.charAt(startPos + i));
            }
            if (token.character != '?') {
                result.append(token.character);
            }
        }
        return result.toString();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        ArrayList<LZ77CompressProgram.Token> compressed = new ArrayList<>();

        System.out.println("Enter the number of Tokens: ");
        int nTokens = scanner.nextInt();

        for (int i = 0; i < nTokens; i++) {
            int offset, length;
            char nextSymbol;

            offset = scanner.nextInt();
            length = scanner.nextInt();
            nextSymbol = scanner.next().charAt(0);

            LZ77CompressProgram.Token token = new LZ77CompressProgram.Token(offset, length, nextSymbol);
            compressed.add(token);
        }

        String decompressed = decompress(compressed);

        try {
            FileWriter fileWriter = new FileWriter("decompressed.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write(decompressed);
            bufferedWriter.close();
        } catch (IOException e) {
            System.err.println("An error occurred while writing the output to the file");
        }
    }
}
