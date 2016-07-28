package boardview;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import model.Position;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

/**
 * View class for a tile on a chess board
 * A tile should be able to display a chess piece
 * as well as highlight itself during the game.
 *
 * @author Nathalie Ruano
 */
public class TileView implements Tile {
    private StackPane stacky = new StackPane();
    private Label symbolLabel = new Label();
    private Color highlight;
    private Color color;
    private Position position;
    private Rectangle tileRec;
    private String symbol;
    /**
     * Creates a TileView with a specified position
     * @param p
     */
    public TileView(Position p) {
        this.position = p;
    }

    @Override
    public Position getPosition() {
        return position;
    }


    @Override
    public Node getRootNode() {
        stacky.getChildren().clear();
        if ((position.getRow() + position.getCol()) % 2 == 0) {
            color = Color.DARKSALMON;
        } else {
            color = Color.WHITE;
        }
        tileRec = new Rectangle();
        tileRec.setWidth(70);
        tileRec.setHeight(70);
        tileRec.setFill(color);
        stacky.getChildren().addAll(tileRec, symbolLabel);
        return stacky;
    }

    @Override
    public void setSymbol(String symbol) {
        this.symbol = symbol;
        symbolLabel.setText(symbol);
        symbolLabel.setFont(new Font("Calibri", 30));
    }


    @Override
    public String getSymbol() {
        return symbol;
    }

    @Override
    public void highlight(Color inColor) {
        highlight = inColor;
        tileRec.setFill(highlight);
    }

    @Override
    public void clear() {
        if ((position.getRow() + position.getCol()) % 2 == 0) {
            tileRec.setFill(Color.WHITE);
        } else {
            tileRec.setFill(Color.DARKSALMON);
        }
    }
}
