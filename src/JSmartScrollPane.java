import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

public class JSmartScrollPane extends JScrollPane {
	
	super();

	private static final long serialVersionUID = 1L;

	private final JScrollBar scrollBar = super.getVerticalScrollBar();
	
	scrollBar.addAdjustmentListener(new AdjustmentListener() {
		
		public void adjustmentValueChanged(AdjustmentEvent e) {
			
			if (((float) e.getValue() / (scrollBar.getMaximum() - scrollBar.getVisibleAmount() - 16)) == 1.0f) {
				
				scrollBar.setValue(scrollBar.getMaximum());
				
			}
		}
		
	});
	
}
