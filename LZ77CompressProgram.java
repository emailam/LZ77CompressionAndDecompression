import java.io.FileWriter;
import java.util.ArrayList;
import java.io.IOException;
import java.io.BufferedWriter;

// This program to compress a string using the 'lz77' algorithm.
public class LZ77CompressProgram {

    // Class for the token <offset, length, nextSymbol>
    // Go back 'offset' times and take 'length' characters and the next character is 'nextSymbol'.
    public static class Token {
        int offset;
        int length;
        char character;

        public Token(int offset, int length, char character) {
            this.offset = offset;
            this.length = length;
            this.character = character;
        }
    }

    // Function to find the longest match starting at index 'currentIndex' and index 'current - searchOffset'.
    private static int findMatchLength(String input, int currentIndex, int searchOffset) {
        int maxLength = Math.min(currentIndex - searchOffset + 1, input.length() - currentIndex);
        int length = 0;
        for (int i = 0; i < maxLength; i++) {
            if (input.charAt(currentIndex + i) == input.charAt(currentIndex - searchOffset + i)) {
                length++;
            } else {
                break;
            }
        }
        return length;
    }

    public static ArrayList<Token> compress(String input) {
        ArrayList<Token> result = new ArrayList<>();
        int currentIndex = 0;
        while (currentIndex < input.length()) {
            int longestMatchLength = 0;
            int bestMatchOffset = 0;

            // Brute force over all the values of the offset to find the best one.
            for (int searchOffset = 1; searchOffset <= currentIndex; searchOffset++) {
                int matchLength = findMatchLength(input, currentIndex, searchOffset);

                if (matchLength > longestMatchLength) {
                    longestMatchLength = matchLength;
                    bestMatchOffset = searchOffset;
                }
            }
            // '?' indicates that the nextSymbol is null since you can not save null as a value of a character.
            char currentChar = '?';
            if (currentIndex + longestMatchLength < input.length()) {
                currentChar = input.charAt(currentIndex + longestMatchLength);
            }
            Token token = new Token(bestMatchOffset, longestMatchLength, currentChar);
            result.add(token);
            currentIndex += longestMatchLength + 1;

        }
        return result;
    }

    public static void main(String[] args) {
        String input = "abbcabbcabbcabbc";
        ArrayList<Token> compressed = compress(input);
        try {
            FileWriter fileWriter = new FileWriter("compressed.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            for (Token x : compressed) {
                String currentToken = x.offset + " " + x.length + " " + x.character;

                bufferedWriter.write(currentToken);
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
        } catch (IOException e) {
            System.err.println("An error occurred while writing the output in the file.");
        }
    }
}
