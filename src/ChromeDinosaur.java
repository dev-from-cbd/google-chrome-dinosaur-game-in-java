import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class ChromeDinosaur extends JPanel {
    int boardWidth = 750;
    int boardHeight = 250;

    //Images
    Image dinasaurImg;
    Image dinasaurDeadImg;
    Image dinasaurJumpImg;
    Image cactus1Img;
    Image cactus2Img;
    Image cactus3Img;

    public ChromeDinosaur(){
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setBackground(Color.LIGHT_GRAY);

        dinasaurImg = new ImageIcon(getClass().getResource("./img/dino-run.gif")).getImage();
        dinasaurDeadImg = new ImageIcon(getClass().getResource("./img/dino-dead.gif")).getImage();
        dinasaurJumpImg = new ImageIcon(getClass().getResource("./img/dino-jump.gif")).getImage();
        cactus1Img = new ImageIcon(getClass().getResource("./img/cactus1.gif")).getImage();
        cactus2Img = new ImageIcon(getClass().getResource("./img/cactus2.gif")).getImage();
        cactus3Img = new ImageIcon(getClass().getResource("./img/cactus3.gif")).getImage();
    }
}
