import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TuringMachine {

    private static final String BLANK = "B";

    private String currentState;
    private List<String> finalStates = new ArrayList<>();
    private List<Transition> transitions = new ArrayList<>();
    private List<String> tape = new ArrayList<>();
    private int headPosition = 0;

    public TuringMachine(String fileName, String input) throws IOException {
        loadFromFile(fileName);
        initTape(input);
    }

    private void initTape(String input) {
        for (int i = 0; i < input.length(); i++) {
            tape.add(String.valueOf(input.charAt(i)));
        }
    }

    private String readSymbol() {
        if (headPosition >= tape.size()) {
            return BLANK;
        }
        return tape.get(headPosition);
    }

    private void writeSymbol(String symbol) {
        while (headPosition >= tape.size()) {
            tape.add(BLANK);
        }
        tape.set(headPosition, symbol);
    }

    private void loadFromFile(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        currentState = br.readLine().trim();
        String[] finals = br.readLine().trim().split(" ");
        finalStates.addAll(Arrays.asList(finals));
        String line;
        while ((line = br.readLine()) != null) {
            String[] p = line.trim().split(" ");
            if (p.length < 5) continue; 
            String state = p[0];
            String read = p[1];
            String write = p[2];
            String nextState = p[3];
            char direction = p[4].charAt(0);
            transitions.add(new Transition(state, read, write, nextState, direction));
        }

        br.close();
    }

    public void run() {
        int step = 0;
      
        while (true) {
            step++;
            String symbol = readSymbol();
            printStep(step, symbol);
            printTape(); 

            if (headPosition == 3) {
                System.out.println("WARNING-Machine passed through cell 3 ");
            }
            Transition foundTransition = null;
            for (Transition t : transitions) {
                if (t.state.equals(currentState) && t.read.equals(symbol)) {
                    foundTransition = t;
                    break;
                }
            }

            if (foundTransition == null) {
                System.out.println("STOP - No transition found.");
                break;
            }

            writeSymbol(foundTransition.write);
            
            String previousState = currentState;
            currentState = foundTransition.nextState;
            if ((previousState.equals("q3") && currentState.equals("q4")))  {
                System.out.println("WARNING-Machine passed through states q3 and q4 consecutively");
            }

            if (foundTransition.direction == 'R') {
                headPosition++;
            } else if (foundTransition.direction == 'L') {
                if (headPosition > 0) {
                    headPosition--;
                } else {
                    System.out.println("STOP - Machine fell off the tape to the left !");
                    currentState = "ERROR_FELL_OFF"; 
                    break;
                }
            } else {
                System.out.println("STOP - Invalid direction '" + foundTransition.direction + "' encountered!");
                currentState = "ERROR_INVALID_DIRECTION"; 
                break;
            }
        }

        if (finalStates.contains(currentState)) {
            System.out.println("\nRESULT: INPUT ACCEPTED (Machine stopped in final state '" + currentState + "')");
        } else {
            System.out.println("\nRESULT: INPUT REJECTED");
        }

        System.out.println("\nFinal tape:");
        printTape();
    }

    private void printStep(int step, String symbol) {
        System.out.println("Step " + step + " | State: " + currentState + " | Position: " + headPosition + " | Read: " + symbol);
    }

    private void printTape() {
        System.out.print("Tape: ");
        int maxIndex = Math.max(tape.size() - 1, headPosition);
        for (int i = 0; i <= maxIndex; i++) {
            if (i == headPosition) System.out.print("[");
            if (i < tape.size()) {
                System.out.print(tape.get(i));
            } else {
                System.out.print(BLANK);
            }
            
            if (i == headPosition) System.out.print("]");
        }
        System.out.println("\n");
    }
}