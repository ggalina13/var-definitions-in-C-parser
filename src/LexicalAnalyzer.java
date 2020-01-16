import javafx.util.Pair;

import java.text.ParseException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class LexicalAnalyzer {

    private final String input;

    private char curChar;
    private int curPos;
    private Terminal curTerminal;
    private String curTerminalText;

    LexicalAnalyzer(String input) {
        this.input = input;
        curPos = 0;
        rgxByTerminal.put(Terminal.COMMA, ",");
        rgxByTerminal.put(Terminal.END, "\\$");
        rgxByTerminal.put(Terminal.SEMICOLON, ";");
        rgxByTerminal.put(Terminal.VARORTYPE, "[^,\\$;\\*\n\t\r &]+");
        rgxByTerminal.put(Terminal.POINTER, "\\*");
        nextChar();
    }

    HashMap<Terminal, String> rgxByTerminal = new HashMap<Terminal, String>();

    private void nextChar() {
        curChar = input.charAt(curPos++);
    }

    String getTerminalText() {
        return curTerminalText;
    }

    Terminal curTerminal() {
        return curTerminal;
    }

    private boolean isBlank(char c) {
        return c == ' ' || c == '\r' || c == '\n' || c == '\t';
    }

    private boolean isVarOrTypeChar(char c) {
        return !isBlank(c) && c != ',' && c != ';' && c != '*' && c != '&';
    }

    Pair<Integer, Integer> findFrom(String rgx, String str, Integer from){
        Pattern pattern = Pattern.compile(rgx);
        Matcher matcher = pattern.matcher(str);
        if (!(matcher.find(from) && matcher.start() == from))
            return null;
        else
            return new Pair(matcher.start(), matcher.end());
    }
    void nextTerminal() throws ParseException {
        while ((curPos < input.length()) && isBlank(curChar)) {
            nextChar();
        }
        Boolean found = false;
        for (Terminal token : Terminal.values()){
            Pair<Integer, Integer> tokenStartEnd = findFrom(rgxByTerminal.get(token), input, curPos - 1);
            if (tokenStartEnd != null){
                curTerminal = token;
                Integer curTerminalStart = tokenStartEnd.getKey();
                Integer curTerminalEnd = tokenStartEnd.getValue();
                curPos = curTerminalEnd;
                curTerminalText = input.substring(curTerminalStart, curTerminalEnd);
                if (curPos < input.length())
                    nextChar();
                found = true;
                break;
            }
        }
        if (!found)
            throw new ParseException( "Illegal character " + curChar, curPos) ;
    }
}