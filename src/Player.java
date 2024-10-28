import java.util.ArrayList;
import java.util.List;

public class Player {
    private HandChoice hand;
    private int numWins;

    // the order of the key choices should be in the same order as HandChoice, except
    // there is no need to supply a key choice for HandChoice.None
    private List<Character> gameKeys = new ArrayList<Character>(5);

    public Player() {
        init();
    }

    public Player(char[] gameKeys) {
        init();
        // save the game key choices
        for (char gameKey : gameKeys) {
            this.gameKeys.add(gameKey);
        }
    }

    private void init() {
        hand = HandChoice.None;
        numWins = 0;
    }

    public void play(HandChoice move) {

        switch (move) {

            case Rock:
            case Paper:
            case Scissors:
            case Lizard:
            case Spock:
                hand = move;
                break;

            case None:
            default:
                break;
        }
    }

    // returns true if the specified key is used by this player as one of his/her choices
    public boolean usesKey(char c) {
        return gameKeys.contains(c);
    }

    public void reset() {
        hand = HandChoice.None;
    }

    // returns the hand choice of this player
    public HandChoice getHand() {
        return hand;
    }

    public void addWin() {
        numWins += 1;
    }

    public int getWins() {
        return numWins;
    }

    // returns which hand choice the specified characters is used for
    // or none if the character is not used by this player as one of his hand choices.
    public HandChoice getHandChoiceByKey(char c) {
        if (usesKey(c)) {
            int i = gameKeys.indexOf(c);
            return HandChoice.values()[i];
        } else {
            return HandChoice.None;
        }
    }

    // add the specified character to the list of hand choices used by this
    // player unless the key is already being used.
    public void addKeyChoice(char c, HandChoice hand) {
        if (gameKeys.contains(c)) {
            // key is already in use, ignore attempt to use same character for multiple hand choices
            return;
        }
        // put the key choice into the list according to its ordinal position in the enum
        gameKeys.add(hand.ordinal(), c);
    }

    // get a set of instructions that show which keys are used for which hand choices
    public String getInstructions() {
        StringBuffer instructions = new StringBuffer();
        for (Character c : gameKeys) {
            instructions.append(String.format("%s:%c ", getHandChoiceByKey(c).toString(), c));
        }
        return instructions.toString().trim();
    }
}