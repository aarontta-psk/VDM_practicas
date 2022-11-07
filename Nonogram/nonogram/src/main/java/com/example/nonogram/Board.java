package com.example.nonogram;

import com.example.engine_common.interfaces.IEngine;
import com.example.engine_common.interfaces.IRender;
import com.example.engine_common.shared.FontType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board {
    static int BOARD_CELLSIZE = 60;
    static int SEPARATION_MARGIN = 5;
    static int SEGS_CHECKED = 5;

    private int board_cell_size;
    private Cell[][] board;
    private List<Integer>[] cols;
    private List<Integer>[] rows;
    private int cellsLeft;
    private int height, width;
    private String font;
    private String sound;
    private String music;

    private List<Cell> checkedCells;
    private double lastTimeChecked;

    public void render(IRender renderMng){
        int x=0, y=0;
        renderMng.setColor(0xFF000000); //Cuadrados alrededor
        renderMng.drawRectangle(board_cell_size + x, y,width*(board_cell_size +SEPARATION_MARGIN) + 2, (height + 1)*(board_cell_size +SEPARATION_MARGIN), false);
        renderMng.drawRectangle(x, board_cell_size + y,(width + 1)*(board_cell_size +SEPARATION_MARGIN), height*(board_cell_size +SEPARATION_MARGIN) + 2, false);

        printNumbers(renderMng);

        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                board[i][j].render(renderMng, (i + 1)* board_cell_size + (i+1)*SEPARATION_MARGIN + x, (j + 1)* board_cell_size + (j + 1)*SEPARATION_MARGIN + y, board_cell_size);
            }
        }
    }

    public void init(int h, int w, IEngine eng) {
        int winW = eng.getRender().getWidth();

        board_cell_size = eng.getRender().getWidth()/(w + 1) - SEPARATION_MARGIN;

        height = h;
        width = w;
        board = new Cell[width][height];
        for(int i=0; i<width; i++){
            for(int j=0; j<height; j++){
                board[i][j] = new Cell();
            }
        }
        cols = new ArrayList[width];
        rows = new ArrayList[height];
        Random random = new Random();

        checkedCells = new ArrayList<Cell>();

        for(int i=0; i<height; i++) {
            rows[i] = new ArrayList<Integer>();
            rows[i].add(-1);
        }

        for(int i = 0; i < width; i++){        //Creación aleatoria del tablero
            cols[i] = new ArrayList<Integer>();
            cols[i].add(-1);
            for(int j = 0; j < height; j++){
                int rand = random.nextInt(10);
                board[i][j].init(rand<4);
                if (rand < 4) {
                    cellsLeft++;

                    if(cols[i].get(cols[i].size() - 1) == -1){      //Rellenado vector columnas
                        cols[i].remove(cols[i].size()-1);
                        cols[i].add(1);
                    }
                    else{
                        cols[i].set(cols[i].size() - 1, cols[i].get(cols[i].size() - 1) + 1);
                    }

                    if(rows[j].get(rows[j].size() - 1) == -1){      //Rellenado vector filas
                        rows[j].remove(rows[j].size()-1);
                        rows[j].add(1);
                    }
                    else{
                        rows[j].set(rows[j].size() - 1, rows[j].get(rows[j].size() - 1) + 1);
                    }
                }
                else{   //Añadimos -1 al vector de filas y columnas si no es solucion y la casilla anterior si
                    if(cols[i].get(cols[i].size() - 1) != -1)
                        cols[i].add(-1);
                    if(rows[j].get(rows[j].size() - 1) != -1)
                        rows[j].add(-1);
                }
            }
            if(cols[i].get(cols[i].size() - 1) != -1)
                cols[i].add(-1);
        }

        for(int i=0; i<height; i++) {
            if (rows[i].get(rows[i].size() - 1) != -1)
                rows[i].add(-1);
        }

        font = eng.getRender().loadFont("./assets/fonts/FFF_Tusj.ttf", FontType.DEFAULT, 60);
        lastTimeChecked = -1;
    }

    public void update(double deltaTime){
        if(lastTimeChecked != -1){
            if(lastTimeChecked - deltaTime < 0){
                lastTimeChecked = -1;
                for(int i=0; i<checkedCells.size(); i++){
                    checkedCells.get(i).unChecked();
                }
                checkedCells.clear();
            }
            else
                lastTimeChecked -= deltaTime;
        }
    }

    private void printNumbers(IRender renderMng){
        renderMng.setFont(font);
        for(int i=0; i< cols.length; i++){
            if(cols[i].size() == 1){
                renderMng.drawText(board_cell_size * (i + 2) + SEPARATION_MARGIN * i - board_cell_size/2, (int)(board_cell_size/1.1), "0");
            }
            for(int j=cols[i].size()-2; j>=0; j--) {
                int w = cols[i].get(j);
                renderMng.drawText(board_cell_size * (i + 2) + SEPARATION_MARGIN * i - board_cell_size/2, (int)(board_cell_size/1.1) - (12*(cols[i].size()-2-j)), Integer.toString(w));
            }
        }

        for(int i=0; i< rows.length; i++){
            if(rows[i].size() == 1){
                renderMng.drawText((int)(board_cell_size/1.4), board_cell_size * (i + 2) + SEPARATION_MARGIN * i - (int)(board_cell_size/2.5), "0");
            }
            for(int j=rows[i].size()-2; j>=0; j--) {
                int w = rows[i].get(j);
                renderMng.drawText((int)(board_cell_size/1.4) - (12*(rows[i].size()-2-j)), board_cell_size * (i + 2) + SEPARATION_MARGIN * i
                        - (int)(board_cell_size/2.5), Integer.toString(w));
            }
        }
    }

    public void checkear(){
        for(int i=0; i<width; i++){
            for(int j=0; j<height; j++){
                if(!board[i][j].isAnswer() && board[i][j].getState() == Cell.State.MARKED){
                    board[i][j].setChecked();
                    checkedCells.add(board[i][j]);
                    lastTimeChecked = SEGS_CHECKED;
                }
            }
        }
    }

    public void markCell(int x, int y) {
        cellsLeft += board[x][y].changeCell();
    }
    public int getCellSize(){return board_cell_size;}
    public int getMarginCells(){return SEPARATION_MARGIN;}
    public int getWidth(){ return cols.length;}
    public int getHeight(){ return rows.length;}
}
