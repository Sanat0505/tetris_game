package com.example.tetris_game;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

class DrawView extends View {
    int yOffset;
    Paint paint;
    GameState gameState;

    public DrawView(Context context, final GameState gameState) {
        super(context);
        paint = new Paint();
        paint.setColor(Color.BLUE);
        yOffset = 200;
        this.gameState = gameState;
    }

    private int getBlockColorCode(int color) {
        switch (color) {
            case 1:
                return Color.BLUE;
            case 2:
                return Color.YELLOW;
            case 3:
                return Color.RED;
            case 4:
                return Color.GREEN;
            case 5:
                return Color.CYAN;
            case 6:
                return Color.MAGENTA;
            case 7:
                return Color.DKGRAY;
            default:
                return Color.TRANSPARENT;
        }

    }

    private void DrawMatrix(BasicBlock[][] matrix, Canvas canvas) {
        for (int i = 0; i < 24; i++) {
            for (int j = 0; j < 20; j++) {
                if (matrix[i][j].state == BasicBlockState.ON_EMPTY)
                    continue;

                int color = this.getBlockColorCode(matrix[i][j].colour);
                Paint p = new Paint();
                p.setColor(color);
                canvas.drawRect(42 + j * 50, yOffset + i * 50 + 2, 88 + j * 50, yOffset + (i + 1) * 50 - 2, p);
            }
        }
    }

    private void Clear(BasicBlock[][] matrix, Canvas canvas) {
        Paint p = new Paint();
        p.setColor(Color.WHITE);
        for (int i = 0; i < 24; i++) {
            for (int j = 0; j < 20; j++) {
                canvas.drawRect(42 + j * 50, yOffset + i * 50 + 2, 88 + j * 50, yOffset + (i + 1) * 50 - 2, p);
            }
        }
    }

    private void DrawTetramino(Tetramino tetramino, Canvas canvas) {
        for (BasicBlock block : tetramino.blocks) {
            int color = this.getBlockColorCode(block.colour);
            Paint p = new Paint();
            p.setColor(color);
            canvas.drawRect(42 + block.coordinate.x * 50, yOffset + block.coordinate.y * 50 + 2, 88 + block.coordinate.x * 50, yOffset + (block.coordinate.y + 1) * 50 - 2, p);

        }
    }

    private void Boundary(Canvas canvas) {
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(5f);
        canvas.drawLine(40, yOffset, 40, yOffset + 1200, paint);
        canvas.drawLine(40, yOffset, 1040, yOffset, paint);
        canvas.drawLine(1040, yOffset, 1040, yOffset + 1200, paint);
        canvas.drawLine(1040, yOffset + 1200, 40, yOffset + 1200, paint);
    }

    private void grid(Canvas canvas) {
        paint.setStrokeWidth(2f);
        for (int i = 90; i < 1040; i = i + 50) {
            canvas.drawLine(i, yOffset, i, yOffset + 1200, paint);
        }
        for (int j = 50; j < 1200; j = j + 50) {
            canvas.drawLine(40, yOffset + j, 1040, yOffset + j, paint);
        }
    }

    private void PrintScore(int score, Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.TRANSPARENT);
        canvas.drawRect(0, 100, 200, 200, paint);
        paint.setColor(Color.BLACK);
        paint.setTextSize(100);
        canvas.drawText(Integer.toString(score), 80, 170, paint);

    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(5f);
        Boundary(canvas);
        grid(canvas);
        if (gameState.status) {
            Clear(gameState.board, canvas);
            DrawMatrix(gameState.board, canvas);
            DrawTetramino(gameState.falling, canvas);
            PrintScore(gameState.score, canvas);
        } else {
            Paint paint = new Paint();
            DrawMatrix(gameState.board, canvas);
            DrawTetramino(gameState.falling, canvas);
            paint.setColor(Color.BLACK);
            paint.setTextSize(200);
            canvas.drawText(getResources().getString(R.string.game_over), 60, 800, paint);
            PrintScore(gameState.score, canvas);
        }

    }

}

