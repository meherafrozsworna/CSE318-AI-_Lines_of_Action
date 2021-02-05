package human_vs_human;

/*
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("human_vs_human.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 550, 550));
        primaryStage.show();
    }


    public static void BoardFrame(String[] args) {
        launch(args);
    }
}
*/
import javafx.application.Application;
import javafx.scene.paint.Color;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.LinkedList;
import java.util.Queue;


public class Main extends Application {
    int input_x = -1;
    int input_y = -1;
    int dest_x = -1 ;
    int dest_y = -1 ;
    int in = 0 ;
    int turn = 1;
    int not_turn = 2 ;
    public static int board_size = 8 ;
    public static int board[][] = new int[board_size][board_size] ;

    @Override
    public void start(Stage stage) {
        Button[][] button = new Button[8][8];
        Label label = new Label("blue's turn");
        //label.setTextFill(Color.web("#ff0000", 0.8));
        label.setTextFill(Color.BROWN);
        label.setFont(Font.font("Arial", FontWeight.BOLD,30));

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8 ; j++) {
                button[i][j] = new Button();
                button[i][j].setPrefHeight(50);
                button[i][j].setPrefWidth(50);
                Button btn = button[i][j];
                if (board[i][j] == 1){
                    btn.setStyle("-fx-background-image :  url('blue_piece.png');");
                }else if (board[i][j]==2){
                    btn.setStyle("-fx-background-image :  url('red_piece.png');");
                }
                String s = i+","+j;

                btn.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        System.out.println(s);
                        if (in == 0){
                            String str[] = s.split(",");
                            input_x = Integer.parseInt(str[0]) ;
                            input_y = Integer.parseInt(str[1]) ;
                            in = 1 ;
                        }else{
                            String str[] = s.split(",");
                            dest_x = Integer.parseInt(str[0]) ;
                            dest_y = Integer.parseInt(str[1]) ;
                            in = 0 ;
                            int winner = move();
                            for (int k = 0; k < board_size; k++) {
                                for (int l = 0; l < board_size ; l++) {
                                    if (board[k][l] == 1){
                                        button[k][l].setStyle("-fx-background-image :  url('blue_piece.png');-fx-background-size : 100% 100% ;");
                                    }else if (board[k][l]==2){
                                        button[k][l].setStyle("-fx-background-image :  url('red_piece.png');-fx-background-size : 100% 100% ;");
                                    }else {
                                        button[k][l].setStyle("-fx-background-image :  url('nothing.png');");
                                    }
                                }
                            }
                            if (winner == 1 ){
                                label.setText("blue wins");
                            }else if (winner ==2){
                                label.setText("red wins");
                            }
                            else if (turn==1)
                                label.setText("blue's turn");
                            else if (turn ==2)
                                label.setText("red's turn");

                            if (winner != 0){
                                for (int k = 0; k < board_size; k++) {
                                    for (int l = 0; l < board_size; l++) {
                                        button[k][l].setDisable(true);
                                        //button[k][l].setStyle("-fx-background-image :  url('nothing.png');");
                                    }
                                }
                            }
                        }
                    }
                });
            }
        }


        //Creating a Grid Pane
        GridPane gridPane = new GridPane();


        //Setting size for the pane
        gridPane.setMinSize(400, 400);

        //Setting the Grid alignment
        gridPane.setAlignment(Pos.CENTER);

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                gridPane.add(button[i][j],j,i);
            }
        }

        //Arranging all the nodes in the grid
        /*gridPane.add(text1, 0, 0);
        gridPane.add(textField1, 1, 0);
        gridPane.add(text2, 0, 1);
        gridPane.add(textField2, 1, 1);
        gridPane.add(button1, 0, 2);
        gridPane.add(button2, 1, 2);*/

        //Creating a scene object

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setMinSize(400,500);

        AnchorPane.setTopAnchor(label,10.0);
        AnchorPane.setLeftAnchor(label,120.0);
        AnchorPane.setTopAnchor(gridPane,50.0);
        anchorPane.getChildren().addAll(label,gridPane);
        Scene scene = new Scene(anchorPane);


        //Setting title to the Stage
        stage.setTitle("Grid Pane Example");

        //Adding scene to the stage
        stage.setScene(scene);

        //Displaying the contents of the stage
        stage.show();
    }
    public static void main(String args[]){

        for (int i = 0; i < board_size; i++) {
            for (int j = 0; j < board_size; j++) {
                board[i][j] = 0;
            }
        }
        for (int i = 1; i < board_size-1 ; i++) {
            board[0][i] = 1;
            board[board_size-1][i] = 1;
        }
        for (int i = 1; i < board_size-1 ; i++) {
            board[i][0] = 2;
            board[i][board_size-1] = 2;
        }
        for (int i = 0; i < board_size; i++) {
            for (int j = 0; j < board_size; j++) {
                System.out.print(board[i][j]+" ");
            }
            System.out.println();
        }

        launch(args);

    }

    public int move()
    {

            int flag = 0;
            int return_value = 0 ;

            if (input_x == dest_x && input_y != dest_y){
                System.out.println("AAA");
                int count = 0 ;
                if (board[input_x][input_y] != turn){
                    flag = 1;
                }else if (board[dest_x][dest_y] == turn){
                    flag = 1 ;
                }

                for (int i = 0; i < board_size ; i++) {
                    if (board[input_x][i] != 0)
                        count++ ;
                }
                if (Math.abs(input_y - dest_y)==count){

                    if (input_y < dest_y){
                        for (int i = input_y+1; i < dest_y; i++) {
                            if (board[input_x][i] == not_turn){
                                //System.out.println("You may not jump over your opponents pieces");
                                flag = 1;
                                break;
                            }
                        }
                    }else{
                        for (int i = input_y-1; i > dest_y; i--) {
                            if (board[input_x][i] == not_turn){
                                //System.out.println("You may not jump over your opponents pieces");
                                flag = 1;
                                break;
                            }
                        }
                    }
                }else{
                    flag = 1;
                }


            }else if (input_y == dest_y && input_x != dest_x){
                System.out.println("BBB");
                int count = 0 ;
                if (board[input_x][input_y] != turn){
                    flag = 1;
                }else if (board[dest_x][dest_y] == turn){
                    flag = 1 ;
                }

                for (int i = 0; i < board_size ; i++) {
                    if (board[i][input_y] != 0)
                        count++ ;
                }
                if (Math.abs(input_x - dest_x)== count){

                    if (input_x < dest_x){
                        for (int i = input_x+1; i < dest_x; i++) {
                            if (board[i][input_y] == not_turn){
                                //System.out.println("You may not jump over your opponents pieces");
                                flag = 1;
                                break;
                            }
                        }
                    }else{
                        for (int i = input_x-1; i > dest_x; i--) {
                            if (board[i][input_y] == not_turn){
                                //System.out.println("You may not jump over your opponents pieces");
                                flag = 1;
                                break;
                            }
                        }
                    }
                }else{
                    flag = 1;
                }

                /*if (flag == 0){
                    System.out.println("valid move");
                    board[input_x][input_y] = 0;
                    board[dest_x][dest_y] = turn ;
                    for (int i = 0; i < board_size; i++) {
                        for (int j = 0; j < board_size; j++) {
                            System.out.print(board[i][j]+" ");
                        }
                        System.out.println();
                    }
                }else {
                    System.out.println("invalid dest");
                    continue;
                }*/
            }else if (Math.abs(input_x-dest_x) == Math.abs(input_y-dest_y)){
                System.out.println("CCC");
                int count = 0 ;
                if (board[input_x][input_y] != turn){
                    flag = 1;
                }else if (board[dest_x][dest_y] == turn){
                    flag = 1 ;
                }

                if (((input_x-dest_x)>0 && (input_y-dest_y)>0) ||
                        ((input_x-dest_x)<0 && (input_y-dest_y)<0)){    // \
                    System.out.println("MM");
                    int top_corner_x = input_x ;
                    int top_corner_y = input_y ;
                    while (top_corner_x != 0 && top_corner_y!= 0){
                        top_corner_x--;
                        top_corner_y-- ;
                    }
                    while (top_corner_x != (board_size ) && top_corner_y != (board_size ) ){
                        if (board[top_corner_x][top_corner_y] != 0)
                            count++;
                        top_corner_x++;
                        top_corner_y++;
                    }
                    if (Math.abs(input_x-dest_x)== count){
                        if (input_x < dest_x){
                            int i = 1;
                            while ((input_x+i) != dest_x){
                                if (board[input_x+i][input_y+i] == not_turn){
                                    flag = 1;
                                    break;
                                }
                                i++ ;
                            }
                        }else {
                            int i = 1;
                            while ((dest_x+i) != input_x){
                                if (board[dest_x+i][dest_y+i] == not_turn){
                                    flag = 1;
                                    break;
                                }
                                i++ ;
                            }
                        }
                    }else{
                        flag = 1 ;
                    }
                }else{   //   /
                    System.out.println("NN");
                    int top_corner_x = input_x ;
                    int top_corner_y = input_y ;
                    while (top_corner_x != 0 && top_corner_y!= (board_size-1)){
                        top_corner_x--;
                        top_corner_y++ ;
                        System.out.println(top_corner_x +"   "+ top_corner_y);
                    }
                    while (top_corner_x != (board_size ) && top_corner_y >= 0 ){
                        if (board[top_corner_x][top_corner_y] != 0)
                            count++;
                        top_corner_x++ ;
                        top_corner_y-- ;
                        System.out.println("PPPPP");
                    }
                    System.out.println("LLL");
                    if (Math.abs(input_x-dest_x)== count){
                        System.out.println("OOO");
                        if (input_x < dest_x){
                            int i = 1;
                            while ((input_x+i) != dest_x){
                                if (board[input_x+i][input_y-i] == not_turn){
                                    flag = 1;
                                    break;
                                }
                                i++ ;
                            }
                        }else {
                            int i = 1;
                            while ((dest_x+i) != input_x){
                                if (board[dest_x+i][dest_y-i] == not_turn){
                                    flag = 1;
                                    break;
                                }
                                i++ ;
                            }
                        }
                    }else{
                        flag = 1 ;
                    }
                }

            }else{
                flag = 1 ;
            }


            int win = 0 ;
            if (flag == 0){
                System.out.println("valid move");
                board[input_x][input_y] = 0;
                board[dest_x][dest_y] = turn ;
                for (int i = 0; i < board_size; i++) {
                    for (int j = 0; j < board_size; j++) {
                        System.out.print(board[i][j]+" ");
                    }
                    System.out.println();
                }

                //check kew win hoilo naki

                //check if turn wins

                boolean turnwin = checkwin(turn);
                if (turnwin){
                    System.out.println("\n\n"+turn+ "wins.........\n");
                    if (turn==1)
                        return_value=1;
                    else
                        return_value = 2 ;
                    win = 1;
                }else{
                    boolean not_turnWin = checkwin(not_turn);
                    if (not_turnWin){
                        System.out.println("\n\n"+not_turn+ "wins.........\n");
                        if (not_turn == 1)
                            return_value=1;
                        else
                            return_value = 2 ;
                        win = 1;
                    }
                }

                if (turn == 1){
                    turn = 2 ;
                    not_turn = 1 ;
                }else {
                    turn = 1 ;
                    not_turn = 2;
                }


            }else{
                System.out.println("invalid input");
            }


        /*if (flag == 0)
            return true;
        else
            return false;
            */
        return return_value ;

    }


    public static boolean checkwin(int turn){
        boolean added[][] = new boolean[board_size][board_size];
        for (int i = 0; i < board_size ; i++) {
            for (int j = 0; j < board_size; j++) {
                added[i][j] = false ;
            }
        }
        int count = 0 ;
        int start_x = -1;
        int start_y = -1;
        int connected = 0;
        Queue<Integer> q_x = new LinkedList<>() ;
        Queue<Integer> q_y = new LinkedList<>();
        for (int i = 0; i < board_size ; i++) {
            for (int j = 0; j < board_size; j++) {
                if(board[i][j]==turn){
                    count++;
                    if (start_x == -1){
                        start_x = i ;
                        start_y = j ;
                    }
                }

            }
        }
        if (count == 1)
            return true ;
        q_x.add(start_x);
        q_y.add(start_y);
        connected++;
        added[start_x][start_y] = true ;

        while (!q_x.isEmpty()){
            int i = q_x.peek();
            int j = q_y.peek();
            q_x.remove();
            q_y.remove();

            if((i>0 && board[i-1][j]==turn) && added[i-1][j]==false){
                connected++;
                added[i-1][j] = true ;
                q_x.add(i-1);
                q_y.add(j) ;
            }
            if((i<(board_size-1)) && board[i+1][j] == turn && added[i+1][j]==false){
                connected++;
                added[i+1][j] = true ;
                q_x.add(i+1);
                q_y.add(j) ;
            }
            if((j>0) && board[i][j-1] == turn && added[i][j-1]==false){
                connected++;
                added[i][j-1] = true ;
                q_x.add(i);
                q_y.add(j-1) ;
            }
            if((j<(board_size-1)) && board[i][j+1]==turn && added[i][j+1]==false){
                connected++;
                added[i][j+1] = true ;
                q_x.add(i);
                q_y.add(j+1) ;
            }
            if((i>0) && (j>0) && board[i-1][j-1] == turn && added[i-1][j-1] == false){
                connected++;
                added[i-1][j-1] = true ;
                q_x.add(i-1);
                q_y.add(j-1) ;
            }
            if((i>0) && (j<(board_size-1)) && board[i-1][j+1]== turn && added[i-1][j+1]==false){
                connected++;
                added[i-1][j+1] = true ;
                q_x.add(i-1);
                q_y.add(j+1) ;
            }
            if((i<(board_size-1)) && (j>0) && board[i+1][j-1]== turn && added[i+1][j-1]==false){
                connected++;
                added[i+1][j-1] = true ;
                q_x.add(i+1);
                q_y.add(j-1) ;
            }
            if((i<(board_size-1)) && (j < board_size-1) && board[i+1][j+1]==turn && added[i+1][j+1]==false){
                connected++;
                added[i+1][j+1] = true ;
                q_x.add(i+1);
                q_y.add(j+1) ;
            }
        }
        if (connected == count)
            return true ;
        else
            return false ;




        /*int flagwin = 0;
        for (int i = 0; i < board_size; i++) {
            for (int j = 0; j < board_size; j++) {
                if (board[i][j]== turn){
                    if ((i>0 && board[i-1][j]==turn) ||
                            ((i<(board_size-1)) && board[i+1][j] == turn) ||
                            ((j>0) && board[i][j-1] == turn) ||
                            ((j<(board_size-1)) && board[i][j+1]==turn )||
                            ((i>0) && (j>0) && board[i-1][j-1] == turn) ||
                            ((i>0) && (j<(board_size-1)) && board[i-1][j+1]== turn) ||
                            ((i<(board_size-1)) && (j>0) && board[i+1][j-1]== turn) ||
                            ((i<(board_size-1)) && (j < board_size-1) && board[i+1][j+1]==turn)
                            ){

                    }else {
                        flagwin =1 ;
                        break;
                    }
                }
            }
            if (flagwin ==1)
                break;
        }
        if (flagwin == 0){
            return true;
        }else
            return false;
            */
    }
}