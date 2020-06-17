package com.tmge.samegame;

import com.tmge.tilegames.Tile;
import javafx.scene.paint.Color;

public class SameGameTile extends Tile {
	public char color;
	protected boolean isEmpty;
	protected Color colorFill;
	
	SameGameTile(int row, int  col, char color) {
		super(row, col);
		isEmpty = false;
		setColor(color);

	}

	@Override
	public String toString() {
		return (isEmpty)? "_" : Character.toString(color);
	}

	public Color getColorFill() {
		return colorFill;
	}

	public boolean isEmpty() {
		return color == '_';
	}

	public void setColor(char color) {
		this.color = color;
		switch (color) {
			case 'R': this.colorFill = Color.DARKRED; break;
			case 'G': this.colorFill = Color.DARKGREEN; break;
			case 'B': this.colorFill = Color.DARKBLUE; break;
			case 'Y': this.colorFill = Color.YELLOW; break;
			default: this.colorFill = Color.DARKGREY;
		}
	}
}