package client.gui;

import client.controller.ConcentrationController;
import client.model.ConcentrationModel;
import client.model.Observer;

import client.ptui.ConcentrationPTUI;
import common.ConcentrationException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * A JavaFX GUI client for the network concentration game.  It represent the "View"
 * component of the MVC architecture in use here.
 *
 * @author RIT CS
 * @author Dhruv Rajpurohit
 */
public class ConcentrationGUI extends Application implements Observer<ConcentrationModel, ConcentrationModel.CardUpdate> {
    private ConcentrationModel model;
    private ConcentrationController control;
    private BorderPane border;
    private TilePane tile;
    private Button resetbut;
    private Button undobut;
    private Button card;
    private Label movLab;
    private Image down;
    private final ArrayList<Image> ups = new ArrayList<>( model.getNumMatches() );
    int row;
    int col;

    /**
     * This is to get all the images to be used in the game.
     */
    private void getImages() {

        this.down =
                new Image(getClass().getResourceAsStream("images/pokeball.png"));

        ArrayList<String> imagenames = new ArrayList<>(model.getNumMatches());
        imagenames.add("images/abra.png");
        imagenames.add("images/bulbasaur.png");
        imagenames.add("images/charizard.png");
        imagenames.add("images/diglett.png");
        imagenames.add("images/golbat.png");
        imagenames.add("images/golem.png");
        imagenames.add("images/jigglypuff.png");
        imagenames.add("images/magikarp.png");
        imagenames.add("images/meowth.png");
        imagenames.add("images/mewtwo.png");
        imagenames.add("images/natu.png");
        imagenames.add("images/pidgey.png");
        imagenames.add("images/pikachu.png");
        imagenames.add("images/poliwag.png");
        imagenames.add("images/psyduck.png");
        imagenames.add("images/rattata.png");
        imagenames.add("images/slowpoke.png");
        imagenames.add("images/snorlak.png");
        imagenames.add("images/squirtle.png");

        Class<?> myClass = this.getClass();
        for (int n = 0; n < model.getNumMatches(); ++n) {
            try {
                String imageName = imagenames.get(n);
                InputStream imgStream = myClass.getResourceAsStream(imageName);
                Image image = new Image(imgStream);
                this.ups.add(image);
                imgStream.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void init() {
        try {
            List<String> args = getParameters().getRaw();

            // get host and port from command line
            String host = args.get(0);
            int port = Integer.parseInt(args.get(1));

            // create the model, and add ourselves as an observer
            this.model = new ConcentrationModel();
            this.model.addObserver(this);

            // initiate the controller
            this.control = new ConcentrationController(host, port, this.model);
        }
        catch (NumberFormatException e){
            System.err.println(e);
            throw new RuntimeException(e);
        }
        catch (ConcentrationException e){
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage stage) {
        Label alab = new Label("select the first card");
        alab.setAlignment(Pos.CENTER_LEFT);
        this.border.setTop(alab);

        getImages();

        this.tile.setPrefRows(4);
        this.tile.setPrefRows(4);
        
        for (int i = 0; i < 16 ; i++) {

            int indx = i;
            ImageView imge = new ImageView(down);
            imge.setFitHeight(80);
            imge.setFitWidth(80);

            PokeButtons butt = new PokeButtons(model.CardUpdate.get(i).getNumber(),imge);
            butt.setPrefSize(80,80);

            butt.setOnAction(event -> model.getCard(row,col));
            this.tile.getChildren().add(butt);

        }

        this.border.setCenter(this.tile);
        BorderPane bot = new BorderPane();
        HBox Hbot = new HBox();


        Hbot.getChildren().add(resetbut);
        Hbot.getChildren().add(undobut);
        bot.setCenter(Hbot);

        this.movLab = new Label(" 0 Moves");
        movLab.setAlignment(Pos.CENTER_RIGHT);

        bot.setRight(movLab);
        this.border.setBottom(bot);


        Scene scene = new Scene(this.border);
        stage.setTitle("Conc");
        stage.setScene(scene);


        stage.show();

    }


    @Override
    public void update(ConcentrationModel model, ConcentrationModel.CardUpdate card) {
        int numcards = model.getNumMatches();
        if(numcards==0){
            movLab.setText("Select first card");
        }
        else if(numcards ==1){
            movLab.setText("Select Another Card");
        }
        else{
            movLab.setText("No Match: Undo or select");
        }
        for(int i =0; i<16; i++){
            PokeButtons butt = (PokeButtons) this.tile.getChildren().get(i);
            if (model.CardUpdate().get(i).isFaceUp()){
                ImageView imge = new ImageView();
                imge = new ImageView(ups.get(butt.getIndx()));
                imge.setFitWidth(80);
                imge.setFitHeight(80);
                butt.setPic(imge);
            }
            else{
                ImageView dwn = new ImageView(down);
                dwn.setFitHeight(80);
                dwn.setFitWidth(80);
                butt.setPic(dwn);
            }
        }
        if(model.getNumMoves() == 0){
            for (int i = 0; i < this.row; i++){
                PokeButtons butt = (PokeButtons) this.tile.getChildren().get(i);
                butt.setIndx(model.getDIM());
                ImageView dwn = new ImageView(down);
                dwn.setFitHeight(80);
                dwn.setFitWidth(80);
                butt.setPic(dwn);


            }
        }

        movLab.setText(model.getNumMoves()+"Moves");
        }

    @Override
    public void stop() {
        this.control.close();
        System.exit(0);
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java ConcentrationGUI host port");
            System.exit(-1);
        } else {
            Application.launch(args);
        }
    }
}
