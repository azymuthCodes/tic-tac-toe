import javafx.animation.PauseTransition;
import javafx.util.Duration;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.FlowPane;


public class TicTacToe extends Application {
    private String player="";
    private String ai="";
    private String winner="";
    private Text result=new Text(winner);
    private Stage root=new Stage();
    private Button[] buttons=new Button[9];
    public void start(Stage primaryStage){
        root=primaryStage;
        root.setTitle("TicTacToe");
        GridPane gamePane=new GridPane();
    
        Scene gameScene=new Scene(gamePane,310,360);
        gameScene.getStylesheets().add("style.css");
        

        Text text=new Text("Choose a Player");
        text.setId("text");
        Button button1=new Button("X");
        button1.setPrefSize(70, 70);
        button1.setOnAction(event -> {player="X";ai="O";root.setScene(gameScene);});

        Button button2=new Button("O");
        button2.setPrefSize(70, 70);
        button2.setOnAction(event -> {player="O";ai="X";root.setScene(gameScene);});

        FlowPane textPane=new FlowPane(text);
        textPane.setId("textpane");
        textPane.setAlignment(Pos.TOP_CENTER);

        FlowPane buttonsPane=new FlowPane(button1,button2);
        buttonsPane.setAlignment(Pos.CENTER);
        buttonsPane.setHgap(70);
        buttonsPane.setVgap(30);

        VBox box=new VBox(textPane,buttonsPane);
        box.setSpacing(50);
        box.setAlignment(Pos.CENTER);


        Scene playerScene=new Scene(box,310,360);
        playerScene.getStylesheets().add("style.css");

        String[] coords={"(0,0)","(1,0)","(2,0)","(0,1)","(1,1)","(2,1)","(0,2)","(1,2)","(2,2)"};
        for(int i=0;i<9;i++){
            buttons[i]=new Button();
            buttons[i].setPrefSize(70, 70);
            buttons[i].setOnAction(this::processButton);
            gamePane.add(buttons[i],(int)(coords[i].charAt(1))-(int)'0',(int)coords[i].charAt(3)-(int)'0');
        }


        gamePane.setHgap(30);
        gamePane.setVgap(30);
        gamePane.setPadding(new Insets(30, 30, 30, 30));
        
        
        root.setScene(playerScene);
        root.show();
    }
    public void processButton(ActionEvent event){

        //Setting the player's character on the desired position
        boolean playerturn=true;
        for(int i=0;i<9;i++){
            if(event.getSource()==buttons[i]){
                if(buttons[i].getText()==""){
                    buttons[i].setText(player);
                    playerturn=false;
                    
                    break;
                }
            }
        }

        //Check if player's turn is over
        if(!playerturn){
            int turns=0;
            while(turns<9){
                int location=(int)(Math.random()*8)+1;
                if(buttons[location].getText()=="" && player.equals("X")){
                    buttons[location].setText(ai);
                    getWinner();
                    break;
                }
                else if(buttons[location].getText()=="" && player.equals("O")){
                    buttons[location].setText(ai);
                    break;
                }
                else{
                    turns++;
                }
            }
        }
    
        //Check if its draw
        if(!getWinner()){
            int counter=0;
            for(int i=0;i<9;i++){
                if(buttons[i].getText()!=""){
                    counter++;
                }
            }
            if(counter==9){
                result.setText("Draw!!!!");
                tryAgain();
            }
        }
        else{
            result.setText(winner+" is the winner");
            delay();   
        }
    }

    //If User opts to try again
    public void tryAgain(){
        
        Button againButton=new Button("Try Again?");
        againButton.setPrefSize(210,30);
        againButton.setOnAction(e-> {TicTacToe restart =new TicTacToe();restart.start(root);});

        Button quitButton=new Button("Quit");
        quitButton.setPrefSize(210,30);
        quitButton.setOnAction(e->System.exit(0));

        result.setFill(javafx.scene.paint.Color.WHITE);
        
        FlowPane againPane=new FlowPane(result,againButton,quitButton);
        againPane.setAlignment(Pos.CENTER);
        againPane.setHgap(310);
        againPane.setVgap(50);

        Scene againScene=new Scene(againPane,310,360);
        
        againScene.getStylesheets().add("style.css");
        root.setScene(againScene);
    }


    //Pausing the application to see the result
    public void delay() {
        PauseTransition pause=new PauseTransition(Duration.seconds(3));
        pause.setOnFinished(e->tryAgain());
        pause.play();
    }



