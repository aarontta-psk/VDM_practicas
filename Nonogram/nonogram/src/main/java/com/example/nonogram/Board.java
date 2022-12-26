package com.example.nonogram;

import com.example.engine_common.interfaces.IRender;
import com.example.engine_common.shared.FontType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board {
    static int SECONDS_CHECKED = 5;

    public boolean win = false;

    private Cell[][] board;
    private List<Integer>[] cols;
    private List<Integer>[] rows;

    private int board_cell_size;
    private int separation_margin;
    private int fontSize;

    private int cellsLeft;
    private int height, width;
    private int posX = 0, posY = 0;

    private String fontWrongText;

    private int maxNumbers;
    private List<Cell> checkedCells;
    private double lastTimeChecked;

    public void init(int w, int h) {
        this.width = w;
        this.height = h;
        this.board = new Cell[this.width][this.height];
        for (int i = 0; i < this.width; i++)
            for (int j = 0; j < this.height; j++)
                this.board[i][j] = new Cell();

        Random random = new Random();
        this.cols = new ArrayList[width];
        this.rows = new ArrayList[height];
        this.checkedCells = new ArrayList<>();

        this.maxNumbers = 1;
        for (int i = 0; i < this.height; i++) {
            this.rows[i] = new ArrayList<>();
            this.rows[i].add(-1);
            if (this.maxNumbers < rows[i].size())
                this.maxNumbers = rows[i].size();
        }

        // creación aleatoria del tablero
        for (int i = 0; i < this.width; i++) {
            this.cols[i] = new ArrayList<>();
            this.cols[i].add(-1);
            for (int j = 0; j < this.height; j++) {
                int rand = random.nextInt(10);
                this.board[i][j].init(rand < 4);
                if (rand < 4) {
                    this.cellsLeft++;

                    // rellenado vector columnas
                    if (this.cols[i].get(this.cols[i].size() - 1) == -1) {
                        this.cols[i].remove(this.cols[i].size() - 1);
                        this.cols[i].add(1);
                        if (this.maxNumbers < cols[i].size())
                            this.maxNumbers = cols[i].size();
                    } else
                        this.cols[i].set(this.cols[i].size() - 1, this.cols[i].get(this.cols[i].size() - 1) + 1);

                    // rellenado vector filas
                    if (this.rows[j].get(this.rows[j].size() - 1) == -1) {
                        this.rows[j].remove(this.rows[j].size() - 1);
                        this.rows[j].add(1);
                        if (this.maxNumbers < this.rows[j].size())
                            this.maxNumbers = this.rows[j].size();
                    } else
                        this.rows[j].set(this.rows[j].size() - 1, this.rows[j].get(this.rows[j].size() - 1) + 1);
                } else {   //Añadimos -1 al vector de filas y columnas si no es solucion y la casilla anterior si
                    if (this.cols[i].get(this.cols[i].size() - 1) != -1)
                        this.cols[i].add(-1);
                    if (this.rows[j].get(this.rows[j].size() - 1) != -1)
                        this.rows[j].add(-1);
                }
            }
            if (this.cols[i].get(this.cols[i].size() - 1) != -1)
                this.cols[i].add(-1);
        }

        for (int i = 0; i < this.height; i++) {
            if (this.rows[i].get(this.rows[i].size() - 1) != -1)
                this.rows[i].add(-1);
        }

        int maxDimension = Math.max(w, h);
        int winW = (GameManager.getInstance().getWidth()) / (maxDimension + maxDimension / 8);
        int winH = ((int) (GameManager.getInstance().getHeight() / 1.85) - maxNumbers * fontSize) / (maxDimension + maxDimension / 8);

        this.board_cell_size = Math.min(winH, winW);
        this.separation_margin = Math.max(this.board_cell_size / 25, 1);
        this.board_cell_size -= this.separation_margin;
        this.fontSize = this.board_cell_size / 3;

        this.posX = (GameManager.getInstance().getWidth() - (this.board_cell_size + this.separation_margin) * this.width
                - this.maxNumbers * this.fontSize) / 2;
        this.posY = ((int) (GameManager.getInstance().getHeight() / 0.75f) - (this.board_cell_size + this.separation_margin) * this.height
                - maxNumbers * fontSize) / 2;

        // error message setup
        this.fontWrongText = Resources.FONT_SIMPLY_SQUARE_BIG;
        this.lastTimeChecked = -1;
    }

    public void render(IRender renderMng) {
        renderMng.setColor(0xFF000000); // board limits
        renderMng.drawRectangle(this.maxNumbers * this.fontSize + this.posX, this.posY + this.maxNumbers * this.fontSize,
                this.width * (this.board_cell_size + this.separation_margin) + 1,
                this.height * (this.board_cell_size + this.separation_margin) + 1, false);
        renderMng.drawRectangle(this.posX + this.maxNumbers * this.fontSize, this.maxNumbers * this.fontSize + this.posY,
                this.width * (this.board_cell_size + this.separation_margin) + 1,
                this.height * (this.board_cell_size + this.separation_margin) + 1, false);

        // board number indicators
        printNumbers(renderMng);

        // board cells
        for (int i = 0; i < this.width; i++)
            for (int j = 0; j < this.height; j++)
                this.board[i][j].render(renderMng, i * this.board_cell_size + (i + 1) * this.separation_margin +
                                this.posX + this.maxNumbers * this.fontSize,
                        j * this.board_cell_size + (j + 1) * this.separation_margin +
                                this.posY + this.maxNumbers * this.fontSize, this.board_cell_size);

        // board fail text
        if (this.lastTimeChecked != -1) {
            renderMng.setColor(0xFFFF0000);
            renderMng.setFont(this.fontWrongText);
            int textWidth = renderMng.getTextWidth(this.fontWrongText, "Te faltan " + this.checkedCells.size() + " casillas");
            int textWidth2 = renderMng.getTextWidth(this.fontWrongText, "Tienes mal " + this.checkedCells.size() + " casillas");
            int textHeight = renderMng.getTextHeight(this.fontWrongText);
            renderMng.drawText((renderMng.getWidth() - textWidth) / 2, posY - renderMng.getHeight() / 10,
                    "Te faltan " + cellsLeft + " casillas");
            renderMng.drawText((renderMng.getWidth() - textWidth2) / 2, posY - renderMng.getHeight() / 10 + textHeight * 2,
                    "Tienes mal " + checkedCells.size() + " casillas");
        }
    }

    public void renderWin(IRender renderMng) {
        this.posX = (GameManager.getInstance().getWidth() - this.board_cell_size * width - this.separation_margin * (this.width + 1)) / 2;
        for (int i = 0; i < this.width; i++)
            for (int j = 0; j < this.height; j++)
                if (this.board[i][j].isAnswer())
                    this.board[i][j].render(renderMng, i * this.board_cell_size + (i + 1) * this.separation_margin + this.posX,
                            j * this.board_cell_size + (j + 1) * this.separation_margin + this.posY - GameManager.getInstance().getHeight() / 10,
                            this.board_cell_size);
    }

    public void update(double deltaTime) {
        // check timer
        if (this.lastTimeChecked != -1) {
            if (this.lastTimeChecked - deltaTime < 0) {
                this.lastTimeChecked = -1;
                for (int i = 0; i < this.checkedCells.size(); i++)
                    this.checkedCells.get(i).uncheck();
                this.checkedCells.clear();
            } else
                this.lastTimeChecked -= deltaTime;
        }
    }

    private void printNumbers(IRender renderMng) {
        renderMng.setFont(renderMng.loadFont("fonts/SimplySquare.ttf", FontType.DEFAULT, this.fontSize));
        for (int i = 0; i < this.cols.length; i++) {
            if (this.cols[i].size() == 1)
                renderMng.drawText(this.posX + this.board_cell_size * (i + 1) + this.separation_margin * i -
                                this.board_cell_size / 2 + this.maxNumbers * fontSize,
                        this.posY + this.maxNumbers * this.fontSize - 2 * this.separation_margin, "0");
            for (int j = this.cols[i].size() - 2; j >= 0; j--) {
                int w = this.cols[i].get(j);
                renderMng.drawText(this.posX + this.board_cell_size * (i + 1) + this.separation_margin * i -
                                this.board_cell_size / 2 + this.maxNumbers * this.fontSize,
                        this.posY + this.maxNumbers * this.fontSize - 2 * this.separation_margin -
                                (this.fontSize * (this.cols[i].size() - 2 - j)),
                        Integer.toString(w));
            }
        }

        for (int i = 0; i < this.rows.length; i++) {
            if (this.rows[i].size() == 1)
                renderMng.drawText(this.posX + this.maxNumbers * this.fontSize - 8 * this.separation_margin,
                        this.posY + this.board_cell_size * (i + 1) + this.separation_margin * i -
                                (int) (this.board_cell_size / 2.5) + this.maxNumbers * this.fontSize, "0");
            for (int j = rows[i].size() - 2; j >= 0; j--) {
                int w = rows[i].get(j);
                renderMng.drawText(this.posX + this.maxNumbers * this.fontSize - 8 * this.separation_margin -
                                (this.fontSize * (this.rows[i].size() - 2 - j)),
                        this.posY + this.board_cell_size * (i + 1) + this.separation_margin * i -
                                (int) (this.board_cell_size / 2.5) + this.maxNumbers * this.fontSize, Integer.toString(w));
            }
        }
    }

    public void check() {
        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                if (!this.board[i][j].isAnswer() && this.board[i][j].getState() == Cell.State.MARKED) {
                    this.board[i][j].setChecked();
                    this.checkedCells.add(this.board[i][j]);
                }
            }
        }

        this.lastTimeChecked = SECONDS_CHECKED;
        if (this.checkedCells.size() == 0 && this.cellsLeft == 0)
            this.win = true;
    }

    public boolean isInBoard(int posX, int posY) {
        return posX > (this.separation_margin + this.posX + this.maxNumbers * this.fontSize) &&
                posX < (this.width * this.board_cell_size + this.width * this.separation_margin + this.posX + this.maxNumbers * this.fontSize) &&
                posY > (this.separation_margin + this.posY + this.maxNumbers * this.fontSize) &&
                posY < (this.height * this.board_cell_size + this.height * this.separation_margin + this.posY + this.maxNumbers * this.fontSize);
    }

    public void markCell(int x, int y) {
        int boardX = (x - this.posX - this.separation_margin - this.maxNumbers * this.fontSize) -
                (x - this.posX - this.separation_margin - this.maxNumbers * this.fontSize) / this.board_cell_size * this.separation_margin;
        int boardY = (y - this.posY - this.separation_margin - this.maxNumbers * this.fontSize) -
                (y - this.posY - this.separation_margin - this.maxNumbers * this.fontSize) / this.board_cell_size * this.separation_margin;
        this.cellsLeft -= this.board[boardX / this.board_cell_size][boardY / this.board_cell_size].changeCell();
    }

    public int getCellSize() {
        return this.board_cell_size;
    }

    public int getMarginCells() {
        return this.separation_margin;
    }

    public int getWidth() {
        return this.cols.length;
    }

    public int getHeight() {
        return this.rows.length;
    }
}
