/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe_javaproject_v.pkg1.pkg1;

import java.util.ArrayList;
import java.util.Collections;
import static java.util.Collections.list;
import java.util.List;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author hagerelsayed
 */
public class TicTacToe_JavaProject_v11 extends Application {
    int loginid=0;
    int register=0;//return of register database func
    database db;
    List <Integer> x_col=new ArrayList<Integer>();
    List <Integer> x_row=new ArrayList<Integer>();
    List <Integer> o_col=new ArrayList<Integer>();
    List <Integer> o_row=new ArrayList<Integer>();

    Gui gui = new Gui();
    Winner winner = new Winner();
    MenuBar menuBar;
    String winnerPlayer = null;

    public static final int ROWS = 3;
    public static final int COLS = 3;
    GridPane gridPane;
    Stage stage = new Stage();

    private GameState currentState; // enum{  PLAYING, DRAW, X_WON, O_WON}
    private CellContent currentPlayer;     //{X|O}Enum

    Label[][] cells;
    int currentRow, currentCol;

    Text decorationText = new Text("Tic Tac Toe");
    ImageView logo = new ImageView(this.getClass().getResource("/images/logo1.jpg").toString());
    Label logoLbl = new Label();

    public static boolean isWin = false;

    @Override
    public void init() throws Exception {
        super.init();
        currentPlayer = CellContent.O;       // O plays first
        currentState = GameState.PLAYING; // ready to play
        cells = new Label[ROWS][COLS];
//
        gridPane = new GridPane();
        // gridPane.setGridLinesVisible(true);
        gridPane.setAlignment(Pos.CENTER);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Label lbl = new Label();
                lbl.getStyleClass().add("cell");
                cells[i][j] = lbl;
                lbl.setMinWidth(150);
                lbl.setMinHeight(150);
                lbl.setAlignment(Pos.CENTER);
                gridPane.add(lbl, j, i);
            }
        }

        if (!isWin) {
            gridPane.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent event) -> {
                for (Node node : gridPane.getChildren()) {
                    if (node instanceof Label) {
                        if (node.getBoundsInParent().contains(event.getSceneX(), event.getSceneY())) {
                            if (cells[GridPane.getRowIndex(node)][GridPane.getColumnIndex(node)].getText().isEmpty()) {
                                cells[GridPane.getRowIndex(node)][GridPane.getColumnIndex(node)]
                                        .setText((currentPlayer == CellContent.X) ? "X" : "O");
                                currentCol = GridPane.getColumnIndex(node);
                                currentRow = GridPane.getRowIndex(node);
                                if(currentPlayer == CellContent.X){
                                System.out.println( "xrow="+currentRow);
                                System.out.println( "xcol="+currentCol);
                                x_col.add(currentCol);
                                x_row.add(currentRow);
                                
                                }
                                if(currentPlayer == CellContent.O){
                                System.out.println( "orow="+currentRow);
                                System.out.println( "ocol="+currentCol);
                                o_col.add(currentCol);
                                o_row.add(currentRow);
                                }
                              
                                
                                
                                if (winner.hasWon((currentPlayer == CellContent.X) ? "X" : "O")) {
//                                
                                    currentState = (currentPlayer == CellContent.X) ? GameState.X_WON : GameState.O_WON;
                                } else if (winner.isDraw()) {
                                    currentState = GameState.DRAW;
                                }
                                //====== Change color which depends on currenPlayer=====
                                if (currentPlayer == CellContent.X) {
                                    node.setStyle("-fx-text-fill: #2196f3;");
                                } else {
                                    node.setStyle("-fx-text-fill: #e91e63;");

                                }

                                switch (currentState) {
                                    case X_WON:
                                        winnerPlayer = "X";
                                        gui.showWinningVideo(winnerPlayer);
                                        isWin = true;

                                        System.out.println("'X' won! Bye!");
//                                    System.exit(1);
                                        break;
                                    case O_WON:
                                        winnerPlayer = "O";
                                        isWin = true;
                                        gui.showWinningVideo(winnerPlayer);

                                        System.out.println("'O' won! Bye!");
                                        break;
                                    case DRAW:
                                        System.out.println("It's Draw! Bye!");
                                        gui.noWinner();
                                        break;
                                    default:
                                        currentPlayer = (currentPlayer == currentPlayer.X) ? currentPlayer.O : currentPlayer.X;
                                        break;
                                }
                            }
                        }
                    }
                }
            });
        }//end of check won or not
    }

    @Override
    public void start(Stage primaryStage) {

        menuBar = new MenuBar();
        menuBar.getStyleClass().add("menu-bar");

        //========== Account Menu =============
        Menu account = new Menu("Account");

        MenuItem login = new MenuItem("login");

        login.setAccelerator(KeyCombination.keyCombination("Ctrl+l"));
        MenuItem register = new MenuItem("Register");
        register.setAccelerator(KeyCombination.keyCombination("ctrl+r"));

        MenuItem menu = new MenuItem("Menu");
        menu.setAccelerator(KeyCombination.keyCombination("ctrl+m"));
        SeparatorMenuItem separatorMenuItem = new SeparatorMenuItem();
        MenuItem exit = new MenuItem("Exit");
        exit.setAccelerator(KeyCombination.keyCombination("esc"));

        //========= Help Menu ========
        Menu help = new Menu("Help");

        //===== set style to menus========
        account.getStyleClass().add("menu");
        help.getStyleClass().add("menu");

        MenuItem about = new MenuItem("About Game");
        //========= Add All Items to Menus==========
        account.getItems().addAll(login, register, menu, separatorMenuItem, exit);
        help.getItems().addAll(about);
        //Add MenuBar
        menuBar.getMenus().addAll(account, help);

        //======== set style to menu item =========
        login.getStyleClass().add("menu-item");
        register.getStyleClass().add("menu-item");
        menu.getStyleClass().add("menu-item");
        exit.getStyleClass().add("menu-item");
        about.getStyleClass().add("menu-item");

        //========= Event Handling on Menu Item ==========
        //login
        login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                gui.login();
                primaryStage.setScene(gui.scene);
                primaryStage.show();

            }
        });//end of login item event

        //Register
        register.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                gui.register();
                primaryStage.setScene(gui.scene);
                primaryStage.show();

            }
        });//end of register item event

        //Meun
        menu.setOnAction((ActionEvent event) -> {
            gui.setGameMenu();
            stage.setScene(gui.scene);
            stage.show();
        });//end of menu item event

        //about
        about.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                gui.setAboutScene();
