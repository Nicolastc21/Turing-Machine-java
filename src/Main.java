public class Main {
    public static void main(String[] args) throws Exception {
        String input = "abbbb";
        TuringMachine tm = new TuringMachine("mt.txt", input);
        tm.run();
    }
} 