import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.Graphics; import java.awt.image.BufferedImage; import java.io.File; import java.io.IOException;
import javax.imageio.ImageIO; import javax.swing.JPanel;

public class Game extends JPanel implements KeyListener {
    
    char c;
    Player  player1, player2;
    boolean gameOver;
    private BufferedImage imageRock, imagePaper, imageScissors, imageLizard, imageSpock, imageDraw;
    GameState result;  // This should be set to "Game in Progress", "It's a Draw", "Player 1 Won", or "Player 2 Won" depending on who has won the current round
    int numDraws;

    private JFrame frame;

    private static final char resetGameKey = 'r';
    private int imageWidth;;

    public Game(final JFrame frame) {

        this.frame = frame;

        // set up player #1 using the no-parameters constructor
        // and the add each key choice individually
        player1 = new Player();
        player1.addKeyChoice('a', HandChoice.Rock);
        player1.addKeyChoice('s', HandChoice.Paper);
        player1.addKeyChoice('d', HandChoice.Scissors);
        player1.addKeyChoice('f', HandChoice.Lizard);
        player1.addKeyChoice('g', HandChoice.Spock);

        // set up player #2 using game hand choice characters positioned as
        // Rock, Paper, Scissor, Lizard, Spock (in that order)
        player2 = new Player(new char[]{'y', 'u', 'i', 'o','p'});

        // put some instructions in the title bar area of the parent JFrame we're running inside of
        frame.setTitle(String.format("#1 %s  #2 %s",player1.getInstructions(),player2.getInstructions()));;

        //initialize gameOver
        gameOver = false;
        result = GameState.InProgress;
        File f = new File("images/rock.jpg");
        System.out.println(f.exists());
        try {
            imageRock = ImageIO.read(new File("images/rock.jpg"));
            imagePaper = ImageIO.read(new File("images/paper.jpg"));
            imageScissors = ImageIO.read(new File("images/scissors.jpg"));
            imageLizard = ImageIO.read(new File("images/lizard.jpg"));
            imageSpock = ImageIO.read(new File("images/spock.jpg"));
            imageDraw = ImageIO.read(new File("images/nuclear.jpg"));
            imageWidth = imageRock.getWidth();

        } catch (IOException ex) { 
            // handle exception...   
        } 
    }

    /** Handle the key released event from the text field. */
    public void keyReleased(KeyEvent e) {
        c = e.getKeyChar();

        String msg;

        if(!gameOver){

            if(player1.usesKey(c)){
                player1.play(player1.getHandChoiceByKey(c));
            } else if (player2.usesKey(c)){
                player2.play(player2.getHandChoiceByKey(c));
            } else if (c == resetGameKey) {
                resetTheGame();
            } else {
                msg = String.format("Key %c is not used in this game", c);
                System.out.println(msg);
            }

            if(!player1.getHand().equals(HandChoice.None) && !player2.getHand().equals(HandChoice.None)){
                gameOver = true;
                declareWinner();
            }
            
        }

        if (c == resetGameKey) {
            resetTheGame();
        }

        repaint();
    }

    private void resetTheGame() {
        String msg;
        player1.reset();
        player2.reset();
        gameOver = false;
        result = GameState.InProgress;
        msg = String.format("%c key pressed - Game as been reset", resetGameKey);
        System.out.println(msg);
    }

    public void declareWinner() {
        //decide who won, increment draws or wins, and set the result

        String fmt = "Player %d played a %s.";
        System.out.println( String.format(fmt, 1, player1.getHand()) );
        System.out.println( String.format(fmt, 2, player2.getHand()) );

        evaluateKeySelections();
    }

    private void declareTie() {
        result = GameState.Draw;
        System.out.println(result);
        numDraws++;
    }

