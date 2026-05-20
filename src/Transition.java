public class Transition {
        String state;
        String read;
        String write;
        String nextState;
        char direction;

        public Transition(String state, String read, String write, String nextState, char direction) {
            this.state = state;
            this.read = read;
            this.write = write;
            this.nextState = nextState;
            this.direction = direction;
        }
    }