//                stage.setScene(gui.scene);
//                stage.show();
            }
        });
        //exit
        exit.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                Platform.exit();

            }
        });//end of exit

        BorderPane borderPane = new BorderPane();

        //========== Default Scene============
        Button loginbtn = new Button("         login                ");
        Button registerbtn = new Button("     Register          ");
        //Text decorationText = new Text("Tic Tac Toe");
        ImageView img = new ImageView(this.getClass().getResource("/images/login.png").toString());
        ImageView registerImg = new ImageView(this.getClass().getResource("/images/register.png").toString());
        registerImg.setFitHeight(50);
        registerImg.setFitWidth(50);

        loginbtn.setMinSize(450, 50);
        registerbtn.setMinSize(450, 50);
        loginbtn.setGraphic(img);
        registerbtn.setGraphic(registerImg);

        //login
        loginbtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                gui.login();
                primaryStage.setScene(gui.scene);
                primaryStage.show();

            }
        });//end of login item event

        //Register
        registerbtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                gui.register();
                primaryStage.setScene(gui.scene);
                primaryStage.show();

            }
        });//end of register item event

        FlowPane root = new FlowPane();
        root.setHgap(10);
        root.setVgap(10);
        root.setOrientation(Orientation.VERTICAL);
        root.setAlignment(Pos.CENTER);
        root.setHgap(50);
        root.setVgap(50);

//        logo.setFitHeight(50);
//        logo.setFitWidth(50);
        logoLbl.setMaxSize(5, 5);
        logoLbl.setGraphic(logo);
