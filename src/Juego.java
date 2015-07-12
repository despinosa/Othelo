
import javax.swing.JButton;
import javax.swing.JOptionPane;


public class Juego extends Thread{
	
	private MiCanvas C;
	private int CELDAS;
	private Tablero t;
	JButton b1, b2;
	JugadorHumano jugador1;
	JugadorMaquina jugador2;
	
	public Juego(MiCanvas canvas, int CELD, JButton bot1, JButton bot2)
	{
		super();
		
		C = canvas;
		CELDAS = CELD;
		b1 = bot1;
		b2 = bot2;
	}
	
	private void inicializar() {
		t = new Tablero(CELDAS);
		C.setTablero(t);
		C.repaint();
		
		jugador1 = new JugadorHumano();
		jugador2 = new JugadorMaquina(false); 
	}
	
	
	public void run() {
		inicializar();
		
		boolean turno_rojo = true;
		int color;
		Movimiento m;
		
		while(!t.estaLleno() && (t.puedeTirar(1) || t.puedeTirar(2)))
		{
			m = null;
			color = 0;
			if (turno_rojo) {
				if (t.puedeTirar(1)) {
					jugador1.setTablero(t);
					jugador1.setCanvas(C);
					m = jugador1.run();
					color = 1;
				} else {
                                        JOptionPane.showMessageDialog(null, "El jugador rojo no puede tirar");
				}
			} else {
				if (t.puedeTirar(2)) {
					jugador2.setTablero(t);
					m = jugador2.run();
					color = 2;
				} else {
                                        JOptionPane.showMessageDialog(null, "El jugador amarillo no puede tirar");
				}
			}
			turno_rojo = !turno_rojo;
			
			if (m!=null) 
                        {
				t.ponerFicha(m.columna,m.fila,color);
				C.setTablero(t);
				C.repaint();
			}
		}
		
		switch(t.ganador()){
		case 0:
                        JOptionPane.showMessageDialog(null, "Empate");
			break;
		case 1:
                        JOptionPane.showMessageDialog(null, "Gana jugador rojo");
			break;
		case 2:
                        JOptionPane.showMessageDialog(null, "Gana jugador amarillo");
			break;
		}
		
		b1.setEnabled(true);
		b2.setEnabled(false);
	}
}
