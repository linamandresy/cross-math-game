package display;

import logic.Cell;
import logic.util.Constante;

public class CrossMathBoardCell extends CrossMathCell {
    
    public CrossMathBoardCell(Cell cell,CrossMathFrame frame){
        super(cell,frame);
        setBounds(Constante.MARGIN_LEFT+(cell.getCurrentIndex().getX()*Constante.MULTIPLICATEUR_TAILLE), Constante.MARGIN_TOP+(cell.getCurrentIndex().getY()*Constante.MULTIPLICATEUR_TAILLE), ConstanteDisplay.largeur, ConstanteDisplay.hauteur);
    }
}