//        logoLbl.setStyle("-fx-padding:20;");
        root.getChildren().addAll(logoLbl, decorationText, loginbtn, registerbtn);
        root.getStyleClass().add("mainBox");
        loginbtn.getStyleClass().add("btn");
        registerbtn.getStyleClass().add("btn");
        decorationText.getStyleClass().add("letter");

        borderPane.setTop(menuBar);
        borderPane.setCenter(root);
        Scene scene = new Scene(borderPane, 1000, 700);
        scene.getStylesheets().add(getClass().getResource("main.css").toExternalForm());

        //===========Login Button==============
        stage.setTitle("TIC TAC TOE Game");

        stage.setScene(scene);

        stage.show();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    class Gui {

        public Scene scene = null;

        public void login() {

            BorderPane borderPane = new BorderPane();
            borderPane.setTop(menuBar);
            Label Username = new Label("Username");
            Username.getStyleClass().add("general-text");
            TextField txfUserName = new TextField();
            txfUserName.getStyleClass().add("custom-text-field");

            Label password = new Label("password");
            password.getStyleClass().add("general-text");
            TextField txfpassw = new TextField();
            txfpassw.getStyleClass().add("custom-text-field");
            Button loginplayer = new Button("     login     ");
            logoLbl.setMaxSize(5, 5);
            logoLbl.setGraphic(logo);
            FlowPane flowPane = new FlowPane();
            flowPane.getChildren().addAll(logoLbl, decorationText, Username, txfUserName, password, txfpassw, loginplayer);
            flowPane.setHgap(50);
            flowPane.setVgap(20);
            flowPane.setOrientation(Orientation.VERTICAL);
            flowPane.setAlignment(Pos.CENTER);
            decorationText.getStyleClass().add("letter");
            loginplayer.getStyleClass().add("btn");
            loginplayer.setMinSize(460, 40);
            loginplayer.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    String playerName = txfUserName.getText();
                    String playerPassword = txfpassw.getText();
                    db = new database();
                    loginid = db.login(playerName, playerPassword);
                    if (loginid == 0) {//if login failed renter login
                        System.out.println("invalid user");
                        gui.login();
                    } else {//if login success 
                        System.out.println("loginuser=" + loginid);
//                      int game_id= db.insert_game(loginid,6);
//                        System.out.println("game_id="+game_id);
                        //here call game code
                        gui.setGameMenu();

                    }

                    System.out.println("Login");
                    setGameMenu();
                    stage.setScene(gui.scene);
                    stage.show();

                }

            });

            borderPane.setCenter(flowPane);
            scene = new Scene(borderPane, 1000, 700);
            scene.getStylesheets().add(getClass().getResource("main.css").toExternalForm());
            flowPane.getStyleClass().add("mainBox");
        }//end login func

        public void register() {
            BorderPane borderPane = new BorderPane();
            borderPane.setTop(menuBar);
            Label Username = new Label("Username");
            TextField txfUserName = new TextField();
            Label name = new Label("name");
            TextField txtName = new TextField();
            Label password = new Label("password");
            TextField txfpassw = new TextField();
            Username.getStyleClass().add("general-text");
            name.getStyleClass().add("general-text");
            password.getStyleClass().add("general-text");
            txtName.getStyleClass().add("custom-text-field");
            txfUserName.getStyleClass().add("custom-text-field");
            txfpassw.getStyleClass().add("custom-text-field");
//            Label type = new Label("prefer x or o?");
//            TextField txftype = new TextField();
            Button addplayer = new Button("Register");
            addplayer.setMinSize(460, 20);
            logoLbl.setMaxSize(5, 5);
            logoLbl.setGraphic(logo);
            FlowPane flowPane = new FlowPane();
            flowPane.getChildren().addAll(logoLbl, decorationText, Username, txfUserName, name, txtName, password, txfpassw, addplayer);
            flowPane.setHgap(50);
            flowPane.setVgap(10);
            flowPane.setOrientation(Orientation.VERTICAL);
            flowPane.setAlignment(Pos.CENTER);
            addplayer.getStyleClass().add("btn");
            addplayer.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    
                     String playerUserName = txfUserName.getText();
                    String playerPassword = txfpassw.getText();
                    String playerName = txtName.getText();
                    db = new database();
                    register = db.register(playerUserName, playerPassword, playerName);//,playerType);
                    if (register == 1) {//if register success redirect to login to start play
                        System.out.println("register success");
                        gui.login();
                    } else {//if registeration failed re-register
                        System.out.println("register failed");
                        gui.register();
                    }

                }

            });

            borderPane.setCenter(flowPane);
            scene = new Scene(borderPane, 1000, 700);
            scene.getStylesheets().add(getClass().getResource("main.css").toExternalForm());
            flowPane.getStyleClass().add("mainBox");

        }

        public void setGameMenu() {

            Button localGame = new Button("  Player VS Player Locally       ");
            Button aiGame = new Button("     Player VS Computer          ");
            Button networkGame = new Button(" Player VS Player Networking ");
            localGame.setMinSize(450, 50);
            aiGame.setMinSize(450, 50);
            networkGame.setMinSize(450, 50);

            BorderPane borderPane = new BorderPane();
            borderPane.setTop(menuBar);
            FlowPane flowPane = new FlowPane();
            logoLbl.setMaxSize(5, 5);
            logoLbl.setGraphic(logo);
            flowPane.getChildren().addAll(logoLbl, decorationText, localGame, aiGame, networkGame);
//            flowPane.setHgap(50);
//            flowPane.setVgap(10);
            flowPane.setOrientation(Orientation.VERTICAL);
            flowPane.setAlignment(Pos.CENTER);
            flowPane.setHgap(50);
            flowPane.setVgap(50);

            localGame.getStyleClass().add("btn");
            aiGame.getStyleClass().add("btn");
            networkGame.getStyleClass().add("btn");
            decorationText.getStyleClass().add("letter");

            //=========== Local Button Handler========
            localGame.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    BorderPane borderPane = new BorderPane();
                    borderPane.setTop(menuBar);
                    borderPane.setCenter(gridPane);
                    Scene scene = new Scene(borderPane, 1000, 700);
                    scene.getStylesheets().add(getClass().getResource("main.css").toExternalForm());
                    gridPane.getStyleClass().add("mainBox");

                    stage.setScene(scene);
                    stage.show();

                }

            });
            //========== AI Button============
            aiGame.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                }

            });
            //========== NetWork Button ==========
            networkGame.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                }

            });

            borderPane.setCenter(flowPane);
            scene = new Scene(borderPane, 1000, 700);
            scene.getStylesheets().add(getClass().getResource("main.css").toExternalForm());
            borderPane.getStyleClass().add("mainBox");
            flowPane.getStyleClass().add("mainBox");

        }//end of setGameMenu Method

        public void showWinningVideo(String winnerPlayer) {
            BorderPane borderPane = new BorderPane();
            borderPane.setTop(menuBar);
//            File f = new File("/home/hagerelsayed/Desktop/ww.mp4");
//            Media m = new Media(f.toURI().toString());
            MediaPlayer player = new MediaPlayer(new Media(getClass().getResource("/videos/ww.mp4").toExternalForm()));
            MediaView mediaView = new MediaView(player);
//            db=new database();
//            db.local_game_save(x_row,x_col,o_row,o_col);
//            x_row=Collections.emptyList();
//            x_col=Collections.emptyList();
//            o_row=Collections.emptyList();
//            o_col=Collections.emptyList();
//              for (int i = 0; i < x_col.size(); i++) {
//                System.out.print("xcol" + x_col.get(i) + "\n");
//
//            }
//            for (int i = 0; i < o_col.size(); i++) {
//                System.out.print("ocol" + o_col.get(i) + "\n");
//            }
//            for (int i = 0; i < x_row.size(); i++) {
//                System.out.print("xrow" + x_row.get(i) + "\n");
//            }
//            for (int i = 0; i < o_row.size(); i++) {
//                System.out.print("orow" + o_row.get(i) + "\n");
//            }

            Button cancle = new Button("Close the Game");
            Button playAgain = new Button("play again");
            cancle.getStyleClass().add("btn");
            playAgain.getStyleClass().add("btn");
            mediaView.getStyleClass().add("mediaView");
            cancle.setMinSize(450, 30);
            playAgain.setMinSize(450, 30);

            cancle.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    Platform.exit();
                    player.stop();
                }
            });
            playAgain.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    setGameMenu();
                    stage.setScene(gui.scene);
                    stage.show();
                    player.stop();

                }
            });

            FlowPane videoPane = new FlowPane();
            videoPane.setVgap(50);
            videoPane.setHgap(20);
            videoPane.setAlignment(Pos.CENTER);
            videoPane.setOrientation(Orientation.VERTICAL);
            borderPane.setCenter(videoPane);

            Text winningText = new Text("YAY,Congratulations ( " + winnerPlayer + " ) you are the Winner");

            videoPane.getChildren().addAll(winningText, mediaView, playAgain, cancle);
            videoPane.getStyleClass().add("mainBox");
            borderPane.getStyleClass().add("mainBox");

            //root.getChildren().addAll(mv,winningText);
            Scene scene = new Scene(borderPane, 1000, 700);
            stage.setScene(scene);
            scene.getStylesheets().add(getClass().getResource("main.css").toExternalForm());
            winningText.getStyleClass().add("winText");
            stage.setTitle("congratulations");
            stage.show();
            player.play();

        }//end of showWinningvideo

        public void noWinner() {
            BorderPane borderPane = new BorderPane();
            borderPane.setTop(menuBar);
            Text hardLuck = new Text("Unfortunatlly,No Winner!!");
            Label sadLabel = new Label();
            ImageView sad = new ImageView(this.getClass().getResource("/images/sad1.png").toString());
            Button cancle = new Button("Close the Game");
            Button playAgain = new Button("play again");
            cancle.getStyleClass().add("btn");
            playAgain.getStyleClass().add("btn");
            sadLabel.setStyle("-fx-padding:20;");
            sadLabel.setGraphic(sad);
            sadLabel.setMinSize(50, 50);
            cancle.setMinSize(450, 30);
            playAgain.setMinSize(450, 30);

            cancle.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    Platform.exit();

                }
            });
            playAgain.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    setGameMenu();
                    stage.setScene(gui.scene);
                    stage.show();

                }
            });

            FlowPane flowPane = new FlowPane();
            flowPane.setVgap(50);
            flowPane.setHgap(20);
            flowPane.setAlignment(Pos.CENTER);
            flowPane.setOrientation(Orientation.VERTICAL);
            borderPane.setCenter(flowPane);
            flowPane.getChildren().addAll(sadLabel, hardLuck, playAgain, cancle);
            Scene scene = new Scene(borderPane, 1000, 700);
            stage.setScene(scene);

            scene.getStylesheets().add(getClass().getResource("main.css").toExternalForm());
            hardLuck.getStyleClass().add("hardLuck-text");
            flowPane.getStyleClass().add("mainBox");
            borderPane.getStyleClass().add("mainBox");

            stage.setTitle("Hard Luck");
            stage.show();

        }//end of no Winner Scene

        public void setAboutScene() {
            BorderPane borderPane = new BorderPane();
            borderPane.setTop(menuBar);
            

            FlowPane flowPane = new FlowPane();
            GridPane gridPane = new GridPane();

            Label desginedBy = new Label("Designed BY");
            Label name1 = new Label("Hager Elsayed");
            Label name2 = new Label("Maiam Eleraky");
            Label name3 = new Label("Somaya Hegab");
            Label name4 = new Label("Nader Nagy");
            gridPane.addRow(0, name1);
            gridPane.addRow(1, name2);
            gridPane.addRow(2, name3);
            gridPane.addRow(3, name4);

            flowPane.getChildren().addAll(desginedBy, gridPane);
            flowPane.setAlignment(Pos.CENTER);
            flowPane.setOrientation(Orientation.VERTICAL);
            borderPane.setCenter(flowPane);

            Scene scene = new Scene(borderPane, 1000, 700);
            stage.setScene(scene);

            scene.getStylesheets().add(getClass().getResource("main.css").toExternalForm());

            flowPane.getStyleClass().add("mainBox");
            borderPane.getStyleClass().add("mainBox");

            stage.setTitle("About Us");
            stage.show();

        }//end of about Scene

    }//end of GUI Class

    class Winner {

        public boolean hasWon(String currentPlayer) {
            return (cells[currentRow][0].getText().equals(currentPlayer) // 3-in-the-row
                    && cells[currentRow][1].getText().equals(currentPlayer)
                    && cells[currentRow][2].getText().equals(currentPlayer)
                    || cells[0][currentCol].getText().equals(currentPlayer) // 3-in-the-column
                    && cells[1][currentCol].getText().equals(currentPlayer)
                    && cells[2][currentCol].getText().equals(currentPlayer)
                    || currentRow == currentCol // 3-in-the-diagonal
                    && cells[0][0].getText().equals(currentPlayer)
                    && cells[1][1].getText().equals(currentPlayer)
                    && cells[2][2].getText().equals(currentPlayer)
                    || currentRow + currentCol == 2 // 3-in-the-opposite-diagonal
                    && cells[0][2].getText().equals(currentPlayer)
                    && cells[1][1].getText().equals(currentPlayer)
                    && cells[2][0].getText().equals(currentPlayer));
        }

        public boolean isDraw() {
            for (int row = 0; row < ROWS; ++row) {
                for (int col = 0; col < COLS; ++col) {
                    if (cells[row][col].getText().isEmpty()) {
                        return false; // Empty
                    }
                }
            }
            return true; // no empty cell, it's a draw
        }

    }

}
