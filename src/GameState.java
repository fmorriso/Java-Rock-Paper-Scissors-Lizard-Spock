/**
 * Valid game states
 */
public enum GameState {
    Draw {@Override public String toString(){return "It's a draw";}},
    Player1Wins {@Override public String toString(){return "Player 1 wins";}},
    Player2Wins {@Override public String toString(){return "Player 2 wins";}},
    InProgress {@Override public String toString(){return "In progress";}}
}
