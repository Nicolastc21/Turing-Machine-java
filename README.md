# Turing Machine Simulator in Java ⚙️

This project is a Java implementation of a Turing Machine with a semi-infinite tape. The program reads transition rules from a text file and processes an input string, simulating the movement of the read/write head on the tape.

## 📌 Features and Implemented Requirements

The project covers the core functionality of a Turing Machine, along with strict validation and monitoring mechanisms:

* **Dynamic Tape:** The tape is simulated using an `ArrayList`, automatically expanding to the right (with blank spaces `B`) as the read/write head advances.
* **Tape Fall-off Protection:** The tape is semi-infinite $[0, +\infty)$. If the machine is at index `0` and receives a command to move Left (`L`), execution stops immediately with an `ERROR_FELL_OFF` state.
* **Direction Validation:** Only `L` (Left) and `R` (Right) movements are accepted. Any other direction (e.g., `A`) stops the machine and throws an `ERROR_INVALID_DIRECTION` warning.
* **Cell 3 Monitoring:** The program prints an output to the console every time the machine's head passes over the cell at index `3`, keeping track of the visit count.
* **Consecutive State Transitions:** The machine monitors and alerts the user of any direct consecutive transition between states `q3` and `q4` (bidirectional).

## 📂 Project Structure

* `TuringMachine.java` - The main class containing the core loop, tape management, and validation logic.
* `Transition.java` - A separate data class representing a rule (current state, read symbol, write symbol, next state, direction).
* `mt.txt` - The configuration file containing the machine's rules and states.

## 🛠️ Configuration File Setup (`mt.txt`)

The text file must follow this exact structure:
1.  **Line 1:** Initial state (e.g., `q0`)
2.  **Line 2:** Final/Accepting states, separated by space (e.g., `q1 q4`)
3.  **Following lines:** Transition rules formatted as `[Current_State] [Read] [Write] [Next_State] [Direction]`

*Rule Example:* `q0 a b q1 R`

## ▶️ How to Run

To run the simulation, instantiate the `TuringMachine` class by providing the file path to your configuration file and the initial input string, then call the `run()` method.

```java
public class Main {
    public static void main(String[] args) {
        try {
            TuringMachine tm = new TuringMachine("mt.txt", "abbbb");
            tm.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
