import java.text.ParseException;
import java.util.HashMap;
import java.util.Set;

class Parser {
    private LexicalAnalyzer lex;
    //private HashMap<Terminal, Set<Character>> firstByTerminal = new HashMap<Terminal, Set<Character>>();
    //private HashMap<NotTerminal, Set<Character>> firstByNotTerminal = new HashMap<NotTerminal, Set<Character>>();
    Tree parse(String input) throws ParseException {
        lex = new LexicalAnalyzer(input);
        lex.nextTerminal();
        return S();
    }
    /*private MakeFirst(){

    }
    private MakeFollow(){

    }*/
    //S -> N E S
    //S -> ε
    private Tree S() throws ParseException {
        switch (lex.curTerminal()) {
            case VARORTYPE:
                //N
                String name = lex.getTerminalText();
                Tree var = new Tree(name);
                lex.nextTerminal();
                //E
                Tree e = E();
                //S
                Tree s = S();
                return new Tree("Str" + " (" + name + ")", e, s);
            case END:
                //ε
                return new Tree("Str", new Tree("$"));
            default:
                throw new AssertionError(lex.curTerminal() + " found as first token of S in Parser");
        }
    }
    //E -> N0 E1
    private Tree E() throws ParseException {
        if(lex.curTerminal() == Terminal.POINTER || lex.curTerminal() == Terminal.VARORTYPE) {
            //N0
            Tree n0 = N0();
            //E1;
            Tree e1 = E1();
            return new Tree("NotEmptyEnum", n0, e1);
        } else {
            throw new AssertionError(lex.curTerminal() + " found as first token of E in Parser");
        }
    }
    //N0 -> * N0
    //N0 -> N
    private Tree N0() throws ParseException {
        switch (lex.curTerminal()) {
            case POINTER:
                //*
                lex.nextTerminal();
                //N0
                Tree n0 = N0();
                return new Tree("VarOrPointer", new Tree("*"), n0);
            case VARORTYPE:
                //N
                String name = lex.getTerminalText();
                lex.nextTerminal();
                return new Tree("VarOrPointer", new Tree(name));
            default:
                throw new AssertionError(lex.curTerminal() + " found as first token of N0 in Parser");
        }
    }
    //E1 -> , E
    //E1 -> ;
    private Tree E1() throws ParseException {
        switch (lex.curTerminal()) {
            case COMMA:
                //,
                lex.nextTerminal();
                //E
                Tree e = E();
                return new Tree("Enum", new Tree(","), e);
            case SEMICOLON:
                //;
                lex.nextTerminal();
                return new Tree("Enum", new Tree(";"));
            default:
                throw new AssertionError(lex.curTerminal() + " found as first token of E1 in Parser");
        }
    }
}