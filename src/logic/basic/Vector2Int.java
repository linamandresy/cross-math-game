package logic.basic;

import java.awt.Graphics;

import display.ConstanteDisplay;
import logic.util.Constante;

public class Vector2Int {
    private int x,y;

    private static final Vector2Int s_Zero = new Vector2Int(0,0);
    private static final Vector2Int s_One = new Vector2Int(1,1);
    private static final Vector2Int s_Up = new Vector2Int(0,1);
    private static final Vector2Int s_Down = new Vector2Int(0,-1);
    private static final Vector2Int s_Left = new Vector2Int(-1,0);
    private static final Vector2Int s_Right = new Vector2Int(1,0);
    
    public Vector2Int negativeValue(){
        return new Vector2Int(-x,-y);
    }
    public void increment(Vector2Int v){
        this.x+= v.x;
        this.y+=v.y;
    }
    public static Vector2Int getZero() {
        return s_Zero;
    }

    public static Vector2Int getOne() {
        return s_One;
    }

    public static Vector2Int getUp() {
        return s_Up;
    }

    public static Vector2Int getDown() {
        return s_Down;
    }

    public static Vector2Int getLeft() {
        return s_Left;
    }

    public static Vector2Int getRight() {
        return s_Right;
    }

    public Vector2Int() {
    }

    public Vector2Int(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void paint(Graphics g,String text){
        g.drawRect(Constante.MARGIN_LEFT+(this.x*Constante.MULTIPLICATEUR_TAILLE), Constante.MARGIN_TOP+(this.y*Constante.MULTIPLICATEUR_TAILLE), ConstanteDisplay.largeur, ConstanteDisplay.hauteur);
        g.drawString(text, Constante.MARGIN_LEFT+(this.x*Constante.MULTIPLICATEUR_TAILLE)-10, Constante.MARGIN_TOP+(this.y*Constante.MULTIPLICATEUR_TAILLE));
    }
}