    //To Check who won
    public boolean getWinner(){
    //Check if Player won

        //Check Row 1
        if(buttons[0].getText()==player && buttons[1].getText()==player && buttons[2].getText()==player){
            for(int i=0;i<9;i++){
                if(i<3){
                    buttons[i].setStyle("-fx-background-color:green;");
                }
                buttons[i].setDisable(true);
            }
            winner=player;
            return true;
        }

        // Check Row 2
        else if(buttons[3].getText()==player && buttons[4].getText()==player && buttons[5].getText()==player){
            for(int i=0;i<9;i++){
                if(i>=3 && i<6){
                    buttons[i].setStyle("-fx-background-color:green;");
                }
                buttons[i].setDisable(true);
            }
            winner=player;
            return true;
        }

        //Check Row 3
        else if(buttons[6].getText()==player && buttons[7].getText()==player && buttons[8].getText()==player){
            for(int i=0;i<9;i++){
                if(i>=6){
                    buttons[i].setStyle("-fx-background-color:green;");
                }
                buttons[i].setDisable(true);
            }
            winner=player;
            return true;
        }

        //Check Column 1
        else if(buttons[0].getText()==player && buttons[3].getText()==player && buttons[6].getText()==player){
            for(int i=0;i<9;i++){
                if(i%3==0){
                    buttons[i].setStyle("-fx-background-color:green;");
                }
                buttons[i].setDisable(true);
            }
            winner=player;
            return true;
        }

        //Check Column 2
        else if(buttons[1].getText()==player && buttons[4].getText()==player && buttons[7].getText()==player){
            for(int i=0;i<9;i++){
                if((i+2)%3==0){
                    buttons[i].setStyle("-fx-background-color:green;");
                }
                buttons[i].setDisable(true);
            }
            winner=player;
            return true;
        }

        //Check Column 3
        else if(buttons[2].getText()==player && buttons[5].getText()==player && buttons[8].getText()==player){
            for(int i=0;i<9;i++){
                if((i+1)%3==0){
                    buttons[i].setStyle("-fx-background-color:green;");
                }
                buttons[i].setDisable(true);
            }
            winner=player;
            return true;
        }

        //Check Diagonal from Top left to bottom right
        else if(buttons[0].getText()==player && buttons[4].getText()==player && buttons[8].getText()==player){
            for(int i=0;i<9;i++){
                if(i%4==0){
                    buttons[i].setStyle("-fx-background-color:green;");
                }
                buttons[i].setDisable(true);
            }
            winner=player;
            return true;
        }

        //Check Diagonal from Top right to bottom left
        else if(buttons[2].getText()==player && buttons[4].getText()==player && buttons[6].getText()==player){
            for(int i=0;i<9;i++){
                if(i%2==0 && i!=0 && i!=8){
                    buttons[i].setStyle("-fx-background-color:green;");
                }
                buttons[i].setDisable(true);
            }
            winner=player;
            return true;
        }
    //Check if AI won
    
        //Check Row 1
        if(buttons[0].getText()==ai && buttons[1].getText()==ai && buttons[2].getText()==ai){
            for(int i=0;i<9;i++){
                if(i<3){
                    buttons[i].setStyle("-fx-background-color:green;");
                }
                buttons[i].setDisable(true);
            }
            winner=ai;
            return true;
        }

        // Check Row 2
        else if(buttons[3].getText()==ai && buttons[4].getText()==ai && buttons[5].getText()==ai){
            for(int i=0;i<9;i++){
                if(i>=3 && i<6){
                    buttons[i].setStyle("-fx-background-color:green;");
                }
                buttons[i].setDisable(true);
            }
            winner=ai;
            return true;
        }

        //Check Row 3
        else if(buttons[6].getText()==ai && buttons[7].getText()==ai && buttons[8].getText()==ai){
            for(int i=0;i<9;i++){
                if(i>=6){
                    buttons[i].setStyle("-fx-background-color:green;");
                }
                buttons[i].setDisable(true);
            }
            winner=ai;
            return true;
        }

        //Check Column 1
        else if(buttons[0].getText()==ai && buttons[3].getText()==ai && buttons[6].getText()==ai){
            for(int i=0;i<9;i++){
                if(i%3==0){
                    buttons[i].setStyle("-fx-background-color:green;");
                }
                buttons[i].setDisable(true);
            }
            winner=ai;
            return true;
        }

        //Check Column 2
        else if(buttons[1].getText()==ai && buttons[4].getText()==ai && buttons[7].getText()==ai){
            for(int i=0;i<9;i++){
                if((i+2)%3==0){
                    buttons[i].setStyle("-fx-background-color:green;");
                }
                buttons[i].setDisable(true);
            }
            winner=ai;
            return true;
        }

        //Check Column 3
        else if(buttons[2].getText()==ai && buttons[5].getText()==ai && buttons[8].getText()==ai){
            for(int i=0;i<9;i++){
                if((i+1)%3==0){
                    buttons[i].setStyle("-fx-background-color:green;");
                }
                buttons[i].setDisable(true);
            }
            winner=ai;
            return true;
        }

        //Check Diagonal from Top left to bottom right
        else if(buttons[0].getText()==ai && buttons[4].getText()==ai && buttons[8].getText()==ai){
            for(int i=0;i<9;i++){
                if(i%4==0){
                    buttons[i].setStyle("-fx-background-color:green;");
                }
                buttons[i].setDisable(true);
            }
            winner=ai;
            return true;
        }

        //Check Diagonal from Top right to bottom left
        else if(buttons[2].getText()==ai && buttons[4].getText()==ai && buttons[6].getText()==ai){
            for(int i=0;i<9;i++){
                if(i%2==0 && i!=0 && i!=8){
                    buttons[i].setStyle("-fx-background-color:green;");
                }
                buttons[i].setDisable(true);
            }
            winner=ai;
            return true;
        }
        else{
            return false;
        }
        
    }
}