    // evaluate the key selections of each player to determine what to do
    private void evaluateKeySelections() {

        switch(player1.getHand()){

            case Rock:
                switch(player2.getHand()){

                    case Rock:
                        declareTie();
                        break;

                    case Paper:
                        // paper covers rock
                    case Spock:
                        // Spock vaporizes rock
                        result = GameState.Player2Wins;
                        System.out.println(result);
                        player2.addWin();
                        break;

                    case Scissors:
                        // rock crushes scissors
                    case Lizard:
                        // rock crushes lizard
                        result = GameState.Player1Wins;
                        System.out.println(result);
                        player1.addWin();
                        break;

                    case None:
                    default:
                        break;
                }
                break;

            case Paper:
                switch(player2.getHand()){

                    case Rock:
                        // paper covers rock
                    case Spock:
                        // paper disproves Spock
                        result = GameState.Player1Wins;
                        System.out.println(result);
                        player1.addWin();
                        break;

                    case Paper:
                        declareTie();
                        break;

                    case Scissors:
                        // scissors cuts paper
                    case Lizard:
                        // lizard eats paper
                        result = GameState.Player2Wins;
                        System.out.println(result);
                        player2.addWin();
                        break;

                    case None:
                    default:
                        break;
                }
                break;

            case Scissors:
                switch(player2.getHand()){

                    case Rock:
                        // Spock smashes scissors
                    case Spock:
                        // rock crushes scissors
                        result = GameState.Player2Wins;
                        System.out.println(result);
                        player2.addWin();
                        break;

                    case Paper:
                        // scissors cuts paper
                    case Lizard:
                        // scissors decapitate lizard
                        result = GameState.Player1Wins;
                        System.out.println(result);
                        player1.addWin();
                        break;

                    case Scissors:
                        declareTie();
                        break;

                    case None:
                    default:
                        break;
                }
                break;

            case Lizard:
                switch(player2.getHand()) {

                    case Rock:
                        // rock crushes lizard
                    case Scissors:
                        // scissors decapitates lizard
                        result = GameState.Player2Wins;
                        System.out.println(result);
                        player2.addWin();
                        break;

                    case Paper:
                        // lizard eats paper
                    case Spock:
                        // lizard poisons Spock
                        result = GameState.Player1Wins;
                        System.out.println(result);
                        player1.addWin();
                        break;

                    case Lizard:
                        declareTie();
                        break;

                    case None:
                    default:
                        break;
                }
                break;

            case Spock:
                switch(player2.getHand()) {

                    case Rock:
                        // Spock vaporizes rock
                    case Scissors:
                        // Spock smashes scissors
                        result = GameState.Player1Wins;
                        System.out.println(result);
                        player1.addWin();
                        break;

                    case Paper:
                        // paper disproves Spock
                    case Lizard:
                        // lizard poisons Spock
                        result = GameState.Player2Wins;
                        System.out.println(result);
                        player2.addWin();
                        break;

                    case Spock:
                        declareTie();
                        break;

                    case None:
                    default:
                        break;
                }
                break;

            case None:
                break;
        }
    }

    public void paint(Graphics g){

        final int xPlayer1 = 25;
        final int xPlayer2 = 575;
        // assume the rock image is representative of most of the image widths

        final int imageGap = (xPlayer2 - xPlayer1);

        // rough estimate of where to draw the image when the game result is a draw
        final int xDraw = xPlayer1 + imageWidth + (imageGap - imageDraw.getWidth())/20;

        int x;
        int y = 200;

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 1200, 1000);
        g.setColor(Color.GREEN);
        Font f = new Font("Arial", Font.BOLD | Font.ITALIC, 50);

        //
        g.setFont(f);
        g.drawString(result.toString(), 250,50);
        g.drawString("Draws: " + numDraws, 325,125);
        g.drawString("Player 1: " + player1.getWins(), 25,125);
        g.drawString("Player 2: " + player2.getWins(), 575,125);
        
        if(gameOver){

            switch (result){

                case Draw:
                    g.drawImage(imageDraw, xDraw, y, null);
                    break;

                case Player1Wins:
                    g.setColor(Color.GREEN);
                    g.fillRect(0, 175, 350, 350);
                    g.setColor(Color.RED);
                    g.fillRect(550, 175, 350, 350);
                    break;

                case Player2Wins:
                    g.setColor(Color.RED);
                    g.fillRect(0, 175, 350, 350);
                    g.setColor(Color.GREEN);
                    g.fillRect(550, 175, 350, 350);
                    break;

                case InProgress:
                    break;
            }



            x = xPlayer1;

            switch(player1.getHand()){

                case Rock:
                    g.drawImage(imageRock, x, y, null);
                    break;
                case Paper:
                    g.drawImage(imagePaper, x, y, null);
                    break;
                case Scissors:
                    g.drawImage(imageScissors, x, y, null);
                    break;
                case Lizard:
                    g.drawImage(imageLizard, x, y, null);
                    break;
                case Spock:
                    g.drawImage(imageSpock, x, y, null);
                    break;
                case None:
                    break;
            }

            x = xPlayer2;
            switch (player2.getHand()){

                case Rock:
                    g.drawImage(imageRock, x, y, null);
                    break;
                case Paper:
                    g.drawImage(imagePaper, x, y, null);
                    break;
                case Scissors:
                    g.drawImage(imageScissors, x, y, null);
                    break;
                case Lizard:
                    g.drawImage(imageLizard, x, y, null);
                    break;
                case Spock:
                    g.drawImage(imageSpock, x, y, null);
                    break;
                case None:
                    break;
            }

        }

    }
    
    public void keyTyped(KeyEvent e) {
        
        c = e.getKeyChar();     
    }

    public void keyPressed(KeyEvent e) {
        c = e.getKeyChar();
    }
}